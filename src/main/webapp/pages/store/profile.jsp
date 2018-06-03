<%@ page import="ru.vetoshkin.store.settings.Settings" %>
<%@ page import="ru.vetoshkin.store.user.dao.UserStorage" %>
<%@ page import="ru.vetoshkin.store.user.dto.UserResponse" %>
<%@ page import="ru.vetoshkin.store.core.AuthFilter" %>
<%@ page import="ru.vetoshkin.store.util.ServletUtil" %>
<%@ page import="ru.vetoshkin.store.util.Json" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="../error/404.jsp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../static/css/materialize.css" media="screen"/>
    <link type="text/css" rel="stylesheet" href="../static/css/styles.css"/>

    <%
        Cookie cookie = ServletUtil.getCookie((HttpServletRequest) pageContext.getRequest(), AuthFilter.cookieName);
        UserResponse user = null;
        if (cookie != null) {
            user = UserStorage.getUser(cookie.getValue()).transfer();
        } else {
            throw new Exception("Пользователь не найден");
        }

        if (user == null)
            throw new Exception("Пользователь не найден");
    %>

    <title>Профиль</title>
</head>

<body class="indigo lighten-5">

<div class="navbar-fixed">
    <nav class="light-blue darken-3">
        <div class="nav-wrapper">
            <a href="/store" class="brand-logo center"><%= Settings.getTitle()%></a>
        </div>
    </nav>
</div>


<main class="row">

    <div class="col s8 offset-s2">
        <div class="row container">
            <div class="col s12">
                <ul class="tabs tabs-fixed-width tab-demo z-depth-1">
                    <li class="tab"><a class="active" href="#profile">Профиль</a></li>
                    <li class="tab"><a class="active" href="#history">История заказов</a></li>
                </ul>
                <div id="profile" class="col s12 card">
                    <div class="card-content">
                        <div class="row">
                            <div class="input-field col s12">
                                <input placeholder="Имя" type="text" v-model="name">
                            </div>
                            <div class="input-field col s12">
                                <input placeholder="Пароль" type="text" v-model="password">
                            </div>
                            <div class="col s12">
                                <input type="checkbox" class="filled-in" id="inform" v-model="dispatch"/>
                                <label for="inform">Получать информирование оп E-mail</label>
                            </div>
                        </div>
                    </div>
                    <div class="card-action">
                        <a href="javascript:;" @click="save">Сохарнить</a>
                        <a href="javascript:;" @click="logout">Выйти</a>
                    </div>
                </div>
                <div id="history" class="col s12 card">
                    <div class="card-content">
                        <div class="row">

                        </div>
                        <div class="row">
                            <div class="col s12">
                                <div class="card pagination"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>



</main>


<script type="text/javascript" src="../static/lib/jquery.js"></script>
<script type="text/javascript" src="../static/lib/materialize.js"></script>
<script type="text/javascript" src="../static/lib/vue.js"></script>
<script type="text/javascript" src="../static/lib/pagination.js"></script>
<script type="text/javascript" src="../static/pages/store/index.js"></script>
<script type="text/javascript">
    window.currentUser = <%= Json.toJson(user)%>;
    $('.tabs').tabs();
</script>
<script type="text/javascript" src="../static/pages/store/profile.js"></script>
</body>
</html>
