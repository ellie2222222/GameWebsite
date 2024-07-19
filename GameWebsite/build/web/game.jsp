<%-- 
    Document   : game
    Created on : Feb 23, 2024, 10:14:23 AM
    Author     : Tam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Game</title>
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
                <div class="return">
                    <button onclick="history.back()">&#10094; &nbsp Go Back</button>
                </div>

                <div class="game-container">
                    <div class="game-box-header">
                        <h2>${requestScope.game.getTitle()}</h2>
                    </div>
                    <div class="game-box-main">
                        <div class="game-image">
                            <img src="${requestScope.game.getImageMain()}"/>
                        </div>
                        <div class="game-overview">
                            <div class="overview-header">
                                <img src="${requestScope.game.getImageHeader()}" />
                            </div>
                            <div class="overview-text">
                                <p id="overview">${requestScope.game.getDescription()}</p>
                                <p>Release date: <span id="date">${requestScope.game.getReleaseDate()}</span></p>
                                <p>Published by: ${requestScope.game.getPublisher()}</p>
                                <p id="genre">Genre(s): ${requestScope.game.getGenreList().toString()}</p>
                            </div>
                        </div>
                    </div>
                    <c:if test="${sessionScope.user.getIsAdmin() == false}">
                        <div class="download-container">
                            <div class="download-box">
                                <c:choose>
                                    <c:when test="${requestScope.download}">
                                        <div class="action-container">
                                            <p>This game is already in your cart</p>
                                            <div class="action-box play-box">
                                                <button type="button">Play now</button>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="action-container">
                                            <c:choose>
                                                <c:when test="${requestScope.game.getPrice() > 0}">
                                                    <p>Buy ${requestScope.game.getTitle()} now</p>
                                                    <div class="action-box">
                                                        <p>$${requestScope.game.getPrice()}</p>
                                                        <form action="CartController" method="POST">
                                                            <input type="hidden" name="gameId" value="${requestScope.game.getGameId()}">
                                                            <c:choose>
                                                                <c:when test="${requestScope.game.isInLibrary() == true}">
                                                                    <button type="button">Already in library</button>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <button type="submit" name="action" value="add">Add to cart</button>
                                                                </c:otherwise>
                                                            </c:choose> 
                                                        </form>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <p>Get ${requestScope.game.getTitle()} now</p>
                                                    <div class="action-box">
                                                        <p>Free</p>
                                                        <form action="CartController" method="POST">
                                                            <input type="hidden" name="action" value="add"/>
                                                            <button type="submit">Download now</button>
                                                        </form>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:if>
                    <div class="game-box-secondary">
                        <div class="game-info">
                            <p id="description">${requestScope.game.getDescription()}</p>
                        </div>
                    </div>
                </div>
            </main>
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
