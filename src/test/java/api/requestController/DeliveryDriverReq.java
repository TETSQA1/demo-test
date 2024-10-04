package api.requestController;

import api.responseController.DeliveryRes;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import utils.PropertiesLoader;

import java.text.SimpleDateFormat;
import java.util.*;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static utils.StaticData.*;

public class DeliveryDriverReq {
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    private static final Properties PROP = new PropertiesLoader(FILE_NAME).load();

    public void deliveryLogInFirstStep() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        JSONObject body = new JSONObject();
        body.put("phone", format("%s-%s", PROP.getProperty("deliveryMobileCode"), PROP.getProperty("deliveryMobileNum")));
        body.put("requestId", "");
        Response response = given().headers(header).body(body.toString()).when().post("/v1/deliveryDriver/auth/login-first-step");
        if (response.statusCode() >= 400) {
            Assert.fail("response for delivery driver " + response.prettyPrint());
        }
        DeliveryRes.DeliveryLogInFirstStep res = response.getBody().as(DeliveryRes.DeliveryLogInFirstStep.class);
        DELIVERY_REQUEST_ID = res.requestId;
    }

    public void DeliveryLogInSecondStep() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        JSONObject body = new JSONObject();
        body.put("verificationCode", PROP.getProperty("otp"));
        body.put("requestId", DELIVERY_REQUEST_ID);
        Response response = given().headers(header).body(body.toString()).when().post("/v1/deliveryDriver/auth/login-second-step");
        DeliveryRes.DeliveryLogInSecondStep res = response.getBody().as(DeliveryRes.DeliveryLogInSecondStep.class);
        DELIVERY_ACCESS_TOKEN = res.accessToken;
    }

    public void createNewParcel() {
        EAN_CODE = new SimpleDateFormat("HHmmssddMMyy").format(new Date());
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        JSONObject body = new JSONObject();
        JSONObject parcel = new JSONObject();
        body.put("recipientId", RECIPIENT_ID);
        body.put("kanguroPointId", KANGURO_POINT_ID);
        parcel.put("EAN", EAN_CODE);
        parcel.put("originalAddress", PROP.getProperty("address"));
        JSONArray attachmentsList = new JSONArray();
        parcel.put("attachments", attachmentsList);
        parcel.put("adultSignatureRequired", false);
        parcel.put("recipientOnlyRequired", false);
        JSONArray monies = new JSONArray();
        parcel.put("monies", monies);
        body.put("parcel", parcel);
        Response response = given().headers(header).header("Authorization", "Bearer " + DELIVERY_ACCESS_TOKEN).body(body.toString()).when().post("/v1/deliveryDriver/parcels");
        DeliveryRes.Parcel res = response.getBody().as(DeliveryRes.Parcel.class);
        PARCEL_IDS.add(res.id);
    }

    public void getReturnQrCode(){
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        Response response = given().headers(header).header("Authorization", "Bearer " + DELIVERY_ACCESS_TOKEN).when().patch(format("/v1/deliveryDriver/parcels/counter/%s/confirm-return", COUNTER_ID));
        DeliveryRes.Parcel res=response.getBody().as(DeliveryRes.Parcel.class);
        RETURN_QRCODE=res.groupId;
    }

    public void parcelStatusInDeliveryApp() {
        DELIVERY_PARCELS_STATUS = new HashMap<>();
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        for (int id = 0; id < PARCEL_IDS.size(); id++) {
            Response response = given().headers(header).header("Authorization", "Bearer " + DELIVERY_ACCESS_TOKEN).when().get(format("/v1/deliveryDriver/parcels/%s", PARCEL_IDS.get(id)));
            DeliveryRes.ParcelStatusInDeliveryApp res = response.getBody().as(DeliveryRes.ParcelStatusInDeliveryApp.class);
            DELIVERY_PARCELS_STATUS.put(EAN_LIST.get(id), res.status);
        }
    }

    public void getAllParcelDetails() {
        PARCEL_IDS = new ArrayList<>();
        EAN_LIST = new ArrayList<>();
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        Response response = given().headers(header).header("Authorization", "Bearer " + DELIVERY_ACCESS_TOKEN).when().get("/v1/deliveryDriver/parcels/current?pageNumber=0&pageSize=10&collect=true&deliver=true");
        DeliveryRes.getAllParcelDetails res = response.getBody().as(DeliveryRes.getAllParcelDetails.class);
        for (int id = 0; id < res.items.size(); id++) {
            if (res.items.get(id).kanguroPoint.counter.phone.contains(PROP.getProperty("counterMobileNum")) && !(res.items.get(id).status.contains("expired"))) {
                PARCEL_IDS.add(res.items.get(id).id);
                EAN_LIST.add(res.items.get(id).EAN);
            }
        }
    }

    public void getTheParcelDetails() {
        EAN_LIST = new ArrayList<>();
        EAN_LIST.add(EAN_CODE);
    }
}
