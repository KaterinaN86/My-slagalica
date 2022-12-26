package pages;

import org.openqa.selenium.By;

public class RegisterPage extends LoginPage {
    /**
     * Locator object for register container div element
     */
    By loginFormLoc = By.xpath("//div[@class='register-form']");

    /**
     * Locator for login button
     */
    By loginBtnLoc = By.xpath("//button[@value='login']");
    /**
     * Locator object for register form title
     */
    By registerFormTitleLoc = By.xpath("//h1[text()='Register']");

    private String configTitle = prop.getProperty("registerPageTitle");

    RegisterPage() {
        super();
    }
}
