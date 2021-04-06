package com.nazarov.springrestapi.rest;

import com.nazarov.springrestapi.model.Account;
import com.nazarov.springrestapi.repository.AccountRepository;
import com.nazarov.springrestapi.security.JwtTokenProvider;
import com.nazarov.springrestapi.security.dto.AuthenticationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@Tag(name = "Authentication controller.", description = "Authentication controller was through on JWT token.")
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

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    @Operation(summary = "Authentication user.", description = "Allows authentication user.")
    @PreAuthorize("permitAll()")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Object, Object>> authentication(@RequestBody @Parameter(description = "Request on JSON format.",
            schema = @Schema(implementation = AuthenticationRequestDto.class)) AuthenticationRequestDto authenticationRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDto.getLogin(),
                    authenticationRequestDto.getPassword()));
            Account account = accountRepository.findByLogin(authenticationRequestDto.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String token = jwtTokenProvider.createToken(authenticationRequestDto.getLogin(), account.getRoles().get(0).getName().name());
            Map<Object, Object> tokenHeaders = new HashMap<>();
            tokenHeaders.put("login", authenticationRequestDto.getLogin());
            tokenHeaders.put("token", token);

            return ResponseEntity.ok(tokenHeaders);
        } catch (AuthenticationException e) {
            new ResponseEntity<>("Invalid login or password", HttpStatus.FORBIDDEN);
        }
        return null;
    }

    @Operation(summary = "User logout.", description = "Allows user logout.")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(httpRequest, httpResponse, null);
    }
}