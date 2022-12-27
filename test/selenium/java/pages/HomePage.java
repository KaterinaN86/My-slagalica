package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Reporter;

public class HomePage extends TestBase {

    By menuTitleLoc= By.xpath("//h1[@class='text-center text-white']");
    By menuLoc= By.xpath("//div[@class='row d-block text-center']");
    By singlePlayerLoc= By.xpath("//button[text()='Jedan igrac']");
    By twoPlayersLoc= By.xpath("//button[text()='Dva igraca']");
    By rulesLoc= By.xpath("//button[text()='Pravila']");
    By rangListLoc  = By.xpath("//button[text()='Rang Lista']");
    By logOutLoc  = By.xpath("//button[text()='Log out']");

    HomePage(){
        super();
    }
    public LoginPage logout(){
        Reporter.log("Logging out active user.");
        driver.findElement(logOutLoc).click();
        System.out.println("User logged out.");
        return new LoginPage();
    }
}
