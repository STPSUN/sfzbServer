var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var auditUserId = null;
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.98);
//	});
	var showNav=parent.document.getElementById("showNav");
	$(showNav).html("普通用户");
	$(grid_selector).jqGrid({
		url : path + "/admin/users/list",
		postData:{"userAuthType":"normal"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'userId',
			index : 'appUuid',
			label : 'UUID',
			hidden:true
		},{
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
			name : 'lastProvinceCode',
			index : 'lastProvinceCode',
			label : 'lastProvinceCode',
			hidden:true
		}, {
			name : 'lastProvinceName',
			index : 'lastProvinceName',
			label : '省份',
			width :80
		}, {
			name : 'lastCityCode',
			index : 'lastCityCode',
			label : 'lastCityCode',
			hidden:true
		}, {
			name : 'lastCityName',
			index : 'lastCityName',
			label : '城市',
			width :80
		}, {
			name : 'bindState',
			index : 'bindState',
			label : '绑定状态'
		}, {
			name : 'bindSRP',
			index : 'bindSRP',
			label : '绑定SRP'
		}, {
			name : 'balance',
			index : 'balance',
			label : '账号金额（元）'
		}, {
			name : 'reviewState',
			index : 'reviewState',
			label : '实名认证',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="0"){ //-1删除 0待审核 1禁用 2审核通过 3审核拒绝
					return "待认证";
				}else if(cellvalue=="2"){
					return "已认证";
				}else if(cellvalue=="3"){
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
				if(rowdata.reviewState=="0"){//待认证则显示审核按钮
					opt += "<a style='color:blue;' href='#' title='审核' onclick='auditUser(\""+rowdata.userId+"\")'>审核</a>&nbsp;&nbsp;";
				}
					opt +="<a style='color:blue;' href='#' title='查看详情' onclick='showUser(\""+rowdata.userId+"\")'>查看详情</a>"
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
				$("#ability").val(rs.data.abilitysName);
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
	//重置表单
	$('#auditForm')[0].reset();
	$.ajax({
		url:path+"/admin/users/"+userId+"/resume",
		cache: false,
		type:"get",
		success:function(rs){
			if(rs.sucess){
				$("#auditModal").modal({
					keyboard : false,
					show : true,
					backdrop : "static"
				});
				auditUserId = rs.data.userId;
				$("#idcardPhoto1").attr("src",rs.data.idcardPhoto1Url);
				$("#idcardPhoto2").attr("src",rs.data.idcardPhoto2Url);
				$("#idcardPhoto3").attr("src",rs.data.idcardPhoto3Url);
				$("#auditRealName").val(rs.data.realName);
				$("#auditIdcardCode").val(rs.data.idcardCode);
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
			url:path+"/admin/users/person/"+auditUserId+"/audit",
			cache: false,
			type:"post",
			data:{
				"review_state":reviewState,
				"reason":reason
			},
			success:function(rs){
				if(rs.sucess){
					$("#auditModal").modal('hide');
					$.messager.alert("温馨提示",'审核成功!');
					refreshData();
				}else{
					$.messager.alert("温馨提示",'审核失败!');
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
function updateStu() {
	$.messager.alert("updateStu");
}

function refreshData() {
	$(grid_selector).jqGrid('setGridParam',{
		postData:{/*userName:null,realName:null,state:null*/},page : 1
	}).trigger("reloadGrid");
}


/**
 * 重置file框
 * @param file
 */
function resetFileInput(file){ 
	file.after(file.clone().val("")); 
	file.remove();
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
