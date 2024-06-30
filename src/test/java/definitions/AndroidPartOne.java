package definitions;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.TaskOnePage;
import utils.DriverSetup;
public class AndroidPartOne {

    private AppiumDriver driver;
    private HomePage homePage;
    private TaskOnePage taskOnePage;
    private DriverSetup driverSetup;

    @Given("I open the Test App")
    public void i_open_the_test_app() {
        // Implement logic to open the app
        homePage = new HomePage(driver);  // Assuming 'driver' is initialized somewhere
        taskOnePage = new TaskOnePage(driver);  // Assuming 'driver' is initialized somewhere
    }

    @When("I tap on {string} button to open Part {int} screen")
    public void i_tap_on_button_to_open_part_screen(String buttonName, Integer partNumber) {
        // Implement logic to tap on the button
        homePage.clickPartButton(partNumber);
    }

    @And("I input valid user credentials in the form")
    public void i_input_valid_user_credentials_in_the_form() {
        // Implement logic to input credentials
        // Example: taskOnePage.enterCredentials(username, password);
    }

    @And("I tap on SUBMIT button")
    public void i_tap_on_submit_button() {
        // Implement logic to tap on submit button
        // Example: taskOnePage.clickSubmit();
    }

    @Then("I see a pop-up window with a message {string}")
    public void i_see_a_pop_up_window_with_a_message(String message) {
        // Implement logic to verify the pop-up message
        // Example: Assert.assertEquals(taskOnePage.getPopupMessage(), message);
    }
}
