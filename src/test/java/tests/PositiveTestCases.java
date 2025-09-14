package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utility.AllureLog4jListener;
import utility.AnnotationTransformer;
import utility.UsersManager;

import static java.lang.invoke.MethodHandles.lookup;

@Listeners({AllureLog4jListener.class, AnnotationTransformer.class})
public class PositiveTestCases extends BaseTest {


    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    @Description("This testcase aim is to ensure that the user can make a payment through NorthCoast tab " +
            "and checks the record is created and successfully added to the list with the submitted entered data.")
    @Feature("PositiveScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 1)
    public void TC1_userCanPayByNorthCoast() throws Exception {
        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);
        homePage.set(new HomePage(20));
        homePage.get().selectPage("الدفع الإلكترونى");

        loginPage.set(new LoginPage(20));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(20));
        paymentPage.get().clickPayNewInvoiceButton();

        paymentDetailsPage.set(new PaymentDetailsPage(20));
        paymentDetailsPage.get().selectVillage(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("Village").toString());
        paymentDetailsPage.get().selectBuildingId(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("BuildingId").toString(), true);
        paymentDetailsPage.get().selectFloorId(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("selectFloorId").toString(), true);
        paymentDetailsPage.get().selectUnitId(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("UnitId").toString(), true);
        paymentDetailsPage.get().clickSubmitButton();

        confirmationPage.set(new UserConfirmationPage(20));
        confirmationPage.get().clickConfirm();

        finalConfirmationPage.set(new FinalConfirmationPage(20));
        String nationalityStr = jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("Nationality").toString();
        finalConfirmationPage.get().selectNationality(FinalConfirmationPage.Nationality.valueOf(nationalityStr));
        finalConfirmationPage.get().enterPassportOrNationalId(FinalConfirmationPage.Nationality.valueOf(nationalityStr),
                jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("PassportNationalId").toString());
        finalConfirmationPage.get().enterMobileNo(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("MobileNo").toString());
        finalConfirmationPage.get().clickSubmitButton();

        finalPaymentPage.set(new FinalPaymentPage(20));
        finalPaymentPage.get().enterPayerName(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("PayerName").toString());
        finalPaymentPage.get().enterAmount(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("Amount").toString());
        finalPaymentPage.get().clickPayNow();

        eFinancePaymentPage.set(new eFinancePaymentPage(30));
        eFinancePaymentPage.get().selectPaymentWay(pages.eFinancePaymentPage.paymentWay.cardsLabel);
        eFinancePaymentPage.get().enterCardNo(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("CardNo").toString());
        eFinancePaymentPage.get().enterSecurityCode(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("SecurityCode").toString());
        eFinancePaymentPage.get().selectExpiryMonth((int) Double.parseDouble(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("ExpiryMonth").toString()));
        eFinancePaymentPage.get().selectExpiryYear((int) Double.parseDouble(jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("ExpiryYear").toString()));
        eFinancePaymentPage.get().clickPayButton();

        paymentPage.get().assertMessageShownAsPaymentDoneSuccessfully(softAssert.get());
        paymentPage.get().assertPaymentRecordAddedToList(
                softAssert.get(),
                jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("PayerName").toString(),
                jsonFileManager.get().getKeyAndValueByKey("TC1_userCanPayByNorthCoast").get("Amount").toString());
        softAssert.get().assertAll();
    }


    @Description("This testcase aim is to ensure that the user can make a payment through AccessKey tab " +
            "and checks the record is created and successfully added to the list with the submitted entered data.")
    @Feature("PositiveScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 2)
    public void TC2_userCanPayByAccessKey() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);
        homePage.set(new HomePage(20));
        homePage.get().selectPage("الدفع الإلكترونى");

        loginPage.set(new LoginPage(20));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(20));
        paymentPage.get().clickPayNewInvoiceButton();

        paymentDetailsPage.set(new PaymentDetailsPage(20));
        paymentDetailsPage.get().selectTab(PaymentDetailsPage.tabs.accessKeyTab);
        paymentDetailsPage.get().enterAccessKey(
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("AccessKey").toString());
        paymentDetailsPage.get().clickSubmitButton();

        confirmationPage.set(new UserConfirmationPage(20));
        confirmationPage.get().clickConfirm();

        finalConfirmationPage.set(new FinalConfirmationPage(20));
        String nationalityStr = jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("Nationality").toString();
        finalConfirmationPage.get().selectNationality(FinalConfirmationPage.Nationality.valueOf(nationalityStr));
        finalConfirmationPage.get().enterPassportOrNationalId(
                FinalConfirmationPage.Nationality.valueOf(nationalityStr),
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("PassportNationalId").toString());
        finalConfirmationPage.get().enterMobileNo(
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("MobileNo").toString());
        finalConfirmationPage.get().clickSubmitButton();

        finalPaymentPage.set(new FinalPaymentPage(20));
        finalPaymentPage.get().enterPayerName(
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("PayerName").toString());
        finalPaymentPage.get().enterAmount(
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("Amount").toString());
        finalPaymentPage.get().clickPayNow();

        eFinancePaymentPage.set(new eFinancePaymentPage(30));
        eFinancePaymentPage.get().selectPaymentWay(pages.eFinancePaymentPage.paymentWay.cardsLabel);
        eFinancePaymentPage.get().enterCardNo(
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("CardNo").toString());
        eFinancePaymentPage.get().enterSecurityCode(
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("SecurityCode").toString());
        eFinancePaymentPage.get().selectExpiryMonth(
                (int) Double.parseDouble(jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("ExpiryMonth").toString()));
        eFinancePaymentPage.get().selectExpiryYear(
                (int) Double.parseDouble(jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("ExpiryYear").toString()));
        eFinancePaymentPage.get().clickPayButton();

        paymentPage.get().assertMessageShownAsPaymentDoneSuccessfully(softAssert.get());
        paymentPage.get().assertPaymentRecordAddedToList(
                softAssert.get(),
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("PayerName").toString(),
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("Amount").toString());
        softAssert.get().assertAll();
    }


    @Description("This testcase aim is to ensure that the user can make a payment through Address tab " +
            "and checks the record is created and successfully added to the list with the submitted entered data.")
    @Feature("PositiveScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void TC3_userCanPayThroughAddressTab() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());

        homePage.set(new HomePage(20));
        homePage.get().selectPage("الدفع الإلكترونى");
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);
        loginPage.set(new LoginPage(20));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(20));
        paymentPage.get().clickPayNewInvoiceButton();

        paymentDetailsPage.set(new PaymentDetailsPage(20));
        paymentDetailsPage.get().selectTab(PaymentDetailsPage.tabs.addressTab);
        paymentDetailsPage.get().selectGovernorateId(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("GovernorateId").toString());
        paymentDetailsPage.get().selectDistrictId(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("DistrictId").toString());
        paymentDetailsPage.get().selectSubdistrictId(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("SubdistrictId").toString());
        paymentDetailsPage.get().selectStreetId(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("StreetId").toString());
        paymentDetailsPage.get().selectBuildingId(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("BuildingId").toString(), false);
        paymentDetailsPage.get().selectFloorId(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("selectFloorId").toString(), false);
        paymentDetailsPage.get().selectUnitId(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("UnitId").toString(), false);
        paymentDetailsPage.get().clickSubmitButton();

        confirmationPage.set(new UserConfirmationPage(20));
        confirmationPage.get().clickConfirm();

        finalConfirmationPage.set(new FinalConfirmationPage(20));
        String nationalityStr = jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("Nationality").toString();
        finalConfirmationPage.get().selectNationality(FinalConfirmationPage.Nationality.valueOf(nationalityStr));
        finalConfirmationPage.get().enterPassportOrNationalId(
                FinalConfirmationPage.Nationality.valueOf(nationalityStr),
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("PassportNationalId").toString());
        finalConfirmationPage.get().enterMobileNo(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("MobileNo").toString());
        finalConfirmationPage.get().clickSubmitButton();

        finalPaymentPage.set(new FinalPaymentPage(20));
        finalPaymentPage.get().enterPayerName(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("PayerName").toString());
        finalPaymentPage.get().enterAmount(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("Amount").toString());
        finalPaymentPage.get().clickPayNow();

        eFinancePaymentPage.set(new eFinancePaymentPage(30));
        eFinancePaymentPage.get().selectPaymentWay(pages.eFinancePaymentPage.paymentWay.cardsLabel);
        eFinancePaymentPage.get().enterCardNo(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("CardNo").toString());
        eFinancePaymentPage.get().enterSecurityCode(
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("SecurityCode").toString());
        eFinancePaymentPage.get().selectExpiryMonth(
                (int) Double.parseDouble(jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("ExpiryMonth").toString()));
        eFinancePaymentPage.get().selectExpiryYear(
                (int) Double.parseDouble(jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("ExpiryYear").toString()));
        eFinancePaymentPage.get().clickPayButton();

        paymentPage.get().assertMessageShownAsPaymentDoneSuccessfully(softAssert.get());
        paymentPage.get().assertPaymentRecordAddedToList(
                softAssert.get(),
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("PayerName").toString(),
                jsonFileManager.get().getKeyAndValueByKey("TC3_userCanPayThroughAddressTab").get("Amount").toString());

        softAssert.get().assertAll();
    }

    @Description("This testcase aim is to ensure that the user can rest all the fields on NorthCoast tab by clicking on clear button.")
    @Feature("PositiveScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 4)
    public void TC4_userRestAllFieldsOnClickingClearButtonAtNorthCoastTab() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());

        homePage.set(new HomePage(20));
        homePage.get().selectPage("الدفع الإلكترونى");
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);

        loginPage.set(new LoginPage(20));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(20));
        paymentPage.get().clickPayNewInvoiceButton();

        paymentDetailsPage.set(new PaymentDetailsPage(20));
        paymentDetailsPage.get().selectVillage(
                jsonFileManager.get().getKeyAndValueByKey("TC4_userRestAllFieldsOnClickingClearButtonAtNorthCoastTab").get("Village").toString());
        paymentDetailsPage.get().selectBuildingId(
                jsonFileManager.get().getKeyAndValueByKey("TC4_userRestAllFieldsOnClickingClearButtonAtNorthCoastTab").get("BuildingId").toString(), true);
        paymentDetailsPage.get().selectFloorId(
                jsonFileManager.get().getKeyAndValueByKey("TC4_userRestAllFieldsOnClickingClearButtonAtNorthCoastTab").get("selectFloorId").toString(), true);
        paymentDetailsPage.get().selectUnitId(
                jsonFileManager.get().getKeyAndValueByKey("TC4_userRestAllFieldsOnClickingClearButtonAtNorthCoastTab").get("UnitId").toString(), true);
        paymentDetailsPage.get().clickClearButton();
        paymentDetailsPage.get().assertClearButtonFunctionality();
    }


    @Description("This testcase aim is to ensure that the user can rest all the fields on Address tab by clicking on clear button.")
    @Feature("PositiveScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 5)
    public void TC5_userRestAllFieldsOnClickingClearButtonAtAddressTab() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);
        homePage.set(new HomePage(20));
        homePage.get().selectPage("الدفع الإلكترونى");

        loginPage.set(new LoginPage(20));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(20));
        paymentPage.get().clickPayNewInvoiceButton();

        paymentDetailsPage.set(new PaymentDetailsPage(20));
        paymentDetailsPage.get().selectTab(PaymentDetailsPage.tabs.addressTab);
        paymentDetailsPage.get().selectGovernorateId(
                jsonFileManager.get().getKeyAndValueByKey("TC5_userRestAllFieldsOnClickingClearButtonAtAddressTab").get("GovernorateId").toString());
        paymentDetailsPage.get().selectDistrictId(
                jsonFileManager.get().getKeyAndValueByKey("TC5_userRestAllFieldsOnClickingClearButtonAtAddressTab").get("DistrictId").toString());
        paymentDetailsPage.get().selectSubdistrictId(
                jsonFileManager.get().getKeyAndValueByKey("TC5_userRestAllFieldsOnClickingClearButtonAtAddressTab").get("SubdistrictId").toString());
        paymentDetailsPage.get().selectStreetId(
                jsonFileManager.get().getKeyAndValueByKey("TC5_userRestAllFieldsOnClickingClearButtonAtAddressTab").get("StreetId").toString());
        paymentDetailsPage.get().selectBuildingId(
                jsonFileManager.get().getKeyAndValueByKey("TC5_userRestAllFieldsOnClickingClearButtonAtAddressTab").get("BuildingId").toString(), false);
        paymentDetailsPage.get().selectFloorId(
                jsonFileManager.get().getKeyAndValueByKey("TC5_userRestAllFieldsOnClickingClearButtonAtAddressTab").get("selectFloorId").toString(), false);
        paymentDetailsPage.get().selectUnitId(
                jsonFileManager.get().getKeyAndValueByKey("TC5_userRestAllFieldsOnClickingClearButtonAtAddressTab").get("UnitId").toString(), false);
        paymentDetailsPage.get().clickClearButton();
        paymentDetailsPage.get().assertClearButtonFunctionality();
    }


    @Description("This testcase aim is to ensure that the user can search using a specific unitId and all the result will have the same Id")
    @Feature("PositiveScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 6)
    public void TC6_userCanSearchForSpecificUnitId() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());

        homePage.set(new HomePage(20));
        homePage.get().selectPage("الدفع الإلكترونى");
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);

        loginPage.set(new LoginPage(20));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(20));
        paymentPage.get().enterUnitCode(
                jsonFileManager.get().getKeyAndValueByKey("TC6_userCanSearchForSpecificUnitId").get("AccessKey").toString());
        paymentPage.get().clickSearchButton();

        Thread.sleep(2000); //Can't be changed.
        paymentPage.get().assertPaymentCodeShownAsEntered(
                softAssert.get(),
                jsonFileManager.get().getKeyAndValueByKey("TC6_userCanSearchForSpecificUnitId").get("AccessKey").toString());
        softAssert.get().assertAll();
    }


    @Description("This testcase aims to ensure that the user can specifically select a record from the first page," +
            "verify that the FinalPaymentPage opens with the correct unitId appeared in the list when clicking on the 'PayForSameUnitButton' button', " +
            "and checks the record is created and successfully added to the list with the submitted data and the expected status.")
    @Feature("PositiveScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 7)
    public void TC7_userCanSelectPaymentAndPayForTheSameUnit() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());

        homePage.set(new HomePage(20));
        homePage.get().selectPage("الدفع الإلكترونى");
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);

        loginPage.set(new LoginPage(20));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(20));
        String selectedUnitCode = paymentPage.get().getSelectedUnitCode(
                Integer.parseInt(jsonFileManager.get().getKeyAndValueByKey("TC7_userCanSelectPaymentAndPayForTheSameUnit").get("selectedRowNo").toString()));
        String actionButton = jsonFileManager.get().getKeyAndValueByKey("TC7_userCanSelectPaymentAndPayForTheSameUnit").get("actionButton").toString();
        paymentPage.get().clickOnDetailsButton(
                PaymentPage.Buttons.valueOf(actionButton),
                Integer.parseInt(jsonFileManager.get().getKeyAndValueByKey("TC7_userCanSelectPaymentAndPayForTheSameUnit").get("selectedRowNo").toString()));

        specificPaymentDetails.set(new SpecificPaymentDetails(20));
        specificPaymentDetails.get().clickPayForSameUnitButton();

        finalPaymentPage.set(new FinalPaymentPage(20));
        finalPaymentPage.get().assertUnitCodeIsSameAsSelected(selectedUnitCode);
        finalPaymentPage.get().enterPayerName(
                jsonFileManager.get().getKeyAndValueByKey("TC7_userCanSelectPaymentAndPayForTheSameUnit").get("PayerName").toString());
        finalPaymentPage.get().enterAmount(
                jsonFileManager.get().getKeyAndValueByKey("TC7_userCanSelectPaymentAndPayForTheSameUnit").get("Amount").toString());
        finalPaymentPage.get().clickPayNow();

        eFinancePaymentPage.set(new eFinancePaymentPage(30));
        eFinancePaymentPage.get().selectPaymentWay(pages.eFinancePaymentPage.paymentWay.cardsLabel);
        eFinancePaymentPage.get().enterCardNo(
                jsonFileManager.get().getKeyAndValueByKey("TC7_userCanSelectPaymentAndPayForTheSameUnit").get("CardNo").toString());
        eFinancePaymentPage.get().enterSecurityCode(
                jsonFileManager.get().getKeyAndValueByKey("TC7_userCanSelectPaymentAndPayForTheSameUnit").get("SecurityCode").toString());
        eFinancePaymentPage.get().selectExpiryMonth(
                (int) Double.parseDouble(jsonFileManager.get().getKeyAndValueByKey("TC7_userCanSelectPaymentAndPayForTheSameUnit").get("ExpiryMonth").toString()));
        eFinancePaymentPage.get().selectExpiryYear(
                (int) Double.parseDouble(jsonFileManager.get().getKeyAndValueByKey("TC7_userCanSelectPaymentAndPayForTheSameUnit").get("ExpiryYear").toString()));
        eFinancePaymentPage.get().clickPayButton();

        softAssert.set(new SoftAssert());
        paymentPage.get().assertMessageShownAsPaymentDoneSuccessfully(softAssert.get());
        paymentPage.get().assertPaymentRecordAddedToList(
                softAssert.get(),
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("PayerName").toString(),
                jsonFileManager.get().getKeyAndValueByKey("TC2_userCanPayByAccessKey").get("Amount").toString());
        softAssert.get().assertAll();


    }


    @Description("This testcase aims to ensure that the user can specifically select a record from the first page," +
            "verify that the PrintPage opens with the correct unitId appeared in the list when clicking on the 'print' button, " +
            "and confirm that two PDF files are downloaded to the selected directory after being cleared with the expected filenames when clicking on the 'DownloadPDF' button.")
    @Feature("PositiveScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 8)
    public void TC8_userCanSelectPaymentAndDownloadPDF() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());

        homePage.set(new HomePage(20));
        homePage.get().selectPage("الدفع الإلكترونى");
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);

        loginPage.set(new LoginPage(20));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(20));
        String selectedUnitCode = paymentPage.get().getSelectedUnitCode(
                Integer.parseInt(jsonFileManager.get().getKeyAndValueByKey("TC8_userCanSelectPaymentAndDownloadPDF").get("selectedRowNo").toString()));
        String actionButton = jsonFileManager.get().getKeyAndValueByKey("TC8_userCanSelectPaymentAndDownloadPDF").get("actionButton").toString();
        paymentPage.get().clickOnDetailsButton(
                PaymentPage.Buttons.valueOf(actionButton),
                Integer.parseInt(jsonFileManager.get().getKeyAndValueByKey("TC8_userCanSelectPaymentAndDownloadPDF").get("selectedRowNo").toString()));

        paymentPrintPage.set(new PaymentPrintPage(20));
        paymentPrintPage.get().assertUnitCodeIsSameAsSelected(selectedUnitCode);
        paymentPrintPage.get().clearDownloadDirectory(configurationLoader.get().getValue("downloadDirectory"));
        paymentPrintPage.get().clickDownloadPDF();
        paymentPrintPage.get().assertFilesDownloaded(configurationLoader.get().getValue("downloadDirectory"));

    }


}

