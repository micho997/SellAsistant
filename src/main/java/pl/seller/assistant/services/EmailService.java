package pl.seller.assistant.services;

import static pl.seller.assistant.services.mail.MessageHolder.END;
import static pl.seller.assistant.services.mail.MessageHolder.MONTHLY_SUMMARY_MESSAGE;
import static pl.seller.assistant.services.mail.MessageHolder.MONTHLY_SUMMARY_SUBJECT;
import static pl.seller.assistant.services.mail.MessageHolder.TOP;
import static pl.seller.assistant.services.mail.MessageHolder.UPDATE_MESSAGE;
import static pl.seller.assistant.services.mail.MessageHolder.UPDATE_SUBJECT;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.seller.assistant.config.EmailConfig;
import pl.seller.assistant.databases.entity.SummaryEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.user.repository.UserEntity;
import pl.seller.assistant.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@Service
@AllArgsConstructor
public class EmailService {

  private static final String SENDER = "SellAssistant";

  private final EmailConfig emailConfig;
  private final UserRepository userRepository;
  private final SummaryService summaryService;

  @Scheduled(cron = "0 0 3 * *")
  private void sendMonthlySummary() throws MessagingException {
    List<UserEntity> users = userRepository.findAll();
    for (UserEntity userEntity : users) {
      Optional<SummaryEntity> summaryEntity = summaryService.makeMonthlySummary(LocalDate.now().minusMonths(1), userEntity.getUsername());
      if (summaryEntity.isPresent()) {
        String content = createMonthlyEmail(summaryEntity.get(), userEntity.getUsername());
        send(userEntity.getEmail(), MONTHLY_SUMMARY_SUBJECT, content);
      }
    }
  }

  public void sendUpdateEmail(UserEntity userEntity, TransactionEntity newTransaction) throws MessagingException {
    String content = createUpdateContent(userEntity.getUsername(), newTransaction);
    send(userEntity.getEmail(), UPDATE_SUBJECT, content);
  }

  private String createMonthlyEmail(SummaryEntity summaryEntity, String username) {
    return TOP + username + "\n"
        + MONTHLY_SUMMARY_MESSAGE + "\n"
        + "Profit: " + summaryEntity.getProfit() + "\n"
        + "Cost: " + summaryEntity.getCost() + "\n"
        + "Profit - cost: " + summaryEntity.getProfitMinusCost() + "\n"
        + "Count of bought items: " + summaryEntity.getBoughtCommodities() + "\n"
        + "Count of sold items: " + summaryEntity.getSoldCommodities() + "\n"
        + "Most popular brand: " + summaryEntity.getMostPopularProducer() + "\n"
        + END;
  }

  private String createUpdateContent(String username, TransactionEntity newTransaction) {
    return TOP + username + "\n"
        + UPDATE_MESSAGE + "\n"
        + "Transaction date: " + newTransaction.getDate() + "\n"
        + "Total price: " + newTransaction.getPrice() + "\n"
        + "Count of items: " + newTransaction.getCommodityEntities().size() + "\n"
        + END;
  }

  private void send(String address, String subject, String content) throws MessagingException {
    Session session = Session.getInstance(emailConfig.getProperties(), emailConfig.getEmailAccount());

    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(SENDER));
    message.addRecipient(RecipientType.TO, new InternetAddress(address));
    message.setSubject(subject);
    message.setText(content);
    Transport.send(message);
  }
}
