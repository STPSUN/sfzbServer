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
<title>项目管理</title>
<link rel="stylesheet" type="text/css" media="all" href="<%=path%>/pages/assets/css/daterangepicker-bs3.css"/>
<link href="<%=path%>/pages/assets/css/datepicker.css" rel="stylesheet" />
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
														<label class="col-sm-1 control-label" style="width: 8%;">项目状态:</label>
														<select class="form-control" id="reviewStateSea" style="width: 10%; float: left;margin-left:12px">
															<option value="">全部</option>
															<!-- //项目状态:-1：已删除,0：已提交未审批,3：待客户确认,4:未中标,5：雇主选标中/接包者报名中/已发布（审批通过）,6：项目进行中/已中标,7：已完结,8：待验收,9：已失效（审批拒绝） -->
															<option value='0'>已提交未审批</option>
															<option value='2'>审批拒绝</option>
															<option value='3'>待客户确认</option>
															<option value='4'>未中标</option>
															<option value='5'>选标中</option>
															<option value='6'>已中标</option>
															<option value='7'>已完结</option>
															<option value='8'>待验收</option>
															<option value='9'>已失效</option>
														</select>
														
														<label class="col-sm-1 control-label" style="width: 9%;">用户关键字:</label>
														<div class="col-sm-3" style="width: 200px;">
															<input class="form-control" placeholder="搜索用户" type="text" id="userKeyWordSea" >
														</div>
														
														<label class="col-sm-1 control-label" style="width: 8%;">区域:</label>
														<div class="col-sm-3" style="width: 200px;">
															<input class="form-control" placeholder="请输入区域" type="text" id="areaSea">
														</div>
														
														<label class="col-sm-1 control-label" style="width: 8%;">项目方式:</label>
														<div class="col-sm-3" style="width: 10%;"><!--  style="width: 200px; float: left;margin-left:12px" -->
															<select class="form-control" id="tenderTypeSea">
															<option value="">全部</option>
															<option value='common_tender'>普通招标</option>
															<option value='contain_tender'>包标</option>
														</select>
														</div>
														
														<div style="clear:both">
														
															<div class="form-group" style="margin-bottom: 0;">
															
																<label class="col-sm-1 control-label" style="margin-left:3px">申请时间:</label>
																<div class="col-sm-3" style="width: 225px;">
																	<input data-original-title="Tooltip for name" class="form-control" placeholder="开始时间--结束时间" type="text" id="dateRange" readonly="readonly">
																</div>
																
																<div style="float:right">
																	<div class="col-sm-1"  style="margin-right:40px">
																		<button id="queryBtn" class="btn btn-info btn-label-left myBtn">
																			<i class="fa fa-search">&nbsp;</i> 查询
																		</button>
																	</div>
																	<!-- <div class="col-sm-1">
																		<button class="btn btn-info myBtn" onclick="resetAppQry()" id="reset"><i class="fa fa-circle-o-notch">&nbsp;</i>重置</button>
																	</div> -->
																</div>
															</div>
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
	
	<!-- 修改模块 -->
	<div id="modifyModal" class="bootbox modal fade" tabindex="-1" role="dialog" style="width:100%;height:100%;border:0">
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
								<input id="projectName" name="projectName" type="text" class="form-control" placeholder="项目名称" onchange="changeProjectValue()">
							</div>
							
							<label class="col-sm-2 control-label">项目预算</label>
							<div class="col-sm-4" style="width: 25%;">
								 <input id="budget" name="budget" type="text" class="form-control" placeholder="请输入项目预算"  onchange="changeProjectValue()" onkeyup="if(/\D/g.test(this.value)){this.value=this.value.replace(/\D/g,'');}" 
								         		onafterpaste="if(/\D/g.test(this.value)){this.value=this.value.replace(/\D/g,'');}"> 
							</div>
						</div>
						
						<div class="form-group" >
							<label class="col-sm-2 control-label">需求类别</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="categoryTypesSelect" name="categoryTypesSelect" type="text" class="form-control" readonly="readonly"/>
								<!-- <p id="categoryTypesSelect" class="form-control"></p> -->
								<!-- <select id="categoryTypesSelect" class="form-control">
								</select> -->
								<!-- <input id="categoryTypes" name="categoryTypes" type="text" class="form-control" placeholder="请输入需求类别"> -->
							</div>
							
							<label class="col-sm-2 control-label">服务方式</label>
							<div class="col-sm-4" style="width: 25%;">
							<!-- 招标方式：common_tender 普通招标 | 包招 contain_tender -->
								<select id="tenderType" class="form-control" onchange="changeProjectValue()">
									<option value="common_tender">普通招标</option>
									<option value="contain_tender">包招</option>
								</select>
							</div>
						</div>
					
						<div class="form-group" >
							<label class="col-sm-2 control-label">所需技能</label>
							<div class="col-sm-8">
								<input id="abilitysDiv" name="abilitysDiv" type="text" class="form-control" readonly="readonly"/>
									  <!--  <label><input type="checkbox" name="abilitys" value="" checked="checked">从用户角度思考能力</label>
									   <label><input type="checkbox" name="abilitys" value="">自我否定勇气</label>
									   <label><input type="checkbox" name="abilitys" value="">出色的分析能力</label>
									   <label><input type="checkbox" name="abilitys" value="">自我否定勇气</label> -->
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">需求地区</label>
							<div class="col-sm-8">
								<input id="regionDetail" name="regionDetail" type="text" class="form-control" placeholder="请输入需求地区"  onchange="changeProjectValue()">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">联系人</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="realName" name="realName" type="text" class="form-control" placeholder="请输入联系人"  onchange="changeProjectValue()">
							</div>
							
							<label class="col-sm-2 control-label">联系电话</label>
							<div class="col-sm-4" style="width: 25%;">
								<input id="telephone" name="telephone" type="text" class="form-control" placeholder="请输入联系电话"  onchange="changeProjectValue()">
							</div>
						</div>
					
						<div class="form-group" >
							<label class="col-sm-2 control-label">项目详情描述</label>
							<div class="col-sm-8">
								<textarea id="description" rows="7" class="form-control limited" maxlength="1000" placeholder="项目详情描述"  onchange="changeProjectValue()"></textarea>
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">截止报名时间</label>
							<div class="col-sm-4" style="width: 25%;">
								<!-- <input id="applyDeadline" name="applyDeadline" type="text" class="form-control" placeholder="请输入截止报名时间" onchange="changeProjectValue()"> -->
								
								<input type="text" class="form-control" value="" id="applyDeadline" onchange="changeProjectValue()"  data-date-format="yyyy-mm-dd">
							</div>
							
							<label class="col-sm-2 control-label">项目截止时间</label>
							<div class="col-sm-4" style="width: 25%;">
								<!-- <input id="submitDeadline" name="submitDeadline" type="text" class="form-control" placeholder="请输入项目截止时间" onchange="changeProjectValue()"> -->
								<input type="text" class="form-control" value="" id="submitDeadline" onchange="changeProjectValue()"  data-date-format="yyyy-mm-dd">
							</div>
						</div>
						<div class="form-group" >
						</div>
						<div class="form-group" >
						   <label class="col-sm-2 control-label"><input type="checkbox" value="" id="isMargin"  onchange="changeProjectValue()">保证售后服务</label>
						   
						   <div class="col-sm-4" style="width: 25%;">
						   		<div class="input-group">
							   		<span class="input-group-addon">质保金比例（%）</span>
									<input id="marginScale" name="marginScale" type="text" class="form-control" placeholder="请输入质保金比例" onchange="changeProjectValue()" onkeyup="if(/\D/g.test(this.value)){this.value=this.value.replace(/\D/g,'');}" 
								         		onafterpaste="if(/\D/g.test(this.value)){this.value=this.value.replace(/\D/g,'');}">
						   		</div>
						   </div>
						   
						   <div class="col-sm-4" style="width: 25%;">
						   		<div class="input-group">
							        <span class="input-group-addon">质保期限（天）</span>
									<input id="marginDay" name="marginDay" type="text" class="form-control" placeholder="请输入质保期限" onchange="changeProjectValue()" onkeyup="if(/\D/g.test(this.value)){this.value=this.value.replace(/\D/g,'');}" 
								         		onafterpaste="if(/\D/g.test(this.value)){this.value=this.value.replace(/\D/g,'');}">
						   		</div>
						   </div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label"><input type="checkbox" value="" id="isReveal" onchange="changeProjectValue()">是否需要兜底</label>
							
							<div class="col-sm-4" style="width: 25%;">
						   		<div class="input-group">
							        <span class="input-group-addon">兜底金比例（%）</span>
									<input id="revealScale" name="revealScale" type="text" class="form-control" placeholder="请输入兜底金比例" onchange="changeProjectValue()" onkeyup="if(/\D/g.test(this.value)){this.value=this.value.replace(/\D/g,'');}" 
								         		onafterpaste="if(/\D/g.test(this.value)){this.value=this.value.replace(/\D/g,'');}">
						   		</div>
						   </div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label "><input type="checkbox" value="" onchange="changeProjectValue()">填写项目计划</label>
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
								<button data-bb-handler="confirm" type="button" id="auditPassBtn" class="btn btn-success" onclick="auditProject(1)">
									<span><i class="icon-ok"></i></span> 审核通过
								</button>
								<button data-bb-handler="confirm" type="button" id="auditNotPassBtn" class="btn btn-success" onclick="auditProject(2)">
									<span><i class="icon-ok"></i></span> 审核不通过
								</button>
							</span>
							
							<button data-bb-handler="confirm" type="button" id="confirmBtn" class="btn btn-success" onclick="auditProject(3)">
								<span><i class="icon-ok"></i></span> 客户确认
							</button>
						</span>
						<button data-bb-handler="confirm" type="button" id="showLogBtn" class="btn btn-success" onclick="showUpdateLog()">
							<span><i class="icon-ok"></i></span> 修改日志
						</button>
						<button data-bb-handler="cancel" type="button" id="mcancerSave" modal="modifyModal" class="cancel btn btn-danger cancel">关闭</button>
					</div>
				</div>
				
			<!-- </div> -->
		</div>
	</div>
	
	<!-- =================新增项目计划痰喘=========================================== -->
	<div id="addProjectPlanModal" class="bootbox modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="myModalLabel">新增项目计划</h4>
			     </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<div class="form-group" style="display: none;">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-8">
								<input id="planId" type="text" class="form-control" placeholder="输入id">
							</div>
						</div>
						
						<div class="form-group" >
							<label class="col-sm-2 control-label">计划时间</label>
							<div class="col-sm-8">
								<input data-original-title="Tooltip for name" class="form-control" placeholder="开始时间--结束时间" type="text" id="dateRange2">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">工作内容</label>
							<div class="col-sm-8">
								<textarea id="planContent" rows="4" class="form-control limited" maxlength="1000" placeholder="项目详情描述"></textarea>
							</div>
						</div>
						
					</form>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="confirm" type="button" id="saveProjectPlan" class="btn btn-success" onclick="saveProjectPlan();">
						<span><i class="icon-ok"></i></span> &nbsp;保&nbsp;&nbsp;存
					</button>
					<button data-bb-handler="cancel" type="button" id="cancleProjectPlan" modal="addProjectPlanModal" class="cancel btn btn-danger cancel">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- =================查看修改日志=========================================== -->
	<div id="showUpdateLogModal" class="bootbox modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="showUpdateLogModalLabel">修改日志</h4>
			     </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<div class="form-group" id="showUpdateLogDiv">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="cancel" type="button" id="showUpdateLogModalBtn" modal="showUpdateLogModal" class="cancel btn btn-danger cancel">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<!-- =================拒绝原因=========================================== -->
	<div id="auditRejectReasonModal" class="bootbox modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="myModalLabel">审核不通过</h4>
			     </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						
						<div class="form-group">
							<label class="col-sm-2 control-label">原因</label>
							<div class="col-sm-8">
								<textarea id="reviewReason" rows="4" class="form-control limited" maxlength="1000" placeholder="请填写不通过原因"></textarea>
							</div>
						</div>
						
					</form>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="confirm" type="button" id="" class="btn btn-success" onclick="auditRejectAction();">
						<span><i class="icon-ok"></i></span> &nbsp;确&nbsp;&nbsp;认
					</button>
					<button data-bb-handler="cancel" type="button" id="" modal="auditRejectReasonModal" class="cancel btn btn-danger cancel">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- =================邀标=========================================== -->
	<div id="inviteProjectModal" class="bootbox modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="showUpdateLogModalLabel">邀标处理</h4>
			     </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" onsubmit="return false;">
						<input id="inviteProjectId" type="hidden" class="form-control" placeholder="">
						<div class="form-group">
							<label class="col-sm-2 control-label">联系人</label>
							<div class="col-sm-8">
								<input id="inviteProjectContact" type="text" class="form-control" placeholder="输入联系人">
								<button  class="btn" id="" onclick="addInviteProjectContact()">
									<i class="fa  fa-plus"></i> 加入
								</button>
							</div>
						</div>
						
						<div class="form-group" id="showInviteProjectContactDiv">
							<label class="col-sm-2 control-label">邀标列表</label>
							<div class="col-sm-8">
								<table border="1" id="showInviteProjectContactTable">
								</table>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="confirm" type="button" id="" class="btn btn-success" onclick="confirmInviteProject();">
						<span><i class="icon-ok"></i></span> &nbsp;确&nbsp;&nbsp;认
					</button>
					<button data-bb-handler="cancel" type="button" id="" modal="inviteProjectModal" class="cancel btn btn-danger cancel">关闭</button>
				</div>
			</div>
		</div>
	</div>
<script src="<%=path%>/pages/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="<%=path%>/pages/assets/js/jqGrid/i18n/grid.locale-en.js" type="text/javascript" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/moment.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/daterangepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path%>/pages/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="<%=path%>/pages/assets/js/date-time/date.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/layer/layer.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/projectManager/projectManager.js"></script>
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
	$('#dateRange2').daterangepicker({});
	$('#applyDeadline').datepicker();
	$('#submitDeadline').datepicker();
</script>
</body>
</html>