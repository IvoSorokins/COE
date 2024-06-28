package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for capturing screenshots
 */
public class ScreenshotUtil {

    /**
     * Captures a screenshot and saves it to the appropriate directory based on test status
     *
     * @param result The TestNG test result
     */
    public void captureScreenshot(ITestResult result, String status) {
        String destDir = "";
        String passFailMethod = result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();

        File scrFile = DriverSetup.driver.getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");

        if (status.equalsIgnoreCase("fail")) {
            destDir = "screenshots/Failures";
        }

        new File(destDir).mkdirs();
        String destFile = passFailMethod + " - " + dateFormat.format(new Date()) + ".png";

        try {
            FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot");
        }
    }
}
