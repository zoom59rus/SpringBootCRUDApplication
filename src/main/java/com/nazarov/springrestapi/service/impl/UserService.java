package com.nazarov.springrestapi.service.impl;

import com.nazarov.springrestapi.model.Event;
import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.repository.EventRepository;
import com.nazarov.springrestapi.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements com.nazarov.springrestapi.service.UserService {

    private final FileRepository fileRepository;

    private final EventRepository eventRepository;

    public UserService(FileRepository fileRepository, EventRepository eventRepository) {
        this.fileRepository = fileRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public File getFileById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public File getFileByName(String name) {
        File file = fileRepository.getFileByName(name);
        if (file != null) {
            return file;
        } else return null;
    }

    @Override
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public List<File> getAllFilesByType(String type) {
        return fileRepository.getFilesByType(type);
    }

    @Override
    public Event findById(Long id){
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public List<Event> getEventsByFileId(Long id) {
        return eventRepository.getEventsByFileId(id);
    }

    @Override
    public List<Event> getEventsByFileName(String name) {
        return eventRepository.getEventsByFileName(name);
    }

    @Override
    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }
}
