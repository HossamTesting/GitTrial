package actions;

import webdriverfactory.GetWebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * BrowserActions is a utility class that provides common browser interactions
 * using Selenium WebDriver.<br>It handles browser navigation, alerts, tab and window management,
 * and frame switching operations, while logging each action.
 * <p>Methods in this class throw {@code NullPointerException} if the WebDriver is not initialized
 * (i.e., {@code GetWebDriver.getLocalDriver()} returns {@code null}).</p>
 *
 * @author Hossam Atef
 * @version 1.0
 */

public class BrowserActions {

    private static final Logger log = LogManager.getLogger(lookup().lookupClass());

    /**
     * Verifies that the WebDriver instance has been properly initialized.
     * <p>
     * This method is called at the beginning of any browser action
     * that requires an active WebDriver session.<br>If the WebDriver is {@code null},
     * it logs an error message and throws an {@link IllegalStateException}
     * to prevent further execution.
     * </p>
     *
     * @throws IllegalStateException if the WebDriver has not been initialized
     */
    private static void checkNullDriver() {
        if (GetWebDriver.getLocalDriver() == null) {
            log.error("WebDriver instance is null. WebDriver must be initialized before using browser actions.");
            throw new IllegalStateException("WebDriver has not been initialized.");
        }
    }

    /**
     * Returns a simple string description of the WebElement.
     *
     * @param element the WebElement to describe
     * @return string representation of the element
     */
    private static String describeElement(WebElement element) {
        try {
            return element.toString().replaceAll(".*-> ", "").replaceFirst("]", "");
        } catch (Exception e) {
            return "Unknown WebElement";
        }
    }

    //________________________________________________________________________________________________________________//
    // Navigation

    /**
     * Navigates to the specified URL.
     *
     * @param url URL to navigate to
     */
    public static void navigateToPage(String url) {
        checkNullDriver();
        if (url == null){throw new IllegalStateException("Please provide a valid url not a null value");}
        GetWebDriver.getLocalDriver().navigate().to(url);
        log.info("Navigated to URL: '{}'", url);

    }

    /**
     * Refreshes the current page.
     */
    public static void refreshPage() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().navigate().refresh();
        log.info("Page refreshed successfully.");
    }

    /**
     * Navigates forward in the browser history.
     */
    public static void navigateForward() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().navigate().forward();
        log.info("Navigated forward.");
    }

    /**
     * Navigates backward in the browser history.
     */
    public static void navigateBackward() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().navigate().back();
        log.info("Navigated backward.");
    }

    //________________________________________________________________________________________________________________//
    // Window Management

    /**
     * Maximizes the browser window to fill the screen.
     */
    public static void maximizeWindow() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().manage().window().maximize();
        log.info("Browser window maximized.");
    }

    /**
     * Minimizes the browser window.
     */
    public static void minimizeWindow() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().manage().window().minimize();
        log.info("Browser window minimized.");
    }

    /**
     * Sets the browser to full screen mode.
     */
    public static void fullscreen() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().manage().window().fullscreen();
        log.info("Browser set to fullscreen mode.");
    }

    /**
     * Sets the browser window to a specific width and height.
     *
     * @param width  the desired width of the browser window in pixels
     * @param height the desired height of the browser window in pixels
     */
    public static void setScreenSize(int width, int height) {
        checkNullDriver();
        Dimension dimension = new Dimension(width, height);
        GetWebDriver.getLocalDriver().manage().window().setSize(dimension);
        log.info("Browser window size set to width: '{}', height: '{}'.", width, height);
    }

    /**
     * Sets the position of the top-left corner of the browser window on the screen.
     *
     * @param x the horizontal position (in pixels) from the left of the screen
     * @param y the vertical position (in pixels) from the top of the screen
     */
    public static void setWindowPosition(int x, int y) {
        checkNullDriver();
        Point point = new Point(x, y);
        GetWebDriver.getLocalDriver().manage().window().setPosition(point);
        log.info("Browser window position set to x: '{}', y: '{}'.", x, y);
    }

    //________________________________________________________________________________________________________________//
    // Alerts

    /**
     * Switches to the currently active alert.
     *
     * @return Alert object
     */
    public static Alert switchToAlert() {
        checkNullDriver();
        Alert alert = GetWebDriver.getLocalDriver().switchTo().alert();
        log.info("Switched to alert.");
        return alert;
    }

    /**
     * Accepts the currently active alert.
     */
    public static void acceptAlert() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().alert().accept();
        log.info("Alert accepted.");
    }

    /**
     * Dismisses the currently active alert.
     */
    public static void dismissAlert() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().alert().dismiss();
        log.info("Alert dismissed.");
    }

    /**
     * Sends text to the currently active alert.
     *
     * @param text Text to send to the alert
     */
    public static void setAlertText(String text) {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().alert().sendKeys(text);
        log.info("Text '{}' entered into alert.", text);
    }

    /**
     * Retrieves the text from the currently active alert.
     *
     * @return Text from the alert
     */
    public static String getAlertText() {
        checkNullDriver();
        String text = GetWebDriver.getLocalDriver().switchTo().alert().getText();
        log.info("Retrieved alert text: '{}'.", text);
        return text;
    }

    //________________________________________________________________________________________________________________//
    // Windows/Tabs

    /**
     * Retrieves the current window\tab handler (identifier).
     *
     * @return Window or tab handle
     */
    public static String getWindowHandle() {
        checkNullDriver();
        String windowHandle = GetWebDriver.getLocalDriver().getWindowHandle();
        log.info("Current window/tab handler retrieved: '{}'.", windowHandle);
        return windowHandle;
    }

    /**
     * Retrieves all window or tab handles currently open by the WebDriver.
     *
     * @return List of window/tab handles
     */
    public static List<String> getWindowHandles() {
        checkNullDriver();
        List<String> handles = new ArrayList<>(GetWebDriver.getLocalDriver().getWindowHandles());
        log.info("Retrieved '{}' window/tab handle(s): '{}'.", handles.size(), handles);
        return handles;
    }

    /**
     * Switches to a browser window or tab using its handle.
     *
     * @param windowHandle Handle of the window or tab to switch to
     */
    public static void switchToWindow(String windowHandle) {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().window(windowHandle);
        log.info("Switched to window/tab with handle: '{}'.", windowHandle);
    }

    /**
     * Retrieves the title of the current browser window or tab.
     *
     * @return Title of the current window or tab
     */
    public static String getWindowTitle() {
        checkNullDriver();
        String title = GetWebDriver.getLocalDriver().getTitle();
        log.info("Retrieved current window/tab title: '{}'.", title);
        return title;
    }

    /**
     * Retrieves the current URL loaded in the active browser window.
     *
     * @return the URL of the currently loaded page as a String
     */

    public static String getURL() {
        checkNullDriver();
        String URL = GetWebDriver.getLocalDriver().getCurrentUrl();
        log.info("Retrieved currently loaded url: '{}'.", URL);
        return URL;
    }

    /**
     * Opens and switches to a new browser tab.
     */
    public static void openAndSwitchToNewTab() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().newWindow(WindowType.TAB);
        log.info("Opened and switched to a new browser tab.");
    }

    /**
     * Opens and switches to a new browser window.
     */
    public static void openAndSwitchToNewWindow() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().newWindow(WindowType.WINDOW);
        log.info("Opened and switched to a new browser window.");
    }

    /**
     * Close the current browser window/tab.
     */
    public static void closeWindow() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().close();
        log.info("Window closed.");
    }

    //________________________________________________________________________________________________________________//
    // Frames

    /**
     * Switches to a frame by its index.
     *
     * @param frameId Index of the frame
     */
    public static void switchToFrame(int frameId) {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().frame(frameId);
        log.info("Switched to frame with index: '{}'.", frameId);
    }

    /**
     * Switches to a frame by its name or ID.
     *
     * @param frameName Name or ID of the frame
     */
    public static void switchToFrame(String frameName) {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().frame(frameName);
        log.info("Switched to frame with name or ID: '{}'.", frameName);
    }

    /**
     * Switches to a frame using its WebElement reference.
     *
     * @param frameName Web element of the frame
     */
    public static void switchToFrame(WebElement frameName) {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().frame(frameName);
        log.info("Switched to frame with Element: '{}'.", describeElement(frameName));
    }

    /**
     * Switches back to the parent frame.
     */
    public static void switchToParentFrame() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().parentFrame();
        log.info("Switched to parent frame.");
    }

    /**
     * Switches back to the default content from a frame.
     */
    public static void switchToDefaultContent() {
        checkNullDriver();
        GetWebDriver.getLocalDriver().switchTo().defaultContent();
        log.info("Switched to default content.");
    }

}


