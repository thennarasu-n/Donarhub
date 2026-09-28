package com.donatehub.service;

import com.donatehub.entity.Donor;
import com.donatehub.exception.ResourceNotFoundException;
import com.donatehub.repository.DonorRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor createDonor(@Valid Donor donor) {
        return donorRepository.save(donor);
    }

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + id));
    }

    public Donor updateDonor(Long id, Donor updatedDonor) {
        Donor existing = getDonorById(id);
        existing.setName(updatedDonor.getName());
        existing.setEmail(updatedDonor.getEmail());
        existing.setPhone(updatedDonor.getPhone());
        return donorRepository.save(existing);
    }

    public void deleteDonor(Long id) {
        Donor donor = getDonorById(id);
        donorRepository.delete(donor);
    }
}
