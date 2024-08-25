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
import utils.DriverSetup;
import utils.LoggerUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class IOSSteps {

    private static AppiumDriver driver ;
    private static DriverSetup driverSetup;
    private static HomePage homePage;
    private static TaskOnePage taskOnePage;
    private static TaskTwoPage taskTwoPage;

    private List<String> savedItems;

    @Before
    public void before(Scenario scenario){
        driverSetup.beforeScenario(scenario.getName(),"iOS");

        driver = DriverSetup.getDriver();
        homePage = new HomePage(driver);
        taskOnePage = new TaskOnePage(driver);
        taskTwoPage = new TaskTwoPage(driver);
    }

    @BeforeAll
    public static void beforeAll() {
        driverSetup = new DriverSetup();
        driverSetup.startAppiumServer();
    }

    @AfterAll
    public static void afterAll(){
        driverSetup.stopAppiumServer();
        DriverSetup.generateAllureReport();
    }

    @After
    public void tearDown(Scenario scenario){
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

    @When("I tap on {string} button to open Part 1 screen")
    public void i_tap_on_button_to_open_part_one_screen(String string) {
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

    // Part 2

    @When("I tap on {string} button to open Part 2 screen")
    public void i_tap_on_button_to_open_part_two_screen(String string) {
        homePage.clickPartButton(2);
        AssertionUtil.assertEquals(taskTwoPage.getHeaderTitle(), string, driver);
    }

    @And("I save list items while scrolling through the list")
    public void i_save_list_items_while_scrolling_through_the_list(){
        savedItems = taskTwoPage.saveListItemsWhileScrollingUp("iOS",5);
    }

    @Then("I validate the saved items are in alphabetical order")
    public void iValidateTheSavedItemsAreInAlphabeticalOrder() {
        List<String> sortedItems = new ArrayList<>(savedItems);
        Collections.sort(sortedItems);

        AssertionUtil.assertEquals(savedItems,sortedItems, driver);
    }

    @Then("I validate the {string} have a * symbol added")
    public void iValidateTheHaveASymbolAdded(String itemNames) {
        List<String> itemsWithAsterisks = Arrays.asList(itemNames.split(", "));
        for (String item : itemsWithAsterisks) {
            AssertionUtil.assertTrue(savedItems.contains(item + "*"), driver);
        }
    }

    @Then("I validate the {string} categories are empty") //// ????
    public void i_validate_the_categories_are_empty(String categories) {
        List<String> emptyCategories = Arrays.asList(categories.split(", "));
        for (String category : emptyCategories) {
            boolean isEmpty = savedItems.stream().noneMatch(item -> item.startsWith(category));

            AssertionUtil.assertTrue(isEmpty, driver);
        }
    }

    @Then("I validate the {string} categories have 3 or more items") // ????
    public void i_validate_the_categories_have_3_or_more_items(String categories){
        List<String> categoriesWithItems = Arrays.asList(categories.split(", "));
        for (String category : categoriesWithItems) {
            long count = savedItems.stream().filter(item -> item.startsWith(category)).count();
            AssertionUtil.assertTrue(count >= 3, driver);
        }
    }
}
