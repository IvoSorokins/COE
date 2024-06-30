package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Utility class for setting up the Appium driver
 */
@Listeners({ TestListener.class })
public class DriverSetup extends TestProperties {

    /**
     * The instance of the Appium driver
     */
    public static AppiumDriver driver;

    /**
     * Sets up the Appium driver before each test method
     */
    @BeforeMethod
    public void setUp() {
        // No-op: Initialization will be done in step definitions
    }

    /**
     * Initializes the Appium driver with the specified platform
     */
    public static void initializeDriver(String platform) {
        if (driver == null) { // Check if driver is not already initialized
            DesiredCapabilities capabilities = TestProperties.setDesiredCapabilities(platform);
            String serverUrlString = TestProperties.getProperty("appiumURL");
            URL serverUrl;

            try {
                serverUrl = new URL(serverUrlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("The Appium server URL is malformed. Please check your configuration.");
            }

            try {
                if (platform.equalsIgnoreCase("iOS")) {
                    driver = new IOSDriver(serverUrl, capabilities);
                } else {
                    driver = new AndroidDriver(serverUrl, capabilities);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to start the Appium server. Please check your configuration and the Appium server logs.");
            }

            // Optionally set implicit wait timeout
            // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
    }

    /**
     * Getter for the driver instance
     */
    public static AppiumDriver getDriver() {
        return driver;
    }

    /**
     * Tears down the Appium driver after each test method
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null; // Reset driver instance
        }
    }
}
