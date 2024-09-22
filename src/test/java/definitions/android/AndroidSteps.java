package definitions.android;

import io.appium.java_client.AppiumDriver;

import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import pages.*;

import utils.AssertionUtil;
import utils.DriverSetup;
import utils.PageFactoryUtil;

import java.util.*;

import static utils.LoggerUtil.logMessage;


public class AndroidSteps {

    private static DriverSetup driverSetup;
    private static AppiumDriver driver;
    private static HomePage homePage;
    private static TaskOnePage taskOnePage;
    private static TaskTwoPage taskTwoPage;
    private static TaskThreePage taskThreePage;

    private List<String> savedItems;
    double savedTotalSum;
    double savedBrandSum;
    Set<String> savedImageNames = new HashSet<>();

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
        logMessage("Ending scenario: " + scenario.getName());
        if (driver != null) {
            driver.quit();
        }
    }

    @Before
    public void before(Scenario scenario){
        driverSetup.beforeScenario(scenario.getName(),"Android");

        driver = DriverSetup.getDriver();
        homePage = new HomePage(driver);
        taskOnePage = new TaskOnePage(driver);
        taskTwoPage = new TaskTwoPage(driver);
        taskThreePage = new TaskThreePage(driver);
    }

    @Given("I open the Test App")
    public void i_open_the_test_app() {
        homePage.headerDisplayed();
    }
    @When("I tap on {string} button to open Part {int} screen")
    public void i_tap_on_button_to_open_part_screen(String expectedHeaderTitle, int partNumber) {
        homePage.clickPartButton(partNumber);

        // Get the appropriate page object based on the part number
        BasePage taskPage = PageFactoryUtil.getPageObject(driver, partNumber);

        AssertionUtil.assertEquals(taskPage.getHeaderTitle(partNumber), expectedHeaderTitle, driver);
    }

    @And("I input valid user credentials in the form")
    public void i_input_valid_user_credentials_in_the_form() {
        taskOnePage.enterCredentials("Ivo17","ivo@gmail.com","test17IVO!");
    }

    @And("I input invalid username in the form")
    public void i_input_invalid_user_credentials_in_the_form(){
        taskOnePage.enterCredentials("I","gmailcom","test");
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

     //Part 2
    @And("I save list items while scrolling through the list")
    public void i_save_list_items_while_scrolling_through_the_list(){
        savedItems = taskTwoPage.saveListItemsWhileScrollingUp(5);
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

    @Then("I validate the {string} categories are empty")
    public void i_validate_the_categories_are_empty(String categories) {
        List<String> emptyCategories = Arrays.asList(categories.split(", "));
        for (String category : emptyCategories) {
            boolean isEmpty = savedItems.stream().noneMatch(item -> item.startsWith(category));

            AssertionUtil.assertTrue(isEmpty, driver);
        }
    }

    @Then("I validate the {string} categories have 3 or more items")
    public void i_validate_the_categories_have_3_or_more_items(String categories){
        List<String> categoriesWithItems = Arrays.asList(categories.split(", "));
        for (String category : categoriesWithItems) {
            long count = savedItems.stream().filter(item -> item.startsWith(category)).count();
            AssertionUtil.assertTrue(count >= 3, driver);
        }
    }

    // Part 3
    @And("I save all item price sum while scrolling through the item list")
    public void iSaveAllItemPriceSumWhileScrollingThroughTheItemList() {
        savedTotalSum = taskThreePage.saveAllItemPriceSumWhileScrolling();
    }

    @Then("I validate the total sum matches all item price sum")
    public void iValidateTheTotalSumMatchesAllItemPriceSum() {
        double displayedTotalSum = taskThreePage.getDisplayedTotalSum();
        AssertionUtil.assertEquals(displayedTotalSum,savedTotalSum, driver);
    }

    @And("I save brand item price sum while scrolling through the item list")
    public void iSaveBrandItemPriceSumWhileScrollingThroughTheItemList() {
        savedBrandSum = taskThreePage.saveBrandItemPriceSumWhileScrolling();
    }

    @Then("I validate a brand total sum matches that brand item price sum")
    public void iValidateABrandTotalSumMatchesThatBrandItemPriceSum() {
        double displayedTotalBrandValue = taskThreePage.getTotalBrandNumber();
        AssertionUtil.assertEquals(savedBrandSum, displayedTotalBrandValue, driver);
    }

    @And("I save all item image names while scrolling through the item list")
    public void iSaveAllItemImageNamesWhileScrollingThroughTheItemList() {
        savedImageNames = taskThreePage.saveAllItemImageNamesWhileScrolling();
    }

    @Then("I validate image names matches from the image name pool")
    public void iValidateImageNamesMatchesFromTheImageNamePool() {
        Set<String> invalidImageNames = taskThreePage.getInvalidImageNames(savedImageNames);
        AssertionUtil.assertSetIsEmpty(invalidImageNames, "Found invalid image names", driver);
    }

    // Part 4
    @And("I enable all section one checkboxes")
    public void iEnableAllSectionOneCheckboxes() {
    }

    @Then("I see a pop-up window with a message {string} for section {int}")
    public void iSeeAPopUpWindowWithAMessageForSection(String arg0, int arg1) {
    }

    @And("I enable all required section two checkboxes")
    public void iEnableAllRequiredSectionTwoCheckboxes() {
    }

    @And("I enable all section three checkboxes that have {string} under them")
    public void iEnableAllSectionThreeCheckboxesThatHaveYesUnderThem() {
    }
}
