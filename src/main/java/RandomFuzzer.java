import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;

public class RandomFuzzer {
    private Random random = new Random();

    final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()`-=[]\\;\',./~_+{}|:\"<>?";

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
}
