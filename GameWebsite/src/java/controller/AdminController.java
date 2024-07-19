/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.orderDao;
import DAO.userDao;
import DTO.Order;
import DTO.User;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tam
 */
public class AdminController extends HttpServlet {

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

        String URL = "admin.jsp";
        String action = request.getParameter("action");
        String lastSearch = request.getParameter("searchTerm");
        userDao udao = new userDao();
        orderDao odao = new orderDao();
        ArrayList<User> userList = udao.getUserList();
        ArrayList<Order> orderList = odao.getOrderList();
        
        switch (action) {
            case "searchOrder":
                String searchTermOrder = request.getParameter("searchTermOrder");
                if (searchTermOrder.equals("")) {
                    searchTermOrder = "0";
                }
                orderList = odao.searchOrder(Integer.parseInt(searchTermOrder));
                request.setAttribute("orderList", orderList);
                URL = "viewOrder.jsp";
                break;
            case "getOrderList":
                request.setAttribute("orderList", orderList);
                URL = "viewOrder.jsp";
                break;
            case "searchUser":
                String searchTermUser = request.getParameter("searchTerm");
                userList = udao.searchUser(searchTermUser);
                request.setAttribute("userList", userList);
                URL = "viewUser.jsp";
                break;
            case "getUser":
                request.setAttribute("userList", userList);
                URL = "viewUser.jsp";
                break;
            case "removeUser":
                String uid = request.getParameter("userId");
                udao.removeUser(Integer.parseInt(uid));
                URL = "viewUser.jsp";
                break;
        }

        request.setAttribute("lastSearch", lastSearch);
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
