package com.tailan.confeitaria.web.services.impl;

import com.tailan.confeitaria.web.domain.User;
import com.tailan.confeitaria.web.domain.enums.UserRole;
import com.tailan.confeitaria.web.infra.exception.ResourceThisPresentException;
import com.tailan.confeitaria.web.repository.UserRepository;
import com.tailan.confeitaria.web.security.TokenService;
import com.tailan.confeitaria.web.services.AuthService;
import com.tailan.confeitaria.web.services.dtos.request.UserLoginDTO;
import com.tailan.confeitaria.web.services.dtos.request.UserRegisterDTO;
import com.tailan.confeitaria.web.services.dtos.response.LoginResponseDTO;
import com.tailan.confeitaria.web.utils.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserMapper userMapper, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Override
    public void registerUser(UserRegisterDTO dto) {
      userExists(dto.email());
      User newUser = userMapper.dtoToUser(dto);
      String hashedPassword = passwordEncoder.encode(newUser.getPassword()); //Codifica  a senha do USER com o algoritimo Bcrypt
        newUser.setPassword(hashedPassword);

        if (dto.role() == null){
          newUser.setRole(UserRole.USER);
      } else newUser.setRole(dto.role());

    userRepository.save(newUser);


    }

    @Override
    public LoginResponseDTO login(UserLoginDTO dto) {
        //Cria um Objeto de AUTENTICAÇÃO com o email e senha do user
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        //Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //Obtem o objeto UserDetails do usuário autenticado
        User user = (User) authentication.getPrincipal();

        String token = tokenService.generateToken(user);
        return new LoginResponseDTO(token, user.getEmail());
    }

    private void userExists(String email) {
        User user = (User) userRepository.findByEmail(email);
        if (user != null) {
            throw new ResourceThisPresentException("User with this email already exists!");
        }

    }
}
