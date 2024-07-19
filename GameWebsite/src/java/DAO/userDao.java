/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utils.DBUtils;
import DTO.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Tam
 */
public class userDao {

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet result = null;

    public User checkAccount(String email, String pass) {
        boolean user = checkExistedAccount(email);
        if (!user) {
            return new User("not found");
        } else {
            String query = "SELECT * FROM [users] WHERE email = ? AND password_hash = ?";

            try {
                connection = DBUtils.getConnection();
                statement = connection.prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, hashPassword(pass));
                result = statement.executeQuery();
                while (result.next()) {
                    return new User(result.getInt("user_id"), 
                            result.getString("username"), 
                            result.getString("email"), 
                            result.getBoolean("is_admin"),
                            result.getDouble("balance"));
                }

                result.close();
                statement.close();
                connection.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public User checkAccountEmail(String email) {
        boolean user = checkExistedAccount(email);
        if (!user) {
            return new User("not found");
        } else {
            String query = "SELECT * FROM [users] WHERE email = ?";

            try {
                connection = DBUtils.getConnection();
                statement = connection.prepareStatement(query);
                statement.setString(1, email);
                result = statement.executeQuery();
                while (result.next()) {
                    return new User(result.getInt("user_id"), 
                            result.getString("username"), 
                            result.getString("email"),
                            result.getBoolean("is_admin"),
                            result.getDouble("balance"));                    
                }
                
                result.close();
                statement.close();
                connection.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean checkExistedAccount(String email) {
        String query = "SELECT * FROM [users] WHERE email = ?";

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            result = statement.executeQuery();
            while (result.next()) {
                return true;
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean signUp(String email, String userName, String pass) {
        boolean response = false;
        String query = "INSERT INTO [users] (email, username, password_hash, is_admin)\n"
                + "VALUES (?, ?, ?, 0)";

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, userName);
            statement.setString(3, hashPassword(pass));
            response = statement.executeUpdate() > 0;
            
            statement.close();
            connection.close();
            return response;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void storeToken(int userId, String token) {
        String query = "UPDATE [users] SET remember_me_token = ? WHERE user_id = ?";

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, token);
            statement.setInt(2, userId);
            statement.execute();
            
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateToken(String token) {
        String query = "SELECT * FROM [users] WHERE remember_me_token = ?";

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, token);
            result = statement.executeQuery();
            while (result.next()) {
                return true;
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String hashPassword(String password) {
        try {
            // Choose the hashing algorithm (SHA-512 in this case)
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            // Get the password bytes
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

            // Update the digest with the password bytes
            byte[] hashedBytes = digest.digest(passwordBytes);

            // Convert the hashed bytes to a hexadecimal string
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle exception (e.g., log it, throw a runtime exception, etc.)
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static String generateSecureToken() {
        return UUID.randomUUID().toString();
    }
    
    public boolean deposit(int userId, double amount) {
        String query = "UPDATE [users] SET [balance] = [balance] + ? \n"
                + "WHERE [user_id] = ?";
        boolean response = false;
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            response = statement.executeUpdate() > 0;
            
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public double getUserBalance(int userId) {
        String query = "SELECT [balance] FROM [users] WHERE [user_id] = ?";
        
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                return result.getDouble("balance");
            }
            
            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT [user_id], [email], [username] FROM [users]";
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                userList.add(new User(result.getInt("user_id"), 
                        result.getString("email"), 
                        result.getString("username")));
            }
            
            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    
    public ArrayList<User> searchUser(String searchTerm) {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT [user_id], [email], [username] FROM [users]\n"
                + "WHERE [username] LIKE ? OR [user_id] LIKE ?";
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + searchTerm + "%");
            statement.setString(2, "%" + searchTerm + "%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                userList.add(new User(result.getInt("user_id"), 
                        result.getString("email"), 
                        result.getString("username")));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    
    public boolean removeUser(int userId) {
        boolean checkRemove = false;
        String query = "DELETE FROM [users] WHERE [user_id] = ?";
        
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            checkRemove = statement.executeUpdate() > 0;
            
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkRemove;
    }
}
