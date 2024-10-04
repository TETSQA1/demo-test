package api.responseController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class DeliveryRes {
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class DeliveryLogInFirstStep {
        public String requestId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class DeliveryLogInSecondStep {
        public String accessToken;
        public DeliveryDriver deliveryDriver;

        @JsonIgnoreProperties(ignoreUnknown = true)

        public static class DeliveryDriver {
            public String id;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parcel {
        public String id;
        public String groupId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParcelStatusInDeliveryApp {
        public String status;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class getAllParcelDetails {
        public List<Items> items;

        @JsonIgnoreProperties(ignoreUnknown = true)

        public static class KanguroPoint {
            public Counter counter;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)

        public static class Counter {

            public String id;
            public String status;
            public String name;
            public String phone;
            public String email;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Items {
            public String id;
            public String EAN;
            public String status;
            public KanguroPoint kanguroPoint;

        }
    }


}
