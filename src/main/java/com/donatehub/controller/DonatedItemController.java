package com.donatehub.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donatehub.dto.DonatedItemDto;
import com.donatehub.entity.DonatedItem;
import com.donatehub.service.DonatedItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class DonatedItemController {

    private final DonatedItemService donatedItemService;

    public DonatedItemController(DonatedItemService donatedItemService) {
        this.donatedItemService = donatedItemService;
    }

    @PostMapping("/items")
    public ResponseEntity<DonatedItemDto> createItem(@Valid @RequestBody DonatedItem item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(donatedItemService.createItem(item)));
    }

    @GetMapping("/items")
    public List<DonatedItemDto> getAllItems() {
        return donatedItemService.getAllItems().stream().map(this::toDto).toList();
    }

    @GetMapping("/items/{id}")
    public DonatedItemDto getItemById(@PathVariable Long id) {
        return toDto(donatedItemService.getItemById(id));
    }

    @PutMapping("/items/{id}")
    public DonatedItemDto updateItem(@PathVariable Long id, @Valid @RequestBody DonatedItem item) {
        return toDto(donatedItemService.updateItem(id, item));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        donatedItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items/collected")
    public List<DonatedItemDto> getCollectedItems() {
        return donatedItemService.getCollectedItems().stream().map(this::toDto).toList();
    }

    @GetMapping("/items/undistributed")
    public List<DonatedItemDto> getUndistributedItems() {
        return donatedItemService.getUndistributedItems().stream().map(this::toDto).toList();
    }

    @GetMapping("/items/category/{category}")
    public List<DonatedItemDto> getItemsByCategory(@PathVariable String category) {
        return donatedItemService.getItemsByCategory(category).stream().map(this::toDto).toList();
    }

    private DonatedItemDto toDto(DonatedItem item) {
        DonatedItemDto dto = new DonatedItemDto();
        dto.setId(item.getId());
        dto.setCategory(item.getCategory().name());
        dto.setCondition(item.getCondition().name());
        dto.setCollected(item.isCollected());
        dto.setDonorId(item.getDonor().getId());
        dto.setDriveId(item.getDrive().getId());
        dto.setDonatedDate(item.getDonatedDate());
        return dto;
    }
}
