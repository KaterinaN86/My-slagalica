package utility;

import org.openqa.selenium.By;

public class Locators {
    /**
     * Username and password text input elements locators, used LoginPage and RegisterPage.
     */
    private final By usernameTextInputLoc = By.xpath("//input[@placeholder='Username']");
    private final By passwordTextInputLoc = By.xpath("//input[@placeholder='Password']");
    /**
     * Register button locator, used in LoginPage and RegisterPage.
     */
    private final By registerBtnLoc = By.xpath("//input[@value='Register']");
    /**
     * Locator object for container element, used in HomePage and SinglePlayerGamePage.
     */
    private final By containerLoc = By.xpath("//div[@class='container p-5']");
    /**
     * Locator object for container title.
     */
    private final By containerTitleLoc = By.xpath("//h1[contains(@class,'text-center text-white')]");
    /**
     * Locator object for div container for button elements on pages HomePage, SinglePlayerGamePage, MojBrojPage and SlagalicaPage
     */
    private final By allButtonDivsLoc = new By.ByXPath("//*[@class='row d-block text-center']//child::div");
    /**
     * Back button locator object. Used in multiple pages.
     */
    private final By backBtnLoc = new By.ByXPath("//*[@class='bi bi-arrow-left']//parent::button");
    /**
     * Timer element locator object. Used in MojBrojPage and SlagalicaPage.
     */
    private final By timerLoc = new By.ByXPath("//div[@id='timer']");

    private final By h1TitleLoc = new By.ByTagName("h1");

    public By getUsernameTextInputLoc() {
        return usernameTextInputLoc;
    }

    public By getPasswordTextInputLoc() {
        return passwordTextInputLoc;
    }

    public By getRegisterBtnLoc() {
        return registerBtnLoc;
    }

    public By getContainerLoc() {
        return containerLoc;
    }

    public By getContainerTitleLoc() {
        return containerTitleLoc;
    }

    public By getAllButtonDivsLoc() {
        return allButtonDivsLoc;
    }

    public By getBackBtnLoc() {
        return backBtnLoc;
    }

    public By getTimerLoc() {
        return timerLoc;
    }

    public By getH1TitleLoc() {
        return h1TitleLoc;
    }

}
