var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var grid_balance_selector = "#grid-balance-table";
var pager_balance_selector = "#grid-balance-pager";
var auditTeamId = null;
var auditUserId = null;
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.98);
//	});
	$(grid_selector).jqGrid({
		url : path + "/admin/users/list",
		postData:{"userAuthType":"team"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'userId',
			index : 'appUuid',
			label : 'UUID',
			hidden:true
		},{
			name : 'reviewTime',
			index : 'reviewTime',
			label : '申请时间',
			width : 200
		}, {
			name : 'teamNickName',
			index : 'teamNickName',
			label : '昵称'
		}, {
			name : 'contactsMobile',
			index : 'contactsMobile',
			label : '手机'
		}, {
			name : 'teamName',
			index : 'teamName',
			label : '团队名称'
		}, {
			name : 'teamSkills',
			index : 'teamSkills',
			label : '团队技能'
		}, {
			name : 'regionName',
			index : 'regionName',
			label : '所在城市',
			width :80
		}, {
			name : 'serviceContent',
			index : 'serviceContent',
			label : '团队服务内容',
			width :80
		}, {
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			align :'center',
			formatter:function(cellvalue, options, rowdata) {
				var opt="";
				if(rowdata.reviewState == "0"){//待审核显示审核按钮
					opt += "<a style='color:blue;' href='#' title='审核' onclick='auditUser(\""+rowdata.userId+"\")'>审核</a>&nbsp;&nbsp;";
				}
					
				opt +="<a style='color:blue;' href='#' title='查看详情' onclick='showUser(\""+rowdata.userId+"\")'>查看详情</a>";
					
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
		var reviewState = $("#qryReviewState").val();
		var teamNickName = $("#qryTeamNickName").val();
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
				reviewState : reviewState,
				teamNickName : teamNickName,
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
 * 详情页面
 * @param userId
 */
function showUser(userId) {
	$('#auditForm')[0].reset();
	$.ajax({
		url:path+"/admin/users/list",
		data:{"userAuthType":"team","userId":userId},
		type:"POST",
		success:function(rs){
			if(rs.records > 0){
				var data = rs.rows[0];
				$("#auditModalLabel").text("团队用户详情");
				$(".modal-footer").hide();
				$("#auditTeamName").val(data.teamName);
				$("#auditRegionName").val(data.regionName);
				$("#auditTeamMember").val(data.teamMemberName);
				$("#auditServiceContent").val(data.serviceContent);
				$("#auditExperience").val(data.experience);
				
				$("#auditTeamLeaderName").val(data.realName);
				$("#auditTeamLeaderICCode").val(data.idcardCode);
				$("#auditTeamLeaderMobile").val(data.mobile);
				$("#auditModal").modal({
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
 * 审核页面
 * @param userId
 */
function auditUser(userId) {
	$('#auditForm')[0].reset();
	$.ajax({
		url:path+"/admin/users/list",
		cache: false,
		data:{"userAuthType":"team","userId":userId},
		type:"POST",
		success:function(rs){
			if(rs.records > 0){
				var data = rs.rows[0];
				$("#auditModalLabel").text("团队用户审核");
				$(".modal-footer").show();
				auditTeamId = data.teamId;
				auditUserId = data.userId;
				$("#auditTeamName").val(data.teamName);
				$("#auditRegionName").val(data.regionName);
				$("#auditTeamMember").val(data.teamMemberName);
				$("#auditServiceContent").val(data.serviceContent);
				$("#auditExperience").val(data.experience);
				
				$("#auditTeamLeaderName").val(data.realName);
				$("#auditTeamLeaderICCode").val(data.idcardCode);
				$("#auditTeamLeaderMobile").val(data.mobile);
				$("#auditModal").modal({
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
 * 审核操作
 * @param reviewState
 */
function auditUserSend(reviewState) {
	var confirmMsg="确认审核通过？";
	if(reviewState!="2"){
		confirmMsg="确认审核不通过？";
	}
	var reason = "未填写待补充";
	$.messager.confirm("温馨提示", confirmMsg, function() {
		$.ajax({
			url:path+"/admin/authentication/team/audit",
			cache: false,
			type:"post",
			data:{
				"review_state":reviewState,
				"user_id":auditUserId,
				"team_id":auditTeamId,
				"reason":reason
			},
			success:function(result){
				if(result.code == "SUCCESS"){
					$('#auditModal').modal('hide');
					refreshData();
				}else{
					$.messager.alert("温馨提示","操作失败："+result.message);
				}
			}
		});
	});
}

/**
 * 重置查询条件
 */
function resetQry(){
	$("#qryTeamNickName").val("");
	$("#qryReviewState").val("");
	$("#dateRange").val("");
}

function refreshData() {
	$(grid_selector).jqGrid('setGridParam',{
		postData:{/*userName:null,realName:null,state:null*/},page : 1
	}).trigger("reloadGrid");
}
