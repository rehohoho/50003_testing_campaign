import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class BaseSerialiserTest {
    protected String inputString;

    public BaseSerialiserTest(String testString) {
        this.inputString = "ID" + testString + ",BOS,USD,CURRENT,0";
    }

    @Test
    public void testSerialiseType() throws Serialiser.SerialiserException {
        assertTrue(Serialiser.getHash(inputString) instanceof String);
    }

    @Test
    public void testDeserialiseType() throws Serialiser.SerialiserException {
        String hashString = Serialiser.getHash(inputString);
        assertTrue(Serialiser.getString(hashString) instanceof String);
    }

    @Test
    public void testSerialiseCharacters() throws Serialiser.SerialiserException {
        assertEquals(Serialiser.getString(Serialiser.getHash(inputString)), inputString);
    }
}