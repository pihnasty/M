package experiment;

import io.gson.read.Reader;
import io.gson.write.Writer;
import org.junit.Test;

import java.io.FileNotFoundException;

public class PlanTest {

    @Test
    public void readFromGsonFileTest() throws FileNotFoundException {
        Plan plan = Plan.getInstance();
        System.out.println(Reader.readFromGsonFile("D:\\A\\M\\research\\src\\test\\java\\experiment", "experiment_plan.json", plan));
    }

    @Test
    public void saveToGsonFileTest() {
        Object planExperiment = Plan.getDefaultPlan();
        Writer.saveToGsonFile("D:\\A\\M\\research\\src\\test\\java\\experiment", "experiment_plan.json", planExperiment);
    }
}
