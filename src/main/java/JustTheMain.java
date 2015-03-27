import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by 11302014 on 19/03/2015.
 */
public class JustTheMain {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, NoSuchProviderException, NoSuchPaddingException {
        Provider[] providers = Security.getProviders();

        for (Provider p : providers) {
            System.out.println(printProvider(p));
        }

        byte[] input = new String("waffels").getBytes();
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        Key pubKey = kp.getPublic();
        Key privKey = kp.getPrivate();

        KeyGenerator desKeyGen = KeyGenerator.getInstance("AES");
        Key desKey = desKeyGen.generateKey();

        /*byte[] cipherText = (new FEncryptor()).encrypt(input, pubKey);
        System.out.println("cipherRSA: " + new String(cipherText));

        byte[] plainText = (new FDecryptor()).decrypt(cipherText, privKey);
        System.out.println("plain : " + new String(plainText));*/

        byte[] cipherText = (new FEncryptor()).encryptDES(input, desKey);
        System.out.println("cipherRSA: " + new String(cipherText));

        byte[] plainText = (new FDecryptor()).decryptDES(cipherText, desKey);
        System.out.println("plain : " + new String(plainText));

        System.out.println( new String((new FEncryptor()).sha1(plainText)) );

    }

    public static String printProvider(Provider provider) {
        return "name: " + provider.getName() + " version: " + provider.getVersion() + " info: " + provider.getInfo();
    }
}
