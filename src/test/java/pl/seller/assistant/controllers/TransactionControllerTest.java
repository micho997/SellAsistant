package pl.seller.assistant.controllers;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.seller.assistant.mother.CommodityMother.carlinSword;
import static pl.seller.assistant.mother.CommodityMother.entity;
import static pl.seller.assistant.mother.TransactionMother.dto;
import static pl.seller.assistant.mother.TransactionMother.exampleTransaction;
import static pl.seller.assistant.mother.TransactionMother.exampleTransactionWithEntry;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.seller.assistant.databases.EntityMapper;
import pl.seller.assistant.databases.entity.ImagesEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.mother.TransactionMother;
import pl.seller.assistant.services.TransactionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

  private static final String SECTION_PATH = "/transactions";

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private TransactionService transactionService;

  private TransactionDto transactionDto;

  @BeforeEach
  void setUp() {
    transactionDto = dto(exampleTransaction(), asList(1L, 2L, 3L));
  }

  @Test
  void should_get_all_transactions() throws Exception {
    // given
    List<TransactionDto> transactionDtoList = List.of(transactionDto, transactionDto);
    when(transactionService.getAll()).thenReturn(transactionDtoList);

    // when
    this.mockMvc.perform(get(SECTION_PATH))
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0]", hasEntry("date", "2020-10-10")))
        .andExpect(jsonPath("$[1]", hasEntry("date", "2020-10-10")));

    // then
    verify(transactionService, times(1)).getAll();
  }

  @Test
  void should_return_status_ok_when_get_is_performed() throws Exception {
    // given
    when(transactionService.getById(1L)).thenReturn(java.util.Optional.ofNullable(transactionDto));

    // when
    this.mockMvc.perform(get(SECTION_PATH + "/1"))
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date", is("2020-10-10")))
        .andExpect(jsonPath("$.price", is(1000.0)))
        .andExpect(jsonPath("$.earned", is(3000.0)));

    // then
    verify(transactionService, times(1)).getById(1L);
  }

  @Test
  void should_return_badRequest_when_get_is_not_performed() throws Exception {
    // given
    when(transactionService.getById(1L)).thenReturn(Optional.empty());

    // when
    this.mockMvc.perform(get(SECTION_PATH + "/1"))
        .andExpect(status().isNotFound());

    // then
    verify(transactionService, times(1)).getById(1L);
  }

  @Test
  void should_return_status_ok_when_post_is_performed() throws Exception {
    // given
    Transaction transaction = exampleTransactionWithEntry();
    transaction.getCommodities().get(0).setImages(List.of());
    when(transactionService.save(any(Transaction.class))).thenReturn(transactionDto);

    // when
    mockMvc.perform(post(SECTION_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(transaction)))
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    // then
    verify(transactionService, times(1)).save(any(Transaction.class));
  }

  private TransactionEntity exampleEntity() {
    Commodity commodity = carlinSword();
    commodity.setImages(List.of());
    ImagesEntity imagesEntity = new ImagesEntity(EntityMapper.toBlob(commodity.getImages()));
    return TransactionMother.entity(exampleTransaction(), Collections.singletonList(entity(commodity, imagesEntity)));
  }
}
