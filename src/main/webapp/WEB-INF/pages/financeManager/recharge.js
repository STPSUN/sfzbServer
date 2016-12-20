var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var auditUserId = null;
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.98);
//	});
	var showNav=parent.document.getElementById("showNav");
	$(showNav).html("充值管理");
	$(grid_selector).jqGrid({
		url : path + "/admin/finance/rechargeList/query",
		//postData:{"userAuthType":"normal"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ 
             {name : 'recordId',index : 'recordId',hidden:true},
             {name : 'payUserId',index : 'payUserId',hidden:true},
             {name : 'bankSerialId',index : 'bankSerialId',hidden:true},//用户填写订单流水号
             {name : 'tradeTime',index : 'tradeTime',label : '充值时间',width : 200}, 
             {name : 'nickName',index : 'nickName',label : '用户昵称'},
             {name : 'mobile',index : 'mobile',label : '联系手机'}, 
             {name : 'realName',index : 'realName',label : '支付账号名称'},
             {name : 'mobile',index : 'mobile',label : '充值账号'},
             {name : 'idcardCode',index : 'idcardCode',label : '身份证号'},
             {name : 'money',index : 'money',label : '充值金额'},
             {name : 'tradePlatform',index : 'tradePlatform',label : '充值方式',
            	 formatter:function(cellvalue, options, rowdata) {
						if(cellvalue=="alipay"){
							return "支付宝";
						}else if(cellvalue=="wechatpay"){
							return "微信支付";
						}else if(cellvalue=="unionpay"){
							return "银联支付";
						}else if(cellvalue=="offline"){
							return "线下汇款";
						}else{
							return "";
						}
					}
             }, 
			{name : 'tradeNo',index : 'tradeNo',label : '订单号',width:200},
			{name : 'tradeState',index : 'tradeState',label : '充值状态',
				formatter:function(cellvalue, options, rowdata) {
					if(cellvalue=="0"){
						return "<span style='color:blue'>待付款</span>";
					}else if(cellvalue=="1"){
						return "<span style='color:green'>支付成功</span>";
					}else if(cellvalue=="-1"){
						return "<span style='color:red'>支付失败</span>";
					}else{
						return "";
					}
				}
			},
			{name : 'opt',index : 'opt',label : '操作',title : false,align :'center',
				formatter:function(cellvalue, options, rowdata) {
					var opt="";
					if(rowdata.tradeState=="0" && rowdata.tradePlatform=="offline"){//待付款、线下汇款则显示核对按钮
						opt += "<a style='color:blue;' href='javascript:void(0)' title='核对' onclick='auditRecharge(\""+rowdata.recordId+"\",\""+rowdata.payUserId+"\",\""+rowdata.money+"\",\""+rowdata.bankSerialId+"\")'>核对</a>";
					}else{
						opt +="无";
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
	updatePagerIcons(grid_selector);
	// 查询
	$("#queryBtn").click(function() {
		var tradePlatform = $("#qryTradePlatform").val();
		var tradeState = $("#qryTradeState").val();
		var mobile = $("#qryMobile").val();
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
				tradePlatform : tradePlatform,
				tradeState : tradeState,
				mobile : mobile,
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

function auditRecharge(recordId,payUserId,money,bankSerialId){
	$("#pass").attr("disabled",false);
	$("#noPass").attr("disabled",false);
	$("#auditRecordId").val(recordId);
	$("#auditPayUserId").val(payUserId);
	$("#auditMoney").val(money);
	$("#auditBankSerialId").val(bankSerialId);
	$("#auditModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}

function auditSend(reviewState){
	var recordId = $("#auditRecordId").val();
	var payUserId = $("#auditPayUserId").val();
	var bankAccountTime = $("#auditBankAccountTime").val();
	var money = $("#auditMoney").val();
	var bankSerialId = $("#auditBankSerialId").val();
	
	if(isNull(bankAccountTime)){
		$.messager.alert("温馨提示",'到账时间不能为空!');
		return;
	}
	if(isNull(money)){
		$.messager.alert("温馨提示",'到账金额不能为空!');
		return;
	}
	if(Number(money)<=0){
		$.messager.alert("温馨提示",'到账金额需大于0元!');
		return;
	}
	if(isNull(bankSerialId)){
		$.messager.alert("温馨提示",'订单流水号不能为空!');
		return;
	}
	var confirmMsg="确认核对通过？";
	if(reviewState!="2"){
		confirmMsg="确认核对不通过？";
	}
	$.messager.confirm("温馨提示", confirmMsg, function(ret) {
		$("#pass").attr("disabled",true);
		$("#noPass").attr("disabled",true);
		$.ajax({
			url:path+"/admin/finance/recharge/audit",
			cache: false,
			type:"post",
			data:{
				"recordId":recordId,
				"payUserId":payUserId,
				"bankAccountTime":bankAccountTime,
				"money":money,
				"bankSerialId":bankSerialId,
				"reviewState":reviewState
			},
			success:function(rs){
				$("#pass").attr("disabled",false);
				$("#noPass").attr("disabled",false);
				if(rs.sucess){
					$("#auditModal").modal('hide');
					$.messager.alert("温馨提示",'核对成功!');
					refreshData();
				}else{
					$.messager.alert("温馨提示",'核对失败!');
				}
			}
		});
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
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
}

/**
 * 重置查询条件
 */
function resetQry(){
	$("#qryTradePlatform").val("");
	$("#qryTradeState").val("");
	$("#qryMobile").val("");
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

function isNull(str){
	if(str=="" || str==null || str==undefined || str=="null"){
		return true;
	}
	return false;
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
