package utils;

import io.appium.java_client.AppiumDriver;

import pages.*;


public class PageFactoryUtil {

    public static BasePage getPageObject(AppiumDriver driver, int partNumber) {
        switch (partNumber) {
            case 1:
                return new TaskOnePage(driver);
            case 2:
                return new TaskTwoPage(driver);
            case 3:
                return new TaskThreePage(driver);
            case 4:
                return new TaskFourPage(driver);
            default:
                throw new IllegalArgumentException("Invalid part number: " + partNumber);
        }
    }
}