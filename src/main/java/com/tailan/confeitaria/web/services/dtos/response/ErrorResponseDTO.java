package com.tailan.confeitaria.web.services.dtos.response;

public record ErrorResponseDTO(String message, String error,  int status, String path) {
}
