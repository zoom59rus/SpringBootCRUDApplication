package com.nazarov.springrestapi.rest;

import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.service.ModeratorService;
import com.nazarov.springrestapi.service.impl.ModeratorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@Tag(name = "Moderator controller.", description = "Allows set and remove files on users any role.")
@RequestMapping("/api/v1/moderators")
@PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
public class ModeratorController {

    private final ModeratorService moderatorService;

    public ModeratorController(ModeratorServiceImpl moderatorService) {
        this.moderatorService = moderatorService;
    }

    @PostMapping(value = "/set/{userId}/{fileId}", produces = MediaType.TEXT_HTML_VALUE)
    @Operation(summary = "Add a file to user by id.", description = "Allows add a file to user by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "304", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<?> setFileToUser(@PathVariable("userId") @Parameter(description = "User id") final Long userId,
                                           @PathVariable("fileId") @Parameter(description = "File id") final Long fileId) {
        if (userId == null || fileId == null) {
            return new ResponseEntity<>(new Error(), HttpStatus.BAD_REQUEST);
        }

        try {
            moderatorService.setFileForUser(userId, fileId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {

        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete/{fileId}", produces = MediaType.TEXT_HTML_VALUE)
    @Operation(summary = "Delete file by id.", description = "Allows delete file by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "304", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<?> deleteFile(@PathVariable("fileId") @Parameter(description = "File id") final Long fileId) {
        if (fileId == null) {
            return new ResponseEntity<>(new Error(), HttpStatus.BAD_REQUEST);
        }

        try {
            moderatorService.removeFile(fileId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "/upload/{userId}/{bucket}/{filePath}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload file on AWS S3 and put data request into DB.", description = "Allows upload file on AWS S3 and put data request into DB")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = File.class))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "304", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<File> uploadFile(@PathVariable("userId") @Parameter(description = "User id") final Long userId,
                                           @PathVariable("bucket") @Parameter(description = "Bucket by AWS S3") final String bucket,
                                           @PathVariable("filePath") @Parameter(description = "File path on upload") final String filePath) {
        if (userId == null || bucket == null || filePath == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            File file = moderatorService.uploadFile(userId, bucket, filePath);
            return ResponseEntity.ok(file);
        } catch (IOException e) {
            log.error("IN - uploadFile - error " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
