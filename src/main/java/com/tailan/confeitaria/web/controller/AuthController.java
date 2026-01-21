package com.tailan.confeitaria.web.controller;

import com.tailan.confeitaria.web.services.AuthService;
import com.tailan.confeitaria.web.services.dtos.request.UserLoginDTO;
import com.tailan.confeitaria.web.services.dtos.request.UserRegisterDTO;
import com.tailan.confeitaria.web.services.dtos.response.ApiResponseDTO;
import com.tailan.confeitaria.web.services.dtos.response.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "User register")
    public ResponseEntity<ApiResponseDTO> registerUser(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos para usuario se registrar.") @Valid UserRegisterDTO userRegisterDTO){
        authService.registerUser(userRegisterDTO);
        ApiResponseDTO responseDTO = new ApiResponseDTO(null, HttpStatus.CREATED.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity<ApiResponseDTO>loginUser(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos para realizar login.") @Valid UserLoginDTO userLoginDTO){
        LoginResponseDTO loginResponseDTO = authService.login(userLoginDTO);
        ApiResponseDTO responseDTO = new ApiResponseDTO(loginResponseDTO, HttpStatus.OK.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
