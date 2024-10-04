package test;

import action.DeliverDriverAction;
import api.requestController.DeliveryDriverReq;
import org.testng.annotations.Test;

import utils.Hooks;


import static Reporting.ComplexReportFactory.getTest;

public class DeliveryDriverTest extends Hooks {

    public DeliveryDriverTest() {
        super(PROP.getProperty("deliveryDriverAppPackage"), PROP.getProperty("deliveryAppActivity"));
    }

    @Test(priority = 1)
    public void verifyTheCreateNewDeliveryParcel() {
        test = getTest("verify the create new delivery parcel");
        DeliverDriverAction deliveryDriverAction = new DeliverDriverAction(driver);
        DeliveryDriverReq deliveryDriver = new DeliveryDriverReq();
        //Login the delivery driver application
        deliveryDriverAction.login();
        //change the application language in to English if needed
        deliveryDriverAction.changeTheApplicationLanguage();
        //create the parcel in delivery driver app
        deliveryDriverAction.createNewDelivery();
        //get the parcel details
        new DeliveryDriverReq().getTheParcelDetails();
        //verify the parcel status through counter,admin,recipient api
        deliveryDriverAction.verifyTheParcelStatusIsChanged(PROP.getProperty("createdParcelStatus"));
        deliveryDriverAction.assertAll();
    }

    @Test(priority = 2)
    public void verifyTheEditParcelInformation() {
        DeliverDriverAction deliveryDriverAction = new DeliverDriverAction(driver);
        test = getTest("verify the edit the parcel information");
        //create the parcel in delivery driver app
        deliveryDriverAction.createNewDelivery();
        //verify the edit parcel information in delivery driver app
        deliveryDriverAction.editParcelInformation();
        deliveryDriverAction.assertAll();
    }
}
