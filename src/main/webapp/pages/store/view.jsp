<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.vetoshkin.store.category.Category" %>
<%@ page import="ru.vetoshkin.store.category.dao.CategoryStorage" %>
<%@ page import="ru.vetoshkin.store.settings.Settings" %>
<%@ page import="ru.vetoshkin.store.util.Json" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ru.vetoshkin.store.product.Product" %>
<%@ page import="ru.vetoshkin.store.product.dao.ProductStorage" %>
<%@ page import="ru.vetoshkin.store.product.dao.PriceService" %>
<%@ page import="java.util.Map" %>
<%@ page import="ru.vetoshkin.store.user.dao.UserStorage" %>
<%@ page import="ru.vetoshkin.store.util.ServletUtil" %>
<%@ page import="ru.vetoshkin.store.core.AuthFilter" %>
<%@ page import="ru.vetoshkin.store.user.dto.UserResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../../static/css/materialize.css" media="screen"/>
    <link type="text/css" rel="stylesheet" href="../../static/css/styles.css"/>

    <%
        Collection<Category> categoryList = CategoryStorage.getAll();

        String productId = pageContext.getRequest().getParameter("id");
        Product product = ProductStorage.get(productId);

        Map<String, Float> allPrices = PriceService.getALlPrice();
        Map<String, String> allTitles = PriceService.getAllTitles();

        Cookie cookie = ServletUtil.getCookie((HttpServletRequest) pageContext.getRequest(), AuthFilter.cookieName);
        UserResponse user = null;
        if (cookie != null) {
            user = UserStorage.getUser(cookie.getValue()).transfer();
        }
    %>

    <title><%= product.getTitle()%></title>
</head>

<body class="indigo lighten-5">

<div class="navbar-fixed">
    <nav class="light-blue darken-3">
        <div class="nav-wrapper">

            <a href="/store" class="brand-logo center"><%= Settings.getTitle()%>
            </a>

            <ul class="right hide-on-med-and-down">
                <li><i class="material-icons left">local_phone</i><b>8-800-00-00-000</b> (с 05:00 до 00:00)</li>
                <li><a href="javascript:;" class="room room-ico"    @click="singIn()"><i class="material-icons left">account_box</i>{{title}}</a></li>
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

    <div class="col s9 product">

        <div class="row">
            <div class="col s12">
                <p class="flow-text">{{title}}</p>
            </div>
        </div>

        <div class="row">
            <div class="col s12">
                <div class="row">
                    <div class="col s12">
                        <div class="card">
                            <div class="row">
                                <div class="col s12">
                                    <blockquote>{{description}}</blockquote>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s4" v-for="img in images">
                            <div class="card small">
                                <div class="row">
                                    <div class="col s12 img-container">
                                        <img class="materialboxed" width="400" :src="img">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s2">
                            <p>
                                <%
                                    if (user != null && user.getEmail() != null) {
                                        %><a class="waves-effect waves-light btn amber darken-3" @click="add()"><i class="material-icons left">shopping_cart</i>{{price}} ₽</a><%
                                    } else {
                                        %>Вы не авторизованы<%
                                    }
                                %>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</main>

<div class="modal-basket"></div>

<script type="text/javascript" src="../../static/lib/jquery.js"></script>
<script type="text/javascript" src="../../static/lib/materialize.js"></script>
<script type="text/javascript" src="../../static/lib/vue.js"></script>
<script type="text/javascript" src="../../static/lib/pagination.js"></script>
<script type="text/javascript">
    window.product   = <%= Json.toJson(product)%>;
    window.allPrices = <%= Json.toJson(allPrices)%>;
    window.allTitles = <%= Json.toJson(allTitles)%>;
    window.currentUser = <%= Json.toJson(user)%>;
</script>

<script type="text/javascript" src="../../static/pages/store/cookie-util.js"></script>
<script type="text/javascript" src="../../static/pages/store/view.js"></script>
<script type="text/javascript" src="../../static/pages/store/basket.js"></script>
</body>
</html>
