import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;

public class RandomFuzzer extends AbstractFuzzer {
    private int cellMinLen;
    private int cellMaxLen;
    private int invalidMinLen;
    private int invalidMaxLen;
    private Random random = new Random();

    final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()`-=[]\\;\',./~_+{}|:\"<>?";

    RandomFuzzer(
        int cellMinLen, 
        int cellMaxLen, 
        int invalidMinLen, 
        int invalidMaxLen
    ) {
        this.cellMinLen = cellMinLen;
        this.cellMaxLen = cellMaxLen;
        this.invalidMinLen = invalidMinLen;
        this.invalidMaxLen = invalidMaxLen;
    }

    private int getRandomInteger(int minLength, int maxLength) {
        return random.nextInt(maxLength + 1 - minLength) + minLength;
    }

    private String getRandomString(int minLength, int maxLength) {
        int length = getRandomInteger(minLength, maxLength);
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        return sb.toString();
    }

    private String getRandomUtf8String(int minLength, int maxLength) {
        int length = getRandomInteger(minLength, maxLength);
        byte[] array = new byte[length];
        random.nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    private String getRandomElementInHashSet(HashSet<String> hs) {
        int size = hs.size();
        int item = random.nextInt(size);
        int i = 0;
        for(String obj : hs)
        {
            if (i == item)
                return obj;
            i++;
        }
        return "";
    }

    public String generateValidEntry() {
        String id =  "ID" + getRandomString(cellMinLen, cellMaxLen);
        String accNo =  "BOS" + getRandomString(cellMinLen, cellMaxLen);
        String currency = getRandomElementInHashSet(Serialiser.accountCurrency);
        String accType = getRandomElementInHashSet(Serialiser.accountTypes);
        String value = String.valueOf(random.nextInt(Integer.MAX_VALUE));
        return id + "," + accNo + "," + currency + "," + accType + "," + value;
    }

    public String generateInvalidEntry() {
        int validFieldCount = random.nextInt(2);
        if (validFieldCount == 1) {
            String entry = getRandomString(cellMinLen, cellMaxLen);
            for (int i = 0; i < Serialiser.expectedFieldCount - 1; i++) {
                entry += "," + getRandomString(cellMinLen, cellMaxLen);
            }
            return entry;
        } else {
            return getRandomString(invalidMinLen, invalidMaxLen);
        }
    }
}
