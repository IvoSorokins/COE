package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.remote.RemoteWebElement;

import java.util.ArrayList;
import java.util.List;

import utils.Helpers;


public class TaskTwoPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.example.appfortestautomation:id/textView']")
    @iOSXCUITFindBy(iOSNsPredicate = "name == \"text\"")
    List<RemoteWebElement> itemsFromList;

    public TaskTwoPage(AppiumDriver driver) {
        super(driver);
    }

    public List<String> saveListItemsWhileScrollingUp(int maxScrolls) {
        List<String> seenItems = new ArrayList<>();

        for (int i = 0; i < maxScrolls; i++) {
            // Get the list of currently visible items
            List<RemoteWebElement> currentVisibleItems = itemsFromList;

            for (RemoteWebElement item : currentVisibleItems) {
                String text = item.getText();
                // Filter items based on your criteria
                if (!text.startsWith("---") && !text.contains("Zaa") && !text.contains("Zbb") && !text.contains("Zcc")) {
                    // Check if the item is already in the seenItems list
                    if (!seenItems.contains(text)) {
                        seenItems.add(text);
                    }
                }
            }

            Helpers.scroll("up", 1, driver);

            if (currentVisibleItems.isEmpty()) {
                break;
            }
        }

        return seenItems;
    }

}
