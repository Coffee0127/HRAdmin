<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login Page</title>
<link href="${ctxPath}/resources/lib/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctxPath}/resources/lib/css/styles.css" rel="stylesheet">
<!--[if lt IE 9]>
<script src="${ctxPath}/resources/lib/js/html5shiv.js"></script>
<script src="${ctxPath}/resources/lib/js/respond.min.js"></script>
<![endif]-->
</head>

<body>
    <div class="row">
        <div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">Log in</div>
                <div class="panel-body">
                    <form role="form">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="AD Employee Number" name="empno" type="text" autofocus="autofocus" />
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="AD Employee Password" name="password" type="password" value="" />
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input name="remember" type="checkbox" value="Remember Me" />Remember Me
                                </label>
                            </div>
                            <a href="index.html" class="btn btn-primary">登入</a>
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