package com.entity.Entity;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.Optional;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public void getRandomAddressBasedOnCEP(AddressEntity addressEntity) {
        try {
            MaskFormatter cepMask = new MaskFormatter("#####-###");
            cepMask.setValueContainsLiteralCharacters(false);
            //Results in random CEP searches based in Sao Paulo state
            HttpResponse<JsonNode> response = Unirest.get("http://geradornv.com.br/wp-json/api/cep/random-by-states")
                    .queryString("state", "SP")
                    .asJson();
            JSONObject jsonObject = response.getBody().getObject();
            addressEntity.setStreetName(jsonObject.getString("type") + " " + jsonObject.getString("street"));
            addressEntity.setNumber(0L); //This api doesn't support address number :(
            addressEntity.setNeighbourhood(jsonObject.getString("neighborhood"));
            addressEntity.setCity(jsonObject.getString("city"));
            addressEntity.setState(jsonObject.getString("state"));
            addressEntity.setZipCode(cepMask.valueToString(jsonObject.getString("cep")));
            addressEntity.setCountry("Brazil");
        } catch (ParseException e) {
            //Error masking zipCode(CEP) to the #####-### pattern
        }
    }

    public void getLatLongGoogleApi(AddressEntity addressEntity) {
        //Format string for the maps.google api and search for address' latitude and longitude
        String address = addressEntity.getStreetName() + " " +
                addressEntity.getNeighbourhood() + " " +
                addressEntity.getCity() + " " +
                addressEntity.getState() + " " +
                addressEntity.getCountry();
        String key = "AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw";
        HttpResponse<JsonNode> response = Unirest.get("https://maps.googleapis.com/maps/api/geocode/json")
                .queryString("address", address)
                .queryString("key", key)
                .asJson();
        JSONArray jsonArray = response.getBody().getObject().getJSONArray("results");
        String lat = (jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
        String lng = (jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));
        addressEntity.setLatitude(Double.parseDouble(lat));
        addressEntity.setLongitude(Double.parseDouble(lng));
    }

    public AddressEntity createNewAddress() {
        AddressEntity addressEntity = new AddressEntity();
        getRandomAddressBasedOnCEP(addressEntity);
        if (addressEntity.getLatitude() == null || addressEntity.getLongitude() == null) {
            getLatLongGoogleApi(addressEntity);
        }
        return addressRepository.save(addressEntity);
    }

    public AddressEntity createHardcodedAddress() {
        AddressEntity addressEntity = new AddressEntity("Rua Das Alamedas", 11L, "Bairro Sao Joao", "Sao Paulo", "Estado de Sao Paulo", "Brazil", "VS1LC");
        return addressRepository.save(addressEntity);
    }

    public Optional<AddressEntity> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public Optional<AddressEntity> updateAddressNumber(Long id, Long number) {
        Optional<AddressEntity> addressEntity = addressRepository.findById(id);
        addressEntity.ifPresent(entity -> entity.setNumber(number));
        return addressEntity;
    }

    public String deleteById(Long id) {
        addressRepository.deleteById(id);
        return String.format("Address with id: %s was deleted", id);
    }

}
