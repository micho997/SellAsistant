package pl.seller.assistant.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class WebSecurityConfigTest {

  @Autowired
  private WebSecurityConfig webSecurityConfig;

  @Test
  void should_created_config_bean() {
    assertNotNull(webSecurityConfig.getPasswordEncoder());
  }

}