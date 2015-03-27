import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 11302014 on 19/03/2015.
 */
public class FDecryptor {

    Cipher cipherRSA = null;
    Cipher cipherAES = null;

    public FDecryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipherRSA = Cipher.getInstance("RSA");
        cipherAES = Cipher.getInstance("AES");
    }

    public byte[] decrypt(FTotalPacket bigBox, Key privateBkey, Key publicAkey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException {
        byte[] decryptedHash;
        Key decryptedKey;
        byte[] decryptedFile;

        FKey encryptedKey = bigBox.getEncryptedKey();
        FHash encryptedHash = bigBox.getEncryptedHash();
        FData encryptedData = bigBox.getEncryptedData();
        //AESkey ontsleutelen
        decryptedKey = new SecretKeySpec(decryptRSA(encryptedKey.getEncryptedKey(), privateBkey), "AES");
        //data ontsleutelen
        decryptedFile = decryptAES(encryptedData.getEncryptedData(), decryptedKey);
        //hash ontsleutelen
        decryptedHash = decryptRSA(encryptedHash.getEncryptedHash(), publicAkey);
        //hash vergelijken met de gehashte gedecryteerde waarde
        byte[] gehasht = FEncryptor.sha1(decryptedFile);

        for (int i = 0; i < gehasht.length; i++) {
            if (gehasht[i] != decryptedHash[i]) {
                return null;
            }
        }

        return decryptedFile;
    }

    private byte[] decryptRSA(byte[] input, Key key) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        cipherRSA.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipherRSA.doFinal(input);

        return decrypted;
    }

    public byte[] decryptAES(byte[] input, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        cipherAES.init(Cipher.DECRYPT_MODE, key);
        byte[] encrypted = cipherAES.doFinal(input);

        return encrypted;
    }
}
