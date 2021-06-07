package com.entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ADDRESS")
@Getter
@Setter
@NoArgsConstructor
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "STREET_NAME")
    private String streetName;

    @NotNull
    @Column(name = "NUMBER")
    private Long number;

    @NotNull
    @Column(name = "COMPLEMENT")
    private String complement;

    @NotNull
    @Column(name = "NEIGHBOURHOOD")
    private String neighbourhood;

    @NotNull
    @Column(name = "CITY")
    private String city;

    @NotNull
    @Column(name = "STATE")
    private String state;

    @NotNull
    @Column(name = "COUNTRY")
    private String country;

    @NotNull
    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    //Constructor ignoring nullable fields
    public AddressEntity(String streetName,
                         Long number, String neighbourhood,
                         String city, String state, String country,
                         String zipCode) {
        this.streetName = streetName;
        this.number = number;
        this.neighbourhood = neighbourhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }
}