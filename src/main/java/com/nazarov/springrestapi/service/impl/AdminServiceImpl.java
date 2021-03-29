package com.nazarov.springrestapi.service.impl;

import com.nazarov.springrestapi.model.Event;
import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.service.AWS3Service;
import com.nazarov.springrestapi.service.AdminService;
import com.nazarov.springrestapi.service.EventService;
import com.nazarov.springrestapi.service.ModeratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final EventService eventService;
    private final UserService userService;
    private final ModeratorService moderatorService;
    private final AWS3Service aws3Service;

    public AdminServiceImpl(EventService eventService, UserService userService, ModeratorServiceImpl moderatorService, AWS3Service aws3Service) {
        this.eventService = eventService;
        this.userService = userService;
        this.moderatorService = moderatorService;
        this.aws3Service = aws3Service;
    }

    @Override
    public File uploadFile(Long userId, String bucket, String path) throws IOException {
        return moderatorService.uploadFile(userId, bucket, path);
    }

    @Override
    public void setFileForUser(Long userId, Long fileId) {
        moderatorService.setFileForUser(userId, fileId);
    }

    @Override
    public void removeFile(Long id) {
        moderatorService.removeFile(id);
    }

    @Override
    public void removeFile(File file) {
        removeFile(file.getId());
    }

    @Override
    public File getFileById(Long id) {
        return userService.getFileById(id);
    }

    @Override
    public File getFileByName(String name) {
        return userService.getFileByName(name);
    }

    @Override
    public List<File> getAllFiles() {
        return userService.getAllFiles();
    }

    @Override
    public List<File> getAllFilesByType(String type) {
        return userService.getAllFilesByType(type);
    }

    @Override
    public Event findById(Long id) {
        return userService.findById(id);
    }

    @Override
    public List<Event> getEventsByFileId(Long id) {
        return userService.getEventsByFileId(id);
    }

    @Override
    public List<Event> getEventsByFileName(String name) {
        return userService.getEventsByFileName(name);
    }

    @Override
    public List<Event> getAllEvent() {
        return userService.getAllEvent();
    }
}
