package testCases.slagalicaTestCases;

import org.testng.annotations.Test;

import java.io.IOException;

public class SlagalicaGameNegativeTestCase extends SlagalicaPageTest {

    @Test(priority = 8)
    public void clickStopButton() {
        this.slagalicaPage.verifyThatStopButtonIsClicked();
    }

//    @Test(priority = 13)
//    public void clickLetterFromFirstListTest() {
//        this.slagalicaPage.clickLetterFromFirstList();
//    }

}
