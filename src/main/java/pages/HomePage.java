package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


public class HomePage {
    private final AppiumDriver driver;

    // Elements
    private static final String partPrefix = "scenario";

    public HomePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    // Locators for the elements
    public WebElement getPartButton(int partNumber) {
        String dynamicId = partPrefix + partNumber;
        return driver.findElement(By.id(dynamicId));
    }

    public void clickPartButton(int partNumber) {
        getPartButton(partNumber).click();
    }
}
