package pages;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import utils.Helpers;


import static com.google.common.base.Ascii.toUpperCase;

public abstract class BasePage {
    protected AppiumDriver driver;

    @AndroidFindBy(id = "headerTitle")
    protected RemoteWebElement headerTitle;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }


    // Method to generate iOS locator based on the part number
    protected String generateIOSLocator(int partNumber) {
        return String.format("**/XCUIElementTypeStaticText[`name == \"Part %d\" AND label == \"Part %d\"`]", partNumber, partNumber);
    }

    // Method to get header title based on the part number and platform
    public String getHeaderTitle(int partNumber) {
        if (Helpers.isIOSPlatform(driver)) {
            String iosLocator = generateIOSLocator(partNumber);
            RemoteWebElement headerElement = (RemoteWebElement) driver.findElement(AppiumBy.iOSClassChain(iosLocator));
            return toUpperCase(headerElement.getText());
        } else {
            return toUpperCase(headerTitle.getText()); // Use Android locator
        }
    }
}