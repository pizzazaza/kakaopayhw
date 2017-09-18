"use strict";

$(document).ready(function() {

    function extend(superClass, def) {
        var extendClass = function extendClass() {
            // Call a parent constructor
            superClass.apply(this, arguments);

            // Call a child constructor
            if (typeof def.init === "function") {
                def.init.apply(this, arguments);
            }
        };

        var ExtProto = function() {};
        ExtProto.prototype = superClass.prototype;

        var extProto = new ExtProto();
        for (var i in def) {
            extProto[i] = def[i];
        }
        extProto.constructor = extendClass;
        extendClass.prototype = extProto;

        return extendClass;
    };
    function deleteCallback(data){

        console.log(data);
    }


    var ajaxCall = extend(eg.Component, {
        init: function () {

            this.obj = {};
        },
        ajax: function (params, doSomething) {
            Object.assign(this.obj,
                this.options = Object.assign({}, {
                    method: 'GET',
                    url: null,
                    contentType: "application/json",
                    data: null
                }), params);

            $.ajax(this.obj, doSomething);
        }
    });




    function btnCallback(e){

        var $btn = $(e.target);
        var $box = $btn.closest(".col-6")
        var fileId = $box.data("file-info");

        if ($btn.hasClass('btn-download')){
            window.location = "/files/"+fileId;

        }else if ( $btn.hasClass('btn-modify')) {

            $.ajax({
                method: "DELETE",
                url: "/files/"+fileId,
                contentType: "application/json"
            }).done(function(data){
                console.log(data);
                var $ele = $(".col-6");
                console.log($ele);
                $box.remove();
            });
        }
    }

    $('.btn').on('click',btnCallback.bind(this));




});