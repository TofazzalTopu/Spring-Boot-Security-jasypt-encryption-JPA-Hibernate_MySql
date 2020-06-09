package com.info.prescription.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

// This is a helping class for you to generate encrypted password for your DB password
// or other any other credentials
public class EncryptConfig {

    private static String mpSecretKey = "MY_SECRET";

    public static void main(String[] args) {
        // replace root with your DB password or other any other credentials
        String valueToBeEncrypted = "root";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(mpSecretKey);
        String encryptedPassword = encryptor.encrypt(valueToBeEncrypted);
        System.out.println("EncryptedPassword: "+encryptedPassword);

        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();

        decryptor.setPassword(mpSecretKey);
        System.out.println("Actual Password: "+decryptor.decrypt(encryptedPassword));
    }
}
