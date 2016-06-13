<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<link href="${ctxPath}/resources/lib/css/bootstrap-table.css" rel="stylesheet" />
<script src="${ctxPath}/resources/lib/js/bootstrap-table.js"></script>
<script src="${ctxPath}/resources/lib/js/bootstrap-table-zh-TW.min.js"></script>
<link href="${ctxPath}/resources/lib/css/validationEngine.jquery.css" rel="stylesheet" />
<script src="${ctxPath}/resources/lib/js/jquery.validationEngine-zh_TW.js"></script>
<script src="${ctxPath}/resources/lib/js/jquery.validationEngine.js"></script>
<script src="${ctxPath}/resources/js/headcount/view.js"></script>
<style>
.editTable {
    border-bottom: none;
}
.editTable>tbody>tr:first-child>td {
    border-top: none;
}
#dataTable>tbody>tr>td, .editTable>tbody>tr>td {
    vertical-align: middle;
}
#dataTable>tbody>tr.edited>td {
    background: #d9edf7;
    border-color: #d9edf7;
}

.editTable textarea {
    resize: vertical;
}
</style>
<body>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">申請總覽</div>
            <div class="query-cond-wrapper">
                <span class="query-cond-heading">查詢條件</span>
                <div class="query-cond-body">
                    <form id="queryCondForm" class="form-horizontal">
                        <div class="form-group"></div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">案件編號：</label>
                            <div class="col-lg-2">
                                <input id="caseId" name="caseId" type="text" class="form-control" placeholder="案件編號" />
                            </div>
                            <label class="col-lg-2 control-label">案件時間：</label>
                            <div class="col-lg-5 form-inline">
                                <div class="input-group date date-picker" style="width: 36%;">
                                    <input id="beginDatetime" name="beginDatetime" type="text" class="form-control" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="起始時間" />
                                    <div class="input-group-addon icon-date-picker">
                                        <span class="glyphicon glyphicon glyphicon-calendar"></span>
                                    </div>
                                </div>
                                <div class="form-control text-center" style="border: none;">~</div>
                                <div class="input-group date date-picker" style="width: 36%;">
                                    <input id="endDateTime" name="endDateTime" type="text" class="form-control date-picker" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="結束時間" />
                                    <div class="input-group-addon icon-date-picker">
                                        <span class="glyphicon glyphicon glyphicon-calendar"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">狀態：</label>
                            <div class="col-lg-2">
                                <select id="caseStatus" name="caseStatus" class="form-control">
                                    <option value="">請選擇</option>
                                </select>
                            </div>
                            <label class="col-lg-2 control-label">部門：</label>
                            <div class="col-lg-2">
                                <select id="dept" name="dept" class="form-control">
                                    <option value="">請選擇</option>
                                </select>
                            </div>
                            <label class="col-lg-1 control-label">單位：</label>
                            <div class="col-lg-2">
                                <select id="unit" name="unit" class="form-control">
                                    <option value="">請選擇</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">需求人力角色：</label>
                            <div class="col-lg-2">
                                <select id="hrmRole" name="hrmRole" class="form-control">
                                    <option value="">請選擇</option>
                                </select>
                            </div>
                            <label class="col-lg-2 control-label">需求人員類別：</label>
                            <div class="col-lg-2">
                                <select id="hrmType" name="hrmType" class="form-control">
                                    <option value="">請選擇</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">人力需求起日：</label>
                            <div class="col-lg-2">
                                <div class="input-group date date-picker">
                                    <input id="requiredBeginDate" name="requiredBeginDate" type="text" class="form-control" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="起始時間" />
                                    <div class="input-group-addon icon-date-picker">
                                        <span class="glyphicon glyphicon glyphicon-calendar"></span>
                                    </div>
                                </div>
                            </div>
                            <label class="col-lg-2 control-label text-nowrap">人力需求迄日：</label>
                            <div class="col-lg-2">
                                <div class="input-group date date-picker">
                                    <input id="requiredEndDate" name="requiredEndDate" type="text" class="form-control" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="結束時間" />
                                    <div class="input-group-addon icon-date-picker">
                                        <span class="glyphicon glyphicon glyphicon-calendar"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-2 col-lg-4">
                                <button id="btnQuery" class="btn btn-info"><span class="glyphicon glyphicon-search"></span></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <table id="dataTable">
                <thead>
                    <tr>
                        <th data-sortable="true" data-field="caseId" data-align="center" data-class="text-nowrap">案件編號</th>
                        <th data-sortable="true" data-field="updateDatetime" data-formatter="dateFormatter" data-align="center">更新時間</th>
                        <th data-sortable="true" data-field="caseStatus" data-formatter="codeTypeFormatter" data-align="center" data-class="text-nowrap">狀態</th>
                        <th data-sortable="true" data-field="preCaseStatus" data-formatter="codeTypeFormatter" data-align="center" data-class="text-nowrap">前次狀態</th>
                        <th data-sortable="true" data-field="dept" data-formatter="codeTypeFormatter" data-align="center" data-class="text-nowrap">部門</th>
                        <th data-sortable="true" data-field="unit" data-formatter="codeTypeFormatter" data-align="center" data-class="text-nowrap">單位</th>
                        <th data-sortable="true" data-field="projectCode" data-formatter="projFormatter" data-align="center">需求人力專案</th>
                        <th data-sortable="true" data-field="hrmRole" data-formatter="codeTypeFormatter" data-align="center">需求人力角色</th>
                        <th data-sortable="true" data-field="hrmType" data-formatter="codeTypeFormatter" data-align="center">需求人員類別</th>
                        <th data-sortable="true" data-field="requiredCount" data-align="center">需求人數</th>
                        <th data-sortable="true" data-field="requiredSkill">Skill</th>
                        <th data-sortable="true" data-field="requiredBeginDate" data-align="center">人力需求起日</th>
                        <th data-sortable="true" data-field="requiredEndDate" data-align="center">人力需求迄日</th>
                        <th data-sortable="true" data-field="reason">增補原因</th>
                        <th data-sortable="true" data-field="note">備註</th>
                        <th data-sortable="true" data-field="applier" data-align="center">申請人</th>
                        <th data-formatter="btnFormatter" data-align="center">細節</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
</div><!--/.row-->
<!-- Modal -->
<div id="caseModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="caseModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">案件編號 - <span></span></h4>
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
                                        <td class="col-md-4">案件編號</td>
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
                                        <td class="col-md-4">前次狀態</td>
                                        <td class="col-md-8"><div data-field="preCaseStatus" data-formatter="codeTypeFormatter" ></div></td>
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
                                        <td class="col-md-8"><div data-field="requiredSkill"></div></td>
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
                                    <tr style="display: none;">
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
                                                    <textarea id="msgDetail" name="msgDetail" class="form-control validate[condRequired[confirm_N]]" style="display: none; margin-top: 0.5em;" rows="3" placeholder="請填寫拒絕原因"></textarea>
                                                </div>
                                                <button id="btnConfirm" type="button" class="btn btn-primary"><span class="p-r-5 glyphicon glyphicon-ok"></span>確認</button>
                                            </form>
                                        </td>
                                    </tr>
                                    <tr style="display: none;">
                                        <td class="col-md-4">人資處理紀錄</td>
                                        <td class="col-md-8">
                                            <form id="hrConfirmForm">
                                                <div class="form-group">
                                                    <textarea id="processMsgDetail" name="processMsgDetail" class="form-control validate[required]" rows="3" placeholder="請填寫紀錄"></textarea>
                                                </div>
                                                <button id="btnHRConfirm" type="button" class="btn btn-primary"><span class="p-r-5 glyphicon glyphicon-ok"></span>確認</button>
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
                <button type="button" class="btn btn-default" data-dismiss="modal"><span class="p-r-5 glyphicon glyphicon-remove"></span>關閉</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>