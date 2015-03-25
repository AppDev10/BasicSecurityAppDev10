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
    Cipher cipherDES = null;

    public FDecryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipherRSA = Cipher.getInstance("RSA");
        cipherDES = Cipher.getInstance("DES");
    }

    public byte[] decrypt(byte[] input, Key key) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        return decryptRSA(input,key);
    }

    private byte[] decryptRSA(byte[] input, Key key) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        cipherRSA.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipherRSA.doFinal(input);

        return decrypted;
    }

    public byte[] decryptDES(byte[] input, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        cipherDES.init(Cipher.DECRYPT_MODE, key);
        byte[] encrypted = cipherDES.doFinal(input);

        return encrypted;
    }
}
