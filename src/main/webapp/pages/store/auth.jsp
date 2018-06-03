<%@ page import="ru.vetoshkin.store.settings.Settings" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../static/css/materialize.css" media="screen"/>
    <link type="text/css" rel="stylesheet" href="../static/css/styles.css"/>

    <title>Войти в кабинет</title>
</head>

<body class="indigo lighten-5">

<div class="navbar-fixed">
    <nav class="light-blue darken-3">
        <div class="nav-wrapper">

            <a href="/store" class="brand-logo center"><%= Settings.getTitle()%>
            </a>

        </div>
    </nav>
</div>


<main class="row login-form">

    <div class="col s6 offset-s3">
        <div class="row container">
            <div class="col s12 card">
                <div class="card-content">
                    <span class="card-title">Войти в кабинет</span>
                    <div class="row">
                        <div class="input-field col s12">
                            <input placeholder="Логин" type="text" class="login" v-model="email">
                        </div>
                        <div class="input-field col s12">
                            <input placeholder="Пароль" type="password" class="pwd" v-model="password">
                        </div>
                    </div>
                </div>
                <div class="card-action">
                    <a href="javascript:;" @click="signIn">Войти</a>
                    <a href="javascript:;" @click="registration">Регистрация</a>
                </div>
            </div>
        </div>
    </div>

</main>

<div class="modal registration">
    <div class="modal-content">
        <h4>Регистрация</h4>
        <div class="row">
            <div class="input-field col s12">
                <input placeholder="Логин" type="text" class="login" v-model="email">
            </div>
            <div class="input-field col s12">
                <input placeholder="Пароль" type="password" class="pwd" v-model="password1">
            </div>
            <div class="input-field col s12">
                <input placeholder="Повторите пароль" type="password" class="pwd" v-model="password2">
            </div>
            <div class="input-field col s12">
                <input placeholder="Имя" type="text" class="pwd" v-model="name">
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <a href="javascript:;" class="modal-close waves-effect waves-red btn-flat close"   @click="cancel()">Отмена</a>
        <a href="javascript:;" class="waves-effect waves-green btn-flat confirm" @click="registr()">Зарегистрироваться</a>
    </div>
</div>

<script type="text/javascript" src="../static/lib/jquery.js"></script>
<script type="text/javascript" src="../static/lib/materialize.js"></script>
<script type="text/javascript" src="../static/lib/vue.js"></script>
<script type="text/javascript" src="../static/pages/store/index.js"></script>
<script type="text/javascript" src="../static/pages/store/auth.js"></script>
</body>
</html>
