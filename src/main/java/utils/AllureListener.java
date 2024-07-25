package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class AllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = DriverSetup.getDriver();

        if (driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot", new ByteArrayInputStream(screenshot));
        }
    }

//    private final AllureLifecycle lifecycle = Allure.getLifecycle();
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        Object testClass = result.getInstance();
//        WebDriver driver = DriverSetup.getDriver();
//
//        if (driver != null) {
//            String currentTestCaseId = lifecycle.getCurrentTestCaseOrStep().get();
//            lifecycle.setCurrentTestCase(currentTestCaseId);
//
//            try {
//                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//                Allure.addAttachment("Screenshot", new ByteArrayInputStream(screenshot));
//            } finally {
//                lifecycle.setCurrentTestCase(currentTestCaseId);
//            }
//        }
//    }

}
