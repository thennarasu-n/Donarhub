package com.donatehub.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "recipients")
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Recipient name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Organization is required")
    @Column(nullable = false)
    private String organization;

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address;

    @Column
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Distribution> distributions = new ArrayList<>();

    public Recipient() {
    }

    public Recipient(String name, String organization, String address, String phone) {
        this.name = name;
        this.organization = organization;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<Distribution> getDistributions() { return distributions; }
    public void setDistributions(List<Distribution> distributions) { this.distributions = distributions; }
}
