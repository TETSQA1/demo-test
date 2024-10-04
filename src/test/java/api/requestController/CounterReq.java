package api.requestController;

import api.responseController.CounterRes;
import api.responseController.DeliveryRes;
import api.responseController.RecipientRes;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import utils.PropertiesLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static utils.StaticData.*;

public class CounterReq {
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    private static final Properties PROP = new PropertiesLoader(FILE_NAME).load();

    public void counterLogInFirstStep() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        JSONObject body = new JSONObject();
        body.put("phone", format("%s-%s", PROP.getProperty("counterMobileNumCode"), PROP.getProperty("counterMobileNum")));
        body.put("requestId", "");
        Response response = given().headers(header).body(body.toString()).when().post("/v1/counter/auth/login-first-step");
        CounterRes.CounterLogInFirstStep res = response.getBody().as(CounterRes.CounterLogInFirstStep.class);
        if (response.statusCode() >= 400) {
            Assert.fail("response for counter " + response.prettyPrint());
        }
        COUNTER_REQUEST_ID = res.requestId;

    }

    public void counterLogInSecondStep() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        JSONObject body = new JSONObject();
        body.put("verificationCode", PROP.getProperty("otp"));
        body.put("requestId", COUNTER_REQUEST_ID);
        Response response = given().headers(header).body(body.toString()).when().post("/v1/counter/auth/login-second-step");
        CounterRes.CounterLogInSecondStep res = response.getBody().as(CounterRes.CounterLogInSecondStep.class);
        KANGURO_POINT_ID = res.counter.kanguroPoint.id;
        COUNTER_ID = res.counter.id;
        COUNTER_NAME = res.counter.name;
        COUNTER_ACCESS_TOKEN = res.accessToken;
    }

    public void byEanAPI(String status, String anotherStatus) {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        for (String eanCode : EAN_LIST) {
            Response response = given().headers(header).header("Authorization", "Bearer " + COUNTER_ACCESS_TOKEN).when().get(format("/v1/counter/parcels/ean/%s", eanCode));
            response.prettyPrint();
            CounterRes.ByEan res = response.getBody().as(CounterRes.ByEan.class);
            if (!(res.status.equalsIgnoreCase(status) || res.status.equalsIgnoreCase(anotherStatus))) {
                System.out.println(eanCode + " is not matched with expected status");
            }
        }
    }

    public void validateAPI() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        JSONObject body = new JSONObject();
        body.put("valid", true);
        body.put("title", "title of incidence");
        body.put("description", "description of incidence");
        JSONArray attachment = new JSONArray();
        body.put("attachments", attachment);
        for (String parcelId : PARCEL_IDS) {
            Response response = given().headers(header).header("Authorization", "Bearer " + COUNTER_ACCESS_TOKEN).when().body(body.toString()).patch(format("/v1/counter/parcels/%s", parcelId));
            if (!(response.statusCode() == 204)) {
                System.out.println(parcelId + " is not validated");
            }
        }
    }

    public void qrCodeActionAPI() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        Response response = given().headers(header).header("Authorization", "Bearer " + COUNTER_ACCESS_TOKEN).when().get(format("/v1/counter/parcels/group/%s/action", RECIPIENT_QRCODE));
        CounterRes.NextAction res = response.getBody().as(CounterRes.NextAction.class);
        if (!res.action.equalsIgnoreCase("deliver-to-recipient")) {
            System.out.println("When hitting the next action API in counter, the action is not matched with expected");
        }
    }

    public void byId(String status, String anotherStatus) {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        for (String parcelId : PARCEL_IDS) {
            Response response = given().headers(header).header("Authorization", "Bearer " + COUNTER_ACCESS_TOKEN).when().get(format("/v1/counter/parcels/%s", parcelId));
            DeliveryRes.ParcelStatusInDeliveryApp res = response.getBody().as(DeliveryRes.ParcelStatusInDeliveryApp.class);
            if (!(res.status.equalsIgnoreCase(status) || res.status.equalsIgnoreCase(anotherStatus))) {
                System.out.println(parcelId + " is not matched with expected status and the actual status is " + res.status);
            }
        }
    }

    public void changeTheStatusForExistingParcels(String tab) {
        if (!PARCEL_IDS.isEmpty()) {
            if (tab.equalsIgnoreCase("on the way")) {
                byEanAPI(PROP.getProperty("createdParcelStatus"), PROP.getProperty("deliveryScanParcelStatus"));
                byId(PROP.getProperty("createdParcelStatus"), PROP.getProperty("deliveryScanParcelStatus"));
                validateAPI();
            }
            byEanAPI(PROP.getProperty("afterDeliveryScanParcelStatue"), PROP.getProperty("CustomerScanParcelStatus"));
            byId(PROP.getProperty("afterDeliveryScanParcelStatue"), PROP.getProperty("CustomerScanParcelStatus"));
            new RecipientReq().generateQRCodeInRecipientApp();
            qrCodeActionAPI();
            validateAPI();
            byId(PROP.getProperty("completedScanning"), PROP.getProperty("completedScanning"));
        } else {
            System.out.println("There is no existing parcels");
        }
    }

    public void getRecipientByPhoneNumber() {
        PARCEL_IDS = new ArrayList<>();
        EAN_LIST = new ArrayList<>();
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        Response response = given().headers(header).header("Authorization", "Bearer " + COUNTER_ACCESS_TOKEN).when().get(format("/v1/counter/parcels/recipients?phone=%s-%s", PROP.getProperty("recipientMobileCode"), PROP.getProperty("recipientMobileNum")));
        if (response.statusCode() == HttpStatus.SC_OK) {
            CounterRes.GetRecipientByPhone res = response.getBody().as(CounterRes.GetRecipientByPhone.class);
            for (int id = 0; id < res.parcels.size(); id++) {
                PARCEL_IDS.add(res.parcels.get(id).id);
                EAN_LIST.add(res.parcels.get(id).EAN);

            }
        } else {
            System.out.println("There is no parcel on the 'In store' tab");
        }
    }

    public void byEanAPIForDeliveryDriver() {
        PARCEL_IDS = new ArrayList<>();
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        Response response = given().headers(header).header("Authorization", "Bearer " + COUNTER_ACCESS_TOKEN).when().get(format("/v1/counter/parcels/ean/%s", EAN_CODE));
        response.prettyPrint();
        CounterRes.ByEan res = response.getBody().as(CounterRes.ByEan.class);
        PARCEL_IDS.add(res.id);

    }


    public void parcelStatusInCounterApp() {
        Map<String, Object> header = new HashMap<>();
        COUNTER_PARCELS_STATUS = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        for (int id = 0; id < PARCEL_IDS.size(); id++) {
            Response response = given().headers(header).header("Authorization", "Bearer " + COUNTER_ACCESS_TOKEN).when().get(format("/v1/counter/parcels/%s", PARCEL_IDS.get(id)));
            RecipientRes.ParcelStatusInRecipientApp res = response.getBody().as(RecipientRes.ParcelStatusInRecipientApp.class);
            COUNTER_PARCELS_STATUS.put(EAN_LIST.get(id), res.status);
        }
    }

}
