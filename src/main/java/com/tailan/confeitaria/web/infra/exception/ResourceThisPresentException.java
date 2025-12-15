package com.tailan.confeitaria.web.infra.exception;

public class ResourceThisPresentException extends RuntimeException {
    public ResourceThisPresentException(String message) {
        super(message);
    }
}
