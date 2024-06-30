package tests;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.Test;
import utils.DriverSetup;

public class AndroidTest {
    @Test
    public void androidTestCase() {
        // Use Android-specific logic
        AppiumDriver driver = DriverSetup.getDriver();
        // Perform Android-specific tests
    }
}
