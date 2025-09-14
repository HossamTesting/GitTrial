package pages;

import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import webdriverfactory.GetWebDriver;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;

public class FinalPaymentPage {

    UIActions uiActions;
    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public FinalPaymentPage(int wait) throws Exception {
        log.info("Initializing FinalPaymentPage object.");
        uiActions = new UIActions(wait);
    }

    public void enterPayerName(String payerName) {
        uiActions.sendKeys(UIActions.LocatorType.id, "payer_name", UIActions.ExplicitWaitCondition.elementToBeClickable, payerName);
    }

    public void enterAmount(String amount) {
        uiActions.sendKeys(UIActions.LocatorType.id, "amount", UIActions.ExplicitWaitCondition.elementToBeClickable, amount);
    }

    public void clickPayNow() {

        WebDriverWait customWait = new WebDriverWait(GetWebDriver.getLocalDriver(), Duration.ofSeconds(20));
        customWait.pollingEvery(Duration.ofMillis(500));

            customWait.until((ExpectedCondition<Boolean>)

                    driver -> {
                        try {
                            WebElement cardsLabelElement = uiActions.tryFindElement(UIActions.LocatorType.id, "cardsLabel", UIActions.ExplicitWaitCondition.none);

                            if (cardsLabelElement != null && uiActions.isElementDisplayed(cardsLabelElement)) {
                                log.info("cardsLabel field appeared. Navigation successful.");
                                return true;
                            }

                            WebElement payNowButton = uiActions.tryFindElement(UIActions.LocatorType.id, "pay_now_button", UIActions.ExplicitWaitCondition.none);

                            if (payNowButton != null && payNowButton.isEnabled()) {
                                uiActions.click(payNowButton);
                                log.info("Clicked Pay Now button. Waiting for next page...");
                            }

                            return false;

                        } catch (Exception e) {
                            log.error("Timeout: cardsLabel did not appear after clicking Pay Now.");
                            throw e;
                        }
                    });
    }


    public void checkFullAmount() {
        uiActions.click(UIActions.LocatorType.id, "full_deserved_amount_checkbox", UIActions.ExplicitWaitCondition.elementToBeClickable);
    }

    public void assertUnitCodeIsSameAsSelected(String actualUnitCode){
            WebElement unitCodeElement = uiActions.findWebElement(UIActions.LocatorType.xPath, "(//h3[@class='text'])[1]", UIActions.ExplicitWaitCondition.visibilityOfElement);
            uiActions.waitUntilElementHaveText(unitCodeElement, 10);
            Assert.assertEquals(actualUnitCode, uiActions.getElementText(unitCodeElement), "Assertion Failed, The unitCode shown in the FinalPaymentPage isn't the same as selected in the list.");
            log.info("\nAssertion Passed, The unitCode shown in the FinalPaymentPage is the same as selected in the list.\n");

    }

}


