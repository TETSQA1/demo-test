package api.requestController;

import api.responseController.AdminRes;
import api.responseController.RecipientRes;
import io.restassured.response.Response;
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

public class AdminReq {
    private final static String FILE_NAME = System.getProperty("user.dir") + "/src/test/java/utils/testData.properties";
    private static final Properties PROP = new PropertiesLoader(FILE_NAME).load();

    public void adminLogInFirstStep() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        JSONObject body = new JSONObject();
        body.put("phone", format("%s-%s", PROP.getProperty("adminMobileCode"), PROP.getProperty("adminMobileNum")));
        body.put("password", PROP.getProperty("adminPassword"));
        Response response = given().headers(header).body(body.toString()).when().post("/v1/admin/auth/login-first-step");
        if (response.statusCode() >= 400) {
            Assert.fail("response " + response.prettyPrint());
        }
        AdminRes.AdminLogInFirstStep res = response.getBody().as(AdminRes.AdminLogInFirstStep.class);
        ADMIN_REQUEST_ID = res.requestId;
    }

    public void adminLogInSecondStep() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        JSONObject body = new JSONObject();
        body.put("verificationCode", PROP.getProperty("adminOtp"));
        body.put("requestId", ADMIN_REQUEST_ID);
        Response response = given().headers(header).body(body.toString()).when().post("/v1/admin/auth/login-second-step");
        AdminRes.AdminLogInSecondStep res = response.getBody().as(AdminRes.AdminLogInSecondStep.class);
        ADMIN_ACCESS_TOKEN = res.accessToken;
    }

    public void parcelStatusInRecipientApp() {
        Map<String, Object> header = new HashMap<>();
        ADMIN_PARCELS_STATUS = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        for (int id = 0; id < PARCEL_IDS.size(); id++) {
            Response response = given().headers(header).header("Authorization", "Bearer " + ADMIN_ACCESS_TOKEN).when().get(format("/v1/admin/parcels/%s", PARCEL_IDS.get(id)));
            RecipientRes.ParcelStatusInRecipientApp res = response.getBody().as(RecipientRes.ParcelStatusInRecipientApp.class);
            ADMIN_PARCELS_STATUS.put(EAN_LIST.get(id), res.status);

        }
    }
}
