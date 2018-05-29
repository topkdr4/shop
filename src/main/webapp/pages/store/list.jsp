<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.vetoshkin.store.category.Category" %>
<%@ page import="ru.vetoshkin.store.category.dao.CategoryStorage" %>
<%@ page import="ru.vetoshkin.store.product.dao.ProductService" %>
<%@ page import="ru.vetoshkin.store.settings.Settings" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ru.vetoshkin.store.util.Json" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../../static/css/materialize.css" media="screen"/>

    <%
        Settings settings = Settings.getInstance();
        Collection<Category> categoryList = CategoryStorage.getAll();

        String categoryId = pageContext.getRequest().getParameter("category");
        int intCategoryId = Integer.parseInt(categoryId);
        Category currentCategory = CategoryStorage.get(intCategoryId);
        long pageCount = ProductService.getPageCount(intCategoryId);
    %>

    <title><%= currentCategory.getTitle()%>
    </title>
</head>

<body class="indigo lighten-5">

<div class="navbar-fixed">
    <nav class="light-blue darken-3">
        <div class="nav-wrapper">

            <a href="/store" class="brand-logo center"><%= settings.getTitle()%>
            </a>

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
                    out.println("<a href=\"/store/product/list?category=" + category.getId() + "\" class=\"collection-item light-blue-text text-darken-3\">" + category.getTitle() + "</a>");
                }
            %>
        </div>
    </div>

    <div class="col s9">

        <div class="row">
            <div class="col s12">
                <p class="flow-text"><%= currentCategory.getTitle()%>
                </p>
            </div>
        </div>

        <div class="row">
            <div class="col s12">
                <div class="bikes">
                    <div class="col s3" v-for="item in data">
                        <div class="card small">
                            <div class="card-image" @click="more(item.id)">
                                <img :src="item.images[0]">
                            </div>
                            <div class="card-content">
                                <p>{{item.title}}</p>
                            </div>
                            <div class="card-action">
                                <a href="javascript:;">В коризну</a>
                                <span>{{item.price}}₽</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col s12">
                <div class="card pagination"></div>
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


<script type="text/javascript" src="../../static/lib/jquery.js"></script>
<script type="text/javascript" src="../../static/lib/materialize.js"></script>
<script type="text/javascript" src="../../static/lib/vue.js"></script>
<script type="text/javascript" src="../../static/lib/pagination.js"></script>
<script type="text/javascript">
    window.pages = <%= Json.toJson(pageCount)%>
    window.category = <%= Json.toJson(intCategoryId)%>
</script>

<script type="text/javascript" src="../../static/pages/store/list.js"></script>
</body>
</html>
