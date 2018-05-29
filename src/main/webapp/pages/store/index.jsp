<%@ page import="ru.vetoshkin.store.category.Category" %>
<%@ page import="ru.vetoshkin.store.category.dao.CategoryStorage" %>
<%@ page import="ru.vetoshkin.store.settings.Settings" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ru.vetoshkin.store.util.Json" %>
<%@ page import="ru.vetoshkin.store.product.dao.PriceService" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../static/css/materialize.css" media="screen"/>
    <link type="text/css" rel="stylesheet" href="../static/css/styles.css" />

    <%
        Settings settings = Settings.getInstance();
        Collection<Category> categoryList = CategoryStorage.getAll();
        Map<String, Float> allPrices = PriceService.getALlPrice();
        Map<String, String> allTitles = PriceService.getAllTitles();
    %>

    <title><%= settings.getTitle()%></title>
</head>

<body class="indigo lighten-5">

<div class="navbar-fixed">
    <nav class="light-blue darken-3">
        <div class="nav-wrapper">

            <a href="/store" class="brand-logo center"><%= settings.getTitle()%></a>

            <ul class="right hide-on-med-and-down">
                <li><i class="material-icons left">local_phone</i><b>8-800-00-00-000</b> (с 05:00 до 00:00)</li>
                <li><a href="javascript:;" class="basket basket-ico" @click="open()"><i class="material-icons left">shopping_cart</i>{{sum}} ₽</a></li>
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
                    out.println("<a href=\"/store/product/list?category=" + category.getId() + "\" class=\"collection-item light-blue-text text-darken-3 large-text\">" + category.getTitle() + "</a>");
                }
            %>
        </div>
    </div>

    <div class="col s9">

        <style>
            .lol {
                background-image: url("http://bpic.588ku.com/back_pic/03/95/53/3457ed091204154.jpg");
                background-repeat: no-repeat;
                background-position: center center;
                background-size: cover;
            }

            .lol1 {
                background-image: url("http://www.bicyclecentreballarat.com.au/wp-content/uploads/2016/06/2.jpg");
                background-repeat: no-repeat;
                background-position: center center;
                background-size: cover;
            }

            .lol2 {
                background-image: url("http://www.bicyclecentreballarat.com.au/wp-content/uploads/2016/06/1.jpg");
                background-repeat: no-repeat;
                background-position: center center;
                background-size: cover;
            }

            .lol3 {
                background-image: url("http://goborobudur.com/wp-content/uploads/2016/09/bikepacking-header5-650x250@2x.jpg");
                background-repeat: no-repeat;
                background-position: center center;
                background-size: cover;
            }
        </style>

        <!-- Карусель -->
        <div class="row">
            <div class="col s12">
                <div class="card row carousel carousel-slider center" data-indicators="true" style="max-height: 400px;">
                    <div class="carousel-item lol">
                        <h2>Реклама</h2>
                    </div>
                    <div class="carousel-item lol1">
                        <h2>Реклама</h2>
                    </div>
                    <div class="carousel-item lol2">
                        <h2>Реклама</h2>
                    </div>
                    <div class="carousel-item lol3">
                        <h2>Реклама</h2>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">

            <div class="col s12">
                <p class="flow-text">Лучшие предложения</p>
            </div>

            <div class="col s3">
                <div class="card small">

                </div>
            </div>

            <div class="col s3">
                <div class="card small">

                </div>
            </div>

            <div class="col s3">
                <div class="card small">

                </div>
            </div>

            <div class="col s3">
                <div class="card small">

                </div>
            </div>
        </div>

    </div>

</main>

<div class="modal-basket"></div>

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
<script type="text/javascript" src="../static/lib/vue.js"></script>
<script type="text/javascript">
    window.allPrices = <%= Json.toJson(allPrices)%>;
    window.allTitles = <%= Json.toJson(allTitles)%>;
</script>
<script type="text/javascript" src="../static/pages/store/index.js"></script>
<script type="text/javascript" src="../static/pages/store/cookie-util.js"></script>
<script type="text/javascript" src="../static/pages/store/basket.js"></script>
</body>
</html>
