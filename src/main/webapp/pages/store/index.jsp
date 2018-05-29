<%@ page import="ru.vetoshkin.store.category.Category" %>
<%@ page import="ru.vetoshkin.store.category.dao.CategoryStorage" %>
<%@ page import="ru.vetoshkin.store.settings.Settings" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../static/css/materialize.css" media="screen"/>

    <%
        Settings settings = Settings.getInstance();
        Collection<Category> categoryList = CategoryStorage.getAll();
    %>

    <title><%= settings.getTitle()%></title>
</head>

<body class="indigo lighten-5">

<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
    <li><a href="#!">one</a></li>
    <li><a href="#!">two</a></li>
    <li class="divider"></li>
    <li><a href="#!">three</a></li>
</ul>

<div class="navbar-fixed">
    <nav class="light-blue darken-3">
        <div class="nav-wrapper">

            <a href="javascript:;" class="brand-logo center"><%= settings.getTitle()%></a>

            <ul class="right hide-on-med-and-down">
                <li><i class="material-icons left">local_phone</i><b>8-800-00-00-000</b> (с 05:00 до 00:00)</li>
                <li><a href="javascript:;"><i class="material-icons left">shopping_cart</i>0 ₽</a></li>
            </ul>
        </div>
    </nav>
</div>


<main class="row">

    <div class="col s3">
        <!-- Меню слева -->
        <div class="collection">
            <%
                for (Category category : categoryList) {
                    out.println("<a href=\"/store/product/" + category.getId() + "/list\" class=\"collection-item light-blue-text text-darken-3\">" + category.getTitle() + "</a>");
                }
            %>
        </div>
    </div>

    <div class="col s9">

        <!-- Карусель -->
        <div class="row">
            <div class="col s12">
                <div class="card row carousel carousel-slider center" data-indicators="true" style="max-height: 300px;">
                    <div class="carousel-item red white-text">
                        <h2>Реклама</h2>
                    </div>
                    <div class="carousel-item amber">
                        <h2>Реклама</h2>
                    </div>
                    <div class="carousel-item green">
                        <h2>Реклама</h2>
                    </div>
                    <div class="carousel-item blue">
                        <h2>Реклама</h2>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">

            <div class="col s12">
                <p class="flow-text">Лучшие предложения</p>
            </div>

            <div class="col s4">
                <div class="card small">

                </div>
            </div>

            <div class="col s4">
                <div class="card small">

                </div>
            </div>

            <div class="col s4">
                <div class="card small">

                </div>
            </div>

        </div>

    </div>

</main>


<footer class="page-footer grey darken-1">
    <div class="footer-copyright">
        <div class="container">
            © 2018 Copyright Text
            <a class="grey-text text-lighten-4 right" href="https://github.com/topkdr4/shop">Исходный код</a>
        </div>
    </div>
</footer>


<script type="text/javascript" src="../static/lib/jquery.js"></script>
<script type="text/javascript" src="../static/lib/materialize.js"></script>

<script type="text/javascript">
    $(".dropdown-button").dropdown();
    var a = $('.carousel.carousel-slider');
    a.carousel({
        dist: 0,
        fullWidth: true,
        duration: 200
    });

    var intervalId = setInterval(function () {
        a.carousel('next', 1);
    }, 3000)
</script>
</body>
</html>
