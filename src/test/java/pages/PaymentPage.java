package pages;

import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import webdriverfactory.GetWebDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.invoke.MethodHandles.lookup;

public class PaymentPage extends BasePage{

    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public PaymentPage(int wait) throws Exception {
        super(new UIActions(wait));
        log.info("Initializing PaymentPage object.");
    }

    public void clickPayNewInvoiceButton() {
        uiActions.click(UIActions.LocatorType.id, "pay_new_bill", UIActions.ExplicitWaitCondition.elementToBeClickable);
    }

    public void clickSearchButton() {
        uiActions.click(UIActions.LocatorType.id, "search_button", UIActions.ExplicitWaitCondition.elementToBeClickable);
    }

    public void enterUnitCode(String unitId) {
        uiActions.sendKeys(UIActions.LocatorType.id, "unit_code", UIActions.ExplicitWaitCondition.elementToBeClickable, unitId);
    }

    public void clickOnDetailsButton(Buttons button, int rowNo) {
        try {

        String buttonId;
        if (button == Buttons.detailsButton) {
            buttonId = "details_button";
        } else if (button == Buttons.receiptButton) {
            buttonId = "pdf-Receipt";
        } else {
            throw new IllegalArgumentException("Unsupported button type: " + button);
        }
       List<WebElement> buttons = getTableButtonsElements(buttonId);

        if (buttons.get(rowNo-1) == (null))
        {    log.error("Assertion failed, The Payment selected doesn't contain a PDF-receipt button, Please select another payment.");
            Assert.fail("Assertion failed, The Payment selected doesn't contain a PDF-receipt button, Please select another payment.");
        }
           WebElement desiredButtonElement = buttons.get(rowNo-1);
            uiActions.click(desiredButtonElement);
            if (buttonId.equals("details_button")){
                waitLoadingScreenRemoved();}


       } catch (NoSuchElementException e) {
           log.error("The PDF Receipt Button isn't available for this payment.");
           throw e;
       }
        catch (ArrayIndexOutOfBoundsException e) {
            log.error("The rowNo entered isn't available in the page, Please select a valid rowNo.");
            throw e;
        }



    }

    public String getSelectedUnitCode(int rowNo){
        List<String> unitCodeCellsValues = uiActions.getTableCellValues(
                UIActions.LocatorType.className, "table",
                UIActions.LocatorType.tagName, "tr",
                UIActions.LocatorType.className, "unit-code"
        );
        if (rowNo <= 0 || rowNo > 10){
            log.error("Please check a valid row, commonly between 1 ~ 10.");
            Assert.fail("Please check a valid row, commonly between 1 ~ 10.");
        }
        String formattedUnitCode = unitCodeCellsValues.get(rowNo-1).substring(1);  ; //To get the unitCode clicked on to check with it
        return formattedUnitCode;
    }

    //Assertion

    public void assertMessageShownAsPaymentDoneSuccessfully(SoftAssert softAssert) {
        try {
            WebElement resultElement = getResultElement();
            String resultText = uiActions.getElementText(resultElement);
            if (resultText.contains("تمت عملية الدفع بنجاح")) {
                softAssert.assertTrue(true);
                log.info("\nAssertion Passed, Payment confirmation message displayed: '{}'.\n", resultText);
            } else {
                log.error("Assertion Failed, Payment failed. Error message displayed: '{}'", resultText);
                softAssert.fail("Assertion Failed, Payment failed. Error message: '" + resultText + "'");
            }

        } catch (TimeoutException e) {
            log.error("Assertion Failed, Timeout waiting for payment result message.");
            softAssert.fail("Assertion Failed, Neither success nor error message appeared within timeout.");
        }


    }

    public void assertNoPaymentsShown() {
        List<String> unitCodeCellsValues = uiActions.getTableCellValues(
                UIActions.LocatorType.className, "table",
                UIActions.LocatorType.tagName, "tr",
                UIActions.LocatorType.className, "unit-code"
        );
        String message = uiActions.getElementText(UIActions.LocatorType.tagName, "tbody", UIActions.ExplicitWaitCondition.visibilityOfElement);
        Assert.assertTrue(unitCodeCellsValues.isEmpty(), "Expected no payments, but found records.");
        log.info("\nAssertion passed, No payments are shown as expected and the message '{}' is shown.\n", message);
    }

    public void assertPaymentCodeShownAsEntered(SoftAssert softAssert, String unitCode) {
        int count = 1;
        int records = 0;
        while (true) {
            //Find the table cells values
            List<String> unitCodeCellsValues = uiActions.getTableCellValues(
                    UIActions.LocatorType.className, "table",
                    UIActions.LocatorType.tagName, "tr",
                    UIActions.LocatorType.className, "unit-code"
            );
            if (unitCodeCellsValues.isEmpty()) {
                Assert.fail("There are no shown rows, Please enter valid Id as the message 'لا توجد مدفوعات' is shown.");
            }

            //Loop for the shown values number and check that the value as inserted
            for (int rowIndex = 0; rowIndex < unitCodeCellsValues.size(); rowIndex++) {
                String unitCodeCellsValue = unitCodeCellsValues.get(rowIndex);
                softAssert.assertEquals(unitCodeCellsValue.substring(1), unitCode);

                if (!unitCodeCellsValue.substring(1).equals(unitCode)) {
                    log.error("The Unit code '{}' of row no '{}' in Page no '{}' isn't same as the searching value '{}'.",
                            unitCodeCellsValue.substring(1), rowIndex + 1, count, unitCode);
                }
            }
            records += unitCodeCellsValues.size();
            try {
                //Find an element in the next page button if it's not it will quit the while
                WebElement nextPageButton = uiActions.tryFindElement(
                        UIActions.LocatorType.xPath,
                        "(//button[@type='button'])[last()]",
                        UIActions.ExplicitWaitCondition.elementToBeClickable
                );

                //if the next page button is clickable click on it and count
                if (uiActions.isElementEnabled(nextPageButton)) {
                    uiActions.click(nextPageButton);
                    WebElement loadingScreen = uiActions.tryFindElement(UIActions.LocatorType.xPath, "//div[@class='loading-container']", UIActions.ExplicitWaitCondition.visibilityOfElement);
                    uiActions.waitUntilInvisibilityOfElement(loadingScreen,20);
                    count++;
                } else {
                    log.info("Next button isn't clickable any more, Only one page is displayed with '{}' no of records.", records);
                    break;
                }
            } catch (Exception e) {
                log.info("Next button isn't clickable any more, '{}' Pages are displayed with '{}' no of records.", count, records);
                break;
            }
        }
    }

    private WebElement getResultElement() {
        WebDriverWait customWait = new WebDriverWait(GetWebDriver.getLocalDriver(), Duration.ofSeconds(30));

        return customWait.until((ExpectedCondition<WebElement>) driver -> {
            try {
                WebElement errorElement = uiActions.tryFindElement(
                        UIActions.LocatorType.id,
                        "lbl_error_msg1",
                        UIActions.ExplicitWaitCondition.none
                );
                String errorText = uiActions.getElementText(errorElement);
                if (errorText != null && !errorText.trim().isEmpty()) {
                    log.info("Detected error message: '{}'", errorText);
                    return errorElement;
                }
            } catch (Exception ignored) {
            }

            try {
                WebElement successElement = uiActions.tryFindElement(
                        UIActions.LocatorType.xPath,
                        "//div[contains(text(),'تمت عملية الدفع بنجاح')]",
                        UIActions.ExplicitWaitCondition.none
                );

                if (successElement.isDisplayed()) {
                    return successElement;
                }
            } catch (Exception ignored) {
            }

            return null;
        });
    }

    public void assertPaymentRecordAddedToList(SoftAssert softAssert, String payerName, String paymentMoney) {
        List<String> payerNameCellsValues = uiActions.getTableCellValues(
                UIActions.LocatorType.className, "table",
                UIActions.LocatorType.tagName, "tr",
                UIActions.LocatorType.className, "payer-name"
        );
        List<String> paymentDateCellsValues = uiActions.getTableCellValues(
                UIActions.LocatorType.className, "table",
                UIActions.LocatorType.tagName, "tr",
                UIActions.LocatorType.className, "payer-date"
        );
        List<String> paymentMoneyCellsValues = uiActions.getTableCellValues(
                UIActions.LocatorType.className, "table",
                UIActions.LocatorType.tagName, "tr",
                UIActions.LocatorType.className, "payer-mony"
        );
//       As Sometimes it takes sometime to change from نحن المعالجه  to تم الدفع
//        List<String> paymentStatusCellsValues = uiActions.getTableCellValues(
//                UIActions.LocatorType.className, "table",
//                UIActions.LocatorType.tagName, "tr",
//                UIActions.LocatorType.className, "payer-status"
//        );
        boolean isRecordValid = true;

        String actualPayerName = payerNameCellsValues.getFirst();

        if (!actualPayerName.equals(payerName)) {
            softAssert.assertEquals(actualPayerName, payerName, "The payerName isn't the same as expected.");
            isRecordValid = false;
        }

        String actualPaymentDate = paymentDateCellsValues.getFirst();
        String expectedPaymentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        if (!actualPaymentDate.equals(expectedPaymentDate)) {
            softAssert.assertEquals(actualPaymentDate, expectedPaymentDate, "The paymentDate isn't the same as expected.");
            isRecordValid = false;
        }

        String actualPaymentMoney = paymentMoneyCellsValues.getFirst();
        String expectedPaymentMoney = String.format("%.2f", Double.parseDouble(paymentMoney));

        if (!actualPaymentMoney.equals(expectedPaymentMoney)) {
            softAssert.assertEquals(actualPaymentMoney, expectedPaymentMoney, "The paymentMoney isn't the same as expected.");
            isRecordValid = false;
        }

//        String actualPaymentStatus = paymentStatusCellsValues.getFirst();
//        String expectedPaymentStatus = "تم الدفع";
//
//        if (!actualPaymentStatus.equals(expectedPaymentStatus)) {
//            softAssert.assertEquals(actualPaymentStatus, expectedPaymentStatus, "The paymentStatus isn't the same as expected.");
//            isRecordValid = false;
//        }

        if (isRecordValid) {
            log.info("\nAssertion Passed, The record is shown in the list.\n");
        }


    }

    private List<WebElement> getTableButtonsElements(String Button) {
        WebElement table = uiActions.findWebElement(UIActions.LocatorType.className, "table", UIActions.ExplicitWaitCondition.visibilityOfElement);
        List<WebElement> rows = uiActions.findWebElementsInParent(table, UIActions.LocatorType.tagName, "tr");

        List<WebElement> detailsButtons = new ArrayList<>();
        int index = 1;

        for (WebElement row : rows) {
            List<WebElement> cells = uiActions.findWebElementsInParent(row, UIActions.LocatorType.className, "payer-details");
            for (WebElement cell : cells) {
                try {
                    detailsButtons.add(cell.findElement(By.id(Button)));
                } catch (Exception ignored) {
                    detailsButtons.add(null);
                    log.warn("The Button at row '{}' is not present; it's value is null.", index);
                }
                index++;
            }
        }
        return detailsButtons;
    }

    public enum Buttons {
        detailsButton,
        receiptButton
    }

}






