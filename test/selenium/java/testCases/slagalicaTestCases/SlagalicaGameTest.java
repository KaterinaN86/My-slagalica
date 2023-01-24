package testCases.slagalicaTestCases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.SinglePlayerGamePage;

import java.io.IOException;

public class SlagalicaGameTest extends SlagalicaBaseTest {

    @Test(priority = 4)
    public void clickStopButton() {
        this.slagalicaPage.verifyThatStopButtonIsClicked();
        waitForElToBeClickable(locators.getH1TitleLoc());
    }
    @Test(priority = 5)
    public void printWordsTest() {
        this.slagalicaPage.printWords();
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getBackBtnLoc()));
    }
}
