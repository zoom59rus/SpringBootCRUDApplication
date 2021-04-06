package com.nazarov.springrestapi.service;

import com.nazarov.springrestapi.model.Account;
import com.nazarov.springrestapi.model.Event;
import com.nazarov.springrestapi.model.Role;
import com.nazarov.springrestapi.model.User;
import com.nazarov.springrestapi.model.dto.AccountDto;
import com.nazarov.springrestapi.model.enums.AccountStatus;
import com.nazarov.springrestapi.model.enums.RoleNames;

import java.util.List;

public interface AdminService extends UserService, ModeratorService {

    Account createAccount(AccountDto accountDto);
    User createUser(User user);


    void setAccountStatus(Long accountId, AccountStatus status);
    void setAccountRole(Long accountId, Long roleId);
    void setAccountPassword(Long accountId, String password);
    void setAccountUser(Long accountId, User user);
    void setAccountUser(Long accountId, Long userId);
    void removeAccount(Long id);
    void removeUser(Long id);
    void removeRole(Long id);

    Account getAccountById(Long id);
    List<Account> getAllAccounts();
    User getUserById(Long id);
    List<User> getAllUsers();
    List<Role> getAllRole();
    Role getRoleById(Long roleId);
}