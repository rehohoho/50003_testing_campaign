import static org.junit.Assert.assertThrows;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BaseSerialiserFieldsTest {
    
    enum Type {VALID, INVALID};
    protected String inputString;
    protected Type type;

    public BaseSerialiserFieldsTest(Type type, String testString) {
        this.type = type;
        this.inputString = testString;
    }
    
    @Test
    public void testValidField() throws Serialiser.SerialiserException {
        Assume.assumeTrue(type == Type.VALID);
        // no exception thrown
        Serialiser.getHash(inputString);
    }

    @Test
    public void testInvalidField() throws Serialiser.SerialiserException {
        Assume.assumeTrue(type == Type.INVALID);
        assertThrows(Serialiser.ValueException.class, () -> {
            Serialiser.getHash(inputString);
        });
    }
    
}