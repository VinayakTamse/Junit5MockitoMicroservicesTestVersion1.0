package com.myapp.Titan.services;

import com.myapp.Titan.model.Country;

import com.myapp.Titan.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class CountryService {

    @Autowired
    CountryRepository repo;

    public List<Country> getCountries()
    {
        List<Country> lists = repo.findAll();
        return lists;
    }

    public Country getCountryById(int id)
    {
        return repo.findById(id).get();
    }

    public Country addCountry(Country country)
    {
        Country c = repo.save(country);
        return c;
    }

    public Country updateCountry(Country country)
    {
           return repo.save(country);

    }

    public Country getCountryByName(String name)
    {
        return repo.findAll().stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public void deleteCountry(int id)
    {
        repo.deleteById(id);
    }

}
