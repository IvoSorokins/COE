package pages;
;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;


public class TaskOnePage {
    private final AppiumDriver driver;

    @AndroidFindBy(id = "headerTitle")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name == \"Part 1\"`]")
    RemoteWebElement headerTitle;

    @AndroidFindBy(id = "editTextTextPersonName")
    @iOSXCUITFindBy(accessibility = "usernameTextField")
    RemoteWebElement editNameField;

    @AndroidFindBy(id = "editTextTextEmailAddress")
    @iOSXCUITFindBy(accessibility = "emailTextField")
    RemoteWebElement editEmailField;

    @AndroidFindBy(id = "editTextTextPassword")
    @iOSXCUITFindBy(accessibility = "passwordTextField")
    RemoteWebElement editPasswordField;

    @AndroidFindBy(id = "submit")
    @iOSXCUITFindBy(accessibility = "submitButton")
    RemoteWebElement submitButton;

    @AndroidFindBy(id = "textView")
    @iOSXCUITFindBy(className = "XCUIElementTypeAlert")
    RemoteWebElement alertPopUp;


    public TaskOnePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public String getHeaderTitle(){
        return headerTitle.getText();
    }

    public void enterCredentials(String UserName, String Email, String Password){
        editNameField.sendKeys(UserName);
        editEmailField.sendKeys(Email);
        editPasswordField.sendKeys(Password);
    }

    public void clickSubmit(){
        submitButton.click();
    }

    public String getTextDisplayed(){
        return alertPopUp.getText();
    }
}
