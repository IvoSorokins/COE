package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Listener class responsible for capturing screenshots on test success or failure
 */
public class TestListener extends ScreenshotUtil implements ITestListener {

//    /**
//     * Captures a screenshot when a test method succeeds
//     *
//     * @param tr The TestNG test result
//     */
//    public void onTestSuccess(ITestResult tr) {
//        captureScreenshot(tr, "pass");
//    }

    /**
     * Captures a screenshot when a test method fails
     *
     * @param tr The TestNG test result
     */
    @Override
    public void onTestFailure(ITestResult tr) {
        captureScreenshot(tr, "fail");
    }
}
