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
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;


/**
 * Utility class for setting up the Appium driver
 */
@Listeners({ TestListener.class })
public class DriverSetup extends ConfigReader {

    /**
     * The instance of the Appium driver
     */
    public static AppiumDriver driver;
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
            capabilities.setCapability(UiAutomator2Options.NO_RESET_OPTION, getProperty("noReset"));
            capabilities.setCapability(UiAutomator2Options.NEW_COMMAND_TIMEOUT_OPTION, getProperty("newCommandTimeout"));
            capabilities.setCapability(UiAutomator2Options.AUTO_GRANT_PERMISSIONS_OPTION, getProperty("autoGrantPermissions"));


            try {
                driver = new AndroidDriver(new URI(getProperty("appiumURL")).toURL(), capabilities);
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
                    .setDeviceName(getProperty("ios.device.name"))
                    .setNoReset(Boolean.parseBoolean(getProperty("noReset")))
                    .setWaitForQuiescence(Boolean.parseBoolean(getProperty("waitForQuiescence")));

            try {
                driver = new IOSDriver(new URI(getProperty("appiumURL")).toURL(), options);
            } catch (MalformedURLException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        // Wait for specified amount of time when trying to find element
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public AppiumDriver getDriver(){
        return driver;
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
