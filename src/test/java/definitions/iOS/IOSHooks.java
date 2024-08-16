package definitions.iOS;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import pages.HomePage;
import pages.TaskOnePage;
import pages.TaskTwoPage;
import utils.DriverSetup;

public class IOSHooks {

    private static DriverSetup driverSetup;
    private static AppiumDriver driver;
    private static HomePage homePage;
    private static TaskOnePage taskOnePage;
    private static TaskTwoPage taskTwoPage;
    private static final String platform = "iOS";

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

    @Before
    public void before(Scenario scenario){
        driverSetup.beforeScenario(scenario.getName(),platform);

        driver = DriverSetup.getDriver();
        homePage = new HomePage(driver);
        taskOnePage = new TaskOnePage(driver);
        taskTwoPage = new TaskTwoPage(driver);
    }

    @After
    public void tearDown(Scenario scenario){
        // Log message and quit driver
        System.out.println("Ending scenario: " + scenario.getName());
        if (driver != null) {
            driver.quit();
        }
    }

    public static AppiumDriver getDriver() {
        return driver;
    }

    public static HomePage getHomePage() {
        return homePage;
    }

    public static TaskOnePage getTaskOnePage() {
        return taskOnePage;
    }

    public static TaskTwoPage getTaskTwoPage() {
        return taskTwoPage;
    }

    public static String getPlatform(){
        return platform;
    }

}
