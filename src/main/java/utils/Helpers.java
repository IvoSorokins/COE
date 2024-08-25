package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;
import java.util.Collections;

public class Helpers {

    // Method to perform scrolling, takes count of scrolls as argument
    public static void scroll(String platform,String direction, int scrollCount, AppiumDriver driver) {
        for (int i = 0; i < scrollCount; i++) {
            if (platform.equals("iOS")) {
                scrollIOS(driver,direction,scrollCount);
            } else {
                scrollAndroid(driver);
            }
        }
    }

    private static void scrollIOS(AppiumDriver driver, String direction, int times) {
        // Define the dimensions
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = size.height / 2;

        // Set scroll points based on the direction
        int endX = startX;
        int endY = startY;

        switch (direction.toLowerCase()) {
            case "up":
                startY = (int) (size.height * 0.8);
                endY = (int) (size.height * 0.2);
                break;
            case "down":
                startY = (int) (size.height * 0.2);
                endY = (int) (size.height * 0.8);
                break;
            case "left":
                startX = (int) (size.width * 0.8);
                endX = (int) (size.width * 0.2);
                break;
            case "right":
                startX = (int) (size.width * 0.2);
                endX = (int) (size.width * 0.8);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Perform the scroll the specified number of times
        for (int i = 0; i < times; i++) {
            // Create a PointerInput instance representing the finger for touch actions
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

            // Create a Sequence of actions to simulate the scroll
            Sequence swipe = new Sequence(finger, 1)
                    .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                    .addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), endX, endY))
                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the scroll action
            ((RemoteWebDriver) driver).perform(Collections.singletonList(swipe));
        }
    }

    private static void scrollAndroid(AppiumDriver driver) {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        int startX = size.width / 2;

        new TouchAction<>((PerformsTouchActions) driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }
}
