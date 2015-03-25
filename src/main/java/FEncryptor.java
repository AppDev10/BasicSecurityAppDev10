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
public class FEncryptor {
    Cipher cipherRSA = null;
    Cipher cipherDES = null;

    public FEncryptor() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipherRSA = Cipher.getInstance("RSA");
        cipherDES = Cipher.getInstance("DES");
    }

    public byte[] encrypt(byte[] input, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        return encryptRSA(input,key);
    }

    private byte[] encryptRSA(byte[] input, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipherRSA.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedInput = cipherRSA.doFinal(input);

        return encryptedInput;
    }

    public byte[] encryptDES(byte[] input, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        cipherDES.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipherDES.doFinal(input);

        return encrypted;
    }
}
