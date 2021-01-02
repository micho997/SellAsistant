package pl.seller.assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.seller.assistant.config.EmailConfig;

@SpringBootApplication
@EnableConfigurationProperties(EmailConfig.class)
public class SellAssistantApplication {

  public static void main(String[] args) {
    SpringApplication.run(SellAssistantApplication.class, args);
  }
}
