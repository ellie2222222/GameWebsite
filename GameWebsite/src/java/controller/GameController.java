/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.cartDao;
import DAO.gameDao;
import DAO.libraryDao;
import DTO.Game;
import DTO.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tam
 */
public class GameController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String URL = "search.jsp";

        libraryDao ldao = new libraryDao();
        gameDao gdao = new gameDao();
        cartDao cdao = new cartDao();

        ArrayList<Game> gameList = null;
        Set<String> genreList = null;
        String action = request.getParameter("action");
        String add = request.getParameter("add");
        String title = request.getParameter("title");
        String sort = request.getParameter("sort");
        String gameId = request.getParameter("gameId");
        String price = request.getParameter("price");
        String releaseDate = request.getParameter("releaseDate");
        String platform = request.getParameter("platform");
        String publisher = request.getParameter("publisher");
        String description = request.getParameter("description");

        if (add != null) {
            URL = "CartController";
        }

        if (action != null) {
            switch (action) {
                case "homePage":
                    ArrayList<Game> trendingList = gdao.getTrendingGameList();
                    request.setAttribute("trendingList", trendingList);
                    URL = "home.jsp";
                    break;
                case "gamePage":
                    Game game = gdao.searchGameById(Integer.parseInt(gameId));
                    request.setAttribute("game", game);

                    User user = (User) request.getSession().getAttribute("user");
                    int userId = 0;
                    if (user != null) {
                        userId = user.getUserId();
                    }

                    boolean result = cdao.checkExistedGameCart(userId, Integer.parseInt(gameId));
                    request.setAttribute("download", result);
                    URL = "game.jsp";
                    break;
                case "update":
                    boolean checkUpdate = gdao.updateGame(new Game(Integer.parseInt(gameId),
                            title,
                            description,
                            Double.parseDouble(price),
                            releaseDate,
                            platform,
                            publisher));
                    request.setAttribute("updateNotification", checkUpdate);
                    URL = "search.jsp";
                    break;
                case "create":
                    boolean checkAdd = gdao.addGame(new Game(title, description, Double.parseDouble(price),
                            releaseDate, platform, publisher));
                    request.setAttribute("addNotification", checkAdd);
                    URL = "search.jsp";
                    break;
                case "delete":
                    boolean checkDelete = gdao.deleteGame(Integer.parseInt(gameId));
                    request.setAttribute("deleteNotification", checkDelete);
                    URL = "search.jsp";
                    break;
            }
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        gameList = gdao.searchGameByTitle("");
        if (user != null) {
            for (Game game : gameList) {
                boolean bool = ldao.checkGameInLibrary(user.getUserId(), game.getGameId());
                game.setInLibrary(bool);
            }
        }
        genreList = gdao.getGenreList();
        if (sort != null) {
            switch (sort) {
                case "Highest price":
                    gameList = gdao.searchGameByPriceOrder(title, "DESC");
                    if (user != null) {
                        for (Game game : gameList) {
                            boolean bool = ldao.checkGameInLibrary(user.getUserId(), game.getGameId());
                            game.setInLibrary(bool);
                        }
                    }
                    break;
                case "Lowest price":
                    gameList = gdao.searchGameByPriceOrder(title, "ASC");
                    if (user != null) {
                        for (Game game : gameList) {
                            boolean bool = ldao.checkGameInLibrary(user.getUserId(), game.getGameId());
                            game.setInLibrary(bool);
                        }
                    }
                    break;
                case "Newest":
                    gameList = gdao.searchGameByDate(title, "DESC");
                    if (user != null) {
                        for (Game game : gameList) {
                            boolean bool = ldao.checkGameInLibrary(user.getUserId(), game.getGameId());
                            game.setInLibrary(bool);
                        }
                    }
                    break;
                case "Oldest":
                    gameList = gdao.searchGameByDate(title, "ASC");
                    if (user != null) {
                        for (Game game : gameList) {
                            boolean bool = ldao.checkGameInLibrary(user.getUserId(), game.getGameId());
                            game.setInLibrary(bool);
                        }
                    }
                    break;
            }
            request.setAttribute("lastSearch", title);
            request.setAttribute("lastSort", sort);
        }
        request.setAttribute("gameList", gameList);
        request.setAttribute("genreList", genreList);
        request.getRequestDispatcher(URL).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
