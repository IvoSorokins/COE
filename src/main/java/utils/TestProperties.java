package utils;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for reading properties from a configuration file
 */
public class TestProperties {

    /** Before running any tests, let's set testProperties */

    //  Wait until visibility of the element
    public static final long waitTimeInSeconds = 5;

    // Set the platform on which the tests will run
    public static final String platform = "iOS";

    // Set the path to the config.properties file
    private static final String propsPath = "src/main/resources/config.properties";


    // Create a Properties object to hold the loaded properties
    private static final Properties props = new Properties();

    static{
        loadProperties();
    }
    /**
     * This method loads properties from a file located at the path specified by the 'propsPath' variable.
     * The properties are loaded into the 'props' Properties object.
     * If an IOException occurs during the operation, the stack trace is printed.
     */
    private static void loadProperties() {
        // Use try-with-resources to automatically close the input stream
        try ( InputStream input = new FileInputStream(propsPath)){
            // Load the properties from the input stream
            props.load(input);
        } catch (IOException ex) {
            // Print the stack trace if an exception occurs while loading the properties
            ex.printStackTrace();
        }
    }


    public static DesiredCapabilities setDesiredCapabilities(String platform) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // Common Caps
        capabilities.setCapability("appium:waitForQuiescence", props.getProperty("waitForQuiescence"));
        capabilities.setCapability("appium:ignoreUnimportantViews", props.getProperty("ignoreUnimportantViews"));


        // Set the desired capabilities based on the loaded properties
        if (platform.equals("iOS")) {
            capabilities.setCapability("appium:bundleId", props.getProperty("bundleId"));
            capabilities.setCapability("appium:automationName", props.getProperty("ios.automation.name"));
            capabilities.setCapability("appium:udid", props.getProperty("ios.udid"));
            capabilities.setCapability("appium:platformName", props.getProperty("ios.platform.name"));
            capabilities.setCapability("appium:deviceName", props.getProperty("ios.device.name"));
            capabilities.setCapability("appium:platformVersion", props.getProperty("ios.device.platform"));

        } else {
            capabilities.setCapability("appium:appPackage", props.getProperty("appPackage"));
            capabilities.setCapability("appium:automationName", props.getProperty("android.automation.name"));
            capabilities.setCapability("appium:platformName", props.getProperty("androidPlatformName"));
            capabilities.setCapability("appium:deviceName", props.getProperty("android.device.name"));
            capabilities.setCapability("appium:udId", props.getProperty("android.udid"));
            capabilities.setCapability("appium:platformVersion", props.getProperty("android.platformVersion"));
            capabilities.setCapability("appium:noReset", props.getProperty("noReset"));
            capabilities.setCapability("appium:newCommandTimeout", props.getProperty("newCommandTimeout"));
            capabilities.setCapability("appium:appActivity", props.getProperty("appActivity"));
        }
        // Return the DesiredCapabilities object
        return capabilities;
    }

    // Return the value of the specified property
    public static String getProperty(String name){
        return props.getProperty(name);
    }
}