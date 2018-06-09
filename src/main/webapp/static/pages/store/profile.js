(function() {
    "use strict";

    Vue.component('paginate', VuejsPaginate);

    var template = '';
    template += '<paginate ';
    template +=     ':pageCount="' + window.pageCount + '" ';
    template +=     ':containerClass="\'pagination\'" ';
    template +=     ':clickHandler="clickCallback" />';

    var pagination = new Vue({
        el: '.pagination',
        template: template,
        methods: {
            clickCallback: getHistoryItems
        }
    });


    var profile = new Vue({
        el: '#profile',
        methods: {
            setState: function(state) {
                this.name = state.name;
                this.dispatch = state.dispatch;
            },
            save: function() {

            },
            logout: function() {
                $.ajax({
                    url:  '/user/logout',
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    success: function() {
                        window.location.href = '/store';
                    }
                })
            }
        },
        data: {
            name: '',
            password: '',
            dispatch: true
        }
    });


    function getHistoryItems(page) {
        $.ajax({
            url:  '/user/payment/' + page,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            success: function(data) {
                history.setState(data.result);
            }
        })
    }


    if (currentUser) {
        profile.setState(currentUser);
        getHistoryItems(1);
    }


    var history = new Vue({
        el: '#history',
        methods: {
            setState: function(state) {
                var result = [];
                state.forEach(function(item) {
                    result.push({
                        id: item.orderId,
                        price: item.price,
                        time: new Date(item.time).toLocaleString(),
                        status: item.status
                    })
                });

                this.data = result;
            }
        },
        data: {
            data: []
        }
    });

})();
