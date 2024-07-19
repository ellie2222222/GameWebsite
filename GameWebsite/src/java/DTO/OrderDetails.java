/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Tam
 */
public class OrderDetails {
    private int orderDetails;
    private int orderId;
    private int gameId;
    private double price;

    public OrderDetails(int orderDetails, int orderId, int gameId, double price) {
        this.orderDetails = orderDetails;
        this.orderId = orderId;
        this.gameId = gameId;
        this.price = price;
    }

    public int getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(int orderDetails) {
        this.orderDetails = orderDetails;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
