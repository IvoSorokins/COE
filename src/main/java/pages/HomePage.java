package pages;


import io.appium.java_client.AppiumDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import static utils.Helpers.waitAndClick;


public class HomePage {
    private final AppiumDriver driver;

    @AndroidFindBy(id = "headerTitle")
    @iOSXCUITFindBy(className = "XCUIElementTypeStaticText")
    RemoteWebElement header;

    public HomePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public WebElement getPartButton(int partNumber) {
        String dynamicId = "scenario" + partNumber;
        return driver.findElement(By.id(dynamicId));
    }

    public void headerDisplayed(){
        header.isDisplayed();
    }

    public void clickPartButton(int partNumber) {waitAndClick(getPartButton(partNumber),driver);
    }
}
