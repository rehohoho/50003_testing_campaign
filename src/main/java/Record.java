
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

/**
 * Reads csv and tracks entries
 */
public class Record {

    private HashSet<String> entries = new HashSet<String>();

    Record(String path) throws FileNotFoundException, IOException {
        readRecords(path);
    }

    private void readRecords(String path) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        
        String formatting = Files.probeContentType(Paths.get(path));
        if (formatting == null || !formatting.equals("application/vnd.ms-excel")) {
            br.close();
            throw new IOException("unexpected file formatting: " + formatting);
        }

        String line;
        while ((line = br.readLine()) != null) {
            line = line.replace("\"", "");
            entries.add(Serialiser.getHash(line));
        }
        br.close();
    }

    public HashSet<String> getEntries() {
        return entries;
    }
    
}
