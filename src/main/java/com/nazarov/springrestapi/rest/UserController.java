package com.nazarov.springrestapi.rest;

import com.nazarov.springrestapi.model.Event;
import com.nazarov.springrestapi.model.File;
import com.nazarov.springrestapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/files", params = "id")
    public ResponseEntity<?> getFileById(final @RequestParam(value = "id") Long id){
        if(id == null){
            return new ResponseEntity<>(new Error("Параметр не может быть null"), HttpStatus.BAD_REQUEST);
        }

        File file = userService.getFileById(id);
        if(file == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(file);
    }

    @GetMapping(value = "/files", params = "name")
    public ResponseEntity<?> getFileByName(final @RequestParam(value = "name") String name){
        if(name == null){
            return new ResponseEntity<>(new Error("Параметр не может быть null"), HttpStatus.BAD_REQUEST);
        }

        File file = userService.getFileByName(name);
        if(file == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(file);
    }

    @GetMapping("/files/all")
    public ResponseEntity<?> getAllFiles(){
        List<File> result = userService.getAllFiles();
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/files", params = "type")
    public ResponseEntity<?> getAllFilesByType(final @RequestParam(value = "type") String type){
        List<File> result = userService.getAllFilesByType(type);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/events", params = "id")
    public ResponseEntity<?> getById(@RequestParam("id") Long id){
        Event result = userService.findById(id);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/events/all")
    public ResponseEntity<?> getAllEvents(){
        List<Event> result = userService.getAllEvent();
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/events", params = "fileid")
    public ResponseEntity<?> getEventsByFileId(@RequestParam("fileid") Long fileId){
        List<Event> result = userService.getEventsByFileId(fileId);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/events", params = "filename")
    public ResponseEntity<?> getEventsByFileName(@RequestParam("filename") String fileName){
        List<Event> result = userService.getEventsByFileName(fileName);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/files/test", params = "id")
    public ResponseEntity<?> test(final @RequestParam(value = "id") Long id){
        if(id == null){
            return new ResponseEntity<>(new Error("Параметр не может быть null"), HttpStatus.BAD_REQUEST);
        }

        File file = userService.getTestFile(id);
        if(file == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println(file);

        return ResponseEntity.ok(file);
    }
}
