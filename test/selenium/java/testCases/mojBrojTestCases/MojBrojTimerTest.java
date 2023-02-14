package testCases.mojBrojTestCases;

import org.testng.annotations.Test;

public class MojBrojTimerTest extends MojBrojBaseTest {
    public MojBrojTimerTest() {
        super();
    }

    @Test(priority = 8)
    public void timerTest() {
        this.mojBrojPage.setTimerToZero(prop.getProperty("mojBrojBadExpMsg"), prop.getProperty("mojBrojTimerScript"));
    }
}
