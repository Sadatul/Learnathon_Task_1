package com.district12.backend.services;

import com.district12.backend.entities.Address;
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

    @Override
    public Long saveAddress(Address address) {
        return addressRepository.save(address).getId();
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
}
