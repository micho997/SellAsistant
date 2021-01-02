package pl.seller.assistant.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.seller.assistant.mother.ExampleData.EXAMPLE_USERNAME;

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
import pl.seller.assistant.mother.ImageMother;

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

  private static final int EXAMPLE_YEAR = 2020;
  private static final Month EXAMPLE_MONTH = Month.of(10);
  private static final LocalDate EXAMPLE_MONTH_OF_YEAR = LocalDate.of(2020, Month.OCTOBER, 1);
  private static final BigDecimal OLD_PRICE = BigDecimal.valueOf(100);
  private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(300);

  @Test
  public void should_make_summary_for_date() {
    // given
    createTransactions();

    // when
    Optional<SummaryEntity> summaryResult = summaryService.makeMonthlySummary(EXAMPLE_MONTH_OF_YEAR, EXAMPLE_USERNAME);

    // then
    assertTrue(summaryResult.isPresent());
    assertEquals(EXAMPLE_USERNAME, summaryResult.get().getOwner());
    assertEquals(BigDecimal.valueOf(3500).setScale(2, RoundingMode.CEILING), summaryResult.get().getProfit());
    assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.CEILING), summaryResult.get().getCost());
    assertEquals(BigDecimal.valueOf(2500).setScale(2, RoundingMode.CEILING), summaryResult.get().getProfitMinusCost());
    assertEquals(10, summaryResult.get().getBoughtCommodities());
    assertEquals(5, summaryResult.get().getSoldCommodities());
    assertEquals("Nike", summaryResult.get().getMostPopularProducer());
    assertNotNull(summaryResult.get().getCommodityWithHighestPriceId());
    assertNotNull(summaryResult.get().getCommodityWithHighestProfitId());
    assertNotNull(summaryResult.get().getLastTransactionDateId());
  }

  @Test
  public void should_save_monthly_summary() {
    // given
    createTransactions();
    SummaryEntity summaryResult = summaryService.makeMonthlySummary(EXAMPLE_MONTH_OF_YEAR, EXAMPLE_USERNAME).orElseThrow();

    // when
    Optional<SummaryEntity> summaryFromDatabase = summaryService.getByIdAndOwner(summaryResult.getMonthOfYear(), EXAMPLE_USERNAME);

    // then
    assertTrue(summaryFromDatabase.isPresent());
    assertEquals(summaryResult.getMonthOfYear(), summaryFromDatabase.get().getMonthOfYear());
    assertEquals(summaryResult.getOwner(), summaryFromDatabase.get().getOwner());
    assertEquals(summaryResult.getProfit(), summaryFromDatabase.get().getProfit());
    assertEquals(summaryResult.getCost(), summaryFromDatabase.get().getCost());
    assertEquals(summaryResult.getProfitMinusCost(), summaryFromDatabase.get().getProfitMinusCost());
    assertEquals(summaryResult.getBoughtCommodities(), summaryFromDatabase.get().getBoughtCommodities());
    assertEquals(summaryResult.getSoldCommodities(), summaryFromDatabase.get().getSoldCommodities());
    assertEquals(summaryResult.getMostPopularProducer(), summaryFromDatabase.get().getMostPopularProducer());
    assertEquals(summaryResult.getCommodityWithHighestPriceId(), summaryFromDatabase.get().getCommodityWithHighestPriceId());
    assertEquals(summaryResult.getCommodityWithHighestProfitId(), summaryFromDatabase.get().getCommodityWithHighestProfitId());
    assertEquals(summaryResult.getLastTransactionDateId(), summaryFromDatabase.get().getLastTransactionDateId());
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
    Optional<SummaryEntity> result = summaryServiceMock.getByDateAndOwner(EXAMPLE_YEAR, EXAMPLE_MONTH, EXAMPLE_USERNAME);

    // then
    verify(summaryRepositoryMock).findAllById(anyCollection());
    assertEquals(Optional.empty(), result);
  }

  private void createTransactions() {
    saveTransaction(get1Transaction());
    saveTransaction(get2Transaction());
    saveTransaction(get3Transaction());
    saveTransaction(get4Transaction());
    saveTransaction(get5Transaction());
  }

  private void saveTransaction(Transaction transaction) {
    transactionService.save(transaction, EXAMPLE_USERNAME);
  }

  private Transaction get1Transaction() {
    return createTransaction(commodity1(), commodity2(), BigDecimal.valueOf(500),
        LocalDate.of(EXAMPLE_YEAR, EXAMPLE_MONTH, 3));
  }

  private Transaction get2Transaction() {
    return createTransaction(commodity3(), commodity4(), BigDecimal.valueOf(600), LocalDate.of(EXAMPLE_YEAR, EXAMPLE_MONTH, 5));
  }

  private Transaction get3Transaction() {
    return createTransaction(commodity5(), commodity6(), BigDecimal.valueOf(700), LocalDate.of(EXAMPLE_YEAR, EXAMPLE_MONTH, 11));
  }

  private Transaction get4Transaction() {
    return createTransaction(commodity7(), commodity8(), BigDecimal.valueOf(800), LocalDate.of(EXAMPLE_YEAR, EXAMPLE_MONTH, 28));
  }

  private Transaction get5Transaction() {
    return createTransaction(commodity9(), commodity10(), BigDecimal.valueOf(900), LocalDate.of(EXAMPLE_YEAR, EXAMPLE_MONTH, 29));
  }

  private Transaction createTransaction(Commodity notSold, Commodity sold, BigDecimal commoditySoldFor, LocalDate transactionDate) {
    notSold.setGotTime(transactionDate);

    sold.setGotTime(transactionDate);
    sold.setSoldTime(transactionDate);
    sold.setNewPrice(commoditySoldFor);

    return Transaction.builder()
        .date(transactionDate)
        .earned(commoditySoldFor)
        .price(notSold.getOldPrice().add(sold.getOldPrice()))
        .commodities(Arrays.asList(notSold, sold)).build();
  }

  private Commodity createCommodity(String producer) {
    return Commodity.builder()
        .producer(producer)
        .oldPrice(OLD_PRICE)
        .newPrice(NEW_PRICE)
        .images(Collections.singletonList(ImageMother.exampleBufferedImage())).build();
  }

  private Commodity commodity1() {
    return createCommodity("Nike");
  }

  private Commodity commodity2() {
    return createCommodity("Orange");
  }

  private Commodity commodity3() {
    return createCommodity("Nike");
  }

  private Commodity commodity4() {
    return createCommodity("Nike");
  }

  private Commodity commodity5() {
    return createCommodity("Orange");
  }

  private Commodity commodity6() {
    return createCommodity("Microsoft");
  }

  private Commodity commodity7() {
    return createCommodity("Nike");
  }

  private Commodity commodity8() {
    return createCommodity("Microsoft");
  }

  private Commodity commodity9() {
    return createCommodity("Sony");
  }

  private Commodity commodity10() {
    return createCommodity("Apple");
  }
}
