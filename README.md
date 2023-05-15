# AttestationSpoofer

### Spoofs a fake root of trust in local attestations.

NOTE: this Xposed module won't help you to pass MEETS_STRONG_INTEGRITY in Play Integrity API.

It only works in local attestations, for example, Key Attestation Demo or some bank apps like CIB.

If you are a developer and want to check if the device has an unlocked bootloader, never trust user data.
You must send to a secure server the X509Certificate and check the attestation extension in that server, or use Play Integrity API.
NEVER TRUST USER DATA BECAUSE THINGS LIKE THIS MODULE MAY HAPPEN :D

Also Play Integrity API isn't perfect, I can pass MEETS_STRONG_INTEGRITY with my unlocked bootloader device.

Sooo, don't check nothing. Lets users do whatever they want with their devices.
