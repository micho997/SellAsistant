package pl.seller.assistant.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmailConfigTest {

  @Autowired
  private EmailConfig emailConfig;

  @Test
  void should_get_properties() {
    assertNotNull(emailConfig.getProperties());
  }
}
