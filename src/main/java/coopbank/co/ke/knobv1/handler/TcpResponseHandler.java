package coopbank.co.ke.knobv1.handler;

//import coopbank.co.ke.knobv1.services.CorrelationService;
//import com.solab.iso8583.IsoMessage;
//import com.solab.iso8583.MessageFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.stereotype.Component;
//import java.nio.charset.StandardCharsets;
//import java.util.HexFormat;

//@Component
//public class TcpResponseHandler {
//    private static final Logger logger = LoggerFactory.getLogger(TcpResponseHandler.class);
//
//    @Autowired
//    private MessageFactory<IsoMessage> messageFactory;
//
//    @Autowired
//    private CorrelationService correlationService;
//
//    @ServiceActivator(inputChannel = "tcpInputChannel")
//    public void handleResponse(byte[] responseData) {
//        logger.info("Raw response length: {}", responseData.length);
//        try {
//            // 1. Log raw bytes in multiple formats
//            logger.info("Raw response received - Length: {} bytes", responseData.length);
//            logger.debug("Hex dump:\n{}", HexFormat.of().formatHex(responseData));
//            logger.debug("ASCII view:\n{}", new String(responseData, StandardCharsets.ISO_8859_1)
//                    .replaceAll("[^\\p{Print}]", "."));
//
//            // 2. Verify minimum length (4-byte header + at least 1 byte payload)
//            if (responseData.length < 5) {
//                throw new IllegalArgumentException("Response too short. Expected at least 5 bytes, got " + responseData.length);
//            }
//
//            // 3. Extract length header
//            String lengthHeader = new String(responseData, 0, 4, StandardCharsets.ISO_8859_1);
//            logger.debug("Length header: {}", lengthHeader);
//
//            // 4. Parse ISO message
//            IsoMessage response = messageFactory.parseMessage(responseData, 4);
//            String stan = response.getField(11).toString();
//
//            logger.info("Successfully parsed response for STAN: {}", stan);
//            logger.debug("Parsed message:\n{}", response.debugString());
//
//            // 5. Complete the request
//            correlationService.completeRequest(stan, response);
//        } catch (Exception ex) {
//            logger.error("Failed to process TCP response", ex);
//            correlationService.failAllRequests(ex);
//        }
//    }
//}