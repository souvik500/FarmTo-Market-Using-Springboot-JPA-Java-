package com.dnapass.training.repository;

import com.dnapass.training.entity.EquipmentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentUserRepository extends JpaRepository<EquipmentUser, Integer> {

}

