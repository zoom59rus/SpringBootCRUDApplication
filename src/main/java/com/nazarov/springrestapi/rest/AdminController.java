package com.nazarov.springrestapi.rest;

import com.nazarov.springrestapi.model.*;
import com.nazarov.springrestapi.model.dto.AccountDto;
import com.nazarov.springrestapi.model.dto.UserDto;
import com.nazarov.springrestapi.model.enums.AccountStatus;
import com.nazarov.springrestapi.service.AdminService;
import com.nazarov.springrestapi.service.utils.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final AdminService adminService;
    private final RegistrationService registrationService;

    public AdminController(AdminService adminService, RegistrationService registrationService) {
        this.adminService = adminService;
        this.registrationService = registrationService;
    }

    @GetMapping("/accounts/all")
    public ResponseEntity<?> getAllAccounts(){
        List<Account> accounts = adminService.getAllAccounts();

        return new ResponseEntity(accounts, HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable("id") Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            Account account = adminService.getAccountById(id);
            return ResponseEntity.ok(account);
        }catch (Exception e){
            log.error("IN - endpoint /accounts/{id} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = adminService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            User user = adminService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e){
            log.error("IN - endpoint /users/{id} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/files/all")
    public ResponseEntity<?> getAllFiles(){
        try{
            List<File> files = adminService.getAllFiles();
            return ResponseEntity.ok(files);
        } catch (Exception e){
            log.error("IN - endpoint /files/all - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<?> getFileById(@PathVariable("id") Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            File file = adminService.getFileById(id);
            return ResponseEntity.ok(file);
        } catch (Exception e){
            log.error("IN - endpoint /files/{id} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/events/all")
    public ResponseEntity<?> getAllEvents(){
        try{
            List<Event> events = adminService.getAllEvent();
            return ResponseEntity.ok(events);
        } catch (Exception e){
            log.error("IN - endpoint /events/all - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/events/file/{fileid}")
    public ResponseEntity<?> getEventByFileId(@PathVariable("fileid") Long fileid){
        System.out.println("Enter");
        if(fileid == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            List<Event> events = adminService.getEventsByFileId(fileid);
            return ResponseEntity.ok(events);
        }catch (Exception e){
            log.error("IN - endpoint /events/{fileid} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/events/user/{userid}")
    public ResponseEntity<?> getEventByUserId(@PathVariable("userid") Long userId){
        if(userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            List<Event> events = adminService.getEventsByUserId(userId);
            return ResponseEntity.ok(events);
        }catch (Exception e){
            log.error("IN - endpoint /events/{fileid} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/roles/all")
    public ResponseEntity<?> getAllRoles(){
        try{
            List<Role> roles = adminService.getAllRole();
            return ResponseEntity.ok(roles);
        } catch (Exception e){
            log.error("IN - endpoint /roles/all - " + e.getMessage());

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable("id") Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            Role role = adminService.getRoleById(id);
            return ResponseEntity.ok(role);
        } catch (Exception e){
            log.error("IN - endpoint /roles/{id} - " + e.getMessage());

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/accounts/{id}/changes/password/{password}")
    public ResponseEntity<?> changeAccountPassword(@PathVariable(value = "id") Long id, @PathVariable("password") String password){
        if(id == null || password == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            adminService.setAccountPassword(id, password);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            log.error("IN - endpoint /accounts/{id}/changes/password/{password} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/accounts/{id}/changes/role/{roleid}")
    public ResponseEntity<?> changeAccountRole(@PathVariable("id") Long id, @PathVariable("roleid") Long roleid){
        if(id == null || roleid == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            adminService.setAccountRole(id, roleid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            log.error("IN - endpoint /accounts/{id}/changes/role/{roleid} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/accounts/{id}/changes/status/{status}")
    public ResponseEntity<?> changeAccountStatus(@PathVariable("id") Long id, @PathVariable("status") String status){
        if(id == null || status == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            adminService.setAccountStatus(id, AccountStatus.valueOf(status));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            log.error("IN - endpoint /accounts/{id}/changes/status/{status} - status:{} invalid.", status);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.error("IN - endpoint /accounts/{id}/changes/status/{status} - {}", e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/accounts/{id}/changes/user/{userid}")
    public ResponseEntity<?> setUserOnAccountById(@PathVariable("id") Long id, @PathVariable("userid") Long userid){
        if(id == null || userid == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            adminService.setAccountUser(id, userid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            log.error("IN - endpoint /admins/accounts/{id}/changes/user/{userid} - {}", e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/accounts/{id}/add/user")
    public ResponseEntity<?> setUserOnAccountByUser(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        if(id == null || userDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        try{
            adminService.setAccountUser(id, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            log.error("IN - endpoint /admins/accounts/{id}/add/user - {}", e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/accounts/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto){
        if(accountDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account account = registrationService.registrationAccount(accountDto);
        return ResponseEntity.ok(account);
    }
}