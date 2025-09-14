package pages;

import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;

public class SpecificPaymentDetails {
    UIActions uiActions;
    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public SpecificPaymentDetails(int wait) throws Exception {
        log.info("Initializing SpecificPaymentDetails object.");
        uiActions = new UIActions(wait);
    }


    public void clickPayForSameUnitButton() {
        uiActions.click(UIActions.LocatorType.id, "pay_for_same_unit", UIActions.ExplicitWaitCondition.elementToBeClickable);
    }

    public void clickPaymentsListsButton() {
        uiActions.click(UIActions.LocatorType.id, "payments_list", UIActions.ExplicitWaitCondition.elementToBeClickable);
    }
}
