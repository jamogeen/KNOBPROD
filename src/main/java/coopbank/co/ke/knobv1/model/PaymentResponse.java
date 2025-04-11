package coopbank.co.ke.knobv1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class PaymentResponse {
    // Getters and setters
    private String mti;
    private String status;
    private String responseCode;
    private Map<String, String> fields;

}