package utils;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.util.Set;

public class AssertionUtil {

    public static void assertTrue(boolean condition,AppiumDriver driver) {
        try {
            Assert.assertTrue(condition);
        } catch (AssertionError e) {
            captureScreenshot(driver, "Screenshot on failure");
            throw e; // Re-throw the exception to ensure the test is marked as failed
        }
    }

    public static void assertEquals(Object actual, Object expected, AppiumDriver driver) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (AssertionError e) {
            captureScreenshot(driver, "Screenshot on failure");
            throw e;
        }
    }

    public static void assertSetIsEmpty(Set<?> set, String errorMessage, AppiumDriver driver) {
        try {
            if (!set.isEmpty()) {
                // If the set is not empty, capture a screenshot and fail the assertion
                captureScreenshot(driver, "Screenshot on failure");
                Assert.fail(errorMessage + ": " + set);
            }
        } catch (AssertionError e) {
            // Re-throw the exception to ensure the test is marked as failed
            throw e;
        }
    }

    private static void captureScreenshot(WebDriver driver, String description) {
        if (driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            if (screenshot != null) {
                Allure.attachment(description, new ByteArrayInputStream(screenshot));
            }
        }
    }
}


