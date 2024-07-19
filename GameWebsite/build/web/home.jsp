<%-- 
    Document   : home
    Created on : Feb 27, 2024, 7:38:58 PM
    Author     : Tam
--%>

<%@page import="DAO.gameDao"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DTO.Game"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <%
            gameDao gdao = new gameDao();
            ArrayList<Game> trendingList = gdao.getTrendingGameList();
            request.setAttribute("trendingList", trendingList);
            Set<String> genreList = gdao.getGenreList();
            request.setAttribute("genreList", genreList);
        %>
        <div class="container">
            <jsp:include page="header.jsp"/>
            <main class="main-page">
                <div class="main-slideshow-container">  
                    <c:forEach var="game" items="${requestScope.trendingList}">
                        <div class="main-slideshow">
                            <img src="${game.getImageSlideshow()}" style="width:100%">
                            <div class="main-slideshow-description">
                                <h1>${game.getTitle()}</h1>
                                <p>${game.getShortDescription()}</p>
                                <form action="MainController">
                                    <input type="hidden" name="controller" value="game" />
                                    <input type="hidden" name="gameId" value ="${game.getGameId()}" />
                                    <button type="submit" name="action" value="gamePage">View more</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                    <!-- Next and previous buttons -->
                    <a class="main-prev prev">&#10094;</a>
                    <a class="main-next next">&#10095;</a>
                    <div class="backdrop slideshow-backdrop"></div>
                </div>
                <div class="welcome-container">
                    <h1>The ultimate destination for playing, discussing, and creating games.</h1>
                </div>
                <div class="genre-slideshow-container">
                    <div class="genre-slideshow-wrapper">
                        <%
                            int count = 0;
                            int prevCount = 0;
                            genreList = (Set<String>) request.getAttribute("genreList");
                            String[] arr = (String[]) genreList.toArray(new String[genreList.size()]);
                            for (int i = 0; i < arr.length; i++) {
                                if (count % 4 == 0 || count == 0) {
                        %>
                        <div class="genre-slideshow">
                            <%                            }
                            %>
                            <div class="genre-slideshow-box">
                                <img src="assets/images/Video-Game-Picture1.webp">
                                <h3><%= arr[i]%></h3>
                            </div>
                            <%
                                if ((prevCount + 3) == count || count + 1 == arr.length) {
                                    prevCount = count + 1;
                            %>
                        </div>
                        <%
                                }
                                count++;
                            }%>
                        <a class="genre-prev prev">&#10094;</a>
                        <a class="genre-next next">&#10095;</a>
                    </div>
                </div>
                        <div class="search-header">
                    <h1>All products</h1>
                </div>
                
                <div class="search-container">
                    <div class="search-container-main">
                        <div class="search-bar-main">
                            <form action="MainController" method="GET">
                                <input type="hidden" name="controller" value="game" />
                                <div class="search-bar search-bar-items">
                                    <input placeholder="Search for games" name="title" value="${requestScope.lastSearch}"/>
                                    <button type="submit" name="action" value="search">Search</button>
                                    <c:if test="${sessionScope.user.getIsAdmin() == true}">
                                        <button type="button" onclick="addProduct(event)">Add new game</button>
                                    </c:if>
                                </div>
                                <div class="sort-filter">
                                    <select name="sort" id="">
                                        <option value="${requestScope.lastSort}">${requestScope.lastSort}</option>
                                        <option value="Relevance">Relevance</option>
                                        <option value="Top rated">Top rated</option>
                                        <option value="Most played">Most played</option>
                                        <option value="Most popular">Most popular</option>
                                        <option value="Highest price">Highest price</option>
                                        <option value="Lowest price">Lowest price</option>
                                        <option value="Newest">Newest</option>
                                        <option value="Oldest">Oldest</option>
                                    </select>
                                </div>
                            </form>
                        </div>
                        <div class="search-items-result">
                            <p>${gameList.size() != null ? gameList.size() : 0} result(s) match your search</p>
                        </div>
                        <div class="search-items">
                            <ul>
                                <c:forEach var="game" items="${gameList}">
                                    <li>
                                        <div class="item-a">
                                            <a href="MainController?controller=game&action=gamePage&gameId=${game.getGameId()}" class="item-image-a">
                                                <div class="item-image">
                                                    <img src="${game.getImageMain()}" alt="">
                                                </div>
                                            </a>
                                            <div class="item-info">
                                                <div class="item-title">
                                                    <p>${game.getTitle()}</p>
                                                </div>
                                                <div class="item-genre" style="display: none;">
                                                    <p>${game.getGenreList()}</p>
                                                </div>
                                                <div class="item-date" id="date">
                                                    <p>${game.getReleaseDate()}</p>
                                                </div>
                                                <div class="item-price">
                                                    <div class="item-price-box">
                                                        <c:choose>
                                                            <c:when test="${game.getPrice() eq 0.0}">
                                                                <p>Free</p>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <p>$${game.getPrice()}</p>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <c:choose>
                                                        <c:when test="${sessionScope.user.getIsAdmin() == true}">
                                                            <div class="item-buttons">
                                                                <button type="button" onclick="displayItemEdit(this)">Edit</button>
                                                                <button type="button" onclick="deleteProduct(this, event)">Delete</button>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form action="MainController" method="GET">
                                                                <input type="hidden" name="controller" value="cart" />
                                                                <div class="item-buttons">
                                                                    <input type="hidden" name="gameId" value="${game.getGameId()}" />
                                                                    <c:choose>
                                                                        <c:when test="${game.isInLibrary() == true}">
                                                                            <button type="button">Already in library</button>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <button type="submit" name="action" value="add">Add to cart</button>
                                                                        </c:otherwise>
                                                                    </c:choose>  
                                                                </div>
                                                            </form>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                        <form action="MainController" method="POST">
                                            <input type="hidden" name="controller" value="game" />
                                            <div class="item-edit">
                                                <input type="hidden" name="gameId" value="${game.getGameId()}"/>
                                                <label for="title">Title</label>
                                                <input type="text" name="title" value="${game.getTitle()}"/>
                                                <label for="price">Price</label>
                                                <input type="text" name="price" value="${game.getPrice()}"/>
                                                <label for="releaseDate">Release date (format)</label>
                                                <input type="date" name="releaseDate" value="${game.getReleaseDate()}"/>
                                                <label for="platform">Platform</label>
                                                <input type="text" name="platform" value="${game.getPlatform()}"/>
                                                <label for="publisher">Publisher</label>
                                                <input type="text" name="publisher" value="${game.getPublisher()}"/>
                                                <label for="description">Description:</label>
                                                <textarea name="description" rows="10">${game.getDescription()}</textarea>
                                                <div class="item-edit-btn">
                                                    <button type="submit" name="action" value="update">Submit</button>
                                                    <button type="button" onclick="displayItemEdit(this)">Cancel</button>
                                                </div>
                                            </div>
                                        </form>
                                        <div class="item-delete">
                                            <p>Are you sure you want to delete this?</p>
                                            <div class="item-delete-btn">
                                                <form action="MainController">
                                                    <input type="hidden" name="controller" value="game" />
                                                    <input type="hidden" name="gameId" value="${game.getGameId()}" />
                                                    <button type="submit" name="action" value="delete">Yes</button>
                                                    <button type="button" onclick="deleteProduct(this, event)">No</button>
                                                </form>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>

                            </ul>
                        </div>
                    </div>
                    <div class="search-container-side">
                        <div class="filter">
                            <div class="filter-item double-slider-box">
                                <p>Price range</p>
                                <div class="range-slider">
                                    <span class="slider-track"></span>
                                    <input type="range" name="priceMinVal" class="min-val" min="0" max="100" value="0" oninput="slideMin()"/>
                                    <input type="range" name="priceMaxVal" class="max-val" min="0" max="200" value="200" oninput="slideMax()"/>
                                </div>
                                <div class="input-box">
                                    <div class="min-box">
                                        <div class="input-wrap">
                                            <span class="input-addon">$</span>
                                            <input type="text" name="priceMinVal" class="input-field min-input" value="0" onchange="setMinInput()"/>
                                        </div>
                                    </div>
                                    <div class="max-box">
                                        <div class="input-wrap">
                                            <span class="input-addon">$</span>
                                            <input type="text" name="priceMaxVal" class="input-field max-input" value="200" onchange="setMaxInput()"/>
                                        </div>
                                    </div>
                                </div> 
                            </div>
                            <div class="filter-item filter-genre">
                                <p>Narrow by genre</p>
                                <div class="search-bar search-bar-genre">
                                    <input placeholder="Search genres" name="title" id="searchGenre" />
                                </div>
                                <div class="genre-items" <c:if test="${requestScope.genreList == null}">style="display: none;"</c:if>>
                                    <ul>
                                        <c:forEach var="genre" items="${requestScope.genreList}">
                                            <li class="checkbox-wrapper">
                                                <input type="checkbox" name="genre" class="checkboxGenre"/>
                                                <label for="genre" class="genreLabel">${genre}</label>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="backdrop"></div>
                    <div class="item-add">
                        <form action="MainController" method="POST">
                            <input type="hidden" name="controller" value="game" />
                            <label for="title">Title</label>
                            <input type="text" name="title" required value="a" />
                            <label for="price">Price</label>
                            <input type="text" name="price" required value="" />
                            <label for="releaseDate">Release date</label>
                            <input type="date" name="releaseDate" required value="" />
                            <label for="platform">Platform</label>
                            <input type="text" name="platform" required value="" />
                            <label for="publisher">Publisher</label>
                            <input type="text" name="publisher" required value="" />
                            <label for="description">Description:</label>
                            <textarea name="description" rows="10"></textarea>
                            <div class="item-add-btn">
                                <button type="submit" name="action" value="create">Submit</button>
                                <button type="button" onclick="addProduct(event)">Cancel</button>
                            </div>  
                        </form>
                    </div>
                </div>
            </main>
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
