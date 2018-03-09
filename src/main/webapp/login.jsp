
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="static/css/materialize.css"  media="screen"/>
</head>

<body class="light-blue lighten-5">

<div class="row container">
    <form class="card card-panel col s6 offset-s3 small">
        <div class="card-content">
            <span class="card-title center-align">Панель администрирования</span>
            <div class="input-field col s12">
                <input placeholder="Логин" id="login" class="validate">
            </div>
            <div class="input-field col s12">
                <input placeholder="Пароль" id="password" class="validate">
            </div>
        </div>
        <div class="card-action">
            <a href="#">Войти</a>
        </div>
    </form>
</div>

<script type="text/javascript" src="static/lib/jquery.js"></script>
</body>
</html>
