(function() {
    "use strict";

    Vue.component('paginate', VuejsPaginate);

    var template = '';
    template += '<paginate ';
    template +=     ':pageCount="' + 1 + '" ';
    template +=     ':containerClass="\'pagination\'" ';
    template +=     ':clickHandler="clickCallback" />';

    var pagination = new Vue({
        el: '.pagination',
        template: template,
        methods: {
            clickCallback: function(page) {

            }
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


    if (currentUser)
        profile.setState(currentUser);



    var history = new Vue({
        el: '#history'
    });

})();
