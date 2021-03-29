package com.nazarov.springrestapi.service;

import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.model.User;

public interface EventService {
    void createEvent(User user, File file, String event);
}
