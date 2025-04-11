package coopbank.co.ke.knobv1.handler;

//import coopbank.co.ke.knobv1.services.TcpClientService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.event.EventListener;
//import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
//import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
//import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
//import org.springframework.stereotype.Component;

//@Component
//public class TcpConnectionEventHandler {
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(TcpConnectionEventHandler.class);
//
//
//    @EventListener
//    public void handleTcpConnectionOpen(TcpConnectionOpenEvent event) {
//        logger.info("Connection opened: " + event.getConnectionId());
//    }
//
//    @EventListener
//    public void handleTcpConnectionClose(TcpConnectionCloseEvent event) {
//        logger.error("Connection closed: " + event.getConnectionId());
//    }
//
//    @EventListener
//    public void onConnectionException(TcpConnectionExceptionEvent event) {
//        logger.error("Connection error: {}", event.getConnectionId(), event.getCause());
//    }
//}