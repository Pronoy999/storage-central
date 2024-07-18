package com.storage.central.core.encryption;

import com.storage.central.common.util.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Service
public class EncryptionHelper {
    @Value("${jwt.secret.key}")
    private String randomKey;

    /**
     * Method to encrypt a string data. This method will encrypt the data using AES 128 bit Encryption.
     *
     * @param plainText: The data to be encrypted.
     * @return the Encrypted data in Base64 encoded.
     */
    public String getEncryptedData(@NotNull final String plainText) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(randomKey.getBytes(), Constants.ENCRYPTION_ALGO);
            Cipher cipher = Cipher.getInstance(Constants.ENCRYPTION_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedData = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            log.error("Exception When Encrypting data.", e);
        }
        return null;
    }

    /**
     * Method to decrypt the string Base 64 encode data and get back a Base64 encoded data decrypted.
     *
     * @param encryptedData: The data to be decrypted in Base 64 encoded.
     * @return the decrypted data in Base64 Encoded.
     */
    public String getDecryptedData(@NotNull final String encryptedData) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(randomKey.getBytes(), Constants.ENCRYPTION_ALGO);
            Cipher cipher = Cipher.getInstance(Constants.ENCRYPTION_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return Base64.getEncoder().encodeToString(decryptedBytes);
        } catch (Exception e) {
            log.error("Exception When Decrypting data.", e);
        }
        return null;
    }
}
