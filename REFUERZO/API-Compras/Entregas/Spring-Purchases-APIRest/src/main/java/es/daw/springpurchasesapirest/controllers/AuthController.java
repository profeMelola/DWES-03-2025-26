package es.daw.springpurchasesapirest.controllers;


import es.daw.springpurchasesapirest.dtos.ApiResponse;
import es.daw.springpurchasesapirest.dtos.AuthRequest;
import es.daw.springpurchasesapirest.dtos.AuthResponse;
import es.daw.springpurchasesapirest.dtos.RegisterRequest;
import es.daw.springpurchasesapirest.exceptions.RoleNotFoundException;
import es.daw.springpurchasesapirest.exceptions.UserAlreadyExistsException;
import es.daw.springpurchasesapirest.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) throws UserAlreadyExistsException, RoleNotFoundException {

        authService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "User registered successfully"));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {


        Optional<String> token = authService.logIn(request);

        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new AuthResponse(token.get()));

    }



}
