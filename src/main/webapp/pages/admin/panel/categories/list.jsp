<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Категории</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" >
    <link type="text/css" rel="stylesheet" href="../static/css/materialize.css"  media="screen" />
    <link type="text/css" rel="stylesheet" href="../static/css/hamburger.css" />
    <link type="text/css" rel="stylesheet" href="../static/css/styles.css" />
</head>

<body class="grey lighten-5">

<div class="fixed-hover fixed-nav-hidden" ></div>

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
            <div class="collapsible-header"><i class="material-icons">trending_up</i>Аналитика</div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">attach_money</i>Платежи <span class="new badge light-blue darken-3">4</span></div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">settings</i>Настройки</div>
            <ul class="collection collapsible-body sub-menu-item">
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">web</i><a href="#!">Шаблоны</a>
                </li>
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">tune</i><a href="#!">Основное</a>
                </li>
            </ul>
        </li>
    </ul>
</div>

<main class="row">

    <div class="col s6 offset-s3">
        <div class="card">
            <table class="striped centered search-result">
                <thead>
                    <tr>
                        <th></th>
                        <th>Название категории</th>
                        <th>Действия</th>
                    </tr>
                </thead>

                <tbody>
                    <tr v-for="item in data">
                        <td>{{item.id}}</td>
                        <td>{{item.title}}</td>
                        <td>
                            <a class="waves-effect waves-red    btn-flat" @click="remove(item.id)"><i class="material-icons">remove</i></a>
                            <a class="waves-effect waves-yellow btn-flat" @click="edit(item.id)"><i class="material-icons">edit</i></a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="card pagination"></div>
        <div>
            <a class="waves-effect waves-green btn-flat" href="/admin/categories/edit"><i class="material-icons">add</i>Добавить категорию</a>
        </div>
    </div>
</main>

</body>

<script type="text/javascript" src="../static/lib/jquery.js"></script>
<script type="text/javascript" src="../static/lib/vue.js"></script>
<script type="text/javascript" src="../static/lib/materialize.js"></script>
<script type="text/javascript" src="../static/lib/pagination.js"></script>
<script type="text/javascript" src="../static/pages/admin/panel/main.js"></script>
<script type="text/javascript" src="../static/pages/admin/panel/categories/list.js"></script>
</html>
