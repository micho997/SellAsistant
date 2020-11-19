package pl.seller.assistant.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.services.TransactionService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping
  public ResponseEntity<List<TransactionDto>> getAll() {
    return ResponseEntity.ok(transactionService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionDto> getById(@PathVariable Long id) {
    return transactionService.getById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<TransactionDto> create(@RequestBody Transaction transaction) {
    TransactionDto transactionDto = transactionService.save(transaction);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(transactionDto.getId())
        .toUri();

    return ResponseEntity.created(location).body(transactionDto);
  }
}

