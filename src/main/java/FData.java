/**
 * Created by 11302014 on 27/03/2015.
 */
public class FData {

    private byte[] encryptedData;

    public FData(byte[] data) {
        this.encryptedData = data;
    }

    public byte[] getEncryptedData() {
        return encryptedData;
    }
}
