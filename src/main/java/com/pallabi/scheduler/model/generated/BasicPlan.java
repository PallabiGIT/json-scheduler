
package com.pallabi.scheduler.model.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class BasicPlan {

    @SerializedName("Resource")
    @Expose
    private String resource;
    @SerializedName("ResourceType")
    @Expose
    private String resourceType;
    @SerializedName("Trip")
    @Expose
    private String trip;
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("PickupTerminal")
    @Expose
    private String pickupTerminal;
    @SerializedName("DeliveryTerminal")
    @Expose
    private String deliveryTerminal;
    @SerializedName("SequenceNumber")
    @Expose
    private Integer sequenceNumber;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPickupTerminal() {
        return pickupTerminal;
    }

    public void setPickupTerminal(String pickupTerminal) {
        this.pickupTerminal = pickupTerminal;
    }

    public String getDeliveryTerminal() {
        return deliveryTerminal;
    }

    public void setDeliveryTerminal(String deliveryTerminal) {
        this.deliveryTerminal = deliveryTerminal;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

}
