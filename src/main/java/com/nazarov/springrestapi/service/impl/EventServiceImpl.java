package com.nazarov.springrestapi.service.impl;

import com.nazarov.springrestapi.model.Event;
import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.model.User;
import com.nazarov.springrestapi.repository.EventRepository;
import com.nazarov.springrestapi.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void createEvent(User user, File file, String event) {
        Event ev = new Event();
        ev.setUser(user);
        ev.setFile(file);
        ev.setEvent(event);
        eventRepository.save(ev);
    }

    @Override
    public List<Event> getEventByUserId(Long userId) {
        return eventRepository.getEventByUserId(userId);
    }
}
