<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
$(function() {
    var pathname = location.pathname;
    var $ul = $('a[href="' + pathname + '"]').addClass('active').parents('ul.children');
    if ($ul) {
        $ul.prev('a.collapsed').find('svg use').attr('xlink:href', '#stroked-chevron-down')
            .end();
        $ul.collapse('show');
    }

    $('a.collapse').on('click', function() {
    	// toggle current collapse icon
        var $use = $(this).find('svg use');
        if ($(this).hasClass('collapsed')) {
            $use.attr('xlink:href', '#stroked-chevron-down');
        } else {
            $use.attr('xlink:href', '#stroked-chevron-right');
        }
        // hide other collapse
        $('ul.collapse').collapse('hide');
        $('a.collapse').not(this).find('svg use').attr('xlink:href', '#stroked-chevron-right');
    });
});
</script>
<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
    <form role="search">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Search">
        </div>
    </form>
    <ul class="nav menu">
        <c:forEach var="menu" items="${rootMenu}">
            <c:choose>
                <c:when test="${empty menu.subMenuItems}">
                    <li><a href="${ctxPath}${menu.url}"><svg class="glyph"><use xlink:href="${menu.icon}"></use></svg>${menu.desc}</a></li>
                </c:when>
                <c:otherwise>
                    <li class="parent">
                        <a class="collapse collapsed" data-toggle="collapse" href="${menu.url}">
                            <svg class="glyph"><use xlink:href="#stroked-chevron-right"></use></svg>${menu.desc}
                        </a>
                        <ul class="children collapse" id="${fn:replace(menu.url, '#', '')}">
                            <c:forEach var="subMenu" items="${menu.subMenuItems}">
                                <li><a href="${ctxPath}${subMenu.url}"><svg class="glyph"><use xlink:href="${subMenu.icon}"></use></svg>${subMenu.desc}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>

    <%--
    <ul class="nav menu">
        <li><a href="${ctxPath}/"><svg class="glyph stroked dashboard-dial"><use xlink:href="#stroked-dashboard-dial"></use></svg>首頁</a></li>
        <li class="parent">
            <a class="collapse collapsed" data-toggle="collapse" href="#headcount-child">
                <svg class="glyph"><use xlink:href="#stroked-chevron-right"></use></svg>員額申請
            </a>
            <ul class="children collapse" id="headcount-child">
                <li><a href="${ctxPath}/headcount/view"><svg class="glyph"><use xlink:href="#stroked-table"></use></svg>申請總覽</a></li>
                <li><a href="${ctxPath}/headcount/apply"><svg class="glyph"><use xlink:href="#stroked-pencil"></use></svg>提出申請</a></li>
            </ul>
        </li>
        <li class="parent">
            <a class="collapse collapsed" data-toggle="collapse" href="#cfg-child">
                <svg class="glyph"><use xlink:href="#stroked-chevron-right"></use></svg>系統管理
            </a>
            <ul class="children collapse" id="cfg-child">
                <li><a href="#"><svg class="glyph"><use xlink:href="#stroked-gear"></use></svg>參數設定</a></li>
                <li><a href="${ctxPath}/users/view"><svg class="glyph"><use xlink:href="#stroked-male-user"></use></svg>使用者管理</a></li>
            </ul>
        </li>
    </ul>
    --%>
        <%--
        <li><a href="widgets.html"><svg class="glyph stroked calendar"><use xlink:href="#stroked-calendar"></use></svg> Widgets</a></li>
        <li><a href="charts.html"><svg class="glyph stroked line-graph"><use xlink:href="#stroked-line-graph"></use></svg> Charts</a></li>
        <li><a href="tables.html"><svg class="glyph stroked table"><use xlink:href="#stroked-table"></use></svg> Tables</a></li>
        <li><a href="forms.html"><svg class="glyph stroked pencil"><use xlink:href="#stroked-pencil"></use></svg> Forms</a></li>
        <li><a href="panels.html"><svg class="glyph stroked app-window"><use xlink:href="#stroked-app-window"></use></svg> Alerts &amp; Panels</a></li>
        <li><a href="icons.html"><svg class="glyph stroked star"><use xlink:href="#stroked-star"></use></svg> Icons</a></li>
        <li class="parent">
            <a data-toggle="collapse" href="#sub-item-1">
                <svg class="glyph stroked chevron-down"><use xlink:href="#stroked-chevron-down"></use></svg> Dropdown
            </a>
            <ul class="children collapse" id="sub-item-1">
                <li>
                    <a class="" href="#">
                        <svg class="glyph stroked chevron-right"><use xlink:href="#stroked-chevron-right"></use></svg> Sub Item 1
                    </a>
                </li>
                <li>
                    <a class="" href="#">
                        <svg class="glyph stroked chevron-right"><use xlink:href="#stroked-chevron-right"></use></svg> Sub Item 2
                    </a>
                </li>
                <li>
                    <a class="" href="#">
                        <svg class="glyph stroked chevron-right"><use xlink:href="#stroked-chevron-right"></use></svg> Sub Item 3
                    </a>
                </li>
            </ul>
        </li>
        <li role="presentation" class="divider"></li>
        <li><a href="${ctxPath}/loginPage"><svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg> Login Page</a></li>
        --%>
</div><!--/.sidebar-->
