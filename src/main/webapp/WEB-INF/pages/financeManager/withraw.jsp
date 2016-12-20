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
<title>提现管理</title>
<link rel="stylesheet" type="text/css" media="all" href="<%=path%>/pages/assets/css/daterangepicker-bs3.css"/>
<link href="<%= basePath%>/pages/assets/css/carousel.css" rel="stylesheet">
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
													<div class="form-group" style="margin-bottom: 5px;">
														<label class="col-sm-2 control-label" style="width: 100px;">提现方式:</label>
														<div class="col-sm-3" style="width: 240px;">
															<select class="form-control" id="qryApplyType" style="width: 120px;">
																<option value="">全部</option>
																<option value='alipay'>支付宝</option>
																<option value='unionpay'>银联</option>
															</select>
														</div>
														<label class="col-sm-2 control-label" style="width: 100px;">联系手机:</label>
														<div class="col-sm-3">
															<input type="text" class="form-control numDecText" style="width: 200px;" id="qryMobile" placeholder="请输入联系手机">
														</div>
													</div>
													<div class="form-group" style="margin-bottom: 0;">
														<label class="col-sm-2 control-label" style="width: 100px;">款项状态:</label>
														<div class="col-sm-3" style="width: 240px;">
															<select class="form-control" id="qryReviewState" style="width: 120px;">
																<option value="">全部</option>
																<option value='watting'>待审批</option>
																<option value='withdraw_succeed'>提现成功</option>
																<option value='withdraw_failse'>提现失败</option>
															</select>
														</div>
														<label class="col-sm-2 control-label" style="width: 100px;">申请时间:</label>
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
	
	<!-- 审核模块 -->
	<div id="withrawModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:600px">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="withrawModalLabel">转账成功</h4>
			      </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="withrawForm" onsubmit="return false;">
					    <input id="actionType" name="actionType" type="hidden" class="form-control" placeholder="">
					    <input id="recordId" name="recordId" type="hidden" class="form-control" placeholder="">
						<div class="form-group" >
							<label class="col-sm-6 control-label" style="text-align: left">支付账户名称：&nbsp;<span id="realName"></span></label>
							<label class="col-sm-6 control-label" style="text-align: left">提现金额：&nbsp;<span id="applyMoney"></span></label>
						</div>
						<div class="form-group" >
							<label class="col-sm-6 control-label" style="text-align: left">提现方式：&nbsp;<span id="applyType"></span></label>
							<label class="col-sm-6 control-label" style="text-align: left">收款账号：&nbsp;<span id="applyCode"></span></label>
						</div>
						<div class="form-group" >
							<label class="col-sm-12 control-label">
								<div style="float: left;margin-top: 5px;">备注：</div>
								<div style="float: left">
									<input id="applyReason" class="form-control limited" maxlength="1000" placeholder="备注信息" style="width:450px"/>
								</div>
							</label>
						</div>
					</form>
					<div class="form-group" >
						<div style="text-align:center;margin:0 auto" id="optMsg">
						</div>
					</div>
				</div>
				<div class="modal-footer" id="auditModalFooter" style="text-align: center">
					<button data-bb-handler="confirm" type="button" id="topass" class="btn btn-success" onclick="withrawActionConfirm()">
						&nbsp;确认&nbsp;
					</button>
					<button data-bb-handler="cancel" type="button" id="tocancel" modal="withrawModal" class="cancel btn btn-danger cancel">取消</button>
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
<script src="<%=path%>/pages/financeManager/withraw.js"></script>
<script src="<%=path%>/pages/assets/js/uploadPreview.js"></script>
<script type="text/javascript">
	var path='<%=path%>';
	$(function(){
		$(".cancel").click(function(){
			$("#"+$(this).attr("modal")).modal("hide");
		});
		
		/*JQuery 限制文本框只能输入数字和小数点*/  
        $(".numDecText").keyup(function(){    
                $(this).val($(this).val().replace(/[^0-9]/g,''));    
            }).bind("paste",function(){  //CTR+V事件处理    
                $(this).val($(this).val().replace(/[^0-9]/g,''));     
            }).css("ime-mode", "disabled"); //CSS设置输入法不可用   
       /*JQuery 限制文本框只能输入数字和小数*/
      	$(".floatDecText").keyup(function(){    
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