<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>添加读者</title>
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

<div class="col-xs-6 col-md-offset-3" style="position: relative;top: 25%">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">添加读者</h3>
        </div>
        <div class="panel-body">
            <form action="reader_add_do.html" method="post" id="readeredit">
                <div class="input-group">
                    <span class="input-group-addon">借书证号</span>
                    <input type="text" class="form-control" name="readerId" id="readerId">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">姓名</span>
                    <input type="text" class="form-control" name="name" id="name">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">性别</span>
                    <input type="text" class="form-control" name="sex" id="sex">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">生日</span>
                    <input type="text" class="form-control" name="birth" id="birth">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">地址</span>
                    <input type="text" class="form-control" name="address" id="address">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">电话</span>
                    <input type="text" class="form-control" name="telcode" id="telcode">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">民族</span>
                    <select name="nation" class="edit-select">
                        <c:forEach items="${nationMap}" var="nat">
                            <option value="${nat.key}">${nat.value}</option>
                        </c:forEach>
                    </select>
                </div>
                <p style="text-align: right;color: red;position: absolute" id="info"></p><br/>
                <input type="button" value="添加" id="readaddBtn" class="btn btn-success text-left">
                <script>

                    $("#readaddBtn").click(function () {
                        var readerId = $("#readerId").val();
                        if ($("#readerId").val() == '' || $("#name").val() == '' || $("#sex").val() == '' || $("#birth").val() == ''
                            || $("#address").val() == '' || $("#telcode").val() == '') {
                            $("#info").text("提示：请填入完整读者信息！");
                            return;
                        }
                        $("#info").text("");
                        $.ajax({
                            type: "POST",
                            url: "/readerCheck",
                            data: {
                                readerId: readerId
                            },
                            dataType: "json",
                            success: function (data) {
                                if (data.success) {
                                    $("#readeredit").submit();
                                } else {
                                    $("#info").text(data.msg);
                                }
                            }
                        });
                    })
                </script>
            </form>
        </div>
    </div>

</div>

</body>
</html>
