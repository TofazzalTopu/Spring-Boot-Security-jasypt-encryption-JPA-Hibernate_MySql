package com.info.prescription.service.encryptor;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

@Service
public class EncryptorDecryptorService {

    // @Comment: Jasypt encrypt and decrypted data based on the secret-key
    private static String mpSecretKey = "MY_SECRET";


    // @Comment: In this method - Jasypt decrypt encrypted data based on the secret-key
    public String setEncryptedValue(String value) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(mpSecretKey);
        String encryptedValue = encryptor.encrypt(value);
        return encryptedValue;
    }

    // @Comment: In this method - Jasypt decrypt encrypted data based on the secret-key
    public String getDecryptedValue(String value) {
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(mpSecretKey);
        return decryptor.decrypt(value);
    }

}
