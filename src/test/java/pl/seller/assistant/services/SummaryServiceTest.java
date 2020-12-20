package pl.seller.assistant.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import pl.seller.assistant.databases.SummaryRepository;
import pl.seller.assistant.databases.entity.SummaryEntity;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.mother.CommodityMother;
import pl.seller.assistant.mother.SameObjectChecker;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SummaryServiceTest {

  @Autowired
  private SummaryService summaryService;
  @Autowired
  private TransactionService transactionService;

  @Mock
  private SummaryEntity summaryEntityMock;
  @Mock
  private SummaryRepository summaryRepositoryMock;
  @InjectMocks
  private SummaryService summaryServiceMock;

  private static final String TEST_USERNAME = "TEST_USER";
  private static final int EXAMPLE_YEAR = 2020;
  private static final Month EXAMPLE_MONTH = Month.of(10);

  private final int transactionYear = 2020;
  private final int transactionMonth = 10;
  private final BigDecimal samePrice = BigDecimal.valueOf(100);
  private final BufferedImage exampleImage = CommodityMother.carlinSword().getImages().get(0);

  @Test
  public void should_make_summary_for_date() {
    // given
    createTransactions();

    // when
    SummaryEntity summaryResult = summaryService.makeMonthlySummary(transactionYear, Month.of(transactionMonth), TEST_USERNAME);

    // then
    assertEquals(Month.of(transactionMonth).toString() + transactionYear, summaryResult.getMonthOfYear());
    assertEquals(TEST_USERNAME, summaryResult.getOwner());
    assertEquals(BigDecimal.valueOf(3500).setScale(2, RoundingMode.CEILING), summaryResult.getProfit());
    assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.CEILING), summaryResult.getCost());
    assertEquals(BigDecimal.valueOf(2500).setScale(2, RoundingMode.CEILING), summaryResult.getProfitMinusCost());
    assertEquals(10, summaryResult.getBoughtCommodities());
    assertEquals(5, summaryResult.getSoldCommodities());
    assertEquals("Nike", summaryResult.getMostPopularProducer());
    assertNotNull(summaryResult.getCommodityWithHighestPriceId());
    assertNotNull(summaryResult.getCommodityWithHighestProfitId());
    assertEquals(LocalDate.of(2020, 10, 29), summaryResult.getLastTransactionDate());
  }

  @Test
  public void should_save_monthly_summary() {
    // given
    createTransactions();
    SummaryEntity summaryResult = summaryService.makeMonthlySummary(transactionYear, Month.of(transactionMonth), TEST_USERNAME);

    // when
    Optional<SummaryEntity> summaryFromDatabase = summaryService.getByIdAndOwner(summaryResult.getMonthOfYear(), TEST_USERNAME);

    // then
    assertTrue(summaryFromDatabase.isPresent());
    SameObjectChecker.equalSummariesEntity(summaryResult, summaryFromDatabase.get());
  }

  @Test
  public void should_return_empty_if_summary_not_exists() {
    assertEquals(Optional.empty(), summaryService.getByIdAndOwner("Incorrect", "Incorrect"));
  }

  @Test
  public void should_return_correct_summary() {
    // given
    when(summaryRepositoryMock.findAllById(Collections.singletonList(anyString()))).thenReturn(Collections.singletonList(summaryEntityMock));

    // when
    Optional<SummaryEntity> result = summaryServiceMock.getByDateAndOwner(EXAMPLE_YEAR, EXAMPLE_MONTH, TEST_USERNAME);

    // then
    verify(summaryRepositoryMock).findAllById(anyCollection());
    assertEquals(Optional.empty(), result);
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
