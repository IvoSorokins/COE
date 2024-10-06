package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import org.testng.SkipException;

import utils.Helpers;

import java.util.List;

import static utils.Helpers.waitAndClick;


public class TaskFourPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.CheckBox[@resource-id=\"com.example.appfortestautomation:id/item\" and @text=\"Item3\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSwitch[@name='item3Toggle' and .//XCUIElementTypeStaticText[contains(@name, 'descriptionText')]]")
    RemoteWebElement lastCheckbox;

    public TaskFourPage(AppiumDriver driver) {
        super(driver);
    }

    public void enableAllSectionsCheckboxes(int section) {
        String locator;

        for (int i = 1; i <= 5; i++) {
            if (Helpers.isIOSPlatform(driver)) {
                locator = "(//XCUIElementTypeSwitch[@name='item" + i + "Toggle']/XCUIElementTypeSwitch)[" + section + "]";
            } else {
                locator = "(//android.widget.CheckBox[@resource-id=\"com.example.appfortestautomation:id/item" + i + "\"])[" + section + "]";
            }
            waitAndClick(driver.findElement(AppiumBy.xpath(locator)), driver);
        }
    }

    public void enableSectionTwoFollowingSiblingsOfItem3() {
        String Locator;
        int ItemNumber;
        int ItemCount;

        // Get the section title (Locator) and extract ItemNumber
        if (Helpers.isIOSPlatform(driver)) {
            Locator = driver.findElement(AppiumBy.accessibilityId("section2Title")).getText();
            if (Locator.contains("PRECEDING")) {
                ItemNumber = Integer.parseInt(Locator.replace("ENABLE ALL PRECEDING SIBLINGS OF ITEM " , ""));
            } else if (Locator.contains("FOLLOWING")) {
                ItemNumber = Integer.parseInt(Locator.replace("ENABLE ALL FOLLOWING SIBLINGS OF ITEM ", ""));
            } else {
                throw new IllegalStateException("Unexpected section title format: " + Locator);
            }

        } else {
            Locator = driver.findElement(
                    AppiumBy.xpath("//android.widget.TextView[contains(@text, 'siblings of')]")).getText();

            if (Locator.contains("preceding")) {
                ItemNumber = Integer.parseInt(Locator.replace("Click all preceding siblings of Item", ""));
            } else if (Locator.contains("following")) {
                ItemNumber = Integer.parseInt(Locator.replace("Click all following siblings of Item", ""));
            } else {
                throw new IllegalStateException("Unexpected section title format: " + Locator);
            }
        }

        // Assert skip if ItemNumber is 5, as there are no siblings of Item5
        if (ItemNumber == 5) {
            throw new SkipException("Item5 has no siblings. Skipping this operation.");
        }


        if (Helpers.isIOSPlatform(driver)) {
            for (int i = ItemNumber + 1; i <= 5; i++) {  // Loop until Item5
                Locator = "(//XCUIElementTypeSwitch[@name='item" + i + "Toggle']/XCUIElementTypeSwitch)[2]";
                WebElement element = driver.findElement(AppiumBy.xpath(Locator));
                waitAndClick(element, driver);}
        } else {
            ItemCount = 5 - ItemNumber;  // Number of items remaining to process
            Locator = "(//android.widget.CheckBox[@resource-id='com.example.appfortestautomation:id/item" + ItemNumber + "'])[2]" +
                    "/following-sibling::android.widget.CheckBox[position()<=" + ItemCount + "]";
            List<WebElement> elements = driver.findElements(AppiumBy.xpath(Locator));

            for (WebElement element : elements) {
                waitAndClick(element, driver);
            }
        }
    }

    public void enableSectionThreeCheckBoxes(String YesOrNo) {
        String Locator;
        int textViewCount = 0;

        if (Helpers.isIOSPlatform(driver)) {
            Locator = "(//XCUIElementTypeStaticText[contains(@label, '" + YesOrNo + "')]/following-sibling::XCUIElementTypeSwitch)";


            List<WebElement> checkBoxes = driver.findElements(AppiumBy.xpath(Locator));
            for (WebElement checkBox : checkBoxes) {
                waitAndClick(checkBox, driver);
            }
        } else {
            Locator = "//android.widget.TextView[@resource-id='com.example.appfortestautomation:id/text']";
            List<WebElement> textViews = driver.findElements(AppiumBy.xpath(Locator));

            for (WebElement textView : textViews) {
                textViewCount++;
                if (textView.getText().equals("yes")) {
                    Locator = "//android.widget.CheckBox[@resource-id='com.example.appfortestautomation:id/item' and @text='Item" + textViewCount + "']";
                    WebElement checkBox = driver.findElement(AppiumBy.xpath(Locator));

                    if (checkBox != null) {
                        waitAndClick(checkBox, driver);
                    } else {
                        System.out.println("No checkbox found for textView with 'yes' text");
                    }
                }
            }
        }
    }

    public String messageText(int section) {
        WebElement popUpMessage;

        if (Helpers.isIOSPlatform(driver)) {
            popUpMessage = driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeAlert/**/XCUIElementTypeStaticText"));
        } else {
            popUpMessage = driver.findElement(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.TextView[" + section + "]"));
        }
        return popUpMessage.getText();
    }

    public void scrollUpUntilSectionThree() {
        Helpers.scrollUntilElementIsVisible(lastCheckbox, "up", driver);}
}
