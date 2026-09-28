package com.donatehub.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donatehub.entity.DonatedItem;
import com.donatehub.repository.DistributionRepository;
import com.donatehub.repository.DonatedItemRepository;
import com.donatehub.repository.DonorRepository;
import com.donatehub.repository.DriveRepository;

@RestController
@RequestMapping("/api")
public class DashboardController {

    private final DriveRepository driveRepository;
    private final DonorRepository donorRepository;
    private final DonatedItemRepository donatedItemRepository;
    private final DistributionRepository distributionRepository;

    public DashboardController(DriveRepository driveRepository,
                              DonorRepository donorRepository,
                              DonatedItemRepository donatedItemRepository,
                              DistributionRepository distributionRepository) {
        this.driveRepository = driveRepository;
        this.donorRepository = donorRepository;
        this.donatedItemRepository = donatedItemRepository;
        this.distributionRepository = distributionRepository;
    }

    @GetMapping("/dashboard/stats")
    public Map<String, Object> getDashboardStats() {
        long totalDrives = driveRepository.count();
        long totalDonors = donorRepository.count();
        long totalItems = donatedItemRepository.count();
        long totalCollected = donatedItemRepository.findByCollectedTrue().size();
        long totalDistributed = distributionRepository.count();
        long totalUndistributed = donatedItemRepository.findByCollectedTrueAndDistributionIsNull().size();
        long totalBooks = donatedItemRepository.findByCategory(DonatedItem.Category.BOOK).size();
        long totalClothes = donatedItemRepository.findByCategory(DonatedItem.Category.CLOTH).size();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDrives", totalDrives);
        stats.put("totalDonors", totalDonors);
        stats.put("totalItems", totalItems);
        stats.put("totalCollected", totalCollected);
        stats.put("totalDistributed", totalDistributed);
        stats.put("totalUndistributed", totalUndistributed);
        stats.put("totalBooks", totalBooks);
        stats.put("totalClothes", totalClothes);
        return stats;
    }
}
