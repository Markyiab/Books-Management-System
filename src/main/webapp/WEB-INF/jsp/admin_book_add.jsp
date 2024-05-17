<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>图书信息添加</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/main.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js"></script>

</head>
<body>
<nav style="background-color: #fff" class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand" href="admin_main.html">图书管理系统</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        图书管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="allbooks.html">全部图书</a></li>
                        <li class="divider"></li>
                        <li><a href="book_add.html">增加图书</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        读者管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="allreaders.html">全部读者</a></li>
                        <li class="divider"></li>
                        <li><a href="reader_add.html">增加读者</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        借还管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="lendlist.html">借还日志</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        维护管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="nation_list.html">民族管理</a></li>
                        <li class="divider"></li>
                        <li><a href="class_list.html">分类管理</a></li>
                    </ul>
                </li>
                <li>
                    <a href="admin_repasswd.html">
                        密码修改
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="login.html"><span class="glyphicon glyphicon-user"></span>&nbsp;${admin.adminId}，已登录</a></li>
                <li><a href="logout.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;退出</a></li>
            </ul>
        </div>
    </div>
</nav>

<div style="position: relative;top: 10%;width: 80%;margin-left: 10%">
    <form action="book_add_do.html" method="post" id="addbook">
        <div class="input-group">
            <span class="input-group-addon">书名</span>
            <input type="text" class="form-control" name="name" id="name" value="${detail.name}">
        </div>
        <div class="input-group">
            <span class="input-group-addon">作者</span>
            <input type="text" class="form-control" name="author" id="author" value="${detail.author}">
        </div>
        <div class="input-group">
            <span class="input-group-addon">出版社</span>
            <input type="text" class="form-control" name="publish" id="publish" value="${detail.publish}">
        </div>
        <div class="input-group">
            <span class="input-group-addon">ISBN</span>
            <input type="text" class="form-control" name="isbn" id="isbn" value="${detail.isbn}">
        </div>
        <div class="input-group">
            <span class="input-group-addon">简介</span>
            <textarea rows="3" class="form-control" name="introduction" id="introduction" value="${detail.introduction}"></textarea>
        </div>
        <div class="input-group">
            <span class="input-group-addon">语言</span>
            <input type="text" class="form-control" name="language" id="language" value="${detail.language}">
        </div>
        <div class="input-group">
            <span class="input-group-addon">价格</span>
            <input type="text" class="form-control" name="price" id="price" value="${detail.price}">
        </div>
        <div class="input-group">
            <span class="input-group-addon">出版日期</span>
            <input type="text" class="form-control" name="pubdate" id="pubdate" value="${detail.pubdate}">
        </div>
        <div class="input-group">
            <span class="input-group-addon">分类号</span>
            <select name="classId" class="edit-select">
                <c:forEach items="${classMap}" var="cla">
                    <option value="${cla.key}">${cla.value}</option>
                </c:forEach>
            </select>
        </div>
        <input type="hidden" name="state" value="1">

        <input type="submit" value="添加" class="btn btn-success text-left">
        <script>

            $("#addbook").submit(function () {
                if ($("#name").val() == '' || $("#author").val() == '' || $("#publish").val() == ''
                    || $("#isbn").val() == '' || $("#introduction").val() == '' || $("#language").val() == ''
                    || $("#price").val() == '' || $("#pubdate").val() == '' || $("#classId").val() == '') {
                    alert("请填入完整图书信息！");
                    return false;
                }
            })
        </script>
    </form>

</div>

</body>
</html>
