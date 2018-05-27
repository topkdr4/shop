(function() {
    "use strict";

    Vue.component('paginate', VuejsPaginate);

    $.ajax({
        url:  '/category/list/count',
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
                    url:  '/category/remove/' + id,
                    type: 'POST',
                    success: function() {
                        window.location.reload(true);
                    }
                });
            },
            edit: function(id) {
                window.location.href="/admin/categories/edit?id=" + id;
            }
        }
    });


    function getItems(page) {
        $.ajax({
            url:  '/category/list/' + page,
            type: 'POST',
            success: function(data) {
                table.setData(data.result);
            }
        });
    }

})();
