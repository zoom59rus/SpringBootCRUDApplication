package com.nazarov.springrestapi.rest;

import com.nazarov.springrestapi.model.Account;
import com.nazarov.springrestapi.model.dto.AccountDto;
import com.nazarov.springrestapi.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registrations")
@PreAuthorize("isAnonymous()")
@Tag(name = "New account registration controller.", description = "Allows register a new user account.")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PutMapping(value = "/accounts", produces = "application/json")
    @Operation(summary = "Registering a new account.",
            description = "Любой не аутентифицированный пользователь имеет доступ к регистрации нового аккаунта.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Create a new account.", content = {@Content(schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)}),
            @ApiResponse(responseCode = "304", description = "Account do not created", content = {@Content(mediaType = MediaType.TEXT_HTML_VALUE)})
    })
    public ResponseEntity<?> registration(@RequestBody final AccountDto accountDto) {
        if (accountDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account account = registrationService.registrationAccount(accountDto);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
