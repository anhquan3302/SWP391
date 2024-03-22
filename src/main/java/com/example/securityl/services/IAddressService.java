package com.example.securityl.services;


import com.example.securityl.Responses.AddressResponse;
import com.example.securityl.dtos.AddressDto;
import com.example.securityl.models.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IAddressService {
    Page<AddressResponse> getAllAddressesByUserId(Long userId, Pageable pageable, String keyword);
    Address createAddress(AddressDto addressDto, Long userId);
    Address updateAddress(Long addressId, AddressDto addressDto);
    void deleteAddress(Long addressId);

    Address getAddressById(Long addressId) throws Exception ;
}
