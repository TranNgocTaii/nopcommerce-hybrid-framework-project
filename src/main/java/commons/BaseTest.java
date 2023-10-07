package commons;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.Reporter;
import utilities.logs.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private WebDriver driver;
    public WebDriver getDriverInstance() {
        return this.driver;
    }

    protected WebDriver getBrowserDriver(String browserName, String appURL){
        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
//            System.setProperty("webdriver.chrome.args","--disable-logging");
//            System.setProperty("webdriver.chrome.silentOutput","true");
//            File file = new File(GlobalConstants.PROJECT_PATH + "/browserExtensions/extension_2_0_13_0.crx");
            ChromeOptions options = new ChromeOptions();
            selectLanguageForChrome(options, "en");
            disableSavePasswordInChrome(options);
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-geolocation");
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//            options.addExtensions(file);
            options.setAcceptInsecureCerts(true);
            driver = new ChromeDriver(options);
        } else if (browserName.equals("h_chrome")){
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("window-size=1920x1080");
            driver = new ChromeDriver(options);
        } else if (browserName.equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
//            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
//            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, GlobalConstants.PROJECT_PATH + "/browserLogs/Firefoxlog.log")
//            FirefoxProfile profile = new FirefoxProfile();
//            File translate = new File(GlobalConstants.PROJECT_PATH + "/browserExtensions/to_google_translate-4.2.0.xpi");
//            profile.addExtension(translate);
            FirefoxOptions options = new FirefoxOptions();
            selectLanguageForFirefox(options, "en");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-geolocation");
//            options.setProfile(profile);
            options.setAcceptInsecureCerts(false);
            driver = new FirefoxDriver(options);
        } else if (browserName.equals("h_firefox")){
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.addArguments("window-size=1920x1080");
            driver = new FirefoxDriver(options);
        } else if (browserName.equals("edge")){
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browserName.equals("ie")){
            WebDriverManager.iedriver().arch32().setup();
            driver = new InternetExplorerDriver();
        } else if (browserName.equals("opera")){
            WebDriverManager.operadriver().create();
            driver = new OperaDriver();
        } else if (browserName.equals("safari")){
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        } else if (browserName.equals("coccoc")){
            WebDriverManager.chromedriver().driverVersion("109.0.5414.74").setup();
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/Applications/CocCoc.app");
            driver = new ChromeDriver(options);
        } else {
            throw new RuntimeException("Browser name invalid");
        }
        driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIMEOUT, TimeUnit.SECONDS);
        driver.get(appURL);
        driver.manage().window().maximize();
        return driver;
    }

    protected boolean verifyTrue(boolean condition) {
        boolean pass = true;
        try {
            Assert.assertTrue(condition);
            Log.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            Log.info(" -------------------------- FAILED -------------------------- ");
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyFalse(boolean condition) {
        boolean pass = true;
        try {
            Assert.assertFalse(condition);
            Log.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            Log.info(" -------------------------- FAILED -------------------------- ");
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        boolean pass = true;
        try {
            Assert.assertEquals(actual, expected);
            Log.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            Log.info(" -------------------------- FAILED -------------------------- ");
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected void closeBrowserDriver() {
        String cmd = null;
        try {
            String osName = GlobalConstants.OS_NAME;

            String driverInstanceName = driver.toString().toLowerCase();

            String browserDriverName = null;

            if (driverInstanceName.contains("chrome")) {
                browserDriverName = "chromedriver";
            } else if (driverInstanceName.contains("internetexplorer")) {
                browserDriverName = "IEDriverServer";
            } else if (driverInstanceName.contains("firefox")) {
                browserDriverName = "geckodriver";
            } else if (driverInstanceName.contains("edge")) {
                browserDriverName = "msedgedriver";
            } else if (driverInstanceName.contains("opera")) {
                browserDriverName = "operadriver";
            } else {
                browserDriverName = "safaridriver";
            }

            if (osName.contains("window")) {
                cmd = "taskkill /F /FI \"IMAGENAME eq " + browserDriverName + "*\"";
            } else {
                cmd = "pkill " + browserDriverName;
            }

            if (driver != null) {
                driver.manage().deleteAllCookies();
                driver.quit();
            }
        } catch (Exception e) {
            Log.info(e.getMessage());
        } finally {
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void selectLanguageForChrome(ChromeOptions options, String language){
        Map<String, Object> chrome_prefs = new HashMap<String, Object>();
        switch (language){
            case "vi":
            case "en":
                chrome_prefs.put("intl.accept_languages", language);
                options.setExperimentalOption("prefs", chrome_prefs);
        }
    }

    private void selectLanguageForFirefox (FirefoxOptions options, String language) {
        switch (language) {
            case "vi":
            case "en":
                options.addPreference("intl.accept_languages", language);
        }
    }

    private void disableSavePasswordInChrome (ChromeOptions options) {
        Map<String, Object> chrome_prefs = new HashMap<String, Object>();
        chrome_prefs.put("credentials_enable_service", false);
        chrome_prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chrome_prefs);
    }
}
