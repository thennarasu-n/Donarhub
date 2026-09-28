package com.donatehub.dto;

public class RecipientDto {
    private Long id;
    private String name;
    private String organization;
    private String address;
    private String phone;

    public RecipientDto() {
    }

    public RecipientDto(Long id, String name, String organization, String address, String phone) {
        this.id = id;
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
}
