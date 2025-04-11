package coopbank.co.ke.knobv1.gateway;


import org.springframework.integration.annotation.MessagingGateway;

/**
 * Author  : jgichiri
 * Package : coopbank.co.ke.knobv1.gateway
 * GitHub  : jamogeen
 * Date    : 10/04/2025
 * Project : KnobV1
 */


@MessagingGateway(defaultRequestChannel = "tcpOutboundChannel")
public interface TcpGateway {
    void sendAsync(byte[] message);
}

