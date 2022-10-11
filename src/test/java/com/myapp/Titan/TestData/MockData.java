package com.myapp.Titan.TestData;

import com.myapp.Titan.model.Country;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static List<Country> listsOfCountries()
    {
        List<Country> lists = new ArrayList<Country>();
        lists.add(new Country(1, "India", "Delhi"));
        lists.add(new Country(2, "Japan", "Tokyo"));
        return lists;
    }
}
