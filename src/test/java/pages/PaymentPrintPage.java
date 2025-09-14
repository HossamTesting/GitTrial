package pages;

import actions.UIActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.io.File;

import static java.lang.invoke.MethodHandles.lookup;

public class PaymentPrintPage {

    UIActions uiActions;
    private static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public PaymentPrintPage(int wait) throws Exception {
        log.info("Initializing PaymentPrintPage object.");
        uiActions = new UIActions(wait);

    }

    public void clickDownloadPDF() {
        uiActions.click(UIActions.LocatorType.xPath, "//*[@type='button' and contains(text(), 'تحميل PDF')]", UIActions.ExplicitWaitCondition.elementToBeClickable);
    }

    public void assertUnitCodeIsSameAsSelected(String actualUnitCode) {
        try {
            uiActions.findWebElement(UIActions.LocatorType.xPath, "//*[contains(text(), '" + actualUnitCode + "')]", UIActions.ExplicitWaitCondition.visibilityOfElement);
            log.info("\nAssertion Passed, The unitCode shown in the PaymentPrintPage is the same as selected in the list.\n");
        } catch (Exception e) {
            log.error("Assertion Failed, The unitCode shown in the PaymentPrintPage isn't the same as selected in the list or not visible in the page.");
            throw e;
        }
    }

    public void assertFilesDownloaded(String downloadDir) {
        uiActions.waitUntilTwoPdfFilesAreDownloaded(downloadDir,10); //This wait until the files are downloaded and have the format of pdf
        String[] expectedFileNames = {
                "إيصال السداد الإلكتروني.pdf",
                "إيصال سداد الضريبة العقارية.pdf"
        };

        for (String fileName : expectedFileNames) {
            File downloadedFile = new File(downloadDir, fileName);

            if (!downloadedFile.exists()) {
                Assert.fail("File '" + fileName + "' was not found in directory: " + downloadDir);
            }
            log.info("Assertion Passed, File '{}' downloaded successfully and is a valid PDF.", fileName);
        }
    }

    public void clearDownloadDirectory(String downloadDirPath) {
        File downloadDir = new File(downloadDirPath);
        if (!downloadDir.exists() || !downloadDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid download directory: " + downloadDirPath);
        }
        File[] files = downloadDir.listFiles();


        if (files != null) {
            if (files.length == 0) {
                log.info("The Directory is empty.");
                return;
            }

            for (File file : files) {
                if (!file.delete()) {
                    log.error("Failed to delete file: '{}'", file.getName());
                    Assert.fail("The file isn't cleared successfully.!");
                }
            }
        }
        log.info("Download directory cleared: '{}'.", downloadDirPath);
    }


}