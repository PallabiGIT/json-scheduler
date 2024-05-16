
package com.pallabi.scheduler.utils.model.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Generated;

@Generated("jsonschema2pojo")
public class Address {

    @SerializedName("Coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("StreetName")
    @Expose
    private String streetName;
    @SerializedName("HouseNumber")
    @Expose
    private String houseNumber;
    @SerializedName("HousePostfix")
    @Expose
    private String housePostfix;
    @SerializedName("PostalCode")
    @Expose
    private String postalCode;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Country")
    @Expose
    private String country;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHousePostfix() {
        return housePostfix;
    }

    public void setHousePostfix(String housePostfix) {
        this.housePostfix = housePostfix;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
