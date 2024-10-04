package action;

import api.requestController.AdminReq;
import api.requestController.CounterReq;
import api.requestController.DeliveryDriverReq;
import api.requestController.RecipientReq;
import io.appium.java_client.AppiumDriver;
import page.CounterPage;
import utils.PropertiesLoader;

import java.util.Properties;

public class CounterAction {

    CounterPage counterPage;
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    public CounterAction(AppiumDriver driver) {

        counterPage = new CounterPage(driver);

    }

    public void login() {
        counterPage.clickWhileUsingTheApp();
        counterPage.clickPopUpCloseBtn();
        counterPage.enterMobileNum(prop.getProperty("counterMobileNum"));
        counterPage.findTheLanguage();
        counterPage.clickEmpezar();
        counterPage.enterOTP();
    }

    public void changeApplicationLanguageToEnglish() {

        counterPage.changeTheLanguageSpanToEng();
    }

    public void verifyDeliveryDriverScan() {
        counterPage.clickStartTabPage();
        counterPage.clickOnDeliveryScanBtn();
        counterPage.clickOnDeliveriesScanBtn();
        counterPage.clickWhileUsingTheApp();
        counterPage.clickIHaveHadAProblem();
        // counterPage.clickWhileUsingTheApp();
        counterPage.clickEnterTheBarCodeManuallyOption();
        counterPage.enterTheBarCodeManually();
        counterPage.clickContinueBtn();
        counterPage.clickEndScan();
        counterPage.verifyTheCourierNameInDetailsOfScannedDrawer();
        counterPage.verifyTheBarCodeInDetailsOfScannedDrawer();
        counterPage.clickGoBackToTheBeginning();
    }

    public void verifyDeliveryDriverScanForMultipleParcel() {
        counterPage.clickStartTabPage();
        counterPage.clickOnDeliveryScanBtn();
        counterPage.clickOnDeliveriesScanBtn();
        counterPage.clickWhileUsingTheApp();
        counterPage.clickIHaveHadAProblem();
        // counterPage.clickWhileUsingTheApp();
        counterPage.clickEnterTheBarCodeManuallyOption();
        counterPage.enterTheBarCodeManuallyForMultipleParcel();
        counterPage.clickContinueBtn();
        counterPage.clickEndScan();
        counterPage.verifyTheCourierNameInDetailsOfScannedDrawer();
        counterPage.verifyTheBarCodeInDetailsOfScannedDrawer();
        counterPage.clickGoBackToTheBeginning();

    }

    public void verifyCustomerScan() {
        counterPage.clickStartTabPage();
        counterPage.clickCustomerScanBtn();
        counterPage.clickWhileUsingTheApp();
        counterPage.clickIHaveHadAProblem();
        counterPage.clickTheEnterTheQRCodeManuallyOption();
        counterPage.enterTheQRCodeManually();
        counterPage.clickContinueBtn();
        counterPage.verifyTheCustomerNameInScanPackageBarcodeDrawer();
        counterPage.verifyTheBarcodeInScanPackageBarcodeDrawer();
        counterPage.clickStartScanning();
        counterPage.clickIHaveHadAProblem();
        counterPage.clickEnterTheBarCodeManuallyOption();
        counterPage.enterTheBarCodeManually();
        counterPage.clickContinueBtn();
        counterPage.verifyCustomerScanned();
        counterPage.clickContinueBtn();
        counterPage.verifyDeliverySuccessfullyPage();
        counterPage.clickFinish();
    }

    public void verifyTheRejectParcelByCustomer() {
        counterPage.clickStartTabPage();
        counterPage.clickCustomerScanBtn();
        counterPage.clickWhileUsingTheApp();
        counterPage.clickIHaveHadAProblem();
        counterPage.clickTheEnterTheQRCodeManuallyOption();
        counterPage.enterTheQRCodeManually();
        counterPage.clickContinueBtn();
        counterPage.verifyTheCustomerNameInScanPackageBarcodeDrawer();
        counterPage.verifyTheBarcodeInScanPackageBarcodeDrawer();
        counterPage.clickStartScanning();
        counterPage.clickIHaveHadAProblem();
        counterPage.clickOnAnotherReasonOption();
        counterPage.clickTheReasonField();
        counterPage.enterTheReason();
        counterPage.clickTheSendBtn();
        counterPage.clickTheCameraBtn();
        counterPage.clickTheCameraOption();
        counterPage.clickTheCaptureBtn();
        counterPage.clickTheOkBtn();
        counterPage.clickTheTickIcon();
        counterPage.clickThePhotoConfirmBtn();
        counterPage.clickTheMobileNumberField();
        counterPage.enterTheCustomerNumber();
        counterPage.clickTheCustomerNumberConfirmBtn();
        counterPage.clickThePackageCheckbox();
        counterPage.clickThePackageSelectionSendBtn();
        counterPage.verifyTheRejectPackageProcessCompleted();
        counterPage.clickTheProcessCompletedPopUpClosedBtn();
        counterPage.clickInventoryPage();
        counterPage.clickExpiredTab();
        counterPage.verifyTheEanCodeIsDisplayedInExpiredTab();

    }
    public void verifyTheReturningExpiredPackage(){

        counterPage.clickStartTabPage();
        counterPage.clickOnDeliveryScanBtn();
        counterPage.clickTheReturnsScansBtn();
        counterPage.clickTheKanguroCourier();
        counterPage.clickIHaveHadAProblem();
        counterPage.clickTheEnterTheQRCodeManuallyOption();
        new DeliveryDriverReq().getReturnQrCode();
        counterPage.enterTheReturnQrcode();
        counterPage.clickContinueBtn();
        counterPage.verifyTheRejectedParcelEanCodeIsDisplayedScanPackagesBarcodesDrawer();
        counterPage.clickStartScanning();
        counterPage.clickIHaveHadAProblem();
        counterPage.clickEnterTheBarCodeManuallyOption();
        counterPage.enterTheRejectedParcelEanCode();
        counterPage.clickContinueBtn();
        counterPage.verifyTheScannedPackageEanCodeIsDisplayedInScannedPackagesDrawer();
        counterPage.clickContinueBtn();
        counterPage.verifyTheSuccessfulMessageIsDisplayed();
        counterPage.clickTheFinishBtn();
    }
    public void verifyCustomerScanForMultipleParcel() {
        counterPage.clickStartTabPage();
        counterPage.clickCustomerScanBtn();
        counterPage.clickWhileUsingTheApp();
        counterPage.clickIHaveHadAProblem();
        counterPage.clickTheEnterTheQRCodeManuallyOption();
        counterPage.enterTheQRCodeManually();
        counterPage.clickContinueBtn();
        counterPage.verifyTheCustomerNameInScanPackageBarcodeDrawer();
        counterPage.verifyTheBarcodeInScanPackageBarcodeDrawer();
        counterPage.clickStartScanning();
        counterPage.clickIHaveHadAProblem();
        counterPage.clickEnterTheBarCodeManuallyOption();
        counterPage.enterTheBarCodeManuallyForCustomerScanForMultipleParcel();
        counterPage.clickContinueBtn();
        counterPage.verifyCustomerScanned();
        counterPage.clickContinueBtn();
        counterPage.verifyDeliverySuccessfullyPage();
        counterPage.clickFinish();

    }

    public void verifyCreatedDeliveryInOnTheWayTab() {
        counterPage.clickInventoryPage();
        counterPage.clickTheWayTabInInventoryPage();
        counterPage.verifyCreatedEanCodeInOnTheWayTab();
        counterPage.getOnTheWayDeliveryCount();
    }

    public void verifyCreatedDeliveryInOnTheWayTabForMultipleParcel() {
        counterPage.clickInventoryPage();
        counterPage.clickTheWayTabInInventoryPage();
        counterPage.verifyCreatedEanCodeInOnTheWayTabForMultipleParcel();
        counterPage.getOnTheWayDeliveryCount();
    }

    public void verifyCreatedDeliveryInStoreTab() {
        counterPage.clickInventoryPage();
        counterPage.verifyCreatedEanCodeInStoreTab();
        counterPage.clickTheWayTabInInventoryPage();
        counterPage.verifyCreatedParcelRemovedFromOnTheWayTab();
        counterPage.verifyOnTheWayTabCountDecreased();
        counterPage.getInStoreTabDeliveryCount();
    }

    public void verifyCreatedDeliveryInStoreTabForMultipleParcel() {
        counterPage.clickInventoryPage();
        counterPage.verifyCreatedEanCodeInStoreTabForMultipleParcel();
        counterPage.clickTheWayTabInInventoryPage();
        counterPage.verifyCreatedParcelRemovedFromOnTheWayTabForMultipleParcel();
        counterPage.verifyOnTheWayTabCountDecreasedForMultipleParcel();
        counterPage.getInStoreTabDeliveryCount();
    }

    public void verifyInStoreTabDeliveryCountDecreased() {
        counterPage.clickInventoryPage();
        counterPage.verifyCreatedParcelRemovedFromStoreTab();
        counterPage.verifyInStoreTabCountDecreased();
    }

    public void verifyInStoreTabDeliveryCountDecreasedForMultipleParcel() {
        counterPage.clickInventoryPage();
        counterPage.verifyCreatedParcelRemovedFromStoreTabForMultipleParcel();
        counterPage.verifyInStoreTabCountDecreasedForMultipleParcel();
    }

    public void getParcelStatus() {
        new RecipientReq().parcelStatusInRecipientApp();
        new AdminReq().parcelStatusInRecipientApp();
        new DeliveryDriverReq().parcelStatusInDeliveryApp();

    }

    public void verifyParcelStatusChanged(String expectedStatus) {
        getParcelStatus();
        counterPage.verifyRecipientParcelStatus(expectedStatus);
        counterPage.verifyAdminParcelStatus(expectedStatus);
        counterPage.verifyDeliveryAppParcelStatus(expectedStatus);
    }

    public void assertAll() {
        counterPage.callAssertAll();
    }


}
