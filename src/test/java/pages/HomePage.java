package pages;

import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;

public class HomePage {

    UIActions uiActions ;
    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public HomePage (int wait) throws Exception {
        log.info("Initializing HomePage object.");
         uiActions = new UIActions(wait);

    }

    public void selectPage  (String page){
        uiActions.click(UIActions.LocatorType.xPath,"//h3[contains(text(), '"+page+"')]", UIActions.ExplicitWaitCondition.visibilityOfElement);
    }



}
