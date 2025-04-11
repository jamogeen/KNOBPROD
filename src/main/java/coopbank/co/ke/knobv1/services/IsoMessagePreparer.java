package coopbank.co.ke.knobv1.services;


import com.solab.iso8583.IsoMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Author  : jgichiri
 * Package : coopbank.co.ke.knobv1.services
 * GitHub  : jamogeen
 * Date    : 10/04/2025
 * Project : KnobV1
 */


@Service
public class IsoMessagePreparer {

    public byte[] prepareIsoMessage(IsoMessage isoMessage) throws IOException {
        // 1. Convert ISO message to byte array
        byte[] isoBytes = isoMessage.writeData();

        // 2. Create length header
        String lengthHeader = String.format("%04d", isoBytes.length);
        System.out.println("Length Header: " + lengthHeader);

        byte[] headerBytes = lengthHeader.getBytes(StandardCharsets.US_ASCII);

        // 3. Combine header + message
        byte[] finalMessage = new byte[headerBytes.length + isoBytes.length];
        System.arraycopy(headerBytes, 0, finalMessage, 0, headerBytes.length);
        System.arraycopy(isoBytes, 0, finalMessage, headerBytes.length, isoBytes.length);

        //return finalMessage;
        return isoBytes;
    }
}