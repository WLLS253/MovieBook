package com.movie.Plugins;

import sun.misc.BASE64Encoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


public class UserPasswordEncrypt {
    private static final String pbkdf2Algorithm = "PBKDF2WithHmacSHA1";
    private static final int hashSize = 32;

    public static String encrypt(String clearText, String salt, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // pbdkf2
        byte[] saltBytes = DatatypeConverter.parseHexBinary(salt);
        KeySpec spec = new PBEKeySpec(clearText.toCharArray(), saltBytes, iterations, hashSize * 4);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(pbkdf2Algorithm);
        byte[] specHash = secretKeyFactory.generateSecret(spec).getEncoded();
        // base64
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String base64Spec = base64Encoder.encode(base64Encoder.encode(specHash).getBytes());
        // md5
        base64Spec = messageDigestIterations(base64Spec, iterations * 2);

        // md5-salt and md5-iterations
        String saltMd5 = messageDigestIterations(salt, iterations * 3);
        String iterMd5 = messageDigestIterations(String.valueOf(iterations * 233), iterations * 4);

        // combination
        String encryptedPassword;
        encryptedPassword = "pbkdf2_sha256" + "$" + iterMd5 + "$" + saltMd5 + "$" + base64Spec;

        return encryptedPassword;
    }

    // md iterations
    public static String messageDigestIterations(String clearText, int iterations) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String messageDigestedText = clearText;
        for (int i = 0; i < iterations; i++) {
            messageDigestedText = base64Encoder.encode(messageDigest.digest(messageDigestedText.getBytes()));
        }
        return messageDigestedText;
    }
}
