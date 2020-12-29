package pl.seller.assistant.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.seller.assistant.databases.entity.SummaryEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.services.SummaryService;
import pl.seller.assistant.services.TransactionService;

import java.time.LocalDate;
import java.util.List;

@RequestMapping
@AllArgsConstructor
public class AccountController {

  private final TransactionService transactionService;
  private final SummaryService summaryService;

  @GetMapping("/{username}")
  public ResponseEntity<List<TransactionEntity>> getTransactions() {
    return ResponseEntity.ok(transactionService.getAllForUser("getUsername()"));
  }

  @GetMapping("/{username}")
  public ResponseEntity<SummaryEntity> getMonthlySummary() {
    return ResponseEntity.ok(summaryService.getMonthlySummary(LocalDate.now().minusMonths(1), "Username"));
  }
}
