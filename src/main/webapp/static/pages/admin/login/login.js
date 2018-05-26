(function() {
    "use strict";

    function login() {
        var $login    = $('.login');
        var $password = $('.password');

        $.ajax({
            url: '/api/admin/auth',
            type:"POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({login: $login.val(), password: $password.val()}),
            success: function() {
                window.location.reload(true);
            }
        });
    }

    $('.sign-in').on('click', login);
})();
