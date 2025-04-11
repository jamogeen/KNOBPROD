package coopbank.co.ke.knobv1.services;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.parse.ConfigParser;
import coopbank.co.ke.knobv1.model.ISO8583FieldMapper;
import coopbank.co.ke.knobv1.model.Iso8583FieldParser;
import coopbank.co.ke.knobv1.model.PaymentRequest;
import coopbank.co.ke.knobv1.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Author   :jamesgichiri
 * Package  :coopbank.co.ke.knobv1.services
 * GitHub   :jamogeen
 * Date     :05-Apr-25
 * Project  :Knob
 */
@Service
public class FormattingService {

    @Autowired
    private MessageFactory<IsoMessage> messageFactory;

    Iso8583FieldParser isoparse = new Iso8583FieldParser();
    ISO8583FieldMapper fieldMapper = new ISO8583FieldMapper();

    public FormattingService() throws Exception {
    }

    public IsoMessage jsonToIso(PaymentRequest request) throws IOException {
        messageFactory.setUseBinaryBitmap(true);
        messageFactory.setCharacterEncoding(StandardCharsets.ISO_8859_1.name());

        // Convert hex MTI to integer
        int mti = Integer.parseInt(request.getMti(), 16);
        MessageFactory<IsoMessage> messageFactory = ConfigParser.createFromClasspathConfig("j8583.xml");
        messageFactory.setCharacterEncoding(StandardCharsets.ISO_8859_1.name());
        messageFactory.setUseBinaryBitmap(true);
        System.out.println("MTI: " + Integer.toHexString(mti));
        IsoMessage isoMessage = messageFactory.newMessage(mti);
        isoMessage.setBinaryBitmap(true);

        // Set fields from request
        for (Map.Entry<String, String> entry : request.getFields().entrySet()) {
            String fieldName = entry.getKey();
            int fieldNum = fieldMapper.getFieldValue(fieldName);
            if (fieldNum > 0) {
                String value = entry.getValue();
                // You might want to add type/length handling here
                String[] result = isoparse.getFieldTypeAndLength(request.getMti(), Integer.parseInt(String.valueOf(fieldNum)));

//            if (result == null || result.length < 2){
//                return ResponseEntity.badRequest().body(Map.of("error", "Invalid Format", "details", "error while parsing field: " +fieldKey));
//            }
                String datatype = result[0];
                String length = result[1];

                //System.out.println("Datatype: " + datatype + " Length: " + length);
                isoMessage.setValue(fieldNum, value, IsoType.valueOf(datatype), Integer.parseInt(length))   ;

            }
        }
        System.out.println("Composed ISO message: " + isoMessage.debugString());
        return isoMessage;
    }

    public PaymentResponse isoToJson(IsoMessage isoResponse) {
        PaymentResponse response = new PaymentResponse();
        response.setStatus("SUCCESS");

        // Create fields map
        Map<String, String> fields = new HashMap<>();

        // Set response code from field 39
        if (isoResponse.hasField(39)) {

            String ResponseMTI = Integer.toHexString(isoResponse.getType());

            response.setMti(ResponseMTI);
            response.setResponseCode(isoResponse.getField(39).toString());

            System.out.println("Response ISO: " + isoResponse.debugString());
            System.out.println("Response MTI by Substring: " + ResponseMTI);


            for (int i = 2; i <= 128; i++) {
                if (isoResponse.hasField(i)) {
                    fields.put(fieldMapper.getFieldName(i), isoResponse.getField(i).toString());

                }
            }

        } else {
            response.setResponseCode("99"); // Default error code
        }

        response.setFields(fields);

        return response;
    }
}