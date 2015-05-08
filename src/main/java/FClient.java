import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by 11302014 on 4/05/2015.
 */
public class FClient implements Runnable {

    private Key myPrivateKey = null;
    private Key myPublicKey = null;
    private String ip = "127.0.0.1";
    public static final int port = 11111;

    private byte[] file = null;
    private String fileName = "test";

    public FClient(String ip, byte[] toSend, String fileName,Key myPrivateKey, Key myPublicKey) {
        super();
        this.myPrivateKey = myPrivateKey;
        this.myPublicKey = myPublicKey;

        this.file = toSend;
        this.fileName = fileName;

        this.ip = ip;
    }

    public void start() {
        Thread buf = new Thread(this);
        buf.start();
    }

    @Override
    public void run() {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ip);
            Socket connection = new Socket(address, port);

            OutputStream output = connection.getOutputStream();
            InputStream input = connection.getInputStream();

            byte[] buffer = new byte[1024*1024];
            int bytesRead = 0;
            int bytesReceived = 0;
            
            Key otherPublicKey = null;

            //ontvangen public key
            System.out.println("Client: ontvangen publickey...");
            while((bytesReceived = input.read(buffer))>0) {
                KeyFactory kf = KeyFactory.getInstance("RSA");
                otherPublicKey = kf.generatePublic(new X509EncodedKeySpec(buffer));
                System.out.println(bytesReceived);
                break;
            }

            //bytes encryptere
            System.out.println("Client: encrypten bytes...");
            FEncryptor fEncryptor = new FEncryptor();
            FTotalPacket toSend = fEncryptor.encrypt(file, otherPublicKey, myPrivateKey);
            toSend.setName(fileName);

            //wegschrijven van buf file
            System.out.println("Client: wegschrijven in buf...");
            FTotalPacket.writeEncBuf(toSend);

            //versturen public key
            System.out.println("Client: versturen publickey...");
            output.write(myPublicKey.getEncoded());
            output.flush();

            FileInputStream fileInputStream = new FileInputStream("buf.enc");

            //versturen van file
            System.out.println("Client: versturen file...");
            while((bytesRead = fileInputStream.read(buffer))>0)
            {
                output.write(buffer,0,bytesRead);
            }
            output.flush();

            output.close();
            input.close();
            connection.close();

            System.out.println("Client: done!");

            fileInputStream.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }
}
