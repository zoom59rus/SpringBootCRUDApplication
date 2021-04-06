package com.nazarov.springrestapi.rest;

import com.nazarov.springrestapi.model.*;
import com.nazarov.springrestapi.model.dto.AccountDto;
import com.nazarov.springrestapi.model.dto.UserDto;
import com.nazarov.springrestapi.model.enums.AccountStatus;
import com.nazarov.springrestapi.service.AdminService;
import com.nazarov.springrestapi.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Administration controller.", description = "Provides full access on this application.")
@RestController
@RequestMapping("/api/v1/admins")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final RegistrationService registrationService;
    private final UserController userController;
    private final ModeratorController moderatorController;

    public AdminController(AdminService adminService, RegistrationService registrationService, UserController userController, ModeratorController moderatorController) {
        this.adminService = adminService;
        this.registrationService = registrationService;
        this.userController = userController;
        this.moderatorController = moderatorController;
    }

    @GetMapping(value = "/accounts/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns all accounts.", description = "Allows returns all account.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Account.class)))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = adminService.getAllAccounts();

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(value = "/accounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns accounts by id.", description = "Allows returns account by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<Account> getAccountById(@PathVariable("id")
                                                  @Parameter(description = "Account id") final Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Account account = adminService.getAccountById(id);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            log.error("IN - endpoint /accounts/{id} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/users/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns all users.", description = "Allows returns all users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns user by id.", description = "Allows returns user by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<User> getUserById(@PathVariable("id")
                                            @Parameter(description = "User id") final Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            User user = adminService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("IN - endpoint /users/{id} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/files/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns all files.", description = "Allows returns all files.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = File.class)))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<List<File>> getAllFiles() {
        try {
            List<File> files = adminService.getAllFiles();
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            log.error("IN - endpoint /files/all - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/files/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns file by id.", description = "Allows returns file by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = File.class))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<File> getFileById(@PathVariable("id")
                                            @Parameter(description = "File id") final Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            File file = adminService.getFileById(id);
            return ResponseEntity.ok(file);
        } catch (Exception e) {
            log.error("IN - endpoint /files/{id} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/events/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns all events.", description = "Allows returns all events.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Event.class)))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<List<Event>> getAllEvents() {
        try {
            List<Event> events = adminService.getAllEvent();
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            log.error("IN - endpoint /events/all - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/events/file/{fileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns events by file id.", description = "Allows returns events by file id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Event.class)))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<List<Event>> getEventByFileId(@PathVariable("fileId")
                                                        @Parameter(description = "File id") final Long fileId) {
        if (fileId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            List<Event> events = adminService.getEventsByFileId(fileId);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            log.error("IN - endpoint /events/{fileId} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/events/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns events by user id.", description = "Allows returns events by user id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Event.class)))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<List<Event>> getEventByUserId(@PathVariable("userId")
                                                        @Parameter(description = "User id") final Long userId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            List<Event> events = adminService.getEventsByUserId(userId);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            log.error("IN - endpoint /events/{userId} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/roles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns all roles.", description = "Allows returns all roles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Role.class)))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<List<Role>> getAllRoles() {
        try {
            List<Role> roles = adminService.getAllRole();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            log.error("IN - endpoint /roles/all - " + e.getMessage());

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns role by id.", description = "Allows returns role by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Role.class))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    public ResponseEntity<?> getRoleById(@PathVariable("id")
                                         @Parameter(description = "Role id") final Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Role role = adminService.getRoleById(id);
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            log.error("IN - endpoint /roles/{id} - " + e.getMessage());

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/accounts/{id}/changes/password/{password}")
    @Operation(summary = "Changes password on account by id.", description = "Allows changes password on account by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<Void> changeAccountPassword(@PathVariable(value = "id") @Parameter(description = "Account id") final Long id,
                                                      @PathVariable("password") @Parameter(description = "New password") final String password) {
        if (id == null || password == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            adminService.setAccountPassword(id, password);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("IN - endpoint /accounts/{id}/changes/password/{password} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/accounts/{id}/changes/role/{roleId}")
    @Operation(summary = "Changes role on account by id.", description = "Allows changes role on account by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<Void> changeAccountRole(@PathVariable("id") @Parameter(description = "Account id") final Long id,
                                                  @PathVariable("roleId") @Parameter(description = "Role id") final Long roleId) {
        if (id == null || roleId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            adminService.setAccountRole(id, roleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("IN - endpoint /accounts/{id}/changes/role/{roleid} - " + e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/accounts/{id}/changes/status/{status}")
    @Operation(summary = "Changes status on account by id.", description = "Allows changes status on account by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<Void> changeAccountStatus(@PathVariable("id") @Parameter(description = "Account id") final Long id,
                                                    @PathVariable("status") @Parameter(description = "Status id") final String status) {
        if (id == null || status == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            adminService.setAccountStatus(id, AccountStatus.valueOf(status));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("IN - endpoint /accounts/{id}/changes/status/{status} - status:{} invalid.", status);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("IN - endpoint /accounts/{id}/changes/status/{status} - {}", e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/accounts/{id}/changes/user/{userId}")
    @Operation(summary = "Changes user profile on account by id.", description = "Allows changes user profile on account by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<?> setUserOnAccountById(@PathVariable("id") @Parameter(description = "Account id") final Long id,
                                                  @PathVariable("userId") @Parameter(description = "User id") final Long userId) {
        if (id == null || userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            adminService.setAccountUser(id, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("IN - endpoint /admins/accounts/{id}/changes/user/{userid} - {}", e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/accounts/{id}/add/user")
    @Operation(summary = "Add user profile on account by id.", description = "Allows add user profile on account by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<Void> setUserOnAccountByUser(@PathVariable("id") @Parameter(description = "Account id") final Long id,
                                                       @RequestBody @Parameter(description = "UserDto on JSON format",
                                                               schema = @Schema(implementation = UserDto.class)) final UserDto userDto) {
        if (id == null || userDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        try {
            adminService.setAccountUser(id, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("IN - endpoint /admins/accounts/{id}/add/user - {}", e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "/accounts/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add account on application.", description = "Add account application, redirect to registration controller.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<Account> createAccount(@RequestBody @Parameter(description = "AccountDto on JSON format",
            schema = @Schema(implementation = AccountDto.class)) final AccountDto accountDto) {
        if (accountDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account account = registrationService.registrationAccount(accountDto);
        return ResponseEntity.ok(account);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "304", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    @Operation(summary = "Add a file to user by id.", description = "Allows add a file to user by id.")
    @PostMapping(value = "/set/{userId}/{fileId}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Void> setFileToUser(@PathVariable("userId") @Parameter(description = "User id") final Long userId,
                                              @PathVariable("fileId") @Parameter(description = "File id") final Long fileId) {
        return moderatorController.setFileToUser(userId, fileId);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "304", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    @Operation(summary = "Delete file by id.", description = "Allows delete file by id.")
    @DeleteMapping(value = "/delete/{fileId}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Void> deleteFile(@PathVariable("fileId") @Parameter(description = "File id on remove") final Long fileId) {
        return moderatorController.deleteFile(fileId);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = File.class))}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "304", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    @Operation(summary = "Upload file on AWS S3 and put data request into DB.", description = "Allows upload file on AWS S3 and put data request into DB")
    @PutMapping(value = "/upload/{userId}/{bucket}/{filePath}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<File> uploadFile(@PathVariable("userId") @Parameter(description = "User id") final Long userId,
                                           @PathVariable("bucket") @Parameter(description = "Bucket name") final String bucket,
                                           @PathVariable("filePath") @Parameter(description = "File path on upload") final String filePath) {
        return moderatorController.uploadFile(userId, bucket, filePath);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = File.class))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    @Operation(summary = "Get file on name.", description = "Allows get file on name.")
    @GetMapping(value = "/files/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<File> getFileByName(@PathVariable("name")
                                                  @Parameter(description = "File name") final String name) {
        return userController.getFileByName(name);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = File.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    @Operation(summary = "Get files on type.", description = "Allows get files on type.")
    @GetMapping(value = "/files/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllFilesByType(@PathVariable("type")
                                                   @Parameter(description = "File type") final String type) {
        return userController.getAllFilesByType(type);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    @Operation(summary = "Get event by id.", description = "Allows get event by id.")
    @GetMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> getById(@PathVariable("id")
                                             @Parameter(description = "Event id") final Long id) {
        return userController.getById(id);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Event.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
    })
    @Operation(summary = "Get event by file name.", description = "Allows get event by file name.")
    @GetMapping(value = "/events/file/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> getEventsByFileName(@PathVariable("fileName")
                                                               @Parameter(description = "File name") final String fileName) {
        return userController.getEventsByFileName(fileName);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "304", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    @Operation(summary = "Delete account by id.", description = "Allows delete account by id.")
    @DeleteMapping(value = "/deletes/{accountId}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Void> deleteAccount(@PathVariable("accountId")
                                               @Parameter(description = "Account id on remove") final Long accountId) {
        if(accountId == null){
            return new ResponseEntity(new Error("Account id con't be null."), HttpStatus.BAD_REQUEST);
        }

        try{
            adminService.removeAccount(accountId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "304", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    @Operation(summary = "Delete user by id.", description = "Allows delete user by id.")
    @DeleteMapping(value = "/deletes/{userId}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Void> deleteUser(@PathVariable("userId")
                                              @Parameter(description = "User id on remove") final Long userId) {
        if(userId == null){
            return new ResponseEntity(new Error("Account id con't be null."), HttpStatus.BAD_REQUEST);
        }

        try{
            adminService.removeUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "304", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    @Operation(summary = "Delete role by id.", description = "Allows delete role by id.")
    @DeleteMapping(value = "/deletes/{roleId}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Void> deleteRole(@PathVariable("roleId")
                                           @Parameter(description = "Role id on remove") final Long roleId) {
        if(roleId == null){
            return new ResponseEntity(new Error("Role id con't be null."), HttpStatus.BAD_REQUEST);
        }

        try{
            adminService.removeUser(roleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}