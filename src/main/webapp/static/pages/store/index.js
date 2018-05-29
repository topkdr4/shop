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
    }, 3000)
})();
