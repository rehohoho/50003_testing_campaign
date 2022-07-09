import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class CompareRecords {

    public static void main(String[] args) {
        Record record1 = new Record(args[0]);
        Record record2 = new Record(args[1]);
        
        HashSet<String> res = new HashSet<String>(record1.getEntries());
        res.removeAll(record2.getEntries());
        record2.getEntries().removeAll(record1.getEntries());
        res.addAll(record2.getEntries());
        
        writeResult(res, args[2]);
    }

    public static void writeResult(HashSet<String> res, String path) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            Iterator<String> it = res.iterator(); // why capital "M"?
            while(it.hasNext()) {
                out.write(it.next());
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
