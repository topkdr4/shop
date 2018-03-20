<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>


<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" >
    <link type="text/css" rel="stylesheet" href="static/css/materialize.css"  media="screen" />
    <link type="text/css" rel="stylesheet" href="static/css/hamburger.css" />

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

    </style>
</head>

<body class="light-blue lighten-5">

<div class="fixed-hover fixed-nav-hidden">

</div>

<div class="navbar-fixed">
    <nav class="light-blue darken-3">
        <div class="nav-wrapper">

            <span class="brand-logo center">Администрирование</span>

            <button id="hamburger" class="hamburger hamburger--collapse light-blue darken-3" type="button">
                    <span class="hamburger-box">
                        <span class="hamburger-inner"></span>
                    </span>
            </button>

            <ul class="right hide-on-med-and-down">
                <li><a href="#!"><i class="material-icons right">exit_to_app</i>Выход</a></li>
            </ul>
        </div>
        <div class="progress orange darken-4" style="margin: 0;">
            <div class="indeterminate teal accent-4"></div>
        </div>
    </nav>
</div>


<div id="fixed-menu" class="left card-panel fixed-nav fixed-nav-hidden">
    <ul class="collapsible" data-collapsible="accordion" style="margin: 0">
        <li>
            <div class="collapsible-header"><i class="material-icons">trending_up</i>Продажи</div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">folder_open</i>Категории</div>
            <ul class="collection collapsible-body" style="padding: 0;">
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">add</i><a href="#!">Добавить категорию</a>
                </li>
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">assignment</i><a href="#!">Все категории</a>
                </li>
            </ul>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">assessment</i>Продукция</div>
            <ul class="collection collapsible-body" style="padding: 0;">
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">add</i><a href="#!">Добавить товар</a>
                </li>
                <li class="collection-item valign-wrapper lvl-1">
                    <i class="material-icons">assignment</i><a href="#!">Все товары</a>
                </li>
            </ul>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">attach_money</i>Платежи <span class="new badge light-blue darken-3">4</span></div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">folder_special</i>Акционные предложения</div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">email</i>Управление рассыклой</div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">settings</i>Настройки</div>
        </li>
    </ul>
</div>

<main class="row">

</main>
<%= request.getAttribute("ebota")%>

</body>
<script type="text/javascript" src="static/lib/jquery.js"></script>
<script type="text/javascript" src="static/lib/materialize.js"></script>
<script type="text/javascript">
    var $menu = $('#fixed-menu');
    var $hamburger = $('#hamburger');
    var $hover = $('.fixed-hover');
    var $body = $('body');

    function showMenu() {
        $hover.toggleClass('fixed-nav-hidden');
        $hamburger.toggleClass('is-active');
        $menu.toggleClass('fixed-nav-hidden');
        $body.toggleClass('overflow-hidden');
    }

    $hamburger.on('click', showMenu);
    $hover.on('click', showMenu);

    $('.collapsible').collapsible();
</script>
</html>
