package com.tailan.confeitaria.web.utils.mapper;

import com.tailan.confeitaria.web.domain.Address;
import com.tailan.confeitaria.web.domain.User;
import com.tailan.confeitaria.web.services.dtos.request.AddressDTO;
import com.tailan.confeitaria.web.services.dtos.request.UserRegisterDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User dtoToUser(UserRegisterDTO dto) {
        User user = new User();
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setCpf(dto.cpf());
        user.setName(dto.name());
        user.setOrders(null);
        user.setPhone(dto.phone());

        if (dto.addresses() != null) {
            //Converte a lista de AddressDto para uma Lista da entidade Address
            List<Address> addresses = dto.addresses().stream().map(addressDto -> toAddressEntity(addressDto, user)).collect(Collectors.toList());
            user.setAddresses(addresses);
        }
        return user;

    }




    public Address toAddressEntity(AddressDTO addressDTO, User user) {
        Address address = new Address();
        address.setStreet(addressDTO.street());
        address.setComplement(addressDTO.complement());
        address.setNeighborhood(addressDTO.neighborhood());
        address.setCity(addressDTO.city());
        address.setState(addressDTO.state());
        address.setNumber(addressDTO.number());
        address.setZipCode(addressDTO.zipCode());
        address.setClient(user);
        return address;
    }
}
