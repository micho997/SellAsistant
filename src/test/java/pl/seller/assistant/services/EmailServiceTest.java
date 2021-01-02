package pl.seller.assistant.services;

import static org.junit.jupiter.api.Assertions.fail;
import static pl.seller.assistant.mother.TransactionMother.entity;
import static pl.seller.assistant.mother.TransactionMother.exampleTransaction;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.mother.UserMother;
import pl.seller.assistant.user.repository.UserEntity;

import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmailServiceTest {

  @Autowired
  private EmailService emailService;

  @Test
  void should_send_update_email() {
    // given
    UserEntity testUser = UserMother.testUser();
    TransactionEntity exampleTransaction = entity(exampleTransaction());

    // then
    try {
      emailService.sendUpdateEmail(testUser, exampleTransaction);
    } catch (MessagingException exception) {
      fail();
    }
  }
}
