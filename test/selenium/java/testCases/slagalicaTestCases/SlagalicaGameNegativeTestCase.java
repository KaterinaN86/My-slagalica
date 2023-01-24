package testCases.slagalicaTestCases;

import org.testng.annotations.Test;

public class SlagalicaGameNegativeTestCase extends SlagalicaBaseTest {

    @Test(priority = 4)
    public void clickLettersFromFirstListTest() {
        this.slagalicaPage.clickLettersBeforeStop(this.slagalicaPage.firstListLocator);
    }
    @Test(priority = 5)
    public void clickLettersFromSecondListTest() {
        this.slagalicaPage.clickLettersBeforeStop(this.slagalicaPage.secondListLocator);
    }
    @Test(priority = 6)
    public void verifyUserWordElementNotEmptyTest() {
        this.slagalicaPage.verifyUserWordFieldNotEmpty();
    }
    @Test (priority =7)
    public void verifyTimerNotStartedTest(){
        this.slagalicaPage.verifyTimerNotStarted();
    }

}
