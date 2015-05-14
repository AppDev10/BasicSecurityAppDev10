import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by 11302014 on 19/03/2015.
 */
public class JustTheMain extends Application {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, NoSuchProviderException, NoSuchPaddingException, IOException, ClassNotFoundException {
        //launch(args);
        /*Provider[] providers = Security.getProviders();

        for (Provider p : providers) {
            System.out.println(printProvider(p));
        }*/

        /*byte[] input = new String("waffels").getBytes();
        KeyPairGenerator kpgA = KeyPairGenerator.getInstance("RSA");
        kpgA.initialize(2048);
        KeyPair kpA = kpgA.genKeyPair();
        Key pubKeyA = kpA.getPublic();
        Key privKeyA = kpA.getPrivate();

        KeyPairGenerator kpgB = KeyPairGenerator.getInstance("RSA");
        kpgB.initialize(2048);
        KeyPair kpB = kpgB.genKeyPair();
        Key pubKeyB = kpB.getPublic();
        Key privKeyB = kpB.getPrivate();


        new FServer(privKeyB,pubKeyB).start();

        new FClient("127.0.0.1", input, "testje123.txt", privKeyA, pubKeyA).start();

        /*byte[] cipherText = (new FEncryptor()).encrypt(input, pubKey);
        System.out.println("cipherRSA: " + new String(cipherText));

        byte[] plainText = (new FDecryptor()).decrypt(cipherText, privKey);
        System.out.println("plain : " + new String(plainText));*/

        launch(args);
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
        primaryStage.setFullScreen(true);



        primaryStage.setTitle("TWO - THINK");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
