var loading;
var user_grid_selector = "#user-grid-table";
var user_pager_selector = "#user-grid-pager";
var plat_grid_selector = "#plat-grid-table";
var plat_pager_selector = "#plat-grid-pager";
/*var pj_grid_selector= "#pj-grid-table";
var pj_pager_selector= "#pj-grid-pager";
var rw_grid_selector= "#rw-grid-table";
var rw_pager_selector= "#rw-grid-pager";*/
var detailUserId = null;
jQuery(function($) {
	getUserList();
	getPlatList();
	chgList(1);//默认显示用户资金
	// 查询
	$("#queryBtn").click(function() {
		query();
	})
});
function query(){
	var qryType = $("#qryType").val();
	var nickName = $("#qryNickName").val();
	var mobile = $("#qryMobile").val();
	var targetType = $("#qryTargetType").val();
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
	if(qryType==1){
		$(user_grid_selector).jqGrid('setGridParam', {
			postData : {
				nickName : nickName,
				mobile : mobile
			},
			page : 1
		}).trigger("reloadGrid");
	}else{
		$(plat_grid_selector).jqGrid('setGridParam', {
			postData : {
				nickName : nickName,
				mobile : mobile,
				targetType : targetType,
				qryStartTime : qryStartTime,
				qryEndTime : qryEndTime
			},
			page : 1
		}).trigger("reloadGrid");
	}
}
function getUserList(){
	$(user_grid_selector).jqGrid({
		url : path + "/admin/finance/userFinanceList/query",
		postData:{"userAuthType":"normal"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'userId',
			index : 'userId',
			label : 'UUID',
			hidden:true
		}, {
			name : 'mobile',
			index : 'mobile',
			label : '用户账号'
		}, {
			name : 'nickName',
			index : 'nickName',
			label : '昵称'
		}, {
			name : 'realName',
			index : 'realName',
			label : '姓名'
		}, {
			name : 'idcardCode',
			index : 'idcardCode',
			label : '身份证号'
		}, {
			name : 'balance',
			index : 'balance',
			label : '金额（元）'
		}, {
			name : 'lastRole',
			index : 'lastRole',
			label : '账号身份',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="worker"){
					return "接包方";
				}else if(cellvalue=="employer"){
					return "发包方";
				}else{
					return "";
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
				opt +="<center><a style='color:blue;' href='#' title='详情' onclick='showUserDetail(\""+rowdata.userId+"\",\""+rowdata.nickName+"\",\""+rowdata.balance+"\")'>详情</a></center>"
				return opt;
		   }
		}
		],
		viewrecords : true,// 是否显示行数
		rowNum : 15, // 每页显示记录数
		rowList : [ 15, 30, 45 ], // 可调整每页显示的记录数
		pager : user_pager_selector,
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
	$(user_grid_selector).jqGrid('navGrid', user_pager_selector, {
		edit : false,add : false,del : false,search : false,view : false,refresh : false
	});
}

function getPlatList(){
	$(plat_grid_selector).jqGrid({
		url : path + "/admin/finance/platFinanceList/query",
		postData:{"userAuthType":"normal"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'userId',
			index : 'userId',
			label : 'UUID',
			hidden:true
		}, {
			name : 'userName',
			index : 'userName',
			label : '用户账号'
		}, {
			name : 'nickName',
			index : 'nickName',
			label : '昵称'
		}, {
			name : 'tradeTime',
			index : 'tradeTime',
			label : '交易时间'
		}, {
			name : 'transType',
			index : 'transType',
			label : '交易类型',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="income"){
					return "收入";
				}else if(cellvalue=="payment"){
					return "支付";
				}else{
					return "";
				}
			}
		}, {
			name : 'targetType',
			index : 'targetType',
			label : '业务类型',
			formatter:function(cellvalue, options, rowdata) {
				var targetType = "";
				if(cellvalue=="fullmoney") targetType = "全款";
				if(cellvalue=="imprestmoney") targetType = "预付款";
				if(cellvalue=="revealmoney") targetType = "兜底服务费";
				if(cellvalue=="remainmoney") targetType = "尾款";
				if(cellvalue=="returnmoney") targetType = "退款";
				if(cellvalue=="marginmoney") targetType = "质保金";
				return targetType;
			}
		}, {
			name : 'money',
			index : 'money',
			label : '交易金额（元）'
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			align :'center',
			formatter:function(cellvalue, options, rowdata) {
				var opt="";
					opt +="<center>无</center>"
    			return opt;
		   }
		}
		],
		viewrecords : true,// 是否显示行数
		rowNum : 15, // 每页显示记录数
		rowList : [ 15, 30, 45 ], // 可调整每页显示的记录数
		pager : plat_pager_selector,
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
	$(plat_grid_selector).jqGrid('navGrid', plat_pager_selector, {
		edit : false,add : false,del : false,search : false,view : false,refresh : false
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
 * 用户和平台切换
 * @param value
 */
function chgList(value){
	//清空查询条件,并查询全部
	refreshData(user_grid_selector);
	refreshData(plat_grid_selector);
	if(value=="1"){
		$("#userFinance").show();
		$("#platFinance").hide();
		$("#platQuerys").hide();
	}else{
		$("#userFinance").hide();
		$("#platFinance").show();
		$("#platQuerys").show();
	}
}


/**
 * 详情页面
 * @param userId
 */
function showUserDetail(userId,nickName,balance) {
	detailUserId = userId;
	//重置表单
	$('#showForm')[0].reset();
	if(nickName==null || nickName=="null" || nickName==undefined) nickName="";
	$("#nickName").html("<div style='color:blue'>"+nickName+"</div>");
	$("#money").html("<a style='color:red'>￥"+Number(balance).toFixed(2)+"</a>");
	//获取托管中的项目资金
	$.ajax({
		url:path+"/admin/users/"+userId+"/resume",
		cache: false,
		type:"get",
		success:function(rs){
			if(rs.sucess){
				
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
	chgUserDetail(1);//默认显示项目
	
	$("#showUserModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
/**
 * 用户资金详情切换
 */

function chgUserDetail(type){
	toPageNum=0;
	if(type==1){
		$("#pjnav").addClass("findnavOn");
		$("#wrnav").removeClass("findnavOn");
		$("#pjTximalis").show();
		$("#wrTximalis").hide();
		getPjTable(detailUserId);
	}else{
		$("#wrnav").addClass("findnavOn");
		$("#pjnav").removeClass("findnavOn");
		$("#pjTximalis").hide();
		$("#wrTximalis").show();
		getWrTable(detailUserId);
	}
	
}

/**
 * 获取项目资金明细数据
 * @param userId
 */
function getPjTable(userId){
	$.ajax({
		url:path+"/admin/finance/userProjectDetailList/query",
		cache: false,
		data:{
			pageNum:toPageNum,
			pageSize:pageSize,
			userId:userId
		},
		type:"post",
		success:function(rs){
			if(rs.sucess){
				var data = rs.data.pageData;
				var dataHtml = "";
				if(data.length>0){
					dataHtml += "<ul id='pjPageData' style='height:400px;'>";
					for(var i=0;i<data.length;i++){
						var transType = data[i].transType;
						var targetType = data[i].targetType;
						if(transType=="payment")transType="支付";
						if(transType=="income")transType="收入";
						//交易对象类型(业务类型) fullmoney全款 | imprestmoney预付款 | revealmoney兜底服务费  | remainmoney尾款  | returnmoney退款
						if(targetType=="fullmoney") targetType = "全款";
						if(targetType=="imprestmoney") targetType = "预付款";
						if(targetType=="revealmoney") targetType = "兜底服务费";
						if(targetType=="remainMoney") targetType = "尾款";
						if(targetType=="returnMoney") targetType = "退款";
						if(targetType=="marginMoney") targetType = "质保金";
						dataHtml += "<li>";
						dataHtml += "<span class='txitime'>"+data[i].tradeTime+"</span>";
						dataHtml += "<span class='txithre'><em>"+transType+"</em></span>";
						dataHtml += "<span class='txidety'><"+data[i].targetName+">项目"+targetType+"<em>¥"+data[i].money.toFixed(2)+"元</em></span>";
						dataHtml += "</li>";
					}
					dataHtml += "</ul>";
					//分页
					dataHtml += "<div class='pagnation'>";
					dataHtml += "<div class='pageStyle' id='pjPagination'></div>";
					dataHtml += "<div class='searchPage'>";
					dataHtml += "<span class='page-sum'>共<strong class='allPage'>"+rs.data.totalPages+"</strong>页</span>";
					dataHtml += "<span class='page-go'>跳转<input type='text'>页</span>";
					dataHtml += "<a href='javascript:;' class='page-btn'>GO</a>";
					dataHtml += "</div></div>";
					$("#pjTximalis").html(dataHtml);
					$("#pjPagination").pagination(rs.data.totalPages,{show_if_single_page:true,callback:function(){userProjectDetailListOnePage(userId)}});
				}else{
					dataHtml += "<div class='metamlis'><img src='"+path+"/pages/assets/css/images/fundmaIcon-2.png'/><p>还未产生任何交易记录</p></div>"
					$("#pjTximalis").html(dataHtml);
				}
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
	
}
/**
 * 项目交易翻页回调
 * @param userId
 */
function userProjectDetailListOnePage(userId){
	$.ajax({
		url:path+"/admin/finance/userProjectDetailList/query",
		cache: false,
		data:{
			pageNum:toPageNum,
			pageSize:pageSize,
			userId:userId
		},
		type:"post",
		success:function(rs){
			if(rs.sucess){
				var data = rs.data.pageData;
				var dataHtml = "";
				if(data.length>0){
					for(var i=0;i<data.length;i++){
						var transType = data[i].transType;
						var targetType = data[i].targetType;
						if(transType=="payment")transType="支付";
						if(transType=="income")transType="收入";
						//交易对象类型(业务类型) fullmoney全款 | imprestmoney预付款 | revealmoney兜底服务费  | remainMoney尾款  | returnMoney退款
						if(targetType=="fullmoney") targetType = "全款";
						if(targetType=="imprestmoney") targetType = "预付款";
						if(targetType=="revealmoney") targetType = "兜底服务费";
						if(targetType=="remainmoney") targetType = "尾款";
						if(targetType=="returnmoney") targetType = "退款";
						if(targetType=="marginmoney") targetType = "质保金";
						dataHtml += "<li>";
						dataHtml += "<span class='txitime'>"+data[i].tradeTime+"</span>";
						dataHtml += "<span class='txithre'><em>"+transType+"</em></span>";
						dataHtml += "<span class='txidety'><"+data[i].targetName+">项目"+targetType+"<em>¥"+data[i].money.toFixed(2)+"元</em></span>";
						dataHtml += "</li>";
					}
					$("#pjPageData").html(dataHtml);
				}
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
}

function getWrTable(userId){
	$.ajax({
		url:path+"/admin/finance/userWRDetailList/query",
		cache: false,
		data:{
			pageNum:toPageNum,
			pageSize:pageSize,
			userId:userId
		},
		type:"post",
		success:function(rs){
			if(rs.sucess){
				var data = rs.data.pageData;
				var dataHtml = "";
				if(data.length>0){
					dataHtml += "<ul id='wrPageData' style='height:400px;'>";
					for(var i=0;i<data.length;i++){
						var transType = data[i].transType;
						var targetType = data[i].targetType;
						var tradePlatform = data[i].tradePlatform;
						var tradeState = data[i].tradeState;
						
						if(transType=="recharge")transType="充值";
						if(transType=="withdraw")transType="提现";
						if(tradePlatform=="alipay") tradePlatform = "支付宝"+transType;
						if(tradePlatform=="wechatpay") tradePlatform = "微信"+transType;
						if(tradePlatform=="unionpay") tradePlatform = "银联"+transType;
						if(tradePlatform=="offline") tradePlatform = "线下汇款"+transType;
						//交易状态 0未打款，1已打款 ， -1打款失败
						if(tradeState=="0") tradeState = "<em style='color:blue'>未打款</em>";
						if(tradeState=="1") tradeState = "<em style='color:green'>已打款</em>";
						if(tradeState=="-1") tradeState = "<em style='color:red'>打款失败</em>";
						dataHtml += "<li>";
						dataHtml += "<span class='txitime'>"+data[i].tradeTime+"</span>";
						dataHtml += "<span class='txithre'><em>"+transType+"</em></span>";
						dataHtml += "<span class='txidety'>"+tradePlatform+"：<em>¥"+data[i].money.toFixed(2)+"元</em>&nbsp;&nbsp;"+tradeState+"</span>";
						dataHtml += "</li>";
					}
					dataHtml += "</ul>"
					//分页
					dataHtml += "<div class='pagnation'>";
					dataHtml += "<div class='pageStyle' id='wrPagination'></div>";
					dataHtml += "<div class='searchPage'>";
					dataHtml += "<span class='page-sum'>共<strong class='allPage'>"+rs.data.totalPages+"</strong>页</span>";
					dataHtml += "<span class='page-go'>跳转<input type='text'>页</span>";
					dataHtml += "<a href='javascript:;' class='page-btn'>GO</a>";
					dataHtml += "</div></div>";
					$("#wrTximalis").html(dataHtml);
					$("#wrPagination").pagination(rs.data.totalPages,{show_if_single_page:true,callback:function(){userWRDetailListOnePage(userId)}});
				}else{
					dataHtml += "<div class='metamlis'><img src='"+path+"/pages/assets/css/images/fundmaIcon-2.png'/><p>还未产生任何交易记录</p></div>"
					$("#wrTximalis").html(dataHtml);
				}
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
	
}

/**
 * 充值提现翻页回调
 * @param userId
 */
function userWRDetailListOnePage(userId){
	$.ajax({
		url:path+"/admin/finance/userWRDetailList/query",
		cache: false,
		data:{
			pageNum:toPageNum,
			pageSize:pageSize,
			userId:userId
		},
		type:"post",
		success:function(rs){
			if(rs.sucess){
				var data = rs.data.pageData;
				var dataHtml = "";
				if(data.length>0){
					for(var i=0;i<data.length;i++){
						var transType = data[i].transType;
						var targetType = data[i].targetType;
						var tradePlatform = data[i].tradePlatform;
						var tradeState = data[i].tradeState;
						
						if(transType=="recharge")transType="充值";
						if(transType=="withdraw")transType="提现";
						if(tradePlatform=="alipay") tradePlatform = "支付宝"+transType;
						if(tradePlatform=="wechatpay") tradePlatform = "微信"+transType;
						if(tradePlatform=="unionpay") tradePlatform = "银联"+transType;
						if(tradePlatform=="offline") tradePlatform = "线下汇款"+transType;
						//交易状态 0未打款，1已打款 ， -1打款失败
						if(tradeState=="0") tradeState = "<em style='color:blue'>未打款</em>";
						if(tradeState=="1") tradeState = "<em style='color:green'>已打款</em>";
						if(tradeState=="-1") tradeState = "<em style='color:red'>打款失败</em>";
						dataHtml += "<li>";
						dataHtml += "<span class='txitime'>"+data[i].tradeTime+"</span>";
						dataHtml += "<span class='txithre'><em>"+transType+"</em></span>";
						dataHtml += "<span class='txidety'>"+tradePlatform+"：<em>¥"+data[i].money.toFixed(2)+"元</em>&nbsp;&nbsp;"+tradeState+"</span>";
						dataHtml += "</li>";
					}
					$("#wrPageData").html(dataHtml);
				}
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
	$("#qryNickName").val("");
	$("#qryMobile").val("");
	$("#qryTargetType").val("");
	$("#dateRange").val("");
}
function updateStu() {
	$.messager.alert("updateStu");
}

function refreshData(grid) {
	resetQry();
	query();
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
