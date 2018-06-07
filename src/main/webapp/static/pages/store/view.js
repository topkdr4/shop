(function() {
    "use strict";

    var productView = new Vue({
        el: '.product',
        methods: {
            setState: function(state) {
                this.id = state.id;
                this.title = state.title;
                this.description = state.description;
                this.price = state.price;
                var images = [];
                state.images.forEach(function(image) {
                    if (image > 0) {
                        images.push('/product/image/' + image);
                    }
                });
                this.images = images;

                $('.materialboxed').materialbox();
            },
            add: function() {
                bascket.add(this.id);
            }
        },
        data: {
            id: '',
            description: '',
            title: '',
            price: 0,
            images: []
        }
    });

    productView.setState(product);
})();
