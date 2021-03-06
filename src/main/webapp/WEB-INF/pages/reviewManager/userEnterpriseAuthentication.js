var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.99 -5);
//	});
	$(grid_selector).jqGrid({
		url : path + "/admin/users/authenticationList",
		postData:{"reviewState":"0","userAuthType":"enterprise"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'userId',
			index : 'userId',
			label : 'UUID',
			hidden:true
		},{
			name : 'enterpriseId',
			index : 'enterpriseId',
			label : 'ENTUUID',
			hidden:true 
		},{
			name : 'userName',
			index : 'userName',
			label : '账号'
		}, {
			name : 'nickName',
			index : 'nickName',
			label : '昵称'
		}, {
			name : 'enterpriseName',
			index : 'enterpriseName',
			label : '企业名称'
		}, {
			name : 'businessLicense',
			index : 'businessLicense',
			label : '营业执照号'
		}, {
			name : 'corporate',
			index : 'corporate',
			label : '手企业法人'
		}, {
			name : 'mobile',
			index : 'mobile',
			label : '联系电话'
		}, {
			name : 'businessLicenseImage',
			index : 'businessLicenseImage',
			label : '营业执照副本',
			formatter:function(cellvalue, options, rowdata) {
				var imgPath ="http://27.151.123.121:11024/bfzb"+"/resource/img/"+cellvalue; //path
				var opt = "<img alt='营业执照副本' src='"+imgPath+"'" +
						" onclick='showImg(\""+imgPath+"\",\"营业执照副本\")' class='img-thumbnail' />";
    			return opt;
		    }
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
					opt = "<button type='button' class='btn btn-link' onclick='verified(\""+rowdata.userId+"\",\""+rowdata.enterpriseId+"\")' >通过</button>" +
					"<button type='button' class='btn btn-link' onclick='unVerified(\""+rowdata.userId+"\",\""+rowdata.enterpriseId+"\")'>不通过</button>";
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
		var enterpriseName = $("#enterpriseName").val();
		var businessLicense = $("#businessLicense").val();
		var state = $("#qryReviewState").val();
		$(grid_selector).jqGrid('setGridParam', {
			postData : {
				userAuthType:"enterprise",
				userName:userName,
				enterpriseName:enterpriseName,
				businessLicense:businessLicense,
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
	var enterpriseName = $("#enterpriseName").val();
	var businessLicense = $("#businessLicense").val();
	var state = $("#qryReviewState").val();
	$(grid_selector).jqGrid('setGridParam',{
		postData:{
			userAuthType:"enterprise",
			userName:userName,
			enterpriseName:enterpriseName,
			businessLicense:businessLicense,
			reviewState:state
		},
		page : 1
	}).trigger("reloadGrid");
}
//通过操作确认窗口
function verified(userId,enterpriseId) {
	$("#auditPassUserId").val(userId);
	$("#auditPassEnterpriseId").val(enterpriseId);
	$("#verifiedModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
//不通过原因填写窗口
function unVerified(userId,enterpriseId) {
	$("#notApprovedUserId").val(userId);
	$("#notApprovedEnterpriseId").val(enterpriseId);
	$("#unVerifiedModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
//企业认证审核通过确认
function verifiesConfirm() {
	var userId = $("#auditPassUserId").val();
	var enterpriseId =$("#auditPassEnterpriseId").val();
	$.ajax({
		url:path+"/admin/authentication/enterprise/audit",
		cache: false,
		type:"post",
		data:{
			"review_state":"1",
			"user_id":userId,
			"enterprise_id":enterpriseId
		},
		success:function(result){
			if(result.code == "SUCCESS"){
				$('#verifiedModal').modal('hide');
				refreshData();
			}else{
				$('#verifiedModal').modal('hide');
				$.messager.alert("温馨提示","审核通过失败："+result.message);
			}
		}
	});
}
//企业认证审核不通过确认
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
	var enterpriseId = $("#notApprovedEnterpriseId").val();
	$.ajax({
		url:path+"/admin/authentication/enterprise/audit",
		cache: false,
		type:"post",
		data:{
			"review_state":"2",
			"reason":reason,
			"user_id":userId,
			"enterprise_id":enterpriseId
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
	$("#enterpriseName").val("");
	$("#businessLicense").val("");
}

function showImg(imgPath,title){
	$("#imgModalLabel").text(title);
	$("#imgModalBody").html("<img alt='"+title+"' src='"+imgPath+"' class='img-responsive' />");
	$("#imgModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
