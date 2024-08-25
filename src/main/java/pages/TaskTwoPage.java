package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;


import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import utils.Helpers;

import static com.google.common.base.Ascii.toUpperCase;

public class TaskTwoPage {
    private final AppiumDriver driver;

    @AndroidFindBy(id = "headerTitle")
    @iOSXCUITFindBy(iOSNsPredicate = "name == \"Part 2\" AND label == \"Part 2\"")
    RemoteWebElement headerTitle;

    @AndroidFindBy(id = "com.example.appfortestautomation:id/brandView")
    @iOSXCUITFindBy(className = "XCUIElementTypeCollectionView")
    RemoteWebElement itemList;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.example.appfortestautomation:id/textView']")
    @iOSXCUITFindBy(iOSNsPredicate = "name == \"text\"")
    List<RemoteWebElement> itemsFromList;

    public TaskTwoPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public String getHeaderTitle(){
        return toUpperCase(headerTitle.getText());
    }

    public List<String> saveListItemsWhileScrollingUp(String platform, int maxScrolls) {
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
