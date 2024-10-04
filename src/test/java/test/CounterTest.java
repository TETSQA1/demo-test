package test;

import action.CounterAction;

import api.requestController.CounterReq;
import api.requestController.DeliveryDriverReq;
import api.requestController.RecipientReq;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Hooks;


import static Reporting.ComplexReportFactory.getTest;

public class CounterTest extends Hooks {

    public CounterTest() {
        super(PROP.getProperty("counterAppPackage"), PROP.getProperty("counterAppActivity"));
    }


    @Test(priority = 1)
    public void verifyCounterApplicationScanners() {

        test = getTest("verify counter application scanners");
        CounterAction counterAction = new CounterAction(driver);
        RecipientReq recipient = new RecipientReq();
        DeliveryDriverReq deliveryDriver = new DeliveryDriverReq();
        // getting all existing parcel details in delivery app via API
        deliveryDriver.getAllParcelDetails();
        // login into counter application
        counterAction.login();
        // changing the application language to english if it's not selected by default
        counterAction.changeApplicationLanguageToEnglish();
        // using API to get above created parcel status from recipient, delivery driver and admin panel, then verify those status for that parcel showing as created
        counterAction.verifyParcelStatusChanged("created");
        // In Inventory page(counter app), verifying the created parcel is displays in under 'on the way' tab
        counterAction.verifyCreatedDeliveryInOnTheWayTab();
        // performing delivery scan for created parcel in counter app
        counterAction.verifyDeliveryDriverScan();
        // using API to get above created parcel status from recipient, delivery driver and admin panel, then verify those status for that parcel showing as dropped-off
        counterAction.verifyParcelStatusChanged("dropped-off");
        // In Inventory page, verifying the created parcel is moved from 'On the way' tab to 'In store' tab and parcels count is decreased in 'On th way' tab
        counterAction.verifyCreatedDeliveryInStoreTab();
        // Generating the QA code via recipient API
        recipient.generateQRCodeInRecipientApp();
        // performing customer scan for created parcel in counter app
        counterAction.verifyCustomerScan();
        // using API to get above created parcel status from recipient, delivery driver and admin panel, then verify those status for that parcel showing as collected
        counterAction.verifyParcelStatusChanged("collected");
        // verifying the created parcel is removed from 'In store' tab and parcels count is decreased in 'In store' tab
        counterAction.verifyInStoreTabDeliveryCountDecreased();
        counterAction.assertAll();
    }

    @Test(priority = 2)
    public void verifyTheScanningAndValidationOfMultipleParcel() {

        test = getTest("verify counter application scanners for multiple parcel");
        DeliveryDriverReq deliveryDriver = new DeliveryDriverReq();
        CounterAction counterAction = new CounterAction(driver);
        RecipientReq recipient = new RecipientReq();
        // using delivery driver, creating a new parcel
        deliveryDriver.createNewParcel();
        // getting all existing parcel details in delivery app via API
        deliveryDriver.getAllParcelDetails();
        // changing the application language to english if it's not selected by default
        counterAction.changeApplicationLanguageToEnglish();
        // using API to get above created parcel status from recipient, delivery driver and admin panel, then verify those status for that parcel showing as created
        counterAction.verifyParcelStatusChanged("created");
        // In Inventory page(counter app), verifying the created parcel is displays in under 'on the way' tab
        counterAction.verifyCreatedDeliveryInOnTheWayTabForMultipleParcel();
        // performing delivery scan for multiple parcel in counter app
        counterAction.verifyDeliveryDriverScanForMultipleParcel();
        // using API to get above created parcel status from recipient, delivery driver and admin panel, then verify those status for that parcel showing as dropped-off
        counterAction.verifyParcelStatusChanged("dropped-off");
        // In Inventory page, verifying the created parcel is moved from 'On the way' tab to 'In store' tab and parcels count is decreased in 'On th way' tab
        counterAction.verifyCreatedDeliveryInStoreTabForMultipleParcel();
        // Generating the QA code via recipient API
        recipient.generateQRCodeInRecipientApp();
        // performing customer scan for created parcel in counter app
        counterAction.verifyCustomerScanForMultipleParcel();
        // using API to get above created parcel status from recipient, delivery driver and admin panel, then verify those status for that parcel showing as collected
        counterAction.verifyParcelStatusChanged("collected");
        // verifying the created parcel is removed from 'In store' tab and parcels count is decreased in 'In store' tab
        counterAction.verifyInStoreTabDeliveryCountDecreasedForMultipleParcel();
        counterAction.assertAll();
    }

    @Test(priority = 3)
    public void verifyTheRejectPackage() {

        test = getTest("verify the reject package ");
        DeliveryDriverReq deliveryDriver = new DeliveryDriverReq();
        CounterAction counterAction = new CounterAction(driver);
        RecipientReq recipient = new RecipientReq();
        // getting all existing parcel details in delivery app via API
        deliveryDriver.getAllParcelDetails();
        // changing the application language to english if it's not selected by default
        counterAction.changeApplicationLanguageToEnglish();
        // using API to get above created parcel status from recipient, delivery driver and admin panel, then verify those status for that parcel showing as created
        counterAction.verifyParcelStatusChanged("created");
        // In Inventory page(counter app), verifying the created parcel is displays in under 'on the way' tab
        counterAction.verifyCreatedDeliveryInOnTheWayTab();
        // performing delivery scan for created parcel in counter app
        counterAction.verifyDeliveryDriverScan();
        // using API to get above created parcel status from recipient, delivery driver and admin panel, then verify those status for that parcel showing as dropped-off
        counterAction.verifyParcelStatusChanged("dropped-off");
        // In Inventory page, verifying the created parcel is moved from 'On the way' tab to 'In store' tab and parcels count is decreased in 'On th way' tab
        counterAction.verifyCreatedDeliveryInStoreTab();
        // Generating the QA code via recipient API
        recipient.generateQRCodeInRecipientApp();
        //verify The reject package functionality
        counterAction.verifyTheRejectParcelByCustomer();
        //using api to get above reject parcel status from admin panel,recipient,delivery driver
        counterAction.verifyParcelStatusChanged("expired");
       // counterAction.verifyTheReturningExpiredPackage();
        counterAction.assertAll();
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
