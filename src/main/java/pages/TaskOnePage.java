package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.remote.RemoteWebElement;

public class TaskOnePage extends BasePage {

    @AndroidFindBy(id = "editTextTextPersonName")
    @iOSXCUITFindBy(accessibility = "usernameTextField")
    RemoteWebElement editNameField;

    @AndroidFindBy(id = "editTextTextEmailAddress")
    @iOSXCUITFindBy(accessibility = "emailTextField")
    RemoteWebElement editEmailField;

    @AndroidFindBy(id = "editTextTextPassword")
    @iOSXCUITFindBy(accessibility = "passwordTextField")
    RemoteWebElement editPasswordField;

    @AndroidFindBy(id = "textView")
    @iOSXCUITFindBy(accessibility = "Success")
    RemoteWebElement successPopUp;

    @AndroidFindBy(id = "textView")
    @iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH 'Incorrect username'")
    RemoteWebElement failPopUp;


    public TaskOnePage(AppiumDriver driver) {
        super(driver);
    }

    public void enterCredentials(String UserName, String Email, String Password){
        editNameField.sendKeys(UserName);
        editEmailField.sendKeys(Email);
        editPasswordField.sendKeys(Password);
    }

    public String getSuccessPopUpText(){
        return successPopUp.getAttribute("name");
    }

    public String getFailPopUpText(){
        return failPopUp.getText();
    }

    public boolean doesFailPopUpContain(String expectedText){
        return getFailPopUpText().contains(expectedText);
    }

}
