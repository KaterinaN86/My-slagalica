package testCases.slagalicaTestCases;

import org.testng.annotations.Test;
import pages.SinglePlayerGamePage;
import pages.SlagalicaPage;

public class SlagalicaLongestWordTest extends SlagalicaBaseTest {

    @Test(priority = 4)
    public void clickStopButtonTest() {
        this.slagalicaPage.verifyThatStopButtonIsClicked();
    }

    @Test(priority = 5)
    public void verifyClickPotvrdiTest() {
        this.slagalicaPage.verifyThatPotvrdiButtonIsClicked();
    }
    @Test(priority = 6)
    public void verifyDialog() {
        this.slagalicaPage.verifyPopUpDialog();
    }

    @Test(priority = 7)
    public void verifyGeneratedWordTest() {
        this.slagalicaPage.getExpectedLongestWord();
        this.slagalicaPage.verifyComputerGeneratedWord();
    }

    @Test(priority = 9)
    public void verifyCloseButtonClicked() {
        this.slagalicaPage.verifyMethods.verifyButtonIsClickable(this.slagalicaPage.closeButton);
        this.slagalicaPage.verifyCloseButtonIsClicked();
    }

    @Test(priority = 10)
    public void verifyDialogClosed() {
        takeSnapShot(this.getClass().getSimpleName()+"\\verifyDialogClosed", prop.getProperty("snapShotExtension"));
        this.slagalicaPage.waitForPopupToClose();
    }
    @Test(priority = 11)
    public void waitBeforeGoBackTest() {
        this.slagalicaPage.waitBeforeGoingBack();
    }

    @Test(priority = 25)
    public void goBackToSinglePlayerGamePage() {
        this.singlePlayerGamePage=new SinglePlayerGamePage();
        this.singlePlayerGamePage = this.slagalicaPage.goBackAfterGameFinished();
        this.singlePlayerGamePage.verifyBeforeGoingBack();
        this.singlePlayerGamePage.verifyTotalPoints(this.slagalicaPage);
    }

}
