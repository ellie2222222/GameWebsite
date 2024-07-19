<%-- 
    Document   : admin
    Created on : Mar 12, 2024, 10:21:40 AM
    Author     : Tam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
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
            <main class="admin-container">
                <div class="manage-container">
                    <a class="manage-user manage-item" href="MainController?controller=admin&action=getUser">
                        <i class="fa-solid fa-users"></i>
                        <p>View users</p>
                    </a>
                    <a class="manage-game manage-item" href="MainController?controller=game&action=search&title=&sort=">
                        <i class="fa-solid fa-gamepad"></i>
                        <p>Manage games</p>
                    </a>
                    <a class="manage-game manage-item" href="MainController?controller=admin&action=getOrderList">
                        <i class="fa-solid fa-cart-shopping"></i>
                        <p>View orders</p>
                    </a>
                </div>
            </main>
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
