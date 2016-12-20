var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var auditUserId = null;
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.98);
//	});
	var showNav=parent.document.getElementById("showNav");
	$(showNav).html("提现管理");
	$(grid_selector).jqGrid({
		url : path + "/admin/finance/action/list",
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'recordId',
			index : 'recordId',
			label : '申请Id',
			hidden:true
		},{
			name : 'applyTime',
			index : 'applyTime',
			label : '申请提现时间',
			width : 200
		}, {
			name : 'nickName',
			index : 'nickName',
			label : '用户昵称'
		}, {
			name : 'mobile',
			index : 'mobile',
			label : '联系手机'
		}, {
			name : 'realName',
			index : 'realName',
			label : '支付账号名称'
		}, {
			name : 'applyType',
			index : 'applyType',
			label : '提现方式',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue == 'alipay') return "支付宝";
				if(cellvalue == 'unionpay') return "银联";
				return cellvalue;
			}
		}, {
			name : 'applyCode',
			index : 'applyCode',
			label : '收款账号'
		}, {
			name : 'idcardCode',
			index : 'idcardCode',
			label : '身份证号'
		}, {
			name : 'applyMoney',
			index : 'applyMoney',
			label : '提现金额'
		}, {
			name : 'reviewState',
			index : 'reviewState',
			label : '款项状态',
			formatter:function(cellvalue, options, rowdata) {//待审批watting | 提现成功withdraw_succeed | 提现失败withdraw_failure
				if("watting" == cellvalue) return "<span style='color:blue'>待审批</span>";
				if("withdraw_succeed" == cellvalue) return "<span style='color:green'>提现成功</span>";
				if("withdraw_failse" == cellvalue) return "<span style='color:red'>提现失败</span>";
				return cellvalue;
			}
		}, {
			name : 'reviewAdminName',
			index : 'reviewAdminName',
			label : '操作人'
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			align :'center',
			width : 200,
			formatter:function(cellvalue, options, rowdata) {
				var opt="";
				if(rowdata.reviewState=="watting"){//待认证则显示审核按钮
					opt += "<a style='color:blue;' href='javascript:void(0)' title='已转账' onclick='withrawAction(1,\""+options.rowId+"\")'>已转账</a>&nbsp;&nbsp;";
					opt += "<a style='color:blue;' href='javascript:void(0)' title='转账失败' onclick='withrawAction(2,\""+options.rowId+"\")'>转账失败</a>";
				}
				opt +="";
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
	updatePagerIcons(grid_selector);
	// 设置分页按钮组
	$(grid_selector).jqGrid('navGrid', pager_selector, {
		edit : false,
		add : false,
		del : false,
		search : false,
		view : false,
		refresh : false
	});

	// 查询
	$("#queryBtn").click(function() {
		query();
	});
});
function query(){
	var applyType = $("#qryApplyType").val();
	var mobile = $("#qryMobile").val();
	var reviewState = $("#qryReviewState").val();
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
			applyType : applyType,
			mobile : mobile,
			reviewState : reviewState,
			qryStartTime : qryStartTime,
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
 * 提现操作
 * @param userId
 */
function withrawAction(type,rowid) {
	$("#topass").attr("disabled",false);
	//重置表单
	$('#withrawForm')[0].reset();
	$("#withrawModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
	
	if(type == 1){
		$("#withrawModalLabel").html("转账成功操作");
		$("#optMsg").html("资料核对正确，请确认是否要进行转账提现操作！");
	}else {
		$("#withrawModalLabel").html("转账失败操作");
		$("#optMsg").html("操作失败，收款账户有误，请确认进行转账失败操作！");
	}
	var row = $(grid_selector).getRowData(rowid);
	
	$("#actionType").val(type);
	$("#recordId").val(row.recordId);
	$("#realName").html(row.realName);
	$("#applyMoney").html(row.applyMoney);
	
	if(row.applyType == 'alipay'){
		$("#applyType").html("支付宝");
	}else if(row.applyType == 'unionpay'){
		$("#applyType").html("银联");
	}else {
		$("#applyType").html(row.applyType);
	}
	$("#applyCode").html(row.applyCode);
}

function withrawActionConfirm(){
	$("#topass").attr("disabled",true);
	var type = $("#actionType").val();
	var recordId = $("#recordId").val();
	var applyReason = $("#applyReason").val();
	$.ajax({
		url:path+"/admin/finance/action/withrawAction",
		cache: false,
		type:"post",
		data:{
			"type" : type,
			"recordId" : recordId,
			"applyReason":applyReason
		},
		success:function(result){
			$("#topass").attr("disabled",false);
			if(result.code == '0'){
				$.messager.alert("温馨提示",result.message);
				$("#withrawModal").modal('hide');
				query();
			}else{
				$.messager.alert("温馨提示",result.message);
			}
		},
		error:function(e){
			$("#topass").attr("disabled",false);
			$.messager.alert("温馨提示","网络异常！");
		}
	});
}
/**
 * 重置查询条件
 */
function resetQry(){
	$("#qryApplyType").val("");
	$("#qryMobile").val("");
	$("#qryReviewState").val("");
	$("#dateRange").val("");
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
