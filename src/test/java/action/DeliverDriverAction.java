package action;


import api.requestController.AdminReq;
import api.requestController.CounterReq;
import api.requestController.DeliveryDriverReq;
import api.requestController.RecipientReq;
import io.appium.java_client.AppiumDriver;
import page.CounterPage;
import page.DeliveryDriverPage;
import utils.PropertiesLoader;

import java.util.Properties;

public class DeliverDriverAction {
    AppiumDriver driver;
    DeliveryDriverPage deliveryDriverPage;
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    public DeliverDriverAction(AppiumDriver driver) {
        this.driver = driver;
        deliveryDriverPage = new DeliveryDriverPage(driver);

    }

    public void login() {
        deliveryDriverPage.clickWhileUsingTheApp();
        deliveryDriverPage.clickPopUpCloseBtn();
        deliveryDriverPage.enterTheDriverNumber();
        deliveryDriverPage.findTheLanguage();
        deliveryDriverPage.clickEmpezar();
        deliveryDriverPage.enterOTP();
    }

    public void changeTheApplicationLanguage() {
        deliveryDriverPage.changeTheLanguageSpanToEng();
    }

    public void createNewDelivery() {
        deliveryDriverPage.clickDeliverTab();
        deliveryDriverPage.searchTheAutomationStore();
        deliveryDriverPage.getTheParcelCountInPeterAutomationBeforeParcelCreate();
        deliveryDriverPage.searchThePixelStore();
        deliveryDriverPage.getTheParcelCountInPixelStoreBeforeParcelCreate();
        deliveryDriverPage.clickTheNewDelivery();
        deliveryDriverPage.clickWhileUsingTheApp();
        deliveryDriverPage.clickTheIHaveHadProblem();
        deliveryDriverPage.clickTheEnterTheBarcodeManuallyOption();
        deliveryDriverPage.enterTheEanCodeManually();
        deliveryDriverPage.clickOnTheContinueBtn();
        deliveryDriverPage.enterCustomerNumber();
        deliveryDriverPage.clickTheAddressField();
        deliveryDriverPage.verifyTheNameIsAutomaticallyUpdated();
        deliveryDriverPage.enterTheAddress();
        deliveryDriverPage.clickTheSelectKangarooPointBtn();
        deliveryDriverPage.selectTheAutomationStore();
        deliveryDriverPage.clickTheDeliveryHereBtn();
        deliveryDriverPage.clickTheSaveBtn();
        deliveryDriverPage.searchTheAutomationStore();
        deliveryDriverPage.verifyTheParcelCountIsDisplayedInReceiveTabInPeterAutomationStore();
        deliveryDriverPage.verifyTheParcelCountIsIncreasedInPeterAutomationAfterParcelCreation();
        deliveryDriverPage.clickTheDeliverCounterBtn();
        deliveryDriverPage.verifyThePackageIsCreated();
    }

    public void assertAll() {
        deliveryDriverPage.callAssertAll();
    }

    public void getParcelStatus() {

        new CounterReq().byEanAPIForDeliveryDriver();
        new RecipientReq().parcelStatusInRecipientApp();
        new AdminReq().parcelStatusInRecipientApp();
        new CounterReq().parcelStatusInCounterApp();
    }

    public void verifyTheParcelStatusIsChanged(String expectedStatus) {
        getParcelStatus();
        deliveryDriverPage.verifyRecipientParcelStatus(expectedStatus);
        deliveryDriverPage.verifyAdminParcelStatus(expectedStatus);
        deliveryDriverPage.verifyCounterAppParcelStatus(expectedStatus);

    }

    public void editParcelInformation() {
        deliveryDriverPage.clickTheCreatedParcel();
        deliveryDriverPage.enterTheNewEanNumber();
        deliveryDriverPage.selectNewCounter();
        deliveryDriverPage.clickPixelStoreSelectBtn();
        deliveryDriverPage.clickTheDeliveryHereBtn();
        deliveryDriverPage.saveBtn();
        deliveryDriverPage.verifyTheParcelCountIsDecreasedInPeterAutomationStore();
        deliveryDriverPage.searchThePixelStore();
        deliveryDriverPage.verifyTheParcelCountIsDisplayedInReceiveTabInPixelStore();
        deliveryDriverPage.verifyTheParcelCountIsIncreasedInPixelStoreAfterParcelCreate();
        deliveryDriverPage.clickThePixelStoreDeliverCounterBTN();
        deliveryDriverPage.clickTheInformationUpdatedParcel();
        deliveryDriverPage.verifyTheEanCodeIsEdited();
        deliveryDriverPage.verifyTheCounterAddressIsEdited();
    }
}
