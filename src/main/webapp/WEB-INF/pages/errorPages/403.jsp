<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Access Denined</title>
<%@ include file="/WEB-INF/pages/common/meta.file" %>
<script type="text/javascript">
$(function() {
    var cd = 1;
    var url = $('a.indexPage').attr('href');
    var cdSpan = $('.cd');
    var timeToRedirect = parseInt($(cdSpan).html());
    setInterval(function() {
        $(cdSpan).html(timeToRedirect - cd++);
    }, 1000);

    setTimeout(function() {
        location.href = url;
    }, timeToRedirect * 1000);
});
</script>
<!-- content -->
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-lg-12">
            <div class="centering text-center error-container">
                <div class="text-center">
                    <h2 class="without-margin">Don't worry. It's <span class="text-warning"><big>403</big></span> error only.</h2>
                    <h4 class="text-warning">Access denied</h4>
                    <h4>
                        <span class="cd">${timeToRedirect}</span>
                        <span> seconds left to redirect to <a href="${ctxPath}${defaultRedirectUrl}" class="indexPage">index page</a></span>
                    </h4>
                </div>
            </div>
        </div>
    </div>
</div>
