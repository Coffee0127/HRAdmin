;
$(function() {
    $.extend(window, {
        url: function(path) {
            return ctxPath + path;
        },
        _popHint: function(msg, target) {
            $('#hintModal').modal();
            $('#hintModal').find('div.alert').hide()
                .end().find('div.alert-' + target).show().find('span.msg').html(msg && msg || 'No more messages...');
            var $modalBackdrop = $('.modal-backdrop');
            if ($modalBackdrop.size() > 1) {
                $modalBackdrop = $modalBackdrop.filter(':last');
                $modalBackdrop.css('z-index', parseInt($modalBackdrop.prev().css('z-index')) + 20);
            }
        },
        hint: function(msg) {
            _popHint(msg, 'success');
        },
        error: function(msg) {
            _popHint(msg, 'danger');
        }
    });

    $.extend($.fn, {
        hideColumns: function(fields) {
            var $this = $(this);
            return this.each(function() {
                fields = $.makeArray(fields);
                $.each(fields, function(index, field) {
                    $this.bootstrapTable('hideColumn', field);
                });
            });
        },
        serializeObject: function() {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function() {
                if (o[this.name] !== undefined) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        }
    });
});

!function($) {
    $(document).on("click", "ul.nav li.parent > a > span.icon", function() {
        $(this).find('em:first').toggleClass("glyphicon-minus");
    });
    $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
}(window.jQuery);

$(window).on('resize', function() {
    if ($(window).width() > 768) {
        $('#sidebar-collapse').collapse('show')
    }
})
$(window).on('resize', function() {
    if ($(window).width() <= 767) {
        $('#sidebar-collapse').collapse('hide')
    }
})