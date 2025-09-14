package tests;

import actions.BrowserActions;
import datadriven.ConfigLoader;
import datadriven.JsonFileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.*;
import utility.UsersManager;
import webdriverfactory.GetWebDriver;

import java.lang.reflect.Method;

import static java.lang.invoke.MethodHandles.lookup;

public class BaseTest {


    private static final Logger log = LogManager.getLogger(lookup().lookupClass());
    protected static ThreadLocal<ConfigLoader> configurationLoader = new ThreadLocal<>();
    protected ThreadLocal<JsonFileManager> jsonFileManager = new ThreadLocal<>();
    protected ThreadLocal<String> methodName = new ThreadLocal<>();
    protected ThreadLocal<SoftAssert> softAssert = new ThreadLocal<>();

    protected ThreadLocal<HomePage> homePage = new ThreadLocal<>();
    protected ThreadLocal<LoginPage> loginPage = new ThreadLocal<>();
    protected ThreadLocal<PaymentPage> paymentPage = new ThreadLocal<>();
    protected ThreadLocal<PaymentDetailsPage> paymentDetailsPage = new ThreadLocal<>();
    protected ThreadLocal<UserConfirmationPage> confirmationPage = new ThreadLocal<>();
    protected ThreadLocal<FinalConfirmationPage> finalConfirmationPage = new ThreadLocal<>();
    protected ThreadLocal<FinalPaymentPage> finalPaymentPage = new ThreadLocal<>();
    protected ThreadLocal<eFinancePaymentPage> eFinancePaymentPage = new ThreadLocal<>();
    protected ThreadLocal<SpecificPaymentDetails> specificPaymentDetails = new ThreadLocal<>();
    protected ThreadLocal<PaymentPrintPage> paymentPrintPage = new ThreadLocal<>();
    protected ThreadLocal<String> acquiredUserId = new ThreadLocal<>();
    protected static UsersManager userManager ;
    private static boolean usersInitialized = false;

    @BeforeMethod
    public void setup(Method method) throws Exception {
        ThreadContext.put("TestName", "setup_" + method.getName());
        methodName.set(method.getName());
        log.info("************ Starting method: setup ************");

        configurationLoader.set(new ConfigLoader("src/test/resources/Config.properties"));
        jsonFileManager.set(new JsonFileManager("src/test/resources/jsonNewData.json"));

        if (!usersInitialized) {
            userManager = new UsersManager();
            userManager.initialize(configurationLoader.get().getArrayValue("nationalId/Passport"));
            usersInitialized = true;
        }

        GetWebDriver.getInstance(
                configurationLoader.get().getValue("browserName"),
                configurationLoader.get().getArrayValue("browserModes")
        );

        BrowserActions.navigateToPage(configurationLoader.get().getValue("url"));
        softAssert.set(new SoftAssert());
    }

    @BeforeSuite
    public void restingJULBridge() {

        ThreadContext.put("TestName", "restingJULBridge");
        log.info("************ Starting method: restingJULBridge ************");
        org.apache.logging.log4j.jul.LogManager.getLogManager().reset();
    }


    @AfterMethod
    public void quitDriver() {
        ThreadContext.put("TestName", "quitDriver_" + methodName.get());
        log.info("************ Starting method: 'quitDriver' ************");
        String userId = acquiredUserId.get();
        if (userId != null) {
            userManager.releaseUser(userId);
        }
        GetWebDriver.quitDriver();
        clearThreadLocals();
    }

    private void clearThreadLocals() {
        softAssert.remove();
        methodName.remove();
        configurationLoader.remove();
        jsonFileManager.remove();
        homePage.remove();
        loginPage.remove();
        paymentPage.remove();
        paymentDetailsPage.remove();
        confirmationPage.remove();
        finalConfirmationPage.remove();
        finalPaymentPage.remove();
        eFinancePaymentPage.remove();
        specificPaymentDetails.remove();
        paymentPrintPage.remove();

    }
}
