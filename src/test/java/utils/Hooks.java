package utils;

import api.requestController.AdminReq;
import api.requestController.CounterReq;
import api.requestController.DeliveryDriverReq;
import api.requestController.RecipientReq;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.util.Strings;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

import static Reporting.ComplexReportFactory.*;


public class Hooks {
    public static AppiumDriver driver;
    public ExtentTest test;
    String testClassName;
    String appPackage;
    String appActivity;
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    public static final Properties PROP = new PropertiesLoader(FILE_NAME).load();

    /*public String applicationName = (Strings.isNullOrEmpty(System.getProperty("applicationName"))) ? PROP.getProperty("applicationName") : System.getProperty("applicationName");

    String bsCode = switch (applicationName) {
        case "deliveryApp" -> PROP.getProperty("deliveryBsCode");
        default -> "null";
    };*/
    public Hooks(String appPackage, String appActivity) {
        this.appPackage = appPackage;
        this.appActivity = appActivity;
    }

    @BeforeSuite
    public void setUp() {

        DesiredCapabilities caps = new DesiredCapabilities();
        String envi = Strings.isNullOrEmpty(System.getProperty("env")) ? PROP.getProperty("env") : System.getProperty("env");
        if (envi.equalsIgnoreCase("local")) {
            caps.setCapability("appium:platformVersion", PROP.getProperty("localDeviceVersion"));
            caps.setCapability("appium:deviceName", PROP.getProperty("localDeviceName"));
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:appPackage", appPackage);
            caps.setCapability("appium:appActivity", appActivity);

            try {
                driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else if (envi.equalsIgnoreCase("browserStack")) {
            caps.setCapability("browserstack.user", "");
            caps.setCapability("browserstack.key", "");
            //  caps.setCapability("app", bsCode);
            caps.setCapability("browserstack.idleTimeout", 60);
            caps.setCapability("device", "google pixel 6");
            caps.setCapability("os_version", "12.0");
            caps.setCapability("platform", "Android");
            caps.setCapability("project", "Automation Script");
            caps.setCapability("build", "Android");
            caps.setCapability("allowInvisibleElements", true);
            caps.setCapability("browserstack.debug", true);
            try {
                driver = new AndroidDriver(new URL("http://hub.browserstack.com/wd/hub"), caps);
                JavascriptExecutor jse = driver;
                jse.executeScript("browserstack_executor: " +
                        "{\"action\":\"adbShell\"," +
                        "\"arguments\": {\"command\" : \"wm fixed-to-user-rotation disabled\"}}"
                );
            } catch (Throwable e) {
                System.out.println(e);
            }
        }
    }

    @AfterTest(alwaysRun = true)
    public void close() {
        getExtentReport().flush();
    }

    @BeforeSuite
    public void deleteExistingReport() {
        File reportFolder = new File(System.getProperty("user.dir") + "/reports");
        File[] reportList = reportFolder.listFiles();
        if (reportList != null) {
            for (File report : reportList) {
                if (!report.isDirectory()) {
                    if (report.delete()) {
                        System.out.println(report.getName() + " got deleted");
                    } else {
                        System.err.println(report.getName() + " is not deleted");
                    }
                }
            }
        } else {
            System.out.println("Report folder is empty");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void reportWrapUp(ITestResult result, Method method) {
        String reportsPath = System.getProperty("user.dir") + "/reports";
        testClassName = method.getDeclaringClass().getSimpleName();
        if (!result.isSuccess()) {
            String imageName = method.getName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String imagePath = reportsPath + "/" + imageName;
            File scrFile = driver.getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File(imagePath + ".png"));
                System.out.println(imagePath + ".png");
                Objects.requireNonNull(getTest()).log(LogStatus.FAIL, "Method - " + method.getName() + " failed , see the screenshot - " + imagePath + ".png");
            } catch (Throwable e) {

                Assert.fail("Error while taking screenshot - " + e);
            }
            getTest().log(LogStatus.FAIL, getTest().addScreenCapture("./" + imageName + ".png"));
        }
        try {
            closeTest(test);

        } catch (Throwable e) {
            System.err.println("Not able to close the test " + e);
        }
    }

    @AfterSuite
    public void quit() {
        try {
            driver.quit();
        } catch (Throwable e) {
            System.err.println("Driver is not quit " + e);
        }
    }

    @AfterMethod(alwaysRun = true, dependsOnMethods = {"reportWrapUp"})
    public void appInvoke() {
        ((AndroidDriver) driver).terminateApp(appPackage);
        ((AndroidDriver) driver).activateApp(appPackage);
    }

    @BeforeSuite
    public void APILoginFunction() {
        RecipientReq recipient = new RecipientReq();
        CounterReq counter = new CounterReq();
        DeliveryDriverReq deliveryDriver = new DeliveryDriverReq();
        AdminReq admin = new AdminReq();
        // performing recipient login via API
        recipient.recipientLogInFirstStep();
        recipient.recipientLogInSecondStep();
        //performing counter login via API
        counter.counterLogInFirstStep();
        counter.counterLogInSecondStep();
        // performing delivery driver login via API
        deliveryDriver.deliveryLogInFirstStep();
        deliveryDriver.DeliveryLogInSecondStep();
        // performing admin panel login via API
        admin.adminLogInFirstStep();
        admin.adminLogInSecondStep();
    }
}
