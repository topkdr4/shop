(function() {
    "use strict";

    /**
     * Почта и наименование магазина
     */
    var settingsComponent = new Vue({
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


    if (settings && settings.mail)
        settingsComponent.setState(settings.mail);



    /**
     * Карусель
     */
    var carouselComponent = new Vue({
        el: '.carousel',
        methods: {
            setState: function(state) {
                var source = [];
                state.forEach(function(item) {
                    source.push({url: item});
                });

                this.source = source;
            },
            save: function() {
                var result = [];
                this.source.forEach(function(item) {
                    result.push(item.url);
                });

                $.ajax({
                    url:  '/settings/carousel/save',
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(result),
                    success: function(resp) {
                        window.location.reload(true);
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
            },
            add: function() {
                if (this.source.length > 8)
                    return;

                this.source.push({
                    url: ""
                });
            },
            remove: function(pos) {
                this.source.splice(pos, 1);
            }
        },
        data: {
            source: []
        }
    });


    if (settings && settings.urls)
        carouselComponent.setState(settings.urls);



    /**
     * Лучший товар
     */
    var bestProducts = new Vue({
        el: '.best-product',
        methods: {
            setState: function (state) {
                var source = [];
                state.forEach(function(item) {
                    source.push({id: item});
                });

                this.source = source;
            },
            save: function () {
                var result = [];
                this.source.forEach(function(item) {
                    if (!result.includes(item.id))
                        result.push(item.id);
                });


                $.ajax({
                    url:  '/settings/best_product/save',
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(result),
                    success: function(resp) {
                        window.location.reload(true);
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
            },
            add: function() {
                if (this.source.length > 16)
                    return;

                this.source.push({
                    id: ""
                });
            },
            remove: function(pos) {
                this.source.splice(pos, 1);
            }
        },
        data: {
            source: []
        }
    });

    if (settings && settings.best)
        bestProducts.setState(settings.best);

})();
