<%-- 
    Document   : profile
    Created on : Mar 7, 2024, 8:18:01 PM
    Author     : Tam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
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
                
                <div class="profile-container">
                    <div class="profile-sidebar">
                        <h2>Account settings</h2>
                        <ul>
                            <li>
                                <p data-value="profile-info"><i class="fa-solid fa-user"></i> &nbsp Personal information</p>
                            </li>
                            <li>
                                <p data-value="banking-info"><i class="fa-solid fa-credit-card"></i> &nbsp Banking information</p>
                            </li>
                            <li>
                                <a href="MainController?controller=cart"><i class="fa-solid fa-cart-shopping"></i> &nbsp Your cart</a>
                            </li>
                            <li>
                                <a href="MainController?controller=library&action=getLibrary"><i class="fa-solid fa-folder"></i> &nbsp Your library</a>
                            </li>
                        </ul>
                    </div>
                    <div class="profile-main profile-info" <c:choose>
                                <c:when test="${balanceUpdated == true}">
                                    style="display: none;"
                                </c:when>
                                <c:otherwise>
                                    style="display: block;"
                                </c:otherwise>
                            </c:choose>>
                        <h2>Your information</h2>
                        <div class="profile-info-content">
                            <label for="email">Email</label>
                            <input type="text" name="email" value="${sessionScope.user.getUserEmail()}" readonly/>
                            <label for="username">Username</label>
                            <input type="text" name="username" value="${sessionScope.user.getUserName()}" readonly/>
                            <!--<button type="button">Change password</button>-->
                        </div>
                    </div>

                    <div class="profile-main banking-info" <c:choose>
                             <c:when test="${balanceUpdated == true}">
                                 style="display: block;"
                             </c:when>
                             <c:otherwise>
                                 style="display: none;"
                             </c:otherwise>
                         </c:choose>>
                        <h2>Your balance</h2>
                        <div class="banking-info-content">
                            <p>Account balance: <fmt:formatNumber value="${sessionScope.user.getBalance()}" type="currency" currencyCode="USD" maxFractionDigits="2" /></p>
                            <form action="MainController" method="POST">
                                <input type="hidden" name="controller" value="user" />
                                <label for="amount">Amount: </label>
                                <input type="number" name="amount" value="0" min="0" />
                                <button type="submit" name="action" value="deposit">Deposit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </main>
            <jsp:include page="footer.jsp"/>
        </div>
        <script>
            // TOGGLE PROFILE SIDEBAR
            const profileMain = document.querySelectorAll(".profile-main");
            function toggleProfileSidebar(event) {
                var dataValue = event.target.dataset.value;

                profileMain.forEach((profile) => {
                    profile.style.display = profile.className.toLowerCase().includes(dataValue.toLowerCase()) ? "flex" : "none"
                });
            }

            const profileSidebars = document.querySelectorAll(".profile-sidebar ul li");
            profileSidebars.forEach((profile) => {
                profile.addEventListener("click", toggleProfileSidebar)
            });
        </script>
    </body>
</html>
