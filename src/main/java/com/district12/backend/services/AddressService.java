package com.district12.backend.services;

import com.district12.backend.entities.Address;

public interface AddressService {
    Long addAddress(Long userId, String name, String address, String city, Long zipCode);
    Address getAddress(Long addressId);
    void deleteAddress(Long id);
    Long updateAddress(Long addressId, String name, String address, Long zipCode);
}
