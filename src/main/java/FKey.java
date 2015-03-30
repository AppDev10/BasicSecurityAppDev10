/**
 * Created by 11302014 on 27/03/2015.
 */
public class FKey {
    private byte[] encryptedKey;

    public FKey(byte[] encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    public byte[] getEncryptedKey() {
        return encryptedKey;
    }
}
