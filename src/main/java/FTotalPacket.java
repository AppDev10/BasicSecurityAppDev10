import java.io.*;

/**
 * Created by 11302014 on 27/03/2015.
 */
public class FTotalPacket implements Serializable {
    private FData encryptedData;
    private FKey encryptedKey;
    private FHash encryptedHash;

    private String name = "test";

    public FTotalPacket(FData encryptedData, FKey encryptedKey, FHash encryptedHash) {
        this.encryptedData = encryptedData;
        this.encryptedKey = encryptedKey;
        this.encryptedHash = encryptedHash;
    }

    public static void writeEncBuf(FTotalPacket toWrite) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("buf.enc"));
        out.writeObject(toWrite);
        out.flush();
        out.close();
    }

<<<<<<< HEAD
    public static void writeFile(byte[] toWrite, String name) throws IOException {
        FileOutputStream out = new FileOutputStream(name);
        out.write(toWrite);
        out.flush();
        out.close();
    }

=======
>>>>>>> origin/master
    public static FTotalPacket readEncBuf() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("buf.enc"));
        FTotalPacket readed = (FTotalPacket) in.readObject();
        in.close();

        return readed;
    }

    public FData getEncryptedData() {
        return encryptedData;
    }

    public FKey getEncryptedKey() {
        return encryptedKey;
    }

    public FHash getEncryptedHash() {
        return encryptedHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
