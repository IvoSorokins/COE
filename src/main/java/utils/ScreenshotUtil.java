package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.LoggerUtil.logMessage;


public class ScreenshotUtil {

    public static void captureScreenshot(WebDriver driver, String scenarioName, String status) {
        String destDir = "screenshots/" + (status.equalsIgnoreCase("pass") ? "Passes" : "Failures");
        String passFailMethod = scenarioName;

        // Ensure directory exists
        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        TakesScreenshot screenshotDriver = ((TakesScreenshot) driver);
        File scrFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        String destFile = passFailMethod + " - " + dateFormat.format(new Date()) + ".png";

        try {
            FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
            logMessage("Screenshot saved at: " + destDir + "/" + destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
