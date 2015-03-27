import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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

    public byte[] decrypt(byte[] input, Key key) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        return decryptRSA(input,key);
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
