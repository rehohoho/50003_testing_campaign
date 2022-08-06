import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(
            SerialiserTest.class,
            SerialiserFieldsTest.class,
            RecordTest.class
        );

        for (Failure failure: result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("Finished running unit tests in " + result.getRunTime() + "ms \n" + 
            result.getRunCount() + " ran \n" +
            result.getIgnoreCount() + " ignored \n" +
            result.getFailureCount() + " failed");
        
        result = JUnitCore.runClasses(
            CompareRecordsTest.class,
            FuzzerTest.class
        );
        
        for (Failure failure: result.getFailures()) {
            System.out.println(failure.toString());
        }
        
        System.out.println("Finished running system tests in " + result.getRunTime() + "ms \n" + 
            result.getRunCount() + " ran \n" +
            result.getIgnoreCount() + " ignored \n" +
            result.getFailureCount() + " failed");

    }
    
}
