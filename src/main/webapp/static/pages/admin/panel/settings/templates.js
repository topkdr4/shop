(function() {
    "use strict";

    var templateTableComponent = new Vue({
        el: '.templates-result',
        methods: {
            setState: function(state) {
                this.source = state;
            },
            edit: function(key) {
                $.ajax({
                    url:  '/template/get/' + key,
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    success: function(resp) {
                        editorComponent.setTemplate(resp.result);
                    }
                });
            },
            remove: function(key) {
                $.ajax({
                    url:  '/template/remove/' + key,
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    success: function() {
                        window.location.reload(true);
                    }
                });
            },
            add: function() {
                editorComponent.setTemplate(null);
            },
            send: function(key) {
                $.ajax({
                    url:  '/template/send/' + key,
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    success: function() {
                        window.location.reload(true);
                    }
                });
            }
        },
        data: {
            source: {}
        }
    });


    if (templateDict)
        templateTableComponent.setState(templateDict);


    var editorComponent = new Vue({
        el: '.template-editor',
        methods: {
            setTemplate: function(template) {
                this.key  = template ? template.key : 'Новый шаблон';
                this.desc = template ? template.desc : '';
                this.isNew = !template;
                editor.setValue(template ? (template.value || '') : 'Укажите шаблон');
                editor.refresh();
                $('.template-editor').modal('open');
            },
            save: function() {
                var that = this;
                $.ajax({
                    url:  '/template/save/',
                    type: 'POST',
                    data: JSON.stringify({
                        key: that.key,
                        desc: that.desc,
                        value: editor.getValue()
                    }),
                    contentType: "application/json; charset=utf-8",
                    success: function() {
                        window.location.reload(true);
                    }
                });
            }
        },
        data: {
            key: 'Новый шаблон',
            desc: '',
            isNew: true
        }
    });


    var config = {
        lineNumbers: true,
        mode:        "xml",
        theme:       "eclipse",
        indentWithTabs: false,
        readOnly:    false,
        autoRefresh: true
    };

    var editor = CodeMirror.fromTextArea($('#editor')[0], config);

    $('.modal').modal({
        dismissible: false,
        inDuration: 0,
        outDuration: 0,
        ready: function() {
            editor.refresh();
        }
    });


})();
