package com.donatehub.service;

import com.donatehub.entity.Recipient;
import com.donatehub.exception.ResourceNotFoundException;
import com.donatehub.repository.RecipientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public Recipient createRecipient(Recipient recipient) {
        return recipientRepository.save(recipient);
    }

    public List<Recipient> getAllRecipients() {
        return recipientRepository.findAll();
    }

    public Recipient getRecipientById(Long id) {
        return recipientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found with id: " + id));
    }

    public Recipient updateRecipient(Long id, Recipient updatedRecipient) {
        Recipient existing = getRecipientById(id);
        existing.setName(updatedRecipient.getName());
        existing.setOrganization(updatedRecipient.getOrganization());
        existing.setAddress(updatedRecipient.getAddress());
        existing.setPhone(updatedRecipient.getPhone());
        return recipientRepository.save(existing);
    }

    public void deleteRecipient(Long id) {
        Recipient recipient = getRecipientById(id);
        recipientRepository.delete(recipient);
    }
}
