package com.district12.backend.controllers;

import com.district12.backend.dtos.AddressRequest;
import com.district12.backend.services.AddressService;
import com.district12.backend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/address")
@RequiredArgsConstructor
@Slf4j
public class AddressControllerV1 {
    private final AddressService addressService;

    @PutMapping("/add")
    public ResponseEntity<Void> addAddress(@RequestBody AddressRequest request) {
        Long userId = SecurityUtils.getOwnerID();
        Long id = addressService.addAddress(userId,
                request.name(),
                request.address(),
                request.city(),
                request.zipCode()
                );

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updateAddress(@RequestBody AddressRequest request, @PathVariable Long addressId) {
        Long id = addressService.updateAddress(addressId, request.name(), request.address(), request.zipCode());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }


}
