package api.responseController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class RecipientRes {
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class recipientFirstLogIn {
        public String requestId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class recipientLogInSecondStep {
        public String accessToken;
        public Recipient recipient;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Recipient {
            public String id;
            public Profile profile;

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Profile {
                public String id;
                public String name;
            }

        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParcelStatusInRecipientApp {
        public String status;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetQRCodeInRecipientApp {
        public String groupId;
    }

}
