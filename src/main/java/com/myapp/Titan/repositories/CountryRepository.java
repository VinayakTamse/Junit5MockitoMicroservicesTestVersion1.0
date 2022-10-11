package com.myapp.Titan.repositories;

import com.myapp.Titan.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Integer> {
}
