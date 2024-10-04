package test;

import action.RecipientAction;
import api.requestController.CounterReq;
import api.requestController.DeliveryDriverReq;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Hooks;


import static Reporting.ComplexReportFactory.getTest;

public class RecipientTest extends Hooks {


    public RecipientTest() {
        super(PROP.getProperty("recipientAppPackage"), PROP.getProperty("recipientActivity"));
    }

    @Test
    public void verifyTheParcelStatusOnInventoryPage() {
        test = getTest("verify the parcel status on inventory tab");
        DeliveryDriverReq deliveryDriver = new DeliveryDriverReq();
        RecipientAction recipientAction = new RecipientAction(driver);
        CounterReq counter = new CounterReq();
        //getting all existing parcel details in delivery app via API
        deliveryDriver.getAllParcelDetails();
        //verify the parcel status through admin,delivery driver api
        recipientAction.verifyTheParcelStatusChanged("created");
        //performing driver scan through counter api
        counter.validateAPI();
        //verify the parcel status through admin and delivery driver api
        recipientAction.verifyTheParcelStatusChanged("dropped-off");
        //login the recipient application
        recipientAction.login();
        //change the application language in to English if needed
        recipientAction.changeTheApplicationLanguage();
        //verify the parcel in pickup to packages drawer and get the QR code in recipient app
        recipientAction.verifyTheParcelIsInStore();
        //pass the Qr code and validate it through counter api
        counter.qrCodeActionAPI();
        //performing customer scan through counter api
        counter.validateAPI();
        //verify the customer rating functionality and verify thank you feedback toast message is displayed
        recipientAction.verifyTheCustomerRatingIsDisplayed();
        //verify the parcel status through admin and delivery driver,counter api
        recipientAction.verifyTheParcelStatusChanged("collected");
        //verify the parcel status on inventory tab
        recipientAction.verifyTheParcelStatusIsCollectedInInventoryTab();
        recipientAction.assertAll();
    }

    @BeforeMethod
    public void createNewParcel() {
        CounterReq counter = new CounterReq();
        DeliveryDriverReq deliveryDriver = new DeliveryDriverReq();
        // getting all existing parcel details in delivery app via API
        deliveryDriver.getAllParcelDetails();
        // using counter validate API, moving all the existing parcel status to collected
        counter.changeTheStatusForExistingParcels("on the way");
        // getting all existing parcel details in counter app via API
        counter.getRecipientByPhoneNumber();
        // using counter validate API, moving all the existing parcel status to collected
        counter.changeTheStatusForExistingParcels("In store");
        // using delivery driver, creating a new parcel
        deliveryDriver.createNewParcel();
    }
}
