package com.donatehub.controller;

import com.donatehub.entity.Recipient;
import com.donatehub.service.RecipientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipientController {

    private final RecipientService recipientService;

    public RecipientController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    @PostMapping("/recipients")
    public ResponseEntity<Recipient> createRecipient(@Valid @RequestBody Recipient recipient) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipientService.createRecipient(recipient));
    }

    @GetMapping("/recipients")
    public List<Recipient> getAllRecipients() {
        return recipientService.getAllRecipients();
    }

    @GetMapping("/recipients/{id}")
    public Recipient getRecipientById(@PathVariable Long id) {
        return recipientService.getRecipientById(id);
    }

    @PutMapping("/recipients/{id}")
    public Recipient updateRecipient(@PathVariable Long id, @Valid @RequestBody Recipient recipient) {
        return recipientService.updateRecipient(id, recipient);
    }

    @DeleteMapping("/recipients/{id}")
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        recipientService.deleteRecipient(id);
        return ResponseEntity.noContent().build();
    }
}
