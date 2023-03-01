package pages;

import base.TestBase;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.LoggerFactory;

public class AsocijacijaPage extends TestBase {

    public AsocijacijaPage() {
        super();
        logger = LoggerFactory.getLogger(this.getClass());
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.toLevel("INFO"));
        context.updateLoggers();
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
