package page;



import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.AppBasePage;
import utils.PropertiesLoader;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static java.lang.String.format;
import static utils.StaticData.*;

public class CounterPage extends AppBasePage {
    String appLanguage = "";
    int onTheWayCount;
    int inStoreCount;
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    private static final Properties PROP = new PropertiesLoader(FILE_NAME).load();


    public CounterPage(AppiumDriver driver) {
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
        click(By.xpath("//android.widget.Button//parent::android.view.View//following-sibling::android.view.View//android.widget.Image"), "X button", 60);
    }

    public void findTheLanguage() {
        appLanguage = (findElementByVisibility(AppiumBy.accessibilityId("Empezar"), 10) != null) ? "spanish" : "english";
    }

    public void enterMobileNum(String mobileNumber) {
        enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"), mobileNumber, "mobile number", 10);
    }

    public void clickEmpezar() {
        String id = (appLanguage.equalsIgnoreCase("english")) ? "Begin" : "Empezar";
        click(AppiumBy.accessibilityId(id), "Empezar", 10);
    }

    public void enterOTP() {
        enter(By.xpath("//android.widget.EditText[@long-clickable='true']"), PROP.getProperty("otp"), "OTP", 30);
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
        findElementByVisibility(By.xpath("//android.widget.TextView[@text='Horario comercial']"), 10);
        scroll(451, 1677, 451, 662);
        scroll(451, 1677, 451, 662);
        click(AppiumBy.accessibilityId("Configuración"), "Configuration", 10);
    }

    public void clickLanguageDropDownIcon() {
        click(By.xpath("//android.view.ViewGroup[@content-desc='Español']//android.widget.ImageView"), "Language selection dropdown icon", 10);
    }

    public void selectEnglishLanguage() {
        click(By.xpath("//android.widget.TextView[@text='Inglés']"),"English",10);
    }

    public void clickBackIcon() {
        click(AppiumBy.accessibilityId("Back"), "Back", 10);
    }

    public void clickStartTabPage() {
        click(AppiumBy.accessibilityId("start"), "start tab", 10);
    }

    public void clickOnDeliveryScanBtn() {
        click(By.xpath("//android.widget.TextView[@text='Delivery driver']//following-sibling::android.view.ViewGroup[@content-desc='Scan']"), "delivery scan button", 10);
    }
    public void enterTheReturnQrcode(){
        enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"),RETURN_QRCODE,"Return QR code",10);
    }
    public void verifyTheRejectedParcelEanCodeIsDisplayedScanPackagesBarcodesDrawer(){
        if(isDisplayed(By.xpath(format("//android.widget.TextView[@text='%s']",EAN_CODE)),10)){
            getTest().log(LogStatus.PASS,"Rejected parcel Ean code is displayed in scan packages barcode drawer");
        }
        else {
            getTest().log(LogStatus.FAIL,"Rejected parcel Ean code is not displayed in scan packages barcode drawer");
            getSoftAssert().fail("Rejected parcel Ean code is not displayed in scan packages barcode drawer");
        }
    }

    public void enterTheRejectedParcelEanCode(){
        for(int Ean=0;Ean<REJECTED_EAN_LIST.size();Ean++){
        enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"),REJECTED_EAN_LIST.get(Ean),"Reject parcel EanCode",10);
    }
    }

    public void verifyTheScannedPackageEanCodeIsDisplayedInScannedPackagesDrawer(){
        click(AppiumBy.accessibilityId("See details"),"Scanned details link",10);
        if(isDisplayed(By.xpath(format("//android.widget.TextView[@text='%s']",EAN_CODE)),10)){
            getTest().log(LogStatus.PASS,"Scanned package EanCode is displayed in Scanned package drawer");
        }
        else {
            getTest().log(LogStatus.FAIL,"Scanned package EanCode is not displayed in Scanned package drawer");
            getSoftAssert().fail("Scanned package EanCode is not displayed in Scanned package drawer");
        }
        click(AppiumBy.accessibilityId("Return"),"Return option",10);
    }
    public void verifyTheSuccessfulMessageIsDisplayed(){
        if(isDisplayed(By.xpath("//android.widget.TextView[@text='delivery successful']"),30)){
            getTest().log(LogStatus.PASS,"Delivery successful message is displayed");
        }
        else {
            getTest().log(LogStatus.FAIL,"Delivery successful message is displayed");
            getSoftAssert().fail("Delivery successful message is displayed");
        }
    }
    public void clickTheFinishBtn(){
        click(AppiumBy.accessibilityId("Finish"),"Finish button",10);
    }

    public void clickOnDeliveriesScanBtn() {
        click(By.xpath("//android.widget.TextView[@text='Deliveries']//following-sibling::android.view.ViewGroup[@content-desc='Scan']"), "deliveries scan button", 10);
        findElementByVisibility(By.xpath("//android.widget.TextView[@text='Scan barcode on packages']"), 10);
    }
    public void clickTheReturnsScansBtn(){
        click(By.xpath("//android.widget.TextView[@text='Returns']//following-sibling::android.view.ViewGroup[@content-desc='Scan']"),"Returns scan button",10);
    }
    public void clickTheKanguroCourier(){
        click(By.xpath("//android.view.ViewGroup[@content-desc='Ka, Kanguro']//parent::android.view.ViewGroup"),"Kanguro Courier",10);
    }

    public void clickIHaveHadAProblem() {
        click(AppiumBy.accessibilityId("I have had a problem"), "I have had a problem", 30);
    }

    public void clickEnterTheBarCodeManuallyOption() {
        click(By.xpath("//android.view.ViewGroup[contains(@content-desc,'Enter the barcode manually')]"), "Enter the barcode manually", 60);
    }

    public void clickOnAnotherReasonOption() {
        click(By.xpath("//android.view.ViewGroup[contains(@content-desc,'Another reason, ')]"), "Another Reason option", 10);
    }

    public void clickTheReasonField() {
        click(By.xpath("//android.view.ViewGroup[@resource-id='text-input-outline']"), " Reason field", 10);
    }

    public void enterTheReason() {
        enter(By.xpath("//android.view.ViewGroup[@resource-id='text-input-outline']//following-sibling::android.widget.EditText"), "Parcel seal damaged", "Reason for reject parcel", 30);
    }

    public void clickTheSendBtn() {
        click(AppiumBy.accessibilityId("Send"), "Send Btn", 10);
    }


    public void clickTheCameraBtn() {
        click(AppiumBy.accessibilityId("Camera"), "camera Btn", 10);
    }

    public void clickTheCameraOption() {
        click(By.xpath("//android.widget.TextView[@text='Camera']//parent::android.view.ViewGroup"), "Camera option", 10);
    }

    public void clickTheCaptureBtn() {
        click(AppiumBy.accessibilityId("Take picture"), "Capture Btn", 10);
    }

    public void clickTheOkBtn() {
        click(By.id("com.sec.android.app.camera:id/okay"), "Ok Btn", 10);
    }

    public void clickTheTickIcon() {
        click(By.id("com.kanguro.counter.beta:id/menu_crop"), "Crop Btn", 10);
    }

    public void clickThePhotoConfirmBtn() {
        isDisappear(By.id("com.kanguro.counter.beta:id/toolbar"), 30);
        click(By.xpath("//android.view.ViewGroup[@content-desc='Cancel']//preceding-sibling::android.view.ViewGroup[@content-desc='Confirm']"), "confirm Btn", 60);
    }

    public void clickTheMobileNumberField() {
        click(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"), "click the mobile number field", 10);
    }

    public void enterTheCustomerNumber() {
        enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"), PROP.getProperty("recipientMobileNum"), "Customer number", 10);
    }

    public void clickTheCustomerNumberConfirmBtn() {
        click(By.xpath("//android.view.ViewGroup[@content-desc='Cancel']//preceding-sibling::android.view.ViewGroup[@content-desc='Confirm']"), "customer number confirm Btn", 30);
    }

    public void clickThePackageCheckbox() {
        click(By.xpath(format("//android.widget.TextView[@text='%s']//parent::android.view.ViewGroup//following-sibling::android.widget.CheckBox", EAN_CODE)), "Package checkbox", 10);
    }

    public void clickThePackageSelectionSendBtn() {
        click(AppiumBy.accessibilityId("Send"), "Send Btn", 10);
    }

    public void verifyTheRejectPackageProcessCompleted() {
        if (isDisplayed(By.xpath("//android.widget.TextView[@text='Process completed']"), 10)) {
            getTest().log(LogStatus.PASS, "Reject package process completed message is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Reject package process completed message is not displayed");
            getSoftAssert().fail("Reject package process completed message is not displayed");
        }
    }

    public void clickExpiredTab() {
        click(By.xpath("//android.view.ViewGroup[contains(@content-desc,'Expired ')]"), "Expired tab", 30);
    }

    public void verifyTheEanCodeIsDisplayedInExpiredTab() {

        REJECTED_EAN_LIST=new ArrayList<>();
        clickByVisibility(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), "Customer drop down ", 10);
        if (isDisplayed(By.xpath(format("//android.widget.TextView[@text='EAN: %s']", EAN_CODE)), 10)) {
            REJECTED_EAN_LIST.add(EAN_CODE);
            getTest().log(LogStatus.PASS, "Reject parcel is successfully moved to Expired tap");
        } else {
            getTest().log(LogStatus.FAIL, "Reject parcel is not moved to Expired tap");
            getSoftAssert().fail("Reject parcel is not moved to Expired tap");
        }

    }


    public void clickTheEnterTheQRCodeManuallyOption() {
        click(By.xpath("//android.view.ViewGroup[contains(@content-desc,'Enter the QR code manually')]"), "Enter the QR code manually option", 10);
    }


    public void enterTheBarCodeManually() {
        enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"), EAN_CODE, "barcode code", 10);
    }

    public void enterTheBarCodeManuallyForMultipleParcel() {
        for (int Ean = 0; Ean < EAN_LIST.size(); Ean++) {
            enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"), EAN_LIST.get(Ean), "barcode code", 10);
            if (Ean == EAN_LIST.size() - 1) {
                break;

            }
            clickContinueBtn();
            clickContinueScan();
            clickIHaveHadAProblem();
            clickEnterTheBarCodeManuallyOption();
        }
    }

    public void enterTheBarCodeManuallyForCustomerScanForMultipleParcel() {
        for (int Ean = 0; Ean < EAN_LIST.size(); Ean++) {
            enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"), EAN_LIST.get(Ean), "barcode code", 10);
            if (Ean == EAN_LIST.size() - 1) {
                break;
            }
            clickContinueBtn();
            clickIHaveHadAProblem();
            clickEnterTheBarCodeManuallyOption();

        }
    }

    public void clickContinueBtn() {
        click(AppiumBy.accessibilityId("Continue"), "Continue", 10);
    }

    public void clickEndScan() {
        click(AppiumBy.accessibilityId("End scan"), "End scan", 10);
    }

    public void clickContinueScan() {
        click(AppiumBy.accessibilityId("Continue scanning"), "Continue scanning", 20);
    }

    public void verifyTheCourierNameInDetailsOfScannedDrawer() {
        String courierName = getAttribute(By.xpath("//android.widget.TextView[@text='Courier']//following-sibling::android.widget.TextView"), "text", "courier name", 10);
        if (courierName.equalsIgnoreCase(PROP.getProperty("courierName"))) {
            Objects.requireNonNull(getTest()).log(LogStatus.PASS, format("The %s name is displaying in the details of scanner drawer", PROP.getProperty("courierName")));
        } else {
            getTest().log(LogStatus.FAIL, format("The %s name is not displaying in the details of scanner drawer", PROP.getProperty("courierName")));
            getSoftAssert().fail(format("The %s name is not displaying in the details of scanner drawer", PROP.getProperty("courierName")));
        }
    }

    public void verifyTheBarCodeInDetailsOfScannedDrawer() {
        for (int Ean = 0; Ean < EAN_LIST.size(); Ean++) {
            String barCode = getAttribute(By.xpath(format("//android.widget.TextView[@text='Barcode']//following-sibling::android.widget.TextView[@text='%s']", EAN_LIST.get(Ean))), "text", "barcode", 10);
            if (barCode.equalsIgnoreCase(EAN_LIST.get(Ean))) {
                getTest().log(LogStatus.PASS, format("The Parcel barcode-%s  is displaying in the details of scanned drawer", EAN_LIST.get(Ean)));
            } else {
                getTest().log(LogStatus.FAIL, format("The Parcel barcode-%s  is not displaying in the details of scanned drawer", EAN_LIST.get(Ean)));
                getSoftAssert().fail(format("The Parcel barcode-%s  is not displaying in the details of scanned drawer", EAN_LIST.get(Ean)));
            }
        }
    }

    public void clickGoBackToTheBeginning() {
        click(AppiumBy.accessibilityId("Go back to the beginning"), "Go back to the beginning", 10);
    }

    public void clickCustomerScanBtn() {
        click(By.xpath("//android.widget.TextView[@text='Customer']//following-sibling::android.view.ViewGroup[@content-desc='Scan']"), "customer scan button", 10);
    }

    public void enterTheQRCodeManually() {
        enter(By.xpath("//android.widget.EditText[@resource-id='text-input-outlined']"), RECIPIENT_QRCODE, "QR code", 10);
    }

    public void verifyTheCustomerNameInScanPackageBarcodeDrawer() {
        String customerName = getAttribute(By.xpath(format("//android.widget.TextView[@text='Customer']//following-sibling::android.widget.TextView[@text='%s']", PROP.getProperty("customerName"))), "text", "customer name", 10);
        if (customerName.equalsIgnoreCase(PROP.getProperty("customerName"))) {
            getTest().log(LogStatus.PASS, format("The %s name is displaying in the scan package barcodes with your camera drawer", PROP.getProperty("customerName")));
        } else {
            getTest().log(LogStatus.FAIL, format("The %s name is not displaying in the scan package barcodes with your camera drawer", PROP.getProperty("customerName")));
            getSoftAssert().fail(format("The %s name is not displaying in the scan package barcodes with your camera drawer", PROP.getProperty("customerName")));
        }
    }

    public void verifyTheBarcodeInScanPackageBarcodeDrawer() {
        for (int Ean = 0; Ean < EAN_LIST.size(); Ean++) {
            if (findElementByVisibility(By.xpath(format("//android.widget.TextView[@text='Barcode']//following-sibling::android.widget.TextView[@text='%s']", EAN_LIST.get(Ean))), 10) != null) {
                getTest().log(LogStatus.PASS, format("The Parcel barcode- %s  is displaying in the scan package barcodes with your camera drawer", EAN_LIST.get(Ean)));
            } else {
                getTest().log(LogStatus.FAIL, format("The Parcel barcode- %s is not displaying in the scan package barcodes with your camera drawer", EAN_LIST.get(Ean)));
                getSoftAssert().fail(format("The Parcel barcode- %s is not displaying in the scan package barcodes with your camera drawer", EAN_LIST.get(Ean)));
            }
        }
    }

    public void clickStartScanning() {
        click(AppiumBy.accessibilityId("Start scanning"), "Start scanning", 10);
    }

    public void verifyDeliverySuccessfullyPage() {
        if (findElementByVisibility(AppiumBy.accessibilityId("Finish"), 10) != null) {
            getTest().log(LogStatus.PASS, "The delivery successful page is displaying after scanned all the Ean code ");
        } else {
            getTest().log(LogStatus.FAIL, "The delivery successful page is not displaying after scanned all the Ean code");
            getSoftAssert().fail("The delivery successful page is not displaying after scanned all the Ean code");
        }
    }

    public void clickFinish() {
        click(AppiumBy.accessibilityId("Finish"), "Finish", 10);
    }

    public void verifyCustomerScanned() {
        if (findElementByVisibility(By.xpath(format("//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView[@text='scanned']", PROP.getProperty("customerName"))), 10) != null) {
            getTest().log(LogStatus.PASS, "The customer is scanned");
        } else {
            getTest().log(LogStatus.FAIL, "The customer is not scanned");
            getSoftAssert().fail("The customer is not scanned");
        }
    }

    public void clickInventoryPage() {
        click(By.xpath("//android.widget.TextView[@text='Inventory']"), "Inventory navigation btn", 30);
    }

    public void clickTheWayTabInInventoryPage() {
        click(By.xpath("//android.view.ViewGroup[contains(@content-desc,'On the way')]"), "On the way tab", 10);
    }

    public void verifyCreatedEanCodeInOnTheWayTab() {
        int deliveryCount = findMultipleElementsByVisibility(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 10).size();
        for (int count = 1; count <= deliveryCount; count++) {
            click(By.xpath("(//android.view.ViewGroup[@resource-id='surface']//android.widget.TextView[@text='Delivery man']//following-sibling::android.view.ViewGroup/android.widget.TextView)[" + count + "]"), "dropdown icon", 10);
            if (findElementByVisibility(By.xpath(format("(//android.view.ViewGroup[@resource-id='surface'])[%s]//android.widget.TextView[@text='EAN: %s']", count, EAN_CODE)), 10) != null) {
                getTest().log(LogStatus.PASS, format("Created delivery order Barcode - %s is displaying in the 'On the way' tab of Inventory page", EAN_CODE));
                break;
            } else if (count == deliveryCount) {
                getTest().log(LogStatus.FAIL, format("Created delivery order Barcode - %s is not displaying in the 'On the way' tab of Inventory page", EAN_CODE));
                Assert.fail("Created delivery order is not displaying in the 'On the way' tab ");
            }
        }
    }

    public void verifyCreatedEanCodeInOnTheWayTabForMultipleParcel() {

        int deliveryCount = findMultipleElementsByPresence(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 10).size();
        for (int count = 1; count <= deliveryCount; count++) {
            click(By.xpath("(//android.view.ViewGroup[@resource-id='surface']//android.widget.TextView[@text='Delivery man']//following-sibling::android.view.ViewGroup/android.widget.TextView)[" + count + "]"), "dropdown icon", 10);
            for (int Ean = 0; Ean < EAN_LIST.size(); Ean++) {
                if (findElementByVisibility(By.xpath(format("(//android.view.ViewGroup[@resource-id='surface'])[%s]//android.widget.TextView[@text='EAN: %s']", count, EAN_LIST.get(Ean))), 10) != null) {
                    getTest().log(LogStatus.PASS, format("Created delivery order Barcode - %s is displaying in the 'On the way' tab of Inventory page", EAN_LIST.get(Ean)));
                } else if (count == deliveryCount) {
                    getTest().log(LogStatus.FAIL, format("Created delivery order Barcode - %s is not displaying in the 'On the way' tab of Inventory page", EAN_LIST.get(Ean)));
                    Assert.fail(format("Created delivery order Barcode- %s is not displaying in the 'On the way' tab ", EAN_LIST.get(Ean)));
                }
            }
        }
    }

    public void verifyCreatedEanCodeInStoreTab() {

        List<WebElement> recipientList = new ArrayList<>();
        for (int retry = 1; retry <= 5; retry++) {
            recipientList = findMultipleElementsByVisibility(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 60);
            if (recipientList == null) {
                recipientList = findMultipleElementsByVisibility(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 60);
                if (recipientList != null) {
                    break;
                }
            } else {
                break;
            }
            if (retry == 5) {
                getTest().log(LogStatus.FAIL, "Created delivery order is not displaying in the 'In store' tab of Inventory page ");
                Assert.fail("Created delivery order is not displaying in the 'In store' tab of Inventory page ");
            }
        }
        int deliveryCount = recipientList.size();
        for (int count = 1; count <= deliveryCount; count++) {
            click(By.xpath("(//android.view.ViewGroup[@resource-id='surface']//android.widget.TextView[@text='Addressee']//following-sibling::android.view.ViewGroup/android.widget.TextView)[" + count + "]"), "dropdown icon", 10);
            if (findElementByVisibility(By.xpath(format("(//android.view.ViewGroup[@resource-id='surface'])[%s]//android.widget.TextView[@text='EAN: %s']", count, EAN_CODE)), 10) != null) {
                getTest().log(LogStatus.PASS, format("Created delivery order Barcode- %s is displaying in the 'In store' tab of Inventory page", EAN_CODE));
                break;
            } else if (count == deliveryCount) {
                getTest().log(LogStatus.FAIL, format("Created delivery order Barcode- %s is displaying in the 'In store' tab of Inventory page", EAN_CODE));
                Assert.fail(format("Created delivery order Barcode- %s is not displaying in the 'In store' ", EAN_CODE));
            }

        }
    }


    public void clickTheProcessCompletedPopUpClosedBtn() {
        click(By.xpath("//android.widget.TextView[@text='Process completed']//parent::android.view.ViewGroup//android.view.ViewGroup"), "X Btn", 10);
    }

    public void verifyCreatedEanCodeInStoreTabForMultipleParcel() {
        List<WebElement> recipientList = new ArrayList<>();
        for (int retry = 1; retry <= 5; retry++) {
            recipientList = findMultipleElementsByVisibility(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 60);
            if (recipientList == null) {
                recipientList = findMultipleElementsByVisibility(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 60);
                if (recipientList != null) {
                    break;
                }
            } else {
                break;
            }
            if (retry == 5) {
                getTest().log(LogStatus.FAIL, "Created delivery order is not displaying in the 'In store' tab of Inventory page ");
                Assert.fail("Created delivery order is not displaying in the 'In store' tab of Inventory page ");
            }
        }
        int deliveryCount = recipientList.size();
        for (int count = 1; count <= deliveryCount; count++) {
            click(By.xpath("(//android.view.ViewGroup[@resource-id='surface']//android.widget.TextView[@text='Addressee']//following-sibling::android.view.ViewGroup/android.widget.TextView)[" + count + "]"), "dropdown icon", 10);
            for (int Ean = 0; Ean < EAN_LIST.size(); Ean++) {
                if (findElementByVisibility(By.xpath(format("(//android.view.ViewGroup[@resource-id='surface'])[%s]//android.widget.TextView[@text='EAN: %s']", count, EAN_LIST.get(Ean))), 10) != null) {
                    getTest().log(LogStatus.PASS, format("Created delivery order Barcode- %s is displaying in the 'In store' tab of Inventory page", EAN_LIST.get(Ean)));
                } else if (count == deliveryCount) {
                    getTest().log(LogStatus.FAIL, format("Created delivery order Barcode- %s is displaying in the 'In store' tab of Inventory page", EAN_LIST.get(Ean)));
                    Assert.fail(format("Created delivery order Barcode- %s is not displaying in the 'In store' ", EAN_LIST.get(Ean)));
                }
            }
        }
    }

    public void getOnTheWayDeliveryCount() {
        onTheWayCount = Integer.parseInt(getAttribute(By.xpath("//android.view.ViewGroup[contains(@content-desc,'On the way')]//android.widget.TextView"), "text", "delivery count of On the way tab", 10).split("On the way")[1].trim());
    }


    public void verifyOnTheWayTabCountDecreased() {

        int afterOnTheWayCount = Integer.parseInt(getAttribute(By.xpath("//android.view.ViewGroup[contains(@content-desc,'On the way')]//android.widget.TextView"), "text", "delivery count of On the way tab", 10).split("On the way")[1].trim());
        if (afterOnTheWayCount == onTheWayCount - 1) {
            getTest().log(LogStatus.PASS, "after performing the delivery scan, the 'On the way' tab count is decreased");
        } else {
            getTest().log(LogStatus.FAIL, "after performing the delivery scan, the 'On the way' tab count is not decreased");
            getSoftAssert().fail("after performing the delivery scan, the 'On the way' tab count is not decreased");
        }
    }

    public void verifyOnTheWayTabCountDecreasedForMultipleParcel() {
        int afterOnTheWayCount = Integer.parseInt(getAttribute(By.xpath("//android.view.ViewGroup[contains(@content-desc,'On the way')]//android.widget.TextView"), "text", "delivery count of On the way tab", 10).split("On the way")[1].trim());
        if (afterOnTheWayCount == onTheWayCount - EAN_LIST.size()) {
            getTest().log(LogStatus.PASS, "after performing the delivery scan, the 'On the way' tab count is decreased");
        } else {
            getTest().log(LogStatus.FAIL, "after performing the delivery scan, the 'On the way' tab count is not decreased");
            getSoftAssert().fail("after performing the delivery scan, the 'On the way' tab count is not decreased");
        }
    }

    public void getInStoreTabDeliveryCount() {
        inStoreCount = Integer.parseInt(getAttribute(By.xpath("//android.view.ViewGroup[contains(@content-desc,'In store')]//android.widget.TextView"), "text", "delivery count of In store tab", 10).split("In store")[1].trim());
    }


    public void verifyInStoreTabCountDecreased() {
        int afterInStoreCount = Integer.parseInt(getAttribute(By.xpath("//android.view.ViewGroup[contains(@content-desc,'In store')]//android.widget.TextView"), "text", "delivery count of In store tab", 10).split("In store")[1].trim());
        if (afterInStoreCount == inStoreCount - 1) {
            getTest().log(LogStatus.PASS, "The created delivery order removed from the In store tab");
        } else {
            getTest().log(LogStatus.FAIL, "The created delivery order is not removed from the In store tab");
            getSoftAssert().fail("The created delivery order is not removed from the In store tab");
        }
    }

    public void verifyInStoreTabCountDecreasedForMultipleParcel() {

        int afterInStoreCount = Integer.parseInt(getAttribute(By.xpath("//android.view.ViewGroup[contains(@content-desc,'In store')]//android.widget.TextView"), "text", "delivery count of In store tab", 10).split("In store")[1].trim());
        if (afterInStoreCount == inStoreCount - EAN_LIST.size()) {
            getTest().log(LogStatus.PASS, "The created delivery order removed from the In store tab");
        } else {
            getTest().log(LogStatus.FAIL, "The created delivery order is not removed from the In store tab");
            getSoftAssert().fail("The created delivery order is not removed from the In store tab");
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


    public void verifyCreatedParcelRemovedFromOnTheWayTab() {
        int deliveryCount = (isDisappear(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 10)) ? 0 : findMultipleElementsByVisibility(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 10).size();
        if (deliveryCount != 0) {
            for (int count = 1; count <= deliveryCount; count++) {
                click(By.xpath("(//android.view.ViewGroup[@resource-id='surface']//android.widget.TextView[@text='Delivery man']//following-sibling::android.view.ViewGroup/android.widget.TextView)[" + count + "]"), "dropdown icon", 10);

                if (findElementByVisibility(By.xpath(format("(//android.view.ViewGroup[@resource-id='surface'])[%s]//android.widget.TextView[@text='EAN: %s']", count, EAN_CODE)), 10) != null) {
                    getTest().log(LogStatus.FAIL, format("Created delivery order Barcode- %s is not removed from 'On the way' tab of Inventory page", EAN_CODE));
                    Assert.fail(format("Created delivery order Barcode- %s is  not removed from 'On the way' tab of Inventory page", EAN_CODE));
                } else if (count == deliveryCount) {
                    getTest().log(LogStatus.PASS, format("Created delivery order Barcode- %s is removed from 'On the way' tab of Inventory page", EAN_CODE));
                }
            }
        } else {
            getTest().log(LogStatus.PASS, "Created delivery order  is removed from 'On the way' tab of Inventory page");
        }
    }

    public void verifyCreatedParcelRemovedFromOnTheWayTabForMultipleParcel() {
        int deliveryCount = (isDisappear(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 10)) ? 0 : findMultipleElementsByVisibility(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 10).size();
        if (deliveryCount != 0) {
            for (int count = 1; count <= deliveryCount; count++) {
                click(By.xpath("(//android.view.ViewGroup[@resource-id='surface']//android.widget.TextView[@text='Delivery man']//following-sibling::android.view.ViewGroup/android.widget.TextView)[" + count + "]"), "dropdown icon", 10);
                for (int Ean = 0; Ean < EAN_LIST.size(); Ean++) {
                    if (findElementByVisibility(By.xpath(format("(//android.view.ViewGroup[@resource-id='surface'])[%s]//android.widget.TextView[@text='EAN: %s']", count, EAN_LIST.get(Ean))), 10) != null) {
                        getTest().log(LogStatus.FAIL, format("Created delivery order Barcode- %s is does not removed from 'On the way' tab of Inventory page", EAN_LIST.get(Ean)));
                        Assert.fail(format("Created delivery order Barcode- %s is does not removed from 'On the way' tab of Inventory page", EAN_LIST.get(Ean)));
                    } else if (count == deliveryCount) {
                        getTest().log(LogStatus.PASS, format("Created delivery order Barcode- %s is removed from 'On the way' tab of Inventory page", EAN_LIST.get(Ean)));
                    }
                }
            }
        } else {
            getTest().log(LogStatus.PASS, "Created delivery order is removed from 'On the way' tab of Inventory page");
        }
    }

    public void verifyCreatedParcelRemovedFromStoreTab() {
        int deliveryCount = (isDisappear(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 10)) ? 0 : findMultipleElementsByVisibility(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 10).size();
        if (deliveryCount != 0) {
            for (int count = 1; count <= deliveryCount; count++) {
                click(By.xpath("(//android.view.ViewGroup[@resource-id='surface']//android.widget.TextView[@text='Addressee']//following-sibling::android.view.ViewGroup/android.widget.TextView)[" + count + "]"), "dropdown icon", 10);
                if (findElementByVisibility(By.xpath(format("(//android.view.ViewGroup[@resource-id='surface'])[%s]//android.widget.TextView[@text='EAN: %s']", count, EAN_CODE)), 10) != null) {
                    getTest().log(LogStatus.FAIL, format("Created delivery order Barcode- %s is not removed from 'In store' tab of Inventory page", EAN_CODE));
                    Assert.fail(format("Created delivery order Barcode- %s is does not removed from 'In store' tab of Inventory page", EAN_CODE));
                } else if (count == deliveryCount) {
                    getTest().log(LogStatus.PASS, "Created delivery order is removed from 'In store' tab of Inventory page");
                }
            }
        } else {
            getTest().log(LogStatus.PASS, "Created delivery order is removed from 'In store' tab of Inventory page");

        }
    }

    public void verifyCreatedParcelRemovedFromStoreTabForMultipleParcel() {
        int deliveryCount = (isDisappear(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 10)) ? 0 : findMultipleElementsByVisibility(By.xpath("//android.view.ViewGroup[@resource-id='surface']"), 10).size();
        if (deliveryCount != 0) {
            for (int count = 1; count <= deliveryCount; count++) {
                click(By.xpath("(//android.view.ViewGroup[@resource-id='surface']//android.widget.TextView[@text='Addressee']//following-sibling::android.view.ViewGroup/android.widget.TextView)[" + count + "]"), "dropdown icon", 10);
                for (int Ean = 0; Ean < EAN_LIST.size(); Ean++) {
                    if (findElementByVisibility(By.xpath(format("(//android.view.ViewGroup[@resource-id='surface'])[%s]//android.widget.TextView[@text='EAN: %s']", count, EAN_LIST.get(Ean))), 10) != null) {
                        getTest().log(LogStatus.FAIL, format("Created delivery order Barcode- %s is not removed from 'In store' tab of Inventory page", EAN_LIST.get(Ean)));
                        Assert.fail(format("Created delivery order Barcode- %s is does not removed from 'In store' tab of Inventory page", EAN_LIST.get(Ean)));
                    } else if (count == deliveryCount) {
                        getTest().log(LogStatus.PASS, "Created delivery order is removed from 'In store' tab of Inventory page");
                    }
                }
            }
        } else {
            getTest().log(LogStatus.PASS, "Created delivery order is removed from 'In store' tab of Inventory page");

        }
    }

    public void callAssertAll() {
        assertAll();
    }

}
