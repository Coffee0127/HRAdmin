<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<link href="${ctxPath}/resources/lib/css/bootstrap-table.css" rel="stylesheet" />
<script src="${ctxPath}/resources/lib/js/bootstrap-table.js"></script>
<script src="${ctxPath}/resources/lib/js/bootstrap-table-zh-TW.min.js"></script>
<link href="${ctxPath}/resources/lib/css/validationEngine.jquery.css" rel="stylesheet" />
<script src="${ctxPath}/resources/lib/js/jquery.validationEngine-zh_TW.js"></script>
<script src="${ctxPath}/resources/lib/js/jquery.validationEngine.js"></script>
<style>
.editTable {
    border-bottom: none;
}
.editTable>tbody>tr:first-child>td {
    border-top: none;
}
#caseTable>tbody>tr>td, .editTable>tbody>tr>td {
    vertical-align: middle;
}

.editTable textarea {
    resize: vertical;
}

.p-r-5 {
    padding-right: 5px;
}
</style>
<body>
<script>
$(function() {
    dateFormatter = function(value) {
        return value.replace(/\..*/g, '');
    };

    projFormatter = function(value, row) {
        return row.projectCode + '-' + row.projectName;
    };

    codeTypeFormatter = function(value) {
        return codeMap[value];
    };

    btnFormatter = function(value, row) {
        return '<button type="button" class="btn btn-info" data-case-id=' + row.caseId + ' data-toggle="modal" data-target="#caseModal"><span class="p-r-5 glyphicon glyphicon-th-list"></span>檢視</button>';
    };

    var $confirmForm = $('#confirmForm'); 
    $confirmForm.validationEngine({ scroll: false });
    
    $('#caseTable').bootstrapTable({
        showToggle: true,
        pagination: true,
        showColumns: true,
        sidePagination: 'server',
        url: url('/apply/find'),
        queryParams: function(params) {
            params.activePage = params.offset / params.limit;
            return params;
        }
    }).hideColumns([ 'skill', 'requiredBeginDate', 'requiredEndDate', 'reason', 'note' ]);

    var _caseId;
    $('#caseModal').on('show.bs.modal', function (event) {
        var $this = $(this),
            $button = $(event.relatedTarget);
        _caseId = $button.data('caseId');
        $this.find('.modal-title span').html(_caseId);
        $.ajax({
            url: url('/apply/findOne'),
            type: 'post',
            data: {
                caseId: _caseId
            }
        })
        .done(function(caseMain) {
            var $caseModal = $('#caseModal');
            // reset tab
            $caseModal.find('div.tabs ul.nav li a:first').tab('show')
               // reset form
               .end().find('#msgDetail').hide()
               .end().find('form')[0].reset();
            // clear previous validation
            $confirmForm.validationEngine('hideAll');
            for (var key in caseMain) {
                if (key == 'caseDetails') {
                    var $table = $caseModal.find('table#detailTable');
                    $table.find('tr:not(:first)').remove();
                    var details = caseMain[key];
                    $.each(details, function(index, detail) {
                        var $tr = $('<tr>');
                        $tr.append($('<td>', { text: codeTypeFormatter(detail['caseStatus']), 'class': 'text-center' }));
                        $tr.append($('<td>', { text: dateFormatter(detail['updateDatetime']), 'class': 'text-center' }));
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
            // TODO
            console.log('query fail');
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
    
    $('#btnConfirm').on('click', function() {
        if ($confirmForm.validationEngine('validate')) {
            $.ajax({
                url: url('/apply/confirm'),
                type: 'post',
                data: $.extend({}, $confirmForm.serializeObject(), {
                    caseId: _caseId
                })
            })
            .done(function(msg) {
                if (msg.code == 0) {
                    hint('處理完畢!');
                } else {
                	error('系統異常!');
                }
            });
        }
        
        return false;
    });
});
</script>
<!-- hint Modal -->
<div class="modal fade" id="hintModal" tabindex="1" role="dialog" aria-labelledby="caseModalLabel" style="z-index: 1070;">
    <div class="modal-dialog" role="document">
        <div class="alert alert-success alert-dismissible fade in collapse" role="alert">
            <svg class="glyph stroked checkmark"><use xlink:href="#stroked-checkmark"></use></svg> <span class="msg"></span> <a href="#" class="close pull-right" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span></a>
        </div>
        <div class="alert alert-danger alert-dismissible fade in collapse" role="alert">
            <svg class="glyph stroked cancel"><use xlink:href="#stroked-cancel"></use></svg> <span class="msg"></span> <a href="#" class="close pull-right" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span></a>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">申請總覽</div>
            <table id="caseTable">
                <thead>
                    <tr>
                        <th data-field="caseId" data-align="center">編號</th>
                        <th data-field="updateDatetime" data-formatter="dateFormatter" data-align="center">更新時間</th>
                        <th data-field="caseStatus" data-formatter="codeTypeFormatter" data-align="center">狀態</th>
                        <th data-field="dept" data-formatter="codeTypeFormatter" data-align="center">部門</th>
                        <th data-field="unit" data-formatter="codeTypeFormatter" data-align="center">單位</th>
                        <th data-field="projectCode" data-formatter="projFormatter" data-align="center">需求人力專案</th>
                        <th data-field="hrmRole" data-formatter="codeTypeFormatter" data-align="center">需求人力角色</th>
                        <th data-field="hrmType" data-formatter="codeTypeFormatter" data-align="center">需求人員類別</th>
                        <th data-field="requiredCount" data-align="center">需求人數</th>
                        <th data-field="skill">Skill</th>
                        <th data-field="requiredBeginDate" data-align="center">人力需求起日</th>
                        <th data-field="requiredEndDate" data-align="center">人力需求迄日</th>
                        <th data-field="reason">增補原因</th>
                        <th data-field="note">備註</th>
                        <th data-field="applier" data-align="center">申請人</th>
                        <th data-formatter="btnFormatter"></th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
</div><!--/.row-->
<!-- Modal -->
<div class="modal fade" id="caseModal" tabindex="-1" role="dialog" aria-labelledby="caseModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">編號 - <span></span></h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body tabs">
                        <ul class="nav nav-pills">
                            <li class="active"><a href="#pilltab1" data-toggle="tab">申請資料</a></li>
                            <li><a href="#pilltab2" data-toggle="tab">狀態歷程</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane fade in active" id="pilltab1">
                                <table class="table editTable">
                                    <tr>
                                        <td class="col-md-4">編號</td>
                                        <td class="col-md-8"><div data-field="caseId"></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">更新時間</td>
                                        <td class="col-md-8"><div data-field="updateDatetime" data-formatter="dateFormatter"></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">狀態</td>
                                        <td class="col-md-8"><div data-field="caseStatus" data-formatter="codeTypeFormatter" ></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">部門</td>
                                        <td class="col-md-8"><div data-field="dept" data-formatter="codeTypeFormatter" ></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">單位</td>
                                        <td class="col-md-8"><div data-field="unit" data-formatter="codeTypeFormatter" ></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">需求人力專案</td>
                                        <td class="col-md-8"><div data-field="projectCode" data-formatter="projFormatter"></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">需求人力角色</td>
                                        <td class="col-md-8"><div data-field="hrmRole" data-formatter="codeTypeFormatter" ></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">需求人員類別</td>
                                        <td class="col-md-8"><div data-field="hrmType" data-formatter="codeTypeFormatter" ></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">需求人數</td>
                                        <td class="col-md-8"><div data-field="requiredCount"></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">Skill</td>
                                        <td class="col-md-8"><div data-field="skill"></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">人力需求起日</td>
                                        <td class="col-md-8"><div data-field="requiredBeginDate"></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">人力需求迄日</td>
                                        <td class="col-md-8"><div data-field="requiredEndDate"></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">增補原因</td>
                                        <td class="col-md-8"><div data-field="reason"></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">備註</td>
                                        <td class="col-md-8"><div data-field="note"></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">申請人</td>
                                        <td class="col-md-8"><div data-field="applier"></div></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-4">處理結果與說明</td>
                                        <td class="col-md-8 radio">
                                            <form id="confirmForm">
                                                <div class="form-group">
                                                    <label style="margin-right: 1em;">
                                                        <input type="radio" name="confirm" value="Y" class="validate[required]">同意
                                                    </label>
                                                    <label>
                                                        <input id="confirm_N" type="radio" name="confirm" value="N" class="validate[required]">拒絕
                                                    </label>
                                                    <textarea id="msgDetail" name="msgDetail" class="form-control validate[condRequired[confirm_N]]" style="display: none; margin-top: 0.5em;" rows="3"></textarea>
                                                </div>
                                                <button id="btnConfirm" type="button" class="btn btn-primary"><span class="p-r-5 glyphicon glyphicon-ok"></span>確認</button>
                                            </form>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tab-pane fade" id="pilltab2">
                                <table id="detailTable" class="table editTable">
                                    <tr>
                                        <th class="col-md-2 text-center">狀態</th>
                                        <th class="col-md-3 text-center">更新時間</th>
                                        <th class="col-md-6 text-center">訊息內容</th>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div><!--/.panel-->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><span class="p-r-5 glyphicon glyphicon-remove"></span>Close</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>