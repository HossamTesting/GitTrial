package pages;

import actions.BrowserActions;
import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import static java.lang.invoke.MethodHandles.lookup;

public class eFinancePaymentPage {

    UIActions uiActions ;
    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public eFinancePaymentPage (int wait) throws Exception {
        log.info("Initializing eFinancePaymentPage object.");
        uiActions = new UIActions(wait);

    }

    public void selectPaymentWay(paymentWay paymentWay) {
        switch (paymentWay) {
            case cardsLabel -> {
                uiActions.click(UIActions.LocatorType.id, "cardsLabel", UIActions.ExplicitWaitCondition.elementToBeClickable);
            }
            case meezaLabel ->
                    uiActions.click(UIActions.LocatorType.id, "meezaLabel", UIActions.ExplicitWaitCondition.elementToBeClickable);

        }
    }
    public enum paymentWay {
        cardsLabel,
        meezaLabel
    }

    public void enterCardNo(String cardNo) throws InterruptedException {
        WebElement frame = uiActions.findWebElement(UIActions.LocatorType.xPath,"//iframe[@title='card number']", UIActions.ExplicitWaitCondition.visibilityOfElement);
        BrowserActions.switchToFrame(frame);
        WebElement cardNoElement = uiActions.findWebElement(UIActions.LocatorType.id,"number", UIActions.ExplicitWaitCondition.elementToBeClickable);
        uiActions.waitUntilElementIsVisibleAndInteractableOrFail(cardNoElement,20);
        Thread.sleep(500);
        uiActions.clearText(cardNoElement);
        uiActions.sendKeys(cardNoElement,cardNo);
        BrowserActions.switchToParentFrame();

    }

    public void enterSecurityCode(String securityCode) throws InterruptedException {
        WebElement frame = uiActions.findWebElement(UIActions.LocatorType.xPath,"//iframe[@title='security code']", UIActions.ExplicitWaitCondition.visibilityOfElement);
        BrowserActions.switchToFrame(frame);
        WebElement securityCodeElement = uiActions.findWebElement(UIActions.LocatorType.id,"securityCode", UIActions.ExplicitWaitCondition.elementToBeClickable);
        uiActions.waitUntilElementIsVisibleAndInteractableOrFail(securityCodeElement,20);
        Thread.sleep(500);
        uiActions.clearText(securityCodeElement);
        uiActions.sendKeys(securityCodeElement,securityCode);
        BrowserActions.switchToParentFrame();
    }

    public void selectExpiryMonth(int month) throws Exception {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month value out of range: '" + month + "'. Valid values are 1 to 12.");
        }
        WebElement frame = uiActions.findWebElement(UIActions.LocatorType.xPath,"//iframe[@title='Expiry Date Month']", UIActions.ExplicitWaitCondition.visibilityOfElement);
        BrowserActions.switchToFrame(frame);
        String formattedMonth = String.format("%02d", month);
        uiActions.selectDropDownOption(
                uiActions.findDropDownElement(UIActions.LocatorType.id, "expiryMonth", UIActions.ExplicitWaitCondition.visibilityOfElement),
                UIActions.SelectBy.text,
                formattedMonth
        );
        BrowserActions.switchToParentFrame();

    }

    public void selectExpiryYear(int year) throws Exception {
        if (year < 25 || year > 42) {
            throw new IllegalArgumentException("Year value out of range: '" + year + "'. Valid values are 25 to 42.");
        }
        WebElement frame = uiActions.findWebElement(UIActions.LocatorType.xPath,"//iframe[@title='Expiry Date Year']", UIActions.ExplicitWaitCondition.visibilityOfElement);
        BrowserActions.switchToFrame(frame);
        String formattedYear = String.format("%02d", year);
        uiActions.selectDropDownOption(
                uiActions.findDropDownElement(UIActions.LocatorType.id, "expiryYear", UIActions.ExplicitWaitCondition.visibilityOfElement),
                UIActions.SelectBy.text,
                formattedYear
        );
        BrowserActions.switchToParentFrame();

    }

    public void clickPayButton(){
        uiActions.click(UIActions.LocatorType.id,"btnPay", UIActions.ExplicitWaitCondition.elementToBeClickable);
    }

}
