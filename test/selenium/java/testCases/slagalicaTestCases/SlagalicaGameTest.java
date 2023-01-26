package testCases.slagalicaTestCases;

import org.testng.annotations.Test;
import pages.SinglePlayerGamePage;

public class SlagalicaGameTest extends SlagalicaBaseTest {
    @Test(priority = 4)
    public void clickStopButton() {
        this.slagalicaPage.waitForStopButtonClickableDelay();
        this.slagalicaPage.verifyThatStopButtonIsClicked();
        waitForElToBeClickable(locators.getH1TitleLoc());
    }

    @Test(priority = 5)
    public void clickRandomLettersAfterStopTest() {
        this.slagalicaPage.clickRandomLetters();
    }

    @Test(priority = 6)
    public void verifyUserWordFieldTest() {
        this.slagalicaPage.verifyLettersAppearsInUserWordField();
    }

    @Test(priority = 7)
    public void verifyClickPotvrdiTest() {
        this.slagalicaPage.verifyThatPotvrdiButtonIsClicked();
    }

    @Test(priority = 8)
    public void verifyDialog() {
        this.slagalicaPage.verifyPopUpDialog();
    }

    @Test(priority = 9)
    public void verifyGeneratedWordTest() {
        this.slagalicaPage.verifyComputerGeneratedWord();
    }

    @Test(priority = 10)
    public void verifyCloseButtonClicked() {
        this.slagalicaPage.verifyMethods.verifyButtonIsClickable(this.slagalicaPage.closeButton);
        this.slagalicaPage.verifyCloseButtonIsClicked();
    }

    @Test(priority = 11)
    public void verifyDialogClosed() {
        takeSnapShot("Slagalica\\verifyDialogClosed", prop.getProperty("snapShotExtension"));
        this.slagalicaPage.waitForPopupToClose();
    }

    @Test(priority = 12)
    public void waitBeforeGoBackTest() {
        this.slagalicaPage.waitBeforeGoingBack();
    }

    @Test(priority = 25)
    public void goBackToSinglePlayerGamePage() {
        this.singlePlayerGamePage = new SinglePlayerGamePage();
        this.singlePlayerGamePage = this.slagalicaPage.goBackAfterGameFinished();
        this.singlePlayerGamePage.verifyBeforeGoingBack();
    }
}
