package pages;

import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import static java.lang.invoke.MethodHandles.lookup;

public class FinalConfirmationPage {

    UIActions uiActions;
    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public FinalConfirmationPage(int wait) throws Exception {
        log.info("Initializing FinalConfirmationPage object.");
        uiActions = new UIActions(wait);

    }

    public void selectNationality(Nationality EGorUS) {
        uiActions.selectDropDownOption(
                uiActions.findDropDownElement(UIActions.LocatorType.id, "nationality_code", UIActions.ExplicitWaitCondition.visibilityOfElement),
                UIActions.SelectBy.value,
                String.valueOf(EGorUS));
    }

    public void enterNationalId(String nationalId) {
        uiActions.clearText(UIActions.LocatorType.id, "national_id", UIActions.ExplicitWaitCondition.elementToBeClickable);
        uiActions.sendKeys(UIActions.LocatorType.id, "national_id", UIActions.ExplicitWaitCondition.elementToBeClickable, nationalId);
    }

    public void enterMobileNo(String mobileNo) {
        uiActions.clearText(UIActions.LocatorType.id, "mobile", UIActions.ExplicitWaitCondition.elementToBeClickable);
        uiActions.sendKeys(UIActions.LocatorType.id, "mobile", UIActions.ExplicitWaitCondition.elementToBeClickable, mobileNo);
    }

    public void clickSubmitButton() {
        WebElement submitButton = uiActions.findWebElement(UIActions.LocatorType.id, "address_form_confirmation_submit", UIActions.ExplicitWaitCondition.elementToBeClickable);
        uiActions.moveToElement(submitButton);
        uiActions.click(submitButton);
    }

    public void clickCancelButton() {
        uiActions.click(UIActions.LocatorType.id, "cancel_confirmation_button_", UIActions.ExplicitWaitCondition.elementToBeClickable);
    }

    public void enterPassportOrNationalId(Nationality nationality, String passportOrNationalId) {
        if (nationality.equals(Nationality.EG)) {
            uiActions.clearText(UIActions.LocatorType.id, "national_id", UIActions.ExplicitWaitCondition.elementToBeClickable);
            uiActions.sendKeys(UIActions.LocatorType.id, "national_id", UIActions.ExplicitWaitCondition.elementToBeClickable, passportOrNationalId);
        } else {
            uiActions.clearText(UIActions.LocatorType.id, "passport_num", UIActions.ExplicitWaitCondition.elementToBeClickable);
            uiActions.sendKeys(UIActions.LocatorType.id, "passport_num", UIActions.ExplicitWaitCondition.elementToBeClickable, passportOrNationalId);
        }

    }

    public enum Nationality {
        EG,
        US,
    }
}
