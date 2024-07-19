<%-- 
    Document   : library
    Created on : Mar 15, 2024, 1:47:34 PM
    Author     : Tam
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <!--JAVASCRIPT-->
        <script src="assets/js/script.js"></script>
        <!--CSS-->
        <link rel="stylesheet" href="assets/css/style.css" />
        <!--ICON-->
        <script
            src="https://kit.fontawesome.com/381dcc53b3.js"
            crossorigin="anonymous"
        ></script>
        <!--GOOGLE FONT-->
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap"
            rel="stylesheet"
            />
    </head>
    <body>
        <div class="container">
            <jsp:include page="header.jsp"/>
            <main>
                <div class="return return-search">
                    <button onclick="history.back()">&#10094; &nbsp Go Back</button>
                </div>
                
                <div class="search-header">
                    <h1>${sessionScope.user.getUserName()}'s library</h1>
                </div>
                
                <div class="search-container">
                    <div class="search-container-main cart-search">
                        <div class="search-bar-main">
                            <form action="MainController" method="GET">
                                <input type="hidden" name="controller" value="library" />
                                <div class="search-bar search-bar-items">
                                    <input placeholder="Search for games" name="title" value="${requestScope.lastSearch}"/>
                                    <button type="submit" name="action" value="searchLibrary">Search</button>
                                </div>
                            </form>
                        </div>

                        <div class="search-items" <c:if test="${libraryList.size() == 0}">style="display: none;"</c:if>>
                                <ul>
                                <c:forEach var="library" items="${libraryList}">
                                    <li>
                                        <div class="item-a">
                                            <a href="GameController?action=gamePage&gameId=${library.getGame().getGameId()}" class="item-image-a">
                                                <div class="item-image">
                                                    <img src="${library.getGame().getImageMain()}" alt="">
                                                </div>
                                            </a>
                                            <div class="item-title">
                                                <p>${library.getGame().getTitle()}</p>
                                            </div>
                                            <div class="item-date" id="date">
                                                <p>${library.getGame().getReleaseDate()}</p>
                                            </div>
                                            <div class="item-buttons item-buttons-library">
                                                <button type="button">Play now</button>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
            </main>
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
