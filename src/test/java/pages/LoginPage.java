package pages;

import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;

public class LoginPage extends BasePage {


    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public LoginPage(int wait) throws Exception {
        super(new UIActions(wait));
        log.info("Initializing LoginPage object.");
    }

    public void enterId(String id) {
        uiActions.sendKeys(UIActions.LocatorType.id, "user_name", UIActions.ExplicitWaitCondition.elementToBeClickable, id);
    }

    public void enterPassword(String password) {
        uiActions.sendKeys(UIActions.LocatorType.id, "password", UIActions.ExplicitWaitCondition.elementToBeClickable, password);
    }

    public void clickLoginButton() {
        uiActions.click(UIActions.LocatorType.id, "login_button", UIActions.ExplicitWaitCondition.elementToBeClickable);
        waitLoadingScreenRemoved();
    }
}
