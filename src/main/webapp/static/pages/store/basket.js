(function () {
    "use strict";

    var modal = new Vue({
        el: '.modal-basket',
        created: function() {
            var html = '';
            html += '<div id="modal1" class="modal" style="width: 400px;">';
            html +=     '<div class="modal-content">';
            html +=         '<h4>Ваш заказ:</h4>';
            html +=          '<div class="row">';
            html +=             '<div class="input-field col s12" v-for="(item, index) in data">';
            html +=                 '{{item.title}} - {{item.price}} ₽';
            html +=                 '<a href="javascript:;" @click="remove(item.id)">Удалить</a>';
            html +=             '</div>';
            html +=          '</div>';
            html +=     '</div>';
            html +=     '<div class="modal-footer">';
            html +=         '<a href="javascript:;" class="modal-close waves-effect waves-green btn-flat" @click="buy()">Оплатить</a>';
            html +=     '</div>';
            html += '</div>';

            var form = '';
            form += '<form method="POST" action="https://money.yandex.ru/quickpay/confirm.xml" style="display: none">';
            form +=     '<input type="hidden" name="receiver"    value="41001775291979">';
            form +=     '<input type="hidden" name="quickpay-form" value="donate">';
            form +=     '<input type="hidden" name="targets"     value="транзакция">';
            form +=     '<input type="hidden" name="sum"         class="sum" value="0" data-type="number">';
            form +=     '<input type="radio"  name="paymentType" value="AC" checked="checked">';
            form +=     '<input type="submit" value="Перевести"  class="go">';
            form += '</form>';

            $('body').append($(form));
            this.$options.template = html;
        },
        methods: {
            buy: function() {
                $('.sum').val(ico.sum);
                $('.go').click();
                setCookie('basket', '[]');
                ico.recalc();
            },
            render: function () {
                var data = [];
                JSON.parse(getCookie('basket')).forEach(function (item) {
                     data.push({title: allTitles[item], price: allPrices[item], id: item});
                });

                this.data = data;
                this.sum = ico.sum;
            },
            remove: function(id) {
                var result = [];
                this.data.forEach(function(item) {
                    if (item.id != id) {
                        result.push(item);
                    }
                });
                setCookie('basket', JSON.stringify(result));
                ico.recalc();
                this.data = result;
            }
        },
        data: {
            data: [],
            sum: 0
        }
    });


    var ico = window.bascket = new Vue({
        el: '.basket-ico',
        created: function () {
            this.recalc();
        },
        methods: {
            open: function () {
                if (this.sum > 0 || JSON.parse(getCookie("basket") || '[]').length > 0) {
                    modal.render();
                    $('.modal').modal('open');
                }
            },
            recalc: function () {
                var result = 0;
                var array = JSON.parse(getCookie("basket") || '[]');
                array.forEach(function(code) {
                    result += allPrices[code];
                });

                this.sum = result;
            }
        },
        data: {
            sum: 0
        }
    });

    $('.modal').modal();

})();
