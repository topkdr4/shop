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
                var array = JSON.parse(getCookie('basket') || '[]');
                if (!array.includes(this.id))
                    array.push(this.id);

                setCookie('basket', JSON.stringify(array));
                bascket.recalc();
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
