package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Reporter;

public class SlagalicaPage extends TestBase {

    public SlagalicaPage() {super();}

    By stopButtonLocator = new By.ById("stopButton");

    public void verifyThatStopButtonIsClicked(){
        driver.findElement((stopButtonLocator)).click();
        Reporter.log("Stop button is clicked.");
        System.out.println("Stop button is clicked.");
    }

}
