import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;


/**
 * Created by 11302014 on 4/05/2015.
 */
public class FServer implements Runnable {

    private Key myPrivateKey = null;
    private Key myPublicKey = null;
    FDecryptor fDecryptor = null;

    public FServer(Key myPrivateKey, Key myPublicKey) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.myPrivateKey = myPrivateKey;
        this.myPublicKey = myPublicKey;
        this.fDecryptor = new FDecryptor();
    }

    public void start() {
        Thread buf = new Thread(this);
        buf.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket bigSocket = new ServerSocket(FClient.port);

            while (true) {
                Socket smallSocket = bigSocket.accept();

                OutputStream output = smallSocket.getOutputStream();
                InputStream input = smallSocket.getInputStream();

                Key otherPublicKey = null;

                byte[] buffer = new byte[1024*1024];
                int bytesReceived = 0;

                //versturen van public key
                System.out.println("Server: start versturen publickey..." + myPublicKey.getEncoded().length);
                output.write(myPublicKey.getEncoded());
                output.flush();

                //ontvangen van public key
                System.out.println("Server: start ontvangen publickey...");
                while((bytesReceived = input.read(buffer))>0) {
                    KeyFactory kf = KeyFactory.getInstance("RSA");
                    otherPublicKey = kf.generatePublic(new X509EncodedKeySpec(buffer));
                    System.out.println(bytesReceived);
                    break;
                }

                FileOutputStream out = new FileOutputStream("buf.enc");

                //ontvangen van file
                System.out.println("Server: start ontvangen file...");

                while((bytesReceived = input.read(buffer))>0) {
                    out.write(buffer,0,bytesReceived);
                    System.out.println(bytesReceived);
                    break;
                }

                out.flush();
                out.close();
                input.close();

                smallSocket.close();
                //buf file lezen
                System.out.println("Server: file lezen...");
                FTotalPacket received = FTotalPacket.readEncBuf();

                //file decrypteren
                System.out.println("Server: file decrypteren...");
                byte[] unencryptedFile = fDecryptor.decrypt(received, myPrivateKey, otherPublicKey);

                //file weg schrijven
                System.out.println("Server: gedecrypte file wegschrijven");
                FTotalPacket.writeFile(unencryptedFile,received.getName());

                System.out.println("Server: Done!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
