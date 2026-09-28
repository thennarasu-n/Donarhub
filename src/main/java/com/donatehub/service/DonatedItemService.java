package com.donatehub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.donatehub.entity.DonatedItem;
import com.donatehub.entity.Donor;
import com.donatehub.entity.Drive;
import com.donatehub.exception.BusinessRuleException;
import com.donatehub.exception.ResourceNotFoundException;
import com.donatehub.repository.DonatedItemRepository;
import com.donatehub.repository.DonorRepository;
import com.donatehub.repository.DriveRepository;

@Service
public class DonatedItemService {

    private final DonatedItemRepository donatedItemRepository;
    private final DonorRepository donorRepository;
    private final DriveRepository driveRepository;

    public DonatedItemService(DonatedItemRepository donatedItemRepository,
                             DonorRepository donorRepository,
                             DriveRepository driveRepository) {
        this.donatedItemRepository = donatedItemRepository;
        this.donorRepository = donorRepository;
        this.driveRepository = driveRepository;
    }

    public DonatedItem createItem(DonatedItem item) {
        validateItem(item);
        item.setCollected(true);
        return donatedItemRepository.save(item);
    }

    public List<DonatedItem> getAllItems() {
        return donatedItemRepository.findAll();
    }

    public DonatedItem getItemById(Long id) {
        return donatedItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
    }

    public DonatedItem updateItem(Long id, DonatedItem updatedItem) {
        DonatedItem existing = getItemById(id);
        validateItem(updatedItem);
        existing.setCategory(updatedItem.getCategory());
        existing.setCondition(updatedItem.getCondition());
        existing.setDonor(updatedItem.getDonor());
        existing.setDrive(updatedItem.getDrive());
        existing.setDonatedDate(updatedItem.getDonatedDate());
        existing.setCollected(updatedItem.isCollected());
        return donatedItemRepository.save(existing);
    }

    public void deleteItem(Long id) {
        DonatedItem item = getItemById(id);
        donatedItemRepository.delete(item);
    }

    public List<DonatedItem> getCollectedItems() {
        return donatedItemRepository.findByCollectedTrue();
    }

    public List<DonatedItem> getUndistributedItems() {
        return donatedItemRepository.findByCollectedTrueAndDistributionIsNull();
    }

    public List<DonatedItem> getItemsByCategory(String category) {
        try {
            DonatedItem.Category value = DonatedItem.Category.valueOf(category.toUpperCase());
            return donatedItemRepository.findByCategory(value);
        } catch (IllegalArgumentException ex) {
            throw new BusinessRuleException("Invalid item category: " + category);
        }
    }

    private void validateItem(DonatedItem item) {
        if (item.getDonor() == null || item.getDonor().getId() == null) {
            throw new BusinessRuleException("A valid donor is required before logging an item.");
        }

        if (item.getDrive() == null || item.getDrive().getId() == null) {
            throw new BusinessRuleException("A valid donation drive is required before logging an item.");
        }

        Donor donor = donorRepository.findById(item.getDonor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + item.getDonor().getId()));
        Drive drive = driveRepository.findById(item.getDrive().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Drive not found with id: " + item.getDrive().getId()));

        if (item.getCategory() == null) {
            throw new BusinessRuleException("Item category is required.");
        }
        if (item.getCondition() == null) {
            throw new BusinessRuleException("Item condition is required.");
        }

        if (item.getCondition() != DonatedItem.Condition.NEW
                && item.getCondition() != DonatedItem.Condition.GOOD
                && item.getCondition() != DonatedItem.Condition.WORN) {
            throw new BusinessRuleException("Item condition must be one of NEW, GOOD, or WORN.");
        }

        item.setDonor(donor);
        item.setDrive(drive);
    }
}
