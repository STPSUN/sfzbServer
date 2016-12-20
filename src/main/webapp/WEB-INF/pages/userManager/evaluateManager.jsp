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
<title>评价管理</title>
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
														<label class="col-sm-1 control-label" style="width:100px;">评价人账号:</label>
														<div class="col-sm-2" style="width:170px">
															<input id="qryUserName" name="qryUserName" style="width:150px" type="text" class="form-control" placeholder="请输入评价人账号">
														</div>
														<label class="col-sm-1 control-label" style="width:115px;">被评价人账号:</label>
														<div class="col-sm-2" style="width:170px">
															<input id="qryTargetUserName" name="qryTargetUserName" style="width:150px" type="text" class="form-control" placeholder="被评价人账号">
														</div>
														<label class="col-sm-1 control-label" style="width: 8%;">评价时间:</label>
														<div class="col-sm-3" style="width: 215px;">
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
	<!-- 用户详情模块 -->
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
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly" onmouseover="this.title=this.value">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">星级:</label>
							<div class="col-sm-6">
								<input id="star" name="star" type="text" class="form-control" style="border-color:#fff;
										box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);" readonly="readonly">
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
	<!-- 项目详情模块 -->
	<div id="modifyModal" class="bootbox modal fade " tabindex="-1" role="dialog" style="width:100%;height:100%;border:0">
		<!-- <div class="modal-dialog" style="width:100%;height:100%,margin:0px,padding:0px"> -->
			<div class="modal-content" style="width:100%;">
				 <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="modifyModalLabel">正在加载中...</h4>
			      </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="modifyForm" onsubmit="return false;">
						
						<input id="projectId" name="projectId" type="hidden" class="form-control" placeholder="项目ID">
						<div class="form-group" >
							<label class="col-sm-2 control-label">项目名称</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="projectName" name="projectName" type="text" class="form-control" placeholder="项目名称" readonly="readonly" onmouseover="this.title=this.value">
							</div>
							
							<label class="col-sm-2 control-label">项目预算</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="budget" name="budget" type="text" class="form-control" placeholder="请输入项目预算" readonly="readonly" onmouseover="this.title=this.value">
							</div>
						</div>
						
						<div class="form-group" >
							<label class="col-sm-2 control-label">需求类别</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="categoryTypes" name="categoryTypes" type="text" class="form-control" placeholder="请输入需求类别" readonly="readonly" onmouseover="this.title=this.value">
							</div>
							
							<label class="col-sm-2 control-label">服务方式</label>
							<div class="col-sm-4" style="width: 25%;">
							<!-- 招标方式：common_tender 普通招标 | 包招 contain_tender -->
								<select id="tenderType" class="form-control" readonly="readonly">
									<option value="common_tender">普通招标</option>
									<option value="contain_tender">包招</option>
								</select>
							</div>
						</div>
					
						<div class="form-group" >
							<label class="col-sm-2 control-label">所需技能</label>
							<div class="col-sm-8" id="abilitysDiv">
								
									  <!--  <label><input type="checkbox" name="abilitys" value="" checked="checked">从用户角度思考能力</label>
									   <label><input type="checkbox" name="abilitys" value="">自我否定勇气</label>
									   <label><input type="checkbox" name="abilitys" value="">出色的分析能力</label>
									   <label><input type="checkbox" name="abilitys" value="">自我否定勇气</label> -->
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">需求地区</label>
							<div class="col-sm-8">
								<input id="regionDetail" name="regionDetail" type="text" class="form-control" placeholder="请输入需求地区" readonly="readonly" onmouseover="this.title=this.value">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">联系人</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="realName" name="realName" type="text" class="form-control" placeholder="请输入联系人" readonly="readonly" onmouseover="this.title=this.value">
							</div>
							
							<label class="col-sm-2 control-label">联系电话</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="telephone" name="telephone" type="text" class="form-control" placeholder="请输入联系电话" readonly="readonly" onmouseover="this.title=this.value">
							</div>
						</div>
					
						<div class="form-group" >
							<label class="col-sm-2 control-label">项目详情描述</label>
							<div class="col-sm-8">
								<textarea id="description" rows="7" class="form-control limited" maxlength="1000" placeholder="项目详情描述" readonly="readonly"></textarea>
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">截止报名时间</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="applyDeadline" name="applyDeadline" type="text" class="form-control" placeholder="请输入截止报名时间" readonly="readonly">
							</div>
							
							<label class="col-sm-2 control-label">项目截止时间</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="submitDeadline" name="submitDeadline" type="text" class="form-control" placeholder="请输入项目截止时间" readonly="readonly">
							</div>
						</div>
						<div class="form-group" >
						</div>
						<div class="form-group" >
						   <label class="col-sm-2 control-label"><input type="checkbox" value="" id="isMargin" >保证售后服务</label>
						   
						   <div class="col-sm-4" style="width: 25%;">
						   		<div class="input-group">
							   		<span class="input-group-addon">质保金比例</span>
									<input id="marginScale" name="marginScale" type="text" class="form-control" placeholder="请输入质保金比例" readonly="readonly">
						   		</div>
						   </div>
						   
						   <div class="col-sm-4" style="width: 25%;">
						   		<div class="input-group">
							        <span class="input-group-addon">质保期限</span>
									<input id="marginDay" name="marginDay" type="text" class="form-control" placeholder="请输入质保期限" readonly="readonly">
						   		</div>
						   </div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label"><input type="checkbox" value="" id="isReveal">是否需要兜底</label>
							
							<div class="col-sm-4" style="width: 25%;">
						   		<div class="input-group">
							        <span class="input-group-addon">兜底金比例</span>
									<input id="revealScale" name="revealScale" type="text" class="form-control" placeholder="请输入兜底金比例" readonly="readonly">
						   		</div>
						   </div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label "><input type="checkbox" value="">填写项目计划</label>
							<div class="col-sm-8">
								<button id="addPlanBtn" class="btn btn-info myBtn" onclick="addProjectPlan();">添加计划</button>
								<span id="projectPlanSpan"></span>
							</div>
						</div>
						
						<div class="form-group" >
							<label class="col-sm-2 control-label">项目进度</label>
							<div class="col-sm-8" id="projectProgressDiv">
								<!-- <table border="1">
									<tr>
										<td>序号</td> <td>上传时间</td> <td>位置</td> <td>现场</td>
									</tr>
								</table> -->
							</div>
						</div>
					</form>
					<div style="text-align:center;margin:0 auto">
						<p>创建时间：<span id="createTimeSpan">2016-10-21 09:26:14</span>&nbsp;&nbsp;&nbsp;&nbsp; 操作时间：<span id="updateTimeSpan">2016-10-21 09:26:14</span></p>
						<!-- 底部按钮 -->
						<span id="optBtnSpan">
							<span id = "auditBtnSpan">
								<button data-bb-handler="confirm" type="button" id="auditPass" class="btn btn-success" onclick="auditProject(1)">
									<span><i class="icon-ok"></i></span> 审核通过
								</button>
								<button data-bb-handler="confirm" type="button" id="msaveSave" class="btn btn-success" onclick="auditProject(2)">
									<span><i class="icon-ok"></i></span> 审核不通过
								</button>
							</span>
							<button data-bb-handler="confirm" type="button" id="msaveSave" class="btn btn-success" onclick="auditProject(3)">
								<span><i class="icon-ok"></i></span> 客户确认
							</button>
							<button data-bb-handler="confirm" type="button" id="msaveSave" class="btn btn-success" onclick="updateLog()">
								<span><i class="icon-ok"></i></span> 修改日志
							</button>
						</span>
						<button data-bb-handler="cancel" type="button" id="mcancerSave" modal="modifyModal" class="cancel btn btn-danger cancel">关闭</button>
					</div>
				</div>
				
			<!-- </div> -->
		</div>
	</div>
<script src="<%=path%>/pages/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="<%=path%>/pages/assets/js/jqGrid/i18n/grid.locale-en.js" type="text/javascript" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/moment.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/daterangepicker.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/date.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/layer/layer.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/userManager/evaluateManager.js"></script>
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