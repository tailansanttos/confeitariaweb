package com.tailan.confeitaria.web.controller;

import com.tailan.confeitaria.web.services.AuthService;
import com.tailan.confeitaria.web.services.dtos.request.UserLoginDTO;
import com.tailan.confeitaria.web.services.dtos.request.UserRegisterDTO;
import com.tailan.confeitaria.web.services.dtos.response.ApiResponseDTO;
import com.tailan.confeitaria.web.services.dtos.response.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        authService.registerUser(userRegisterDTO);
        ApiResponseDTO responseDTO = new ApiResponseDTO(null, HttpStatus.CREATED.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO>loginUser(@RequestBody @Valid UserLoginDTO userLoginDTO){
        LoginResponseDTO loginResponseDTO = authService.login(userLoginDTO);
        ApiResponseDTO responseDTO = new ApiResponseDTO(loginResponseDTO, HttpStatus.OK.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
