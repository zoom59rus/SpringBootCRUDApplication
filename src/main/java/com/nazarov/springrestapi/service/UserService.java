package com.nazarov.springrestapi.service;

import com.nazarov.springrestapi.model.Event;
import com.nazarov.springrestapi.model.File;

import java.util.List;

public interface UserService {

    File getFileById(Long id);
    File getFileByName(String name);
    List<File> getAllFiles();
    List<File> getAllFilesByType(String type);

    Event findById(Long id);

    List<Event> getEventsByFileId(Long id);
    List<Event> getEventsByFileName(String name);
    List<Event> getEventsByUserId(Long userId);
    List<Event> getAllEvent();
}