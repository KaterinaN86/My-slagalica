package testCases.slagalicaTestCases;

import org.testng.annotations.Test;

public class SlagalicaGameNegativeTestCase extends SlagalicaBaseTest {

    @Test(priority = 4)
    public void clickLettersFromFirstListTest() {
        this.slagalicaPage.waitForStopButtonClickableDelay();
        this.slagalicaPage.clickRandomLetters();
    }
    @Test(priority = 5)
    public void verifyUserWordElementIsEmptyTest() {
        this.slagalicaPage.verifyUserWordFieldIsEmpty();
    }
    @Test (priority =6)
    public void verifyTimerNotStartedTest(){
        this.slagalicaPage.verifyTimerNotStarted();
    }

}
