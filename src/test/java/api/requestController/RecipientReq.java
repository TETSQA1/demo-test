package api.requestController;

import api.responseController.RecipientRes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import utils.PropertiesLoader;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static utils.StaticData.*;

public class RecipientReq {
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    private static final Properties PROP = new PropertiesLoader(FILE_NAME).load();

    public void recipientLogInFirstStep() {
        RestAssured.baseURI = PROP.getProperty("apiBaseUrl");
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        JSONObject body = new JSONObject();
        body.put("phone", format("%s-%s", PROP.getProperty("recipientMobileCode"), PROP.getProperty("recipientMobileNum")));
        body.put("requestId", "");
        Response response = given().headers(header).body(body.toString()).when().post("/v1/recipient/auth/login-first-step");
        if (response.statusCode() >= 400) {
            Assert.fail("response for Recipient " + response.prettyPrint());
        }
        RecipientRes.recipientFirstLogIn res = response.getBody().as(RecipientRes.recipientFirstLogIn.class);
        REQUEST_ID = res.requestId;

    }

    public void recipientLogInSecondStep() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        JSONObject body = new JSONObject();
        body.put("verificationCode", PROP.getProperty("otp"));
        body.put("requestId", REQUEST_ID);
        Response response = given().headers(header).body(body.toString()).when().post("/v1/recipient/auth/login-second-step");
        RecipientRes.recipientLogInSecondStep res = response.getBody().as(RecipientRes.recipientLogInSecondStep.class);
        PROFILE_NAME = res.recipient.profile.name;
        RECIPIENT_ID = res.recipient.id;
        RECIPIENT_ACCESS_TOKEN = res.accessToken;
    }

    public void parcelStatusInRecipientApp() {
        Map<String, Object> header = new HashMap<>();
        RECIPIENT_PARCELS_STATUS = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        for (int id = 0; id < PARCEL_IDS.size(); id++) {
            Response response = given().headers(header).header("Authorization", "Bearer " + RECIPIENT_ACCESS_TOKEN).when().get(format("/v1/recipient/parcels/%s", PARCEL_IDS.get(id)));
            RecipientRes.ParcelStatusInRecipientApp res = response.getBody().as(RecipientRes.ParcelStatusInRecipientApp.class);
            RECIPIENT_PARCELS_STATUS.put(EAN_LIST.get(id), res.status);
        }
    }

    public void generateQRCodeInRecipientApp() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        Response response = given().headers(header).header("Authorization", "Bearer " + RECIPIENT_ACCESS_TOKEN).when().patch(format("/v1/recipient/parcels/counter/%s/confirm", COUNTER_ID));
        RecipientRes.GetQRCodeInRecipientApp res = response.getBody().as(RecipientRes.GetQRCodeInRecipientApp.class);
        RECIPIENT_QRCODE = res.groupId;

    }

}
