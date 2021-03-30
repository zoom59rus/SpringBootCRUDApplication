package com.nazarov.springrestapi.service;

import com.nazarov.springrestapi.model.Event;
import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.model.User;

import java.util.List;

public interface EventService {
    void createEvent(User user, File file, String event);
    List<Event> getEventByUserId(Long userId);
}
