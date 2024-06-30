package tests;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.Test;
import utils.DriverSetup;

public class iOSTest {
    @Test
    public void iOSTestCase() {
        // Use iOS-specific logic
        AppiumDriver driver = DriverSetup.getDriver();
        // Perform iOS-specific tests
    }
}
