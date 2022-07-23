
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Enclosed.class)
public class RecordTest {

    @RunWith(Parameterized.class)
    public static class ValidPathTest {
        private String recordPath;

        public ValidPathTest(String recordPath) {
            this.recordPath = recordPath;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"./assets/sample_file_1.csv"}, 
                {"./assets/sample_file_2.csv"}, 
                {"./assets/sample_file_3.csv"},
            });
        }
        
        @Test
        public void testRecordConstructor() throws FileNotFoundException, IOException {
            Record record = new Record(recordPath);
            assertEquals(record.getEntries().size(), 1001);
            System.out.println(Files.probeContentType(Paths.get(recordPath)));
        }
    }

    @RunWith(Parameterized.class)
    public static class InvalidPathTest {
        private String recordPath;

        public InvalidPathTest(String recordPath) {
            this.recordPath = recordPath;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {"./assets/sample_file_1.txt"}, 
                {""}, 
            });
        }
        
        @Test
        public void testRecordConstructor() {
            assertThrows(FileNotFoundException.class, () -> {
                new Record(recordPath);
            });
        }
    }

}
