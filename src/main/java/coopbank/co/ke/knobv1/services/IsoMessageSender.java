package coopbank.co.ke.knobv1.services;


import com.solab.iso8583.IsoMessage;
import coopbank.co.ke.knobv1.gateway.TcpGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Author  : jgichiri
 * Package : coopbank.co.ke.knobv1.services
 * GitHub  : jamogeen
 * Date    : 10/04/2025
 * Project : KnobV1
 */


@Service
public class IsoMessageSender {

    @Autowired
    private TcpGateway tcpGateway;

    @Autowired
    private IsoMessagePreparer messagePreparer;

    public void sendIsoMessage(IsoMessage message) throws IOException {
        byte[] preparedMessage = message.writeData();
        tcpGateway.sendAsync(preparedMessage);
    }
}