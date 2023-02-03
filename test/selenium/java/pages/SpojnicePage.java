package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import utility.spojnice.SpojniceData;
import utility.spojnice.SpojniceDataModel;

import java.util.ArrayList;
import java.util.List;

public class SpojnicePage extends TestBase {
    //Locator object for game headline text.
    By headlineLoc = new By.ById("headline");
    //Locator object for table element body.
    By tableBodyLoc = new By.ById("tbody");
    //Locator object for submit button.
    By submitBtnLoc = new By.ById("submitBtn");
    //List of objects that contain data for game. One objects matches data for current game.
    List<SpojniceDataModel> dataList;

    List<String> column1Words;
    List<String> column2Words;

    SpojniceDataModel gameData;

    List<WebElement> column1Btns;
    List<WebElement> column2Btns;

    boolean noAlertOnSubmit;

    public SpojnicePage() {
        super();
        this.gameData = new SpojniceDataModel();
        dataList = SpojniceData.readData();
    }

    public void verifyTitlesAfterOpen(String expectedPageTitle, String expectedContentTitle) {
        this.setConfigTitle(expectedPageTitle);
        this.verifyMethods.verifyPageTitle();
        this.verifyMethods.verifyH1Content(expectedContentTitle);
    }

    public void verifyTableContent() {
        column1Words = new ArrayList<>();
        column2Words = new ArrayList<>();
        column1Btns=new ArrayList<>();
        column2Btns=new ArrayList<>();
        int i = 1;
        waitForVisibilityOf(tableBodyLoc);
        try {
            List<WebElement> tableCellsEl = findAll(new By.ByXPath("//*[@id='tbody']//child::td"));
            wait.until(ExpectedConditions.visibilityOfAllElements(tableCellsEl));
            Reporter.log("Verifying all buttons for picking pairs are enabled.");
            System.out.println("Verifying all buttons for picking pairs are enabled.");
            for (WebElement el : tableCellsEl) {
                if (i % 2 != 0) {
                    column1Btns.add(el);
                    column1Words.add(el.getText());
                } else {
                    column2Btns.add(el);
                    column2Words.add(el.getText());
                }
                Assert.assertTrue(el.isEnabled());
                i++;
            }
            Reporter.log("All buttons are enabled.");
            System.out.println("All buttons are enabled.");
            Reporter.log("Verifying data in columns.");
            System.out.println("Verifying data in columns.");
            verifyColumnData();
        } catch (Exception e) {
            System.err.println("Game error! Columns with word buttons not displayed!");
        }
    }

    public void verifyGameHeadline() {
        boolean isValid = false;
        try {
            waitForVisibilityOf(headlineLoc);
            String headlineText = find(headlineLoc).getText();
            for (SpojniceDataModel modelObject : dataList) {
                isValid = modelObject.getHeadline().equals(headlineText);
                if (isValid) {
                    this.gameData = modelObject;
                    break;
                }
            }
            Reporter.log("Verifying game headline.");
            System.out.println("Verifying game headline.");
            Assert.assertTrue(isValid, "Game headline: " + headlineText + " doesn't match expected data.");
            Reporter.log("Game headline: " + headlineText + " verified.");
            System.out.println("Game headline: " + headlineText + " verified.");
        } catch (Exception e) {
            System.err.println("Game error! Headline is not displayed!");
        }
    }

    public void verifyColumnData() {
        int i = 0;
        for (String word : column1Words) {
            Assert.assertTrue(gameData.getColumn1().get(i).contains(word), "Word: " + word + " doesn't match expected data.");
            i++;
        }
        i = 0;
        for (String word : column2Words) {
            Assert.assertTrue(gameData.getColumn2().get(i).contains(word), "Word: " + word + " doesn't match expected data.");
            i++;
        }
        Reporter.log("Words in button columns match expected data.");
        System.out.println("Words in button columns match expected data.");
    }

    public void clickSubmitBtn() {
        noAlertOnSubmit = false;
        this.waitForElToBeClickable(submitBtnLoc);
        click(submitBtnLoc);
        try {
            this.dealWithAlert();
            System.out.println(this.getAlertMsg());
        }catch (Exception e){
            noAlertOnSubmit=true;
            System.err.println("Submit button alert not displayed!");
        }
    }
    public TestBase pickGoBackMethod(){
        if(noAlertOnSubmit){
            return this.goBack();
        }
        else{
            return this.goBackAfterGameFinished();
        }
    }
}
