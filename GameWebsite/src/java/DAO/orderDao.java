/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Order;
import DTO.OrderDetails;
import Utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tam
 */
public class orderDao {

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet result = null;
    
    public ArrayList getOrderDetailList(int orderId) {
        if (orderId == 0) {
            return null;
        }
        ArrayList orderDetailList = new ArrayList<>();
        String query = "SELECT * FROM [order_details] WHERE [order_id] = ?";
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            result = statement.executeQuery();
            while (result.next()) {
               orderDetailList.add(new OrderDetails(result.getInt("order_detail_id"),
                                        result.getInt("order_id"),
                                        result.getInt("game_id"),
                                        result.getDouble("price")));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
        }
        return orderDetailList;
    }

    public ArrayList<Order> getOrderList() {
        ArrayList<Order> orderList = new ArrayList<>();
        String query = "SELECT * FROM [orders]";
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
               orderList.add(new Order(result.getInt("order_id"),
                                        result.getInt("user_id"),
                                        result.getString("order_date"),
                                        result.getDouble("total_amount"),
                                        getOrderDetailList(result.getInt("order_id"))));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
        }
        return orderList;
    }
    
    public ArrayList<Order> searchOrder(int searchTerm) {
        ArrayList<Order> orderList = new ArrayList<>();
        String query = "SELECT * FROM [orders]\n"
                + "WHERE [order_id] = ? OR [user_id] = ?";
        if (searchTerm == 0) {
            query = "SELECT * FROM [orders]";
        }
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            if (searchTerm != 0) {
                statement.setInt(1, searchTerm);
                statement.setInt(2, searchTerm);
            }
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                orderList.add(new Order(result.getInt("order_id"),
                                        result.getInt("user_id"),
                                        result.getString("order_date"),
                                        result.getDouble("total_amount"),
                                        getOrderDetailList(result.getInt("order_id"))));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
