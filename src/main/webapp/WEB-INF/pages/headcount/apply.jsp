<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="en">
<link href="${ctxPath}/resources/lib/css/validationEngine.jquery.css" rel="stylesheet" />
<script src="${ctxPath}/resources/lib/js/jquery.validationEngine-zh_TW.js"></script>
<script src="${ctxPath}/resources/lib/js/jquery.validationEngine.js"></script>
<style>
.form-group .col-md-4, .form-group .col-md-6 {
    padding-left: 0;
}
@media (max-width: 992px) {
    .form-group .col-md-4:not(:last-child), .form-group .col-md-6:not(:last-child) {
        padding-bottom: 15px;
    }
}
.required label:after {
    content: "*";
    color: red;
    padding-left: 3px;
    font-size: 16px;
    vertical-align: middle;
}
.p-r-5 {
    padding-right: 5px;
}
</style>
<body>
<script>
$(function() {
    var _units = sessionStorage.getItem('_units') && JSON.parse(sessionStorage.getItem('_units')) || {};

    var caseMain = JSON.parse('${caseMain}') || {};
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
        $('#dept').val(caseMain.dept).change();
        $('#unit').val(caseMain.unit);
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
        $('#hrmRole').val(caseMain.hrmRole);
        $.each(msg.hrmTypes, function(index, object) {
            var key = _.keys(object)[0];
            $('#hrmType').append(
                $('<option>', {
                    'value' : key,
                    'html' : object[key]
                })
            );
        });
        $('#hrmType').val(caseMain.hrmType);
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
                data: {
                    deptId: deptId
                }
            })
            .done(function(msg) {
                _units[deptId] = msg;
                appendUnits(msg);
            });
        } else {
            appendUnits(_units[deptId]);
        }
    });

    $('#applicationForm').validationEngine({ scroll: false });
    
    $('#btnSubmit').on('click', function() {
    	if ($('#applicationForm').validationEngine('validate')) {
            sessionStorage.setItem('_units', JSON.stringify(_units));
            $('#applicationForm').submit();
    	}
        return false;
    });
});
</script>
<form:form id="applicationForm" method="POST" action="${ctxPath}/headcount/submit" commandName="caseMain" autocomplete="false">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">提出申請</div>
                <div class="panel-body">
                    <div class="col-md-6">
                        <div class="form-group">
                            <div class="col-md-4">
                                <label>填寫人</label>
                                <div class="form-control"><%= com.bxf.hradmin.common.web.utils.UserUtils.getUser().getName() %></div>
                            </div>
                            <div class="col-md-4 required">
                                <label>部門</label>
                                <form:select path="dept" cssClass="form-control validate[required]">
                                    <form:option value="">請選擇</form:option>
                                </form:select>
                            </div>
                            <div class="col-md-4 required lastChild">
                                <label>單位</label>
                                <form:select path="unit" cssClass="form-control validate[required]">
                                    <form:option value="">請選擇</form:option>
                                </form:select>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="form-group required">
                            <div class="col-md-6">
                                <label>需求人力專案代號</label>
                                <form:input path="projectCode" cssClass="form-control validate[required]" maxlength="10" />
                            </div>
                            <div class="col-md-6">
                                <label>需求人力專案名稱</label>
                                <form:input path="projectName" cssClass="form-control validate[required]" maxlength="10" />
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="form-group required">
                            <div class="col-md-4">
                                <label>需求人力角色</label>
                                <form:select path="hrmRole" cssClass="form-control validate[required]">
                                    <form:option value="">請選擇</form:option>
                                </form:select>
                            </div>
                            <div class="col-md-4">
                                <label>需求人員類別</label>
                                <form:select path="hrmType" cssClass="form-control validate[required]">
                                    <form:option value="">請選擇</form:option>
                                </form:select>
                            </div>
                            <div class="col-md-4">
                                <label>人數</label>
                                <form:input path="requiredCount" type="number" cssClass="form-control validate[required]" min="1" max="100" />
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="form-group required">
                            <label>Skill</label>
                            <form:textarea path="requiredSkill" cssClass="form-control validate[required]" rows="3" />
                        </div>
                        <div class="form-group">
                            <div class="col-md-6 required">
                                <label>人力需求起日</label>
                                <form:input path="requiredBeginDate" type="date" cssClass="form-control validate[required]" />
                            </div>
                            <div class="col-md-6">
                                <label>人力需求訖日</label>
                                <form:input path="requiredEndDate" type="date" cssClass="form-control" />
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>增補原因</label>
                            <form:textarea path="reason" cssClass="form-control" rows="3" />
                        </div>
                        <div class="form-group">
                            <label>備註</label>
                            <form:textarea path="note" cssClass="form-control" rows="3" />
                        </div>

                        <button id="btnSubmit" class="btn btn-primary"><span class="p-r-5 glyphicon glyphicon-ok"></span>申請</button>
                        <button type="reset" class="btn btn-default"><span class="p-r-5 glyphicon glyphicon-refresh"></span>重填</button>
                    </div>
                </div>
            </div>
        </div><!-- /.col-->
    </div><!-- /.row -->
</form:form>
</body>
</html>