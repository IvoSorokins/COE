package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;
import java.util.Collections;


public class Helpers {

    // Method to perform scrolling, takes count of scrolls as argument
    public static void scroll(String direction, int scrollCount, AppiumDriver driver) {
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
        for (int i = 0; i < scrollCount; i++) {
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

    // Method to check if the current platform is iOS
    public static boolean isIOSPlatform(AppiumDriver driver) {
        // Adjust this method according to your platform check logic
        String platformName = driver.getCapabilities().getPlatformName().toString().toLowerCase();
        return platformName.contains("ios");
    }

    // Helper method to parse text to double
    public static double parseTextToDouble(String priceText) {
        try {
            // Remove all non-numeric characters except for digits, commas, and periods
            String cleanedPrice = priceText.replaceAll("[^0-9,\\.]", "");

            // Replace commas with periods to handle decimal values correctly (e.g., "1,654.89" -> "1654.89")
            cleanedPrice = cleanedPrice.replace(',', '.');

            // Parse the cleaned string to a double
            return Double.parseDouble(cleanedPrice);
        } catch (NumberFormatException e) {
            // Handle error case, log, or return 0.0 if parsing fails
            System.out.println("Error parsing price: " + priceText);
            return 0.0;
        }
    }

    // Method to check if element is visible
    public static boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false; // If exception occurs (e.g., element not found), return false
        }
    }

}
