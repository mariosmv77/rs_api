package gr.codehub.rsapi;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {
    public static int counter;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("ssss");
    }
}
