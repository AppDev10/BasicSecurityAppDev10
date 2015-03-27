/**
 * Created by 11302014 on 27/03/2015.
 */
public class FTotalPacket {
    private FData encryptedData;
    private FKey encryptedKey;
    private FHash encryptedHash;

    public FTotalPacket(FData encryptedData, FKey encryptedKey, FHash encryptedHash) {
        this.encryptedData = encryptedData;
        this.encryptedKey = encryptedKey;
        this.encryptedHash = encryptedHash;
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
}
