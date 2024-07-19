<%-- 
    Document   : manageUser
    Created on : Mar 12, 2024, 11:24:31 AM
    Author     : Tam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage User</title>
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
                <div class='manage-user-container'>
                    <div class="return">
                        <div class="return-a">
                            <a href="admin.jsp">Go back</a>
                        </div>
                    </div>

                    <div class='manage-user-header'>
                        <h1>VIEW USER</h1>
                    </div>

                    <div class="user-searchbar">
                        <form action="MainController" method="POST">
                            <input type="hidden" name="controller" value="admin" />
                            <input type="text" name="searchTerm" value="${requestScope.lastSearch}" placeholder="Search user by name or id"/>
                            <button type="submit" name="action" value="searchUser">Search</button>
                        </form>
                    </div>

                    <div class="user-search-container">
                        <ul>
                            <li class='user-li-header'>
                                <div>
                                    <p>User ID</p>    
                                </div>
                                <div>
                                    <p>Email</p>    
                                </div>
                                <div>
                                    <p>Name</p>
                                </div>
                            </li>
                            <li class='user-li-element'>
                                <c:forEach var="user" items="${requestScope.userList}">
                                    <div class='item-user'>
                                        <div>
                                            <p>${user.getUserId()}</p>    
                                        </div>
                                        <div>
                                            <p>${user.getUserEmail()}</p>
                                        </div>
                                        <div>
                                            <p>${user.getUserName()}</p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </li>
                        </ul>
                    </div>
                </div>
            </main>
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
