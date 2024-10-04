package api.responseController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class CounterRes {
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class CounterLogInFirstStep {
        public String requestId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CounterLogInSecondStep {
        public String accessToken;
        public Counter counter;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Counter {
            public String id;
            public KanguroPoint kanguroPoint;
            public String name;

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class KanguroPoint {
                public String id;
            }

        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ByEan {
        public String status;
        public String id;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NextAction {
        public String action;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class GetRecipientByPhone {
        public List<Parcels> parcels;

        @JsonIgnoreProperties(ignoreUnknown = true)

        public static class Parcels {
            public String EAN;
            public String id;
        }
    }
}
