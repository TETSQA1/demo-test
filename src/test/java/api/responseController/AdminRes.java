package api.responseController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class AdminRes {
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class AdminLogInFirstStep {
        public String requestId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class AdminLogInSecondStep {
        public String accessToken;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParcelStatusInAdminPanel {
        public String status;
    }
}
