import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Serialiser {

    static final String DELIM = ",";
    static final int expectedFieldCount = 5;

    static final HashSet<String> accountCurrency = Stream.of(
        "AUD", "CAD", "CHF", "EUR", "GBP", "HKD", "INR", "JPY", "MXN", "NOK", "NZD", 
        "SEK", "SGD", "TRY", "USD", "XAG", "XAU", "XBN", "XBT", "XET", "XLC", "XNG", 
        "XPD", "XPT", "XRP", "XTI"
    ).collect(Collectors.toCollection(HashSet::new));

    static final HashSet<String> accountTypes = Stream.of(
        "SAVINGS", "CURRENT"
    ).collect(Collectors.toCollection(HashSet::new));

    public static class SerialiserException extends Exception {
        public SerialiserException(String errorMessage) { super(errorMessage); }
    }

    public static class FieldCountException extends SerialiserException {
        public FieldCountException(String errorMessage) { super(errorMessage); }
    }

    public static class ValueException extends SerialiserException {
        public ValueException(String errorMessage) { super(errorMessage); }
    }

    private static void checkCustomerId(String customerId) throws ValueException {
        if (customerId.length() < 2) {
            throw new ValueException("Length of customer id too short: " + customerId);
        }
        if (!customerId.substring(0, 2).equals("ID")) {
            throw new ValueException("Invalid format of customer id: " + customerId);
        }
    }

    private static void checkAccountNo(String customerId) throws ValueException {
        if (customerId.length() < 3) {
            throw new ValueException("Length of account no too short: " + customerId);
        }
        if (!customerId.substring(0, 3).equals("BOS")) {
            throw new ValueException("Invalid format of account no: " + customerId);
        }
    }

    private static void checkCurrency(String currency) throws ValueException {
        if (!accountCurrency.contains(currency)) {
            throw new ValueException("Unknown currency: " + currency);
        }
    }

    private static void checkAccountType(String accountType) throws ValueException {
        if (!accountTypes.contains(accountType)) {
            throw new ValueException("Unknown account type: " + accountType);
        }
    }

    private static void checkBalance(String balance) throws ValueException {
        //match a number with optional '-' and decimal.
        if (!balance.matches("\\d+(\\.\\d+)?")) {
            throw new ValueException("Unknown balance: " + balance);
        }
    }
    
    /**
     * 
     * @return hashed representation
     */
    public static String getHash(String line) throws SerialiserException {
        String[] data = line.split(DELIM);
        if (data.length != expectedFieldCount) {
            throw new FieldCountException("Got " + data.length + " fields, expected 5");
        }
        checkCustomerId(data[0]);
        checkAccountNo(data[1]);
        checkCurrency(data[2]);
        checkAccountType(data[3]);
        checkBalance(data[4]);
        return line;
    }

    /**
     * 
     * @return readable representation
     */
    public static String getString(String hashed) {
        return hashed;
    }
}
