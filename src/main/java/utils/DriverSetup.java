package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.qameta.allure.Step;
import org.openqa.selenium.Platform;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Duration;

import org.openqa.selenium.remote.DesiredCapabilities;
import pages.HomePage;
import pages.TaskOnePage;

import static utils.ConfigReader.getProperty;

/**
 * Utility class for setting up the Appium driver
 */
@Listeners({ TestListener.class })
public class DriverSetup extends ConfigReader {

    /**
     * The instance of the Appium driver
     */
    public static AppiumDriver driver;

    protected HomePage homePage;
    protected TaskOnePage taskOnePage;


    public void setUp(String platform) {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals("Android")) {
            capabilities.setPlatform(Platform.ANDROID);
            capabilities.setCapability(UiAutomator2Options.DEVICE_NAME_OPTION, getProperty("android.device.name"));
            capabilities.setCapability(UiAutomator2Options.UDID_OPTION, getProperty("android.udid"));
            capabilities.setCapability(UiAutomator2Options.PLATFORM_VERSION_OPTION, getProperty("android.platformVersion"));
            capabilities.setCapability(UiAutomator2Options.AUTOMATION_NAME_OPTION, getProperty("android.automation.name"));
            capabilities.setCapability(UiAutomator2Options.APP_ACTIVITY_OPTION, getProperty("appActivity"));
            capabilities.setCapability(UiAutomator2Options.APP_PACKAGE_OPTION, getProperty("appPackage"));
            capabilities.setCapability(UiAutomator2Options.NO_RESET_OPTION, false);
            capabilities.setCapability(UiAutomator2Options.NEW_COMMAND_TIMEOUT_OPTION, getProperty("newCommandTimeout"));
            capabilities.setCapability(UiAutomator2Options.FULL_RESET_OPTION, true);
            capabilities.setCapability(UiAutomator2Options.AUTO_GRANT_PERMISSIONS_OPTION, true);
            capabilities.setCapability("appium:disableIdLocatorAutocompletion", true);

            try {
                driver = new AndroidDriver(new URI(getProperty("appium.server.url")).toURL(), capabilities);
            } catch (MalformedURLException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        else if(platform.equals("iOS")) {
            XCUITestOptions options = new XCUITestOptions();
            options
                    .setPlatformName("iOS")
                    .setDeviceName(getProperty("ios.platform.name"))
                    .setPlatformVersion(getProperty("ios.device.platform"))
                    .setAutomationName(getProperty("ios.automation.name"))
                    .setUdid(getProperty("ios.udid"))
                    .setBundleId(getProperty("bundleId"))
                    .setDeviceName("ios.device.name")
                    .setNoReset(false)
                    .setAutoAcceptAlerts(true);

            try {
                driver = new IOSDriver(new URI(getProperty("appium.server.url")).toURL(), options);
            } catch (MalformedURLException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        // Wait for specified amount of time when trying to find element
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));



        // Initialize Pages here
        homePage = new HomePage(driver);
        taskOnePage = new TaskOnePage(driver);

    }

    /**
     * Tears down the Appium driver after each test method
     */
    @Step("Driver is closed")
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
