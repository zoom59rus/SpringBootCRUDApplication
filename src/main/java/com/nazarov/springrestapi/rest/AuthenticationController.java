package com.nazarov.springrestapi.rest;

import com.nazarov.springrestapi.model.Account;
import com.nazarov.springrestapi.repository.AccountRepository;
import com.nazarov.springrestapi.security.JwtTokenProvider;
import com.nazarov.springrestapi.security.dto.AuthenticationRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(AuthenticationManager authenticationManager, AccountRepository accountRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequestDto authenticationRequestDto){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDto.getLogin(),
                                                                                        authenticationRequestDto.getPassword()));
            Account account = accountRepository.findByLogin(authenticationRequestDto.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String token = jwtTokenProvider.createToken(authenticationRequestDto.getLogin(), account.getRoles().get(0).getName().name());
            Map<Object, Object> tokenHeaders = new HashMap<>();
            tokenHeaders.put("login", authenticationRequestDto.getLogin());
            tokenHeaders.put("token", token);

            return ResponseEntity.ok(tokenHeaders);
        }catch (AuthenticationException e){
            new ResponseEntity<>("Invalid login or password", HttpStatus.FORBIDDEN);
        }
        return null;
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(httpRequest, httpResponse, null);
    }
}