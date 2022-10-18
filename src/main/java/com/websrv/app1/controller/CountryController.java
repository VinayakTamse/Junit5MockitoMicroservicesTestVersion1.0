package com.websrv.app1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.websrv.app1.bean.Country;
import com.websrv.app1.services.CountryService;

@RestController
@RequestMapping("/country")
public class CountryController {
	
	@Autowired
	CountryService countryServ;
	
	@GetMapping("/all")
	public ResponseEntity<List<Country>> getAllCountries()
	{
		try {
		List<Country> lists =  countryServ.getAllCountryLists();
		return new ResponseEntity<List<Country>>(lists, HttpStatus.OK);
		
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Country> getCountryById(@PathVariable(value="id") int id)
	{
		try {
			Country country = countryServ.getCountryById(id);
			return new ResponseEntity<Country>(country,HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping("/name")
	public ResponseEntity<Country> getCountryByName(@RequestParam(value="qname") String name)
	{
		try {
			Country country = countryServ.getCountryByName(name);
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Country> addCountry(@RequestBody Country country)
	{
		try {
			Country newCountry = countryServ.addCountry(country);
			return new ResponseEntity<Country>(newCountry, HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.IM_USED);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable(value="id") int id, @RequestBody Country country)
	{
		try {
			Country existingCountry = countryServ.getCountryById(id);
			existingCountry.setName(country.getName());
			existingCountry.setCapital(country.getCapital());
			Country newCountry = countryServ.updateCountry(existingCountry);
			return new ResponseEntity<Country>(newCountry,HttpStatus.ACCEPTED);
			
		}
		catch (Exception e)
		{
			try {
				country.setId(id);
				Country c = countryServ.addCountry(country);
				return new ResponseEntity<Country>(c, HttpStatus.CREATED);
				
			}
			catch (Exception e2)
			{
				
			}
			
			return new ResponseEntity<>(HttpStatus.CONFLICT);
			
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCountry(@PathVariable(value="id") int id)
	{
		try {
			countryServ.deleteCountry(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	

}
