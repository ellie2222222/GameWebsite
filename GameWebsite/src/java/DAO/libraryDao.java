/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Cart;
import DTO.Game;
import DTO.Library;
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
public class libraryDao {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet result = null;
    
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

    public ArrayList<Library> getLibrary(int userId) {
        ArrayList<Library> wishList = new ArrayList<>();
        String query = "SELECT * FROM games g JOIN user_library uw ON g.game_id = uw.game_id WHERE user_id = ?";
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
                
                wishList.add(new Library(result.getInt("library_id"),
                        result.getInt("user_id"),
                        game));
            }
            
            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
        }
        return wishList;
    }

    public boolean addToLibrary (int userId, int gameId) {
        String query = "INSERT INTO [user_library] (user_id, game_id) VALUES (?, ?)";

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
        }
        return response;
    }
    
    public boolean checkGameInLibrary(int userId, int gameId) {
        boolean response = false;

        String query = "SELECT * FROM [user_library] WHERE [game_id] = ? AND [user_id] = ?";

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, gameId);
            statement.setInt(2, userId);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                return true;
            }
            
            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return response;
    }
    
    public ArrayList<Library> searchLibraryByTitle(int userId, String title) {
        ArrayList<Library> libraryList = new ArrayList<>();
        String query = "SELECT * FROM games g JOIN user_library uc on g.game_id = uc.game_id\n"
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

                libraryList.add(new Library(result.getInt("library_id"),
                        result.getInt("user_id"),
                        game));
            }

            result.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return libraryList;
    }
}
