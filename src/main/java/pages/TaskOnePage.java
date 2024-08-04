package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import static com.google.common.base.Ascii.toUpperCase;



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
    @iOSXCUITFindBy(accessibility = "Success")
    RemoteWebElement successPopUp;

    @AndroidFindBy(id = "textView")
    @iOSXCUITFindBy(accessibility = "Incorrect username! Expected to match: ^[a-zA-Z0-9]{4,}$\n" +
            "\n" +
            "Incorrect email! Expected to match: ^[a-zA-Z0-9].+@[a-z0-9].+\\.[a-z].+$\n" +
            "\n" +
            "Incorrect password! Expected to match: " +
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@$!%*#?&])[A-Za-z0-9@$!%*#?&]{8,}$")
    RemoteWebElement failPopUp;


    public TaskOnePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public String getHeaderTitle(){
        return toUpperCase(headerTitle.getText());
    }

    public void enterCredentials(String UserName, String Email, String Password){
        editNameField.sendKeys(UserName);
        editEmailField.sendKeys(Email);
        editPasswordField.sendKeys(Password);
    }

    public void clickSubmit(){
        submitButton.click();
    }
    public String getSuccessPopUpText(){
        return successPopUp.getAttribute("name");
    }

    public String getFailPopUpText(){
        return failPopUp.getText();
    }

    public boolean doesFailPopUpContain(String expectedText){
        return getFailPopUpText().contains("Ivo");
    }
}
