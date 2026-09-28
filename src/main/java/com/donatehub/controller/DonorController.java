package com.donatehub.controller;

import com.donatehub.entity.Donor;
import com.donatehub.service.DonorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping("/donors")
    public ResponseEntity<Donor> createDonor(@Valid @RequestBody Donor donor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donorService.createDonor(donor));
    }

    @GetMapping("/donors")
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    @GetMapping("/donors/{id}")
    public Donor getDonorById(@PathVariable Long id) {
        return donorService.getDonorById(id);
    }

    @PutMapping("/donors/{id}")
    public Donor updateDonor(@PathVariable Long id, @Valid @RequestBody Donor donor) {
        return donorService.updateDonor(id, donor);
    }

    @DeleteMapping("/donors/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return ResponseEntity.noContent().build();
    }
}
