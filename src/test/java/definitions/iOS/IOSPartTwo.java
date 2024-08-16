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



public class IOSPartTwo {

    private HomePage homePage = IOSHooks.getHomePage();
    private TaskOnePage taskOnePage = IOSHooks.getTaskOnePage();
    private TaskTwoPage taskTwoPage = IOSHooks.getTaskTwoPage();
    private AppiumDriver driver = IOSHooks.getDriver();

    private String platform = IOSHooks.getPlatform();

    @When("I tap on {string} button to open Part 2 screen")
    public void i_tap_on_button_to_open_part_screen(String string) {
        homePage.clickPartButton(2);
        AssertionUtil.assertEquals(taskTwoPage.getHeaderTitle(), string, driver);
    }

    @And("I save list items while scrolling through the list")
    public void i_save_list_items_while_scrolling_through_the_list(){
        System.out.println(taskTwoPage.saveListItemsWhileScrolling(platform,5));
    }

    @Then("I validate the saved items are in an alphabetical order")
    public void i_validate_the_saved_items_are_in_alphabetical_order() {
    }


    @Then("I validate the {string} has a * symbol added")
    public void i_validate_the_has_a_symbol_added(String itemName) {}


    @Then("I validate the {string} categories are empty")
    public void i_validate_the_categories_are_empty(String categories) {}

    @Then("I validate the {string} categories have 3 or more items")
    public void i_validate_the_categories_have_3_or_more_items(String categories){}

}
