package pages;

import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import static java.lang.invoke.MethodHandles.lookup;

public class UserConfirmationPage extends BasePage{

    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public UserConfirmationPage(int wait) throws Exception {
        super(new UIActions(wait));
        log.info("Initializing UserConfirmationPage object.");
    }

    public void clickConfirm (){
        uiActions.click(UIActions.LocatorType.id,"data_confirmation_button", UIActions.ExplicitWaitCondition.elementToBeClickable);
       waitLoadingScreenRemoved();
    }
    public void clickCancel (){
        uiActions.click(UIActions.LocatorType.id,"cancel_button", UIActions.ExplicitWaitCondition.elementToBeClickable);
    }
}
