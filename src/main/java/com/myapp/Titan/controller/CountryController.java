package com.myapp.Titan.controller;

import com.myapp.Titan.model.Country;
import com.myapp.Titan.repositories.CountryRepository;
import com.myapp.Titan.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryService serv;
    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Country>> getAllCountries()
    {
       try {
           List<Country> lists =  serv.getCountries();
           return new ResponseEntity<List<Country>>(lists, HttpStatus.OK);
       }
       catch (Exception e)
       {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

    }


    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable int id)

    {
            try {
                Country country = serv.getCountryById(id);
                return new ResponseEntity<Country>(country, HttpStatus.OK);
            }
            catch (Exception e)
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @PostMapping("/add")
    public ResponseEntity<Country> addCountry(@RequestBody Country country)
    {
        try {
            Country c = serv.addCountry(country);
            return new ResponseEntity<Country>(c, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value="id") int id, @RequestBody Country country)
    {
        try {
            Country existingCountry = serv.getCountryById(id);
            existingCountry.setName(country.getName());
            existingCountry.setCapital(country.getCapital());
            Country newCountry = serv.updateCountry(existingCountry);
            return new ResponseEntity<Country>(newCountry, HttpStatus.ACCEPTED);
        }
        catch (Exception e)
        {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/name")
    public ResponseEntity<Country> getCountryByName(@RequestParam(value="q") String name)
    {
        try {
            Country country = serv.getCountryByName(name);
            return new ResponseEntity<Country>(country, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCountry(@PathVariable int id)
    {
        try {
            serv.deleteCountry(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
