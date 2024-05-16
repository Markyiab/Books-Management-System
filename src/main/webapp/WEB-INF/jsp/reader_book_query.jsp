<%@ page import="com.book.domain.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>全部图书信息</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/main.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js"></script>

</head>
<body>
<nav class="navbar navbar-default" role="navigation" style="background-color:#fff">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand " href="reader_main.html"><p class="text-primary">我的图书馆</p></a>
        </div>
        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <ul class="nav navbar-nav navbar-left">
                <li class="active">
                    <a href="reader_querybook.html">
                        图书查询
                    </a>
                </li>
                <li>
                    <a href="reader_info.html">
                        个人信息
                    </a>
                </li>
                <li>
                    <a href="mylend.html">
                        我的借还
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="reader_info.html"><span class="glyphicon glyphicon-user"></span>&nbsp;${readercard.name}，已登录</a></li>
                <li><a href="login.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;退出</a></li>
            </ul>
        </div>
    </div>
</nav>


<div class="query-table">
    <form method="post" action="reader_querybook_do.html" class="form-inline" id="searchform">
        <div class="input-group">
            <input type="text" placeholder="输入书名" class="my-input" name="name" value="${queryBook.name}">
            <input type="text" placeholder="请输入作者" class="my-input" name="author" value="${queryBook.author}">
            <input type="text" placeholder="请输入出版社" class="my-input" name="publish" value="${queryBook.publish}">
            <select name="classId" class="my-select">
                <option value="0">请选择分类</option>
                <c:forEach items="${classInfos}" var="m">
                    <option value="${m.key}"
                        <c:if test="${queryBook.classId eq m.key}">
                            selected="selected"
                        </c:if>
                    >${m.value}
                    </option>
                </c:forEach>
            </select>
            <select name="language" class="my-select">
                <option value="">请选择语言</option>
                <c:forEach items="${languages}" var="lang">
                    <option value="${lang}"
                        <c:if test="${queryBook.language eq lang}">
                            selected="selected"
                        </c:if>
                    >${lang}
                    </option>
                </c:forEach>
            </select>
            <select name="orderBy" class="my-select">
                <option value="">请选择排序方式</option>
                <c:forEach items="${orderBy}" var="order">
                    <option value="${order.key}"
                        <c:if test="${queryBook.orderBy eq order.key}">
                            selected="selected"
                        </c:if>
                    >${order.value}
                    </option>
                </c:forEach>
            </select>${queryBook.order}
            <input type="radio" name="order" value="desc" <c:if test="${queryBook.order ne 'asc'}">checked</c:if>>降序</input>
            <input type="radio" name="order" value="asc" <c:if test="${queryBook.order eq 'asc'}">checked</c:if>>升序</input>
            <span class="input-group-btn">
                <input type="submit" value="搜索" class="btn btn-default">
            </span>
        </div>
    </form>
</div>
<div style="position: relative;top: 10%">
    <c:if test="${!empty succ}">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
                ${succ}
        </div>
    </c:if>
    <c:if test="${!empty error}">
        <div class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
                ${error}
        </div>
    </c:if>
</div>
<c:if test="${!empty books}">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                查询结果：
            </h3>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>书名</th>
                    <th>作者</th>
                    <th>出版社</th>
                    <th>ISBN</th>
                    <th>价格</th>
                    <th>状态</th>
                    <th>详情</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${books}" var="book">
                    <tr>
                        <td><c:out value="${book.name}"/></td>
                        <td><c:out value="${book.author}"/></td>
                        <td><c:out value="${book.publish}"/></td>
                        <td><c:out value="${book.isbn}"/></td>
                        <td><c:out value="${book.price}"/></td>
                        <c:if test="${book.state eq 1}">
                            <td>在馆</td>
                        </c:if>
                        <c:if test="${book.state eq 0}">
                            <td>借出</td>
                        </c:if>
                        <td>
                            <a href="readerbookdetail.html?bookId=<c:out value="${book.bookId}"/>">
                                <button type="button" class="btn btn-success btn-xs">详情</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>

</body>
</html>
