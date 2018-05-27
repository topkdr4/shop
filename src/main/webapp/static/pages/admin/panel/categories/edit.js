(function() {
    "use strict";

    var $title = $('.title');

    if (currentState) {
        $title.val(currentState.title);
    }

    function save() {
        var id = currentState ? currentState.id : null;
        var title = $title.val();

        $.ajax({
            url: '/category/save',
            type:"POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({id: id, title: title}),
            success: function(data) {
                window.location.href = '/admin/categories/edit?id=' + data.result;
            }
        });
    }

    $('.save-category').on('click', save);
})();
