(function() {
    "use strict";

    Vue.component('paginate', VuejsPaginate);

    $.ajax({
        url:  '/product/list/count',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        success: function(data) {
            var template = '';
            template += '<paginate ';
            template +=     ':pageCount="' + data.result + '" ';
            template +=     ':containerClass="\'pagination\'" ';
            template +=     ':clickHandler="clickCallback" />';

            var pagination = new Vue({
                el: '.pagination',
                template: template,
                methods: {
                    clickCallback: getItems
                }
            });

            getItems(1);
        }
    });

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


    function getItems(page) {
        $.ajax({
            url:  '/products/list/' + page,
            type: 'POST',
            success: function(data) {
                table.setData(data.result);
            }
        });
    }


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

    $.ajax({
        url: '/category/list',
        type: 'POST',
        success: function(data) {
            var html = '';
            html += '<select class="selected-category">';
            data.result.forEach(function(item) {
                html += '<option value="' + item.id + '">' + item.title + '</option>';
            });
            html += '</select>';

            $('.categories').html(html);
            $('.selected-category').material_select();

        }
    });

})();
