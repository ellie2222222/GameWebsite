/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hd
 */
public class MainController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    
    private static final String USER = "User";
    private static final String USER_CONTROLLER = "UserController";
    private static final String CART = "Cart";
    private static final String CART_CONTROLLER = "CartController";
    private static final String GAME = "Game";
    private static final String GAME_CONTROLLER = "GameController";
    private static final String ADMIN = "Admin";
    private static final String ADMIN_CONTROLLER = "AdminController";
    private static final String LIBRARY = "Library";
    private static final String LIBRARY_CONTROLLER = "LibraryController";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String controller = request.getParameter("controller");
            if (USER.equalsIgnoreCase(controller)){
                url = USER_CONTROLLER;
            } else if (CART.equalsIgnoreCase(controller)){
                url = CART_CONTROLLER;
            } else if (GAME.equalsIgnoreCase(controller)){
                url= GAME_CONTROLLER;
            } else if (ADMIN.equalsIgnoreCase(controller)){
                url= ADMIN_CONTROLLER;
            } else if (LIBRARY.equalsIgnoreCase(controller)){
                url= LIBRARY_CONTROLLER;
            } else {
                request.setAttribute("ERROR", "Invalid action");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            request.getRequestDispatcher(url).forward(request, response);
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
