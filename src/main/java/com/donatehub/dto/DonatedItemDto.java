package com.donatehub.dto;

import java.time.LocalDate;

public class DonatedItemDto {
    private Long id;
    private String category;
    private String condition;
    private boolean collected;
    private Long donorId;
    private Long driveId;
    private LocalDate donatedDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public boolean isCollected() { return collected; }
    public void setCollected(boolean collected) { this.collected = collected; }

    public Long getDonorId() { return donorId; }
    public void setDonorId(Long donorId) { this.donorId = donorId; }

    public Long getDriveId() { return driveId; }
    public void setDriveId(Long driveId) { this.driveId = driveId; }

    public LocalDate getDonatedDate() { return donatedDate; }
    public void setDonatedDate(LocalDate donatedDate) { this.donatedDate = donatedDate; }
}
