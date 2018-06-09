<%@ page import="ru.vetoshkin.store.category.Category" %>
<%@ page import="ru.vetoshkin.store.category.dao.CategoryStorage" %>
<%@ page import="ru.vetoshkin.store.product.Product" %>
<%@ page import="ru.vetoshkin.store.product.dao.ProductStorage" %>
<%@ page import="ru.vetoshkin.store.util.Json" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../../static/css/materialize.css" media="screen"/>
    <link type="text/css" rel="stylesheet" href="../../static/css/hamburger.css"/>
    <link type="text/css" rel="stylesheet" href="../../static/css/styles.css"/>
    <%
        String stringId = pageContext.getRequest().getParameter("id");
        Product product = ProductStorage.get(stringId != null ? stringId : "");

        Collection<Category> categoryList = CategoryStorage.getAll();
    %>
    <title><%=product != null ? product.getTitle() : "Новая категория"%>
    </title>
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
            <div class="collapsible-header"><i class="material-icons">attach_money</i>Платежи <span class="new badge light-blue darken-3">4</span></div>
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

<main class="row">
    <div class="col s6 offset-l3">
        <div class="row">
            <div class="col s12">
                <div class="card product">
                    <div class="card-content">
                        <div class="row">
                            <div class="input-field col s4">
                                <input placeholder="Артикул" class="code" type="text" v-model="id" />
                            </div>
                            <div class="input-field col s4">
                                <input placeholder="Наименование" class="title" type="text" v-model="title" />
                            </div>
                            <div class="input-field col s4">
                                <input placeholder="Цена" class="price" type="text" v-model="price" />
                            </div>
                            <div class="input-field col s12">
                                <textarea class="materialize-textarea description" v-model="description"></textarea>
                            </div>
                            <div class="input-field col s12">
                                <select class="categories">
                                </select>
                            </div>
                        </div>
                        <div class="row" v-show="!isNew">
                            <div class="col s4">
                                <div class="card small">
                                    <div class="card-image waves-effect waves-block waves-light">
                                        <img class="activator" :src="firstImage" v-show="firstImage">
                                    </div>
                                    <div class="card-content">
                                        <span class="card-title activator grey-text text-darken-4">Изображение 1<i class="material-icons right">more_vert</i></span>
                                    </div>
                                    <div class="card-reveal">
                                        <span class="card-title grey-text text-darken-4">Изображение 1<i class="material-icons right">close</i></span>
                                        <p>
                                            <a class="waves-effect waves-light btn green accent-4"  v-show="!firstImage" @click="uploadImage(1)"><i class="material-icons right">file_upload</i>Загрузить</a>
                                            <a class="waves-effect waves-light btn materialize-red" v-show="firstImage"  @click="removeImage(1)"><i class="material-icons right">delete_forever</i>Удалить</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col s4">
                                <div class="card small">
                                    <div class="card-image waves-effect waves-block waves-light">
                                        <img class="activator" :src="secondImage" v-show="secondImage">
                                    </div>
                                    <div class="card-content">
                                        <span class="card-title activator grey-text text-darken-4">Изображение 2<i class="material-icons right">more_vert</i></span>
                                    </div>
                                    <div class="card-reveal">
                                        <span class="card-title grey-text text-darken-4">Изображение 2<i class="material-icons right">close</i></span>
                                        <p>
                                            <a class="waves-effect waves-light btn green accent-4"  v-show="!secondImage" @click="uploadImage(2)"><i class="material-icons right">file_upload</i>Загрузить</a>
                                            <a class="waves-effect waves-light btn materialize-red" v-show="secondImage"  @click="removeImage(2)"><i class="material-icons right">delete_forever</i>Удалить</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col s4">
                                <div class="card small">
                                    <div class="card-image waves-effect waves-block waves-light">
                                        <img class="activator" :src="thirdImage" v-show="thirdImage">
                                    </div>
                                    <div class="card-content">
                                        <span class="card-title activator grey-text text-darken-4">Изображение 3<i class="material-icons right">more_vert</i></span>
                                    </div>
                                    <div class="card-reveal">
                                        <span class="card-title grey-text text-darken-4">Изображение 3<i class="material-icons right">close</i></span>
                                        <p>
                                            <a class="waves-effect waves-light btn green accent-4"  v-show="!thirdImage" @click="uploadImage(3)"><i class="material-icons right">file_upload</i>Загрузить</a>
                                            <a class="waves-effect waves-light btn materialize-red" v-show="thirdImage"  @click="removeImage(3)"><i class="material-icons right">delete_forever</i>Удалить</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-action">
                        <a href="javascript:;" @click="save()">Сохарнить</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<form action="POST" enctype="multipart/form-data" id="image-upload" style="display: none">
    <input class="file-image" type="file" hidden  accept="image/x-png,image/jpeg" name="image" />
</form>

</body>
<script type="text/javascript" src="../../static/lib/jquery.js"></script>
<script type="text/javascript" src="../../static/lib/vue.js"></script>
<script type="text/javascript" src="../../static/lib/materialize.js"></script>
<script type="text/javascript" src="../../static/lib/pagination.js"></script>
<script type="text/javascript" src="../../static/pages/admin/panel/main.js"></script>
<script type="text/javascript">
    window.currentState = <%=Json.toJson(product)%>;
    window.categoryList = <%=Json.toJson(categoryList)%>;
</script>
<script type="text/javascript" src="../../static/pages/admin/panel/products/edit.js"></script>
</html>
