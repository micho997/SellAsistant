package pl.seller.assistant.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.seller.assistant.mother.ExampleData.EXAMPLE_ID;
import static pl.seller.assistant.mother.TransactionMother.entity;
import static pl.seller.assistant.mother.TransactionMother.exampleTransaction;
import static pl.seller.assistant.mother.TransactionMother.exampleTransactionWithEntry;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.services.TransactionService;

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

  @Test
  void should_return_status_ok_when_get_is_performed() throws Exception {
    // given
    when(transactionService.getById(EXAMPLE_ID)).thenReturn(Optional.of(entity(exampleTransaction())));

    // when
    this.mockMvc.perform(get(SECTION_PATH + "/" + EXAMPLE_ID))
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date", is("2020-10-10")))
        .andExpect(jsonPath("$.price", is(1000.00)))
        .andExpect(jsonPath("$.earned", is(3000.00)));

    // then
    verify(transactionService, times(1)).getById(EXAMPLE_ID);
  }

  @Test
  void should_return_badRequest_when_get_is_not_performed() throws Exception {
    // given
    when(transactionService.getById(EXAMPLE_ID)).thenReturn(Optional.empty());

    // when
    this.mockMvc.perform(get(SECTION_PATH + "/" + EXAMPLE_ID))
        .andExpect(status().isNotFound());

    // then
    verify(transactionService, times(1)).getById(EXAMPLE_ID);
  }

  @Test
  void should_return_status_ok_when_post_is_performed() throws Exception {
    // given
    when(transactionService.save(any(Transaction.class), anyString())).thenReturn(entity(exampleTransactionWithEntry()));

    // when
    mockMvc.perform(post(SECTION_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(exampleTransactionWithEntry())))
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    // then
    verify(transactionService, times(1)).save(any(Transaction.class), anyString());
  }
}
