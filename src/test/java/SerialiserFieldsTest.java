import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;


@RunWith(Enclosed.class)
public class SerialiserFieldsTest {
    
    @RunWith(Parameterized.class)
    public static class SerialiserFailFieldsTest {
        protected String inputString;

        public SerialiserFailFieldsTest(String testString) {
            this.inputString = testString;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"a,b,c,d"}, {",,,,"}, {"a,a,a,a,a,a"}
            });
        }

        @Test
        public void testWrongFieldCount() throws Serialiser.SerialiserException {
            assertThrows(Serialiser.FieldCountException.class, () -> {
                Serialiser.getHash(inputString);
            });
        }
    }

    public static class SerialiserIdFieldTest extends BaseSerialiserFieldsTest {

        public SerialiserIdFieldTest(Type type, String testString) {
            super(type, testString + ",BOS,USD,CURRENT,0");
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {Type.INVALID, "a"},
                {Type.INVALID, "Id"},
                {Type.INVALID, "iD"},
                {Type.INVALID, "id"},
                {Type.INVALID, "0"},
                {Type.INVALID, " "},
                // empty field is catched by FieldCountException
                
                {Type.VALID, "ID"},
                {Type.VALID, "ID0987654321"},
                {Type.VALID, "ID0"},
                {Type.VALID, "IDabc"},
                {Type.VALID, "IDIDID"},
            });
        }
    }

    public static class SerialiserAccountNoFieldTest extends BaseSerialiserFieldsTest {

        public SerialiserAccountNoFieldTest(Type type, String testString) {
            super(type, "ID," + testString + ",USD,CURRENT,0");
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {Type.INVALID, "bos"},
                {Type.INVALID, "Bos"},
                {Type.INVALID, "BOs"},
                {Type.INVALID, "boS"},
                {Type.INVALID, "asdf"},
                {Type.INVALID, "123"},
                {Type.INVALID, " "},
                // empty field is catched by FieldCountException
                
                {Type.VALID, "BOS"},
                {Type.VALID, "BOS0"},
                {Type.VALID, "BOS1234567890"},
                {Type.VALID, "BOSasdf"},
                {Type.VALID, "BOSBOSBOS"},
            });
        }
    }

    public static class SerialiserCurrencyFieldTest extends BaseSerialiserFieldsTest {

        public SerialiserCurrencyFieldTest(Type type, String testString) {
            super(type, "ID,BOS," + testString + ",CURRENT,0");
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {Type.INVALID, "asdf"},
                {Type.INVALID, "USDSGD"},
                {Type.INVALID, "USDD"},
                {Type.INVALID, "usd"},
                {Type.INVALID, "sgd"},
                {Type.INVALID, "0"},
                {Type.INVALID, " "},
                // empty field is catched by FieldCountException

                {Type.VALID, "USD"},
                {Type.VALID, "TRY"},
                {Type.VALID, "EUR"},
                {Type.VALID, "CAD"},
            });
        }
    }

    public static class SerialiserAccountTypeFieldTest extends BaseSerialiserFieldsTest {

        public SerialiserAccountTypeFieldTest(Type type, String testString) {
            super(type, "ID,BOS,USD," + testString + ",0");
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {Type.INVALID, "asdf"},
                {Type.INVALID, "savings"},
                {Type.INVALID, "current"},
                {Type.INVALID, "SAVINGS "},
                {Type.INVALID, " SAVINGS"},
                {Type.INVALID, "0"},
                {Type.INVALID, " "},
                // empty field is catched by FieldCountException

                {Type.VALID, "CURRENT"},
                {Type.VALID, "SAVINGS"},
            });
        }
    }

    public static class SerialiserBalanceFieldTest extends BaseSerialiserFieldsTest {

        public SerialiserBalanceFieldTest(Type type, String testString) {
            super(type, "ID,BOS,USD,SAVINGS," + testString);
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {Type.INVALID, "asdf"},
                {Type.INVALID, "1234567890a"},
                {Type.INVALID, "-1"},
                {Type.INVALID, "-123456789"},
                {Type.INVALID, "-a123456789"},
                {Type.INVALID, " "},
                // empty field is catched by FieldCountException
                
                {Type.VALID, "0"},
                {Type.VALID, "0.1"},
                {Type.VALID, "0.001"},
                {Type.VALID, "1234567890"},
                {Type.VALID, "98765.43210"},
            });
        }
    }

}
