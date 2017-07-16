package com.thinhlp.cocshopapp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thinhlp on 7/15/17.
 */

public class OrderDto {

    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("employeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("orderDetails")
    @Expose
    private List<OrderDetail> orderDetails = null;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
