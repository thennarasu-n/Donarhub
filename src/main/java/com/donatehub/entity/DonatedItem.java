package com.donatehub.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "donated_items")
public class DonatedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_condition", nullable = false)
    private Condition condition;

    @Column(nullable = false)
    private boolean collected = false;

    @NotNull(message = "Donor is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    @NotNull(message = "Drive is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "drive_id", nullable = false)
    private Drive drive;

    @Column(nullable = false)
    private LocalDate donatedDate;

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Distribution distribution;

    public enum Category { BOOK, CLOTH }
    public enum Condition { NEW, GOOD, WORN }

    public DonatedItem() {
        this.donatedDate = LocalDate.now();
    }

    public DonatedItem(Category category, Condition condition, Donor donor, Drive drive, LocalDate donatedDate) {
        this.category = category;
        this.condition = condition;
        this.donor = donor;
        this.drive = drive;
        this.donatedDate = donatedDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Condition getCondition() { return condition; }
    public void setCondition(Condition condition) { this.condition = condition; }

    public boolean isCollected() { return collected; }
    public void setCollected(boolean collected) { this.collected = collected; }

    public Donor getDonor() { return donor; }
    public void setDonor(Donor donor) { this.donor = donor; }

    public Drive getDrive() { return drive; }
    public void setDrive(Drive drive) { this.drive = drive; }

    public LocalDate getDonatedDate() { return donatedDate; }
    public void setDonatedDate(LocalDate donatedDate) { this.donatedDate = donatedDate; }

    public Distribution getDistribution() { return distribution; }
    public void setDistribution(Distribution distribution) { this.distribution = distribution; }
}
