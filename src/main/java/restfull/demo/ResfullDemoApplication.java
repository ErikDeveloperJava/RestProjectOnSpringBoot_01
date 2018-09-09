package restfull.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ResfullDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResfullDemoApplication.class, args);
    }
}
