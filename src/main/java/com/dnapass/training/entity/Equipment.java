package com.dnapass.training.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="equipment")
public class Equipment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer count;
    private Double rentPerDay ;
    private String eState;
    private String eCity;
    private String village ;
    private String ePinCode ;
    private String contactPerson ;
    private String eMobileNumber ;

    private String status;


    // Mappings
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "equipment")
    private List<EquipmentUser> equipmentUsers = new ArrayList<>();

    public Equipment() {
    }

    public Equipment(Integer id, String name, Integer count, Double rentPerDay, String eState, String eCity, String village, String ePinCode, String contactPerson, String eMobileNumber, String status, User user) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.rentPerDay = rentPerDay;
        this.eState = eState;
        this.eCity = eCity;
        this.village = village;
        this.ePinCode = ePinCode;
        this.contactPerson = contactPerson;
        this.eMobileNumber = eMobileNumber;
        this.status = status;
        this.user = user;
    }

    public Equipment(Integer id, String name, Integer count, Double rentPerDay, String eState, String eCity, String village, String ePinCode, String contactPerson, String eMobileNumber, User user) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.rentPerDay = rentPerDay;
        this.eState = eState;
        this.eCity = eCity;
        this.village = village;
        this.ePinCode = ePinCode;
        this.contactPerson = contactPerson;
        this.eMobileNumber = eMobileNumber;
        this.user = user;
    }

    public Equipment(String name, Integer count, Double rentPerDay, String eState, String eCity, String village, String ePinCode, String contactPerson, String eMobileNumber, User user) {
        this.name = name;
        this.count = count;
        this.rentPerDay = rentPerDay;
        this.eState = eState;
        this.eCity = eCity;
        this.village = village;
        this.ePinCode = ePinCode;
        this.contactPerson = contactPerson;
        this.eMobileNumber = eMobileNumber;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(Double rentPerDay) {
        this.rentPerDay = rentPerDay;
    }

    public String getEState() {
        return eState;
    }

    public void setEState(String eState) {
        this.eState = eState;
    }

    public String getECity() {
        return eCity;
    }

    public void setECity(String eCity) {
        this.eCity = eCity;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getEPinCode() {
        return ePinCode;
    }

    public void setEPinCode(String ePinCode) {
        this.ePinCode = ePinCode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEMobileNumber() {
        return eMobileNumber;
    }

    public void setEMobileNumber(String eMobileNumber) {
        this.eMobileNumber = eMobileNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
