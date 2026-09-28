package com.donatehub.service;

import com.donatehub.entity.Distribution;
import com.donatehub.entity.DonatedItem;
import com.donatehub.entity.Recipient;
import com.donatehub.exception.BusinessRuleException;
import com.donatehub.exception.ResourceNotFoundException;
import com.donatehub.repository.DistributionRepository;
import com.donatehub.repository.DonatedItemRepository;
import com.donatehub.repository.RecipientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DistributionService {

    private final DistributionRepository distributionRepository;
    private final DonatedItemRepository donatedItemRepository;
    private final RecipientRepository recipientRepository;

    public DistributionService(DistributionRepository distributionRepository,
                              DonatedItemRepository donatedItemRepository,
                              RecipientRepository recipientRepository) {
        this.distributionRepository = distributionRepository;
        this.donatedItemRepository = donatedItemRepository;
        this.recipientRepository = recipientRepository;
    }

    public Distribution createDistribution(Long itemId, Long recipientId) {
        DonatedItem item = donatedItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));

        if (!item.isCollected()) {
            throw new BusinessRuleException("Item has not been collected yet.");
        }

        if (distributionRepository.existsByItemId(itemId)) {
            throw new BusinessRuleException("Item has already been distributed.");
        }

        Recipient recipient = recipientRepository.findById(recipientId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found with id: " + recipientId));

        Distribution distribution = new Distribution();
        distribution.setItem(item);
        distribution.setRecipient(recipient);
        distribution.setDistributedAt(LocalDateTime.now());

        Distribution saved = distributionRepository.save(distribution);
        item.setCollected(true);
        donatedItemRepository.save(item);
        return saved;
    }

    public List<Distribution> getAllDistributions() {
        return distributionRepository.findAll();
    }

    public Distribution getDistributionById(Long id) {
        return distributionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Distribution not found with id: " + id));
    }
}
