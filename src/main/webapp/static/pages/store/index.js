(function() {
    "use strict";

    var slider = $('.carousel.carousel-slider');
    slider.carousel({
        dist: 0,
        fullWidth: true,
        duration: 200
    });

    var intervalId = setInterval(function () {
        slider.carousel('next', 1);
    }, 3000);


    var bestProductsComp = new Vue({
        el: '.best-products',
        methods: {
            setState: function() {
                var result = [];
                bestProduct.order.forEach(function(code) {
                    result.push(bestProduct.products[code]);
                });

                result.forEach(function(item) {
                    for (var i = 0; i < item.images.length; i++) {
                        item.images[i] = '/product/image/' + item.images[i];
                    }
                });

                this.source = result;
            },
            more: function(id) {
                window.location.href = '/store/product?id=' + id;
            }
        },
        data: {
            source: []
        }
    });


    bestProductsComp.setState();
})();
