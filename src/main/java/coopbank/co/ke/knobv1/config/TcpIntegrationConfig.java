package coopbank.co.ke.knobv1.config;


import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import coopbank.co.ke.knobv1.serializer.Iso8583LengthHeaderSerializer;
import coopbank.co.ke.knobv1.services.CorrelationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * Author  : jgichiri
 * Package : coopbank.co.ke.knobv1.config
 * GitHub  : jamogeen
 * Date    : 10/04/2025
 * Project : KnobV1
 */


@Configuration
@EnableIntegration
public class TcpIntegrationConfig {

    @Value("${tcp.host}")
    private String host;

    @Value("${tcp.port}")
    private int port;

    @Bean
    public TcpNetClientConnectionFactory clientConnectionFactory() {
        TcpNetClientConnectionFactory factory = new TcpNetClientConnectionFactory(host, port);
        factory.setSingleUse(false);
        factory.setSerializer(new Iso8583LengthHeaderSerializer());
        factory.setDeserializer(new Iso8583LengthHeaderSerializer());
        factory.setSoKeepAlive(true);
        return factory;
    }

    @Bean
    public MessageChannel tcpOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel tcpReplyChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "tcpOutboundChannel")
    public MessageHandler tcpOutboundGateway() {
        TcpOutboundGateway gateway = new TcpOutboundGateway();
        gateway.setConnectionFactory(clientConnectionFactory());
        gateway.setReplyChannel(tcpReplyChannel());
        return gateway;
    }

    @Bean
    @ServiceActivator(inputChannel = "tcpReplyChannel")
    public MessageHandler tcpReplyHandler(CorrelationService correlationService, MessageFactory<IsoMessage> messageFactory) {
        return message -> {
            try {
                byte[] bytes = (byte[]) message.getPayload();
                IsoMessage isoResponse = messageFactory.parseMessage(bytes, 0);
                String stan = isoResponse.getField(11).toString();
                correlationService.completeRequest(stan, isoResponse);
            } catch (Exception ex) {
                correlationService.failRequest("unknown", ex);
            }
        };
    }
}