package com.nazarov.springrestapi.service.utils;

import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.model.User;
import org.springframework.stereotype.Component;

@Component
public class EventDefinitions {
    private static final String SET_FILE_TO_USER = "The user with id:%d was set file with id:%d";
    private static final String UPLOAD_FILE_TO_USER = "The file:%s was upload and set for user with id:%d";
    private static final String DELETE_FILE = "The file:%s was deleted";

    public static String setFileToUser(Long userId, Long fileId){
        return String.format(SET_FILE_TO_USER, userId, fileId);
    }

    public static String uploadFileToUser(User user, File file){
        return String.format(UPLOAD_FILE_TO_USER, file.getName(), user.getId());
    }

    public static String deleteFile(File file){
        return String.format(DELETE_FILE, file.getName());
    }
}
