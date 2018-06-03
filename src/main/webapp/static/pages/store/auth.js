(function() {
    "use strict";

    var form = new Vue({
        el: '.login-form',
        methods: {
            signIn: function() {
                var data = {
                    email: this.email,
                    password: this.password
                };

                $.ajax({
                    url:  '/user/auth',
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(data),
                    success: function() {
                        Materialize.toast('Успешно авторизованы', 1500);
                    },
                    error: function () {
                        Materialize.toast('Неверный логин или пароль', 1500);
                    }
                });
            },
            registration: function() {
                registrationForm.open();
            }
        },
        data: {
            email: '',
            password: ''
        }
    });


    var registrationForm = new Vue({
        el: '.registration',
        methods: {
            open: function() {
                $('.registration').modal('open');
            },
            registr: function() {
                var data = {
                    email: this.email.trim(),
                    password: this.password1.trim(),
                    name:  this.name.trim()
                };


                if (data.password.length == 0) {
                    Materialize.toast('Парол не может быть пустым!', 1500);
                    return;
                }

                if (data.password != this.password2) {
                    Materialize.toast('Пароли не совпадают!', 1500);
                    return;
                }


                if (data.email.length == 0) {
                    Materialize.toast('Email не указан!', 1500);
                    return;
                }

                $.ajax({
                    url:  '/user/registr',
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(data),
                    success: function() {
                        Materialize.toast('Успешно зарегтистрированны', 1500);
                        $('.registration').modal('close');
                    },
                    error: function () {
                        Materialize.toast('Такой пользователь уже существует', 1500);
                    }
                });
            }
        },
        data: {
            email: '',
            password1: '',
            password2: '',
            name: ''
        }
    });


    $('.registration').modal({
        dismissible: false
    });
})();
