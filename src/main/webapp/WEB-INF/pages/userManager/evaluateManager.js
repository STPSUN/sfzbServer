var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var grid_balance_selector = "#grid-balance-table";
var pager_balance_selector = "#grid-balance-pager";
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.98);
//	});
	$(grid_selector).jqGrid({
		url : path + "/admin/users/evaluate/query",
		datatype : "json",
		mtype : 'get',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'userId',
			index : 'appUuid',
			label : 'UUID',
			hidden:true
		}, {
			name : 'targetOwnerId',
			index : 'targetOwnerId',
			label : '被评价人Id',
			hidden:true
		}, {
			name : 'targetId',
			index : 'targetId',
			label : '项目Id',
			hidden:true
		}, {
			name : 'userName',
			index : 'userName',
			label : '评价人账号',
			formatter:function(cellvalue, options, rowdata) {
				return "<a style='color:blue;' href='#' title='查看用户详情' onclick='showUser(\""+rowdata.userId+"\")'>"+cellvalue+"</a>";
			}
		}, {
			name : 'nickName',
			index : 'nickName',
			label : '评价人昵称'
		}, {
			name : 'targetUserName',
			index : 'targetUserName',
			label : '被评价人账号',
			formatter:function(cellvalue, options, rowdata) {
				return "<a style='color:blue;' href='#' title='查看用户详情' onclick='showUser(\""+rowdata.targetOwnerId+"\")'>"+cellvalue+"</a>";
			}
		}, {
			name : 'targetNickName',
			index : 'targetNickName',
			label : '被评价人昵称'
		}, {
			name : 'targetType',
			index : 'targetType',
			label : '评论目标类型',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="evaluate_employer"){
					return "评价雇主";
				}else if(cellvalue=="evaluate_receiver"){
					return "评价接包者";
				}else{
					return "";
				}
			}
		}, {
			name : 'projectName',
			index : 'projectName',
			label : '项目名称',
			formatter:function(cellvalue, options, rowdata) {
				return "<a style='color:blue;' href='#' title='项目详情' onclick='editProject(\""+rowdata.targetId+"\",\"show\")'>"+cellvalue+"</a>";
			}
		}, {
			name : 'updateTime',
			index : 'updateTime',
			label : '评价时间'
		}, {
			name : 'star',
			index : 'star',
			label : '评价星级'
		}, {
			name : 'content',
			index : 'content',
			label : '评价内容'
		}
		],
		viewrecords : true,// 是否显示行数
		rowNum : 15, // 每页显示记录数
		rowList : [ 15, 30, 45 ], // 可调整每页显示的记录数
		pager : pager_selector,
		altRows : true, // 设置表格 zebra-striped 值
		gridview : true, // 加速显示
		multiselect : false,// 是否支持多选
		multiselectWidth : 40, // 设置多选列宽度
		multiboxonly : true,
		rownumbers: true, //添加序号列
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
		beforeRefresh : refreshData
	});
	
	// 查询
	$("#queryBtn").click(function() {
		var userName = $("#qryUserName").val();
		var targetUserName = $("#qryTargetUserName").val();
		var dateRange = $("#dateRange").val();
		var qryStartTime="";
		var qryEndTime="";
		if(dateRange!=null && dateRange!=""){
			var drArr = dateRange.split(" - ");
			if(drArr.length>=2){
				qryStartTime = drArr[0];
				qryEndTime = drArr[1];
			}
		}
		$(grid_selector).jqGrid('setGridParam', {
			postData : {
				userName : userName,
				targetUserName : targetUserName,
				qryStartTime : qryStartTime,
				qryEndTime : qryEndTime
			},
			page : 1
		}).trigger("reloadGrid");
	})
});

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
			})
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

/**
 * 重置查询条件
 */
function resetQry(){
	$("#qryUserName").val("");
	$("#qryTargetUserName").val("");
	$("#dateRange").val("");
}

function refreshData() {
	$(grid_selector).jqGrid('setGridParam',{
		postData:{/*userName:null,realName:null,state:null*/},page : 1
	}).trigger("reloadGrid");
}

/**
 * 详情页面
 * @param userId
 */
function showUser(userId) {
	//重置表单
	$('#showForm')[0].reset();
	$.ajax({
		url:path+"/admin/users/"+userId+"/resume",
		cache: false,
		type:"get",
		success:function(rs){
			if(rs.sucess){
				$("#showModal").modal({
					keyboard : false,
					show : true,
					backdrop : "static"
				});
				$("#realName").val(rs.data.realName);
				$("#sex").val(rs.data.sex);
				$("#mobile").val(rs.data.mobile);
				$("#star").val(rs.data.star);
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
}

//项目编辑 type = show/edit
function editProject(prjectUuid,type) {
	
	if(prjectUuid=="null"||prjectUuid==""){
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
				
				$("#marginScale").val(result.data.marginScale);
				
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
				$("#revealScale").val(result.data.revealScale);
				$("#marginDay").val(result.data.marginDay);
				
				$("#createTimeSpan").html(result.data.createTime);
				$("#updateTimeSpan").html(result.data.updateTime);
				//是否显示审核按钮
				if(result.data.state == '0'){
					$("#auditBtnSpan").show();
				}else {
					$("#auditBtnSpan").hide();
				}
				
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
	//需求类别
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
					str += '<label><input type="checkbox" name="abilitys" value="'+list[i].abilityId+'" checked="checked">'+list[i].abilityName+'</label>';
				}
				
				if(str == ''){
					str = "没有需要的技能！";
				}
				$("#abilitysDiv").html(str);
				
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
					str = '<table border="1"><tr><td>序号</td> <td>签到时间</td> <td>类别</td> <td>工程师</td><td>工时</td><td>位置</td><td>签到日志</td></tr>';
									
					for(var i=0;i < list.length; i++){
						str += '<tr><td>'+(i+1)+'</td><td>'+list[i].progress+'</td><td>'+list[i].progress+'</td><td width="100">'+list[i].progress+'</td>'
						+'<td>'+list[i].progress+'</td><td width="100">'+list[i].progress+'</td><td width="100">'+list[i].progress+'</td></tr>';
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
					str = '<table border="1"><tr><td>序号</td> <td>开始时间</td> <td>截止时间</td> <td>计划工作内容</td><td>操作</td></tr>';
									
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