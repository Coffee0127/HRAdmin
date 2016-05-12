<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.file"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><tiles:insertAttribute name="title" /></title>
<%@ include file="/WEB-INF/pages/common/meta.file"%>
</head>
<body>
    <%-- Header --%>
    <tiles:insertAttribute name="header" />
<%--     <jsp:include page="/WEB-INF/pages/layout/header.jsp" /> --%>

    <%-- Menu --%>
    <tiles:insertAttribute name="menu" />
<%--     <jsp:include page="/WEB-INF/pages/layout/menu.jsp" /> --%>

    <div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
                <li class="active">Icons</li>
            </ol>
        </div><!--/.row-->

        <%-- Content --%>
        <tiles:insertAttribute name="content" />
    </div><!--/.main-->
</body>
</html>