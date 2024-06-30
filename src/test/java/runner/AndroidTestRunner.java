package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

    @CucumberOptions(
            features = {"src/test/resources/features/TestCases.feature"}, // Path to your feature files
            glue = {"test.java.definitions.AndroidPartOne.java"},   // Package where your step definitions are located
            plugin = {"pretty", "html:target/cucumber-reports"}
    )

    public class AndroidTestRunner extends AbstractTestNGCucumberTests {

    }

