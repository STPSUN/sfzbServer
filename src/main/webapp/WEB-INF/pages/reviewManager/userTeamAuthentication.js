var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.99 -5);
//	});
	$(grid_selector).jqGrid({
		url : path + "/admin/users/authenticationList",
		postData:{"reviewState":"0","userAuthType":"team"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'userId',
			index : 'userId',
			label : 'UUID',
			hidden:true
		},{
			name : 'teamId',
			index : 'teamId',
			label : 'TUUID',
			hidden:true
		},{
			name : 'userName',
			index : 'userName',
			label : '账号'
		}, {
			name : 'teamNickName',
			index : 'teamNickName',
			label : '昵称'
		}, {
			name : 'teamName',
			index : 'teamName',
			label : '团队名称'
		}, {
			name : 'teamSkills',
			index : 'teamSkills',
			label : '团队技能'
		}, {
			name : 'serviceContent',
			index : 'serviceContent',
			label : '团队介绍'
		}, {
			name : 'updateTime',
			index : 'updateTime',
			label : '提交时间'
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			width :190,
			formatter:function(cellvalue, options, rowdata) {
				var state = rowdata.reviewState;
				$("#qryReviewState").val(state);
				var opt = "";
				if(state == "0"){
					opt = "<button type='button' class='btn btn-link' onclick='verified(\""+rowdata.userId+"\",\""+rowdata.teamId+"\")' >通过</button>" +
					"<button type='button' class='btn btn-link' onclick='unVerified(\""+rowdata.userId+"\",\""+rowdata.teamId+"\")'>不通过</button>";
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
		multiselect : false,// 是否支持多选
		multiselectWidth : 40, // 设置多选列宽度
		rownumbers: true, //添加序号列
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
		beforeRefresh : refreshData
	});

	// 查询
	$("#queryBtn").click(function() {
		var userName = $("#userName").val();
		var teamNickName = $("#nickName").val();
		var teamName = $("#teamName").val();
		var state = $("#qryReviewState").val();
		$(grid_selector).jqGrid('setGridParam', {
			postData : {
				userAuthType:"team",
				userName:userName,
				teamNickName:teamNickName,
				teamName:teamName,
				reviewState:state
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

function refreshData() {
	var userName = $("#userName").val();
	var teamNickName = $("#nickName").val();
	var teamName = $("#teamName").val();
	var state = $("#qryReviewState").val();
	$(grid_selector).jqGrid('setGridParam',{
		postData:{
			userAuthType:"team",
			userName:userName,
			teamNickName:teamNickName,
			teamName:teamName,
			reviewState:state
		},
		page:1
	}).trigger("reloadGrid");
}
//通过操作确认窗口
function verified(userId,teamId) {
	$("#auditPassUserId").val(userId);
	$("#auditPassTeamId").val(teamId);
	$("#verifiedModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
//不通过原因填写窗口
function unVerified(userId,teamId) {
	$("#notApprovedUserId").val(userId);
	$("#notApprovedTeamId").val(teamId);
	$("#unVerifiedModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
//团队认证审核通过确认
function verifiesConfirm() {
	var userId = $("#auditPassUserId").val();
	var teamId = $("#auditPassTeamId").val();
	$.ajax({
		url:path+"/admin/authentication/team/audit",
		cache: false,
		type:"post",
		data:{
			"review_state":"2",
			"user_id":userId,
			"team_id":teamId
		},
		success:function(result){
			if(result.code == "SUCCESS"){
				$('#verifiedModal').modal('hide');
				refreshData();
			}else{
				$.messager.alert("温馨提示","审核通过失败："+result.message);
			}
		}
	});
}
//团队认证审核不通过确认
function unVerifiesConfirm() {
	var reason = $("#reason").val();
	if(reason == null || reason.trim() == ""){
		$.messager.alert("温馨提示",'审核未通过原因未填写!');
		return;
	}
	if(reason.length > 250){
		$.messager.alert("温馨提示",'审核未通过原因超出长度!');
		return;
	}
	var userId = $("#notApprovedUserId").val();
	var teamId = $("#notApprovedTeamId").val();
	$.ajax({
		url:path+"/admin/authentication/team/audit",
		cache: false,
		type:"post",
		data:{
			"review_state":"3",
			"reason":reason,
			"user_id":userId,
			"team_id":teamId
		},
		success:function(result){
			if(result.code == "SUCCESS"){
				$('#unVerifiedModal').modal('hide');
				refreshData();
			}else{
				$.messager.alert("温馨提示","操作失败："+result.message);
				$('#unVerifiedModal').modal('hide');
			}
		}
	});
}

function resetQry(){
	$("#userName").val("");
	$("#nickName").val("");
	$("#teamName").val("");
}