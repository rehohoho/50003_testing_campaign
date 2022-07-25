
import static org.junit.Assert.assertTrue;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Enclosed.class)
public class CompareRecordsTest {

    private static void compareFile(String path1, String path2)  throws IOException {
        try (BufferedReader bf1 = Files.newBufferedReader(Path.of(path1));
            BufferedReader bf2 = Files.newBufferedReader(Path.of(path2))) {

            String line1;
            String line2;
            while ((line1 = bf1.readLine()) != null)
            {
                line2 = bf2.readLine();
                assertTrue(line1.equals(line2) || !line1.contains(path1));
            }
        }
    }

    @RunWith(Parameterized.class)
    public static class SystemTest {
        private String[] args;
        private String outPath;
        private String expectedOutPath;

        public SystemTest(String[] args, String outPath, String expectedOutPath) {
            this.args = args;
            this.outPath = outPath;
            this.expectedOutPath = expectedOutPath;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> generateInput() {
            return Arrays.asList(new Object[][] {
                {new String[]{"./assets/sample_file_4.csv", "./assets/sample_file_5.csv", "./assets/systemsTestOut1.csv"}, "./assets/systemsTestOut1", "./assets/systemsTest1"},
                {new String[]{"./assets/sample_file_1.csv", "./assets/sample_file_2.csv", "./assets/systemsTestOut2.csv"}, "./assets/systemsTestOut2", "./assets/systemsTest2"},
                {new String[]{"./assets/sample_file_1.csv", "./assets/sample_file_3.csv", "./assets/systemsTestOut3.csv"}, "./assets/systemsTestOut3", "./assets/systemsTest3"},
                {new String[]{"./assets/sample_file_2.csv", "./assets/sample_file_3.csv", "./assets/systemsTestOut4.csv"}, "./assets/systemsTestOut4", "./assets/systemsTest4"}
            });
        }
        
        @Test
        public void testMain() 
            throws FileNotFoundException, IOException, Serialiser.SerialiserException
        {
            final PrintStream sout = System.out;
            final PrintStream fout = new PrintStream(
                new BufferedOutputStream(new FileOutputStream(outPath + ".txt")), true);
            System.setOut(fout);

            CompareRecords.main(args);
            System.setOut(sout);

            compareFile(outPath + ".txt", expectedOutPath + ".txt");
            compareFile(outPath + ".csv", expectedOutPath + ".csv");
            File outTxt = new File(outPath + ".txt");
            File outCsv = new File(outPath + ".csv");
            outTxt.delete();
            outCsv.delete();
        }
    }

}
