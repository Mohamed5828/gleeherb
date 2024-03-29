package com.mohamed.egHerb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FinalItemDTO {
    private String apartment;
    private String email;
    private String floor;
    private String firstName;
    private String street;
    private String building;
    private String phoneNumber;
    private String city;
    private String country;
    private String lastName;

    public FinalItemDTO() {
    }

    @Override
    public String toString() {
        return "FinalItemDTO{" +
                "apartment='" + apartment + '\'' +
                ", email='" + email + '\'' +
                ", floor='" + floor + '\'' +
                ", firstName='" + firstName + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
