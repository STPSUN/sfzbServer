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
<title>企业用户</title>
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
														<label class="col-sm-1 control-label">企业用户:</label>
														<div class="col-sm-2">
															<input id="qryUserName" name="qryUserName" style="width:150px" type="text" class="form-control" placeholder="请输入注册账号">
														</div>
														<label class="col-sm-1 control-label" style="width: 8%;">认证状态:</label>
														<select class="form-control" id="qryReviewState" style="width: 120px; float: left;margin-left:12px">
															<option value="">全部</option>
															<option value='0'>待认证</option>
															<!-- <option value='1'>禁用</option> -->
															<option value='1'>已认证</option>
															<option value='2'>认证不通过</option>
															<option value='3'>未认证</option>
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
	<!-- 账户金额详情 -->
	<div id="balanceModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:73%">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="addModalLabel">账户金额</h4>
			    </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" onsubmit="return false;">
						<div class="form-group" >
							<label class="col-sm-2 control-label">账户可用余额:</label>
							<div class="col-sm-3">
						        <input id="balance" name="balance" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" >
							</div>
							<label class="col-sm-2 control-label" title="计算公式：成交金额*（预付款比例+兜底比例）">账户项目预付款:</label>
							<div class="col-sm-3">
						        <input id="imprest" name="imprest" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);">
							</div>
						</div>
					</form>
					<hr />
					<div>
						<div style="margin-bottom: 5px;"></div>
						<table id="grid-balance-table"></table>
						<div id="grid-balance-pager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 用户详情 -->
	<div id="userModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:50%">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title">用户信息</h4>
			      </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" onsubmit="return false;">
						<div class="form-group">
							<label class="col-sm-5 control-label">企业名称：</label>
							<div class="col-sm-7">
						        <input id="userEnterpriseName" name="userEnterpriseName" type="text" class="form-control" style="border-color:#fff;
									box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly" onmouseover="this.title=this.value">
							</div>
							<label class="col-sm-5 control-label">企业法人：</label>
							<div class="col-sm-7">
						        <input id="userCorporate" name="userCorporate" type="text" class="form-control" style="border-color:#fff;
									box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly" onmouseover="this.title=this.value">
							</div>
							<label class="col-sm-5 control-label">营业执照号：</label>
							<div class="col-sm-7">
						        <input id="userBusinessLicense" name="userBusinessLicense" type="text" class="form-control" style="border-color:#fff;
									box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly" onmouseover="this.title=this.value">
							</div>
							<label class="col-sm-5 control-label">联系电话：</label>
							<div class="col-sm-7">
						        <input id="userMobile" name="userMobile" type="text" class="form-control" style="border-color:#fff;
									box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly">
							</div>
							<div class="col-sm-12" style="text-align:center;">
						        <img id="userBusinessLicenseImage" alt="营业执照副本照片" src="" style="width:400px">
							</div>
							<div class="col-sm-12" style="text-align:center;">
						        <label class="control-label">营业执照副本照片</label>
							</div>
						</div>
					</form>
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
			        <h4 class="modal-title" id="auditModalLabel">企业用户审核</h4>
			      </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="auditForm" onsubmit="return false;">
						<div class="form-group container theme-showcase" role="main" style="width:100%;max-width:100%;">
							<div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="margin-bottom:0px;height:300px;">
							  <!-- <ol class="carousel-indicators">
							    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
							  </ol> -->
							  <div class="carousel-inner" role="listbox" style="height:300px;overflow: auto;">
							    <div class="item active">
							      <img  id="auditBusinessLicenseImage" alt="营业执照副本照片" src="" onclick="showImg('营业执照副本照片')">
							    </div>
							  </div>
							</div>
					    </div>
					    <div class="form-group" >
							<label class="col-sm-5 control-label">企业名称：</label>
							<div class="col-sm-4">
						        	<input id="auditEnterpriseName" name="auditEnterpriseName" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly" onmouseover="this.title=this.value">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-5 control-label">营业执照号：</label>
							<div class="col-sm-4">
						        <input id="auditBusinessLicense" name="auditBusinessLicense" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly" onmouseover="this.title=this.value">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-5 control-label">企业法人：</label>
							<div class="col-sm-4">
						        <input id="auditCorporate" name="auditCorporate" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly" onmouseover="this.title=this.value">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer" id="auditModalFooter" style="text-align: center">
					<button data-bb-handler="confirm" type="button" id="pass" class="btn btn-success" onclick="auditUserSend('1')">
						&nbsp;通过&nbsp;
					</button>
					<button data-bb-handler="confirm" type="button" id="noPass" class="btn btn-success" onclick="auditUserSend('2')">
						&nbsp;不通过&nbsp;
					</button>
					<!-- <button data-bb-handler="cancel" type="button" id="mcancerSave" modal="modifyModal" class="cancel btn btn-danger cancel">取消</button> -->
				</div>
			</div>
		</div>
	</div>
	<!-- 图片展示模态  -->
	<div id="imgModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:50%">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="imgModalLabel">图片展示</h4>
			    </div>
				<div class="modal-body" id="imgModalBody">
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
<script src="<%=path%>/pages/userManager/userEnterprise.js"></script>
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
</body>
</html>