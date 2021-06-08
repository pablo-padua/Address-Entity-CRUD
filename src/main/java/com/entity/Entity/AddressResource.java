package com.entity.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/address")
public class AddressResource {

    @Autowired
    private AddressService addressService;

    /**
     * Get request to find an AddressEntity by its Id
     * @return the entity's parsed json or null if not found
     **/
    @GetMapping("/find/{id}")
    public ResponseEntity<Optional<AddressEntity>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    /**
    * Post request to create a new address
    **/
    @PostMapping("/create")
    public ResponseEntity<AddressEntity> createNewAddress(){
        return new ResponseEntity<>(addressService.createNewAddress(), HttpStatus.CREATED);
    }

    /**
     * Post request to create a new hardcoded address
     **/
    @PostMapping("/create-hardcoded")
    public ResponseEntity<AddressEntity> createHardcodedAddress(){
        AddressEntity result = addressService.createHardcodedAddress();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Put request to update an address' number
     **/
    @PutMapping("/update-number/{id}")
    public ResponseEntity<Optional<AddressEntity>> updateAddressNumber(@PathVariable Long id, @RequestParam Long number) {
        return ResponseEntity.ok(addressService.updateAddressNumber(id, number));
    }

    /**
     * Delete request to delete an address entity based on its ID
     **/
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        return new ResponseEntity<>(addressService.deleteById(id), HttpStatus.OK);
    }

}
