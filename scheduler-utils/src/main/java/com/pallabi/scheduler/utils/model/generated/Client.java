
package com.pallabi.scheduler.utils.model.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import jakarta.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class Client {

    @SerializedName("ContactName")
    @Expose
    private String contactName;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("OpeningHours")
    @Expose
    private List<OpeningHour> openingHours;
    @SerializedName("Address")
    @Expose
    private Address address;
    @SerializedName("ExtraTime")
    @Expose
    private String extraTime;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<OpeningHour> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHour> openingHours) {
        this.openingHours = openingHours;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getExtraTime() {
        return extraTime;
    }

    public void setExtraTime(String extraTime) {
        this.extraTime = extraTime;
    }

}
