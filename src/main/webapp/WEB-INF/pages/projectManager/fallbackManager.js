var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.99);
//	});
	var showNav=parent.document.getElementById("showNav");
	$(showNav).html("兜底管理");
	$(grid_selector).jqGrid({
		url : path + "/admin/fallbackManager/action/list",
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
			name : 'createTime',
			index : 'createTime',
			label : '兜底生成时间'
		}, {
			name : 'projectName',
			index : 'projectName',
			label : '项目名称'
		}, {
			name : 'applyContent',
			index : 'applyContent',
			label : '兜底内容'
		}, {
			name : 'userName',
			index : 'userName',
			label : '用户昵称'
		}, {
			name : 'mobile',
			index : 'mobile',
			label : '联系手机'
		}, {
			name : 'tenderUserName',
			index : 'tenderUserName',
			label : '接包人账号'
		}, {
			name : 'adminReviewState',
			index : 'adminReviewState',
			label : '兜底状态',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="0"){ //审核状态：0已提交未审核；1审核通过；2审核拒绝
					return "已提交未审批";
				}else if(cellvalue=="1"){
					return "审核通过";
				}else if(cellvalue=="2"){
					return "审批拒绝";
				}else{
					return cellvalue;
				}
			}
		}, {
			name : 'budget',
			index : 'budget',
			label : '项目预算金额'
		}, {
			name : 'revealCharge',
			index : 'revealCharge',
			label : '兜底服务费',
			align :'center',
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
			width : 250,
			formatter:function(cellvalue, options, rowdata) {
				var opt = "<button type='button' class='btn btn-link' onclick='showFallback(\""+rowdata.projectId+"\",\"show\")'>查看</button>";
				//未审核
				if(rowdata.adminReviewState == '0'){
					
					opt += "<button type='button' class='btn btn-link' onclick='auditFallback(\""+rowdata.projectId+"\",1)'>启动</button>";
					opt += "<button type='button' class='btn btn-link' onclick='auditFallback(\""+rowdata.projectId+"\",2)'>驳回</button>";
					
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
	var userSea = $("#userSea").val();
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
			adminReviewStateSea : adminReviewStateSea,
			userSea : userSea,
			startTimeSea : qryBeginTime,
			endTimeSea : qryEndTime
		},
		page : 1
	}).trigger("reloadGrid");
}
//查看兜底项目
function showFallback(projectId){
	$("#showProjectModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
	$.ajax({
		url:path+"/admin/projectManager/action/getProjectById",
		cache: false,
		type:"get",
		data:{
			"prjectUuid":projectId,
		},
		success:function(result){
			
			if(result.code == '0'){
				
				$("#projectName").val(result.data.projectName);
				$("#budget").val(result.data.budget);
				$("#regionDetail").val(result.data.regionDetail);
				$("#description").val(result.data.description);
				$("#applyDeadline").val(result.data.applyDeadline);
				$("#submitDeadline").val(result.data.submitDeadline);
				
				$("#userMobel").val(result.data.userMobel);
				$("#realName").val(result.data.tenderRealName);
				$("#idcardCode").val(result.data.idcardCode);
				
				if(result.data.hisOrderCount != '0'){
					
					$("#avgStars").val(result.data.countStars / result.data.hisOrderCount);
				}else {
					$("#avgStars").val(0);
				}
				$("#hisOrderCount").val(result.data.hisOrderCount);
				//项目招标方式
				if(result.data.tenderType == 'common_tender'){
					
					$("#tenderType").val('普通招标');
				}else if(result.data.tenderType == 'contain_tender'){
					$("#tenderType").val('包标');
				}
				
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
}
//审核 type=1 通过 type=2 驳回
function auditFallback(projectId,type){
	var msg = "";
	
	if(type == 1){
		msg = "启用";
		$("#startFallbackModal").modal({
			keyboard : false,
			show : true,
			backdrop : "static"
		});
		
		$('#startFallbackProjectId').val(projectId);
		$('#startFallbackContact').val('');
		
	}else {
		msg = "驳回";
		$.messager.confirm("温馨提示", "确认是否"+msg+"选中兜底记录？", function() {
			$.ajax({
				url:path + "/admin/fallbackManager/action/auditFallback",
				cache: false,
				type:"post",
				data:{
					"auditAction":type,
					"projectId":projectId
				},
				success:function(result){
					$.messager.alert("温馨提示",result.message);
					if(result.code == "0"){
						query();
					}
				},error:function(){
					$.messager.alert("温馨提示","发生后台错误!");
				}
			});
			
		});
	}
}
//启用兜底服务
function startFallback(){
	
	var projectId = $('#startFallbackProjectId').val();
	var contact = $('#startFallbackContact').val();
	
	if(contact == null || contact == ''){
		$.messager.alert("温馨提示","工程师手机号不能为空！");
		return ;
	}
	$.ajax({
		url:path + "/admin/fallbackManager/action/startFallback",
		cache: false,
		type:"post",
		data:{
			"contact":contact,
			"projectId":projectId
		},
		success:function(result){
			$.messager.alert("温馨提示",result.message);
			if(result.code == "0"){
				
				$("#startFallbackModal").modal('hide');
				query();
			}
		},error:function(){
			$.messager.alert("温馨提示","发生后台错误!");
		}
	});
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
/**
 * 重置查询条件
 */
function resetAppQry(){
	$("#adminReviewStateSea").val('');
	$("#userSea").val('');
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
