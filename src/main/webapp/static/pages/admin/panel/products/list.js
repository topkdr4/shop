(function() {
    "use strict";

    Vue.component('paginate', VuejsPaginate);

    var pagination = window.pag = new Vue({
        el: '.pagination',
        template: '<paginate :pageCount="pages" :containerClass="\'pagination\'" :clickHandler="clickCallback" />',
        methods: {
            clickCallback: function(page) {
                if (page == 0)
                    return;

                getProductByCategory($categoriesList.val(), page);
            },
            setPageCount: function(count) {
                this.pages = count;
                this.$children[0].selectFirstPage();
            }
        },
        data: {
            pages: 1
        }
    });


    /**
     * Таблица результата
     */
    var table = new Vue({
        el: '.search-result',
        data: {
            data: []
        },
        methods: {
            setData: function(data) {
                this.data = data;
            },
            remove: function(id) {
                $.ajax({
                    url:  '/product/remove/' + id,
                    type: 'POST',
                    success: function() {
                        window.location.reload(true);
                    }
                });
            },
            edit: function(id) {
                window.location.href="/admin/products/edit?id=" + id;
            }
        }
    });



    var $categoriesList = $('.selected-category');
    $categoriesList.on('change', productListInit);


    /**
     * Инициализация списка категорий
     */
    function productListInit() {
        var category = $categoriesList.val();
        getPageForCategory(category, function(data) {
            pagination.setPageCount(data.result);
            getProductByCategory(category, 1);
        });
    }


    /**
     * Заполнить селект списком категорий
     */
    $.ajax({
        url: '/category/list',
        type: 'POST',
        success: function(data) {

            data.result.forEach(function(item) {
                $categoriesList.append($('<option></option>', {
                    value: item.id,
                    text:  item.title
                }));
            });

            productListInit();

            $('select').material_select();
        }
    });



    /**
     * Получить кол-во страниц для категории
     */
    function getPageForCategory(category, callback) {
        $.ajax({
            url: '/product/list/' + category + '/count',
            type: 'POST',
            success: callback
        });
    }


    /**
     * Получить данные по категории + странице
     */
    function getProductByCategory(category, page) {
        $.ajax({
            url:  '/product/list/' + category + '/' + page,
            type: 'POST',
            success: function(data) {
                table.setData(data.result);
            }
        });
    }


})();



/**
 * Загрузка xml на сервер
 */
(function() {
    "use strict";

    $('.xml-wrap').on('click', function () {
        $('.xml-file').click();
    });


    $('.xml-file').on('change', function() {
        var value = $(this).val();
        if (!value)
            return;

        var form = $('#xml-upload')[0];

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/product/upload",
            data: new FormData(form),
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function () {
                console.log(arguments)
            },
            error: function () {
                console.log(arguments)
            }
        });
    });
})();
