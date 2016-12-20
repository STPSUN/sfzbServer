<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String basePath = request.getContextPath();
if(basePath.endsWith("/")){
	basePath = basePath.substring(0, basePath.length()-1);
}
String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+basePath;
%>
<!DOCTYPE HTML>
<html>
<head>
<title>个人资料</title>
<link rel="stylesheet" type="text/css" media="all" href="<%=path%>/pages/assets/css/daterangepicker-bs3.css"/>

<style>

</style>
</head>
<body >
<div class="container" style="margin-top: 100px;">
	<div class="row" style="height:60px;">
	    <div class="col-md-3" ></div>
		<div class="col-md-3" >
			账号：
		</div>
		<div id="adminAccound" class="col-md-3" >
			
		</div>
		<div class="col-md-3" ></div>
	</div>
	<div class="row" style="height:60px">
		<div class="col-md-3" ></div>
		<div id="adminName" class="col-md-3" >
			姓名：
		</div>
		<div class="col-md-3" ></div>
		<div class="col-md-3" ></div>
	</div>
	<div class="row"  style="height:60px">
		<div class="col-md-3" ></div>
		<div id="adminRole" class="col-md-3">
			角色：
		</div>
		<div class="col-md-3" ></div>
		<div class="col-md-3" ></div>
	</div>
</div>

<script type="text/javascript">
	var path='<%=path%>';
	$(function(){
		$.ajax({
			type : "GET",
			url : ctp + "/admin/index/qrySelfMsg",
			dataType : "json",
			success : function(data) {
				if(data.sucess == true){
					$("#adminAccound").html(data.data.admin_account);
				}
			},
			error : function() {
				$.messager.alert("温馨提示","请求错误！");
			}
		});
	});
</script>
</body>
</html>