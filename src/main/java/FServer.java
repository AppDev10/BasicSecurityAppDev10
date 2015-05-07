import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 11302014 on 4/05/2015.
 */
public class FServer implements Runnable {

    private Key myPrivateKey = null;
    FDecryptor fDecryptor = null;

    public FServer(Key myPrivateKey) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.myPrivateKey = myPrivateKey;
        this.fDecryptor = new FDecryptor();
    }

    @Override
    public void run() {
        try {
            ServerSocket bigSocket = new ServerSocket(FClient.port);

            while (true) {
                Socket smallSocket = bigSocket.accept();

                InputStream input = smallSocket.getInputStream();

                FileOutputStream out = new FileOutputStream("buf.enc");

                byte[] buffer = new byte[1024*1024];

                int bytesReceived = 0;

                while((bytesReceived = input.read(buffer))>0) {
                    out.write(buffer,0,bytesReceived);
                    System.out.println(bytesReceived);
                    break;
                }

                out.flush();
                out.close();

                FTotalPacket received = FTotalPacket.readEncBuf();
                byte[] unencryptedFile = fDecryptor.decrypt(received, myPrivateKey, null);




                input.close();
                smallSocket.close();
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
        }
    }
}
