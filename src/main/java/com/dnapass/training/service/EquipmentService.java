package com.dnapass.training.service;

import com.dnapass.training.entity.Equipment;
import com.dnapass.training.entity.EquipmentDetails;
import com.dnapass.training.entity.EquipmentUser;
import com.dnapass.training.repository.EquipmentRepository;
import com.dnapass.training.repository.EquipmentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class EquipmentService
{
    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    EquipmentUserRepository equipmentUserRepository;


    //To search all equipment based on their city
    public List<Equipment> searchEquipment(String city) {
        return equipmentRepository.findByCity(city);
    }

    //To fetch all equipment for the farmers to book it
    public List<Equipment> getAllEquipment()
    {
        List<Equipment> equipmentList = equipmentRepository.findAll();

        if (equipmentList.isEmpty()) {
            return null;
        }

        return equipmentList;
    }

    //To add new equipment
    public Equipment addEquipment(Equipment e) {
        return equipmentRepository.save(e);
    }

    //To delete particular equipment based on equipment id
    public void deleteEquipmentDetail(Integer equipId) {

        equipmentRepository.deleteById(equipId);
    }

    //To get equipment based on id
    public Equipment getEquipmentById(Integer id) {
        return equipmentRepository.findById(id).orElse(null);

    }

    //To fetch all equipment for that particular user
    public List<Equipment> getAllEquipmentById(Integer id) {

        return equipmentRepository.findAllEquipment(id);

    }

    //To fetch all hired equipment for particular user
    public List<EquipmentDetails> getHiredEquipment(Integer uid){
        return equipmentRepository.findHiredEquipment(uid);

    }

    //To add new equipmentuser which contains both equipment details and user details
    public EquipmentUser addEquipmentUser(EquipmentUser euser) {
        return equipmentUserRepository.save(euser);

    }

    //To delete equipment based on booking-id while returning the equipment
    public void deleteEquipmentUser(Integer bookingId) {
        equipmentUserRepository.deleteById(bookingId);
    }
}
