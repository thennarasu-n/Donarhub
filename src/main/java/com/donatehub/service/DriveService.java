package com.donatehub.service;

import com.donatehub.entity.Drive;
import com.donatehub.exception.BusinessRuleException;
import com.donatehub.exception.ResourceNotFoundException;
import com.donatehub.repository.DriveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriveService {

    private final DriveRepository driveRepository;

    public DriveService(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public Drive createDrive(Drive drive) {
        validateDrive(drive);
        return driveRepository.save(drive);
    }

    public List<Drive> getAllDrives() {
        return driveRepository.findAll();
    }

    public Drive getDriveById(Long id) {
        return driveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drive not found with id: " + id));
    }

    public Drive updateDrive(Long id, Drive updatedDrive) {
        Drive existing = getDriveById(id);
        validateDrive(updatedDrive);
        existing.setName(updatedDrive.getName());
        existing.setDescription(updatedDrive.getDescription());
        existing.setStartDate(updatedDrive.getStartDate());
        existing.setEndDate(updatedDrive.getEndDate());
        existing.setStatus(updatedDrive.getStatus());
        return driveRepository.save(existing);
    }

    public void deleteDrive(Long id) {
        Drive drive = getDriveById(id);
        driveRepository.delete(drive);
    }

    private void validateDrive(Drive drive) {
        if (drive.getStartDate() == null || drive.getEndDate() == null) {
            throw new BusinessRuleException("Start date and end date are required.");
        }
        if (drive.getEndDate().isBefore(drive.getStartDate())) {
            throw new BusinessRuleException("End date cannot be before start date.");
        }
    }
}
