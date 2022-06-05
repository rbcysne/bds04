package com.devsuperior.bds04.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Transactional(readOnly = true )
	public Page<EventDTO> findAll(Pageable pageable) {

		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
		
		Page<Event> page = eventRepository.findAll(pageRequest);
		
		return page.map(x -> new EventDTO(x));
	}

	@Transactional
	public EventDTO insert(EventDTO eventDTO) {

		Event event = new Event();

		this.copyDtoToEntity(eventDTO, event);

		event = this.eventRepository.save(event);

		return new EventDTO(event);
	}

	private void copyDtoToEntity(EventDTO eventDTO, Event event) {

		event.setName(eventDTO.getName());
		event.setDate(eventDTO.getDate());
		event.setUrl(eventDTO.getUrl());
		event.setCity(new City(eventDTO.getCityId(), null));

	}
}
