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
    
    /**
     * 
     * @return hashed representation
     */
    public static String getHash(String line) throws SerialiserException {
        String[] data = line.split(DELIM);
        if (data.length != 5) {
            throw new FieldCountException(
                "Got " + data.length + " fields, expected 5");
        }
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
