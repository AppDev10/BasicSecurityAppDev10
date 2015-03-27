import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 11302014 on 19/03/2015.
 */
public class FEncryptor {
    Cipher cipherRSA = null;
    Cipher cipherAES = null;

    public FEncryptor() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipherRSA = Cipher.getInstance("RSA");
        cipherAES = Cipher.getInstance("AES");
    }

    public FTotalPacket encrypt(byte[] input, Key publicBkey, Key privateAkey) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException {

        //generate AESkey
        KeyGenerator aesKeyGen = KeyGenerator.getInstance("AES");
        Key aesKey = aesKeyGen.generateKey();

        //encrypt data
        FData encryptedData;
        encryptedData = new FData(encryptAES(input, aesKey));

        //data sha1
        byte[] hash;
        FHash signedHash;
        hash = sha1(input);
        signedHash = new FHash(encryptRSA(hash, privateAkey));

        //AESkey encrypte met rsa
        FKey encryptedKey;
        byte[] bytesOfKey = aesKey.getEncoded();
        encryptedKey = new FKey(encryptRSA(bytesOfKey, publicBkey));

        return new FTotalPacket(encryptedData,encryptedKey,signedHash);
    }

    private byte[] encryptRSA(byte[] input, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipherRSA.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedInput = cipherRSA.doFinal(input);

        return encryptedInput;
    }

    public byte[] encryptAES(byte[] input, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        cipherAES.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipherAES.doFinal(input);

        return encrypted;
    }

    public static byte[] sha1(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input);

        return result;
    }
}
