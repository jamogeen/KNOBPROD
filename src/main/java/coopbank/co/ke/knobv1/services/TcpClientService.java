package coopbank.co.ke.knobv1.services;


/**
 * Author   :jamesgichiri
 * Package  :coopbank.co.ke.knobv1.services
 * GitHub   :jamogeen
 * Date     :05-Apr-25
 * Project  :Knob
 */
//@Service
//public class TcpClientService {
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(TcpClientService.class);
//
//    @Autowired
//    private MessageChannel tcpOutputChannel;
//
//    @Autowired
//    private CorrelationService correlationService;
//
//    @Async("isoTaskExecutor")
//    public CompletableFuture<IsoMessage> sendIsoMessage(IsoMessage message) {
//        String stan = message.getField(11).toString();
//        logger.info("Processing STAN: {}", stan);
//
//        byte[] finalMessage = prepareMessage(message);
//
//        int maxAttempts = 3;
//        int attempt = 0;
//        long backoffDelay = 1000; // Initial delay in ms
//
//        while (attempt < maxAttempts) {
//            attempt++;
//            try {
//                logger.info("Attempt {} to send message with STAN: {}", attempt, stan);
//                tcpOutputChannel.send(MessageBuilder.withPayload(finalMessage).build());
//
//                CompletableFuture<IsoMessage> future = new CompletableFuture<>();
//                correlationService.registerRequest(stan, future);
//
//                logger.info("Future sent as : {}", future);
//
//                return future;
//            } catch (Exception e) {
//                logger.error("Attempt {} failed for STAN: {}", attempt, stan, e);
//                if (attempt >= maxAttempts) {
//                    logger.error("All attempts failed for STAN: {}", stan);
//                    throw new RuntimeException("Failed to send message after " + maxAttempts + " attempts", e);
//                }
//
//                try {
//                    Thread.sleep(backoffDelay * attempt); // Exponential backoff
//                } catch (InterruptedException ie) {
//                    Thread.currentThread().interrupt();
//                    throw new RuntimeException("Thread interrupted during backoff", ie);
//                }
//            }
//        }
//        throw new RuntimeException("Unexpected error in sendIsoMessage");
//    }
//
//    private byte[] prepareMessage(IsoMessage message) {
//        byte[] isoMessageBytes = message.writeData();
//        int messageLength = isoMessageBytes.length;
//        String lengthString = String.format("%04d", messageLength);
//        byte[] lengthHeader = lengthString.getBytes(StandardCharsets.ISO_8859_1);
//
//        byte[] finalMessage = new byte[lengthHeader.length + isoMessageBytes.length];
//        System.arraycopy(lengthHeader, 0, finalMessage, 0, lengthHeader.length);
//        System.arraycopy(isoMessageBytes, 0, finalMessage, lengthHeader.length, isoMessageBytes.length);
//
//        return finalMessage;
//    }
//}