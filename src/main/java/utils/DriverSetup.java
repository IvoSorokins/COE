package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

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

public class DriverSetup extends ConfigReader {

    public static AppiumDriver driver;
    private Process appiumProcess;

    public void setUp(String platform) {

        // Fetch the timeout value as a String from properties
        String timeoutString = getProperty("newCommandTimeout");
        int timeoutInMilis;
        try {
            timeoutInMilis = Integer.parseInt(timeoutString); // Convert String to int
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for newCommandTimeout: " + timeoutString, e);
        }
        Duration newCommandTimeout = Duration.ofMillis(timeoutInMilis);

        UiAutomator2Options AndroidOptions = new UiAutomator2Options();


        if (platform.equals("Android")) {
            logMessage("Configuring Android capabilities...");
            AndroidOptions.setPlatformName(getProperty("android.platform.name"));
            AndroidOptions.setDeviceName(getProperty("android.device.name"));
            AndroidOptions.setUdid(getProperty("android.udid"));
            AndroidOptions.setPlatformVersion(getProperty("android.platformVersion"));
            AndroidOptions.setAutomationName(getProperty("android.automation.name"));
            AndroidOptions.setAppActivity(getProperty("appActivity"));
            AndroidOptions.setAppPackage(getProperty("appPackage"));
            AndroidOptions.setNoReset(Boolean.parseBoolean(getProperty("noReset")));
            AndroidOptions.setAutoGrantPermissions(Boolean.parseBoolean(getProperty("autoGrantPermissions")));
            AndroidOptions.setNewCommandTimeout(Duration.ofSeconds(2));

            try {
                driver = new AndroidDriver(new URI(getProperty("appiumURL")).toURL(), AndroidOptions);
            } catch (MalformedURLException | URISyntaxException e) {
                throw new RuntimeException(e);
            }

        } else if (platform.equals("iOS")) {
            logMessage("Configuring iOS capabilities...");

            XCUITestOptions iOSOptions = new XCUITestOptions();
            iOSOptions
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
                driver = new IOSDriver(new URI(getProperty("appiumURL")).toURL(), iOSOptions);
            } catch (MalformedURLException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        // Wait for specified amount of time when trying to find element
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
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

            // Handle the output from Appium server
            BufferedReader reader = new BufferedReader(new InputStreamReader(appiumProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("Appium REST http interface listener started")) {
                    logMessage("Appium server started successfully.");
                    break;
                }
            }
        } catch (IOException e) {
            logMessage("Failed to start Appium server: " + e.getMessage());
        }
    }

    public void stopAppiumServer() {
        if (appiumProcess != null) {
            logMessage("Stopping Appium Server");
            appiumProcess.destroy();
            try {
                appiumProcess.waitFor();
            } catch (InterruptedException e) {
                logMessage("Error while waiting for Appium server to stop: " + e.getMessage());
            }
        }
    }

    public void beforeScenario(String scenarioName, String platform) {
        setUp(platform);
        logMessage("Starting scenario: " + scenarioName);
    }

    public static void generateAllureReport() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder
                    ("bash", "-c", "allure generate allure-results --single-file  --clean");
            Process process = processBuilder.start();

            // Read the output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                logMessage(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logMessage("Allure report generation completed successfully.");
            } else {
                logMessage("Allure report generation failed.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}