<%-- 
    Document   : error.jsp
    Created on : Mar 14, 2024, 3:52:11 PM
    Author     : Tam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ERROR</title>
        <!--JAVASCRIPT-->
        <script src="assets/js/script.js"></script>
        <!--CSS-->
        <link rel="stylesheet" href="assets/css/slideshow.css" />
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
        <title>Home Page</title>
    </head>
    <body>
        <div class="container">
            <jsp:include page="header.jsp"/>
            <main class="error">
                <div class="error-container">
                    <h1>UNKNOWN ERROR OCCURRED</h1>
                </div>
            </main>
            <jsp:include page="footer.jsp"/>
    </body>
</html>
