package utils;

import Reporting.ComplexReportFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumBy;


import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class AppBasePage {
    public AppiumDriver driver;
    private static String exceptionMsg;
    SoftAssert softAssert;

    public AppBasePage(AppiumDriver driver) {
        this.driver = driver;
        softAssert = new SoftAssert();
    }

    public SoftAssert getSoftAssert() {
        return softAssert;
    }

    public void assertAll() {
        softAssert.assertAll();
    }

    public void enter(By by, String value, String name, int timeInSec) {

        try {
            WebElement element = findElementByVisibility(by, timeInSec);
            if (element != null) {
                element.sendKeys(value);
                getTest().log(LogStatus.PASS, name + " field is entered with value \"" + value + "\"");
            } else {
                getTest().log(LogStatus.FAIL, name + " is not found" + exceptionMsg);
                Assert.fail(name + " is not found " + exceptionMsg);
            }
        } catch (Throwable e) {
            getTest().log(LogStatus.FAIL, name + " field is entered with value \"" + value + "\"" + e);
            Assert.fail(name + " field is entered with value \"" + value + "\"" + e);
        }
    }

    public void clickAndEnter(By by, String value, String name, int timeInSec) {

        try {
            WebElement element = findElementByVisibility(by, timeInSec);
            if (element != null) {
                element.click();
                element.clear();
                element.sendKeys(value);
                getTest().log(LogStatus.PASS, name + " field is entered with value \"" + value + "\"");
            } else {
                getTest().log(LogStatus.FAIL, name + " is not found" + exceptionMsg);
                Assert.fail(name + " is not found " + exceptionMsg);
            }
        } catch (Throwable e) {
            getTest().log(LogStatus.FAIL, name + " field is entered with value " + value + " " + e);
            Assert.fail(name + " field is entered with value " + value + " " + e);
        }
    }

    public void clickByVisibility(By by, String name, int timeInSec) {
        try {
            WebElement ele = findElementByVisibility(by, timeInSec);

            if (ele != null) {
                ele.click();
                getTest().log(LogStatus.PASS, name + " is clicked");
            } else {
                getTest().log(LogStatus.FAIL, name + " is  not found");
                getSoftAssert().fail(name + " is not found");
            }
        } catch (Throwable e) {
            exceptionMsg = "Error : " + e;
            getTest().log(LogStatus.FAIL, name + " not found ");
        }

    }

    public void enterUsingAction(By by, String value, String name, int timeInSec) {

        try {
            new Actions(driver).moveToElement(findElementByPresence(by, timeInSec)).sendKeys(value).build().perform();
            getTest().log(LogStatus.PASS, name + " field is entered with value \"" + value + "\"");
        } catch (Throwable e) {
            getTest().log(LogStatus.FAIL, name + " field is entered with value \"" + value + "\"" + e);

        }
    }

    public WebElement findElementByVisibility(By by, int timeInSec) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable e) {
            exceptionMsg = "Error : " + e;
            return null;
        }
    }

    public boolean isDisplayed(By by, int timeInSec) {
        boolean result = false;
        try {
            result = findElementByVisibility(by, timeInSec).isDisplayed();
        } catch (Throwable e) {
            exceptionMsg = "Error : " + e;
        }
        return result;
    }


    public WebElement findElementByClickable(By by, int timeInSec) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
            return wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable e) {
            exceptionMsg = "Error : " + e;
            return null;
        }
    }

    public List<WebElement> findMultipleElementsByVisibility(By by, int timeInSec) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return driver.findElements(by);
        } catch (Throwable e) {
            exceptionMsg = "Error : " + e;
            return null;
        }
    }

    public List<WebElement> findMultipleElementsByPresence(By by, int timeInSec) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        } catch (Throwable e) {
            exceptionMsg = "Error : " + e;
            return null;
        }
    }

    public WebElement findElementByPresence(By by, int timeInSec) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
            return wait.until(presenceOfElementLocated(by));
        } catch (Throwable e) {
            exceptionMsg = "Error : " + e;
            return null;
        }
    }

    public boolean isDisappear(By by, int timeInSec) {
        boolean result = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
            result = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Throwable ignored) {

        }
        return result;
    }

    public void click(By by, String name, int timeInSec) {

        try {
            WebElement element = findElementByVisibility(by, timeInSec);
            if (element != null) {
                element.click();
                getTest().log(LogStatus.PASS, name + " is clicked");
            } else {
                getTest().log(LogStatus.FAIL, name + " is not found and element is null" + exceptionMsg);
                Assert.fail(name + " is not found and element is null ");
            }
        } catch (Throwable e) {
            getTest().log(LogStatus.FAIL, name + " is not clicked" + " " + e);
            Assert.fail(name + " is not clicked" + " " + e);
            System.out.println("while clicking this By " + by + "and error is " + e);
        }
    }

    public void clickByAction(By by, String name, int timeInSec) {
        try {
            new Actions(driver).moveToElement(findElementByPresence(by, timeInSec)).click().build().perform();
            getTest().log(LogStatus.PASS, name + " is clicked");

        } catch (Throwable e) {
            getTest().log(LogStatus.FAIL, name + " is not clicked " + e);
            System.out.println("while clicking this By " + by + "and error is " + e);
            Assert.fail(name + " is not clicked" + " " + e);
        }
    }

    public String getAttribute(By by, String attribute, String name, int timeInSec) {
        String msg = "";
        try {
            WebElement element = findElementByPresence(by, timeInSec);
            if (element != null) {
                msg = element.getAttribute(attribute);
                getTest().log(LogStatus.PASS, name + " field returned the value as \"" + msg + "\" for the attribute \"" + attribute + "\"");
            } else {
                getTest().log(LogStatus.FAIL, by + " is not available ");

            }
        } catch (Throwable e) {
            getTest().log(LogStatus.FAIL, by + " is not available " + e);
        }
        return msg;
    }

    public String currentDate(String patten) {
        return new SimpleDateFormat(patten).format(new Date());
    }

    public String NextDate(String patten) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat(patten).format(cal.getTime());
    }

    public void scroll(String text) {
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(newUiSelector().scrollable(true).scrollIntoView(new UiSelector().textContains('" + text + "')"));

    }

    public List<String> getMultipleElementAttribute(By by, String attribute, int timeInSec) {
        List<String> values = new ArrayList<>();
        try {
            List<WebElement> elements = (List<WebElement>) driver.findElements(by);
            for (WebElement element : elements) {
                values.add(element.getAttribute(attribute));
            }
        } catch (Throwable e) {
            getTest().log(LogStatus.FAIL, by + "is not available " + e);
            Assert.fail(by + "is not available " + e);
        }
        return values;
    }

    /*public void scrolling(By by,int timeInSec,int percentage,String direction) {
        RemoteWebElement scrollView = (RemoteWebElement) findElementByVisibility(by,timeInSec);

        driver.executeScript("gesture: swipe", Map.of("elementId", scrollView.getId(),
                "percentage", percentage,
                "direction", direction));
    }*/

    public void scroll(int startX, int startY, int endX, int endY) {
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence sequence = new Sequence(finger, 1);
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(0),
                    PointerInput.Origin.viewport(), startX, startY));
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(600),
                    PointerInput.Origin.viewport(), endX, endY));
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
            driver.perform(Collections.singletonList(sequence));
            getTest().log(LogStatus.PASS, "Scrolled ");
        } catch (Throwable e) {
            getTest().log(LogStatus.FAIL, "Failed to scroll " + e);
            Assert.fail("Failed to scroll " + e);
        }
    }

    public ExtentTest getTest() {
        return Objects.requireNonNull(ComplexReportFactory.getTest());
    }
}
