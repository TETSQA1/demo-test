package page;


import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.AppBasePage;
import utils.PropertiesLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static java.lang.String.format;
import static utils.StaticData.*;
import static utils.StaticData.EAN_LIST;


public class DeliveryDriverPage extends AppBasePage {
    String appLanguage = "";
    String Address;
    String counterAddress;
    int automationStoreParcelCount;
    int initialAutomationStoreParcelCount;
    int initialPixelStoreParcelCount;
    int pixelStoreParcelCount;
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    private static final Properties PROP = new PropertiesLoader(FILE_NAME).load();

    public DeliveryDriverPage(AppiumDriver driver) {
        super(driver);
    }

    public void enterTheDriverNumber() {
        enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"), PROP.getProperty("deliveryMobileNum"), "Delivery driver number", 10);
    }

    public void clickDeliverTab() {
        click(By.xpath("//android.widget.TextView[@text='Deliver']"), "Deliver tab", 30);
    }

    public void clickTheNewDelivery() {
        click(By.xpath("//android.widget.TextView[@text=\"There are no results related to your search\"]//following-sibling::android.view.View"), "New delivery Btn", 10);
    }

    public void clickTheIHaveHadProblem() {
        click(By.xpath("//android.widget.TextView[@text='I have had a problem']"), "I have had problem", 10);
    }

    public void clickTheEnterTheBarcodeManuallyOption() {
        click(By.xpath("//android.view.ViewGroup[contains(@content-desc,'Enter the barcode manually,')]"), "click the Enter the Barcode manually option ", 10);
    }

    public void enterTheEanCodeManually() {
        EAN_CODE = new SimpleDateFormat("HHmmssddMMyy").format(new Date());
        enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"), EAN_CODE, "enter the Ean code", 10);
    }

    public void clickOnTheContinueBtn() {
        click(AppiumBy.accessibilityId("Continue"), "Continue Btn", 10);
    }

    public void enterCustomerNumber() {
        clickAndEnter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined' and @text='Phone']"), PROP.getProperty("recipientMobileNum"), "Enter Customer number", 10);

    }

    public void verifyTheNameIsAutomaticallyUpdated() {
        if (isDisplayed(By.xpath(format("//android.widget.EditText[@resource-id='text-input-outlined' and @text='%s']", PROP.getProperty("customerName"))), 30)) {
            getTest().log(LogStatus.PASS, "Customer Name is Updated automatically");
        } else {
            getTest().log(LogStatus.FAIL, "Customer Name is not Updated");
            getSoftAssert().fail("Customer Name is not Updated ");
        }
    }

    public void clickTheAddressField() {
        scroll(451, 900, 451, 300);
        click(By.xpath("//android.widget.EditText[@text='Address']"), "Address field", 10);
    }

    public void enterTheAddress() {
        enter(By.xpath("//android.widget.EditText[@text='Address']"), PROP.getProperty("address"), "Address", 10);
        Address = PROP.getProperty("address");
        scroll(451, 900, 451, 300);
        click(By.xpath(format("//android.view.ViewGroup[@content-desc=\"Sant Cugat del Vallès, Spain\"]/android.view.ViewGroup\n", PROP.getProperty("address"))), "address", 10);
    }

    public void clickTheSelectKangarooPointBtn() {
        click(By.xpath("//android.widget.TextView[@text=\"Select Kanguro point\"]//parent::android.view.ViewGroup"), "Select Kanguro point Btn",30 );
    }

    public void clickWhileUsingTheApp() {
        if (findElementByVisibility(By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"), 10) != null) {
            click(By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"), "While using the app", 10);
        } else {
            getTest().log(LogStatus.INFO, "While using the app is not displayed");
        }
    }

    public void clickPopUpCloseBtn() {
        click(By.xpath("//android.widget.Image[contains(@text,'gvDVWEO4QeaCCtlHGsXg_image')]"), "X button", 10);
    }


    public void clickEmpezar() {
        String id = (appLanguage.equalsIgnoreCase("english")) ? "Begin" : "Empezar";
        click(AppiumBy.accessibilityId(id), "Empezar", 10);
    }

    public void findTheLanguage() {
        appLanguage = (findElementByVisibility(AppiumBy.accessibilityId("Empezar"), 10) != null) ? "spanish" : "english";
    }

    public void enterOTP() {
        //WebElement otpField = driver.findElement(By.xpath("//android.widget.EditText"));
        enter(By.xpath("//android.widget.EditText"),"1234","otp",20);
        //enter(By.xpath("//android.widget.EditText[@long-clickable='true']"), PROP.getProperty("otp"), "OTP", 15);
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

    public void clickOnProfileIcon() {
        click(AppiumBy.accessibilityId("Perfil"), "profile navigation button", 10);

    }

    public void clickOnConfiguration() {
        click(AppiumBy.accessibilityId("Configuración"), "Configuration", 10);
    }

    public void selectTheAutomationStore() {

        findElementByVisibility(By.xpath("//android.view.View[@resource-id='appbar-content-title-text']"), 10);
        for (int retry = 0; retry <= 2; retry++) {
            if (isDisplayed(By.xpath(format("(//android.view.ViewGroup[@content-desc=\"Deliver counter\"])[1]/android.view.View", PROP.getProperty("counterName1"))), 10)) {
                click(By.xpath(format("(//android.view.ViewGroup[@content-desc=\"Deliver counter\"])[1]/android.view.View", PROP.getProperty("counterName1"))), "Store selection Btn", 10);
                break;
            } else {

                scroll(216, 1382, 221, 242);

            }
        }
    }

    public void clickTheDeliveryHereBtn() {
        click(By.xpath("//android.view.ViewGroup[@content-desc='deliver here']"), "Delivery here Btn", 10);
    }

    public void clickTheSaveBtn() {
        click(By.xpath("//android.widget.TextView[@text='Save']//parent::android.view.ViewGroup"), "save Btn", 10);
    }

    public void clickTheDeliverCounterBtn() {
        click(By.xpath(format("//android.widget.TextView[@text='%s ']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Deliver counter']", PROP.getProperty("counterName1"))), "Deliver counter", 10);
    }

    public void verifyThePackageIsCreated() {
        scroll(532, 1575, 532, 675);
        if (isDisplayed(By.xpath(format("//android.view.ViewGroup[contains(@content-desc,'%s, Created')]", EAN_CODE)), 10)) {
            getTest().log(LogStatus.PASS, "Created parcel is displayed in packages to pick up drawer");
        } else {
            getTest().log(LogStatus.FAIL, "Created parcel is not displayed in packages to pick up drawer");
            getSoftAssert().fail("Created parcel is not displayed in packages to pick up drawer");
        }
    }

    public void verifyRecipientParcelStatus(String expectedStatus) {
        for (int id = 0; id < PARCEL_IDS.size(); id++) {
            if (expectedStatus.equalsIgnoreCase(RECIPIENT_PARCELS_STATUS.get(EAN_LIST.get(id)))) {
                getTest().log(LogStatus.PASS, format("The recipient parcel %s status is matched with expected status(%s)", EAN_LIST.get(id), expectedStatus));
            } else {
                getTest().log(LogStatus.FAIL, format("The recipient parcel %s status is not matched with expected status(%s)", EAN_LIST.get(id), expectedStatus));
                getSoftAssert().fail(format("The Recipient parcel %s status is not matched with expected status", EAN_LIST.get(id)));

            }
        }
    }

    public void verifyAdminParcelStatus(String expectedStatus) {
        for (int id = 0; id < EAN_LIST.size(); id++) {
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

    public void clickTheCreatedParcel() {

        click(By.xpath(format("//android.widget.TextView[@text='%s']//parent::android.view.ViewGroup", EAN_CODE)), "created parcel", 10);
    }

    public void clickTheInformationUpdatedParcel() {
        scroll(451, 900, 451, 300);
        click(By.xpath(format("//android.widget.TextView[@text='%s']//parent::android.view.ViewGroup", NEW_EAN_CODE)), "updated parcel", 10);
    }

    public void searchTheAutomationStore() {
        enter(By.xpath("//android.widget.EditText[@resource-id='search-bar']"), PROP.getProperty("counterName1"), "counter name", 10);
    }

    public void getTheParcelCountInPeterAutomationBeforeParcelCreate() {
        WebElement ele = findElementByVisibility(By.xpath(format("//android.widget.TextView[@text='%s ']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Deliver counter']//preceding-sibling::android.view.ViewGroup[@content-desc]", PROP.getProperty("counterName1"))), 10);
        initialAutomationStoreParcelCount = Integer.parseInt((ele != null) ? ele.getAttribute("content-desc") : "0");
    }

    public void searchThePixelStore() {
        enter(By.xpath("//android.widget.EditText[@resource-id='search-bar']"), PROP.getProperty("counterName2"), "counter name", 10);
    }

    public void verifyTheParcelCountIsDisplayedInReceiveTabInPeterAutomationStore() {
        scroll(528, 721, 527, 1575);
        WebElement ele = findElementByVisibility(By.xpath(format("//android.widget.TextView[@text='%s ']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Deliver counter']//preceding-sibling::android.view.ViewGroup[@content-desc]", PROP.getProperty("counterName1"))), 10);
        automationStoreParcelCount = Integer.parseInt((ele != null) ? ele.getAttribute("content-desc") : "0");
        if (isDisplayed(By.xpath(format("//android.widget.TextView[@text='%s ']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Deliver counter']//preceding-sibling::android.view.ViewGroup[@content-desc]", PROP.getProperty("counterName1"))), 10)) {
            getTest().log(LogStatus.PASS, "Parcel count is displayed in peter automation Receive tab");
        } else {
            getTest().log(LogStatus.FAIL, "Parcel count is displayed in peter automation Receive tab");
            getSoftAssert().fail("Parcel count is displayed in Receive tab");
        }
    }

    public void verifyTheParcelCountIsIncreasedInPeterAutomationAfterParcelCreation() {
        if (initialAutomationStoreParcelCount + 1 == automationStoreParcelCount) {
            getTest().log(LogStatus.PASS, "Automation store Parcel count is increased");
        } else {
            getTest().log(LogStatus.FAIL, "Automation store Parcel count is not increased");
            getSoftAssert().fail("Automation store Parcel count is not increased");
        }
    }

    public void verifyTheParcelCountIsDecreasedInPeterAutomationStore() {
        WebElement ele = findElementByVisibility(By.xpath(format("//android.widget.TextView[@text='%s ']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Deliver counter']//preceding-sibling::android.view.ViewGroup[@content-desc]", PROP.getProperty("counterName1"))), 10);
        int count = Integer.parseInt(ele.getAttribute("content-desc"));
        if (automationStoreParcelCount - 1 == count) {
            getTest().log(LogStatus.PASS, "Automation parcel count is decreased");
        } else {
            getTest().log(LogStatus.FAIL, "Automation parcel count is not  decreased");
            getSoftAssert().fail("Automation parcel count is not  decreased");
        }

    }

    public void getTheParcelCountInPixelStoreBeforeParcelCreate() {
        WebElement ele = findElementByVisibility(By.xpath(format("//android.widget.TextView[@text='%s  ']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Deliver counter']//preceding-sibling::android.view.ViewGroup[@content-desc]", PROP.getProperty("counterName2"))), 10);
        initialPixelStoreParcelCount = Integer.parseInt((ele != null) ? ele.getAttribute("content-desc") : "0");
    }

    public void verifyTheParcelCountIsDisplayedInReceiveTabInPixelStore() {
        scroll(451, 900, 451, 300);
        WebElement ele = findElementByVisibility(By.xpath(format("//android.widget.TextView[@text='%s  ']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Deliver counter']//preceding-sibling::android.view.ViewGroup[@content-desc]", PROP.getProperty("counterName2"))), 10);
        pixelStoreParcelCount = Integer.parseInt((ele != null) ? ele.getAttribute("content-desc") : "0");
        if (isDisplayed(By.xpath(format("//android.widget.TextView[@text='%s  ']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Deliver counter']//preceding-sibling::android.view.ViewGroup[@content-desc]", PROP.getProperty("counterName2"))), 10)) {
            getTest().log(LogStatus.PASS, "Parcel count is displayed in pixel store Receive tab");
        } else {
            getTest().log(LogStatus.FAIL, "Parcel count is not displayed in pixel store Receive tab");
            getSoftAssert().fail("Parcel count is not displayed in pixel store Receive tab");
        }
    }

    public void verifyTheParcelCountIsIncreasedInPixelStoreAfterParcelCreate() {
        if (initialPixelStoreParcelCount + 1 == pixelStoreParcelCount) {
            getTest().log(LogStatus.PASS, "Pixel store parcel count is increased");
        } else {
            getTest().log(LogStatus.PASS, "Pixel store parcel count is not increased");
            getSoftAssert().fail("Pixel store parcel count is not increased");
        }
    }

    public void enterTheNewEanNumber() {
        NEW_EAN_CODE = new SimpleDateFormat("HHmmssddMMyy").format(new Date());
        clickAndEnter(By.xpath(format("//android.widget.EditText[@resource-id='text-input-outlined' and @text='%s']", EAN_CODE)), NEW_EAN_CODE, "New Ean Code", 10);
    }


    public void selectNewCounter() {
        scroll(451, 900, 451, 300);
        counterAddress = getAttribute(By.xpath(format("//android.widget.EditText[@resource-id='text-input-outlined' and @text='%s']", PROP.getProperty("automationStoreCounterAddress"))), "text", "Address", 10);
        clickByVisibility(By.xpath("//android.widget.Button[@resource-id='right-icon-adornment']"), "Counter Reset Btn", 10);
        for (int retry = 0; retry < 5; retry++) {
            if (findElementByVisibility(By.xpath("//android.widget.TextView[@text='Pixel store  ']"), 10) != null) {
                break;
            } else {
                clickByVisibility(By.xpath("//android.widget.Button[@resource-id='right-icon-adornment']"), "Counter Reset Btn", 10);
                if (retry == 4) {
                    getTest().log(LogStatus.FAIL, "Counter address reset Btn is not found");
                    Assert.fail("Counter reset Btn is not found");
                }
            }

        }
    }

    public void clickPixelStoreSelectBtn() {
        click(By.xpath(format("//android.widget.TextView[@text='%s  ']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Select']", PROP.getProperty("counterName2"))), "Pixel store select Btn", 10);
    }


    public void saveBtn() {
        click(By.xpath("//android.widget.TextView[@text='Save']//parent::android.view.ViewGroup"), "Save Btn", 10);
    }

    public void clickThePixelStoreDeliverCounterBTN() {

        click(By.xpath(format("//android.widget.TextView[contains(@text,'%s')]//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@content-desc='Deliver counter']", PROP.getProperty("counterName2"))), "Deliver counter Btn", 10);
    }

    public void verifyTheEanCodeIsEdited() {
        if (isDisplayed(By.xpath(format("//android.widget.EditText[@resource-id='text-input-outlined' and @text='%s']", NEW_EAN_CODE)), 10)) {
            getTest().log(LogStatus.PASS, "Ean code is updated");
        } else {
            getTest().log(LogStatus.FAIL, "Ean code is does not updated ");
            getSoftAssert().fail("Ean code is does not updated");
        }

    }

    public void verifyTheCounterAddressIsEdited() {
        if (isDisplayed(By.xpath(format("//android.widget.EditText[@resource-id='text-input-outlined' and @text='%s']", PROP.getProperty("pixelStoreCounterAddress"))), 10)) {
            getTest().log(LogStatus.PASS, "Counter address is updated");
        } else {
            getTest().log(LogStatus.FAIL, "Counter address is not updated");
            getSoftAssert().fail("Counter address is not updated");
        }

    }


    public void clickLanguageDropDownIcon() {
        click(AppiumBy.accessibilityId("Español"), "Language selection dropdown icon", 10);
    }

    public void selectEnglishLanguage() {
        click(By.xpath("//android.widget.TextView[@text='Inglés']"),"English",10);
    }

    public void clickBackIcon() {
        click(AppiumBy.accessibilityId("Back"), "Back", 10);
    }

    public void callAssertAll() {
        assertAll();
    }

}
