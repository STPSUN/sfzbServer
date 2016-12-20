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
<title>广告管理</title>
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
														<label class="control-label" style="width: 80px;float: left;">广告类型：</label>
														<select class="form-control" id="qryAdvClientType" style="width: 120px; float: left;margin-left:12px;height:30px;">
															<option value=''>全部</option>
															<option value='PC_CLIENT'>PC端</option>
															<option value='IOS_CLIENT'>IOS端</option>
															<option value='ANDROID_CLIENT'>安卓端</option>
														</select>
													</div>
													<div class="col-sm-3">
														<label class="control-label" style="width: 80px;float: left;">广告位置：</label>
														<select class="form-control" id="qryAdvLocation" style="width: 120px; float: left;margin-left:12px;height:30px;">
															<option value=''>全部</option>
															<option value='0'>PC首页banner</option>
															<option value='1'>APP首页banner</option>
															<option value='2'>首页底部</option>
															<option value='3'>列表页底部</option>
															<option value='4'>详情页底部</option>
															<option value='5'>其他底部</option>
														</select>
													</div>
													<div class="col-sm-3">
														<label class="control-label" style="width: 70px;float: left;">广告名称：</label>
														<input type="text" class="form-control" style="width: 100px;float: left;margin-left:12px" id="qryTitle" placeholder="广告名称查询">
													</div>
													<div class="col-sm-3">
														<label class="control-label" style="width: 80px;float: left;">广告状态：</label>
														<select class="form-control" id="qryStatus" style="width: 120px; float: left;margin-left:12px;height:30px;">
															<option value=''>全部</option>
															<option value='0'>启用</option>
															<option value='1'>停用</option>
														</select>
													</div>
													<label class="col-sm-1 control-label" style="width: 105px;margin-top:10px;">创建时间：</label>
													<div class="col-sm-3" style="width: 225px;margin-top:10px;">
														<input data-original-title="Tooltip for name" class="form-control" placeholder="起始时间--截止时间" type="text" id="dateRange" readonly="readonly">
													</div>
													<div class="col-sm-2" style="margin-top:10px;text-align:right;">
														<button id="queryBtn" class="btn btn-info btn-label-left myBtn" >
															<i class="fa fa-search">&nbsp;</i> 查询
														</button>
													</div>
													<div class="col-sm-2" style="float:left;margin-top:10px;">
														<button class="btn btn-info myBtn" style="margin-left: 10px;" onclick="resetQry()" id="reset"><i class="fa fa-circle-o-notch">&nbsp;</i>重置</button>
													</div>
													<div class="col-sm-2" style="float:right;margin-top:10px;">
														<button id="addAdv" class="btn btn-info btn-label-left myBtn">
															<i class="fa fa-pencil-square-o">&nbsp;</i> 新增广告
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
	<!-- 广告新增，修改模块 -->
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
							<label class="col-sm-3 control-label" style="text-align:right;">广告名称</label>
							<div class="col-sm-8">
						        <input id="title" name="title" type="text" class="form-control" style="
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);height:34px;">
							</div>
							
							<label class="col-sm-3 control-label" style="text-align:right;margin-top:10px;">广告图片</label>
							<div class="col-sm-9" style="margin-top:10px;">
								<input id="advImgFileText" name="advImgFileText" type="text" readonly="readonly" class="form-control" style="height:34px;width:240px">
								<a href="javascript:;" class="file" style="margin-left:250px;height:34px; position:absolute; bottom:0;cursor:pointer">选择文件
									<input id="advImgFile" name="advImgFile" type="file" accept="image/*" onchange="showFileName()">
								</a>
							</div>
							
							<label class="control-label col-sm-3" style="text-align:right;margin-top:10px;">广告类型</label>
							<div class="col-sm-8" style="margin-top:10px;">
								<select class="form-control" id="advClientType" style="width: 200px; float: left;height:34px;">
									<option value=''>请选择</option>
									<option value='PC_CLIENT'>PC端</option>
									<option value='IOS_CLIENT'>IOS端</option>
									<option value='ANDROID_CLIENT'>安卓端</option>
								</select>
							</div>
							<label class="control-label col-sm-3" style="text-align:right;margin-top:10px;">广告位置</label>
							<div class="col-sm-8" style="margin-top:10px;">
								<select class="form-control" id="advLocation" style="width: 200px; float: left;height:34px;">
									<option value=''>请选择</option>
									<option value='0'>PC首页banner</option>
									<option value='1'>APP首页banner</option>
									<option value='2'>首页底部</option>
									<option value='3'>列表页底部</option>
									<option value='4'>详情页底部</option>
									<option value='5'>其他底部</option>
								</select>
							</div>
							<label class="col-sm-3 control-label" style="text-align:right;margin-top:10px;">广告链接</label>
							<div class="col-sm-8" style="margin-top:10px;">
						        <input id="advLink" name="advLink" type="text" class="form-control" style="
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);height:34px;">
							</div>
							<label class="col-sm-3 control-label" style="text-align:right;margin-top:10px;">播放时间</label>
							<div class="col-sm-8" style="height:34px;margin-top:10px;">
								<input data-original-title="Tooltip for name" class="form-control" placeholder="开始时间--结束时间" type="text" id="playTime" readonly="readonly"
								style="width: 225px;">
							</div>
							<label class="col-sm-3 control-label" style="text-align:right;margin-top:10px;">投放区域</label>
							<div class="col-sm-8" style="margin-top:10px;">
								<select class="form-control" id="playArea" style="width: 200px; float: left;height:34px;">

								</select>
							</div>
							<label class="control-label col-sm-3" style="text-align:right;margin-top:10px;">广告排序</label>
							<div class="col-sm-8" style="margin-top:10px;">
								<select class="form-control" id="advSort" style="width: 200px; float: left;height:34px;">
									<!-- 
									<option value=''>全部</option>
									 -->
								</select>
							</div>
							
							<label class="col-sm-3 control-label" style="text-align:right;margin-top:10px;">广告内容</label>
							<div class="col-sm-8">
						        <textarea id="advContent" name="advContent" rows="5" class="form-control" style="
										box-shadow:inset 1 1px 1px rgba(0,0,0,0.075);margin-top:10px;">
								</textarea>
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
<script src="<%=path%>/pages/assets/js/ajaxfileupload.js"></script>
<script src="<%=path%>/pages/assets/js/jqGrid/i18n/grid.locale-en.js" type="text/javascript" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/moment.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/daterangepicker.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/date.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/layer/layer.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/contentManager/adBanners.js"></script>
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
	$('#playTime').daterangepicker({});
</script>
</body>
</html>