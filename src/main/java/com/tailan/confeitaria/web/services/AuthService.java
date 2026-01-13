package com.tailan.confeitaria.web.services;

import com.tailan.confeitaria.web.domain.User;
import com.tailan.confeitaria.web.services.dtos.request.UserLoginDTO;
import com.tailan.confeitaria.web.services.dtos.request.UserRegisterDTO;
import com.tailan.confeitaria.web.services.dtos.response.LoginResponseDTO;

public interface AuthService {
    void registerUser(UserRegisterDTO dto);
    LoginResponseDTO login(UserLoginDTO dto);

    User getUser(String email);
}
