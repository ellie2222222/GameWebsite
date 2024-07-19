/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.cartDao;
import DTO.Cart;
import DTO.User;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tam
 */
public class CartController extends HttpServlet {

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

        String URL = "cart.jsp";

        cartDao cdao = new cartDao();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = 0;
        if (user != null) {
            userId = user.getUserId();

            ArrayList<Cart> cartList = null;

            String action = request.getParameter("action");
            if (action != null) {
                String gameId = request.getParameter("gameId");

                switch (action) {
                    case "delete":
                        if (gameId != null) {
                            boolean result = cdao.deleteFromCart(userId, Integer.parseInt(gameId));
                            request.setAttribute("deleteResult", result);
                        }
                        break;
                    case "add":
                        boolean addtocart = false;
                        if (gameId != null) {
                            addtocart = cdao.addToCart(userId, Integer.parseInt(gameId));
                        }
                        request.setAttribute("addtocartNotification", addtocart);
                        request.setAttribute("add", null);

                        URL = "GameController";
                        break;
                    case "checkout":
                        boolean checkCheckout = cdao.checkout(user);
                        user.setBalance(cdao.getUserBalance(userId));
                        session.setAttribute("user", user);
                        request.setAttribute("checkoutNotification", checkCheckout);
                        URL = "cart.jsp";
                        break;
                }
            }
            cartList = cdao.getCart(userId);
            String title = request.getParameter("title");
            String sort = request.getParameter("sort");
            if (sort != null) {
                switch (sort) {
                    case "Highest price":
                        cartList = cdao.searchCartByPriceOrder(userId, title, "DESC");
                        break;
                    case "Lowest price":
                        cartList = cdao.searchCartByPriceOrder(userId, title, "ASC");
                        break;
                    case "Newest":
                        cartList = cdao.searchCartByDate(userId, title, "DESC");
                        break;
                    case "Oldest":
                        cartList = cdao.searchCartByDate(userId, title, "ASC");
                        break;
                    default:
                        cartList = cdao.searchCartByTitle(userId, title);
                        break;
                }
                request.setAttribute("lastSearch", title);
                request.setAttribute("lastSort", sort);
            }
            request.setAttribute("cartList", cartList);
            request.getRequestDispatcher(URL).forward(request, response);
        } else {
            URL = "login.jsp";
            response.sendRedirect(URL);
        }
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
