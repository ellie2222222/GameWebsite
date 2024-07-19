<%-- 
    Document   : cart
    Created on : Feb 19, 2024, 2:07:29 PM
    Author     : Tam
--%>

<%@page import="DTO.Cart"%>
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
                    <h1>${sessionScope.user.getUserName()}'s cart</h1>
                </div>

                <div class="search-container">
                    <div class="search-container-main cart-search">
                        <div class="search-bar-main">
                            <form action="MainController" method="GET">
                                <input type="hidden" name="controller" value="cart" />
                                <div class="search-bar search-bar-items">
                                    <input placeholder="Search for games" name="title" value="${requestScope.lastSearch}"/>
                                    <button type="submit" name="action" value="searchCart">Search</button>
                                </div>
                                <div class="sort-filter">
                                    <select name="sort" id="">
                                        <option value="${requestScope.lastSort}">${requestScope.lastSort}</option>
                                        <option value="Highest price">Highest price</option>
                                        <option value="Lowest price">Lowest price</option>
                                    </select>
                                </div>
                            </form>
                        </div>

                        <div class="search-items" <c:if test="${cartList.size() == 0}">style="display: none;"</c:if>>
                                <ul>
                                <c:forEach var="cart" items="${cartList}">
                                    <li>
                                        <div class="item-a">
                                            <a href="GameController?action=gamePage&gameId=${cart.getGame().getGameId()}" class="item-image-a">
                                                <div class="item-image">
                                                    <img src="${cart.getGame().getImageMain()}" alt="">
                                                </div>
                                            </a>
                                            <div class="item-title">
                                                <p>${cart.getGame().getTitle()}</p>
                                            </div>
                                            <div class="item-date" id="date">
                                                <p>${cart.getGame().getReleaseDate()}</p>
                                            </div>
                                            <div class="item-price">
                                                <div class="item-price-box">
                                                    <c:choose>
                                                        <c:when test="${cart.getGame().getPrice() eq 0.0}">
                                                            <p>Free</p>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <p>$${cart.getGame().getPrice()}</p>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            </a>
                                            <div class="delete-form">
                                                <button type="button" onclick="deleteProduct(this, event)"><i class="fa-solid fa-trash"></i></button>
                                            </div>
                                            <div class="item-delete">
                                                <p>Are you sure you want to remove this item from cart?</p>
                                                <div class="item-delete-btn">
                                                    <form action="MainController">
                                                        <input type="hidden" name="controller" value="cart" />
                                                        <input type="hidden" name="gameId" value="${cart.getGame().getGameId()}" />
                                                        <button type="submit" name="action" value="delete">Yes</button>
                                                        <button type="button" onclick="deleteProduct(this, event)">No</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>

                        <div class="cart-checkout">
                            <div class="cart-checkout-description">
                                <p>Total number of items:</p>
                                <div class="cart-checkout-description-value">
                                    <p>${requestScope.cartList.size()}</p>
                                </div>
                            </div>
                            <div class="cart-checkout-description">
                                <p>Total price:</p>
                                <div class="cart-checkout-description-value">
                                    <c:set var="totalPrice" value="0" />
                                    <c:forEach var="cartItem" items="${cartList}">
                                        <c:set var="totalPrice" value="${totalPrice + cartItem.game.price}" />
                                    </c:forEach>
                                    <p><fmt:formatNumber value="${totalPrice}" type="currency" currencyCode="USD" maxFractionDigits="2" /></p>
                                </div>
                            </div>
                            <div class="cart-checkout-btn">
                                <button type="button" onclick="displayCheckout(event)">Proceed to checkout</button>
                            </div>
                        </div>
                        <div class="item-checkout">
                            <c:set var="userBalance" value="${sessionScope.user.getBalance()}"></c:set>
                                <div class="item-checkout-content">
                                    <div>
                                        <p>Your balance: </p>
                                        <p><fmt:formatNumber value="${userBalance}" type="currency" currencyCode="USD" maxFractionDigits="2" /></p>
                                </div>
                                <div>
                                    <p>Total price: </p>
                                    <p><fmt:formatNumber value="${totalPrice}" type="currency" currencyCode="USD" maxFractionDigits="2" /></p>
                                </div>
                                <div>
                                    <p>Remaining balance: </p>
                                    <c:choose>
                                        <c:when test="${userBalance > totalPrice}">
                                            <p><fmt:formatNumber value="${userBalance - totalPrice}" type="currency" currencyCode="USD" maxFractionDigits="2" /> </p>
                                        </c:when>
                                        <c:otherwise>
                                            <p>Your balance is insufficient, cannot proceed</p>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                            <div class="item-checkout-btn">
                                <form action="MainController">
                                    <input type="hidden" name="controller" value="cart" />
                                    <button type="submit" name="action" value="checkout">Proceed</button>
                                    <button type="button" onclick="displayCheckout(event)">Cancel</button>
                                </form> 
                            </div>
                        </div>
                    </div>
                    <div class="noti">
                        <h3 class="msg">
                            <c:if test="${requestScope.checkoutNotification == true}">
                                <script>
                                    document.addEventListener("DOMContentLoaded", function () {
                                        showNotification("Checkout successfully");
                                    });
                                </script>
                            </c:if>
                            <c:if test="${requestScope.checkoutNotification == false}">
                                <script>
                                    document.addEventListener("DOMContentLoaded", function () {
                                        showNotification("Checkout fail");
                                    });
                                </script>
                            </c:if>
                        </h3>
                    </div>
                    <div class="backdrop"></div>
            </main>
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
