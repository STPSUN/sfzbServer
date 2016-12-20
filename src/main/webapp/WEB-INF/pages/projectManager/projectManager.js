var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.99);
//	});
	var showNav=parent.document.getElementById("showNav");
	$(showNav).html("项目管理");
	$(grid_selector).jqGrid({
		url : path + "/admin/projectManager/action/list",
//		postData:{"userAuthType":"normal"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'projectId',
			index : 'projectId',
			label : 'UUID',
			hidden:true
		},{
			name : 'submitDeadline',
			index : 'submitDeadline',
			label : 'UUID',
			hidden:true
		},{
			name : 'realName',
			index : 'realName',
			label : 'UUID',
			hidden:true
		},{
			name : 'createTime',
			index : 'createTime',
			label : '申请时间'
		}, {
			name : 'userName',
			index : 'userName',
			label : '账户'
		}, {
			name : 'nickName',
			index : 'nickName',
			label : '昵称'
		}, {
			name : 'telephone',
			index : 'telephone',
			label : '联系电话'
		}, {
			name : 'tenderType',
			index : 'tenderType',
			label : '项目选择方式',
			formatter:function(cellvalue, options, rowdata) {
				
				if(cellvalue == 'common_tender'){
					
					return "普通招标";
				}else if(cellvalue == 'contain_tender'){
					
					return "包标";
				}else {
					
					return cellvalue;
				}
		   }
		}, {
			name : 'state',
			index : 'state',
			label : '当前状态',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="0"){ //项目状态:-1：已删除,0：已提交未审批,3：待客户确认,4:未中标,5：雇主选标中/接包者报名中/已发布（审批通过）,6：项目进行中/已中标,7：已完结,8：待验收,9：已失效（审批拒绝）
					return "已提交未审批";
				}else if(cellvalue=="1"){
					return "已禁用";
				}else if(cellvalue=="2"){
					return "审批拒绝";
				}else if(cellvalue=="3"){
					return "待客户确认";
				}else if(cellvalue=="4"){
					return "未中标";
				}else if(cellvalue=="5"){
					return "选标中";
				}else if(cellvalue=="6"){
					return "已中标";
				}else if(cellvalue=="7"){
					return "已完结";
				}else if(cellvalue=="8"){
					return "待验收";
				}else if(cellvalue=="9"){
					return "已失效";
				}else{
					return cellvalue;
				}
			}
		}, {
			name : 'regionDetail',
			index : 'regionDetail',
			label : '需求地区'
		}, 
		
//		{
//			name : 'regionDetail',
//			index : 'regionDetail',
//			label : '区域'
//		},
//		{
//			name : 'regionCode',
//			index : 'regionCode',
//			label : '楼号-门牌号',
//			align :'center'
//		},
		
		{
			name : 'applyDeadline',
			index : 'applyDeadline',
			label : '报名截止时间'
		} ,
		
		{
			name : 'submitDeadline',
			index : 'submitDeadline',
			label : '交付截止时间'
		} , {
			name : 'budget',
			index : 'budget',
			label : '项目预算'
		}, {
			name : 'serverCharge',
			index : 'serverCharge',
			label : '服务费',
			formatter:function(cellvalue, options, rowdata) {
				//是否兜底 0否 1是
				if(rowdata.isReveal == '1'){
					var reveal = 0;
					if(rowdata.revealScale){
						reveal = rowdata.revealScale;
					}
					return reveal * rowdata.budget;
				}else {
					
					return 0;
				}
		   }
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			width : 175,
			formatter:function(cellvalue, options, rowdata) {
				var opt = "<button type='button' class='btn btn-link' onclick='editProject(\""+rowdata.projectId+"\",\"show\")'>详情</button>";
				//未审核
				if(rowdata.state == '0'){
					
					opt += "<button type='button' class='btn btn-link' onclick='editProject(\""+rowdata.projectId+"\",\"edit\")'>编辑</button>";
					
				}
				//包标并且未审核
				if(rowdata.tenderType == 'contain_tender' && rowdata.state == '0'){
					
					opt += "<button type='button' class='btn btn-link' onclick='inviteProject(\""+rowdata.projectId+"\")'>邀标</button>";
					
				}
				//后期同步srs平台
//				if(rowdata.tenderType == 'SRS'){
//					
//					opt += "<a style='color:blue;font-size:16px' href='javascript:void(0)' title='同步' onclick='editProject(\""+rowdata.projectId+"\",\"show\")'>同步" +
//					/*"<i class='icon-zoom-in '>" +
//						"</i>" +*/
//					"</a>&nbsp;&nbsp;";
//				}
    			return opt;
		   }
		}
		],
		viewrecords : true,// 是否显示行数
		rowNum : 15, // 每页显示记录数
		rowList : [ 15, 30, 45 ], // 可调整每页显示的记录数
		pager : pager_selector,
		altRows : true, // 设置表格 zebra-striped 值
		gridview : true, // 加速显示
		multiselect : true,// 是否支持多选
		multiselectWidth : 40, // 设置多选列宽度
		multiboxonly : true,
		loadComplete : function() {
			var table = this;
			var re_records = $(this).getGridParam('records');
			if(re_records == 0 || re_records == null){
				if($(".norecords").html() == null){
					$(this).parent().append("<div class=\"norecords\">没有记录</div>");
				}
				$(".norecords").show();
			}else{$(".norecords").hide();}
			setTimeout(function() {
				updatePagerIcons(table);
			}, 0);
		},
		autowidth : true,
		jsonReader : {// jsonReader来跟服务器端返回的数据做对应
			root : "rows",
			total : "total",
			records : "records",
			repeatitems : false
		},
		emptyrecords : '没有记录!',
		loadtext : '正在查询数据...'
	});
	// 设置分页按钮组
	$(grid_selector).jqGrid('navGrid', pager_selector, {
		edit : false,
		add : false,
		del : false,
		search : false,
		view : false,
		refresh : true,
		refreshicon : 'icon-refresh green',
		refreshtitle : '刷新',
		refreshtext : '刷新',
		beforeRefresh : query()
	});

	// 查询
	$("#queryBtn").click(function() {
		query();
	});
});
//查询操作
function query(){
	var reviewStateSea = $("#reviewStateSea").val();
	var userKeyWordSea = $("#userKeyWordSea").val();
	var areaSea = $("#areaSea").val();
	var tenderTypeSea = $("#tenderTypeSea").val();
	var dateRange = $("#dateRange").val();
	var qryBeginTime="";
	var qryEndTime="";
	if(dateRange!=null && dateRange!=""){
		var drArr = dateRange.split(" - ");
		if(drArr.length>=2){
			qryBeginTime = drArr[0];
			qryEndTime = drArr[1];
		}
	}
	$(grid_selector).jqGrid('setGridParam', {
		postData : {
			reviewStateSea : reviewStateSea,
			userKeyWordSea : userKeyWordSea,
			areaSea : areaSea,
			tenderTypeSea : tenderTypeSea,
			startTimeSea : qryBeginTime,
			endTimeSea : qryEndTime
		},
		page : 1
	}).trigger("reloadGrid");
}
//这个是分页图标，必须添加
function updatePagerIcons(table) {
	var replacement = {
		'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
		'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
		'ui-icon-seek-next' : 'icon-angle-right bigger-140',
		'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon')
			.each(function() {
				var icon = $(this);
				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

				if ($class in replacement)
					icon.attr('class', 'ui-icon ' + replacement[$class]);
			});
}
//添加提示
function enableTooltips(table) {
	$('.navtable .ui-pg-button').tooltip({
		container : 'body'
	});
	$(table).find('.ui-pg-div').tooltip({
		container : 'body'
	});
}
//存储已邀标列表
var inviteUsers = [];
//邀标
function inviteProject(projectId){
	$('#inviteProjectId').val(projectId);
//	$.messager.alert("温馨提示",'邀标需求仍在确认，延期处理！');
	$("#inviteProjectContact").val('');
	resetInviteTable();
	$("#inviteProjectModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
function resetInviteTable(){
	$("#showInviteProjectContactTable").html('<tr><td width="50">序号</td> <td width="100">邀请时间</td> <td width="100">手机号码</td> <td width="100">姓名</td><td width="50">操作</td></tr>');
	
}
//增加联系人到邀标列表
function addInviteProjectContact(){
	var telephone = $("#inviteProjectContact").val();
	
	if(telephone == null || telephone == ''){
		$.messager.alert("温馨提示",'请输入联系人手机号！');
		return ;
	}
	
	for(var i = 0; i < inviteUsers.length; i++){
		if(telephone == inviteUsers[i].mobile ){
			$.messager.alert("温馨提示",'已在邀标列表，请勿重复邀请！');
			return ;
		}
	}
	$.ajax({
		url:path+"/admin/projectManager/action/getUserByTelephone",
		cache: false,
		type:"get",
		data:{
			"telephone" : telephone
		},
		success:function(result){
			
			if(result.code == '0'){
				var user = result.data;
				inviteUsers.push(user);
				addOneUserToInviteProject(user);
				
				$("#inviteProjectContact").val('');
				
			}else{
				$.messager.alert("温馨提示",result.message);
			}
		}
	});
}
//增加一个用户到界面上
function addOneUserToInviteProject(user){
	var str = '';
	str += '<tr><td>'+(i+1)+'</td><td>'+user.createTime+'</td><td>'+user.mobile+'</td><td>'+user.realName+'</td>'
	+'<td>'
	+'<a href="#" onclick="deleteInviteUser(\''+user.userId+'\');">删除</a></td></tr>';
	
	$("#showInviteProjectContactTable").append(str);
}
//删除已选邀标
function deleteInviteUser(userId){
	for(var i = 0; i < inviteUsers.length; i++){
		if(userId == inviteUsers[i].userId ){
			//删除
			inviteUsers.splice(i,1);
			break;
		}
	}
	//刷新table
	resetInviteTable();
	for(var i = 0; i < inviteUsers.length; i++){
			
		addOneUserToInviteProject(inviteUsers[i]);
	}
}
//提交邀标操作
function confirmInviteProject(){
	var inviteUserIds = [];
	for(var i = 0; i < inviteUsers.length; i++){
		inviteUserIds.push(inviteUsers[i].userId);
	}
	var projectId = $('#inviteProjectId').val();
	$.ajax({
		url:path+"/admin/projectManager/action/inviteUserForProject",
		cache: false,
		type:"POST",
		traditional: true,
		data:{
			"projectId" : projectId,
			"inviteUsers" : inviteUserIds
		},
		success:function(result){
			
			$.messager.alert("温馨提示",result.message);
			if(result.code == '0'){
				$("#inviteProjectModal").modal('hide');
			}
		}
	});
}
//项目编辑 type = show/edit
function editProject(prjectUuid,type) {
	
	if(prjectUuid=="null" || prjectUuid==""){
		$.messager.alert("温馨提示",'获取行数据异常!');
		return;
	}
	$("#modifyModalLabel").val("数据加载中...");
	//是否可以操作项目计划
	var canEdit = true;
	if(type == "show"){
		//隐藏操作按钮
		$("#optBtnSpan").hide();
		//添加计划按钮隐藏
		$("#addPlanBtn").hide();
		$("#modifyModalLabel").html("查看项目");
		//不能操作项目计划
		canEdit = false;
	}else if(type == "edit"){
		$("#modifyModalLabel").html("编辑项目");
		//添加计划按钮显示
		$("#addPlanBtn").show();
		$("#optBtnSpan").show();
	}
	//加载主要信息
	$.ajax({
		url:path+"/admin/projectManager/action/getProjectById",
		cache: false,
		type:"get",
		data:{
			"prjectUuid":prjectUuid,
		},
		success:function(result){
			
			if(result.code == '0'){
				//弹出修改/查看页面
				$("#modifyModal").modal({
					keyboard : false,
					show : true,
					backdrop : "static"
				});
				
				$("#projectId").val(result.data.projectId);
				$("#projectName").val(result.data.projectName);
				$("#budget").val(result.data.budget);
				$("#tenderType").val(result.data.tenderType);
				$("#regionDetail").val(result.data.regionDetail);
				$("#realName").val(result.data.realName);
				$("#telephone").val(result.data.telephone);
				$("#description").val(result.data.description);
				$("#applyDeadline").val(result.data.applyDeadline);
				$("#submitDeadline").val(result.data.submitDeadline);
				
				if(result.data.marginScale != null && result.data.marginScale != ''){
					$("#marginScale").val((result.data.marginScale * 100));
				}else{
					
					$("#marginScale").val(0);
				}
				
				//是否兜底
				if(result.data.isReveal == '1'){
					
					$("#isReveal").prop("checked",true);
				}else {
					$("#isReveal").removeAttr("checked");
				}
				//是否质保
				if(result.data.isMargin == '1'){
					$("#isMargin").prop("checked",true);
				}else {
					$("#isMargin").removeAttr("checked");
				}
				
				if(result.data.marginScale != null && result.data.marginScale != ''){
					$("#revealScale").val((result.data.revealScale * 100));
				}else{
					
					$("#revealScale").val(0);
				}
				
				$("#marginDay").val(result.data.marginDay);
				
				$("#createTimeSpan").html(result.data.createTime);
				$("#updateTimeSpan").html(result.data.updateTime);
				//是否显示审核按钮
				if(result.data.state == '0'){
					$("#auditBtnSpan").show();
					$("#optBtnSpan").show();
					
				}else {
					$("#auditBtnSpan").show();
					$("#optBtnSpan").hide();
				}
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
	//需求类别
	$.ajax({
		url:path+"/admin/projectManager/action/getProjectCategoryById",
		cache: false,
		type:"get",
		data:{
			"prjectUuid":prjectUuid,
		},
		success:function(result){
			
			if(result.code == '0'){
				var str = '';
				var list = result.data;
				for(var i=0;i < list.length; i++){
//					str += '<option value="'+list[i].categoryId+'">'+list[i].categoryName+'</option>';
					str += list[i].categoryName;
					
					if(i < list.length - 1){
						str += "  |  ";
					}
				}
				
				if(list.length <= 0){
					str = "";
				}
				$("#categoryTypesSelect").val(str);
				
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
	//加载计划内容
	reflushPlanContent(prjectUuid,canEdit);
	//加载所需技能信息
	$.ajax({
		url:path+"/admin/projectManager/action/getProjectAblitisById",
		cache: false,
		type:"get",
		data:{
			"prjectUuid":prjectUuid,
		},
		success:function(result){
			
			if(result.code == '0'){
				var str = '';
				var list = result.data;
				for(var i=0;i < list.length; i++){
//					str += '<label><input type="checkbox" name="abilitys" value="'+list[i].abilityId+'" checked="checked">'+list[i].abilityName+'</label>';
					str += list[i].abilityName;
					
					if(i < (list.length - 1)){
						str += "  |  ";
					}
				}
				
				if(str == ''){
					str = "没有需要的技能！";
				}
				$("#abilitysDiv").val(str);
				
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
	
	//加载项目进度
	$.ajax({
		url:path+"/admin/projectManager/action/getProjectProgressById",
		cache: false,
		type:"get",
		data:{
			"prjectUuid":prjectUuid,
		},
		success:function(result){
			
			if(result.code == '0'){
				
				var str = '';
				var list = result.data;
				
				if(list.length > 0){
					str = '<table border="1"><tr><td>序号</td> <td>签到时间</td> <td>类别</td> <td>工程师</td><td>进度</td><td>位置</td></tr>';
									
					for(var i=0;i < list.length; i++){
						str += '<tr><td>'+(i+1)+'</td><td>'+list[i].createTime+'</td><td>工作进度</td><td width="100">'+list[i].eventUserId+'</td>'
						+'<td>'+list[i].progress+'</td><td width="100">'+list[i].eventAddress+'</td></tr>';
					}
					
					str += '</table>';
				}
				
				else {
					str = "没有项目进度！";
				}
				$("#projectProgressDiv").html(str);
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
}
//改变项目的值，需客户确认
function changeProjectValue(){
	//隐藏审核按钮
	$("#auditBtnSpan").hide();
}
//加载计划内容
function reflushPlanContent(prjectUuid,canEdit){
	//加载项目计划
	$.ajax({
		url:path+"/admin/projectManager/action/getProjectPlansById",
		cache: false,
		type:"get",
		data:{
			"prjectUuid":prjectUuid,
		},
		success:function(result){
			
			if(result.code == '0'){
				var str = '';
				var list = result.data;
				
				if(list.length > 0){
					str = '<table border="1"><tr><td>序号</td> <td>开始日期</td> <td>截止日期</td> <td>计划工作内容</td><td>操作</td></tr>';
									
					for(var i=0;i < list.length; i++){
						str += '<tr><td>'+(i+1)+'</td><td>'+list[i].planStartTime+'</td><td>'+list[i].planEndTime+'</td><td width="100">'+list[i].planContent+'</td>'
						+'<td><a href="#" onclick="editProjectPlan(\''+list[i].planId+'\',\''+list[i].planStartTime+'\',\''+list[i].planEndTime+'\',\''+list[i].planContent+'\','+canEdit+');">编辑</a>&nbsp;&nbsp;'
						+'<a href="#" onclick="deleteProjectPlan(\''+list[i].planId+'\','+canEdit+');">删除</a></td></tr>';
					}
					
					str += '</table>';
				}
				
				else {
					str = "没有填写工作计划！";
				}
				$("#projectPlanSpan").html(str);
				
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
}
//审核项目 type=1 通过 type=2 不通过
function auditProject(type){
	var prjectUuid = $("#projectId").val();
	//通过
	if(type == 1){
		$.ajax({
			url:path+"/admin/projectManager/action/auditProjectById",
			cache: false,
			type:"post",
			data:{
				"auditAction" : type,
				"prjectUuid":prjectUuid,
			},
			success:function(result){
				
				if(result.code == '0'){
					query();
					$.messager.alert("温馨提示",result.message);
					$("#modifyModal").modal('hide');
				}else{
					$.messager.alert("温馨提示",result.message);
				}
			}
		});
	}
	//不通过
	else if(type == 2){
		$("#auditRejectReasonModal").modal({
			keyboard : false,
			show : true,
			backdrop : "static"
		});
		$('#reviewReason').val('');
		
	}else if(type == 3){
		var projectName = $("#projectName").val();
		var budget = $("#budget").val();
		var tenderType = $("#tenderType").val();
		var regionDetail = $("#regionDetail").val();
		var realName = $("#realName").val();
		var telephone = $("#telephone").val();
		var description = $("#description").val();
		var applyDeadline = $("#applyDeadline").val();
		var submitDeadline = $("#submitDeadline").val();
		
		var marginScale = $("#marginScale").val();
		var revealScale = $("#revealScale").val();
		
		if(marginScale != null && marginScale != ''){
			marginScale = (marginScale / 100);
		}
		if(revealScale != null && revealScale != ''){
			revealScale = (revealScale / 100);
		}
		
		var marginDay = $("#marginDay").val();
		
		var isReveal = '';
		var isMargin = '';
		//是否兜底
		if($('#isReveal').is(':checked')){
			isReveal = '1';
		}else {
			isReveal = '0';
		}
		//是否质保
		if($('#isMargin').is(':checked')){
			isMargin = '1';
		}else {
			isMargin = '0';
		}

		$.ajax({
			url:path+"/admin/projectManager/action/auditProjectById",
			cache: false,
			type:"post",
			data:{
				"auditAction" : type,
				"prjectUuid":prjectUuid,
				"projectName":projectName,
				"budget":budget,
				"tenderType":tenderType,
				"regionDetail":regionDetail,
				"realName":realName,
				"telephone":telephone,
				"description":description,
				"applyDeadline":applyDeadline,
				"submitDeadline":submitDeadline,
				"marginScale":marginScale,
				"revealScale":revealScale,
				"marginDay":marginDay,
				"isReveal":isReveal,
				"isMargin":isMargin
			},
			success:function(result){
				
				if(result.code == '0'){
					query();
					$.messager.alert("温馨提示",result.message);
					$("#modifyModal").modal('hide');
				}else{
					$.messager.alert("温馨提示",result.message);
				}
			}
		});
	}
}
//审核不通过操作
function auditRejectAction(){
	var reviewReason = $('#reviewReason').val();
	var prjectUuid = $("#projectId").val();
	
	if(reviewReason == null || reviewReason == ''){
		$.messager.alert("温馨提示",'请填写不通过原因！');
		return ;
	}
	$.ajax({
		url:path+"/admin/projectManager/action/auditProjectById",
		cache: false,
		type:"post",
		data:{
			"auditAction" : 2,
			"prjectUuid":prjectUuid,
			"reviewReason" : reviewReason
		},
		success:function(result){
			
			if(result.code == '0'){
				query();
				$.messager.alert("温馨提示",result.message);
				$("#modifyModal").modal('hide');
				$("#auditRejectReasonModal").modal('hide');
				
			}else{
				$.messager.alert("温馨提示",result.message);
			}
		}
	});
}
//新增项目计划
function addProjectPlan(){
	
	//清除计划ID
	$("#planId").val("");
	$("#dateRange2").val("");
	$("#planContent").val("");
	
	$("#addProjectPlanModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}

//编辑项目计划 
function editProjectPlan(planId,startTime,endTime,planContent,canEdit){
	
	if(!canEdit){
		$.messager.alert("温馨提示",'查看模式下不能操作！');
		
		return ;
	}
	$("#planId").val(planId);
	$("#dateRange2").val(startTime + ' - ' + endTime);
	$("#planContent").val(planContent);
	
	$("#addProjectPlanModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
//删除项目计划 
function deleteProjectPlan(planId,canEdit){
	
	if(!canEdit){
		$.messager.alert("温馨提示",'查看模式下不能操作！');
		
		return ;
	}
	
	$.messager.confirm("温馨提示", "确认是否删除项目计划？", function() {
	
		changeProjectValue();
		$.ajax({
			url:path+"/admin/projectManager/action/deleteProjectPlan",
			cache: false,
			type:"post",
			data:{
				"planId" : planId
			},
			success:function(result){
				
				if(result.code == '0'){
					$.messager.alert("温馨提示",result.message);
					reflushPlanContent($('#projectId').val(),true);
				}else{
					$.messager.alert("温馨提示",'获取信息错误!');
				}
			}
		});
	});
}
//保存项目计划
function saveProjectPlan(){
	var projectUuid = $("#projectId").val();
	var dateRange2 = $("#dateRange2").val();
	var planContent = $("#planContent").val();
	var planId = $("#planId").val();
	if(projectUuid == ""){
		$.messager.alert("温馨提示",'项目ID不能为空！');
		
		return ;
	}else if(dateRange2 == null || dateRange2 == ''){
		$.messager.alert("温馨提示",'请选择计划时间！');
		
		return ;
	}else if(planContent == null || planContent == ''){
		$.messager.alert("温馨提示",'请输入计划内容！');
		
		return ;
	}
	var qryBeginTime="";
	var qryEndTime="";
	if(dateRange2!=null && dateRange2!=""){
		var drArr = dateRange2.split(" - ");
		if(drArr.length>=2){
			qryBeginTime = drArr[0];
			qryEndTime = drArr[1];
		}
	}
	//保存计划
	$.ajax({
		url:path+"/admin/projectManager/action/saveProjectPlan",
		cache: false,
		type:"post",
		data:{
			"planId" : planId,
			"prjectUuid":projectUuid,
			"planStartTime":qryBeginTime,
			"planEndTime":qryEndTime,
			"planContent":planContent
		},
		success:function(result){
			
			if(result.code == '0'){
				changeProjectValue();
				//加载计划内容
				$("#addProjectPlanModal").modal('hide');
				reflushPlanContent(projectUuid,true);
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
}
//查看修改日志
function showUpdateLog(){
	var prjectUuid = $("#projectId").val();
	$.ajax({
		url:path+"/admin/projectManager/action/getProjectUpdateLogById",
		cache: false,
		type:"get",
		data:{
			"projectUuid":prjectUuid,
		},
		success:function(result){
			
			if(result.code == '0'){
				var str = '';
				var list = result.data;
				
				if(list.length > 0){
					str = '<table border="1"><tr><td>序号</td> <td>字段</td> <td>内容</td> <td>修改时间</td></tr>';
									
					for(var i=0;i < list.length; i++){
						str += '<tr><td>'+(i+1)+'</td><td>'+list[i].property+'</td><td width="200">'+list[i].content+'</td><td width="200">'+list[i].createDate+'</td>'
						+'</tr>';
					}
				}
				
				else {
					str = "没有修改日志！";
				}
				$("#showUpdateLogDiv").html(str);
				
				$("#showUpdateLogModal").modal({
					keyboard : false,
					show : true,
					backdrop : "static"
				});
				
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
}
/**
 * 重置查询条件
 */
function resetAppQry(){
	$("#reviewStateSea").val('');
	$("#userKeyWordSea").val('');
	$("#areaSea").val('');
	$("#tenderTypeSea").val('');
	$("#dateRange").val('');
}
function updateStu() {
	$.messager.alert("updateStu");
}

/**
 * 长度
 * @param str
 * @returns {Number}
 */
function strlen(str){
    var len = 0;
    for (var i=0; i<str.length; i++) {
     var c = str.charCodeAt(i);  
     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
       len++; 
     }else { 
      len+=2; 
     } 
    } 
    return len;
    
}

/**
 * 数字
 */
function keyPress() {    
    var keyCode = event.keyCode;    
    if ((keyCode >= 48 && keyCode <= 57))    
   {    
        event.returnValue = true;    
    } else {    
          event.returnValue = false;    
   }    
}
