package pl.seller.assistant.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Base64;
import java.util.Properties;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.mail")
@EnableEncryptableProperties
@AllArgsConstructor
@Getter
@Setter
public class EmailConfig {

  private final String host;
  private final String port;
  private final String username;
  private final String password;

  public Properties getProperties() {
    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", host);
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.port", port);
    properties.put("mail.debug", "true");
    properties.put("mail.smtp.socketFactory.port", port);
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    properties.put("mail.smtp.socketFactory.fallback", "false");
    return properties;
  }

  public javax.mail.Authenticator getEmailAccount() {
    return new javax.mail.Authenticator() {
      @Override
      protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(username, new String(Base64.getMimeDecoder().decode(password)));
      }
    };
  }
}
