(function() {
    "use strict";

    Vue.component('paginate', VuejsPaginate);

    var template = '';
    template += '<paginate ';
    template +=     ':pageCount="' + window.pages+ '" ';
    template +=     ':containerClass="\'pagination\'" ';
    template +=     ':clickHandler="clickCallback" />';

    var pagination = new Vue({
        el: '.pagination',
        template: template,
        methods: {
            clickCallback: getItems
        }
    });

    var table = new Vue({
        el: '.bikes',
        data: {
            data: []
        },
        methods: {
            setData: function(data) {
                this.data = data;
            },
            more: function(id) {
                window.location.href = '/store/product?id=' + id;
            },
            add: function (code) {
                var array = JSON.parse(getCookie('basket') || '[]');
                if (!array.includes(code))
                    array.push(code);

                setCookie('basket', JSON.stringify(array));
                bascket.recalc();
            }
        }
    });


    function getItems(page) {
        $.ajax({
            url:  '/product/list/' + category + '/' + page,
            type: 'POST',
            success: function(data) {
                data.result.forEach(function(item) {
                    for (var i = 0; i < item.images.length; i++) {
                        item.images[i] = '/product/image/' + item.images[i];
                    }
                });
                table.setData(data.result);
            }
        });
    }


    getItems(1);
})();
