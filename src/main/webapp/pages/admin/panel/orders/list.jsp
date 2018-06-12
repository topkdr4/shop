<%@ page import="ru.vetoshkin.store.basket.dao.OrderStorage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../../static/css/materialize.css" media="screen"/>
    <link type="text/css" rel="stylesheet" href="../../static/css/hamburger.css"/>
    <link type="text/css" rel="stylesheet" href="../../static/css/styles.css"/>

    <title>Платежи</title>

    <style>
        .fixed-nav {
            position: absolute;
            margin: 0;
            padding: 0;
            width: 350px;
            height: calc(100% - 64px);
            overflow: auto;
            z-index: 65;
        }

        .fixed-nav-hidden {
            display: none;
        }

        .fixed-hover {
            position: absolute;
            z-index: 60;
            width:100%;
            height: 100%;
            margin: 0;
            padding:0;
            background-color: rgba(0, 0, 0, 0.3);
        }

        .overflow-hidden {
            overflow: hidden;
        }

        .lvl-1 {
            padding-left: 40px !important;
        }

        .lvl-1 > .material-icons {
            margin-right: 16px;
        }

        #fixed-menu a {
            color: rgba(0, 0, 0, 0.87);
        }

        .fixed-button {
            bottom: 50px;
            position: absolute;
            right: 50px;
        }

    </style>
    <%
        int newOrders = OrderStorage.getNewOrderCount();
    %>
</head>
<body class="grey lighten-5">

<div class="fixed-hover fixed-nav-hidden"></div>
<div class="navbar-fixed z-depth-1">
    <nav class="light-blue darken-3">
        <div class="nav-wrapper">

            <span class="brand-logo center">Панель управления</span>

            <button id="hamburger" class="hamburger hamburger--collapse light-blue darken-3" type="button">
                        <span class="hamburger-box">
                            <span class="hamburger-inner"></span>
                        </span>
            </button>

            <ul class="right hide-on-med-and-down">
                <li><a href="javascript:;" class="logout"><i class="material-icons right">exit_to_app</i>Выход</a></li>
            </ul>
        </div>
    </nav>
</div>
<div id="fixed-menu" class="left card-panel fixed-nav fixed-nav-hidden">
    <ul class="collapsible menu-root" data-collapsible="accordion">
        <li>
            <div class="collapsible-header"><i class="material-icons">folder_open</i>Каталог</div>
            <ul class="collection collapsible-body sub-menu-item">
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">class</i><a href="/admin/categories">Категории</a>
                </li>
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">assignment</i><a href="/admin/products">Товар</a>
                </li>
            </ul>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">attach_money</i><a href="/admin/orders/list">Платежи</a>
                <%
                    if (newOrders > 0)
                        out.print("<span class=\"new badge light-blue darken-3\">" + newOrders + "</span>");
                %>
            </div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">settings</i>Настройки</div>
            <ul class="collection collapsible-body sub-menu-item">
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">web</i><a href="/admin/settings/templates">Шаблоны</a>
                </li>
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">tune</i><a href="/admin/settings/edit">Основное</a>
                </li>
            </ul>
        </li>
    </ul>
</div>

<main class="row templates-result">
    <div class="col s10 offset-l1 card">
        11
    </div>
    <div class="col s10 offset-l1 card" style="padding: 0;">
        <table class="striped">
            <thead>
            <tr>
                <th>Название</th>
                <th>Описание</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(value, key) in source">
                <td>{{key}}</td>
                <td>{{value}}</td>
                <td>
                    <a class="waves-effect waves-red    btn-flat" @click="remove(key)"><i class="material-icons">remove</i></a>
                    <a class="waves-effect waves-yellow btn-flat" @click="edit(key)"><i class="material-icons">edit</i></a>
                </td>
                <td>
                    <a class="waves-effect waves-green  btn-flat" @click="send(key)"><i class="material-icons">send</i></a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <a class="btn-floating btn-large waves-effect waves-light red fixed-button" @click="add()"><i class="material-icons">add</i></a>
</main>



</body>
<script type="text/javascript" src="../../static/lib/jquery.js"></script>
<script type="text/javascript" src="../../static/lib/vue.js"></script>
<script type="text/javascript" src="../../static/lib/materialize.js"></script>
<script type="text/javascript" src="../../static/lib/pagination.js"></script>
<script type="text/javascript" src="../../static/pages/admin/panel/main.js"></script>
</html>
