package com.donatehub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "distributions", uniqueConstraints = {
    @UniqueConstraint(name = "uk_distribution_item", columnNames = "item_id")
})
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Item is required")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false, unique = true)
    private DonatedItem item;

    @NotNull(message = "Recipient is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false)
    private Recipient recipient;

    @Column(nullable = false)
    private LocalDateTime distributedAt;

    public Distribution() {
        this.distributedAt = LocalDateTime.now();
    }

    public Distribution(DonatedItem item, Recipient recipient, LocalDateTime distributedAt) {
        this.item = item;
        this.recipient = recipient;
        this.distributedAt = distributedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DonatedItem getItem() { return item; }
    public void setItem(DonatedItem item) { this.item = item; }

    public Recipient getRecipient() { return recipient; }
    public void setRecipient(Recipient recipient) { this.recipient = recipient; }

    public LocalDateTime getDistributedAt() { return distributedAt; }
    public void setDistributedAt(LocalDateTime distributedAt) { this.distributedAt = distributedAt; }
}
