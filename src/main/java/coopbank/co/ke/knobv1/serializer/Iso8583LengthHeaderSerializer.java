package coopbank.co.ke.knobv1.serializer;


import org.springframework.integration.ip.tcp.serializer.AbstractByteArraySerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Author  : jgichiri
 * Package : coopbank.co.ke.knobv1.serializer
 * GitHub  : jamogeen
 * Date    : 10/04/2025
 * Project : KnobV1
 */


public class Iso8583LengthHeaderSerializer extends AbstractByteArraySerializer {

    private static final int LENGTH_HEADER_SIZE = 4; // 4-byte ASCII length header

    @Override
    public void serialize(byte[] message, OutputStream outputStream) throws IOException {
        if (message == null || message.length == 0) {
            throw new IOException("Message cannot be null or empty");
        }

        // 1. Create length header (ASCII encoded)
        String lengthHeader = String.format("%04d", message.length);
        byte[] headerBytes = lengthHeader.getBytes(java.nio.charset.StandardCharsets.US_ASCII);

        // 2. Write header + message
        outputStream.write(headerBytes);
        outputStream.write(message);
        outputStream.flush();
    }

    @Override
    public byte[] deserialize(InputStream inputStream) throws IOException {
        // 1. Read length header
        byte[] header = new byte[LENGTH_HEADER_SIZE];
        int bytesRead = readFully(inputStream, header);
        if (bytesRead != LENGTH_HEADER_SIZE) {
            throw new IOException("Failed to read length header");
        }

        // 2. Parse message length
        int messageLength;
        try {
            messageLength = Integer.parseInt(new String(header, java.nio.charset.StandardCharsets.US_ASCII));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid length header format", e);
        }

        // 3. Read message body
        byte[] message = new byte[messageLength];
        bytesRead = readFully(inputStream, message);
        if (bytesRead != messageLength) {
            throw new IOException("Failed to read complete message");
        }

        return message;
    }

    private int readFully(InputStream in, byte[] buffer) throws IOException {
        int bytesRead;
        int totalBytes = 0;
        while (totalBytes < buffer.length) {
            bytesRead = in.read(buffer, totalBytes, buffer.length - totalBytes);
            if (bytesRead == -1) {
                break;
            }
            totalBytes += bytesRead;
        }
        return totalBytes;
    }
}