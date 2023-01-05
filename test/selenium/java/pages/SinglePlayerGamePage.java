package pages;

import base.TestBase;
import org.openqa.selenium.By;

/**
 * Class representing page where user chooses single player game. Defined using Page Object Model, inherits TestBase fields and methods.
 */
public class SinglePlayerGamePage extends TestBase {

    /**
     * Locator object for container element title.
     */
    By containerTitleLoc = By.xpath("//h1[contains(@class,'naslov')]");

    public SinglePlayerGamePage() {
        super();
    }
}
