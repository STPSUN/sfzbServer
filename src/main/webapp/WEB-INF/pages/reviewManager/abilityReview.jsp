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
<title>技能认证</title>
<link rel="stylesheet" type="text/css" media="all" href="<%=path%>/pages/assets/css/daterangepicker-bs3.css"/>

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
.form-control{
	height:30px;
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
													<div class="col-sm-2" style="width:240px;">
														<label class="control-label" style="width: 80px;float: left;">审核状态：</label>
														<select class="form-control" id="qryReviewState" style="width: 120px; float: left;margin-left:12px">
															<option value='0'>待审核</option>
															<option value='1'>审核通过</option>
															<option value='2'>审核不通过</option>
														</select>
													</div>
													<div class="col-sm-2" style="width:200px;">
														<label class="control-label" style="width: 50px;float: left;">账号：</label>
														<input type="text" class="form-control col-sm-2" style="width: 100px;float: left;margin-left:12px" id="userName" placeholder="账号模糊查询">
													</div>
													<div class="col-sm-2" style="width:200px;">
														<label class="control-label" style="width: 50px;float: left;">昵称：</label>
														<input type="text" class="form-control" style="width: 100px;float: left;margin-left:12px" id="nickName" placeholder="昵称模糊查询">
													</div>
													<div class="col-sm-2" style="width:230px;">
														<label class="control-label" style="width: 70px;float: left;">联系手机：</label>
														<input type="text" class="form-control" style="width: 100px;float: left;margin-left:12px" id="mobile" placeholder="手机号查询">
													</div>
													<div class="col-sm-2" style="width:80px;">
														<button id="queryBtn" class="btn btn-info btn-label-left myBtn">
															<i class="fa fa-search">&nbsp;</i> 查询
														</button>
													</div>
													<div class="col-sm-2" style="width:80px;">
														<button onclick="resetQry()" style="margin-left: 10px;" class="btn btn-info btn-label-left myBtn">
															<i class="fa fa-search">&nbsp;</i> 重置
														</button>
													</div>
													</div>
												</form>
											</div>
										</div>
									</div>
									<div class="col-xs-12">
										<div style="margin-bottom: 5px;"></div>
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
	<!-- 审核通过模块 -->
	<div id="verifiedModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:65%">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" >技能审核通过确认</h4>
			      </div>
				<div class="modal-body">
					<input type="text" class="form-control" style="display: none;" id="auditPassUserId" placeholder="">
					<p>
						申请技能资料审核正确，是否进行确认操作。
					</p>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="confirm" type="button" class="btn btn-success" onclick="verifiesConfirm()">
						<span><i class="icon-ok"></i></span> &nbsp;确&nbsp;&nbsp;认
					</button>
					<button data-bb-handler="cancel" type="button" data-dismiss="modal" class="cancel btn btn-danger cancel">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 审核不通过模块 -->
	<div id="unVerifiedModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:65%">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" >审核不通过信息反馈</h4>
			      </div>
				<div class="modal-body">
					<input type="text" class="form-control" style="display: none;" id="notApprovedUserId" placeholder="">
					<p>
						 技能审核资料不通过
					</p>
					<div>
						<label class="control-label" >填写原因：</label>
						<textarea class="form-control" rows="5" id="reason"></textarea>
					</div>
				</div>
				<div class="modal-footer" id="modifModalFooter">
					<button data-bb-handler="confirm" type="button" id="msaveSave" class="btn btn-success" onclick="unVerifiesConfirm()">
						<span><i class="icon-ok"></i></span> &nbsp;提&nbsp;&nbsp;交
					</button>
					<button data-bb-handler="cancel" type="button" data-dismiss="modal" class="cancel btn btn-danger cancel">取消</button>
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
<script src="<%=path%>/pages/reviewManager/abilityReview.js"></script>
<script type="text/javascript">
	var path='<%=path%>';
</script>
</body>
</html>