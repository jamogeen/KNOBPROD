package coopbank.co.ke.knobv1.serializer;


import org.springframework.integration.ip.tcp.serializer.AbstractByteArraySerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Author  : jgichiri
 * Package : coopbank.co.ke.knobv1.serializer
 * GitHub  : jamogeen
 * Date    : 10/04/2025
 * Project : KnobV1
 */


public class ByteArrayLengthHeaderSerializer extends AbstractByteArraySerializer {

    @Override
    public void serialize(byte[] bytes, OutputStream outputStream) throws IOException {
        System.out.println("Serializing...");
        if (bytes != null) {
            // 1. Create length header (4 bytes)
            String lengthHeader = String.format("%04d", bytes.length);
            System.out.println("Length Header on Sending: " + lengthHeader);
            byte[] lengthBytes = lengthHeader.getBytes(StandardCharsets.ISO_8859_1);

            // 2. Write length header + message
            outputStream.write(lengthBytes);
            outputStream.write(bytes);
            outputStream.flush();
        }
    }

    @Override
    public byte[] deserialize(InputStream inputStream) throws IOException {

        System.out.println("Deserializing...");
        // 1. Read 4-byte length header
        byte[] lengthBytes = new byte[4];
        int bytesRead = inputStream.read(lengthBytes);
        if (bytesRead != 4) {
            throw new IOException("Failed to read length header");
        }

        // 2. Parse message length
        int messageLength = Integer.parseInt(new String(lengthBytes, StandardCharsets.ISO_8859_1));

        // 3. Read message body
        byte[] messageBytes = new byte[messageLength];
        bytesRead = inputStream.read(messageBytes);
        if (bytesRead != messageLength) {
            throw new IOException("Failed to read complete message");
        }

        return messageBytes;
    }
}