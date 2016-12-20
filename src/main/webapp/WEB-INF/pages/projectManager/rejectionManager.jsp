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
<title>拒收管理</title>
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
														<label class="col-sm-1 control-label" style="width: 8%;">处理状态:</label>
														<select class="form-control" id="adminReviewStateSea" style="width: 200px; float: left;margin-left:12px">
															<option value="">全部</option>
															<option value='0'>未处理</option>
															<option value='1'>拒绝</option>
															<option value='2'>同意拒收</option>
														</select>
														
														<label class="col-sm-1 control-label" style="width: 8%;">项目名称:</label>
														<input  class="form-control"  style="width: 175px; float: left;margin-left:12px" placeholder="项目名称搜索" type="text" id="projectNameSea">
														
														<label class="col-sm-1 control-label" style="width: 8%;">申请时间:</label>
														<div class="col-sm-3" style="width: 225px;">
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
	
	<!-- =================同意或者驳回=========================================== -->
	<div id="auditRejectionModal" class="bootbox modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="myModalLabel">同意拒收</h4>
			     </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<div class="form-group" style="display: none;">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-8">
								<input id="projectId" type="text" class="form-control" placeholder="输入id">
								<input id="auditType" type="text" class="form-control" placeholder="">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">核实理由</label>
							<div class="col-sm-8">
								<textarea id="rejectionReason" rows="4" class="form-control limited" maxlength="1000" placeholder="填写核实原因"></textarea>
							</div>
						</div>
						
					</form>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="confirm" type="button" id="saveProjectPlan" class="btn btn-success" onclick="confirmAuditRejection();">
						<span><i class="icon-ok"></i></span> &nbsp;确&nbsp;&nbsp;认
					</button>
					<button data-bb-handler="cancel" type="button" id="cancleProjectPlan" modal="auditRejectionModal" class="cancel btn btn-danger cancel">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- =================附件查看窗口=========================================== -->
	<div id="showAttachModal" class="bootbox modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="myModalLabel">查看附件</h4>
			     </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						
						<div class="form-group">
							<div id="showImgDiv"></div>
							<div id="downloadAttachDiv"></div>
						</div>
						
					</form>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="cancel" type="button" id="showAttachModalCancelBtn" modal="showAttachModal" class="cancel btn btn-danger cancel">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<!-- =================大图查看窗口=========================================== -->
	<div id="showBigImgModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:50%">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="">查看图片</h4>
			    </div>
				<div class="modal-body" id="bigImgDiv">
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
<script src="<%=path%>/pages/projectManager/rejectionManager.js"></script>
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