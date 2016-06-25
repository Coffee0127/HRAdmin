$(function() {

    // append caseStatus
    for (var key in Properties.CaseStatus) {
        var value = Properties.CaseStatus[key];
        $('#caseStatus').append(
            $('<option>', {
                'value': value,
                'html': codeTypeFormatter(value)
            })
        );
    }

    var _units = sessionStorage.getItem('_units') && JSON.parse(sessionStorage.getItem('_units')) || {};
    // append dept, unit, hrmRole, hrmType
    $.ajax({
        url: url('/codeType/query'),
        type: 'post'
    })
    .done(function(msg) {
        $.each(msg.depts, function(key, value) {
            $('#dept').append(
                $('<option>', {
                    'value': key,
                    'html': value
                })
            );
        });

        $.each(msg.hrmRoles, function(index, object) {
            var key = _.keys(object)[0];
            var value = object[key];
            $('#hrmRole').append(
                $('<option>', {
                    'value': key,
                    'html': value
                })
            );
        });
        $.each(msg.hrmTypes, function(index, object) {
            var key = _.keys(object)[0];
            $('#hrmType').append(
                $('<option>', {
                    'value' : key,
                    'html' : object[key]
                })
            );
        });
    });

    $('#dept').on('change', function() {
        $('#unit').find('option:not(:first)').remove();
        var deptId = $(this).val();
        if (deptId == '') {
            return;
        }

        var appendUnits = function(units) {
            $.each(units, function(index, object) {
                var key = _.keys(object)[0];
                $('#unit').append(
                    $('<option>', {
                        'value' : key,
                        'html' : object[key]
                    })
                );
            });
            if (units.length == 1) {
                $('#unit option:eq(1)').prop('selected', true)
            }
        };

        if (!_.contains(_.keys(_units), deptId)) {
            $.ajax({
                url: url('/codeType/queryUnits'),
                type : 'post',
                data: { deptId: deptId }
            })
            .done(function(msg) {
                _units[deptId] = msg;
                appendUnits(msg);
            });
        } else {
            appendUnits(_units[deptId]);
        }
    });

    $('.icon-date-picker').on('click', function() {
        $(this).prev().trigger('focus').datepicker('show');
    });
    $('#beginDatetime').datepicker({
        endDate: '0d'
    });
    $('#endDateTime').datepicker({})
        // TODO if set endDate first, update today would fail
        .datepicker('update', new Date())
        .datepicker('setEndDate', '0d');
    $('#requiredBeginDate').datepicker({});
    $('#requiredEndDate').datepicker({});

    projFormatter = function(value, row) {
        return row.projectCode + '-' + row.projectName;
    };

    btnFormatter = function(value, row) {
        return '<button type="button" class="btn btn-info" data-case-id=' + row.caseId + ' data-toggle="modal" data-target="#caseModal"><span class="p-r-5 glyphicon glyphicon-th-list"></span>檢視</button>';
    };

    var $confirmForm = $('#confirmForm'),
        $applierForm = $('#applierResponseForm'),
        $hrConfirmForm = $('#hrConfirmForm'),
        // real query condition
        _queryCond = {};
    $confirmForm.validationEngine({ scroll: false });
    $applierForm.validationEngine({ scroll: false });
    $hrConfirmForm.validationEngine({ scroll: false });

    $('#dataTable').bootstrapTable({
        showToggle: true,
        pagination: true,
        showColumns: true,
        method: 'post',
        uniqueId: 'caseId',
        sidePagination: 'server',
        url: url('/headcount/find'),
        queryParams: function(params) {
            params.activePage = params.offset / params.limit;
            $.extend(params, _queryCond);
            return params;
        }
    }).hideColumns([ 'preCaseStatus', 'requiredSkill', 'requiredBeginDate', 'requiredEndDate', 'reason', 'note' ]);

    $('#btnQuery').on('click', function() {
        // TODO #1647 refresh with select page 1
        $('#dataTable').bootstrapTable('getOptions').sidePagination = 'client';
        $('#dataTable').bootstrapTable('selectPage', 1);
        $('#dataTable').bootstrapTable('getOptions').sidePagination = 'server';
        _queryCond = $('#queryCondForm').serializeObject();
        $('#dataTable').bootstrapTable('refresh', {});
        return false;
    });

    var _editedCaseId, _$editedCaseRow;
    $('#caseModal').on('show.bs.modal', function (event) {
        var $this = $(this),
            $button = $(event.relatedTarget);
        _$editedCaseRow = $button.parents('tr');
        $('tr.edited').removeClass('edited');
        $button.one('focus', function(e){$(this).blur();});
        _editedCaseId = $button.data('caseId');
        $this.find('.modal-title span').html(_editedCaseId);
        $.ajax({
            url: url('/headcount/findOne'),
            type: 'post',
            data: {
                caseId: _editedCaseId
            }
        })
        .done(function(caseMain) {
            var $caseModal = $('#caseModal');
            // reset tab
            $caseModal.find('div.tabs ul.nav li a:first').tab('show')
               // reset all forms
               .end().find('#msgDetail').hide()
               .end().find('form').each(function(index, element) {
                   $(element)[0].reset();
               });
            // clear previous validation
            $('.confirm-form').validationEngine('hideAll').parents('tr').hide();
            switch (caseMain.caseStatus) {
                case Properties.CaseStatus.RECEIVED_CASE_STATUS:
                case Properties.CaseStatus.REPLY_CASE_STATUS:
                    $confirmForm.parents('tr').show();
                    break;
                case Properties.CaseStatus.UNPASSED_CASE_STATUS:
                    $applierForm.parents('tr').show();
                    break;
                case Properties.CaseStatus.PASSED_CASE_STATUS:
                case Properties.CaseStatus.HANDLING_CASE_STATUS:
                    $hrConfirmForm.parents('tr').show();
                    break;
            }
            $caseModal.find('table.table div[data-field]').html('-');
            for (var key in caseMain) {
                if (key == 'caseDetails') {
                    var $table = $caseModal.find('table#detailTable');
                    $table.find('tr:not(:first)').remove();
                    var details = caseMain[key];
                    $.each(details, function(index, detail) {
                        var $tr = $('<tr>');
                        $tr.append($('<td>', { text: codeTypeFormatter(detail['caseStatus']), 'class': 'text-center' }));
                        $tr.append($('<td>', { text: dateFormatter(detail['updateDatetime']), 'class': 'text-center' }));
                        $tr.append($('<td>', { text: detail['updater'], 'class': 'text-center' }));
                        $tr.append($('<td>', { text: detail['msgDetail'] }));
                        $table.append($tr);
                    });
                    continue;
                }
                var $div = $caseModal.find('table.table div[data-field="' + key + '"]');
                var formatter = $div.data('formatter');
                var value = caseMain[key];
                if (formatter) {
                    value = window[formatter](value, caseMain);
                }
                $div.html(value);
            }
        })
        .fail(function() {
            showErrorMessage('query fail');
        });
    });

    $('input[name="confirm"]').on('change', function() {
        if ($(this).val() == 'Y') {
            $('#msgDetail').fadeOut().val('').validationEngine('hide');
        } else {
            $('#msgDetail').fadeIn().focus();
        }
        return false;
    });

    var submitForm = function(form, url, callback) {
        var $form = $(form);
        if ($form.validationEngine('validate')) {
            $.ajax({
                url: url,
                type: 'post',
                data: $.extend({}, $form.serializeObject(), {
                    caseId: _editedCaseId
                })
            })
            .done(function(msg) {
                if (callback != null) {
                    callback.call(null, msg);
                    return;
                }

                if (msg.code == 0) {
                    showHintMessage('處理完畢!');
                    // update the dataTable
                    $('#dataTable').bootstrapTable('updateByUniqueId', {
                        id: _editedCaseId,
                        row: JSON.parse(msg.desc)
                    }).find('tr[data-uniqueid="' + _editedCaseId + '"]').addClass('edited');
                    setTimeout(function() {
                        $('.modal').modal('hide');
                    }, 1500);
                } else {
                    showErrorMessage('系統異常：' + msg.desc);
                }
            });
        }
        return false;
    };

    // 同意 / 不同意
    $('#btnConfirm').on('click', function() {
        return submitForm($confirmForm, url('/headcount/confirm'));
    });

    // 申請者回覆
    $('#btnApplierReply').on('click', function() {
        return submitForm($applierForm, url('/headcount/reply'));
    });

    // 申請者放棄申請
    $('#btnApplierDiscard').on('click', function() {
        return submitForm($applierForm, url('/headcount/dispose'));
    });

    // HR 處理
    $('#btnHRConfirm').on('click', function() {
        return submitForm($hrConfirmForm, url('/headcount/process'));
    });

    // HR 結案
    $('#btnHRClose').on('click', function() {
        return submitForm($hrConfirmForm, url('/headcount/close'));
    });
});