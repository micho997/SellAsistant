package pl.seller.assistant.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.services.TransactionService;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

  private final TransactionService transactionService;

  @GetMapping("/{id}")
  public ResponseEntity<TransactionEntity> getById(@PathVariable Long id) {
    return transactionService.getById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<TransactionEntity> create(@RequestBody Transaction transaction) {
    TransactionEntity savedTransaction = transactionService.save(transaction, "IN PROGRESS");
    URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/{id}")
        .buildAndExpand(savedTransaction.getId())
        .toUri();

    return ResponseEntity.created(location).body(savedTransaction);
  }
}
