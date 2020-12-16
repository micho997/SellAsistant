package pl.seller.assistant.services.summary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.seller.assistant.mother.CommodityMother.carlinSword;
import static pl.seller.assistant.mother.CommodityMother.pugSocks;
import static pl.seller.assistant.mother.TransactionMother.dto;
import static pl.seller.assistant.mother.TransactionMother.exampleTransaction;

import org.junit.Test;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.mother.CommodityMother;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SummaryHelperTest {

  private final SummaryHelper summaryHelper = new SummaryHelper();

  @Test
  public void should_calculate_profit() {
    // given
    List<TransactionDto> transactions = getExampleTransactionsDtoList();
    BigDecimal expectedProfit = BigDecimal.valueOf(1000);

    // when
    BigDecimal resultProfit = summaryHelper.calculateProfit(transactions);

    // then
    assertEquals(expectedProfit, resultProfit);
  }

  @Test
  public void should_calculate_profit_when_transaction_earned_is_null() {
    // given
    List<TransactionDto> transactions = new ArrayList<>(getExampleTransactionsDtoList());
    TransactionDto transactionDto = TransactionDto.builder().build();
    transactions.add(transactionDto);
    BigDecimal expectedProfit = BigDecimal.valueOf(1000);

    // when
    BigDecimal resultProfit = summaryHelper.calculateProfit(transactions);

    // then
    assertEquals(expectedProfit, resultProfit);
  }

  @Test
  public void should_calculate_cost() {
    // given
    List<TransactionDto> transactions = getExampleTransactionsDtoList();
    BigDecimal expectedCost = BigDecimal.valueOf(500);

    // when
    BigDecimal resultCost = summaryHelper.calculateCost(transactions);

    // then
    assertEquals(expectedCost, resultCost);
  }

  @Test
  public void should_calculate_profit_minus_cost() {
    // given
    List<TransactionDto> transactions = getExampleTransactionsDtoList();
    BigDecimal expectedProfitMinusCost = BigDecimal.valueOf(500);

    // when
    BigDecimal resultProfitMinusCost = summaryHelper.calculateProfitMinusCost(transactions);

    // then
    assertEquals(expectedProfitMinusCost, resultProfitMinusCost);
  }

  @Test
  public void should_calculate_profit_minus_cost_when_all_earned_are_null() {
    // given
    List<TransactionDto> transactions = getExampleTransactionsDtoList();
    transactions.get(0).setEarned(null);
    BigDecimal expectedProfitMinusCost = BigDecimal.valueOf(-500);

    // when
    BigDecimal resultProfitMinusCost = summaryHelper.calculateProfitMinusCost(transactions);

    // then
    assertEquals(expectedProfitMinusCost, resultProfitMinusCost);
  }

  @Test
  public void should_count_bought_items() {
    // given
    List<TransactionDto> transactions = getExampleTransactionsDtoList();
    int expectedCommoditiesInStock = 5;

    // when
    int resultSumOfCommoditiesInStock = summaryHelper.countBoughtCommodities(transactions);

    // then
    assertEquals(expectedCommoditiesInStock, resultSumOfCommoditiesInStock);
  }

  @Test
  public void should_count_sold_items() {
    // given
    List<CommodityDto> commodities = getExampleCommoditiesDtoList();
    int expectedSoldCommodities = 4;

    // when
    int resultSoldCommodities = summaryHelper.countSoldCommodities(commodities);

    // then
    assertEquals(expectedSoldCommodities, resultSoldCommodities);
  }

  @Test
  public void should_find_most_popular_producer() {
    // given
    List<CommodityDto> commodities = getExampleCommoditiesDtoList();
    String expectedProducerName = "Cipsoft";

    // when
    String resultProducer = summaryHelper.findMostPopularProducer(commodities);

    // then
    assertEquals(resultProducer, expectedProducerName);
  }

  @Test
  public void should_find_commodity_highest_price() {
    // given
    List<CommodityDto> commodities = new ArrayList<>(getExampleCommoditiesDtoList());
    CommodityDto commodityWithHighestPrice = CommodityDto.builder()
        .price(BigDecimal.valueOf(10000)).build();
    commodities.add(commodityWithHighestPrice);

    // when
    CommodityDto resultCommodityWithHighestPrice = summaryHelper.getHighestPrice(commodities);

    // then
    assertEquals(commodityWithHighestPrice, resultCommodityWithHighestPrice);
  }

  @Test
  public void should_find_commodity_with_highest_profit() {
    // given
    List<CommodityDto> commodities = new ArrayList<>(getExampleCommoditiesDtoList());
    CommodityDto commodityWithHighestProfit = CommodityDto.builder()
        .price(BigDecimal.valueOf(100))
        .currentPrice(BigDecimal.valueOf(5100))
        .soldTime(LocalDate.now()).build();
    commodities.add(commodityWithHighestProfit);

    // when
    CommodityDto resultCommodityWithHighestProfit = summaryHelper.getHighestProfit(commodities);

    // then
    assertEquals(commodityWithHighestProfit, resultCommodityWithHighestProfit);
  }

  @Test
  public void should_find_transaction_with_latest_date() {
    // given
    List<TransactionDto> transactions = getTransactionsWithDifferentDate();
    LocalDate expectedDate = LocalDate.of(2020, 12, 2);

    // when
    LocalDate resultDate = summaryHelper.findLastTransactionDate(transactions);

    // then
    assertEquals(expectedDate, resultDate);
  }

  private List<TransactionDto> getExampleTransactionsDtoList() {
    TransactionDto transactionDto = dto(exampleTransaction(), Collections.singletonList(1L));
    transactionDto.setPrice(BigDecimal.valueOf(100));
    transactionDto.setEarned(BigDecimal.valueOf(200));
    return Arrays.asList(transactionDto, transactionDto, transactionDto, transactionDto, transactionDto);
  }

  private List<CommodityDto> getExampleCommoditiesDtoList() {
    CommodityDto commodityDtoSold = CommodityMother.dto(carlinSword(), 1L);
    commodityDtoSold.setSoldTime(LocalDate.now());
    CommodityDto commodityDtoNotSold = CommodityMother.dto(pugSocks(), 1L);
    return Arrays.asList(commodityDtoSold, commodityDtoSold, commodityDtoSold, commodityDtoSold, commodityDtoNotSold);
  }

  private List<TransactionDto> getTransactionsWithDifferentDate() {
    return Arrays.asList(TransactionDto.builder().date(LocalDate.of(2020, 10, 1)).build(),
        TransactionDto.builder().date(LocalDate.of(2020, 10, 1)).build(),
        TransactionDto.builder().date(LocalDate.of(2020, 10, 3)).build(),
        TransactionDto.builder().date(LocalDate.of(2020, 10, 5)).build(),
        TransactionDto.builder().date(LocalDate.of(2020, 10, 7)).build(),
        TransactionDto.builder().date(LocalDate.of(2020, 10, 8)).build(),
        TransactionDto.builder().date(LocalDate.of(2020, 10, 10)).build(),
        TransactionDto.builder().date(LocalDate.of(2020, 11, 1)).build(),
        TransactionDto.builder().date(LocalDate.of(2020, 12, 1)).build(),
        TransactionDto.builder().date(LocalDate.of(2020, 12, 2)).build());
  }
}