import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class FuzzerTest {
    private String[] args;

    public FuzzerTest(String[] args) {
        this.args = args;
    }
    
    @Parameterized.Parameters
    public static Collection<Object[]> generateInput() {
        File dir = new File(FuzzInputs.OUT_PATH);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".csv");
            }
        });
        
        List<Object[]> params = new ArrayList<>();
        for (File file : files) {
            params.add(new Object[]{
                new String[] {file.getAbsolutePath(), file.getAbsolutePath(), "res.csv"}
            });
        }
        return params;
    }

    @Test
    public void testMain() 
        throws FileNotFoundException, IOException, Serialiser.SerialiserException
    {
        final PrintStream sout = System.out;
        final PrintStream fout = new PrintStream(
            new BufferedOutputStream(new FileOutputStream(args[0] + ".log")), true);
        System.setOut(fout);
        CompareRecords.main(args);
        System.setOut(sout);
    }
}
