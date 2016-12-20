<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
	if (basePath.endsWith("/")) {
		basePath = basePath.substring(0, basePath.length() - 1);
	}
	String path = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + basePath;
	HashMap user = (HashMap) session.getAttribute("userInfo");
	String userName = "";
	String lastOutTime = "";
	if (user != null) {
		userName = user.get("admin_account").toString();
//		lastOutTime = user.get("last_out_time").toString().replace(".0","");
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<title>蝙蝠众包管理后台</title>
<style>
.in.modal-backdrop {
	opacity: 1;
	background: url(<%=path%>/pages/assets/images/bbg.jpg) top center no-repeat #2c477a;
}
#loginModel{
	top:115px;
	overflow-y:hidden;
	padding:85px 0 0 15px;
	
}
.username {
	padding: 6px 0 0 0;
	background: url(<%=path%>/pages/assets/images/user_n.jpg) no-repeat;
	width:254px;
	margin:0px 0;
	opacity: 1;
}
.password{
	padding: 6px 0 0 0;
    background: url(<%=path%>/pages/assets/images/pass_n.jpg) no-repeat;
    width: 254px;
    margin: 10px 0;
}
.code{
	padding: 0 0 20px 0;
    background: url(<%=path%>/pages/assets/images/code_n.jpg) no-repeat;
    width: 254px;
    overflow: hidden;
    position: relative;
}
#userName,#password{
    width: 200px;
    border: none;
    height: 32px;
    line-height: 32px;
    background-color: #2c477a;
    margin: 0 0 10px 46px;
}
#userName{
    width: 200px;
    border: none;
    height: 32px;
    line-height: 32px;
    background-color: #2c477a;
    margin: 0 0 10px 46px;
}
#code{
	border: none;
    height: 32px;
    line-height: 32px;
    background-color: #2c477a;
    margin: 6px 0 0 7px;
    width: 100px;
}
.btn2{
    display: block;
    background: url(<%=path%>/pages/assets/images/btn.jpg) no-repeat;
    height: 48px;
    width: 254px;
    float: left;
    border:none;
    background-color: #2c477a;
}
#lg{
	color: #00006e;
    text-decoration: none;
}
.modal-content{
border:none;
}
.logo{
	width: 30px;
	height:30px;
    padding: 0px;
    margin-top: -4px;
	background:url(<%= basePath%>/pages/assets/images/logo.png)
}
.nav-tabs li .close-tab{
	padding-left:0px;
	text-align: right;
	right:2px;
}
</style>
</head>
<body >
	<div class="navbar navbar-default" id="navbar">
		<div class="navbar-container" id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="<%=path%>/" class="navbar-brand"> <small> 
				<img class="logo" src="<%= basePath%>/pages/assets/images/logo.png" /> 蝙蝠众包管理后台
				</small>
				</a>
				<!-- /.brand -->
			</div>
			<!-- /.navbar-header -->

			<div class="navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">
					<li class="black">
						上次退出时间：<%= lastOutTime%>
					</li>
					<!-- 
					<li class="grey">
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							<i class="icon-tasks"></i>
							<span class="badge badge-grey">4</span>
						</a>

						<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
							<li class="dropdown-header">
								<i class="icon-ok"></i>
								还有4个任务完成
							</li>

							<li>
								<a href="#">
									<div class="clearfix">
										<span class="pull-left">软件更新</span>
										<span class="pull-right">65%</span>
									</div>

									<div class="progress progress-mini ">
										<div style="width:65%" class="progress-bar "></div>
									</div>
								</a>
							</li>

							<li>
								<a href="#">
									<div class="clearfix">
										<span class="pull-left">硬件更新</span>
										<span class="pull-right">35%</span>
									</div>

									<div class="progress progress-mini ">
										<div style="width:35%" class="progress-bar progress-bar-danger"></div>
									</div>
								</a>
							</li>

							<li>
								<a href="#">
									<div class="clearfix">
										<span class="pull-left">单元测试</span>
										<span class="pull-right">15%</span>
									</div>
									<div class="progress progress-mini ">
										<div style="width:15%" class="progress-bar progress-bar-warning"></div>
									</div>
								</a>
							</li>
							<li>
								<a href="#">
									<div class="clearfix">
										<span class="pull-left">错误修复</span>
										<span class="pull-right">90%</span>
									</div>
									<div class="progress progress-mini progress-striped active">
										<div style="width:90%" class="progress-bar progress-bar-success"></div>
									</div>
								</a>
							</li>

							<li>
								<a href="#">
									查看任务详情
									<i class="icon-arrow-right"></i>
								</a>
							</li>
						</ul>
					</li>
 -->
 <!-- 
					<li class="purple"><a data-toggle="dropdown"
						class="dropdown-toggle" href="#"> <i
							class="icon-bell-alt icon-animated-bell"></i> <span
							class="badge badge-important">8</span>
					</a>

						<ul
							class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
							<li class="dropdown-header"><i class="icon-warning-sign"></i>
								8条通知</li>

							<li><a href="#">
									<div class="clearfix">
										<span class="pull-left"> <i
											class="btn btn-xs no-hover btn-pink icon-comment"></i> 新闻评论
										</span> <span class="pull-right badge badge-info">+12</span>
									</div>
							</a></li>

							<li><a href="#"> <i
									class="btn btn-xs btn-primary icon-user"></i> 切换为编辑登录..
							</a></li>

							<li><a href="#">
									<div class="clearfix">
										<span class="pull-left"> <i
											class="btn btn-xs no-hover btn-success icon-shopping-cart"></i>
											新订单
										</span> <span class="pull-right badge badge-success">+8</span>
									</div>
							</a></li>

							<li><a href="#">
									<div class="clearfix">
										<span class="pull-left"> <i
											class="btn btn-xs no-hover btn-info icon-twitter"></i> 粉丝
										</span> <span class="pull-right badge badge-info">+11</span>
									</div>
							</a></li>

							<li><a href="#"> 查看所有通知 <i class="icon-arrow-right"></i>
							</a></li>
						</ul></li>
					 -->
					<!-- 
					<li class="green">
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							<i class="icon-envelope icon-animated-vertical"></i>
							<span class="badge badge-success">5</span>
						</a>

						<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
							<li class="dropdown-header">
								<i class="icon-envelope-alt"></i>
								5条消息
							</li>

							<li>
								<a href="#">
									<img src="<%=path%>/pages/assets/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />
									<span class="msg-body">
										<span class="msg-title">
											<span class="blue">Alex:</span>
											不知道写啥 ...
										</span>

										<span class="msg-time">
											<i class="icon-time"></i>
											<span>1分钟以前</span>
										</span>
									</span>
								</a>
							</li>

							<li>
								<a href="#">
									<img src="<%=path%>/pages/assets/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" />
									<span class="msg-body">
										<span class="msg-title">
											<span class="blue">Susan:</span>
											不知道翻译...
										</span>

										<span class="msg-time">
											<i class="icon-time"></i>
											<span>20分钟以前</span>
										</span>
									</span>
								</a>
							</li>

							<li>
								<a href="#">
									<img src="<%=path%>/pages/assets/avatars/avatar4.png" class="msg-photo" alt="Bob's Avatar" />
									<span class="msg-body">
										<span class="msg-title">
											<span class="blue">Bob:</span>
											到底是不是英文 ...
										</span>

										<span class="msg-time">
											<i class="icon-time"></i>
											<span>下午3:15</span>
										</span>
									</span>
								</a>
							</li>

							<li>
								<a href="inbox.html">
									查看所有消息
									<i class="icon-arrow-right"></i>
								</a>
							</li>
						</ul>
					</li>
 -->
					<li class="light-blue"><a data-toggle="dropdown" href="#"
						class="dropdown-toggle"> <img class="nav-user-photo"
							src="<%=path%>/pages/assets/avatars/profile-pic.jpg" /> <span
							class="user-info"> <small>欢迎光临,</small> <span><%=userName%></span>
						</span> <i class="icon-caret-down"></i>
					</a>
						<ul
							class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
							<li><a href="#" id = "modPasswordBtn"> <i class="icon-cog"></i> 修改密码</a></li>
							<li><a href="#" id = "persionMessage"> <i class="icon-user"></i> 个人资料</a></li>
							<li class="divider"></li>
							<li><a id="outLogin" href="#"> <i class="icon-off"></i>
									退出
							</a></li>
						</ul></li>
				</ul>
				<!-- /.ace-nav -->
			</div>
			<!-- /.navbar-header -->
		</div>
		<!-- /.container -->
	</div>

	<div class="main-container" id="main-container">


		<div class="main-container-inner" >
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>

			<div class="sidebar" id="sidebar">

				<ul id="menus" class="nav nav-list">
				</ul>
				<div class="sidebar-collapse" id="sidebar-collapse">
					<i class="icon-double-angle-left"
						data-icon1="icon-double-angle-left"
						data-icon2="icon-double-angle-right"></i>
				</div>
			</div>
			<div class="main-content" >
				<ul class="nav nav-tabs" role="tablist" style="height:34px">
					<li role="presentation" class="active" style="margin-right: 4px;">
						<a href="#home" aria-controls="home" role="tab" data-toggle="tab"><i
							class="icon-home home-icon"></i>首页</a>
					</li>

				</ul>
				<div class="tab-content" style="margin-right: 5px;" >
					<div role="tabpanel" class="tab-pane active" id="home">
						<iframe id="mainIframeBtn" src="<%=path%>/admin/main" scrolling="no"
							frameborder="0" width="100%" style="border: 0;"></iframe>
					</div>
				</div>
			</div>
			<!-- /.main-content 2016.12.07隐藏导航条 -->
			<div class="ace-settings-container" id="ace-settings-container" style="display:none">
				<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
					id="ace-settings-btn">
					<i class="icon-cog bigger-150"></i>
				</div>
				<div class="ace-settings-box" id="ace-settings-box">
					<div>
						<div class="pull-left">
							<select id="skin-colorpicker" class="hide">
								<option data-skin="default" value="#438EB9">#438EB9</option>
								<option data-skin="skin-1" value="#222A2D">#222A2D</option>
								<option data-skin="skin-2" value="#C6487E">#C6487E</option>
								<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
							</select>
						</div>
						<span>&nbsp; 选择皮肤</span>
					</div>
					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-navbar" /> <label class="lbl"
							for="ace-settings-navbar"> 固定导航条</label>
					</div>
					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-sidebar" /> <label class="lbl"
							for="ace-settings-sidebar"> 固定滑动条</label>
					</div>
					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-breadcrumbs" /> <label class="lbl"
							for="ace-settings-breadcrumbs">固定面包屑</label>
					</div>
					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-rtl" /> <label class="lbl"
							for="ace-settings-rtl">切换到左边</label>
					</div>
					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-add-container" /> <label class="lbl"
							for="ace-settings-add-container"> 切换窄屏 <b></b>
						</label>
					</div>
				</div>
			</div>
			<!-- /#ace-settings-container -->
		</div>
		<!-- /.main-container-inner -->
		<!-- 
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
		 -->
	</div>
	<!-- /.main-container -->


	<!-- 新增企业模块 -->
	<div id="loginModel" class="bootbox modal fade" tabindex="-1"
		role="dialog" style="border:none;">
		<div class="modal-dialog" style="width:350px;">
			<div class="modal-content" style="background: url(<%=path%>/pages/assets/images/login_n.png) center no-repeat #2c477a;">
				<!-- #1d2024  646478 283c50-->
				<div class="modal-body" >
					<div class="space-6"></div>
					<div class="position-relative" >
						<div id="login-box" class="login-box visible widget-box no-border">
							<div class="widget-body" style="background: url(<%=path%>/pages/assets/images/1.png) center no-repeat #2c477a;">
								<div class="widget-main" style="margin-top: -73px;">
									<h4 class="header blue lighter bigger">
										<i class="icon-coffee green"></i> <span id="msg">请输入用户信息</span>
									</h4>
									<div class="space-6"></div>
									<form>
										<fieldset>
											<div class="block clearfix username">

												<input id="userName" type="text" class="form-control " placeholder="账号" />
												<%-- <input id="userName" type="text" class="form-control" placeholder="账号" style="background:url(<%=path%>/pages/assets/images/user_n.jpg)  no-repeat "/>
														<i class="icon-user"></i> --%>

											</div>
											<div class="block clearfix password">
											 <input id="password" type="password" class="form-control " placeholder="密码" /> 
											</div>
											 <div class="block clearfix code"> 
													<div class="row">
														<div class="col-md-7">
															<input id="code" type="text" class="form-control"
																placeholder="验证码"  />
														</div>
														<div class="col-md-4">
															<img id="randImage" onclick="refreshImage();"
																src="<%=path%>/admin/image"
																style="cursor:pointer; height:38px;width:100px; margin-left:-20px;margin-top:5px;"
																border=0 height="28" />
														</div>
													</div>
											</div>
											<!--  <div class="space"></div> -->
											<div class="clearfix" style="height:45px;width:100px;" >
												<button id="lg" type="button" class="btn2" >
													<!-- 	<i class="icon-key"></i>
														登录 -->
													</button>
											</div>
											<div class="space-4"></div>
										</fieldset>
									</form>
								</div>
								<!-- /widget-main -->
								<!-- <div class="toolbar clearfix text-center"
									style="background:#2c477a">
									<div class="text-center" style="width: 100%">
										<p class="white text-center">Copyright © 2013-2015 高蓝平台</p>
									</div>
								</div> -->
							</div>
							<!-- /widget-body -->
						</div>
						<!-- /login-box -->
					</div>
					<!-- /position-relative -->
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改密码模块 -->
	<div id="modPasswordModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:40%">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title">修改密码</h4>
			     </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" onsubmit="return false;">
						<div class="form-group">
							<label class="col-sm-3 control-label">旧密码</label>
							<div class="col-sm-8">
								<input id="oldPassword" maxlength="32" type="password"  data-original-title="Tooltip for name" class="form-control"  type="text">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">新密码</label>
							<div class="col-sm-8">
								<input id="newPassword" maxlength="32" type="password"  data-original-title="Tooltip for name" class="form-control"  type="text">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">确认新密码</label>
							<div class="col-sm-8">
								<input id="newPassword2" maxlength="32" type="password"  data-original-title="Tooltip for name" class="form-control"  type="text">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="confirm" type="button" class="btn btn-success" onclick="modPassword()">
						<span><i class="icon-ok"></i></span> &nbsp;提&nbsp;&nbsp;交
					</button>
					<button data-bb-handler="cancel" type="button" modal="modPasswordModal" class="cancel btn btn-danger cancel" onclick="closeModal()">退出</button>
				</div>
			</div>
		</div>
	</div> 
<script src="<%=path%>/pages/assets/js/layer/layer.min.js" charset="UTF-8"></script>
<script type="text/javascript">
	var loading; 
	$(function() {  
		// 初始化查询菜单
		initData();
		$("#menus").on("click", ".changeUrl", function() {
			var id = $(this).attr("id");
			var url = $(this).attr("value");
			if (id && url) {
				addTabs({
					id : id,
					title : $(this).text(),
					url : ctp + "/" + url,
					close : true
				});
			}
		});
		$(".nav-tabs").on("click", ".close-tab", function() {
			id = $(this).prev("a").attr("aria-controls");
			closeTab(id);
		});
		// $("#mainIframeBtn").height($(document).height()-120);
		// iframe高度自适应
		$("#mainIframeBtn").load(function() {
			var mainheight = $(this).contents().find("body").height() + 30;
			if (mainheight < 400) {
				mainheight = 400;
			}
			
			$(this).height(mainheight);
		});
		$("#userName").focus(function() {
			$("#msg").removeClass("text-danger").text("请输入用户账号");
		})
		$("#password").focus(function() {
			$("#msg").removeClass("text-danger").text("请输入用户密码");
		})
		$("#code").focus(function() {
			$("#msg").removeClass("text-danger").text("请输入验证码");
		})
		// 退出操作
		$("#outLogin").click(function() {
			$.ajax({
				url : ctp + "/admin/loginOut",
				cache : false,
				dataType : 'json',
				type : 'get',
				success : function(json) {
					try {
						window.location.href = ctp + "/admin/";
					} catch (e) {
						window.location.href = ctp + "/admin/";
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.location.href = ctp;
				}
			});
		})
		// 登录按钮
		$("#lg").click(function() {
			if ($.trim($("#userName").val()) == "") {
				// $("#username").focus();
				$("#msg").addClass("text-danger").text("用户账号不能为空");
				return;
			}
			if ($.trim($("#password").val()) == "") {
				$("#msg").addClass("text-danger").text("用户密码不能为空");
				return;
			}
			if ($.trim($("#code").val()) == "") {
				$("#msg").addClass("text-danger").text("验证码不能为空");
				return;
			}
			// 登录操作
			$.ajax({
				type : "POST",
				url : ctp + "/login",
				data : {
					"userName" : $("#userName").val(),
					"password" : hex_md5($("#password").val()), // 这里加密一次
					"captcha" : $("#code").val()
				},
				dataType : "json",
				success : function(data) {
					refreshImage();
					if (data.restcode == "-1") {
						$("#msg").addClass("text-danger").text(data.msg);
					} else {
						$("#loginModel").modal("hide");
						window.location.href = ctp;
					}
				}
			});
		});
		
		//点击修改密码，弹出修改密码窗口
		$("#modPasswordBtn").click(function() {
			$("#modPasswordModal").modal('show');
		});
		
		//点击个人资料，出现个人资料标签页
		$("#persionMessage").on("click", function() {
			var id = "0";
			var url = "ddd";
			if (id && url) {
				addTabs({
					id : id,
					title : $(this).text(),
					url : ctp + "/admin/index/adminUser",
					close : true
				});
			}
		});
		
	});
	
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) { // enter 键
			$("#lg").click();
		}
	};
	
	// 初始化菜单
	function initData() {
		// 查询菜单操作
		$.ajax({
					type : "GET",
					url : ctp + "/admin/index/queryMenus",
					data : {},
					cache : false,
					dataType : "json",
					// contentType:"application/json",
					success : function(data) {
						var menuList = data.menuList;
						var html = '';
						var htmls = new Array();
	
						for (var i = 0; i < menuList.length; i++) {
							if (typeof (menuList[i].URL) != "undefined" && menuList[i].URL != "" && menuList[i].URL != null) {
								htmls
										.push('<li><a id="'
												+ menuList[i].MENU_ID
												+ '" value="'
												+ menuList[i].URL
												+ '" href="javascript:void(0);" class="changeUrl">');
							} else {
								htmls
										.push('<li><a href="javascript:void(0);" class="dropdown-toggle">');
							}
							htmls.push('<i class="' + menuList[i].ICON_CLASS
									+ '"></i>');
							htmls.push('<span class="menu-text">'
									+ menuList[i].MENU_NAME + '</span>');
							htmls.push('</a>');
							if (typeof (menuList[i].SUB_MENUS) != "undefined") {
								var subMenuList = menuList[i].SUB_MENUS;
								if (subMenuList.length > 0) {
									htmls.push('<ul class="submenu">');
									for (var y = 0; y < subMenuList.length; y++) {
										htmls.push('<li>');
										htmls
												.push('<a id="'
														+ subMenuList[y].MENU_ID
														+ '" value="'
														+ subMenuList[y].URL
														+ '" href="javascript:void(0)" class="changeUrl">');
										htmls
												.push('<i class="icon-double-angle-right"></i><span class="menu-text">'
														+ subMenuList[y].MENU_NAME
														+ '</span>');
										htmls.push('</a>');
										htmls.push('</li>');
									}
									htmls.push('</ul>');
								}
							}
							// 这里判断如果有值就继续
							htmls.push('</li>');
						}
						var html = htmls.join("");
						$("#menus").html(html);
	
					}
				});
	}
	
	// 打开菜单
	function changeUrl(url) {
		clearFlash();
		$("#mainFrame").attr("src", ctp + "/" + url);
	}
	// 清除iframe
	function clearFlash() {
		try {
			// 在iframe销毁前清理掉iframe中的内容（特别是flash）
			$("#mainFrame").contents().find("body").empty();
		} catch (e) {
		}
	}
	// 刷新验证码
	function refreshImage() {
		var randImage = document.getElementById("randImage");
		var changeURL = ctp + "/admin/image?date=" + new Date() + "";
		randImage.src = changeURL;
	}
	
	function closeModal(){
		$("#modPasswordModal").modal('hide');
	}
	
	//修改密码
	function modPassword(){
		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		var newPassword2 = $("#newPassword2").val();
		if($.trim(oldPassword) == ""){
			$.messager.alert("温馨提示", "请输入密码！");
			return;
		}
		if($.trim(newPassword) == ""){
			$.messager.alert("温馨提示", "请输入新密码！");
			return;
		}
		if($.trim(newPassword2) == ""){
			$.messager.alert("温馨提示", "请再次输入新密码！");
			return;
		}
		if(newPassword!=newPassword2){
			$.messager.alert("温馨提示", "两次输入新密码不一致，请确认！");
			return;
		}
		$.ajax({
			type : "POST",
			url : ctp + "/admin/index/AdminPassword",
			data : {
				"oldPassword" : hex_md5($("#oldPassword").val()), //这里加密一次
				"newPassword" : hex_md5($("#newPassword").val()), //这里加密一次
			},
			dataType : "json",
			beforeSend : function(){
	               loading=layer.load("正在操作中...");
	               $("#psaveSave").attr("disabled",true);
	        },
			success : function(data) {
				alert(data.message);
				if(data.success){
					alert("密码已被修改，请重新登录！")
					$("#outLogin").click();
					//$("#modPasswordModal").modal('hide');
				}
			},
			error : function() {
				$("#psaveSave").attr("disabled",false);
				$.messager.alert("温馨提示","请求错误！");
			},
			complete : function(){
				$("#psaveSave").attr("disabled",false);
	            layer.close(loading);
	        }
		});	
	
	}
	function strlen(str){
	    var len = 0;
	    for (var i=0; i<str.length; i++) {
	     var c = str.charCodeAt(i);  
	     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
	       len++; 
	     }else { 
	      len+=2; 
	     } 
	    } 
	    return len;
	    
	}
</script>
</body>
</html>