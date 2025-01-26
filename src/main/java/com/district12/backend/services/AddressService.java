package com.district12.backend.services;

import com.district12.backend.entities.Address;

public interface AddressService {
    Long saveAddress(Address address);
    Address getAddress(Long addressId);
    void deleteAddress(Long id);
}
