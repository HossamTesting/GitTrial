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
import pages.*;
import utility.AllureLog4jListener;
import utility.AnnotationTransformer;

import static java.lang.invoke.MethodHandles.lookup;

@Listeners({AllureLog4jListener.class, AnnotationTransformer.class})
public class NegativeTestCases extends BaseTest {

    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    @Description("This testcase aim is to ensure that when a user enters incorrect data in the NorthCoast tab, " + "System prevents payment submission and displays an appropriate error message.")
    @Feature("NegativeScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 9)
    public void TC9_userEntersWrongDataAtNorthCoastTab() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);
        homePage.set(new HomePage(10));
        homePage.get().selectPage("الدفع الإلكترونى");

        loginPage.set(new LoginPage(10));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(10));
        paymentPage.get().clickPayNewInvoiceButton();

        paymentDetailsPage.set(new PaymentDetailsPage(10));
        paymentDetailsPage.get().selectVillage(jsonFileManager.get().getKeyAndValueByKey("TC9_userEntersWrongDataAtNorthCoastTab").get("Village").toString());
        paymentDetailsPage.get().selectBuildingId(jsonFileManager.get().getKeyAndValueByKey("TC9_userEntersWrongDataAtNorthCoastTab").get("BuildingId").toString(), true);
        paymentDetailsPage.get().selectFloorId(jsonFileManager.get().getKeyAndValueByKey("TC9_userEntersWrongDataAtNorthCoastTab").get("selectFloorId").toString(), true);
        paymentDetailsPage.get().selectUnitId(jsonFileManager.get().getKeyAndValueByKey("TC9_userEntersWrongDataAtNorthCoastTab").get("UnitId").toString(), true);
        paymentDetailsPage.get().clickSubmitButton();
        paymentDetailsPage.get().assertErrorMessageAppear();
    }


    @Description("This testcase aim is to ensure that when a user enters incorrect data in the AccessKey tab, " + "System prevents payment submission and displays an appropriate error message.")
    @Feature("NegativeScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 10)
    public void TC10_userEntersWrongDataAtAccessKeyTab() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());

        homePage.set(new HomePage(10));
        homePage.get().selectPage("الدفع الإلكترونى");
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);
        loginPage.set(new LoginPage(10));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(10));
        paymentPage.get().clickPayNewInvoiceButton();

        paymentDetailsPage.set(new PaymentDetailsPage(10));
        paymentDetailsPage.get().selectTab(PaymentDetailsPage.tabs.accessKeyTab);
        paymentDetailsPage.get().enterAccessKey(jsonFileManager.get().getKeyAndValueByKey("TC10_userEntersWrongDataAtAccessKeyTab").get("AccessKey").toString());
        paymentDetailsPage.get().clickSubmitButton();
        paymentDetailsPage.get().assertErrorMessageAppear();
    }


    @Description("This testcase aim is to ensure that when a user enters incorrect data in the Address tab, " + "System prevents payment submission and displays an appropriate error message.")
    @Feature("NegativeScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 11)
    public void TC11_userEntersWrongDataAtAddressTab() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);
        homePage.set(new HomePage(10));
        homePage.get().selectPage("الدفع الإلكترونى");

        loginPage.set(new LoginPage(10));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(10));
        paymentPage.get().clickPayNewInvoiceButton();

        paymentDetailsPage.set(new PaymentDetailsPage(10));
        paymentDetailsPage.get().selectTab(PaymentDetailsPage.tabs.addressTab);
        paymentDetailsPage.get().selectGovernorateId(jsonFileManager.get().getKeyAndValueByKey("TC11_userEntersWrongDataAtAddressTab").get("GovernorateId").toString());
        paymentDetailsPage.get().selectDistrictId(jsonFileManager.get().getKeyAndValueByKey("TC11_userEntersWrongDataAtAddressTab").get("DistrictId").toString());
        paymentDetailsPage.get().selectSubdistrictId(jsonFileManager.get().getKeyAndValueByKey("TC11_userEntersWrongDataAtAddressTab").get("SubdistrictId").toString());
        paymentDetailsPage.get().selectStreetId(jsonFileManager.get().getKeyAndValueByKey("TC11_userEntersWrongDataAtAddressTab").get("StreetId").toString());
        paymentDetailsPage.get().selectBuildingId(jsonFileManager.get().getKeyAndValueByKey("TC11_userEntersWrongDataAtAddressTab").get("BuildingId").toString(), false);
        paymentDetailsPage.get().selectFloorId(jsonFileManager.get().getKeyAndValueByKey("TC11_userEntersWrongDataAtAddressTab").get("selectFloorId").toString(), false);
        paymentDetailsPage.get().selectUnitId(jsonFileManager.get().getKeyAndValueByKey("TC11_userEntersWrongDataAtAddressTab").get("UnitId").toString(), false);
        paymentDetailsPage.get().clickSubmitButton();
        paymentDetailsPage.get().assertErrorMessageAppear();
    }


    @Description("This testcase aim is to ensure that when user can search using a wrong unitId no records shown in the table.")
    @Feature("NegativeScenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 12)
    public void TC12_userCanSearchWithWrongUnitIdInSearchBar() throws Exception {

        ThreadContext.put("TestName", methodName.get());
        log.info("************ Starting method: '{}' ************", methodName.get());

        homePage.set(new HomePage(10));
        homePage.get().selectPage("الدفع الإلكترونى");
        String userId = userManager.acquireUser();
        acquiredUserId.set(userId);
        loginPage.set(new LoginPage(10));
        loginPage.get().enterId(userId);
        loginPage.get().enterPassword(configurationLoader.get().getValue("password"));
        loginPage.get().clickLoginButton();

        paymentPage.set(new PaymentPage(10));
        paymentPage.get().enterUnitCode(jsonFileManager.get().getKeyAndValueByKey("TC12_userCanSearchWithWrongUnitIdInSearchBar").get("AccessKey").toString());
        paymentPage.get().clickSearchButton();
        Thread.sleep(2000); //Can't be changed.
        paymentPage.get().assertNoPaymentsShown();
    }
}
