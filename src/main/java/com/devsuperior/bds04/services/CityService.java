package com.devsuperior.bds04.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;


@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true )
	public List<CityDTO> findAll() {

		List<City> cities = new ArrayList<>();
		cities = cityRepository.findAll(Sort.by("name"));
		
		return cities.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public CityDTO insert(CityDTO cityDTO) {
		
		City city = new City();
		city.setName(cityDTO.getName());
		
		city = cityRepository.save(city);
		
		return new CityDTO(city);
	}

}
