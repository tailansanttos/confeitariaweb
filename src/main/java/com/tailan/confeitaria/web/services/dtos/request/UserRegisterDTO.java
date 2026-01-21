package com.tailan.confeitaria.web.services.dtos.request;

import com.tailan.confeitaria.web.domain.Address;
import com.tailan.confeitaria.web.domain.Order;
import com.tailan.confeitaria.web.domain.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "DTO pra detalhes de usuario.")
public record UserRegisterDTO(@NotBlank(message = "O noome é obrigatório.")String name,
                              @NotBlank(message = "O e-mail é obrigatório.") @Email(message = "Formato do e-mail inválido.")String email,
                              @NotBlank(message = "O telefone é obrigatório.") String phone,
                              @NotBlank(message = "A senha é obrigatória.")String password,
                              @NotBlank(message = "O cpf é obrigatório.")String cpf,
                              @NotEmpty(message = "O endereço é obrigatório.") List<AddressDTO> addresses,
                              UserRole role) {
}
