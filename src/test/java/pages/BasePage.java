package pages;

import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import static java.lang.invoke.MethodHandles.lookup;

public class BasePage {

    UIActions uiActions ;
    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public BasePage (UIActions uiActions) {
        log.info("Initializing BasePage object.");
        this.uiActions = uiActions;

    }
    public void waitLoadingScreenRemoved(){
        try {
            WebElement loadingScreen = uiActions.tryFindElement(UIActions.LocatorType.xPath, "//div[@class='loading-container']", UIActions.ExplicitWaitCondition.visibilityOfElement);
            uiActions.waitUntilInvisibilityOfElement(loadingScreen, 20);
        }
        catch (Exception e){
            log.error("The Loading screen hasn't been removed, it keeps loading.");
            throw e;
        }
    }


}
