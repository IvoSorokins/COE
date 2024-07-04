package definitions.iOS;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.TaskOnePage;
import utils.DriverSetup;

public class IOSPartONe {

    private DriverSetup driverSetup;
    private AppiumDriver driver;
    private HomePage homePage;
    private TaskOnePage taskOnePage;

    @Before
    public void setUp() {
        driverSetup = new DriverSetup();
        driverSetup.setUp("iOS");
        driver = driverSetup.getDriver();
        homePage = new HomePage(driver);
        taskOnePage = new TaskOnePage(driver);
    }

    @After
    public void tearDown(){
        driverSetup.tearDown();
    }

    @Given("I open the Test App")
    public void i_open_the_test_app() {
        homePage.iOSHeaderDisplayed();
    }
    @When("I tap on {string} button to open Part {int} screen")
    public void i_tap_on_button_to_open_part_screen(String string, Integer int1) {
        homePage.clickPartButton(int1);
        taskOnePage.iOSHeaderDisplayed();
    }

    @And("I input valid user credentials in the form")
    public void i_input_valid_user_credentials_in_the_form() {
        taskOnePage.iOSEnterCredentials("Ivos","ivo.sorokins@gmail.com","ivo17IVO!");
    }
    @And("I input invalid username in the form")
    public void i_input_invalid_user_credentials_in_the_form(){
        taskOnePage.iOSEnterCredentials("Ivo","gmail.com","test");
    }

    @And("I tap on SUBMIT button")
    public void i_tap_on_submit_button() {
        taskOnePage.iOSClickSubmit();
    }

    @Then("I see a pop-up window with a message {string}")
    public void i_see_a_pop_up_window_with_a_message(String string) {
        taskOnePage.iOSSuccessPopUpDisplayed();
    }

    @Then("I see a pop-up window with a error message {string}")
    public void i_see_a_pop_up_window_with_a_error_message(String string){
        taskOnePage.iOSTextDisplayed(string);
    }
}
