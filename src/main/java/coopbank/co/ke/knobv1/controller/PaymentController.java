package coopbank.co.ke.knobv1.controller;


import com.solab.iso8583.IsoMessage;
import coopbank.co.ke.knobv1.gateway.TcpGateway;
import coopbank.co.ke.knobv1.model.PaymentRequest;
import coopbank.co.ke.knobv1.model.PaymentResponse;
import coopbank.co.ke.knobv1.services.CorrelationService;
import coopbank.co.ke.knobv1.services.FormattingService;
import coopbank.co.ke.knobv1.services.IsoMessageSender;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

/**
 * Author   :jamesgichiri
 * Package  :coopbank.co.ke.knobv1.controller
 * GitHub   :jamogeen
 * Date     :05-Apr-25
 * Project  :Knob
 */


@RestController
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private CorrelationService correlationService;

    @Autowired
    private FormattingService formattingService;

    @Autowired
    private TcpGateway tcpGateway;

    @Autowired
    private IsoMessageSender isoMessageSender;

    @Value("${tcp.timeout:5000}")
    private int TIMEOUT;




    @PostMapping("/api/dotran")
    public DeferredResult<ResponseEntity<PaymentResponse>> doTran(@RequestBody PaymentRequest request) {
        logger.info("Received payment request:.... MTI:{} with STAN {} ", request.getMti(),request.getStan());
        DeferredResult<ResponseEntity<PaymentResponse>> deferredResult = new DeferredResult<>((long) TIMEOUT);

        // Validate request
        if (request.getFields() == null || request.getStan() == null || request.getMti() == null) {
            PaymentResponse errorResponse = new PaymentResponse();
            errorResponse.setStatus("ERROR");
            errorResponse.setResponseCode("99");
            deferredResult.setResult(ResponseEntity.badRequest().body(errorResponse));
            return deferredResult;
        }

        try {
            // Convert to ISO message
            IsoMessage isoMessage = formattingService.jsonToIso(request);
            String stan = request.getStan();

            System.out.println("Correlation ID: " + stan);

            // Register the request
            CompletableFuture<IsoMessage> responseFuture = correlationService.registerRequest(stan);
            responseFuture.orTimeout(TIMEOUT, TimeUnit.MILLISECONDS);

            isoMessageSender.sendIsoMessage(isoMessage);


            // Handle response
            responseFuture.thenAccept(isoResponse -> {
                PaymentResponse paymentResponse = formattingService.isoToJson(isoResponse);
                logger.info("Request processing ends................: {}", paymentResponse);
                deferredResult.setResult(ResponseEntity.ok(paymentResponse));
            }).exceptionally(ex -> {
                PaymentResponse errorResponse = new PaymentResponse();
                errorResponse.setStatus("ERROR");
                errorResponse.setResponseCode("98");
                errorResponse.setFields(Map.of("error", ex.getMessage()));
                deferredResult.setResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
                return null;
            });

        } catch (Exception ex) {
            PaymentResponse errorResponse = new PaymentResponse();
            errorResponse.setStatus("ERROR");
            errorResponse.setResponseCode("97");
            errorResponse.setFields(Map.of("error", ex.getMessage()));
            deferredResult.setResult(ResponseEntity.badRequest().body(errorResponse));
        }

        return deferredResult;
    }


}