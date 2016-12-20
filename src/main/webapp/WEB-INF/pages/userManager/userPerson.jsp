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
<title>普通用户</title>
<link rel="stylesheet" type="text/css" media="all" href="<%=path%>/pages/assets/css/daterangepicker-bs3.css"/>
<link href="<%= basePath%>/pages/assets/css/carousel.css" rel="stylesheet">

<style>
.btn{
	padding-top: 0;padding-bottom: 0;
}
.norecords { 
	border-width: 2px !important; 
	display:none; 
	font-weight: bold; 
	left: 45%; 
	margin: 5px; 
	padding: 6px; 
	position: absolute; 
	text-align: center; 
	top: 45%; 
	width: auto; 
	z-index: 102; 
}
.file {
   position: relative;
   display: inline-block;
   background: #D0EEFF;
   border: 1px solid #99D3F5;
   border-radius: 4px;
   padding: 4px 12px;
   overflow: hidden;
   color: #1E88C7;
   text-decoration: none;
   text-indent: 0;
   line-height: 20px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}
.noBorder{
	border-color:#fff;
	box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);
}
#gview_grid-table {
	border-left-width: 1px;
	border-right-width: 1px;
	border-left-style: solid;
	border-right-style: solid;
	border-left-color: #e1e1e1;
	border-right-color: #e1e1e1;
	border-collapse: collapse;
	border-spacing: 0;
}
.widget-box{
	border-style: none;
}
.widget-body{
	border-style: none;
}
.carousel {
  height: 100%;
}
/* Declare heights because of positioning of img element */
.carousel .item {
  height: 100%;
  background-color: #fff;
}
.carousel-inner > .item > img {
  max-width:100%;
}
</style>
</head>
<body style="background: #ffffff;">
	<div class="row">
		<div class="col-xs-12">
			<div class="widget-box">
				<div class="widget-body">
					<div class="widget-body-inner" style="display: block;">
						<div class="widget-main">
							<div>
								<div class="row">
									<div class="col-xs-12" id="selectRow">
										<div class="panel panel-default">
											<div class="panel-heading">
												<form class="form-horizontal" role="form" onsubmit="return false;">
													<div class="form-group" style="margin-bottom: 0;">
														<label class="col-sm-1 control-label">注册用户:</label>
														<div class="col-sm-2">
															<input id="qryUserName" name="qryUserName" style="width:150px" type="text" class="form-control" placeholder="请输入注册账号">
														</div>
														<label class="col-sm-1 control-label" style="width: 8%;">认证状态:</label>
														<select class="form-control" id="qryReviewState" style="width: 120px; float: left;margin-left:12px">
															<option value="">全部</option>
															<option value='0'>待认证</option>
															<!-- <option value='1'>禁用</option> -->
															<option value='2'>已认证</option>
															<option value='3'>认证不通过</option>
															<option value='4'>未认证</option>
														</select>
														<label class="col-sm-1 control-label" style="width: 8%;">注册时间:</label>
														<div class="col-sm-3" style="width: 225px;">
															<input data-original-title="Tooltip for name" class="form-control" placeholder="起始时间--截止时间" type="text" id="dateRange" readonly="readonly">
														</div>
														<div class="col-sm-1" style="float:right;margin-right: 3%;">
															<button class="btn btn-info myBtn" onclick="resetQry()" id="reset"><i class="fa fa-circle-o-notch">&nbsp;</i>重置</button>
														</div>
														<div class="col-sm-1" style="float:right;">
															<button id="queryBtn" class="btn btn-info btn-label-left myBtn">
																<i class="fa fa-search">&nbsp;</i> 查询
															</button>
														</div>
													</div>
												</form>
											</div>
										</div>
									</div>
									<div class="col-xs-12">
										<div style="margin-bottom: 5px;">
										</div>
										<table id="grid-table"></table>
										<div id="grid-pager"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>	
			</div>
		</div>	
	</div>
	<!-- 查看模块 -->
	<div id="showModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:50%">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="addModalLabel">用户信息</h4>
			      </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="showForm" onsubmit="return false;">
						<div class="form-group" >
							<label class="col-sm-2 control-label">姓名:</label>
							<div class="col-sm-3">
						        	<input id="realName" name="realName" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly">
							</div>
							<label class="col-sm-2 control-label">性别:</label>
							<div class="col-sm-3">
						        <input id="sex" name="sex" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">联系电话:</label>
							<div class="col-sm-6">
								<input id="mobile" name="mobile" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">星级:</label>
							<div class="col-sm-6">
								<input id="star" name="star" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">技能:</label>
							<div class="col-sm-6">
								<input id="ability" name="ability" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly" onmouseover="this.title=this.value">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<!-- <button data-bb-handler="cancel" type="button" id="showCancel" modal="showModal" class="cancel btn btn-danger cancel">取消</button> -->
				</div>
			</div>
		</div>
	</div>
	<!-- 审核模块 -->
	<div id="auditModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:50%">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="auditModalLabel">普通用户审核</h4>
			      </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="auditForm" onsubmit="return false;">
						<div class="form-group container theme-showcase" role="main" style="max-width:100%;">
							<div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="margin-bottom:0px;">
							  <ol class="carousel-indicators">
							    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
							    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
							    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
							  </ol>
							  <div class="carousel-inner" role="listbox" style="">
							    <div class="item active">
							      <center><img width="100%" id="idcardPhoto1" alt="身份证正面照片" src=""></center>
							    </div>
							    <div class="item">
							      <center><img width="100%" id="idcardPhoto2" alt="身份证反面照片" src=""></center>
							    </div>
							    <div class="item">
							      <center><img width="100%" id="idcardPhoto3" alt="手持身份证照片" src=""></center>
							    </div>
							  </div>
							</div>
					    </div>
					    <div class="form-group" >
							<label class="col-sm-5 control-label">用户认证姓名:</label>
							<div class="col-sm-4">
						        	<input id="auditRealName" name="auditRealName" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-5 control-label">用户认证身份证号:</label>
							<div class="col-sm-4">
						        <input id="auditIdcardCode" name="auditIdcardCode" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer" id="auditModalFooter" style="text-align: center">
					<button data-bb-handler="confirm" type="button" id="pass" class="btn btn-success" onclick="auditUserSend('2')">
						&nbsp;通过&nbsp;
					</button>
					<button data-bb-handler="confirm" type="button" id="noPass" class="btn btn-success" onclick="auditUserSend('3')">
						&nbsp;不通过&nbsp;
					</button>
					<!-- <button data-bb-handler="cancel" type="button" id="mcancerSave" modal="modifyModal" class="cancel btn btn-danger cancel">取消</button> -->
				</div>
			</div>
		</div>
	</div>
<script src="<%=path%>/pages/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="<%=path%>/pages/assets/js/jqGrid/i18n/grid.locale-en.js" type="text/javascript" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/moment.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/daterangepicker.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/date.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/layer/layer.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/userManager/userPerson.js"></script>
<script src="<%=path%>/pages/assets/js/uploadPreview.js"></script>
<script type="text/javascript">
	var path='<%=path%>';
	$(function(){
		$(".cancel").click(function(){
			$("#"+$(this).attr("modal")).modal("hide");
		});
		
		/*JQuery 限制文本框只能输入数字和小数点*/  
        $(".numDecText").keyup(function(){    
                $(this).val($(this).val().replace(/[^0-9.]/g,''));    
            }).bind("paste",function(){  //CTR+V事件处理    
                $(this).val($(this).val().replace(/[^0-9.]/g,''));     
            }).css("ime-mode", "disabled"); //CSS设置输入法不可用   
	});
	//时间控件
	$('#dateRange').daterangepicker({});
	
</script>

<!--用户详情备用  -->
<%-- <div class="form-group" >
							<label class="col-sm-2 control-label">头像</label>
							<div class="col-sm-8">
								<img id="ImgPr" width="120" height="120" src="<%=path%>/pages/assets/images/uploadpic.png"/>
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">账户</label>
							<div class="col-sm-4">
								<input id="userName" name="userName" type="text" class="form-control">
							</div>
							<label class="col-sm-2 control-label">昵称</label>
							<div class="col-sm-4">
						        	<input id="nickName" name="nickName" type="text" class="form-control">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">性别</label>
							<div class="col-sm-4">
						        <select class="form-control" id="sex" name="sex" style="float:left;">
										<option value='0'>男</option>
										<option value='1'>女</option>
								</select>
							</div>
							<label class="col-sm-2 control-label">领域</label>
							<div class="col-sm-4">
								<input id="realm" name="realm" type="text" class="form-control">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">省份/城市/地区</label>
							<div class="col-sm-4">
								<input id="lastProvinceCode" name="lastProvinceCode" type="hidden">
								<input id="lastAreaCode" name="lastAreaCode" type="hidden">
								<input id="lastCityCode" name="lastCityCode" type="hidden">
								<input id="lastProvinceName" name="lastProvinceName" type="text" style="width:33%;float:left" class="form-control" >
								<input id="lastCityName" name="lastCityName" type="text" style="width:33%;float:left" class="form-control" >
								<input id="lastAreaName" name="lastAreaName" type="text" style="width:33%;float:left" class="form-control" >
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">账号类型</label>
							<div class="col-sm-4">
						        <select class="form-control" id="lastRole" name="lastRole" style="float:left;">
										<option value='worker'>接包方</option>
										<option value='employer'>发包方</option>
								</select>
							</div>
							<label class="col-sm-2 control-label">实名认证</label>
							<div class="col-sm-4">
								<input id="reviewState" name="reviewState" type="text" class="form-control" >
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">绑定状态</label>
							<div class="col-sm-4">
								<input id="bindState" name="bindState" type="text" class="form-control" >
							</div>
							<label class="col-sm-2 control-label">绑定SRP</label>
							<div class="col-sm-4">
								<input id="bindSrp" name="bindSrp" type="text" class="form-control" >
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">账号金额</label>
							<div class="col-sm-4">
								<input id="balance" name="balance" type="text" class="form-control" >
							</div>
							<label class="col-sm-2 control-label">手机号</label>
							<div class="col-sm-4">
								<input id="mobile" name="mobile" type="text" class="form-control" >
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">支付宝账号</label>
							<div class="col-sm-4">
								<input id="alipayCode" name="alipayCode" type="text" class="form-control" >
							</div>
							<label class="col-sm-2 control-label">银行卡号</label>
							<div class="col-sm-4">
								<input id="bankCardCode" name="bankCardCode" type="text" class="form-control" >
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">微信号</label>
							<div class="col-sm-4">
								<input id="wechatCode" name="wechatCode" type="text" class="form-control" >
							</div>
							<label class="col-sm-2 control-label">银行卡号</label>
							<div class="col-sm-4">
								<input id="bankCardCode" name="bankCardCode" type="text" class="form-control" >
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">注册时间</label>
							<div class="col-sm-4">
								<input id="createTime" name="createTime" type="text" class="form-control" >
							</div>
							<label class="col-sm-2 control-label">修改时间</label>
							<div class="col-sm-4">
								<input id="lastModified" name="lastModified" type="text" class="form-control" >
							</div>
						</div> --%>


</body>
</html>