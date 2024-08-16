package definitions.iOS;



import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import pages.HomePage;
import pages.TaskOnePage;


import pages.TaskTwoPage;
import utils.AssertionUtil;


public class IOSPartOne {

    private HomePage homePage = IOSHooks.getHomePage();
    private TaskOnePage taskOnePage = IOSHooks.getTaskOnePage();
    private TaskTwoPage taskTwoPage = IOSHooks.getTaskTwoPage();
    private AppiumDriver driver = IOSHooks.getDriver();

    @Given("I open the Test App")
    public void i_open_the_test_app() {
        homePage.headerDisplayed();
    }

    @When("I tap on {string} button to open Part 1 screen")
    public void i_tap_on_button_to_open_part_screen(String string) {
        homePage.clickPartButton(1);
        AssertionUtil.assertEquals(taskOnePage.getHeaderTitle(), string, driver);
    }

    @And("I input valid user credentials in the form")
    public void i_input_valid_user_credentials_in_the_form() {
        taskOnePage.enterCredentials("IvoS17","ivo.sorokins@gmail.com","ivo17IVO!");
    }

    @And("I input invalid username in the form")
    public void i_input_invalid_user_credentials_in_the_form(){
        taskOnePage.enterCredentials("I","gmailcom","tst");
    }

    @And("I tap on SUBMIT button")
    public void i_tap_on_submit_button() {
        taskOnePage.clickSubmit();
    }

    @Then("I see a pop-up window with a message {string}")
    public void i_see_a_pop_up_window_with_a_message(String string) {
        AssertionUtil.assertEquals(taskOnePage.getSuccessPopUpText(),string ,driver);
    }

    @Then("I see a pop-up window with a error message {string}")
    public void i_see_a_pop_up_window_with_a_error_message(String string) {
        AssertionUtil.assertTrue(taskOnePage.doesFailPopUpContain(string),driver);
    }
}
