package coopbank.co.ke.knobv1.config;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.parse.ConfigParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class AppConfig {

    @Bean
    public MessageFactory<IsoMessage> messageFactory() throws IOException {
        MessageFactory<IsoMessage> mf = ConfigParser.createFromClasspathConfig("j8583.xml");
        mf.setUseBinaryBitmap(true);
        mf.setCharacterEncoding(StandardCharsets.ISO_8859_1.name());
        return mf;
    }
}
