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

    public static class SerialiserInvalidIdTest extends BaseSerialiserFieldsTest {

        public SerialiserInvalidIdTest(Type type, String testString) {
            super(type, testString);
            this.inputString = testString + ",BOS,USD,CURRENT,0";
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
                
                {Type.VALID, "ID"},
                {Type.VALID, "ID0987654321"},
                {Type.VALID, "ID0"},
                {Type.VALID, "IDabc"},
                {Type.VALID, "IDIDID"},
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class SerialiserInvalidAccountNoTest {
        protected String inputString;

        public SerialiserInvalidAccountNoTest(String testString) {
            this.inputString = "ID," + testString + ",USD,CURRENT,0";
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"bos"}, {"Bos"}, {"BOs"}, {"asdf"}, {"123"}, {" "}, {""}
            });
        }

        @Test
        public void testInvalidAccountNo() throws Serialiser.SerialiserException {
            assertThrows(Serialiser.ValueException.class, () -> {
                Serialiser.getHash(inputString);
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class SerialiserInvalidCurrencyTest {
        protected String inputString;

        public SerialiserInvalidCurrencyTest(String testString) {
            this.inputString = "ID,BOS," + testString + ",CURRENT,0";
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"asdf"}, {"USDSGD"}, {"USDD"}, {"usd"}, {"sgd"}, {"0"}, {" "}
            });
        }

        @Test
        public void testInvalidCurrency() throws Serialiser.SerialiserException {
            assertThrows(Serialiser.ValueException.class, () -> {
                Serialiser.getHash(inputString);
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class SerialiserInvalidAccountTypeTest {
        protected String inputString;

        public SerialiserInvalidAccountTypeTest(String testString) {
            this.inputString = "ID,BOS,USD," + testString + ",0";
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"asdf"}, {"savings"}, {"current"}, {"SAVINGS "}, {" SAVINGS"}, 
                {"0"}, {" "}
            });
        }

        @Test
        public void testInvalidAccountType() throws Serialiser.SerialiserException {
            assertThrows(Serialiser.ValueException.class, () -> {
                Serialiser.getHash(inputString);
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class SerialiserInvalidBalanceTest {
        protected String inputString;

        public SerialiserInvalidBalanceTest(String testString) {
            this.inputString = "ID,BOS,USD,SAVINGS," + testString;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"asdf"}, {"1234567890a"}, {"-1"}, {" "}
            });
        }

        @Test
        public void testInvalidAccountType() throws Serialiser.SerialiserException {
            assertThrows(Serialiser.ValueException.class, () -> {
                Serialiser.getHash(inputString);
            });
        }
    }

}
