(function() {
    "use strict";

    Vue.component('paginate', VuejsPaginate);

    var template = '';
    template += '<paginate ';
    template +=     ':pageCount="' + window.pages + '" ';
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


    var orders = new Vue({
        el: '.order-result',
        methods: {
            setState: function(state) {
                var result = [];
                state.forEach(function(item) {
                    item.time = new Date(item.time).toLocaleString();
                    result.push(item);
                });
                this.source = result;
            },
            change: function(index) {
                var item = this.source[index];

                $.ajax({
                    url:  '/order/change/' + item.orderId + '/' + item.status,
                    type: 'POST',
                    success: function() {
                        window.location.reload(true);
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
            }
        },
        data: {
            source: []
        }
    });


    function getItems(page) {
        $.ajax({
           url: '/order/list/' + page,
           type: 'POST',
           success: function(data) {
               orders.setState(data.result);
           }
        });
    }
})();
