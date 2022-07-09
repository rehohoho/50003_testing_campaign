
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * Reads csv and tracks entries
 */
public class Record {

    private HashSet<String> entries = new HashSet<String>();

    Record(String path) {
        readRecords(path);
    }

    private void readRecords(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("\"", "");
                entries.add(Serialiser.getHash(line));
            }
        } catch (FileNotFoundException e) {
            System.out.print(e);
        } catch (IOException e) {
            System.out.print(e);
        }
    }

    public HashSet<String> getEntries() {
        return entries;
    }
    
}
