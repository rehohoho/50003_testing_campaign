public class Serialiser {

    static final String DELIM = ",";

    public enum AccountCurrency {
        
    }

    public enum AccountType {
        SAVINGS, CURRENT
    }
    
    /**
     * 
     * @return hashed representation
     */
    public static String getHash(String line) {
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
