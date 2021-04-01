package com.nazarov.springrestapi.rest;

import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.service.ModeratorService;
import com.nazarov.springrestapi.service.impl.ModeratorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/moderators")
@PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
public class ModeratorController {

    private final ModeratorService moderatorService;

    public ModeratorController(ModeratorServiceImpl moderatorService) {
        this.moderatorService = moderatorService;
    }

    @PostMapping(value = "/set", params = {"userid", "fileid"})
    private ResponseEntity<?> setFileToUser(@RequestParam("userid") Long userid, @RequestParam("fileid") Long fileid) {
        if (userid == null || fileid == null) {
            return new ResponseEntity<>(new Error(), HttpStatus.BAD_REQUEST);
        }

        try {
            moderatorService.setFileForUser(userid, fileid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {

        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping(value = "/delete", params = "id")
    public ResponseEntity<?> deleteFile(@RequestParam("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(new Error(), HttpStatus.BAD_REQUEST);
        }

        try {
            moderatorService.removeFile(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping(value = "/upload", params = {"userid", "bucket", "path"})
    public ResponseEntity<?> uploadFile(final @RequestParam("userid") Long userid, final @RequestParam("bucket") String bucket, final @RequestParam("path") String path) {
        if (userid == null || bucket == null || path == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            File file = moderatorService.uploadFile(userid, bucket, path);
            return ResponseEntity.ok(file);
        } catch (IOException e) {
            log.error("IN - uploadFile - error " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
