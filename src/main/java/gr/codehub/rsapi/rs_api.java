package gr.codehub.rsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class rs_api {
    public static void main(String[] args) {
        SpringApplication.run(rs_api.class, args);
    }
}
