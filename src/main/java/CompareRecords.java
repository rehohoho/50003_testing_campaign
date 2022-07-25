import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class CompareRecords {

    public static void main(String[] args) 
        throws FileNotFoundException, IOException, Serialiser.SerialiserException
    {
        Record record1 = new Record(args[0]);
        Record record2 = new Record(args[1]);
        Object[] res = compare(record1, record2);
        
        if (args[2] != null) {
            writeResult(res, args[2]);
            System.out.println("Result csv outputted at " + args[2]);
        } else {
            System.out.println(res);
        }
    }

    public static void writeResult(Object[] res, String path) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            for (Object it: res) {
                out.write(it.toString());
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static Object[] compare(Record record1, Record record2) {
        HashSet<String> res = new HashSet<String>(record1.getEntries());
        res.removeAll(record2.getEntries());
        record2.getEntries().removeAll(record1.getEntries());
        res.addAll(record2.getEntries());
        
        Object[] resArr = res.toArray();
        Arrays.sort(resArr);
        return resArr;
    }

}
