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
    //List of objects that contain data for game. One objects matches data for current game.
    List<SpojniceDataModel> dataList;

    List<WebElement> tableCellsEl;

    SpojniceDataModel gameData;

    public SpojnicePage() {
        super();
        this.gameData=new SpojniceDataModel();
        dataList = SpojniceData.readData();
    }

    public void verifyTitlesAfterOpen(String expectedPageTitle, String expectedContentTitle) {
        this.setConfigTitle(expectedPageTitle);
        this.verifyMethods.verifyPageTitle();
        this.verifyMethods.verifyH1Content(expectedContentTitle);
    }

    public void verifyTableContent() {
        List<String> column1Btns = new ArrayList<>();
        List<String> column2Btns = new ArrayList<>();
        int i = 1;
        waitForVisibilityOf(tableBodyLoc);
        tableCellsEl = findAll(new By.ByXPath("//*[@id='tbody']//child::td"));
        wait.until(ExpectedConditions.visibilityOfAllElements(tableCellsEl));
        Reporter.log("Verifying all buttons for picking pairs are enabled.");
        System.out.println("Verifying all buttons for picking pairs are enabled.");
        for (WebElement el : tableCellsEl) {
            if (i % 2 != 0) {
                column1Btns.add(el.getText());
            } else {
                column2Btns.add(el.getText());
            }
            Assert.assertTrue(el.isEnabled());
            i++;
        }
        Reporter.log("All buttons are enabled.");
        System.out.println("All buttons are enabled.");
        Reporter.log("Verifying data in columns.");
        System.out.println("Verifying data in columns.");
        verifyColumnData(column1Btns,column2Btns);
    }

    public void verifyGameHeadline() {
        boolean isValid = false;
        waitForVisibilityOf(headlineLoc);
        String headlineText = find(headlineLoc).getText();
        for (SpojniceDataModel modelObject : dataList) {
            isValid = modelObject.getHeadline().equals(headlineText);
            if (isValid) {
                this.gameData=modelObject;
                break;
            }
        }
        Reporter.log("Verifying game headline.");
        System.out.println("Verifying game headline.");
        Assert.assertTrue(isValid, "Game headline: " + headlineText + " doesn't match expected data.");
        Reporter.log("Game headline: " + headlineText + " verified.");
        System.out.println("Game headline: " + headlineText + " verified.");
    }

    public void verifyColumnData(List<String> column1, List<String> column2) {
        int i =0;
        for(String word:column1) {
            Assert.assertTrue(gameData.getColumn1().get(i).contains(word),"Word: "+ word + " doesn't match expected data.");
            i++;
        }
        i=0;
        for(String word:column2) {
            Assert.assertTrue(gameData.getColumn2().get(i).contains(word), "Word: "+ word + " doesn't match expected data.");
            i++;
        }
        Reporter.log("Words in button columns match expected data.");
        System.out.println("Words in button columns match expected data.");
    }
}
