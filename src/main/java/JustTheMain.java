import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by 11302014 on 19/03/2015.
 */
public class JustTheMain extends Application {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, NoSuchProviderException, NoSuchPaddingException {
        launch(args);



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

    }

    public static String printProvider(Provider provider) {
        return "name: " + provider.getName() + " version: " + provider.getVersion() + " info: " + provider.getInfo();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root);


        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());



        primaryStage.setTitle("TWO - THINK");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
