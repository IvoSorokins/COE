package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

    @CucumberOptions(
            features = {"src/test/resources/features/TestCases.feature"}, // Path to your feature files
            glue = {"definitions.android"},   // Package where your step definitions are located
            tags = "@PartFour",
            plugin = {"pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"})

    public class AndroidTestRunner extends AbstractTestNGCucumberTests {}
