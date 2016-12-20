var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var grid_balance_selector = "#grid-balance-table";
var pager_balance_selector = "#grid-balance-pager";
var auditUserId = null;
var auditEnterpriseId = null;
var selectImgPath = null;
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.98);
//	});
	$(grid_selector).jqGrid({
		url : path + "/admin/users/list",
		postData:{"userAuthType":"enterprise"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'createTime',
			index : 'createTime',
			label : '注册时间',
			width : 200
		}, {
			name : 'userName',
			index : 'userName',
			label : '注册账号'
		}, {
			name : 'nickName',
			index : 'nickName',
			label : '昵称'
		}, {
			name : 'userId',
			index : 'appUuid',
			label : '账号Id'
		}, {
			name : 'lastRole',
			index : 'lastRole',
			label : '账号类型',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="worker"){
					return "接包方";
				}else if(cellvalue=="employer"){
					return "发包方";
				}else{
					return "";
				}
			}
		}, {
			name : 'regionName',
			index : 'regionName',
			label : '所在城市',
			width :80
		}, {
			name : 'balance',
			index : 'balance',
			label : '账户金额',
			formatter:function(cellvalue, options, rowdata) {
				return "<a style='color:blue;' href='#' title='查看详情' onclick='balanceDetail(\""+rowdata.userId+"\")'>查看详情</a>";
			}
		}, {
			name : 'userDetail',
			index : 'userDetail',
			label : '用户详情',
			formatter:function(cellvalue, options, rowdata) {
				return "<a style='color:blue;' href='#' title='查看详情' onclick='showUser(\""+rowdata.userId+"\")'>查看详情</a>";
			}
		}, {
			name : 'reviewState',
			index : 'reviewState',
			label : '实名认证',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="0"){ //-1删除 0待审核 1禁用 2审核通过 3审核拒绝
					return "待认证";
				}else if(cellvalue=="1"){
					return "已认证";
				}else if(cellvalue=="2"){
					return "认证不通过";
				}else{
					return "未认证";
				}
			}
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			align :'center',
			formatter:function(cellvalue, options, rowdata) {
				var opt="";
				if(rowdata.reviewState == "0"){//审核中显示审核按钮
					opt += "<a style='color:blue;' href='#' title='审核' onclick='auditUser(\""+rowdata.userId+"\")'>审核</a>&nbsp;&nbsp;";
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
	
///////////////////////账户金额详情///////////////////////
	$(grid_balance_selector).jqGrid({
		url : path + "/admin/users/financialDetail/query",
		//postData:{"userId":userId},
		datatype : "json",
		mtype : 'POST',
		width:500,
		height : 200,   
		colModel : [ {
			name : 'userId',
			index : 'appUuid',
			label : 'UUID',
			hidden:true
		},{
			name : 'tradeTime',
			index : 'tradeTime',
			label : '创建时间'
		},{
			name : 'transType',
			index : 'transType',
			label : '交易类型',
			formatter:function(cellvalue, options, rowdata) {
				var transType = "";
				if( "recharge" == cellvalue){
					transType = "充值";
				}else if("withdraw" == cellvalue){
					transType = "提现";
				}else if("payment" == cellvalue){
					transType = "支付";
				}else if("income" == cellvalue){
					transType = "收入";
				}
				return transType;
			}
		},{
			name : 'targetType',
			index : 'targetType',
			label : '业务类型'
		},{
			name : 'money',
			index : 'money',
			label : '交易金额'
		},{
			name : 'tradeState',
			index : 'tradeState',
			label : '状态',
			formatter:function(cellvalue, options, rowdata) {
				var state = "";
				if( "0" == cellvalue){
					state = "未打款";
				}else if("1" == cellvalue){
					state = "已打款";
				}else if("-1" == cellvalue){
					state = "打款失败";
				}
				return state;
			}
		}
		],
		//viewrecords : true,// 是否显示行数
		rowNum : 15, // 每页显示记录数
		rowList : [ 15, 30, 45 ], // 可调整每页显示的记录数
		pager : pager_balance_selector,
		altRows : true, // 设置表格 zebra-striped 值
		gridview : true, // 加速显示
		//multiselect : true,// 是否支持多选
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
			}else{
				$(".norecords").hide();
			}
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
	$(grid_balance_selector).jqGrid('navGrid', pager_balance_selector, {
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
///////////////////////账户金额详情///////////////////////

	// 查询
	$("#queryBtn").click(function() {
		var reviewState = $("#qryReviewState").val();
		var userName = $("#qryUserName").val();
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
				userName : userName,
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
	$.ajax({
		url:path+"/admin/users/list",
		data:{"userAuthType":"enterprise","userId":userId},
		type:"POST",
		success:function(rs){
			if(rs.records > 0){
				var data = rs.rows[0];
				var imgPath ="http://27.151.123.121:11024/bfzb"+"/resource/img/"+data.businessLicenseImage;
				$("#userEnterpriseName").val(data.enterpriseName);
				$("#userCorporate").val(data.corporate);
				$("#userBusinessLicense").val(data.businessLicense);
				$("#userMobile").val(data.mobile);
				$("#userBusinessLicenseImage").attr("src",imgPath);
				$("#userModal").modal({
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
	$.ajax({
		url:path+"/admin/users/list",
		cache: false,
		data:{"userAuthType":"enterprise","userId":userId},
		type:"POST",
		success:function(rs){
			if(rs.records > 0){
				var data = rs.rows[0];
				var imgPath ="http://27.151.123.121:11024/bfzb"+"/resource/img/"+data.businessLicenseImage;
				$("#auditEnterpriseName").val(data.enterpriseName);
				$("#auditBusinessLicense").val(data.businessLicense);
				$("#auditCorporate").val(data.corporate);
				$("#auditBusinessLicenseImage").attr("src",imgPath);
				selectImgPath = imgPath;
				auditUserId = data.userId;
				auditEnterpriseId = data.enterpriseId
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
	if(reviewState!="1"){
		confirmMsg="确认审核不通过？";
	}
	var reason = "未填写待补充";
	$.messager.confirm("温馨提示", confirmMsg, function() {
		$.ajax({
			url:path+"/admin/authentication/enterprise/audit",
			cache: false,
			type:"post",
			data:{
				"review_state":reviewState,
				"user_id":auditUserId,
				"enterprise_id":auditEnterpriseId,
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
	$("#qryUserName").val("");
	$("#qryReviewState").val("");
	$("#dateRange").val("");
}

function refreshData() {
	$(grid_selector).jqGrid('setGridParam',{
		postData:{/*userName:null,realName:null,state:null*/},page : 1
	}).trigger("reloadGrid");
}

function refreshBalacneData() {
	$(grid_balance_selector).jqGrid('setGridParam',{
		postData:{/*userName:null,realName:null,state:null*/},page : 1
	}).trigger("reloadGrid");
}

function balanceDetail(userId){
	$.ajax({
		url:path+"/admin/users/balanceandimprest/query",
		cache: false,
		data:{"userId":userId},
		type:"post",
		success:function(rs){
			if(rs.code == "SUCCESS"){
				var data = rs.data;
				$("#balance").val(data.balance);
				$("#imprest").val(data.imprest);
				$(grid_balance_selector).jqGrid('setGridParam', {
					postData : {
						userId:userId
					},
					page : 1
				}).trigger("reloadGrid");
				$("#balanceModal").modal({
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

function showImg(title){
	$("#imgModalLabel").text(title);
	$("#imgModalBody").html("<img alt='"+title+"' src='"+selectImgPath+"' class='img-responsive' />");
	$("#imgModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
