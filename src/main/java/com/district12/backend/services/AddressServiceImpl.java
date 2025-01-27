package com.district12.backend.services;

import com.district12.backend.entities.Address;
import com.district12.backend.entities.User;
import com.district12.backend.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    @Override
    public Long addAddress(Long userId, String name, String address, String city, Long zipCode) {
        User user = userService.getUserById(userId);
        Address shippingAddress = new Address(name, address, city, zipCode, user);
        return addressRepository.save(shippingAddress).getId();
    }

    @Override
    public Address getAddress(Long addressId) {
        Optional<Address> byId = addressRepository.findById(addressId);
        if (byId.isPresent()) {
            return byId.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Long updateAddress(Long addressId, String name, String address, Long zipCode) {
        Address existingAddress = getAddress(addressId);
        existingAddress.setName(name);
        existingAddress.setAddress(address);
        existingAddress.setZipCode(zipCode);

        return addressId;
    }
}
