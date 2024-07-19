/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.ArrayList;

/**
 *
 * @author Tam
 */
public class Order {
    private int orderId;
    private int userId;
    private String orderDate;
    private double totalAmount;
    private ArrayList orderDetailList;

    public Order(int orderId, int userId, String orderDate, double totalAmount, ArrayList orderDetailList) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderDetailList = orderDetailList;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ArrayList getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(ArrayList orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("User ID: ").append(userId).append("\n");
        sb.append("Order Date: ").append(orderDate).append("\n");
        sb.append("Total Amount: ").append(totalAmount).append("\n");
        sb.append("Order detail list: ").append(orderDetailList);
        
        if (orderDetailList != null && !orderDetailList.isEmpty()) {
            sb.append("Order Details:\n");
            for (Object orderDetail : orderDetailList) {
                sb.append("\t").append(orderDetail).append("\n");
            }
        }
        
        return sb.toString();
    }
}
