(function() {
    "use strict";

    var $file = $('.file-image');
    $file.on('change', function () {
        var value = $(this).val();
        if (!value)
            return;

        var form = $('#image-upload')[0];
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: '/product/image/upload/' + productForm.id + '/' + productForm.selectedImage,
            data: new FormData(form),
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function () {
                window.location.reload(true);
            },
            error: function () {
                console.log(arguments)
            }
        });
    });

    var productForm = new Vue({
        el: '.product',
        methods: {
            setState: function(state) {
                this.id    = state.id;
                this.title = state.title;
                this.description = state.description;
                this.isNew = false;
                this.firstImage  = state.images[0] ? '/product/image/' + state.images[0] : '';
                this.secondImage = state.images[1] ? '/product/image/' + state.images[1] : '';
                this.thirdImage  = state.images[2] ? '/product/image/' + state.images[2] : '';


                $categoriesList.val(state.category);

                $('select').material_select();
            },
            save: function() {
                var data = {
                    id:    this.id,
                    title: this.title,
                    description: this.description,
                    category: +($categoriesList.val())
                };

                $.ajax({
                    url:  '/product/save',
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(data),
                    success: function(resp) {
                        window.location.href = '/admin/products/edit?id=' + resp.result;
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
            },
            removeImage: function(index) {
                var that = this;
                $.ajax({
                    url:  '/product/image/remove/' + that.id + '/' + index,
                    type: 'POST',
                    success: function(resp) {
                        window.location.reload(true)
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
            },
            uploadImage: function(index) {
                this.selectedImage = index;
                $file.click();
            }
        },
        data: {
            id: '',
            title: '',
            description: '',
            firstImage:  '',
            secondImage: '',
            thirdImage:  '',
            selectedImage: -1,
            isNew: true
        }
    });


    var $categoriesList = $('.categories');
    categoryList.forEach(function(item) {
        $categoriesList.append($('<option></option>', {
            value: item.id,
            text:  item.title
        }));
    });


    if (currentState)
        productForm.setState(currentState);

})();
