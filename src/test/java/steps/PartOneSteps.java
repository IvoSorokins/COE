package steps;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.TaskOnePage;

public class PartOneSteps {

    private AppiumDriver driver;
    private HomePage homePage;
    private TaskOnePage taskOnePage;

    @Given("I open the Test App")
    public void i_open_the_test_app(){
        homePage = new HomePage(driver);
        taskOnePage = new TaskOnePage(driver);
    }

    @When("I tap on \"PART 1\" button to open Part 1 screen")
    public void i_tap_part_one(){
        homePage.clickPartButton(1);
    }
    @And("I input valid user credentials in the form")
    public void i_input_valid_user_credentials_in_form(){

    }

    @And ("I tap on SUBMIT button")
    public void i_tap_submit_button(){

    }
    @Then("I see a pop-up window with a message 'Success'")
    public void i_see_popup_success(){

    }

}
