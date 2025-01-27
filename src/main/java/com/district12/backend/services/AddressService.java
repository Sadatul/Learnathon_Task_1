package com.district12.backend.services;

import com.district12.backend.entities.Address;

public interface AddressService {
    Long addAddress(Address address);
    Address getAddress(Long addressId);
    void deleteAddress(Long id);
    Long updateAddress(Long addressId, String name, String address, Long zipCode);
}
