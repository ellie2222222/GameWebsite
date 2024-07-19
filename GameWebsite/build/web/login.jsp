<%-- 
    Document   : login
    Created on : Feb 5, 2024, 3:07:04 PM
    Author     : Tam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
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
            <main class="login-main">
                <div class="login-container">
                    <div class="sign-in">
                        <h1>Sign In</h1>
                        <c:set var="cookie" value="${pageContext.request.cookies}"/>
                        <form action="MainController" method="get">
                            <input type="hidden" name="controller" value="user" />
                            <label for="email">Email</label>
                            <input type="text" name="email" required value="${cookie.email.value}" />
                            <label for="pass">Password</label>
                            <input type="password" name="pass" required value="${cookie.pass.value}" />
                            <div class="remember-me">
                                <label for="remember">Remember me</label>
                                <input ${cookie.rememberMeToken == null ? '' : "checked" } id="remember" name="remember" type="checkbox">
                            </div>
                            <button type="submit" name="action" value="signin">Sign in</button>
                            <button type="button" class="no-account" onclick="displayLogin(false)">Don't have an account? Sign up now for free!</button>
                            <c:if test="${requestScope.error != null}">
                                <p style="color: red; align-content: center;">
                                    ${requestScope.error}
                                </p>
                            </c:if>
                        </form>
                    </div>
                    <div class="sign-up" style="display: none;">
                        <h1>Sign Up</h1>
                        <form action="MainController" method="POST">
                            <input type="hidden" name="controller" value="user" />
                            <label for="email">Email</label>
                            <input type="text" name="email" id="email" value="${requestScope.email}" required />
                            <label for="username">User Name</label>
                            <input type="text" name="username" id="username" value="${requestScope.username}" required />
                            <label for="pass">Password</label>
                            <input type="password" name="pass" id="signup-pass" required />
                            <label for="repass">Retype password</label>
                            <input type="password" name="repass" id="signup-repass" required oninput="checkPasswordMatch()"/>
                            <p id="password-match-message" class="password-match-message"></p>
                            <button type="submit" name="action" value="signup">Sign up</button>
                            <button type="button" class="no-account" onclick="displayLogin(true)">Already have an account? Sign in now</button>
                        </form>
                        <p style="color: red; align-content: center;">
                            ${requestScope.error_signup}
                        </p>
                        <c:if test="${requestScope.error_signup != null}">
                            <script>
                                displayLogin(false);
                            </script>
                        </c:if>
                    </div>
                </div>
            </main>
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
