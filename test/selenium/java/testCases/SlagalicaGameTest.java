package testCases;

import org.testng.annotations.Test;
import pages.SinglePlayerGamePage;

import java.io.IOException;

public class SlagalicaGameTest extends SlagalicaPageTest {


    @Test(priority = 1)
    public void logInAndOpen() {
        this.singlePlayerGamePage = this.loginPage.openLoginPage().userLogin(prop.getProperty("userAdisUsername"),
                prop.getProperty("userAdisPassword")).clickSinglePlayerGame();
        this.slagalicaPage = this.singlePlayerGamePage.openSlagalicaPage();
    }


    @Test(priority = 8)
    public void clickStopButton() {
        this.slagalicaPage.verifyThatStopButtonIsClicked();
    }

    @Test(priority = 13)
    public void printWordsTest() throws IOException {
        this.slagalicaPage.printWords();
    }
    @Test(priority = 14)
    public void verifyBackButton() {
        this.slagalicaPage.verifyMethods.verifyBackButtonIsClickable();
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBackAfterGameFinished();
    }
}
