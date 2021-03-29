package com.nazarov.springrestapi.service;

import com.nazarov.springrestapi.model.File;

import java.io.IOException;

public interface ModeratorService{

    File uploadFile(Long userId, String bucket, String path) throws IOException;

    void setFileForUser(Long userId, Long fileId);
    void removeFile(Long id);
    void removeFile(File file);
}
