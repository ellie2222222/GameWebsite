/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utils.DBUtils;
import DTO.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Tam
 */
public class gameDao {

    public ArrayList<String> getImagePath(int gameId) {
        String query = "SELECT * FROM [game_images] WHERE [game_id] = ?";
        ArrayList<String> imagePathList = new ArrayList<>();
        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
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

    public Game searchGameById(int gameId) {
        String query = "SELECT * FROM [games] WHERE [game_id] = ?";
        Game game = null;
        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, gameId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                game = new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id")));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    public ArrayList<Game> searchGameByTitle(String title) {
        ArrayList<Game> gameList = new ArrayList<>();
        String query = "SELECT * FROM [games] WHERE [title] LIKE ?";

        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + title + "%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                gameList.add(new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id"))));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return gameList;
    }

    public ArrayList<Game> searchGameByPrice(double min, double max) {
        ArrayList<Game> gameList = new ArrayList<>();
        if (max == 0) {
            max = Integer.MAX_VALUE;
        }
        String query = "SELECT * FROM [games] WHERE [price] >= ? AND [price] <= ?";

        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, min);
            statement.setDouble(2, max);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                gameList.add(new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id"))));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return gameList;
    }

    public ArrayList<Game> searchGameByPriceOrder(String title, String order) {
        ArrayList<Game> gameList = new ArrayList<>();
        String query = "SELECT * FROM [games] WHERE [title] LIKE ?\n"
                + "ORDER BY [price] " + order;

        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + title + "%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                gameList.add(new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id"))));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return gameList;
    }

    public ArrayList<Game> searchGameByDate(String title, String order) {
        ArrayList<Game> gameList = new ArrayList<>();
        String query = "SELECT * FROM [games] WHERE [title] LIKE ?\n"
                + "ORDER BY [release_date] " + order;

        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + title + "%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                gameList.add(new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id"))));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return gameList;
    }

    public boolean addGame(Game game) {
        String query = "INSERT INTO [games] (title, description, price, release_date, platform, publisher) \n"
                + "VALUES(?, ?, ?, ?, ?, ?)";
        boolean response = false;
        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, game.getTitle());
            statement.setString(2, game.getDescription());
            statement.setDouble(3, game.getPrice());
            statement.setString(4, game.getReleaseDate());
            statement.setString(5, game.getPlatform());
            statement.setString(6, game.getPublisher());
            response = statement.executeUpdate() > 0;

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean deleteGame(int gameId) {
        boolean response = false;
        String query = "DELETE FROM [games] WHERE [game_id] = ?";

        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, gameId);
            response = statement.executeUpdate() > 0;

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean updateGame(Game game) {
        boolean response = false;
        String query = "UPDATE [games]\n"
                + "SET [title] = ?, [price] = ?, \n"
                + "[release_date] = ?, [platform] = ?, \n"
                + "[publisher] = ?, [description] = ? \n"
                + "WHERE [game_id] = ?";

        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, game.getTitle());
            statement.setDouble(2, game.getPrice());
            statement.setString(3, game.getReleaseDate());
            statement.setString(4, game.getPlatform());
            statement.setString(5, game.getPublisher());
            statement.setString(6, game.getDescription());
            statement.setInt(7, game.getGameId());
            response = statement.executeUpdate() > 0;

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public ArrayList<Game> getTrendingGameList() {
        ArrayList<Game> trendingList = new ArrayList<>();
        String query = "SELECT * FROM [games]\n"
                + "WHERE [title] = 'League of Legends'"
                + "OR [title] = 'Minecraft'";

        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                trendingList.add(new Game(result.getInt("game_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getString("release_date"),
                        result.getString("platform"),
                        result.getString("publisher"),
                        getGameGenreList(result.getInt("game_id")),
                        getImagePath(result.getInt("game_id"))));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return trendingList;
    }

    public Set<String> getGenreList() {
        Set<String> genreList = new HashSet<>();

        String query = "SELECT * FROM [game_genres]";

        try (Connection connection = DBUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                genreList.add(result.getString("genre"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return genreList;
    }
    
    public ArrayList<String> getGameGenreList(int gameId) {
        ArrayList<String> genreList = new ArrayList<>();

        String query = "SELECT [genre] FROM [game_genres] WHERE [game_id] = ?";

        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
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
}
