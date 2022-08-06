import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FuzzInputs {

    static String OUT_PATH = "fuzzTests/";
    static String VALID_FILE_PREFIX = "valid";
    static String INVALID_FILE_PREFIX = "invalid";
    
    static AbstractFuzzer fuzzer;
    static int entryCount;
    static int outCount;

    interface EntryFunction {
        String run();
    }

    public static void main(String[] args) throws Exception {
        if (args[0].equals("random")) {
            fuzzer = new RandomFuzzer(0, 16, 0, 64);
        } else {
            throw new Exception("Unknown fuzzer type: " + args[0]);
        }

        entryCount = Integer.parseInt(args[1]);
        outCount = Integer.parseInt(args[2]);

        File directory = new File(OUT_PATH);
        if (!directory.exists()){
            directory.mkdir();
        }
        
        for (int i = 0; i < outCount; i++) {
            generateCsv(OUT_PATH + VALID_FILE_PREFIX + String.valueOf(i) + ".csv", 
                () -> fuzzer.generateValidEntry());
            generateCsv(OUT_PATH + INVALID_FILE_PREFIX + String.valueOf(i) + ".csv", 
                () -> fuzzer.generateInvalidEntry());
        }
    }
    
    public static void generateCsv(String path, EntryFunction getEntry) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            for (int i = 0; i < entryCount; i++) {
                out.write(getEntry.run());
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
