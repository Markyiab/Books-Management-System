<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${readercard.name}的主页</title>
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
<div class="col-xs-5 col-md-offset-3">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                信息修改
            </h3>
        </div>
        <div class="panel-body">
            <form action="reader_edit_do_r.html" method="post" id="edit">

                <div class="input-group">
                    <span class="input-group-addon">读者证号</span>
                    <input type="text" readonly="readonly" class="form-control" name="readerId" id="readerId" value="${readerinfo.readerId}">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">姓名</span>
                    <input type="text" class="form-control" name="name" id="name" value="${readerinfo.name}">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">性别</span>
                    <input type="text" class="form-control" name="sex" id="sex" value="${readerinfo.sex}">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">生日</span>
                    <input type="text" class="form-control" name="birth" id="birth" value="${readerinfo.birth}">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">地址</span>
                    <input type="text" class="form-control" name="address" id="address" value="${readerinfo.address}">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">电话</span>
                    <input type="text" class="form-control" name="telcode" id="telcode" value="${readerinfo.telcode}">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">民族</span>
                    <input type="text" class="form-control" name="nation" id="nation" value="${readerinfo.nation}">
                </div>
                <br/>
                <input type="submit" value="确定" class="btn btn-success btn-sm" class="text-left">
                <script>

                    $("#edit").submit(function () {
                        if ($("#name").val() == '' || $("#sex").val() == '' || $("#birth").val() == '' || $("#address").val() == ''
                            || $("#telcode").val() == '' || $("#nation").val() == '') {
                            alert("请填入完整个人信息！");
                            return false;
                        }
                    })
                </script>
            </form>
        </div>
    </div>
</div>

</body>
</html>
