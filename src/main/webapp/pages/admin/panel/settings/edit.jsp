<%@ page import="ru.vetoshkin.store.settings.Settings" %>
<%@ page import="ru.vetoshkin.store.util.Json" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../../static/css/materialize.css" media="screen"/>
    <link type="text/css" rel="stylesheet" href="../../static/css/hamburger.css"/>
    <link type="text/css" rel="stylesheet" href="../../static/css/styles.css"/>
    <%
        Settings settings = Settings.getInstance();
    %>
    <title>Настройки</title>
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
            <div class="collapsible-header"><i class="material-icons">trending_up</i>Аналитика</div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">attach_money</i>Платежи <span
                    class="new badge light-blue darken-3">4</span></div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">settings</i>Настройки</div>
            <ul class="collection collapsible-body sub-menu-item">
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">web</i><a href="#!">Шаблоны</a>
                </li>
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">tune</i><a href="/admin/settings/edit">Основное</a>
                </li>
            </ul>
        </li>
    </ul>
</div>

<main class="row">
    <div class="col s8 offset-l2">
        <div class="row settings-form">
            <!-- Карусель на главной странице -->
            <div class="col s6">
                <div class="card">
                    <div class="card-content">
                            <span class="card-title">
                                <b>&laquo;Карусель&raquo;</b><br>
                                <input type="checkbox" class="filled-in" id="carousel" checked="checked" />
                                <label for="carousel">Включить</label>
                            </span>
                    </div>
                </div>
            </div>

            <!-- Лучшие товары на главной странице -->
            <div class="col s6">
                <div class="card">
                    <div class="card-content">
                            <span class="card-title">
                                <b>&laquo;Лучшие предложения&raquo;</b><br>
                                <input type="checkbox" class="filled-in" id="best-product" checked="checked" />
                                <label for="best-product">Включить</label>
                            </span>
                    </div>
                </div>
            </div>

            <!-- Почтовые настройки -->
            <div class="col s12">
                <div class="card">
                    <div class="card-content">
                            <span class="card-title">
                               <b>&laquo;Почта&raquo;</b>
                            </span>
                        <div class="row">
                            <div class="input-field col s6">
                                <input placeholder="Хост" id="host" type="text" v-model="host" />
                                <label for="host">mail.smtp.host</label>
                            </div>
                            <div class="input-field col s6">
                                <input placeholder="Порт" id="port" type="text" v-model="port" />
                                <label for="port">mail.smtp.port</label>
                            </div>
                            <div class="input-field col s6">
                                <input placeholder="Адрес почты" id="address" type="text" v-model="email" />
                                <label for="address">Адрес почты</label>
                            </div>
                            <div class="input-field col s6">
                                <input placeholder="Пароль" id="password" type="password" v-model="password" />
                                <label for="password">Пароль</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Название магазина -->
            <div class="col s12">
                <div class="card">
                    <div class="card-content">
                        <span class="card-title">
                           <b>&laquo;Название магазина&raquo;</b>
                        </span>
                        <div class="row">
                            <div class="input-field col s12">
                                <input placeholder="Название магазина" id="store-title" type="text" v-model="title" />
                                <label for="store-title">Название магазина</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col s12">
                <div class="card">
                    <div class="card-content">
                        <p>
                            <a class="waves-effect waves-light btn green accent-4"  @click="save()"><i class="material-icons right">save</i>Сохранить</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

</body>
<script type="text/javascript" src="../../static/lib/jquery.js"></script>
<script type="text/javascript" src="../../static/lib/vue.js"></script>
<script type="text/javascript" src="../../static/lib/materialize.js"></script>
<script type="text/javascript" src="../../static/lib/pagination.js"></script>
<script type="text/javascript" src="../../static/pages/admin/panel/main.js"></script>
<script type="text/javascript">
    window.currentState = <%= Json.toJson(settings)%>
</script>

<script type="text/javascript" src="../../static/pages/admin/panel/settings/edit.js"></script>
</html>
