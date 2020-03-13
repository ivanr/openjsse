package org.openjsse.sun.security.ssl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignatureSchemeMapper {

    // This list must match the schemes in SignatureScheme exactly.
    private static Map<String, Integer> namesToIds = new HashMap<>() {
        {
            put("ed25519", 0x0807);
            put("ed448", 0x0808);
            put("ecdsa_secp256r1_sha256", 0x0403);
            put("ecdsa_secp384r1_sha384", 0x0503);
            put("ecdsa_secp521r1_sha512", 0x0603);
            put("rsa_pss_rsae_sha256", 0x0804);
            put("rsa_pss_rsae_sha384", 0x0805);
            put("rsa_pss_rsae_sha512", 0x0806);
            put("rsa_pss_pss_sha256", 0x0809);
            put("rsa_pss_pss_sha384", 0x080A);
            put("rsa_pss_pss_sha512", 0x080B);
            put("rsa_pkcs1_sha256", 0x0401);
            put("rsa_pkcs1_sha384", 0x0501);
            put("rsa_pkcs1_sha512", 0x0601);
            put("dsa_sha256", 0x0402);
            put("ecdsa_sha224", 0x0303);
            put("rsa_sha224", 0x0301);
            put("dsa_sha224", 0x0302);
            put("ecdsa_sha1", 0x0203);
            put("rsa_pkcs1_sha1", 0x0201);
            put("dsa_sha1", 0x0202);
            put("rsa_md5", 0x0101);
        }
    };

    public static SignatureScheme findScheme(String name) {
        Integer schemeId = namesToIds.get(name);
        if (schemeId == null) {
            return null;
        }

        return SignatureScheme.valueOf(schemeId);
    }

    public static List<SignatureScheme> schemesFromNames(List<String> names) {
        List<SignatureScheme> schemes = new ArrayList<>();

        for (String name : names) {
            SignatureScheme scheme = SignatureSchemeMapper.findScheme(name);
            if (scheme == null) {
                throw new RuntimeException("Signature scheme not found: " + name);
            }

            schemes.add(scheme);
        }

        return schemes;
    }

    public static void validateSchemeNames(List<String> names) {
        schemesFromNames(names);
    }
}
