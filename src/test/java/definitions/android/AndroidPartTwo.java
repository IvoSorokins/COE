package definitions.android;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import pages.HomePage;
import pages.TaskOnePage;
import utils.DriverSetup;

import static utils.LoggerUtil.logMessage;


public class AndroidPartTwo {

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
        driverSetup.generateAllureReport();
    }

    @Before
    public void setUp(Scenario scenario){
        driverSetup.beforeScenario(scenario.getName(),"Android");

        driver = driverSetup.getDriver();
        homePage = new HomePage(driver);
        taskOnePage = new TaskOnePage(driver);
    }

    @After
    public static void tearDown(Scenario scenario){
        if (scenario.isFailed()) {
            // Take screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);

            // Attach screenshot to Allure report
            Allure.getLifecycle().addAttachment(
                    "Screenshot on failure", "image/png", "png", screenshotBytes);
            logMessage("Added Attachment: " + screenshotBytes);
        }

        // Log message and quit driver
        System.out.println("Ending scenario: " + scenario.getName());
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I open the Test App")
    public void i_open_the_test_app() {
        homePage.headerDisplayed();
    }

    @When("I tap on {string} button to open Part 2 screen")
    public void i_tap_on_button_to_open_part_screen(String string) {
        homePage.clickPartButton(2);
        Assert.assertEquals(taskOnePage.getHeaderTitle(), string);
    }
    @And("I save list items while scrolling through the list")
    public void i_save_list_items_while_scrolling_through_the_list() {
        // Implement logic to save list items while scrolling
    }

    @Then("I validate the saved items are in an alphabetical order")
    public void i_validate_the_saved_items_are_in_an_alphabetical_order() {
        // Implement logic to validate that the items are in alphabetical order
    }

    @Then("I validate the {string} has a * symbol added")
    public void i_validate_the_has_symbol_added(String items){

    }

    @Then("I validate the {string} categories are empty")
    public void i_validate_the_categories_are_empty(String categories){}

    @Then("I validate the {string} categories have 3 or more items")
    public void i_validate_the_categories_have_3_or_more_items(String categories){}
}
