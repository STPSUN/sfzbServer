var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.99);
//	});
	var showNav=parent.document.getElementById("showNav");
	$(showNav).html("质保管理");
	$(grid_selector).jqGrid({
		url : path + "/admin/warrantyManager/action/list",
//		postData:{"userAuthType":"normal"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [{
			name : 'projectId',
			index : 'projectId',
			label : 'UUID',
			hidden:true
		}, {
			name : 'adminReviewReason',
			index : 'adminReviewReason',
			label : '处理原因',
			hidden:true
		}, {
			name : 'adminId',
			index : 'adminId',
			label : '处理人',
			hidden:true
		},{
			name : 'createTime',
			index : 'createTime',
			label : '申请时间'
		}, {
			name : 'emploperUserName',
			index : 'emploperUserName',
			label : '雇主账号'
		}, {
			name : 'emploperNickName',
			index : 'emploperNickName',
			label : '雇主昵称'
		}, {
			name : 'projectName',
			index : 'projectName',
			label : '项目名称'
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
			name : 'projectState',
			index : 'projectState',
			label : '项目状态',
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
			name : 'tenderUserName',
			index : 'tenderUserName',
			label : '接包方账号'
		}, {
			name : 'tenderNickName',
			index : 'tenderNickName',
			label : '接包方昵称'
		}, {
			name : 'employerReason',
			index : 'employerReason',
			label : '申请终止理由',
			align :'center'
		}, {
			name : 'adminReviewState',
			index : 'adminReviewState',
			label : '处理状态',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="0"){ //客服处理结果：0 未处理 | 1 拒绝 | 2同意拒收
					return "未处理";
				}else if(cellvalue=="1"){
					return "拒绝质保";
				}else if(cellvalue=="2"){
					return "同意质保";
				}else{
					return cellvalue;
				}
			}
		} ,{
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			width : 225,
			formatter:function(cellvalue, options, rowdata) {
				var opt = '';
				//未审核
				if(rowdata.adminReviewState == '0'){
					opt += "<button type='button' class='btn btn-link' onclick='auditRejection(\""+rowdata.projectId+"\",1)'>同意</button>";
					opt += "<button type='button' class='btn btn-link' onclick='auditRejection(\""+rowdata.projectId+"\",2)'>驳回</button>";
				}else {
					opt += "<button type='button' class='btn btn-link' onclick='showRejection(\""+rowdata.adminId+"\",\""+rowdata.adminReviewReason+"\")'>查看</button>";
				}
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
function query(){
	var adminReviewStateSea = $("#adminReviewStateSea").val();
	var projectNameSea = $("#projectNameSea").val();
	var userKeyWordSea = $('#userKeyWordSea').val();
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
			userKeyWordSea : userKeyWordSea,
			adminReviewStateSea : adminReviewStateSea,
			projectNameSea : projectNameSea,
			qryBeginTime : qryBeginTime,
			qryEndTime : qryEndTime
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
//查看拒收处理
function showRejection(adminId,adminReviewReason){
	$("#showAuditRejectionModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
	
	if(adminId == null || adminId == 'null'){
		adminId = "";
	}
	$('#showRejectionUser').val(adminId);
	$('#showRejectionReason').val(adminReviewReason);
}
//审核 type=1 通过 type=2 驳回
function auditRejection(projectId,type){
	var msg = "";
	
	if(type == 1){
		msg = "同意质保";
	}else {
		msg = "驳回质保";
	}
	$('#rejectionReason').val('');
	$('#myModalLabel').html(msg);
	$('#auditType').val(type);
	$('#projectId').val(projectId);
	
	$("#auditRejectionModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
//确认拒收审核操作
function confirmAuditRejection(){
	
	//审核原因
	var reason = $('#rejectionReason').val();
	var type = $('#auditType').val();
	var projectId = $('#projectId').val();
	
	if(reason == null || reason == ''){
		$.messager.alert("温馨提示","请填写原因！");
		return;
	}
	$.ajax({
		url:path + "/admin/warrantyManager/action/auditWarranty",
		cache: false,
		type:"post",
		data:{
			"auditAction":type,
			"projectId":projectId,
			"reason": reason
			},
		success:function(result){
			$.messager.alert("温馨提示",result.message);
			if(result.code == "0"){
				query();
				$("#auditRejectionModal").modal('hide');
			}
		},error:function(){
			$.messager.alert("温馨提示","发生后台错误!");
		}
	});
}
/**
 * 重置查询条件
 */
function resetAppQry(){
	$("#adminReviewStateSea").val('');
	$("#projectNameSea").val('');
	$("#dateRange").val('');
	$("#userKeyWordSea").val('');
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
