package es.swer45.attestationspoofer;

import com.google.common.primitives.Bytes;

import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;

import java.util.Enumeration;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;

public class Main implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        Class<?> clazz = XposedHelpers.findClass("com.android.org.conscrypt.OpenSSLX509Certificate", lpparam.classLoader);
        XposedHelpers.findAndHookMethod(clazz, "getExtensionValue", String.class, new XC_MethodReplacement(XCallback.PRIORITY_HIGHEST) {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                byte[] bytes = (byte[]) XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args);
                String oid = (String) param.args[0];
                if (!oid.equals("1.3.6.1.4.1.11129.2.1.17")) return bytes;
                ASN1Primitive primitive = ASN1Primitive.fromByteArray(bytes);
                if (!(primitive instanceof ASN1OctetString)) {
                    throw new RuntimeException("primitive is not an octet string");
                }
                ASN1OctetString octetString = (ASN1OctetString) primitive;
                byte[] octets = octetString.getOctets();
                ASN1Sequence sequence = ASN1Sequence.getInstance(octets);
                ASN1Encodable encodable = sequence.getObjectAt(7);
                if (!(encodable instanceof ASN1Sequence)) {
                    throw new RuntimeException("encodable is not a sequence");
                }
                ASN1Sequence authorizationList = (ASN1Sequence) encodable;
                Enumeration<?> enumeration = authorizationList.getObjects();
                ASN1TaggedObject rootOfTrust = null;
                while (enumeration.hasMoreElements()) {
                    Object o = enumeration.nextElement();
                    if (!(o instanceof ASN1TaggedObject)) continue;
                    ASN1TaggedObject taggedObject = (ASN1TaggedObject) o;
                    int tag = taggedObject.getTagNo();
                    if (tag != 704) continue;
                    rootOfTrust = taggedObject;
                    break;
                }
                if (rootOfTrust == null) {
                    throw new RuntimeException("Root of trust is null");
                }
                ASN1Sequence rootOfTrustSeq = (ASN1Sequence) rootOfTrust.getExplicitBaseObject();
                ASN1Boolean deviceLocked = (ASN1Boolean) rootOfTrustSeq.getObjectAt(1);
                ASN1Enumerated verifiedBootState = (ASN1Enumerated) rootOfTrustSeq.getObjectAt(2);

                byte[] deviceLockedBytes = deviceLocked.getEncoded();
                byte[] verifiedBootStateBytes = verifiedBootState.getEncoded();

                byte[] searchBytes = Bytes.concat(deviceLockedBytes, verifiedBootStateBytes);

                int index = Bytes.indexOf(bytes, searchBytes);

                bytes[index + 2] = 1;
                bytes[index + 5] = 0;

                return bytes;
            }
        });
    }
}
