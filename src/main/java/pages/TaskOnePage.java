package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class TaskOnePage {
    private final AppiumDriver driver;

    // Android elements
    private static final String header = "headerTitle";

    private static final String editFieldPrefix = "editTextText";
    private static final String editNameField = "PersonName";
    private static final String editEmailField = "EmailAddress";
    private static final String editPasswordField = "Password";
    private static final String submitButton = "submit";
    private static final String successPopUp = "textView";

    // iOs Elements
    private static final String iOSHeader = "XCUIElementTypeNavigationBar";
    private static final String iOSEditFieldPrefix = "TextField";
    private static final String iOSEditNameField = "username";
    private static final String iOSEditEmailField = "email";
    private static final String iOSEditPasswordField = "password";
    private static final String iOSSubmitButton = "submitButton"; // Accesability
    private static final String iOSSuccessPopUp = "XCUIElementTypeAlert";




    public TaskOnePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public void headerDisplayed(){
        driver.findElement(By.id(header)).isDisplayed();
    }

    public void enterCredentials(String UserName, String Email, String Password){
        driver.findElement(By.id(editFieldPrefix + editNameField)).sendKeys(UserName);
        driver.findElement(By.id(editFieldPrefix + editEmailField)).sendKeys(Email);
        driver.findElement(By.id(editFieldPrefix + editPasswordField)).sendKeys(Password);
    }

    public void clickSubmit(){
        driver.findElement(By.id(submitButton)).click();
    }

    public void successPopUpDisplayed(){
        driver.findElement(By.id(successPopUp)).isDisplayed();
    }

    public void textDisplayed(String message){
        driver.findElement(By.xpath("//android.widget.TextView[contains(@text, '" + message  + "')]"));
    }

    // iOS Specific Steps
    public void iOSHeaderDisplayed(){
        driver.findElement(By.className(iOSHeader)).isDisplayed();
    }

    public void iOSEnterCredentials(String UserName, String Email, String Password){
        driver.findElement(new AppiumBy.ByAccessibilityId(iOSEditNameField + iOSEditFieldPrefix)).sendKeys(UserName);
        driver.findElement(new AppiumBy.ByAccessibilityId(iOSEditEmailField + iOSEditFieldPrefix)).sendKeys(Email);
        driver.findElement(new AppiumBy.ByAccessibilityId(iOSEditPasswordField + iOSEditFieldPrefix)).sendKeys(Password);
    }
    public void iOSClickSubmit(){
        driver.findElement(new AppiumBy.ByAccessibilityId(iOSSubmitButton)).click();
    }

    public void iOSSuccessPopUpDisplayed(){
        driver.findElement(By.className(iOSSuccessPopUp)).isDisplayed();
    }

    public void iOSTextDisplayed(String message){
        driver.findElement(By.xpath("//XCUIElementTypeStaticText[contains(@name, '" + message + "')]"));
    }

}
