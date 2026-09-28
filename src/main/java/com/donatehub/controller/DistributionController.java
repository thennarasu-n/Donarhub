package com.donatehub.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donatehub.dto.DistributionDto;
import com.donatehub.entity.Distribution;
import com.donatehub.service.DistributionService;

@RestController
@RequestMapping("/api")
public class DistributionController {

    private final DistributionService distributionService;

    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @PostMapping("/distributions")
    public ResponseEntity<DistributionDto> createDistribution(@RequestBody Map<String, Long> payload) {
        Long itemId = payload.get("itemId");
        Long recipientId = payload.get("recipientId");

        if (itemId == null || recipientId == null) {
            throw new IllegalArgumentException("itemId and recipientId are required.");
        }

        Distribution distribution = distributionService.createDistribution(itemId, recipientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(distribution));
    }

    @GetMapping("/distributions")
    public List<DistributionDto> getAllDistributions() {
        return distributionService.getAllDistributions().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/distributions/{id}")
    public DistributionDto getDistributionById(@PathVariable Long id) {
        return toDto(distributionService.getDistributionById(id));
    }

    private DistributionDto toDto(Distribution distribution) {
        DistributionDto dto = new DistributionDto();
        dto.setId(distribution.getId());
        dto.setItemId(distribution.getItem().getId());
        dto.setRecipientId(distribution.getRecipient().getId());
        dto.setDistributedAt(distribution.getDistributedAt());
        return dto;
    }
}
