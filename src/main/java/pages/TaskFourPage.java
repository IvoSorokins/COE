package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import utils.Helpers;


public class TaskFourPage extends BasePage {

    @AndroidFindBy(id = "submit4")
    @iOSXCUITFindBy(accessibility = "submitButton")
    RemoteWebElement submitButton;

    public TaskFourPage(AppiumDriver driver) {
        super(driver);
    }


    // Method to enable all section 1 checkboxes
    public void enableAllSectionOneCheckboxes() {
        // Locate all section one checkboxes and enable them
        for (int i = 1; i <= 5; i++) { // Assuming 5 checkboxes
            WebElement checkbox = (WebElement) driver.findElement(AppiumBy.xpath("//XCUIElementTypeSwitch[@name='item" + i + "Toggle']"));
            if (!checkbox.getAttribute("value").equals("1")) {
                checkbox.click();
            }
        }
    }

    public void tapOnSubmitButton() {
        boolean reachedEnd = false; // Flag to detect if end of the list is reached

        while (!reachedEnd) {
            // Check if the submit button is visible
            if (Helpers.isElementVisible(submitButton)) {
                submitButton.click(); // Click the button if visible
                reachedEnd = true; // Exit the loop after clicking
            } else {
                // Scroll down if the button is not visible
                Helpers.scroll("down", 1, driver);
            }
        }
    }
}
// TODO: 1.) Add scroll down to the tapOnSubmitButton 2.) enableAllSectionOneCheckboxes also needs Android Part

