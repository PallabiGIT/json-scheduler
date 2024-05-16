
package com.pallabi.scheduler.utils.model.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import jakarta.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class ComplexObject {

    @SerializedName("OrderID")
    @Expose
    private String orderID;
    @SerializedName("OrderLineID")
    @Expose
    private String orderLineID;
    @SerializedName("CustomerId")
    @Expose
    private String customerId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("ServiceCode")
    @Expose
    private String serviceCode;
    @SerializedName("OrderComment")
    @Expose
    private String orderComment;
    @SerializedName("ActivityComment")
    @Expose
    private String activityComment;
    @SerializedName("OrganisationCode")
    @Expose
    private String organisationCode;
    @SerializedName("RestrictOnOrganisationCode")
    @Expose
    private Boolean restrictOnOrganisationCode;
    @SerializedName("IsUnrestricted")
    @Expose
    private Boolean isUnrestricted;
    @SerializedName("Priority")
    @Expose
    private Integer priority;
    @SerializedName("ContainerType")
    @Expose
    private String containerType;
    @SerializedName("NumberOfBins")
    @Expose
    private Integer numberOfBins;
    @SerializedName("OrderedDateFrom")
    @Expose
    private String orderedDateFrom;
    @SerializedName("OrderedDateTo")
    @Expose
    private String orderedDateTo;
    @SerializedName("Client")
    @Expose
    private Client client;
    @SerializedName("OrderInfotexts")
    @Expose
    private List<OrderInfotext> orderInfotexts;
    @SerializedName("ActivityInfotexts")
    @Expose
    private List<ActivityInfotext> activityInfotexts;
    @SerializedName("Trucks")
    @Expose
    private List<Object> trucks;
    @SerializedName("BasicPlan")
    @Expose
    private BasicPlan basicPlan;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderLineID() {
        return orderLineID;
    }

    public void setOrderLineID(String orderLineID) {
        this.orderLineID = orderLineID;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getActivityComment() {
        return activityComment;
    }

    public void setActivityComment(String activityComment) {
        this.activityComment = activityComment;
    }

    public String getOrganisationCode() {
        return organisationCode;
    }

    public void setOrganisationCode(String organisationCode) {
        this.organisationCode = organisationCode;
    }

    public Boolean getRestrictOnOrganisationCode() {
        return restrictOnOrganisationCode;
    }

    public void setRestrictOnOrganisationCode(Boolean restrictOnOrganisationCode) {
        this.restrictOnOrganisationCode = restrictOnOrganisationCode;
    }

    public Boolean getIsUnrestricted() {
        return isUnrestricted;
    }

    public void setIsUnrestricted(Boolean isUnrestricted) {
        this.isUnrestricted = isUnrestricted;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public Integer getNumberOfBins() {
        return numberOfBins;
    }

    public void setNumberOfBins(Integer numberOfBins) {
        this.numberOfBins = numberOfBins;
    }

    public String getOrderedDateFrom() {
        return orderedDateFrom;
    }

    public void setOrderedDateFrom(String orderedDateFrom) {
        this.orderedDateFrom = orderedDateFrom;
    }

    public String getOrderedDateTo() {
        return orderedDateTo;
    }

    public void setOrderedDateTo(String orderedDateTo) {
        this.orderedDateTo = orderedDateTo;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<OrderInfotext> getOrderInfotexts() {
        return orderInfotexts;
    }

    public void setOrderInfotexts(List<OrderInfotext> orderInfotexts) {
        this.orderInfotexts = orderInfotexts;
    }

    public List<ActivityInfotext> getActivityInfotexts() {
        return activityInfotexts;
    }

    public void setActivityInfotexts(List<ActivityInfotext> activityInfotexts) {
        this.activityInfotexts = activityInfotexts;
    }

    public List<Object> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Object> trucks) {
        this.trucks = trucks;
    }

    public BasicPlan getBasicPlan() {
        return basicPlan;
    }

    public void setBasicPlan(BasicPlan basicPlan) {
        this.basicPlan = basicPlan;
    }

}
