import java.util.Arrays;
import java.util.Collection;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Enclosed.class)
public class SerialiserTest {

    @RunWith(Parameterized.class)
    public static class SerialiserStringTest extends BaseSerialiserTest {

        public SerialiserStringTest(String testString) {
            super(testString);
        }
        
        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"asdf"}, {"ASDF"}, {"asDF"}, {"a"}, {"B"},
                {"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"}
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class SerialiserNumericsTest extends BaseSerialiserTest {

        public SerialiserNumericsTest(String testString) {
            super(testString);
        }
        
        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"1234"}, {"0"}, {"0123456789"}, {"9876543210"}, {"0a"}, {"a0"}
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class SerialiserSymbolsTest extends BaseSerialiserTest {

        public SerialiserSymbolsTest(String testString) {
            super(testString);
        }
        
        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"!@#$%^&*()"}, {"\""}, {"\'"}, {"*"}, {"0!"}, {"!0"},
                {"a@"}, {"@a"}, {"#b9"}, {"b8$"}, {"7d$"}
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class SerialiserEmptyTest extends BaseSerialiserTest {

        public SerialiserEmptyTest(String testString) {
            super(testString);
        }
        
        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {""}, {"[]"}
            });
        }
    }
    
}
