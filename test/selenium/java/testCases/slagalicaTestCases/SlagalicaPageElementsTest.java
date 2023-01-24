package testCases.slagalicaTestCases;

import org.testng.annotations.Test;

public class SlagalicaPageElementsTest extends SlagalicaBaseTest {

    public SlagalicaPageElementsTest() {
        super();
    }

    @Test(priority = 4)
    public void verifyFirstList() {
        this.slagalicaPage.verifyThatFirstListIsDisplayed();
    }

    @Test(priority = 5)
    public void verifySecondList() {
        this.slagalicaPage.verifyThatSecondListIsDisplayed();
    }

    @Test(priority = 6)
    public void verifyDeleteButton() {
        this.slagalicaPage.verifyThatIzbrisiButtonIsClicked();
    }

    @Test(priority = 7)
    public void verifyStopButton() {
        this.slagalicaPage.verifyStopButtonDisplayed();
    }

    @Test(priority = 9)
    public void verifyPotvrdiButton() {
        this.slagalicaPage.verifyThatPotvrdiButtonIsClicked();
    }

    @Test(priority = 10)
    public void verifyDialog() {
        this.slagalicaPage.verifyPopUpDialog();
    }

    @Test(priority = 11)
    public void verifyCloseButtonClicked() {
        this.slagalicaPage.verifyCloseButtonIsClicked();
    }

    @Test(priority = 12)
    public void verifyDialogClosed() {
        takeSnapShot("Slagalica\\verifyDialogClosed", prop.getProperty("snapShotExtension"));
        this.slagalicaPage.waitForPopupToClose();
    }
}