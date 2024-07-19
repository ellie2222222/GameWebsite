/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.gameDao;
import DAO.userDao;
import DTO.Game;
import DTO.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tam
 */
public class UserController extends HttpServlet {

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
        String URL = "login.jsp";
        
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        String repass = request.getParameter("repass");
        
        userDao dao = new userDao();
        boolean redirect = false;

        HttpSession session = request.getSession();
        if (action != null) {
            switch (action) {
                case "login":
                    URL = "login.jsp";
                    break;
                case "signup":
                    request.setAttribute("email", email);
                    if (!email.endsWith("@gmail.com")) {
                        URL = "UserController?action=login";
                        request.setAttribute("error_signup", "Invalid email format (should ends with @gmail.com)");
                        break;
                    }
                    request.setAttribute("username", username);
                    if (pass == null || !(pass.equals(repass))) {
                        request.setAttribute("error_signup", "Password do not match! Please retry");
                    } else {
                        boolean isExist = dao.checkExistedAccount(email);
                        if (!isExist) {
                            boolean result = dao.signUp(email, username, pass);
                            if (result) {
                                request.setAttribute("error_signup", "Sign up successfully");
                            } else {
                                request.setAttribute("error_signup", "Sign up fail");
                            }
                        } else {
                            request.setAttribute("error_signup", "Email already exists!");
                        }
                    }
                    URL = "UserController?action=login";
                    break;
                case "signin":
                    User user = dao.checkAccount(email, pass);
                    Cookie[] cookies = request.getCookies();
                    
                    gameDao gdao = new gameDao();
                    ArrayList<Game> trendingList = gdao.getTrendingGameList();
                    request.setAttribute("trendingList", trendingList);
                    ArrayList<Game> gameList = gdao.searchGameByTitle("");
                    request.setAttribute("gameList", gameList);
                    
                    String remember = request.getParameter("remember");
                    if (remember == null || remember.equals("")) {
                        Cookie tokenCookie = new Cookie("rememberMeToken", "");
                        Cookie emailCookie = new Cookie("email", "");
                        Cookie passCookie = new Cookie("pass", "");
                        tokenCookie.setMaxAge(0);
                        emailCookie.setMaxAge(0);
                        passCookie.setMaxAge(0);
                        response.addCookie(tokenCookie);
                        response.addCookie(emailCookie);
                        response.addCookie(passCookie);
                    }

                    boolean flagRememberMe = false;
                    if (user == null) {
                        if (cookies != null) {
                            for (Cookie cookie : cookies) {
                                if (cookie.getName().equals("rememberMeToken")) {
                                    if (dao.validateToken(cookie.getValue())) {
                                        User u = dao.checkAccountEmail(email);
                                        session.setAttribute("user", u);
                                        if (u.getIsAdmin()) {
                                            URL = "admin.jsp";
                                        } else {
                                            URL = "home.jsp";
                                        }
                                        flagRememberMe = true;
                                    }
                                }
                            }
                        }
                        if (flagRememberMe) {
                            break;
                        }
                    }

                    if (user == null) {
                        request.setAttribute("error", "Incorrect password");
                        URL = "UserController?action=login";
                    } else if (user.getUserEmail().equals("not found")) {
                        request.setAttribute("error", "Email does not exist");
                        URL = "UserController?action=login";
                    } else {
                        session.setAttribute("user", user);
                        boolean flag = false;
                        if (remember != null && !remember.equals("")) {
                            if (cookies != null) {
                                for (Cookie cookie : cookies) {
                                    if (cookie.getName().equals("rememberMeToken")) {
                                        flag = true;
                                    }
                                }
                            }
                            if (flag == false) {
                                String rememberMeToken = userDao.generateSecureToken();
                                dao.storeToken(user.getUserId(), rememberMeToken);

                                // Set the token in a cookie
                                Cookie tokenCookie = new Cookie("rememberMeToken", rememberMeToken);
                                Cookie emailCookie = new Cookie("email", email);
                                Cookie passCookie = new Cookie("pass", "password");
                                tokenCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
                                emailCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
                                passCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
                                response.addCookie(tokenCookie);
                                response.addCookie(emailCookie);
                                response.addCookie(passCookie);
                            }
                        } else {
                            Cookie tokenCookie = new Cookie("rememberMeToken", "");
                            Cookie emailCookie = new Cookie("email", "");
                            Cookie passCookie = new Cookie("pass", "");
                            tokenCookie.setMaxAge(0);
                            emailCookie.setMaxAge(0);
                            passCookie.setMaxAge(0);
                            response.addCookie(tokenCookie);
                            response.addCookie(emailCookie);
                            response.addCookie(passCookie);
                        }
                        if (user.getIsAdmin()) {
                            URL = "admin.jsp";
                        } else {
                            URL = "home.jsp";
                        }
                    }
                    break;
                case "logout":
                    session.removeAttribute("user");
                    session.invalidate();
                    redirect = true;
                    URL = "login.jsp";
                    break;
                case "deposit":
                    User u = (User) session.getAttribute("user");
                    int uid = u.getUserId();
                    String amount = request.getParameter("amount");
                    dao.deposit(uid, Double.parseDouble(amount));
                    u.setBalance(dao.getUserBalance(uid));
                    session.setAttribute("user", u);
                    request.setAttribute("balanceUpdated", true);
                    
                    URL = "profile.jsp";
                    break;
                default:
                    URL = "login.jsp";
                    break;
            }
            if (redirect) {
                response.sendRedirect(URL);
            } else {
                request.getRequestDispatcher(URL).forward(request, response);
            }
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
