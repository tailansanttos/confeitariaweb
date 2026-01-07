package com.tailan.confeitaria.web.services.dtos.request;

import com.tailan.confeitaria.web.domain.User;

public record AddressDTO(String zipCode, String street, String complement, String neighborhood, String city, String state, String number, User client) {
}
