package page;

import api.requestController.DeliveryDriverReq;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import org.testng.Assert;
import utils.AppBasePage;
import utils.PropertiesLoader;

import java.util.Properties;

import static java.lang.String.format;
import static utils.StaticData.*;
import static utils.StaticData.EAN_LIST;

public class RecipientPage extends AppBasePage {
    String appLanguage = "";
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    private static final Properties PROP = new PropertiesLoader(FILE_NAME).load();

    public RecipientPage(AppiumDriver driver) {
        super(driver);
    }

    public void clickWhileUsingTheApp() {
        if (findElementByVisibility(By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"), 10) != null) {
            click(By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"), "While using the app", 10);
        } else {
            getTest().log(LogStatus.INFO, "While using the app is not displayed");
        }
    }

    public void clickPopUpCloseBtn() {
        click(By.xpath("//android.widget.Image[contains(@text,'DdXbPL6TF2Jbahfjuv0w_image')]"), "X button", 10);
    }

    public void enterTheMobileNumber() {
        enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"), PROP.getProperty("recipientMobileNum"), "Customer number", 10);
    }

    public void clickEmpezar() {
        String id = (appLanguage.equalsIgnoreCase("english")) ? "Begin" : "Empezar";
        click(AppiumBy.accessibilityId(id), "Empezar", 10);
    }

    public void findTheLanguage() {
        appLanguage = (findElementByVisibility(AppiumBy.accessibilityId("Empezar"), 10) != null) ? "spanish" : "english";
    }

    public void changeTheLanguageSpanToEng() {
        if (appLanguage.equalsIgnoreCase("spanish")) {
            clickOnProfileIcon();
            clickOnConfiguration();
            clickLanguageDropDownIcon();
            selectEnglishLanguage();
            clickBackIcon();
        } else {
            getTest().log(LogStatus.INFO, "The application language is in english");
        }
    }

    public void enterOTP() {
        enter(By.xpath("//android.widget.EditText[@long-clickable='true']"), PROP.getProperty("otp"), "OTP", 10);
    }

    public void homePageIsDisplayed() {
        if (isDisplayed(By.xpath(format("//android.widget.TextView[contains(@text,'%s')]", PROP.getProperty("customerName"))), 10)) {
            getTest().log(LogStatus.PASS, "recipient app Home page is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "recipient app Home page is not displayed");
            Assert.fail("recipient app Home page is not displayed");
        }
    }

    public void clickOnProfileIcon() {
        click(By.xpath("//android.widget.TextView[@text='Perfil']"), "Perfil tab", 30);
    }

    public void clickOnConfiguration() {
        click(AppiumBy.accessibilityId("Configuración"), "Configuración", 10);
    }

    public void clickLanguageDropDownIcon() {
        click(AppiumBy.accessibilityId("Español"), "Language selection dropdown icon", 10);
    }

    public void clickBackIcon() {
        click(AppiumBy.accessibilityId("Back"), "Back", 10);
    }

    public void selectEnglishLanguage() {
        click(By.xpath("//android.widget.TextView[@text='Inglés']"),"English",10);
    }
    public void clickTheReceiveTab() {
        click(AppiumBy.accessibilityId("Receive"), "receive tab", 10);
    }

    public void verifyAdminParcelStatus(String expectedStatus) {
        for(int id = 0; id < EAN_LIST.size(); id++) {
            if (expectedStatus.equalsIgnoreCase(ADMIN_PARCELS_STATUS.get(EAN_LIST.get(id)))) {

                getTest().log(LogStatus.PASS, format("The admin panel parcel %s status is matched with expected status(%s)", EAN_LIST.get(id), expectedStatus));
            } else {
                getTest().log(LogStatus.FAIL, format("The admin panel parcel %s status is not matched with expected status(%s)", EAN_LIST.get(id), expectedStatus));
                getSoftAssert().fail(format("The Admin parcel  %s status is not matched with expected status", EAN_LIST.get(id)));
            }
        }
    }

    public void verifyCounterAppParcelStatus(String expectedStatus) {

        for (int id = 0; id < EAN_LIST.size(); id++) {
            if (expectedStatus.equalsIgnoreCase(COUNTER_PARCELS_STATUS.get(EAN_LIST.get(id)))) {
                getTest().log(LogStatus.PASS, format("The counter app parcel %s status is matched with expected status(%s)", EAN_LIST.get(id), expectedStatus));
            } else {
                getTest().log(LogStatus.FAIL, format("The counter app parcel %s status is matched with expected status(%s)", EAN_LIST.get(id), expectedStatus));
                getSoftAssert().fail(format("The counter driver parcel %s status is not matched with expected status", EAN_LIST.get(id)));
            }
        }
    }

    public void verifyDeliveryAppParcelStatus(String expectedStatus) {
        for (int id = 0; id < EAN_LIST.size(); id++) {
            if (expectedStatus.equalsIgnoreCase(DELIVERY_PARCELS_STATUS.get(EAN_LIST.get(id)))) {
                getTest().log(LogStatus.PASS, format("The delivery app parcel %s status is matched with expected status(%s)", EAN_LIST.get(id), expectedStatus));
            } else {
                getTest().log(LogStatus.FAIL, format("The delivery app parcel %s status is matched with expected status(%s)", EAN_LIST.get(id), expectedStatus));
                getSoftAssert().fail(format("TheDelivery driver parcel %s status is not matched with expected status", EAN_LIST.get(id)));
            }
        }
    }

    public void clickThePickUpPackagesBtn() {
        scroll(451, 1677, 451, 662);
        click(By.xpath(format("//android.widget.TextView[contains(@text,'%s')]//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Pick up packages']", PROP.getProperty("counterName1"))), "Pick up packages Btn", 10);
    }

    public void verifyTheParcelEanCodeIsDisplayedInPackagesToPickUpDrawer() {
        scroll(451, 1677, 451, 662);
        if (isDisplayed(By.xpath(format("//android.widget.TextView[@text='%s']", EAN_CODE)), 10)) {
            getTest().log(LogStatus.PASS, "Created parcel is displayed in packages to pick up drawer");
        } else {
            getTest().log(LogStatus.FAIL, "Created parcel is not displayed in packages to pick up drawer");
            getSoftAssert().fail("Created parcel is displayed in packages to pick up drawer");
        }
    }

    public void clickOnPickUpPackagesBtn() {
        click(AppiumBy.accessibilityId("Pick up packages"), "pick up packages", 10);
    }

    public void getTheQrCode() {
        RECIPIENT_QRCODE = getAttribute(By.xpath("//android.widget.SeekBar[@content-desc='Bottom Sheet']//android.view.ViewGroup[2]//android.widget.TextView"), "text", "Qr code", 30);
    }

    public void clickTheInventorTab() {
        click(AppiumBy.accessibilityId("Inventory"), "Inventory tab", 10);
    }

    public void clickTheSearchField() {
        click(By.xpath("//android.widget.EditText[@resource-id='search-bar']"), "seach bar", 10);
    }

    public void enterTheEanCode() {
        enter(By.xpath("//android.widget.EditText[@resource-id='search-bar']"), EAN_CODE, "Eancode", 10);
    }

    public void verifyTheCollectedStatusIsDisplayedInInventoryTab() {
        if (isDisplayed(By.xpath(format("//android.view.ViewGroup[@content-desc='%s, COLLECTED']", EAN_CODE)), 10)) {
            getTest().log(LogStatus.PASS, "Parcel Collected status is displayed in Inventory tab");
        } else {
            getTest().log(LogStatus.FAIL, "Parcel Collected status is displayed in Inventory tab");
            getSoftAssert().fail("Parcel Collected status is displayed in Inventory tab");
        }
    }

    public void customerRatingWindowIsIsDisplayed() {
        if (isDisplayed(By.xpath("//android.widget.TextView[contains(@text,'How was your Kanguro experience')]"), 120)) {
            getTest().log(LogStatus.PASS, "Customer rating window is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Customer rating window is not displayed");
            getSoftAssert().fail("Customer rating window is not displayed");
        }
    }

    public void clickTheStarRating() {
        click(By.xpath("//android.widget.TextView[contains(@text,'How was your Kanguro experience')]//following-sibling::android.view.ViewGroup[3]"), "Customer rating", 10);
    }

    public void clickTheSendBtn() {
        click(AppiumBy.accessibilityId("Send"), "Customer rating send Btn", 10);
    }

    public void verifyTheThankYouToastMessageIsDisplayed() {
        if (isDisplayed(By.xpath("//android.widget.TextView[contains(@text,'Thanks for your feedback')]"), 10)) {
            getTest().log(LogStatus.PASS, "Thank you feed back toast message is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Thank you feed back toast message is not displayed");
            getSoftAssert().fail("Thank you feed back toast message is not displayed");
        }
    }

    public void clickTheConfirmBtn() {
        isDisplayed(By.xpath("//android.widget.TextView[contains(@text,'You already have your packages')]"), 30);
        click(AppiumBy.accessibilityId("Confirm"), "confirm Btn", 10);
    }

    public void verifyTheHomePageIsRedirected() {
        if (isDisplayed(By.xpath("//android.widget.TextView[contains(@text,'Hella automation')]"), 10)) {
            getTest().log(LogStatus.PASS, "After click the Confirm Btn Its redirected to the home page");
        } else {
            getTest().log(LogStatus.FAIL, "After click the Confirm Btn Its not redirect the home page");
            getSoftAssert().fail("After click the Confirm Btn Its not redirect to the home page");
        }
    }

    public void callAssertAll() {
        assertAll();
    }
}
