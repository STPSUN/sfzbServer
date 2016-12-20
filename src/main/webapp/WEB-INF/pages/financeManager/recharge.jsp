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
<title>充值管理</title>
<link rel="stylesheet" type="text/css" media="all" href="<%=path%>/pages/assets/css/daterangepicker-bs3.css"/>
<link rel="stylesheet" type="text/css" media="all" href="<%=path%>/pages/assets/css/bootstrap-datetimepicker.css"/>
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
													<div class="form-group">
														<label class="col-sm-2 control-label">充值方式:</label>
														<div class="col-sm-2">
															<select class="form-control" id="qryTradePlatform" style="width: 150px; float: left;">
																<option value=''>全部</option>
																<option value='alipay'>支付宝</option>
																<option value='wechatpay'>微信支付</option>
																<option value='unionpay'>银联支付</option>
																<option value='offline'>线下汇款</option>
															</select>
														</div>
														<label class="col-sm-2 control-label">充值状态:</label>
														<div class="col-sm-2">
															<select class="form-control" id="qryTradeState" style="width: 175px; float: left;">
																<option value="">全部</option>
																<option value='0'>待付款</option>
																<option value='1'>支付成功</option>
																<option value='-1'>支付失败</option>
															</select>
														</div>
														<div class="col-sm-1" style="float:right;margin-right: 7%;">
															<button class="btn btn-info myBtn" onclick="resetQry()" id="reset"><i class="fa fa-circle-o-notch">&nbsp;</i> 重置</button>
														</div>
													</div>
													<div class="form-group" style="margin-bottom: 0;">
														<label class="col-sm-2 control-label">联系手机:</label>
														<div class="col-sm-2">
															<input id="qryMobile" name="qryMobile" style="width:150px" type="text" class="form-control" placeholder="请输入手机号码">
														</div>
														<label class="col-sm-2 control-label">充值时间:</label>
														<div class="col-sm-3" style="width: 200px;">
															<input data-original-title="Tooltip for name" class="form-control" placeholder="起始时间--截止时间" type="text" id="dateRange" readonly="readonly">
														</div>
														<div class="col-sm-1" style="float:right;margin-right: 7%;">
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
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);">
							</div>
							<label class="col-sm-2 control-label">性别:</label>
							<div class="col-sm-3">
						        <input id="sex" name="sex" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">联系电话:</label>
							<div class="col-sm-6">
								<input id="mobile" name="mobile" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">星级:</label>
							<div class="col-sm-6">
								<input id="star" name="star" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);">
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
	<!-- 核对模块 -->
	<div id="auditModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:400px">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="auditModalLabel">核对信息</h4>
			      </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="auditForm" onsubmit="return false;">
					    <div class="form-group" >
					    <input id="auditRecordId" name="auditRecordId" type="hidden">
					    <input id="auditPayUserId" name="auditPayUserId" type="hidden">
							<label class="col-sm-4 control-label">到账时间:</label>
							<div class="col-sm-7">
						        	<input id="auditBankAccountTime" name="auditBankAccountTime" type="text" class="form-control" placeholder="请输入到账时间">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-4 control-label">到账金额:</label>
							<div class="col-sm-7">
						        <input id="auditMoney" name="auditMoney" type="text" class="form-control floatDecText" placeholder="请输入到账金额">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-4 control-label">订单流水号:</label>
							<div class="col-sm-7">
						        <input id="auditBankSerialId" name="auditBankSerialId" type="text" class="form-control numDecText" placeholder="请输入订单流水号">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer" id="auditModalFooter" style="text-align: center">
					<button data-bb-handler="confirm" type="button" id="pass" class="btn btn-success" onclick="auditSend('2')">
						&nbsp;通过&nbsp;
					</button>
					<button data-bb-handler="confirm" type="button" id="noPass" class="btn btn-success" onclick="auditSend('1')">
						&nbsp;不通过&nbsp;
					</button>
				</div>
			</div>
		</div>
	</div>
<script src="<%=path%>/pages/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="<%=path%>/pages/assets/js/jqGrid/i18n/grid.locale-en.js" type="text/javascript" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/moment.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/daterangepicker.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/date.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/layer/layer.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/financeManager/recharge.js"></script>
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
	$('#auditBankAccountTime').datetimepicker({
	    format: 'yyyy-mm-dd hh:ii:ss',
	    language: 'zh-CN',
	    todayBtn: true,
	    autoclose: true,
	    todayHighlight:true
	});
	
</script>
</body>
</html>