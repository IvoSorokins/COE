package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;

import java.time.Duration;

public class Helpers {

    // Method to perform scrolling, takes count of scrolls as argument
    public static void scroll(String platform, int scrollCount, AppiumDriver driver) {
        for (int i = 0; i < scrollCount; i++) {
            if (platform.equals("iOS")) {
                scrollIOS(driver);
            } else {
                scrollAndroid(driver);
            }
        }
    }

    private static void scrollIOS(AppiumDriver driver) {
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
