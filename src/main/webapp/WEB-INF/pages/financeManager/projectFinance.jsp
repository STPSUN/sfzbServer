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
<link href="<%= basePath%>/pages/assets/css/public.css" rel="stylesheet">
<link href="<%= basePath%>/pages/assets/css/style.css" rel="stylesheet">

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
													<div class="form-group">
														<label class="col-sm-1 control-label">切换:</label>
														<div class="col-sm-2">
															<select class="form-control" id="qryType" style="width: 150px;" onChange="chgList(this.value)">
																<option value="1">用户账户资金</option>
																<option value='2'>平台账户资金</option>
															</select>
														</div>
														<div id="platQuerys" style="display: none">
															<label class="col-sm-1 control-label">业务类型:</label>
															<div class="col-sm-2">
																<select class="form-control" id="qryTargetType" style="width: 150px;">
																	<option value="">全部</option>
																	<option value="fullmoney">全款</option>
																	<option value="imprestmoney">预付款</option>
																	<option value="revealmoney">兜底服务费</option>
																	<option value="remainmoney">尾款</option>
																	<option value='returnmoney'>退款</option>
																	<option value='marginmoney'>质保金</option>
																</select>
															</div>
															<label class="col-sm-1 control-label">交易时间:</label>
															<div class="col-sm-2" style="width: 220px;">
																<input data-original-title="Tooltip for name" class="form-control" placeholder="起始时间--截止时间" type="text" id="dateRange" readonly="readonly">
															</div>
														</div>
													</div>
													<div class="form-group" style="margin-bottom: 0;">
														<label class="col-sm-1 control-label">用户账号:</label>
														<div class="col-sm-2">
															<input id="qryMobile" name="qryMobile" style="width:150px" type="text" class="form-control" placeholder="请输入用户账号">
														</div>
														<label class="col-sm-1 control-label">昵称:</label>
														<div class="col-sm-2">
															<input id="qryNickName" name="qryNickName" style="width:150px" type="text" class="form-control" placeholder="请输入昵称">
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
										<div id="userFinance" style="margin-bottom: 5px;">
											<table id="user-grid-table"></table>
											<div id="user-grid-pager"></div>
										</div>
										<div id="platFinance" style="margin-bottom: 5px;">
											<table id="plat-grid-table"></table>
											<div id="plat-grid-pager"></div>
										</div>
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
	<div id="showUserModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:800px">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="addModalLabel">用户资金详情</h4>
			      </div>
				<div class="modal-body" style="max-height:650px;">
					<form class="form-horizontal" role="form" id="showForm" onsubmit="return false;">
						<div class="form-group" style="margin-bottom: 2px;border-bottom:1px solid #F5F5F5">
							<label class="col-sm-2 control-label">用户昵称:</label>
							<label class="col-sm-2 control-label" style="text-align: left" id="nickName"></label>
							<label class="col-sm-2 control-label" style="width:150px;">托管中的项目资金:</label>
							<label class="col-sm-1 control-label" id="projectMoney"></label>
							<label class="col-sm-2 control-label">余额资金:</label>
							<label class="col-sm-1 control-label" style="text-align: left" id="money"></label>
						</div>
						<div class="findmaList form-group">
					    <div class="findlnavs"><a href="javascript:void(0)" class="findnavOn" id="pjnav" onclick="chgUserDetail(1);">项目交易</a><a href="javascript:void(0)" id="wrnav" onclick="chgUserDetail(2);">充值提现</a></div>
					</form>
					<div class="tximalis" id="pjTximalis"></div>
					<div class="tximalis" id="wrTximalis"></div>
				</div>
				<!-- <div class="modal-footer">
					<button data-bb-handler="cancel" type="button" id="showCancel" modal="showModal" class="cancel btn btn-danger cancel">取消</button>
				</div> -->
			</div>
		</div>
	</div>
<script src="<%=path%>/pages/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="<%=path%>/pages/assets/js/jqGrid/i18n/grid.locale-en.js" type="text/javascript" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/moment.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/daterangepicker.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/date.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/layer/layer.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/pagination.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/financeManager/projectFinance.js"></script>
<script src="<%=path%>/pages/assets/js/uploadPreview.js"></script>
<script type="text/javascript">
	var path='<%=path%>';
	var toPageNum=0;
	var pageSize=10;
	function getPageNum(){
		  alert(toPageNum+1);
	}
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