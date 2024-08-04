package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

    @CucumberOptions(
            features = {"src/test/resources/features/TestCases.feature"}, // Path to your feature files
            glue = {"definitions.iOS"},   // Package where your step definitions are located
            tags = "@PartOne",
            plugin = {"pretty"})

    public class IOSTestRunner  extends AbstractTestNGCucumberTests {}
