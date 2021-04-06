package com.nazarov.springrestapi.rest;

import com.nazarov.springrestapi.model.Event;
import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User controller.", description = "This control accept a request from accounts with the USER and ADMIN roles.")
@RestController
@RequestMapping(value = "/api/v1/users")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/files/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get file on id.", description = "Allows get file on id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = File.class))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<File> getFileById(@PathVariable("id")
                                            @Parameter(description = "File id", required = true) final Long id) {
        if (id == null) {
            return new ResponseEntity(new Error("Параметр не может быть null"), HttpStatus.BAD_REQUEST);
        }

        File file = userService.getFileById(id);
        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(file);
    }

    @GetMapping(value = "/files/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get file on name.", description = "Allows get file on name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = File.class))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<File> getFileByName(@PathVariable("name")
                                              @Parameter(description = "File name") final String name) {
        if (name == null) {
            return new ResponseEntity(new Error("Параметр не может быть null"), HttpStatus.BAD_REQUEST);
        }

        File file = userService.getFileByName(name);
        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(file);
    }

    @GetMapping(value = "/files/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all files.", description = "Allows get all files.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = File.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<List<File>> getAllFiles() {
        List<File> result = userService.getAllFiles();
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/files/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get files on type.", description = "Allows get files on type.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = File.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<?> getAllFilesByType(@PathVariable("type")
                                                        @Parameter(description = "File type") final String type) {
        List<File> result = userService.getAllFilesByType(type);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get event by id.", description = "Allows get event by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<Event> getById(@PathVariable("id")
                                         @Parameter(description = "Event id") final Long id) {
        Event result = userService.findById(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/events/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all events.", description = "Allows get all events.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Event.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity getAllEvents() {
        List<Event> result = userService.getAllEvent();
        if (result == null) {
            return new ResponseEntity<HttpMessage>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/events/file/{fileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get event by file id.", description = "Allows get event by file id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Event.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<List<Event>> getEventsByFileId(@PathVariable("fileId")
                                                         @Parameter(description = "File id") final Long fileId) {
        List<Event> result = userService.getEventsByFileId(fileId);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/events/file/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get event by file name.", description = "Allows get event by file name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Event.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<List<Event>> getEventsByFileName(@PathVariable("fileName")
                                                           @Parameter(description = "File name") final String fileName) {
        List<Event> result = userService.getEventsByFileName(fileName);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }
}
