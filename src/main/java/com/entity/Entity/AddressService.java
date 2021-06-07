package com.entity.Entity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void createNewAddress(String streetName,
                                 Long number, String neighbourhood,
                                 String city, String state, String country, String zipCode){
        AddressEntity addressEntity = new AddressEntity(streetName, number, neighbourhood, city, state, country, zipCode);
        if (addressEntity.getLatitude() == null){

        }
        if (addressEntity.getLongitude() == null){

        }
        addressRepository.save(addressEntity);
    }

    public AddressEntity createHardcodedAddress(){
        AddressEntity addressEntity = new AddressEntity("Rua Das Alamedas", 11L,"Bairro Sao Joao", "Sao Paulo", "Estado de Sao Paulo", "Brazil", "VS1LC");
        return addressRepository.save(addressEntity);
    }

    public Optional<AddressEntity> getAddressById(Long id){
        return addressRepository.findById(id);
    }

}
