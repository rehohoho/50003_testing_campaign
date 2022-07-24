public class Serialiser {

    static final String DELIM = ",";

    public enum AccountCurrency {
        
    }

    public enum AccountType {
        SAVINGS, CURRENT
    }

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
    
    /**
     * 
     * @return hashed representation
     */
    public static String getHash(String line) throws SerialiserException {
        String[] data = line.split(DELIM);
        if (data.length != 5) {
            throw new FieldCountException("Got " + data.length + " fields, expected 5");
        }
        checkCustomerId(data[0]);
        checkAccountNo(data[1]);
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
