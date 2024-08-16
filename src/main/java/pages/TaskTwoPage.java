package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;


import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utils.Helpers;

import static com.google.common.base.Ascii.toUpperCase;

public class TaskTwoPage {
    private final AppiumDriver driver;

    @AndroidFindBy(id = "headerTitle")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name == \"Part 2\"`]")
    RemoteWebElement headerTitle;

    @AndroidFindBy()
    @iOSXCUITFindBy(className = "XCUIElementTypeCollectionView")
    RemoteWebElement itemList;

    @iOSXCUITFindBy(iOSNsPredicate = "name == \"text\"")
    List<RemoteWebElement> itemsFromList;

    public TaskTwoPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public String getHeaderTitle(){
        return toUpperCase(headerTitle.getText());
    }

    public List<String> saveListItems() {
        List<String> itemTexts = new ArrayList<>();
        for (RemoteWebElement item : itemsFromList) {
            itemTexts.add(item.getText());
        }
        return itemTexts;
    }


    public List<String> saveListItemsWhileScrolling(String platform, int maxScrolls) {
        Set<String> itemTexts = new HashSet<>();
        int previousSize = 0;

        for (int i = 0; i < maxScrolls; i++) {
            List<RemoteWebElement> visibleItems = itemsFromList;
            for (RemoteWebElement item : visibleItems) {
                itemTexts.add(item.getText());
            }

            if (itemTexts.size() == previousSize) {
                break;
            }

            previousSize = itemTexts.size();

            Helpers.scroll(platform,1, driver);
        }

        return new ArrayList<>(itemTexts);
    }

}
