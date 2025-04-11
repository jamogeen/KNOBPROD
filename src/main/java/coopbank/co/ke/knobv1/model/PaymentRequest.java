package coopbank.co.ke.knobv1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class PaymentRequest {
    private Map<String, String> fields;
    @Setter
    private String stan; // System Trace Audit Number
    @Setter
    private String mti;

    public PaymentRequest(String s, Object o, Object o1, Object o2) {
    }


}