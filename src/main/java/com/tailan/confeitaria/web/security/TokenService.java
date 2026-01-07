package com.tailan.confeitaria.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.tailan.confeitaria.web.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    //RESPONSAVEL POR GERAR E VALIDAR TOKENS

    @Value("${api.security.token.secret}")
    private String secret;



    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("confeitaria-auth-api") //quem gerou
                    .withSubject(user.getEmail()) //quem é o dono
                    .withExpiresAt(genExpirationDate()) //Quando vence
                    .sign(algorithm); //assina com o algoritimo
            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating token", exception);
        }
    }


    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("confeitaria-auth-api")
                    .build()
                    .verify(token) //Se o token for inválido, lança exceção
                    .getSubject(); //retorna o e-mail do usuario
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Error while validating token", exception);
        }
    }


    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
