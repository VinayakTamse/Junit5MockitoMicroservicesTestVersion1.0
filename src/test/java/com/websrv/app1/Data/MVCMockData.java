package com.websrv.app1.Data;

import java.util.ArrayList;
import java.util.List;

import com.websrv.app1.bean.Country;

public class MVCMockData {
	
	public static List<Country> getCountryDataLists()
	{
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country(1, "India", "Delhi"));
		countries.add(new Country(2, "Japan", "Tokyo"));
		countries.add(new Country(3, "Nepal", "Katmandu"));
		return countries;
	}
	
	public static Country countryData()
	{
		return new Country(4, "France", "Paris");
	}
	
	public static Country getCountryMock(int id)
	{

		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country(1, "India", "Delhi"));
		countries.add(new Country(2, "Japan", "Tokyo"));
		countries.add(new Country(3, "Nepal", "Katmandu"));
		return countries.stream().filter(e -> e.getId()==id).findFirst().get();
	}


}
