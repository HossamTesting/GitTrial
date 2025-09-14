package pages;

import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;

public class PaymentDetailsPage {

    UIActions uiActions;
    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public PaymentDetailsPage(int wait) throws Exception {
        log.info("Initializing PaymentDetailsPage object.");
        uiActions = new UIActions(wait);
    }

    public void selectVillage(String villageName) {
        WebElement villageList = uiActions.findWebElement(UIActions.LocatorType.id, "place_id_north_input", UIActions.ExplicitWaitCondition.elementToBeClickable);
        uiActions.sendKeys(villageList, villageName);
        uiActions.waitForAttributeContainsValueOrFail(villageList, "aria-activedescendant", "react", 10);
        uiActions.enterKeyCombination(Keys.ENTER);
        try {
            uiActions.findWebElement(UIActions.LocatorType.xPath, "//*[contains(text(),'" + villageName + "')]", UIActions.ExplicitWaitCondition.visibilityOfElement);
            log.info("Village '{}' selected successfully.", villageName);
        } catch (Exception e) {
            log.error("Unable to select village '{}'. It may not exist in the list or was selected incorrectly.", villageName);
            throw e;
        }
    }

    public void selectBuildingId(String buildingId, boolean NorthCoast) {
        WebElement buildingList;
        if (NorthCoast) {
            buildingList = uiActions.findWebElement(UIActions.LocatorType.id, "build_id_north_input", UIActions.ExplicitWaitCondition.elementToBeClickable);
        } else {
            buildingList = uiActions.findWebElement(UIActions.LocatorType.id, "build_id_input", UIActions.ExplicitWaitCondition.elementToBeClickable);

        }
        uiActions.sendKeys(buildingList, buildingId);
        uiActions.waitForAttributeContainsValueOrFail(buildingList, "aria-activedescendant", "react", 10);
        uiActions.enterKeyCombination(Keys.ENTER);

        try {
            uiActions.findWebElement(UIActions.LocatorType.xPath, "//div[@id='build_id']//div[normalize-space(text())='" + buildingId + "']", UIActions.ExplicitWaitCondition.visibilityOfElement);
            log.info("Building ID '{}' selected successfully.", buildingId);
        } catch (Exception e) {
            log.error("Unable to select Building ID '{}'. It may not exist in the list or was selected incorrectly.", buildingId);
            throw e;
        }
    }

    public void selectFloorId(String floorId, boolean northCoast) {
        WebElement floorList;
        if (northCoast) {
            floorList = uiActions.findWebElement(UIActions.LocatorType.id, "part_id_north_input", UIActions.ExplicitWaitCondition.elementToBeClickable);
        } else {
            floorList = uiActions.findWebElement(UIActions.LocatorType.id, "part_id_input", UIActions.ExplicitWaitCondition.elementToBeClickable);

        }
        uiActions.sendKeys(floorList, floorId);
        uiActions.waitForAttributeContainsValueOrFail(floorList, "aria-activedescendant", "react", 10);
        uiActions.enterKeyCombination(Keys.ENTER);

        try {
            uiActions.findWebElement(UIActions.LocatorType.xPath, "//div[@id='part_id']//div[normalize-space(text())='" + floorId + "']", UIActions.ExplicitWaitCondition.visibilityOfElement);
            log.info("Floor ID '{}' selected successfully.", floorId);
        } catch (Exception e) {
            log.error("Unable to select Floor ID '{}'. It may not exist in the list or was selected incorrectly.", floorId);
            throw e;
        }
    }

    public void selectUnitId(String unitId, boolean northCoast) {
        WebElement unitList;
        if (northCoast) {
            unitList = uiActions.findWebElement(UIActions.LocatorType.id, "unit_id_north_input", UIActions.ExplicitWaitCondition.elementToBeClickable);
        } else {
            unitList = uiActions.findWebElement(UIActions.LocatorType.id, "unit_id_input", UIActions.ExplicitWaitCondition.elementToBeClickable);

        }
        uiActions.sendKeys(unitList, unitId);
        uiActions.waitForAttributeContainsValueOrFail(unitList, "aria-activedescendant", "react", 10);
        uiActions.enterKeyCombination(Keys.ENTER);

        try {
            uiActions.findWebElement(UIActions.LocatorType.xPath, "//div[@id='unit_id']//div[normalize-space(text())='" + unitId + "']", UIActions.ExplicitWaitCondition.visibilityOfElement);
            log.info("Unit ID '{}' selected successfully.", unitId);
        } catch (Exception e) {
            log.error("Unable to select Unit ID '{}'. It may not exist in the list or was selected incorrectly.", unitId);
            throw e;
        }
    }

    public void clickSubmitButton() {
        String activeTab = uiActions.getElementAttribute(UIActions.LocatorType.css, "[class='nav-link active']", UIActions.ExplicitWaitCondition.visibilityOfElement, "id");
        try {
            switch (activeTab) {
                case "north_coast_tab" ->
                        uiActions.click(UIActions.LocatorType.id, "north_coast_form_submit_check", UIActions.ExplicitWaitCondition.visibilityOfElement);
                case "address-tab" ->
                        uiActions.click(UIActions.LocatorType.id, "address_form_submit", UIActions.ExplicitWaitCondition.visibilityOfElement);
                case "access_key_tab" ->
                        uiActions.click(UIActions.LocatorType.id, "access_key_form_submit", UIActions.ExplicitWaitCondition.visibilityOfElement);
            }
        } catch (Exception e) {
            log.error("The Submit Button isn't clickable");
            throw e;
        }
    }

    public void clickClearButton() {
        String activeTab = uiActions.getElementAttribute(UIActions.LocatorType.css, "[class='nav-link active']", UIActions.ExplicitWaitCondition.visibilityOfElement, "id");
        try {
            switch (activeTab) {
                case "north_coast_tab" ->
                        uiActions.click(UIActions.LocatorType.id, "north_coast_form_clear", UIActions.ExplicitWaitCondition.elementToBeClickable);
                case "address-tab" ->
                        uiActions.click(UIActions.LocatorType.id, "address_form_clear", UIActions.ExplicitWaitCondition.elementToBeClickable);
                default -> Assert.fail("Access key Tab isn't supported, As it contains no clear button " + activeTab);

            }
        } catch (Exception e) {
            log.error("The Clear Button isn't clickable");
            throw e;
        }

    }

    public void selectTab(tabs tabName) {
        switch (tabName) {
            case northCoastTab ->
                    uiActions.click(UIActions.LocatorType.id, "north_coast_tab", UIActions.ExplicitWaitCondition.elementToBeClickable);
            case addressTab ->
                    uiActions.click(UIActions.LocatorType.id, "address-tab", UIActions.ExplicitWaitCondition.elementToBeClickable);
            case accessKeyTab ->
                    uiActions.click(UIActions.LocatorType.id, "access_key_tab", UIActions.ExplicitWaitCondition.elementToBeClickable);
        }
    }

    public void enterAccessKey(String accessKey) throws InterruptedException {
        uiActions.sendKeys(UIActions.LocatorType.xPath, "//input[@id='access_key']", UIActions.ExplicitWaitCondition.elementToBeClickable, accessKey);
    }

    public enum tabs {
        northCoastTab,
        addressTab,
        accessKeyTab
    }

    public void selectGovernorateId(String governorateId) {
        WebElement unitList = uiActions.findWebElement(UIActions.LocatorType.id, "governorate_id_input", UIActions.ExplicitWaitCondition.elementToBeClickable);
        uiActions.sendKeys(unitList, governorateId);
        uiActions.waitForAttributeContainsValueOrFail(unitList, "aria-activedescendant", "react", 10);
        uiActions.enterKeyCombination(Keys.ENTER);

        try {
            uiActions.findWebElement(UIActions.LocatorType.xPath, "//div[@id='governorate_id']//div[normalize-space(text())='" + governorateId + "']", UIActions.ExplicitWaitCondition.visibilityOfElement);
            log.info("Governorate ID '{}' was successfully selected.", governorateId);
        } catch (Exception e) {
            log.error("Failed to select Governorate ID '{}'. It may not exist in the list or was selected incorrectly.", governorateId);
            throw e;
        }
    }

    public void selectDistrictId(String districtId) {
        WebElement unitList = uiActions.findWebElement(UIActions.LocatorType.id, "district_id_input", UIActions.ExplicitWaitCondition.elementToBeClickable);
        uiActions.sendKeys(unitList, districtId);
        uiActions.waitForAttributeContainsValueOrFail(unitList, "aria-activedescendant", "react", 10);
        uiActions.enterKeyCombination(Keys.ENTER);

        try {
            uiActions.findWebElement(UIActions.LocatorType.xPath, "//div[@id='district_id']//div[normalize-space(text())='" + districtId + "']", UIActions.ExplicitWaitCondition.visibilityOfElement);
            log.info("District ID '{}' was successfully selected.", districtId);
        } catch (Exception e) {
            log.error("Failed to select District ID '{}'. It may not exist in the list or was selected incorrectly.", districtId);
            throw e;
        }
    }

    public void selectSubdistrictId(String subdistrictId) {
        WebElement unitList = uiActions.findWebElement(UIActions.LocatorType.id, "village_id_input", UIActions.ExplicitWaitCondition.elementToBeClickable);
        uiActions.sendKeys(unitList, subdistrictId);
        uiActions.waitForAttributeContainsValueOrFail(unitList, "aria-activedescendant", "react", 10);
        uiActions.enterKeyCombination(Keys.ENTER);

        try {
            uiActions.findWebElement(UIActions.LocatorType.xPath, "//div[@id='village_id']//div[normalize-space(text())='" + subdistrictId + "']", UIActions.ExplicitWaitCondition.visibilityOfElement);
            log.info("Subdistrict ID '{}' was successfully selected.", subdistrictId);
        } catch (Exception e) {
            log.error("Failed to select Subdistrict ID '{}'. It may not exist in the list or was selected incorrectly.", subdistrictId);
            throw e;
        }
    }

    public void selectStreetId(String streetId) {
        WebElement unitList = uiActions.findWebElement(UIActions.LocatorType.id, "street_id_input", UIActions.ExplicitWaitCondition.elementToBeClickable);
        uiActions.sendKeys(unitList, streetId);
        uiActions.waitForAttributeContainsValueOrFail(unitList, "aria-activedescendant", "react", 10);
        uiActions.enterKeyCombination(Keys.ENTER);

        try {
            uiActions.findWebElement(UIActions.LocatorType.xPath, "//div[@id='street_id']//div[normalize-space(text())='" + streetId + "']", UIActions.ExplicitWaitCondition.visibilityOfElement);
            log.info("Street ID '{}' was successfully selected.", streetId);
        } catch (Exception e) {
            log.error("Failed to select Street ID '{}'. It may not exist in the list or was selected incorrectly.", streetId);
            throw e;
        }
    }

    public void assertErrorMessageAppear() {
        try {
            WebElement errorMessage = uiActions.findWebElement(UIActions.LocatorType.css, "[role='alert']", UIActions.ExplicitWaitCondition.visibilityOfElement);
            log.info("\nAssertion Passed, The Error message '{}' is displayed.\n", errorMessage.getText());
        } catch (Exception e) {
            Assert.fail("Test case Failed!, The Error message isn't displayed as expected");

        }

    }

    public void assertClearButtonFunctionality() {
        String activeTab = uiActions.getElementAttribute(UIActions.LocatorType.css, "[class='nav-link active']", UIActions.ExplicitWaitCondition.visibilityOfElement, "id");
        try {
            switch (activeTab) {
                case "north_coast_tab" ->
                {
                    List<String> nonResetFields = new ArrayList<>();
                    if (isFieldVisibleAndNotEmpty("//*[@id='place_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("Village Name");
                    }
                    if (isFieldVisibleAndNotEmpty("//*[@id='build_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("Building ID");
                    }
                    if (isFieldVisibleAndNotEmpty("//*[@id='part_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("Floor ID");
                    }
                    if (isFieldVisibleAndNotEmpty("//*[@id='unit_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("Unit ID");
                    }
                    if (!nonResetFields.isEmpty()) {
                        Assert.fail("Clear button did not reset the following fields: " + String.join(", ", nonResetFields) + ".");
                    }
                }
                case "address-tab" ->
                {
                    List<String> nonResetFields = new ArrayList<>();
                    if (isFieldVisibleAndNotEmpty("//*[@id='governorate_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("Governorate ID");
                    }
                    if (isFieldVisibleAndNotEmpty("//*[@id='district_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("District ID");
                    }
                    if (isFieldVisibleAndNotEmpty("//*[@id='village_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("SubDistrict ID");
                    }
                    if (isFieldVisibleAndNotEmpty("//*[@id='street_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("Street ID");
                    }
                    if (isFieldVisibleAndNotEmpty("//*[@id='build_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("Building ID");
                    }
                    if (isFieldVisibleAndNotEmpty("//*[@id='part_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("Floor ID");
                    }
                    if (isFieldVisibleAndNotEmpty("//*[@id='unit_id']//div[@class='css-16iv56l-singleValue']")) {
                        nonResetFields.add("Unit ID");
                    }
                    if (!nonResetFields.isEmpty()) {
                        Assert.fail("Clear button did not reset the following fields: " + String.join(", ", nonResetFields) + ".");
                    }
                }
                case "access_key_tab" -> Assert.fail("Access key Tab isn't supported, As it contains no clear button " + activeTab);

            }

        } catch (Exception e) {
            log.info("Error has happened");
            throw e;
        }
        log.info("\nAssertion Passed, Clear button is working as expected.\n");

    }

    /*  In a try-catch attempting find the element if exist and not null then it returns true to fail the main fun
        otherwise it returns false as the element isn't exist so clear works as expected*/
    private boolean isFieldVisibleAndNotEmpty(String xPath) {
        try {
            WebElement element = uiActions.tryFindElement(
                    UIActions.LocatorType.xPath,
                    xPath,
                    UIActions.ExplicitWaitCondition.none
            );
            return element != null && !element.getText().trim().isEmpty();
        } catch (Exception e) {
            // Element not found or not visible — treat as cleared
            return false;
        }
    }


}


