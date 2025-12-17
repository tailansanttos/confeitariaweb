package com.tailan.confeitaria.web.services.dtos.api;

public record ErrorResponseDTO(String message, String error,  int status, String path) {
}
