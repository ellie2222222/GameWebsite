<%-- 
    Document   : header
    Created on : Feb 6, 2024, 3:32:36 PM
    Author     : Tam
--%>

<%@page import="DTO.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<header class="navbar">
    <div class="header-item home">
        <i class="fa-solid fa-house"></i>
        <c:choose>
            <c:when test="${sessionScope.user.getIsAdmin() != true}">
                <a href="MainController?controller=game&action=homePage">Home</a>
            </c:when>
            <c:otherwise>
                <a href="admin.jsp">Home</a>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="header-item">
        <ul class="nav-items">
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <c:if test="${sessionScope.user.getIsAdmin() != true}">
                        <li class="item">
                            <a href="#"><i class="fa-solid fa-gamepad"></i> &nbsp Games</a>
                        </li>
                        <li class="item">
                            <a href="#"><i class="fa-solid fa-newspaper"></i> &nbsp News</a>
                        </li>
                        <li class="item">
                            <a href="MainController?controller=cart&action=getCart"><i class="fa-solid fa-cart-shopping"></i> &nbsp Cart</a>
                        </li>
                        <li class="item">
                            <a href="MainController?controller=library&action=getLibrary"><i class="fa-solid fa-folder"></i> &nbsp Library</a>
                        </li>
                        <li class="item">
                            <a href="#"><i class="fa-solid fa-phone"></i> &nbsp Support</a>
                        </li>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <li class="item">
                        <a href="#"><i class="fa-solid fa-gamepad"></i> &nbsp Games</a>
                    </li>
                    <li class="item">
                        <a href="#"><i class="fa-solid fa-newspaper"></i> &nbsp News</a>
                    </li>
                    <li class="item">
                        <a href="#"><i class="fa-solid fa-phone"></i> &nbsp Support</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    <c:if test="${sessionScope.user.getIsAdmin() != true}">
        <div class="header-item search-bar">
            <form action="MainController" method="GET">
                <input type="hidden" name="controller" value="game" />
                <input placeholder="Search for games" name="title" value=""/>
                <button type="submit" name="action" value="search"><i class="fa-solid fa-magnifying-glass"></i></button>
            </form>
        </div>
    </c:if>

    <div class="header-item account">
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <div class="account-box my-account">
                    <p><i class="fa-solid fa-user"></i> &nbsp My account</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="account-box">
                    <a href="MainController?controller=user&action=login"><i class="fa-solid fa-right-from-bracket"></i> &nbsp Sign in / Sign up</a>
                </div>
            </c:otherwise>
        </c:choose>
        <div class="account-dropdown">
            <ul>
                <c:if test="${sessionScope.user.getIsAdmin() != true}">
                    <li>
                        <i class="fa-solid fa-address-card"></i>
                        <a href="profile.jsp">Manage profile</a>
                    </li>
                </c:if>

                <li>
                    <i class="fa-solid fa-right-from-bracket"></i>
                    <a href="MainController?controller=user&action=logout">Log out</a>
                </li>
            </ul>
        </div>
    </div>
    <script>
        function accountDropdown() {
            const account = document.querySelector('.account-dropdown');
            account.style.display = (account.style.display === "none" || account.style.display === "") ? "block" : "none";
        }
        let accountBox = document.querySelector(".my-account");
        if (accountBox !== null) {
            accountBox.addEventListener("click", accountDropdown);
        }
    </script>
</header>
