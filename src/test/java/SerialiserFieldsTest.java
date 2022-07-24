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

    @RunWith(Parameterized.class)
    public static class SerialiserInvalidIdTest {
        protected String inputString;

        public SerialiserInvalidIdTest(String testString) {
            this.inputString = testString + ",BOS,USD,CURRENT,0";
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"a"}, {"Id"}, {"iD"}, {"id"}, {"0"}, {" "}
            });
        }

        @Test
        public void testInvalidId() throws Serialiser.SerialiserException {
            assertThrows(Serialiser.ValueException.class, () -> {
                Serialiser.getHash(inputString);
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

}
