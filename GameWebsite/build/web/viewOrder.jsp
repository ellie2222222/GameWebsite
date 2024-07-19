<%-- 
    Document   : order
    Created on : Mar 14, 2024, 8:08:24 PM
    Author     : Tam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View order</title>
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
                <div class='manage-order-container'>
                    <div class="return">
                        <div class="return-a">
                            <a href="admin.jsp">Go back</a>
                        </div>
                    </div>

                    <div class='manage-order-header'>
                        <h1>VIEW ORDER</h1>
                    </div>

                    <div class="order-searchbar">
                        <form action="MainController" method="POST">
                            <input type="hidden" name="controller" value="admin" />
                            <input type="number" name="searchTermOrder" placeholder="Search order by order id or user id"/>
                            <button type="submit" name="action" value="searchOrder">Search</button>
                        </form>
                    </div>

                    <div class="order-search-container">
                        <ul>
                            <li class='order-li-header'>
                                <div>
                                    <p>Order ID</p>    
                                </div>
                                <div>
                                    <p>User ID</p>    
                                </div>
                                <div>
                                    <p>Order date</p>
                                </div>
                                <div>
                                    <p>Total amount</p>
                                </div>
                            </li>
                            <li class='order-li-element'>
                                <c:forEach var="order" items="${requestScope.orderList}">
                                    <div class="item-order-wrapper">
                                        <div class='item-order'>
                                            <div>
                                                <p>${order.getOrderId()}</p>
                                            </div>
                                            <div>
                                                <p>${order.getUserId()}</p>
                                            </div>
                                            <div>
                                                <p>${order.getOrderDate()}</p>
                                            </div>
                                            <div>
                                                <p>${order.getTotalAmount()}</p>
                                            </div>
                                        </div>
                                        <ul>
                                            <li class='order-detail-li-header'>
                                                <div>
                                                    <p>Order Detail ID</p>    
                                                </div>
                                                <div>
                                                    <p>Game ID</p>
                                                </div>
                                                <div>
                                                    <p>Price</p>
                                                </div>
                                            </li>
                                            <li>
                                                <c:forEach var="orderDetail" items="${order.getOrderDetailList()}">
                                                    <div class="item-order-detail">
                                                        <div>
                                                            <p>${orderDetail.getOrderDetails()}</p>
                                                        </div>
                                                        <div>
                                                            <p>${orderDetail.getGameId()}</p>
                                                        </div>
                                                        <div>
                                                            <p>${orderDetail.getPrice()}</p>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </li>
                                        </ul>
                                    </div>
                                </c:forEach>
                            </li>
                        </ul>
                    </div>
                    <div class="backdrop"></div>
                </div>
            </main>
            <jsp:include page="footer.jsp"/>
    </body>
</html>
