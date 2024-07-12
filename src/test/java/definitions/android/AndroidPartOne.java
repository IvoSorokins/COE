package definitions.android;

import io.appium.java_client.AppiumDriver;

import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import pages.HomePage;
import pages.TaskOnePage;

import utils.DriverSetup;


public class AndroidPartOne {

    private static DriverSetup driverSetup;
    private static AppiumDriver driver;
    private static HomePage homePage;
    private static TaskOnePage taskOnePage;

    @BeforeAll
    public static void beforeAll() {
        driverSetup = new DriverSetup();
        driverSetup.startAppiumServer();
    }

    @AfterAll
    public static void afterAll(){
        driverSetup.stopAppiumServer();
    }

    @Before
    public void setUp(Scenario scenario){
        driverSetup.beforeScenario(scenario.getName(),"Android");

        driver = driverSetup.getDriver();
        homePage = new HomePage(driver);
        taskOnePage = new TaskOnePage(driver);
    }
    @After
    public static void tearDown(Scenario scenario){driverSetup.afterScenario(scenario.getName(),scenario.isFailed());}

    @Given("I open the Test App")
    public void i_open_the_test_app() {
        homePage.headerDisplayed();
    }

    @When("I tap on {string} button to open Part {int} screen")
    public void i_tap_on_button_to_open_part_screen(String string, Integer int1) {

        homePage.clickPartButton(int1);
        taskOnePage.headerDisplayed();
    }

    @And("I input valid user credentials in the form")
    public void i_input_valid_user_credentials_in_the_form() {
        taskOnePage.enterCredentials("Ivo17","ivo@gmail.com","test17IVO!");
    }

    @And("I input invalid username in the form")
    public void i_input_invalid_user_credentials_in_the_form(){
        taskOnePage.enterCredentials("Ivo","gmail.com","test");
    }

    @And("I tap on SUBMIT button")
    public void i_tap_on_submit_button() {
        taskOnePage.clickSubmit();
    }

    @Then("I see a pop-up window with a message {string}")
    public void i_see_a_pop_up_window_with_a_message(String string) {
        taskOnePage.textDisplayed(string);
    }

    @Then("I see a pop-up window with a error message {string}")
    public void i_see_a_pop_up_window_with_a_error_message(String string){
        taskOnePage.textDisplayed(string);
    }

}
