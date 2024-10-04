package action;

import api.requestController.AdminReq;
import api.requestController.CounterReq;
import api.requestController.DeliveryDriverReq;
import api.requestController.RecipientReq;
import io.appium.java_client.AppiumDriver;
import page.RecipientPage;
import utils.PropertiesLoader;

import java.util.Properties;

public class RecipientAction {
    RecipientPage recipientPage;
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    public RecipientAction(AppiumDriver driver) {
        recipientPage = new RecipientPage(driver);
    }

    public void login() {
        recipientPage.clickWhileUsingTheApp();
        recipientPage.clickPopUpCloseBtn();

        recipientPage.enterTheMobileNumber();
        recipientPage.findTheLanguage();
        recipientPage.clickEmpezar();
        recipientPage.enterOTP();
        recipientPage.homePageIsDisplayed();

    }

    public void changeTheApplicationLanguage() {
        recipientPage.changeTheLanguageSpanToEng();
    }

    public void getParcelStatus() {
        new CounterReq().byEanAPIForDeliveryDriver();
        new AdminReq().parcelStatusInRecipientApp();
        new DeliveryDriverReq().parcelStatusInDeliveryApp();
        new CounterReq().parcelStatusInCounterApp();


    }

    public void verifyTheParcelStatusChanged(String expectedStatus) {
        getParcelStatus();
        recipientPage.verifyAdminParcelStatus(expectedStatus);
        recipientPage.verifyDeliveryAppParcelStatus(expectedStatus);
        recipientPage.verifyCounterAppParcelStatus(expectedStatus);
    }

    public void verifyTheParcelIsInStore() {
        recipientPage.clickTheReceiveTab();
        recipientPage.clickThePickUpPackagesBtn();
        recipientPage.verifyTheParcelEanCodeIsDisplayedInPackagesToPickUpDrawer();
        recipientPage.clickOnPickUpPackagesBtn();
        recipientPage.getTheQrCode();
    }

    public void verifyTheCustomerRatingIsDisplayed() {
        recipientPage.customerRatingWindowIsIsDisplayed();
        recipientPage.clickTheStarRating();
        recipientPage.clickTheSendBtn();
        recipientPage.verifyTheThankYouToastMessageIsDisplayed();
        recipientPage.clickTheConfirmBtn();
        recipientPage.verifyTheHomePageIsRedirected();


    }

    public void assertAll() {
        recipientPage.callAssertAll();
    }

    public void verifyTheParcelStatusIsCollectedInInventoryTab() {
        recipientPage.clickTheInventorTab();
        recipientPage.clickTheSearchField();
        recipientPage.enterTheEanCode();
        recipientPage.verifyTheCollectedStatusIsDisplayedInInventoryTab();

    }
}
