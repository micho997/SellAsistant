package pl.seller.assistant.services.summary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.mother.CommodityMother;
import pl.seller.assistant.services.TransactionService;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SummaryServiceTest {

  @Autowired
  private SummaryService summaryService;
  @Autowired
  private TransactionService transactionService;

  private final int transactionYear = 2020;
  private final int transactionMonth = 10;
  private final BigDecimal samePrice = BigDecimal.valueOf(100);
  private final BufferedImage exampleImage = CommodityMother.carlinSword().getImages().get(0);

  @Test
  public void should_make_summary_for_date() {
    // given
    createTransactions();

    // when
    Summary summary = summaryService.makeMonthlySummary(transactionYear, Month.of(transactionMonth));

    // then
    System.out.println("Done!");
  }

  private void createTransactions() {
    transactionService.save(get1Transaction());
    transactionService.save(get2Transaction());
    transactionService.save(get3Transaction());
    transactionService.save(get4Transaction());
    transactionService.save(get5Transaction());
  }

  private Transaction get1Transaction() {
    return createTransaction(commodity1(), commodity2(), BigDecimal.valueOf(500),
        LocalDate.of(transactionYear, transactionMonth, 3));
  }

  private Transaction get2Transaction() {
    return createTransaction(commodity3(), commodity4(), BigDecimal.valueOf(600), LocalDate.of(transactionYear, transactionMonth, 5));
  }

  private Transaction get3Transaction() {
    return createTransaction(commodity5(), commodity6(), BigDecimal.valueOf(700), LocalDate.of(transactionYear, transactionMonth, 11));
  }

  private Transaction get4Transaction() {
    return createTransaction(commodity7(), commodity8(), BigDecimal.valueOf(800), LocalDate.of(transactionYear, transactionMonth, 28));
  }

  private Transaction get5Transaction() {
    return createTransaction(commodity9(), commodity10(), BigDecimal.valueOf(900), LocalDate.of(transactionYear, transactionMonth, 29));
  }

  private Transaction createTransaction(Commodity notSold, Commodity sold, BigDecimal commoditySoldFor, LocalDate transactionDate) {
    notSold.setGotTime(transactionDate);

    sold.setGotTime(transactionDate);
    sold.setSoldTime(transactionDate);
    sold.setCurrentPrice(commoditySoldFor);

    return Transaction.builder()
        .date(transactionDate)
        .earned(commoditySoldFor)
        .price(notSold.getPrice().add(sold.getPrice()))
        .commodities(Arrays.asList(notSold, sold)).build();
  }

  private Commodity createCommodity(
      String producer, BigDecimal price, BufferedImage image) {
    return Commodity.builder()
        .producer(producer)
        .price(price)
        .currentPrice(price)
        .images(Collections.singletonList(image)).build();
  }

  private Commodity commodity1() {
    return createCommodity("Nike", samePrice, exampleImage);
  }

  private Commodity commodity2() {
    return createCommodity("Orange", samePrice, exampleImage);
  }

  private Commodity commodity3() {
    return createCommodity("Nike", samePrice, exampleImage);
  }

  private Commodity commodity4() {
    return createCommodity("Nike", samePrice, exampleImage);
  }

  private Commodity commodity5() {
    return createCommodity("Orange", samePrice, exampleImage);
  }

  private Commodity commodity6() {
    return createCommodity("Microsoft", samePrice, exampleImage);
  }

  private Commodity commodity7() {
    return createCommodity("Nike", samePrice, exampleImage);
  }

  private Commodity commodity8() {
    return createCommodity("Microsoft", samePrice, exampleImage);
  }

  private Commodity commodity9() {
    return createCommodity("Sony", samePrice, exampleImage);
  }

  private Commodity commodity10() {
    return createCommodity("Apple", samePrice, exampleImage);
  }

}