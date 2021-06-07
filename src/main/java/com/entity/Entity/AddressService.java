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

    public void getRandomAddressBasedOnCEP(AddressEntity addressEntity){
        URL url = new URL("http://geradornv.com.br/wp-json/api/cep/random-by-states");
        HttpURLConnection httpURLConnection = url.openConnection();
        url.setRequestMethod("GET");
        int responseCode = url.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            HttpResponse<JsonNode> response = Unirest.get(url)
                    .queryString("state","SP")
                    .asString();
            //Results in random CEP searches based on Sao Paulo state
        }
        url.disconnect();
    }

    public void createNewAddress(){
        AddressEntity addressEntity = new AddressEntity();
        getRandomAddressBasedOnCEP(addressEntity);
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
