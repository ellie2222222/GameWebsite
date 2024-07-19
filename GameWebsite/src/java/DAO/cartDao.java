/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utils.DBUtils;
import DTO.Cart;
import DTO.Game;
import DTO.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tam
 */
public class cartDao {

    Connection connection = null;
    PreparedStatement statement = null;

    public ArrayList<String> getImagePath(int gameId) {
        String query = "SELECT * FROM [game_images] WHERE [game_id] = ?";
        ArrayList<String> imagePathList = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, gameId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                imagePathList.add(result.getString("image_path"));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return imagePathList;
    }

    public ArrayList<Cart> getCart(int userId) {
        ArrayList<Cart> cartList = new ArrayList<>();
        String query = "SELECT * FROM games g JOIN user_cart uc ON g.game_id = uc.game_id WHERE user_id = ?";
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Game game = new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id")));

                cartList.add(new Cart(result.getInt("cart_id"),
                        result.getInt("user_id"),
                        game));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return cartList;
    }

    public boolean checkExistedGameCart(int userId, int gameId) {
        String query = "SELECT * FROM [user_cart] WHERE user_id = ? AND game_id = ?";

        boolean response = false;
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, gameId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                response = true;
            }
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean deleteFromCart(int userId, int gameId) {
        String query = "DELETE FROM [user_cart] WHERE [user_id] = ? AND [game_id] = ?";

        boolean response = false;
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, gameId);
            response = statement.executeUpdate() > 0;

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean addToCart(int userId, int gameId) {
        boolean checkExist = checkExistedGameCart(userId, gameId);
        if (checkExist) {
            return false;
        }
        String query = "INSERT INTO [user_cart] (user_id, game_id) VALUES (?, ?)";

        boolean response = false;
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, gameId);
            response = statement.executeUpdate() > 0;

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public ArrayList<Cart> searchCartByTitle(int userId, String title) {
        ArrayList<Cart> cartList = new ArrayList<>();
        String query = "SELECT * FROM games g JOIN user_cart uc on g.game_id = uc.game_id\n"
                + "WHERE g.title LIKE ? AND uc.user_id = ?";

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + title + "%");
            statement.setInt(2, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Game game = new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id")));

                cartList.add(new Cart(result.getInt("cart_id"),
                        result.getInt("user_id"),
                        game));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return cartList;
    }

    public ArrayList<Cart> searchCartByPrice(double min, double max) {
        ArrayList<Cart> cartList = new ArrayList<>();
        if (max == 0) {
            max = Integer.MAX_VALUE;
        }
        String query = "SELECT * FROM [games] WHERE [price] >= ? AND [price] <= ?";

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setDouble(1, min);
            statement.setDouble(2, max);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Game game = new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id")));

                cartList.add(new Cart(result.getInt("cart_id"),
                        result.getInt("user_id"),
                        game));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return cartList;
    }

    public ArrayList<Cart> searchCartByPriceOrder(int userId, String title, String order) {
        ArrayList<Cart> cartList = new ArrayList<>();
        String query = "SELECT * FROM games g JOIN user_cart uc ON g.game_id = uc.game_id\n"
                + "WHERE [title] LIKE ? AND [user_id] = ?\n"
                + "ORDER BY [price] " + order;

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + title + "%");
            statement.setInt(2, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Game game = new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id")));

                cartList.add(new Cart(result.getInt("cart_id"),
                        result.getInt("user_id"),
                        game));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return cartList;
    }

    public ArrayList<Cart> searchCartByDate(int userId, String title, String order) {
        ArrayList<Cart> cartList = new ArrayList<>();
        String query = "SELECT * FROM games g JOIN user_cart uc ON g.game_id = uc.game_id\n"
                + "WHERE [title] LIKE ? AND [user_id] = ?\n"
                + "ORDER BY [release_date] " + order;

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + title + "%");
            statement.setInt(2, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Game game = new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id")));

                cartList.add(new Cart(result.getInt("cart_id"),
                        result.getInt("user_id"),
                        game));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return cartList;
    }

    public ArrayList<String> getGameGenreList(int gameId) {
        ArrayList<String> genreList = new ArrayList<>();

        String query = "SELECT [genre] FROM [game_genres] WHERE [game_id] = ?";

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, gameId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                genreList.add(result.getString("genre"));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return genreList;
    }

    public boolean checkout(User user) {
        boolean response = false, update = false, delete = false;
        boolean insertLibrary = false, insertOrder = false, insertOrderDetail = false;

        double amount = 0;
        HashMap<Integer, Double> purchaseList = new HashMap<>();
        try {
            // GET USER CART
            String query = "SELECT * FROM [user_cart] uc JOIN [games] g ON uc.game_id = g.game_id\n"
                    + "WHERE uc.user_id = ?";
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, user.getUserId());
            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                return false;
            }

            // CALCULATE TOTAL PRICE AND RETRIEVE LIST OF GAMES
            amount += result.getDouble("price");
            purchaseList.put(result.getInt("game_id"), result.getDouble("price"));
            while (result.next()) {
                amount += result.getDouble("price");
                purchaseList.put(result.getInt("game_id"), result.getDouble("price"));
            }

            result.close();
            statement.close();
            connection.close();

            // UPDATE USER BALANCE
            if (user.getBalance() > amount) {
                String query2 = "UPDATE [users] SET [balance] = [balance] - ? WHERE [user_id] = ?";
                connection = DBUtils.getConnection();
                statement = connection.prepareStatement(query2);
                statement.setDouble(1, amount);
                statement.setInt(2, user.getUserId());
                update = statement.executeUpdate() > 0;
                statement.close();
                connection.close();
            } else {
                update = false;
                return false;
            }

            // ADDING GAME INTO USER'S LIBRARY
            for (Map.Entry<Integer, Double> entry : purchaseList.entrySet()) {
                String query3 = "INSERT INTO [user_library] (user_id, game_id) VALUES (?, ?)";
                connection = DBUtils.getConnection();
                statement = connection.prepareStatement(query3);
                statement.setInt(1, user.getUserId());
                statement.setInt(2, entry.getKey());
                insertLibrary = statement.executeUpdate() > 0;
                if (insertLibrary == false) {
                    break;
                }

                statement.close();
                connection.close();
            }

            // REMOVING GAME FROM USER'S CART
            String query4 = "DELETE FROM [user_cart] WHERE [user_id] = ?";
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query4);
            statement.setInt(1, user.getUserId());
            delete = statement.executeUpdate() > 0;

            statement.close();
            connection.close();

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            // ADDING PURCHASE TO ORDER
            String query5 = "INSERT INTO [orders] (user_id, order_date, total_amount)\n"
                    + "VALUES(?, ?, ?)";
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query5);
            statement.setInt(1, user.getUserId());
            statement.setString(2, formattedDateTime);
            statement.setDouble(3, amount);
            insertOrder = statement.executeUpdate() > 0;

            statement.close();
            connection.close();

            // GET ORDER ID FROM USER
            int orderId = 0;
            String query6 = "SELECT [order_id] FROM [orders] WHERE user_id = ?";
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query6);
            statement.setInt(1, user.getUserId());
            result = statement.executeQuery();
            while (result.next()) {
                orderId = result.getInt("order_id");
            }

            result.close();
            statement.close();
            connection.close();

            // ADDING ORDER DETAILS
            for (Map.Entry<Integer, Double> entry : purchaseList.entrySet()) {
                String query7 = "INSERT INTO [order_details] (order_id, game_id, price)\n"
                        + "VALUES (?, ?, ?)";
                connection = DBUtils.getConnection();
                statement = connection.prepareStatement(query7);
                statement.setInt(1, orderId);
                statement.setInt(2, user.getUserId());
                statement.setDouble(3, entry.getValue());
                insertOrderDetail = statement.executeUpdate() > 0;

                statement.close();
                connection.close();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if (update == true
                && insertLibrary == true
                && insertOrder == true
                && insertOrderDetail
                && delete == true) {
            return true;
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
}
