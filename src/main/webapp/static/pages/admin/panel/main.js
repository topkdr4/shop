(function() {
    "use strict";

    var $menu = $('#fixed-menu');
    var $hamburger = $('#hamburger');
    var $hover = $('.fixed-hover');
    var $body = $('body');

    function showMenu() {
        $hover.toggleClass('fixed-nav-hidden');
        $hamburger.toggleClass('is-active');
        $menu.toggleClass('fixed-nav-hidden');
        $body.toggleClass('overflow-hidden');
    }

    $hamburger.on('click', showMenu);
    $hover.on('click', showMenu);

    $('.collapsible').collapsible();


    $('.logout').on('click', function() {
        $.ajax({
            url: '/api/admin/logout',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            success: function() {
                // TODO
                window.location.reload(true);
            }
        });
    });
})();
