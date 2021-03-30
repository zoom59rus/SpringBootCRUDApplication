package com.nazarov.springrestapi.service.impl;

import com.nazarov.springrestapi.model.*;
import com.nazarov.springrestapi.model.dto.AccountDto;
import com.nazarov.springrestapi.model.enums.AccountStatus;
import com.nazarov.springrestapi.model.enums.RoleNames;
import com.nazarov.springrestapi.repository.AccountRepository;
import com.nazarov.springrestapi.repository.EventRepository;
import com.nazarov.springrestapi.repository.RoleRepository;
import com.nazarov.springrestapi.repository.UserRepository;
import com.nazarov.springrestapi.service.AWS3Service;
import com.nazarov.springrestapi.service.AdminService;
import com.nazarov.springrestapi.service.EventService;
import com.nazarov.springrestapi.service.ModeratorService;
import com.nazarov.springrestapi.service.utils.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final EventService eventService;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final RegistrationService registrationService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ModeratorService moderatorService;
    private final AWS3Service aws3Service;

    public AdminServiceImpl(EventService eventService, AccountRepository accountRepository, RoleRepository roleRepository, RegistrationService registrationService, UserService userService, UserRepository userRepository, EventRepository eventRepository, ModeratorServiceImpl moderatorService, AWS3Service aws3Service) {
        this.eventService = eventService;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.registrationService = registrationService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.moderatorService = moderatorService;
        this.aws3Service = aws3Service;
    }

    @Override
    public Account createAccount(AccountDto accountDto) {
        Account account = registrationService.registrationAccount(accountDto);

        return account;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void setAccountStatus(Long accountId, AccountStatus status) {
        Account account = accountRepository.getOne(accountId);
        try {
            account.setStatus(status);
            accountRepository.save(account);
        } catch (Exception e){
            log.error("IN - setAccountStatus - " + e.getMessage());
        }
    }

    @Override
    public void setAccountRole(Long accountId, Long roleId) {
        Account account = accountRepository.getOne(accountId);
        Role find = roleRepository.findById(roleId).orElse(null);
        if(find == null){
            log.error("IN - setAccountRole - role:{} not found.", roleId);
            return;
        }
        if(account.getRoles().contains(find)){
            log.info("IN - setAccountRole - role:{} was added.", roleId);
            return;
        }

        account.getRoles().add(find);
        accountRepository.save(account);
    }

    @Override
    public void setAccountPassword(Long accountId, String password) {
        Account account = accountRepository.getOne(accountId);
        account.setPassword(password);
        accountRepository.save(account);
    }

    @Override
    public void setAccountUser(Long accountId, Long userId) {
        Account account = accountRepository.getOne(accountId);
        User user = userRepository.getOne(userId);
        user.setAccount(account);
        userRepository.save(user);
    }

    @Override
    public void setAccountUser(Long accountId, User user) {
        Account account = accountRepository.getOne(accountId);
        try{
            user.setAccount(account);
            userRepository.save(user);
        }catch (Exception e){
            log.error("IN -setAccountUser - " + e.getMessage());
        }
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
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

    @Override
    public List<Event> getEventsByUserId(Long userId) {
        return userService.getEventsByUserId(userId);
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }
}
