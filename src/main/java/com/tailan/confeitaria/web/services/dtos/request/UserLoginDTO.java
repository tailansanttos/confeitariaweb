package com.tailan.confeitaria.web.services.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(@NotBlank(message = "O e-mail é obrigatórioo.") @Email(message = "Formato do e-mail inválido.") String email,
                           @NotBlank(message = "A senha é obrigatórioo.") String password) {
}
