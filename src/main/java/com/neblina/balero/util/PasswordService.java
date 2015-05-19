/**
 * Modificado por:  Anibal Gomez <anibalgomez@icloud.com>
 * Basado en: http://www.devbistro.com/articles/Java/Password-Encryption
 */

package com.neblina.balero.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordService {
    private static PasswordService instance;

    private PasswordService() {
    }

    public synchronized String encrypt(String plaintext) throws Exception {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA"); //step 2
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        }
        try {
            md.update(plaintext.getBytes("UTF-8")); //step 3
        } catch (UnsupportedEncodingException e) {
            throw new Exception(e.getMessage());
        }

        byte raw[] = md.digest(); //step 4
        String hash = (new BASE64Encoder()).encode(raw); //step 5
        return hash; //step 6
    }

    public static synchronized PasswordService getInstance() { // step 1
        if (instance == null) {
            instance = new PasswordService();
        }
        return instance;
    }
}