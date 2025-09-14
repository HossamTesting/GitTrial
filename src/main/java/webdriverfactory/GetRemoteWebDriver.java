//package webdriverfactory;
//
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.edge.EdgeOptions;
//import org.openqa.selenium.firefox.FirefoxOptions;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import java.net.MalformedURLException;
//import java.net.URL;
//import static java.lang.invoke.MethodHandles.lookup;
//
//public class GetRemoteWebDriver {
//
//    private static final Logger log = LogManager.getLogger(lookup().lookupClass());
//
//    public static RemoteWebDriver setupRemoteDriver(String browserName, String url ,String Plat, String... modes) throws Exception {
//        // Create options based on the browser name
//        switch (browserName.replaceAll("\\s+", "").toLowerCase()) {
//            case "chrome":
//
//                // Set any specific Chrome options here if needed
//               ChromeOptions options = GetChrome.setupChromeDriver(modes);
//               options.setPlatformName(Plat);
//
//                return new RemoteWebDriver(new URL(url), options);
//
//            case "firefox":
//                FirefoxOptions firefoxOptions = GetFirefox.setupFirefoxDriver(modes);
//                // Set any specific Firefox options here if needed
//                return new RemoteWebDriver(new URL(url), firefoxOptions);
//
//            case "edge":
//                EdgeOptions edgeOptions = GetEdge.setupEdgeDriver(modes);
//                edgeOptions.setPlatformName(Plat);
//                // Set any specific Edge options here if needed;
//                return new RemoteWebDriver(new URL(url), edgeOptions);
//
//            // Add more cases for other browsers if needed
//            default:
//                log.error("Unknown browser specified: '{}', Please use 'chrome', 'firefox', or 'edge'.", browserName);
//                throw new IllegalArgumentException("Unknown browser specified, Supported browsers are: chrome, firefox, edge.");
//        }
//    }
//    private static String getBrowserName(String browserName) {
//        // Clean the input by removing spaces and converting to lower case
//        return switch (browserName.replaceAll("\\s+", "").toLowerCase()) {
//
//            case "chrome", "googlechrome" -> "chrome";
//            case "edge", "microsoftedge"-> "MicrosoftEdge";
//            case "firefox" -> "firefox";
//            default ->{
//                throw new RuntimeException("The entered browser name isn't exist '"+browserName+"'");
//            }
//        };
//    }
//}
//
