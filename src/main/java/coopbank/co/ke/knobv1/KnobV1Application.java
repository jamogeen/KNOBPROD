package coopbank.co.ke.knobv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class KnobV1Application {


    public static void main(String[] args) {
        SpringApplication.run(KnobV1Application.class, args);
        System.out.println("Application Started Successfully......");
    }

}
