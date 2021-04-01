package com.nazarov.springrestapi.security;

import com.nazarov.springrestapi.model.Account;
import com.nazarov.springrestapi.model.enums.AccountStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class AccountDetails implements UserDetails {

    private String login;
    private String password;
    private AccountStatus status;
    private boolean isActive;
    private List<SimpleGrantedAuthority> authorities;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromAccount(Account account){
        return new User(
                account.getLogin(),
                account.getPassword(),
                account.getStatus().name().equals("ACTIVE"),
                account.getStatus().name().equals("ACTIVE"),
                account.getStatus().name().equals("ACTIVE"),
                account.getStatus().name().equals("ACTIVE"),
                account.getRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName().name()))
                        .collect(Collectors.toSet()));
    }
}