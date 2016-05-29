<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login Page</title>
<link rel="SHORTCUT ICON" href="${ctxPath}/resources/images/icon.png"/>
<link href="${ctxPath}/resources/lib/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctxPath}/resources/lib/css/styles.css" rel="stylesheet">
<!--[if lt IE 9]>
<script src="${ctxPath}/resources/lib/js/html5shiv.js"></script>
<script src="${ctxPath}/resources/lib/js/respond.min.js"></script>
<![endif]-->
<style>
.error {
    color: red
}
</style>
</head>
<body>
    <div>
        <div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">Log in</div>
                <div class="panel-body">
                    <form role="form" method="post" action="${ctxPath}/login" autocomplete="off">
                        <fieldset>
                            <div class="form-group">
                                <label>使用者帳號</label>
                                <input class="form-control" name="username" type="text" autofocus="autofocus" />
                            </div>
                            <div class="form-group">
                                <label>使用者密碼</label>
                                <input class="form-control" name="password" type="password" />
                            </div>
                            <c:if test="${not empty param.login_error}">
                                <div class="form-group error">The user name or password was incorrect.</div>
                            </c:if>
                            <button type="submit" class="btn btn-primary">登入</button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div><!-- /.col-->
    </div><!-- /.row -->

    <script src="${ctxPath}/resources/lib/js/jquery-1.11.1.min.js"></script>
    <script src="${ctxPath}/resources/lib/js/bootstrap.min.js"></script>
</body>
</html>