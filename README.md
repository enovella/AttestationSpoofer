# AttestationSpoofer

### Spoofs a fake root of trust in local attestations.

NOTE: this Xposed module won't help you to pass MEETS_STRONG_INTEGRITY in Play Integrity API.

It only works in local attestations, for example, Key Attestation Demo or some bank apps like CIB.

If you are a developer and want to check if the device has an unlocked bootloader, never trust user data.
You must send to a secure server the X509Certificate and check the attestation extension in that server, or use Play Integrity API.
NEVER TRUST USER DATA BECAUSE THINGS LIKE THIS MODULE MAY HAPPEN :D

Also Play Integrity API isn't perfect, I can pass MEETS_STRONG_INTEGRITY with my unlocked bootloader device.

Sooo, don't check nothing. Lets users do whatever they want with their devices.

Like safetynet-fix by kdrag0n, this should be implemented in custom ROMs, so Xposed won't be required and can't be detected by apps like Momo.

<img src="https://github.com/Xposed-Modules-Repo/es.swer45.attestationspoofer/assets/98092901/b597a1f7-f71e-4b1d-a70a-2d13bf5a80b7" alt= “” width="200" height="400">
<img src="https://github.com/Xposed-Modules-Repo/es.swer45.attestationspoofer/assets/98092901/181e37cc-bdca-4d1c-b0f4-633095a0f9ed" alt= “” width="200" height="400">
