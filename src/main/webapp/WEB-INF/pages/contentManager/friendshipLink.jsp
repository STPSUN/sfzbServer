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
<title>友情链接管理</title>
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

.ui-jqgrid tr.jqgrow td {
white-space: normal !important;
height:auto;
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
<body style="background: #ffffff;" >
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
													<div class="col-sm-3">
														<label class="control-label" style="width: 80px;float: left;">位置分类：</label>
														<select class="form-control" id="qryAdvLocation" style="width: 120px; float: left;margin-left:12px;height:30px;">
															<option value=''>全部</option>
															<option value='6'>首页友情链接</option>
															<option value='7'>列表页友情链接</option>
															<option value='8'>新闻页友情链接</option>
															<option value='9'>分站友情链接</option>
														</select>
													</div>
													<div class="col-sm-3">
														<label class="control-label" style="width: 70px;float: left;">网站名称：</label>
														<input type="text" class="form-control" style="width: 100px;float: left;margin-left:12px" id="qryTitle" placeholder="网站名称查询">
													</div>
													<div class="col-sm-2" style="width:80px;">
														<button id="queryBtn" class="btn btn-info btn-label-left myBtn" >
															<i class="fa fa-search">&nbsp;</i> 查询
														</button>
													</div>
													<div class="col-sm-2" style="float:left;">
														<button class="btn btn-info myBtn" style="margin-left: 10px;" onclick="resetQry()" id="reset"><i class="fa fa-circle-o-notch">&nbsp;</i>重置</button>
													</div>
													<div class="col-sm-2" style="float:right;">
														<button id="addAdv" class="btn btn-info btn-label-left myBtn">
															<i class="fa fa-pencil-square-o">&nbsp;</i> 新增友情链接
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
	<!-- 友情链接新增，修改模块 -->
	<div id="showModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:50%">
			<div class="modal-content">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="addModalLabel">添加广告</h4>
			      </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="showForm" onsubmit="return false;">
						<div class="form-group" >
							<label class="col-sm-3 control-label" style="text-align:right;">网站名称</label>
							<div class="col-sm-8">
						        <input id="title" name="title" type="text" class="form-control" style="
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);height:34px;">
							</div>
							<label class="control-label col-sm-3" style="text-align:right;margin-top:10px;">位置分类</label>
							<div class="col-sm-8" style="margin-top:10px;">
								<select class="form-control" id="advLocation" style="width: 200px; float: left;height:34px;">
									<option value=''>请选择</option>
									<option value='6'>首页友情链接</option>
									<option value='7'>列表页友情链接</option>
									<option value='8'>新闻页友情链接</option>
									<option value='9'>分站友情链接</option>
								</select>
							</div>
							<label class="control-label col-sm-3" style="text-align:right;margin-top:10px;">具体位置</label>
							<div class="col-sm-8" style="margin-top:10px;">
								<select class="form-control" id="advLocationDetail" style="width: 200px; float: left;height:34px;">
									<option value=''>请选择</option>
								</select>
							</div>
							<label class="col-sm-3 control-label" style="text-align:right;margin-top:10px;">详情链接</label>
							<div class="col-sm-8" style="margin-top:10px;">
						        <input id="advLink" name="advLink" type="text" class="form-control" style="
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);height:34px;">
							</div>
							<label class="col-sm-3 control-label" style="text-align:right;margin-top:10px;">联系人</label>
							<div class="col-sm-8" style="margin-top:10px;">
						        <input id="advUser" name="advUser" type="text" class="form-control" style="
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);height:34px;">
							</div>
							<label class="col-sm-3 control-label" style="text-align:right;margin-top:10px;">联系方式</label>
							<div class="col-sm-8" style="margin-top:10px;">
						        <input id="advUserMobile" name="advUserMobile" type="text" class="form-control" style="
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);height:34px;">
							</div>
							<label class="control-label col-sm-3" style="text-align:right;margin-top:10px;">顺序</label>
							<div class="col-sm-8" style="margin-top:10px;">
								<input id="advSort" name="advSort" type="text" class="form-control" style="
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);height:34px;" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="confirm" type="button" id="pass" class="btn btn-success" onclick="changeSend()">
						&nbsp;提交&nbsp;
					</button>
					<button data-bb-handler="cancel" type="button" id="showCancel" modal="showModal" class="cancel btn btn-danger cancel">取消</button>
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
<script src="<%=path%>/pages/contentManager/friendshipLink.js"></script>
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
</script>
</body>
</html>