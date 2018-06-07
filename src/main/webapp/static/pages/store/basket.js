(function () {
    "use strict";

    var modal = window.bascket = new Vue({
        el: '.modal-basket',
        created: function() {
            var html = '';
            html += '<div id="modal1" class="modal" style="width: 600px;">';
            html +=     '<div class="modal-content">';
            html +=         '<h4>Ваш заказ:</h4>';
            html +=          '<div class="row" v-for="(item, index) in data">';
            html +=             '<div class="col s3 valign-wrapper" style="height: 36px">';
            html +=                 '{{item.title}}';
            html +=             '</div>';
            html +=             '<div class="col s3 valign-wrapper" style="height: 36px">';
            html +=                 '<a href="javascript:;" class="btn-flat btn-small" style="padding: 0;" @click="remove(item.id)"><i class="material-icons center" style="margin: 0 5px 0 5px;">indeterminate_check_box</i></a>';
            html +=                 '<p style="width: 30px;" class="center-align">{{item.count}}</p>';
            html +=                 '<a href="javascript:;" class="btn-flat btn-small" style="padding: 0;" @click="add(item.id)"><i class="material-icons center" style="margin: 0 5px 0 5px;">add_box</i></a>';
            html +=             '</div>';
            html +=             '<div class="col s3 valign-wrapper" style="height: 36px">';
            html +=                 '<p>{{item.sum}} ₽</p>';
            html +=             '</div>';
            html +=             '<div class="col s3">';
            html +=                 '<a href="javascript:;" class="waves-effect waves-red btn-flat btn-small" @click="fullRemove(item.id)"><i class="material-icons center">remove_shopping_cart</i></a>';
            html +=             '</div>';
            html +=          '</div>';
            html +=          '<div class="row">';
            html +=             '<hr/>';
            html +=             '<div class="col s 12">';
            html +=             'Сумма к оплате: <b>{{sum}}</b> ₽';
            html +=             '</div>';
            html +=          '</div>';
            html +=     '</div>';
            html +=     '<div class="modal-footer">';
            html +=         '<a href="javascript:;" class="modal-close waves-effect waves-green btn-flat" @click="buy()">Оплатить</a>';
            html +=     '</div>';
            html += '</div>';

            this.$options.template = html;
            var that = this;

            $.ajax({
                url:  '/basket/get',
                type: 'POST',
                success: function(data) {
                    that.prepareResponse(data.result);
                }
            });
        },
        methods: {
            buy: function() {
                var form = '';
                form += '<form method="POST" action="https://money.yandex.ru/quickpay/confirm.xml" style="display: none">';
                form +=     '<input type="hidden" name="receiver"    value="41001775291979">';
                form +=     '<input type="hidden" name="quickpay-form" value="donate">';
                form +=     '<input type="hidden" name="targets"     value="транзакция">';
                form +=     '<input type="hidden" name="sum"         class="sum" value="' + ico.sum + '" data-type="number">';
                form +=     '<input type="radio"  name="paymentType" value="AC" checked="checked">';
                form +=     '<input type="submit" value="Перевести"  class="go">';
                form +=     '<input type="hidden" name="successURL"  value="http://localhost:8181/store">';
                form += '</form>';

                $('body').append($(form));
                $('.go').click();
            },
            remove: function(id) {
                for (var i = 0; i < this.data.length; i++) {
                    if (this.data[i].id == id) {
                        if (this.data[i].count == 1) {
                            this.fullRemove(id);
                        } else {
                            this.data[i].count--;
                            this.saveBasket();
                        }
                        break;
                    }
                }
            },
            add: function(id) {
                var find = false;
                for (var i = 0; i < this.data.length; i++) {
                    if (this.data[i].id == id) {
                        find = true;
                        this.data[i].count++;
                        break;
                    }
                }

                if (!find) {
                    this.data.push({
                        id: id,
                        count: 1
                    });
                }

                this.saveBasket();
            },
            fullRemove: function(id) {
                var result = [];
                this.data.forEach(function(item) {
                    if (item.id != id) {
                        result.push(item);
                    }
                });
                this.data = result;
                this.saveBasket();

            },
            saveBasket: function() {
                var that = this;
                $.ajax({
                    url:  '/basket/save',
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({items: that.data, sum: ico.sum}),
                    success: function(data) {
                        that.prepareResponse(data.result);
                    }
                });
            },
            prepareResponse: function(result) {
                this.sum = ico.sum = result.sum;
                this.data = result.items;
                if (this.data.length == 0)
                    $('#modal1').modal('close');
            }
        },
        data: {
            data: [],
            sum: 0
        }
    });


    var ico = new Vue({
        el: '.basket-ico',
        methods: {
            open: function () {
                if (this.sum == 0)
                    return;

                $('#modal1').modal('open');
            }
        },
        data: {
            sum: 0
        }
    });


    var room = new Vue({
        el: '.room-ico',
        methods: {
            setState: function(state) {
                if (state.email) {
                    this.email = state.email;
                    this.title = state.email;
                } else {
                    this.email = null;
                    this.title = 'Личный кабинет';
                }
            },
            singIn: function() {
                if (this.email)
                     window.location.href = '/store/profile';
                else window.location.href = '/store/auth';
            }
        },
        data: {
            email: null,
            title: 'Личный кабинет'
        }
    });

    if (currentUser)
        room.setState(currentUser);


    $('.modal').modal();

})();
