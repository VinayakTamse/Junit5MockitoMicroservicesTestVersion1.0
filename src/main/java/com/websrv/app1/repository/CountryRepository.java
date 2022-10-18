package com.websrv.app1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.websrv.app1.bean.Country;

public interface CountryRepository extends JpaRepository<Country,Integer> {

}
