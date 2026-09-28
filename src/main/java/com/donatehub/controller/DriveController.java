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

import com.donatehub.entity.Drive;
import com.donatehub.service.DriveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class DriveController {

    private final DriveService driveService;

    public DriveController(DriveService driveService) {
        this.driveService = driveService;
    }

    @PostMapping("/drives")
    public ResponseEntity<Drive> createDrive(@Valid @RequestBody Drive drive) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driveService.createDrive(drive));
    }

    @GetMapping("/drives")
    public List<Drive> getAllDrives() {
        return driveService.getAllDrives();
    }

    @GetMapping("/drives/{id}")
    public Drive getDriveById(@PathVariable Long id) {
        return driveService.getDriveById(id);
    }

    @PutMapping("/drives/{id}")
    public Drive updateDrive(@PathVariable Long id, @Valid @RequestBody Drive drive) {
        return driveService.updateDrive(id, drive);
    }

    @DeleteMapping("/drives/{id}")
    public ResponseEntity<Void> deleteDrive(@PathVariable Long id) {
        driveService.deleteDrive(id);
        return ResponseEntity.noContent().build();
    }
}
