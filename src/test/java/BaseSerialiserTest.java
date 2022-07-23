import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BaseSerialiserTest {
    protected String inputString;

    public BaseSerialiserTest(String testString) {
        this.inputString = testString;
    }

    @Test
    public void testSerialiseType() {
        assertTrue(Serialiser.getHash(inputString) instanceof String);
    }

    @Test
    public void testDeserialiseType() {
        String hashString = Serialiser.getHash(inputString);
        assertTrue(Serialiser.getString(hashString) instanceof String);
    }

    @Test
    public void testSerialiseCharacters() {
        assertEquals(Serialiser.getString(Serialiser.getHash(inputString)), inputString);
    }
}