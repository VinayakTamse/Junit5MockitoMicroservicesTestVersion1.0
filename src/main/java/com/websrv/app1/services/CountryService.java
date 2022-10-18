package com.websrv.app1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.websrv.app1.bean.Country;
import com.websrv.app1.repository.CountryRepository;

@Service
@Component
public class CountryService {
	
	@Autowired
	CountryRepository countryRepo;
	
	public List<Country> getAllCountryLists()
	{
		List<Country> lists = countryRepo.findAll();
		return lists;
	}
	
	public Country getCountryById(int id)
	{
		Country country = countryRepo.findById(id).get();
		return country;
	}
	
	public Country getCountryByName(String name)
	{
		Country country = null;
		country = countryRepo.findAll().stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().get();
		return country;
	}
	
	public Country addCountry(Country country)
	{
		return countryRepo.save(country);
	}
	
	public Country updateCountry(Country country)
	{
		return countryRepo.save(country);
	}
	
	public void deleteCountry(int id)
	{
		
		countryRepo.deleteById(id);
	
	}

}
