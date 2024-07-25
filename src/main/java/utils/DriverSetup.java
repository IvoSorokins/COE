package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Listeners;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import static utils.LoggerUtil.logMessage;


/**
 * Utility class for setting up the Appium driver
 */
@Listeners({ AllureListener.class })
public class DriverSetup extends ConfigReader {

    public static AppiumDriver driver;
    private Process appiumProcess;

    public void setUp(String platform) {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        logMessage("Setting Up Capabilities");
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

        } else if (platform.equals("iOS")) {
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
                    .setWaitForQuiescence(Boolean.parseBoolean(getProperty("waitForQuiescence")))
                    .setAutoAcceptAlerts(Boolean.parseBoolean(getProperty("autoAcceptAlerts")));

            try {
                driver = new IOSDriver(new URI(getProperty("appiumURL")).toURL(), options);
            } catch (MalformedURLException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        // Wait for specified amount of time when trying to find element
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public static AppiumDriver getDriver() {
        return driver;
    }

    public void startAppiumServer() {
        logMessage("Starting Appium Server");
        try {
            String[] command = {"appium"};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            appiumProcess = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(appiumProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("Appium REST http interface listener started")) {
                    break;
                }
            }
        } catch (Exception e) {
            logMessage(String.valueOf(e));
        }
    }

    public void stopAppiumServer() {
        if (appiumProcess != null) {
            logMessage("Stopping Appium Server");
            appiumProcess.destroy();
        }
    }

    public void beforeScenario(String scenarioName, String platform) {
        logMessage("Starting scenario: " + scenarioName);
        setUp(platform);

    }

    public static void generateAllureReport() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "allure serve allure-results");

            Process process = processBuilder.start();

            // Read the output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Allure report generation completed successfully.");
            } else {
                System.out.println("Allure report generation failed.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}