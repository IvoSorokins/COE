package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Helpers;

import java.time.Duration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TaskThreePage extends BasePage {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @AndroidFindBy(id = "total")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name == \"totalText\"`]")
    RemoteWebElement totalElement;

    @AndroidFindBy(id = "totalBrand")
    @iOSXCUITFindBy(accessibility = "totalBrandText")
    RemoteWebElement totalBrand;


    public TaskThreePage(AppiumDriver driver) {
        super(driver);
    }


    public double saveAllItemPriceSumWhileScrolling() {
        double totalPriceSum = 0.0;
        Set<String> seenPrices = new HashSet<>(); // Use a Set to store unique prices for better performance
        int previousSize = -1; // Track the size of seenPrices after the previous scroll
        boolean reachedEnd = false; // Flag to detect if end of the list is reached

        while (!reachedEnd) {
            List<WebElement> currentVisiblePrices;

            // Determine platform and find elements
            if (Helpers.isIOSPlatform(driver)) {
                currentVisiblePrices = driver.findElements(AppiumBy.iOSNsPredicateString("name == 'priceText'"));
            } else {
                currentVisiblePrices = driver.findElements(AppiumBy.id("fakePrice"));
            }

            // If no more items are visible, break the loop
            if (currentVisiblePrices.isEmpty()) {
                break;
            }

            // Collect prices from visible items
            for (WebElement priceElement : currentVisiblePrices) {
                String priceText = priceElement.getText();

                // Add only unique prices
                if (!seenPrices.contains(priceText)) {
                    seenPrices.add(priceText);
                    double priceValue = Helpers.parseTextToDouble(priceText);
                    totalPriceSum += priceValue;
                }
            }

            // Check if the size of seenPrices has changed
            if (seenPrices.size() == previousSize) {reachedEnd = true;}
            else {
                previousSize = seenPrices.size();
                Helpers.scroll("up", 1, driver);
            }
        }

        return totalPriceSum;
    }

    public double getDisplayedTotalSum() {
        String totalText = totalElement.getText();
        return Helpers.parseTextToDouble(totalText);
    }

    public String getDisplayedBrandName() {
        String totalBrandText = totalBrand.getText(); // Example: "Total (puma): US$587.48"
        String brandName = totalBrandText.replaceAll("Total \\(|\\):.*", ""); // Extracts the brand name
        return brandName.trim(); // Ensure there is no leading or trailing whitespace
    }

    public double getTotalBrandNumber(){
        String totalBrandText = totalBrand.getText();
        String numericValue = totalBrandText.replaceAll("[^0-9,\\.]", "");
        numericValue = numericValue.replace(',', '.');
        return Helpers.parseTextToDouble(numericValue);
    }

    public double saveBrandItemPriceSumWhileScrolling() {
        double calculatedBrandPriceSum = 0.0;
        List<WebElement> currentVisibleItems;
        boolean reachedEnd = false;
        int previousSize = -1;
        Set<String> seenItems = new HashSet<>(); //Track unique items to avoid processing duplicates in the same scroll

        String targetBrand = getDisplayedBrandName();

        while (!reachedEnd) {
            // Find items based on platform
            if (Helpers.isIOSPlatform(driver)) {
                currentVisibleItems = driver.findElements(AppiumBy.xpath("//XCUIElementTypeCollectionView/XCUIElementTypeCell"));
            } else {
                currentVisibleItems = driver.findElements(AppiumBy.xpath("//android.widget.LinearLayout"));
            }

            if (currentVisibleItems.isEmpty()) {break;}

            //Process each visible item to extract brand and price information
            for (WebElement itemElement : currentVisibleItems) {
                String brandText;
                String priceText;

                if (Helpers.isIOSPlatform(driver)) {
                    brandText = itemElement.findElement(AppiumBy.iOSNsPredicateString("name == 'brandText'")).getText();
                    priceText = itemElement.findElement(AppiumBy.iOSNsPredicateString("name == 'priceText'")).getText();
                } else {
                    WebElement brandElement = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("fakeBrand")));
                    WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("fakePrice")));
                    brandText = brandElement.getText();
                    priceText = priceElement.getText();
                }

                brandText = brandText.replace("Brand: ", "").trim();

                // Combine brand and price text to create a unique key to avoid duplicates
                String uniqueItemIdentifier = brandText + ":" + priceText;

                // Process only if this unique item has not been seen in the current scroll
                if (!seenItems.contains(uniqueItemIdentifier)) {seenItems.add(uniqueItemIdentifier);

                    // If the brand matches the target brand, add its price to the total sum
                    if (brandText.equalsIgnoreCase(targetBrand)) {
                        double priceValue = Helpers.parseTextToDouble(priceText);
                        calculatedBrandPriceSum += priceValue;
                    }
                }
            }

            // Check if the set of seen items has changed
            if (seenItems.size() == previousSize) {reachedEnd = true;}
             else {
                previousSize = seenItems.size();
                Helpers.scroll("up", 1, driver);
            }
        }
        return calculatedBrandPriceSum;
    }

    public Set<String> saveAllItemImageNamesWhileScrolling() {
        Set<String> imageNameSet = new HashSet<>();
        int previousSize = -1;
        boolean reachedEnd = false;

        while (!reachedEnd) {
            List<WebElement> currentVisibleItems;

            if (Helpers.isIOSPlatform(driver)) {
                currentVisibleItems = driver.findElements(AppiumBy.iOSNsPredicateString("name == 'imageText'"));
            } else {
                currentVisibleItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.id("fakeImage")));
            }


            if (currentVisibleItems.isEmpty()) {break;}

            for (WebElement itemElement : currentVisibleItems) {
                String imageName = itemElement.getText();
                imageNameSet.add(imageName);
            }


            if (imageNameSet.size() == previousSize) {reachedEnd = true;}
            else {
                previousSize = imageNameSet.size();
                Helpers.scroll("up", 1, driver);
            }
        }
        return imageNameSet;
    }

    public Set<String> getInvalidImageNames(Set<String> savedImageNames) {
        Set<String> expectedImageNames = getExpectedImageNames();
        Set<String> invalidImageNames = new HashSet<>();

        for (String imageName : savedImageNames) {
            if (!expectedImageNames.contains(imageName)) {
                invalidImageNames.add(imageName);
            }
        }
        return invalidImageNames;
    }

    public Set<String> getExpectedImageNames() {
        Set<String> expectedImageNames = new HashSet<>();
        String platformName = String.valueOf(driver.getCapabilities().getPlatformName());

        // Android image name pool
        if (platformName.contains("ANDROID")) {
            expectedImageNames.addAll(Set.of(
                    "FAKEIMAGE-Puppy",
                    "FAKEIMAGE-Godzilla",
                    "FAKEIMAGE-AppleMacBook",
                    "FAKEIMAGE-Kitten",
                    "FAKEIMAGE-ABeautifulBoat",
                    "FAKEIMAGE-Castle",
                    "FAKEIMAGE-SamsungGalaxy",
                    "FAKEIMAGE-Arcade",
                    "FAKEIMAGE-CarThatYouCantAfford",
                    "FAKEIMAGE-JustAPicOfTheFloor",
                    "FAKEIMAGE-SomeRandomQuoteOnANiceBackground",
                    "FAKEIMAGE-NiceShoes",
                    "FAKEIMAGE-Apartment",
                    "FAKEIMAGE-GlassOfWine"

            ));
        }
        // iOS image name pool
        else if (platformName.contains("IOS")) {
            expectedImageNames.addAll(Set.of(
                    "FAKEIMAGE-cactus",
                    "FAKEIMAGE-book",
                    "FAKEIMAGE-phone",
                    "FAKEIMAGE-person",
                    "FAKEIMAGE-watch",
                    "FAKEIMAGE-car",
                    "FAKEIMAGE-macbook"
            ));
        }
        return expectedImageNames;
    }
}
