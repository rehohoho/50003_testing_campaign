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
        public static Collection generateInput() {
            return Arrays.asList(new Object[][] {
                {"asdf"}
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class SerialiserNumericsTest extends BaseSerialiserTest {

        public SerialiserNumericsTest(String testString) {
            super(testString);
        }
        
        @Parameterized.Parameters
        public static Collection generateInput() {
            return Arrays.asList(new Object[][] {
                {"1234"}
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class SerialiserSymbolsTest extends BaseSerialiserTest {

        public SerialiserSymbolsTest(String testString) {
            super(testString);
        }
        
        @Parameterized.Parameters
        public static Collection generateInput() {
            return Arrays.asList(new Object[][] {
                {"!@#$%^&*()"}
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class SerialiserEmptyTest extends BaseSerialiserTest {

        public SerialiserEmptyTest(String testString) {
            super(testString);
        }
        
        @Parameterized.Parameters
        public static Collection generateInput() {
            return Arrays.asList(new Object[][] {
                {""}
            });
        }
    }
    
}
