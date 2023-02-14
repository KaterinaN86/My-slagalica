package pages;

import base.TestBase;
import org.slf4j.LoggerFactory;

public class AsocijacijaPage extends TestBase {

    public AsocijacijaPage() {
        super();
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public void verifyTitlesAndOtherPageElements(String configTitle, String containerTitle) {
        // Setting expected title value before calling verify method.
        setConfigTitle(configTitle);
        setPageTitle();
        this.verifyMethods.verifyPageTitle();
        // Verify container and container title.
        this.verifyMethods.verifyContainerDisplayed();
        this.verifyMethods.verifyContainerTitle(containerTitle);
    }
}
