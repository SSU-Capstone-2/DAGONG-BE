package com.capstone2.capstone2.domain.location.repository;

import com.capstone2.capstone2.domain.location.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import com.capstone2.capstone2.domain.location.entity.City;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findByNameAndCity(String name, City city);
    long countByCity(City city);
}
