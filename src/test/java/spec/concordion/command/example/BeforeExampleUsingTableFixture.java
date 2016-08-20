package spec.concordion.command.example;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

@RunWith(ConcordionRunner.class)
public class BeforeExampleUsingTableFixture extends SpecWithBeforeSpec {
    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public void incrementCounterBy(int num) {
        counter += num;
    }
}
