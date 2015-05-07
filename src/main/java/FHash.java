import java.io.Serializable;

/**
 * Created by 11302014 on 27/03/2015.
 */
public class FHash implements Serializable {
    private byte[] encryptedHash;

    public FHash(byte[] encryptedHash) {
        this.encryptedHash = encryptedHash;
    }

    public byte[] getEncryptedHash() {
        return encryptedHash;
    }
}
