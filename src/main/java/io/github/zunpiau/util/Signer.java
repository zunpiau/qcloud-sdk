package io.github.zunpiau.util;

import io.github.zunpiau.constant.SignatureMethod;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Signer {

    private static final Signer instance = new Signer ();
    private Mac hash256;
    private Mac hash1;

    private Signer() {
    }

    public static Signer getInstance() {
        return instance;
    }

    public byte[] hash(SignatureMethod method, String key, String text) {
        Mac mac;
        try {
            if (SignatureMethod.HmacSHA1.equals (method)) {
                if (hash1 == null) {
                    hash1 = Mac.getInstance (method.name ());
                    hash1.init (new SecretKeySpec (key.getBytes (StandardCharsets.UTF_8), hash1.getAlgorithm ()));
                }
                mac = hash1;
            } else {
                if (hash256 == null) {
                    hash256 = Mac.getInstance (method.name ());
                    hash256.init (new SecretKeySpec (key.getBytes (StandardCharsets.UTF_8), hash256.getAlgorithm ()));
                }
                mac = hash256;
            }
            return mac.doFinal (text.getBytes ());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException (e);
        }
    }

}
