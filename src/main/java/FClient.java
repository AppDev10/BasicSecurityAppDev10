import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by 11302014 on 4/05/2015.
 */
public class FClient implements Runnable {

    private String ip = "127.0.0.1";
    public static final int port = 11111;

    public FClient(String ip, FTotalPacket toSend) {
        super();

        this.ip = ip;
    }

    @Override
    public void run() {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ip);
            Socket connection = new Socket(address, port);

            OutputStream output = connection.getOutputStream();

            FileInputStream fileInputStream = new FileInputStream("buf.enc");
            byte[] buffer = new byte[1024*1024];
            int bytesRead = 0;

            while((bytesRead = fileInputStream.read(buffer))>0)
            {
                output.write(buffer,0,bytesRead);
            }

            fileInputStream.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
