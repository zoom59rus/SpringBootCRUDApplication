package com.nazarov.springrestapi.service.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.model.User;
import com.nazarov.springrestapi.model.enums.FileStatus;
import com.nazarov.springrestapi.repository.EventRepository;
import com.nazarov.springrestapi.repository.FileRepository;
import com.nazarov.springrestapi.repository.UserRepository;
import com.nazarov.springrestapi.service.AWS3Service;
import com.nazarov.springrestapi.service.EventService;
import com.nazarov.springrestapi.service.ModeratorService;
import com.nazarov.springrestapi.service.utils.EventDefinitions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
public class ModeratorServiceImpl implements ModeratorService {

    private FileRepository fileRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private AWS3Service s3Service;
    private EventService eventService;

    public ModeratorServiceImpl(FileRepository fileRepository, UserRepository userRepository, EventRepository eventRepository, AWS3Service s3Service, EventService eventService) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.s3Service = s3Service;
        this.eventService = eventService;
    }

    @Override
    public File uploadFile(Long userId, String bucket, String path) throws IOException {
        S3Object s3Object = s3Service.upload(bucket, path);

        File file = new File();
        file.setName(s3Object.getKey());
        file.setType(FilenameUtils.getExtension(path));
        file.setStatus(FileStatus.ACTIVE);
        file.setSize((float) Files.size(Paths.get(path)));
        file.setUpload(s3Object.getObjectMetadata().getLastModified());

        User user = userRepository.getOne(userId);
        user.getFiles().add(file);
        file.addUser(user);
        fileRepository.save(file);

        eventService.createEvent(user, file, EventDefinitions.uploadFileToUser(user, file));

        return file;
    }

    @Override
    public void setFileForUser(Long userId, Long fileId) {
        File file = fileRepository.getOne(fileId);
        if(file == null){
            log.warn("IN - setFileForUser - file is id:{} not found.", fileId);
            return;
        }
        if (file.getStatus().equals(FileStatus.DELETED) || file.getStatus().equals(FileStatus.BANNED)) {
            log.warn("IN - setFileForUser - file is id:{} deleted or banned. Please, change file status.", fileId);
            return;
        }

        User user = userRepository.getOne(userId);
        user.getFiles().add(file);
        userRepository.save(user);

        eventService.createEvent(user, file, EventDefinitions.setFileToUser(userId, fileId));
    }

    @Override
    public void removeFile(Long fileId) {
        File file = fileRepository.getOne(fileId);
        file.setStatus(FileStatus.DELETED);
        for (User user : file.getUsers()) {
            user.getFiles().remove(file);
        }

        fileRepository.save(file);

        eventService.createEvent(null, file, EventDefinitions.deleteFile(file));
    }

    @Override
    public void removeFile(File file) {
        removeFile(file.getId());
    }
}
