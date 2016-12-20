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
<title>兜底管理</title>
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
														<label class="col-sm-1 control-label" style="width: 8%;">兜底状态:</label>
														<select class="form-control" id="adminReviewStateSea" style="width: 175px; float: left;margin-left:12px">
															<option value="">全部</option>
															<option value='0'>已提交未审批</option>
															<option value='1'>审核通过</option>
															<option value='2'>审批拒绝</option>
														</select>
														
														<label class="col-sm-1 control-label" style="width: 8%;">关键字:</label>
														<input  class="form-control"  style="width: 175px; float: left;margin-left:12px" placeholder="用户关键字搜索" type="text" id="userSea">
														
														<label class="col-sm-1 control-label" style="width: 8%;">生成时间:</label>
														<div class="col-sm-3" style="width: 200px;">
															<input data-original-title="Tooltip for name" class="form-control" placeholder="开始时间--结束时间" type="text" id="dateRange" readonly="readonly">
														</div>
														<div class="col-sm-1" style="float:right;margin-right: 3%;">
															<button class="btn btn-info myBtn" onclick="resetAppQry()" id="reset"><i class="fa fa-circle-o-notch">&nbsp;</i>重置</button>
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
	
	<!-- 查看项目模块 -->
	<div id="showProjectModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:65%">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="modifyModalLabel">项目详情</h4>
			      </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="modifyForm" onsubmit="return false;">
						
						<div class="form-group" >
							<label class="col-sm-2 control-label">项目名称</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="projectName" name="projectName" type="text" class="form-control" placeholder="">
							</div>
							
							<label class="col-sm-2 control-label">项目预算</label>
							<div class="col-sm-4" style="width: 25%;">
								 <input id="budget" name="budget" type="text" class="form-control" placeholder="">
							</div>
						</div>
						
						<div class="form-group" >
							<label class="col-sm-2 control-label">需求类别</label>
							<div class="col-sm-8">
								<input id="tenderType" name="tenderType" type="text" class="form-control" placeholder="">
							</div>
						</div>
				
						<div class="form-group" >
							<label class="col-sm-2 control-label">需求地区</label>
							<div class="col-sm-8">
								<input id="regionDetail" name="regionDetail" type="text" class="form-control" placeholder="">
							</div>
						</div>
						
						<div class="form-group" >
							<label class="col-sm-2 control-label">项目详细描述</label>
							<div class="col-sm-8">
								<textarea rows="5" id="description" name="description" class="form-control" placeholder="">
								</textarea>
							</div>
						</div>
						
						<div class="form-group" >
							<label class="col-sm-2 control-label">报名截止时间</label>
							<div class="col-sm-8">
								<input id="applyDeadline" name="applyDeadline" type="text" class="form-control" placeholder="">
							</div>
						</div>
						
						<div class="form-group" >
							<label class="col-sm-2 control-label">项目截止时间</label>
							<div class="col-sm-8">
								<input id="submitDeadline" name="submitDeadline" type="text" class="form-control" placeholder="">
							</div>
						</div>
						
						<hr>
						<div class="form-group">
							<label class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-8">
								<input id="realName" name="realName" type="text" class="form-control" placeholder="">
							</div>
						</div>
					
						<div class="form-group">
							<label class="col-sm-2 control-label">联系方式</label>
							<div class="col-sm-8">
								<input id="userMobel" name="userMobel" type="text" class="form-control" placeholder="">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">身份证号</label>
							<div class="col-sm-8">
								<input id="idcardCode" name="idcardCode" type="text" class="form-control" placeholder="">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">星级评价</label>
							<div class="col-sm-4" style="width: 25%;">
								<div class="input-group">
									<input id="avgStars" name="countStars" type="text" placeholder="" class="form-control"/>
									<span class="input-group-addon">星</span>
								</div>
							</div>
							
							<label class="col-sm-2 control-label">接单总数</label>
							<div class="col-sm-4" style="width: 25%;">
								<div class="input-group">
									<input id="hisOrderCount" name="hisOrderCount" type="text" class="form-control" placeholder="">
									<span class="input-group-addon">单</span>
								</div>
							</div>
						</div>
					
						<div class="form-group">
							
						</div>
						
					</form>
				</div>
				<div class="modal-footer" id="modifModalFooter">
					<!-- <button data-bb-handler="confirm" type="button" id="msaveSave" class="btn btn-success" onclick="editAppSend('m',this)">
						<span><i class="icon-ok"></i></span> &nbsp;修&nbsp;&nbsp;改
					</button> -->
					<button data-bb-handler="cancel" type="button" modal="showProjectModal" class="cancel btn btn-danger cancel">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<!-- =================启动兜底=========================================== -->
	<div id="startFallbackModal" class="bootbox modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="showUpdateLogModalLabel">启用兜底服务</h4>
			     </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" onsubmit="return false;">
						<input id="startFallbackProjectId" type="hidden" class="form-control" placeholder="">
						<div class="form-group">
							<label class="col-sm-2 control-label">指派接替人</label>
							<div class="col-sm-8">
								<input id="startFallbackContact" type="text" class="form-control" placeholder="工程师注册手机号">
							</div>
						</div>
						
					</form>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="confirm" type="button" id="" class="btn btn-success" onclick="startFallback();">
						<span><i class="icon-ok"></i></span> 启动兜底服务
					</button>
					<button data-bb-handler="cancel" type="button" id="" modal="startFallbackModal" class="cancel btn btn-danger cancel">关闭</button>
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
<script src="<%=path%>/pages/projectManager/fallbackManager.js"></script>
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