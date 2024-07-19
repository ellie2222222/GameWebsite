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
public class User {
    private int userId;
    private String userName;
    private String userEmail;
    private String userPass;
    private boolean isAdmin;
    private double balance;

    public User() {
    }

    public User(String userEmail) {
        this.userEmail = userEmail;
    }

    public User(int userId, String userEmail, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public User(int userId, String userName, String userEmail, boolean isAdmin, double balance) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.isAdmin = isAdmin;
        this.balance = balance;
    }
    
    public User(int userId, String userName, String userEmail, String userPass, boolean isAdmin) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    @Override
public String toString() {
    return "User{" +
            "userId=" + userId +
            ", userName='" + userName + '\'' +
            ", userEmail='" + userEmail + '\'' +
            ", userPass='" + userPass + '\'' +
            ", isAdmin=" + isAdmin +
            ", balance=" + balance +
            '}';
}
}
