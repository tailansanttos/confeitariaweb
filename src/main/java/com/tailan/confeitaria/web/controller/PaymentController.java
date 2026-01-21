package com.tailan.confeitaria.web.controller;

import com.tailan.confeitaria.web.services.PaymentService;
import com.tailan.confeitaria.web.services.dtos.response.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Product")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(description = "Realizar pagamento do pedido")
    @ApiResponse(responseCode = "200", description = "Pagamento realizado com sucesso." )
    @PostMapping("/{orderId}")
    public ResponseEntity<ApiResponseDTO> makePayment(@PathVariable("orderId") Long orderId, Authentication authentication) {
        String username = authentication.name();
        paymentService.makePayment(orderId, username);
        ApiResponseDTO apiResponse = new ApiResponseDTO(null, HttpStatus.ACCEPTED.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
