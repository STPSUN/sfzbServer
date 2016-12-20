<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
session.setAttribute("hasCode",false);
%>
<!DOCTYPE HTML >
<html lang="zh-CN">
  <head>
    <meta charset="utf-8;">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>蝙蝠众包后台管理</title>
    <link href="<%= basePath%>/pages/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%= basePath%>/pages/assets/css/carousel.css" rel="stylesheet">
    <%--  <link href="<%= basePath%>/pages/assets/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="<%= basePath%>/pages/assets/css/theme.css" rel="stylesheet"> --%>
<style>
.user-login .login-info .login-form li .ico-1 {
    background: #fff url(<%= basePath%>/pages/assets/images/icon_user.png) no-repeat;
}
.user-login .login-info .login-form li .ico-2 {
    background: #fff url(<%= basePath%>/pages/assets/images/icon_passw.png) no-repeat;
}
.user-login .login-info .login-form li{
	list-style:none;
	margin-top: 10px;
    zoom: 1;
}
.user-login .login-info .login-form li input.login-ipt {
    margin: 0;
    height: 35px;
    border: 0;
    padding: 0 10px 0 45px;
	}


.login-info{
	background:url(<%= basePath%>/pages/assets/images/black_opac.png);
	width: 244px;
    padding: 20px 0 20px 20px;
    overflow: hidden;
    zoom: 1;
}
.login-info .login-tit {
    font-size: 20px;
    height: 35px;
    color: #fff;
}
.login-info .login-form li input.ipt-s {
    width: 110px;
    padding: 0 10px;
	}
.login-info .login-form {
    margin: 0;
    padding: 0;
	}
	.login .code-pic {
    height: 35px;
    width: 80px;
    margin-left: 5px;
    background: #999;
	}
	.login-info .login-form li .login-btn {
    width: 203px;
    height: 38px;
    background: #008bed;
    border: 1px solid #2aa6fd;
    display: block;
    color: #fff;
    line-height: 35px;
    text-align: center;
    margin-top: 10px;
    zoom: 1;
    letter-spacing: 10px;
    font-size: 18px;}
.tips{
width: 244px;
    padding: 12px;
    margin-top: 5px;
	background:url(<%= basePath%>/pages/assets/images/white_opac.png)
}
.logo{
	width: 40px;
	height:40px;
    padding: 0px;
    margin-top: -10px;
	background:url(<%= basePath%>/pages/assets/images/logo.png)
}
#code{
padding: 0 10px;
}
.tips span {
    display: block;
    font-size: 16px;
    font-weight: 700;
	}

div{
	    font-family: 'Segoe UI_', 'microsoft yahei', 'Open Sans', 'microsoft yahei', SimSun, verdana, arial, sans-serif, 微软雅黑;
    font-weight: 400;
    font-style: normal;
}
</style>
  </head>
	
  <body role="document">
  
  	<div  class="logintop">
  		<div class="center">
			<h1>
				<img class="logo" src="<%= basePath%>/pages/assets/images/logo.png" />
				<span class="blue">蝙蝠众包</span>
			</h1>
			<h4 class="blue">&copy; —运营管理后台</h4>
		</div>
  	</div>
    <div class="container theme-showcase" role="main" style="width:100%;max-width:100%;">
      <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="margin-bottom:0px;height:480px;">
        <ol class="carousel-indicators">
          <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
          <li data-target="#carousel-example-generic" data-slide-to="1"></li>
          <li data-target="#carousel-example-generic" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner" role="listbox">
          <div class="item active">
            <img  alt="First slide" src="<%= basePath%>/pages/assets/images/11401.jpg">
          </div>
          <div class="item">
            <img  alt="Second slide" src="<%= basePath%>/pages/assets/images/11402.jpg">
          </div>
          <div class="item">
            <img  alt="Third slide" src="<%= basePath%>/pages/assets/images/11403.jpg">
          </div>
        </div>
      </div>

    </div>
	<div  id="login_form"  class="wrap-980 login-pos" style="width:274px;position: absolute;right:300px;top:120px;" >
                <div class="user-login">
                    <div class="login-info">
                        <form class="form-horizontal" role="form" id="find_form" onsubmit="return false">
                            <div class="login-tit">用户登录<span id="warn_tip" style="margin-left: 20px;font-size: 13px;font-family: '微软雅黑'; color: rgb(202, 69, 69);"></span></div>

                            <!--  错误提示 begin  -->
                            <div  id="msg" style="color: red;">请输入用户信息！</div>
                            <!--  错误提示 end  -->
                            <ul class="login-form">
                                <li>
                                    <input id="userName" type="text" style="font-family: '微软雅黑'" placeholder="请输入账号" maxlength="30" class="login-ipt ipt-l ico-1" />
                                </li>
                                <li>
                                    <input  id="password" type="password" style="font-family: '微软雅黑'"  placeholder="请输入密码" maxlength="20" class="login-ipt ipt-l ico-2" />
                                </li>
                                <li class="clearfix" id="liCode" style="display:none">
                                    <input id="code"  style="font-family: '微软雅黑'"  type="text" placeholder="验证码" class="login-ipt ipt-s place-left" >
                                    <span class="code-pic place-left" style="background: white;">
                                     <img id="randImage" onclick="refreshImage();"
																src="<%=path%>/admin/image"
																style="cursor:pointer; height:38px;width:100px; margin-top:-3px;"
																border=0 height="28" />   
                                     </span>
                                </li>
                                <li>
                                	<button id="lg" class="login-btn">登录</button>
                                </li>
                            </ul>
                        </form>
                    </div>
                    <div class="tips" style="color: #000000;"><span>温馨提示：</span>平台在IE9及以上版本、Chrome谷歌浏览器（推荐）、360浏览器的极速模式下使用最优。</div>
                </div>
            </div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<%= basePath%>/pages/assets/js/jquery-2.0.3.min.js"></script>
    <script src="<%= basePath%>/pages/assets/js/bootstrap.min.js"></script>
    <script>
   
	//登录按钮
	$(function() {
		 var ctp="<%= basePath%>";
		 var hasCode=false;
		 refreshImage();
		$("#lg").click(function() {
		if ($.trim($("#userName").val()) == "") {
			//$("#username").focus();
			$("#msg").addClass("text-danger").text("用户账号不能为空");
			return;
		}
		if ($.trim($("#password").val()) == "") {
			$("#msg").addClass("text-danger").text("用户密码不能为空");
			return;
		}
	
		if (hasCode&&$.trim($("#code").val()) == "") {
			$("#msg").addClass("text-danger").text("验证码不能为空");
			return;
		}
		//登录操作
		$.ajax({
			type : "POST",
			url : ctp + "/admin/login",
			data : {
				"userName" : $("#userName").val(),
				"password" : hex_md5($("#password").val()), //这里加密一次
				"captcha" : $("#code").val()
			},
			dataType : "json",
			success : function(data) {
				refreshImage();
				error_num=data.error_num;
				if (data.restcode == "-1") {
					hasCode=data.hasCode;
					$("#msg").addClass("text-danger").text(data.msg);
					if(hasCode){
						$("#liCode").css("display","block");
						session.setAttribute("hasCode", true);
					}
				} else {
					$("#loginModel").modal("hide");
					window.location.href = ctp + "/admin/";
				}
			}
		});
	});
	});
	function refreshImage() {
		var randImage = document.getElementById("randImage");
		var changeURL = ctp + "/admin/image?date=" + new Date() + "";
		randImage.src = changeURL;
	}
    </script>
  </body>
</html>
