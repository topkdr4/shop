(function() {
    "use strict";

    var settings = window.settings = new Vue({
        el: '.settings-form',
        methods: {
            setState: function(state) {
                this.host = state.host;
                this.port = state.port;
                this.email = state.email;
                this.password = state.password;
                this.title = state.title;
            },
            save: function() {
                var data = {
                    title: this.title,
                    host:  this.host,
                    port:  +(this.port),
                    email: this.email,
                    password: this.password
                };

                $.ajax({
                    url:  '/settings/save',
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(data),
                    success: function(resp) {
                        window.location.reload(true);
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
            }
        },
        data: {
            host: '',
            port: 0,
            email: '',
            password: '',
            title: ''
        }
    });


    if (currentState)
        settings.setState(currentState);

})();