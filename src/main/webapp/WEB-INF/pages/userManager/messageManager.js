var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var grid_balance_selector = "#grid-balance-table";
var pager_balance_selector = "#grid-balance-pager";
var auditUserId = null;
var auditEnterpriseId = null;
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.98);
//	});
	$(grid_selector).jqGrid({
		url : path + "/admin/users/message/query",
		datatype : "json",
		mtype : 'get',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'userId',
			index : 'appUuid',
			label : 'UUID',
			hidden:true
		}, {
			name : 'messgeId',
			index : 'messgeId',
			label : '消息Id',
			hidden:true
		}, {
			name : 'userName',
			index : 'userName',
			label : '推送账号'
		}, {
			name : 'nickName',
			index : 'nickName',
			label : '昵称'
		}, {
			name : 'content',
			index : 'content',
			label : '消息内容'
		}, {
			name : 'createTime',
			index : 'createTime',
			label : '推送时间'
		}, {
			name : 'isRead',
			index : 'isRead',
			label : '消息状态',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="0"){
					return "未读";
				}else if(cellvalue=="1"){
					return "已读";
				}else{
					return "";
				}
			}
		}, {
			name : 'readTime',
			index : 'readTime',
			label : '已读时间',
			formatter:function(cellvalue, options, rowdata) {
				if(rowdata.isRead =="0"){
					return "";
				}else if(rowdata.isRead =="1"){
					return cellvalue;
				}else{
					return "";
				}
			}
		}, {
			name : 'sendUser',
			index : 'senUser',
			label : '发布人员'
		}, {
			name : 'channel',
			index : 'channel',
			label : '发送渠道',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="push"){
					return "推送";
				}else if(cellvalue=="sms"){
					return "短信";
				}else{
					return "";
				}
			}
		}, {
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			align :'center',
			formatter:function(cellvalue, options, rowdata) {
				var opt = "<a style='color:blue;' href='#' title='删除' onclick='deleteMsg(\""+rowdata.messgeId+"\")'>删除</a>"
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
		var nickName = $("#qryNickName").val();
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
				nickName : nickName,
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
 * 删除操作
 * @param messageId
 */
function deleteMsg(messageId) {
	var confirmMsg="确认删除消息记录？";
	$.messager.confirm("温馨提示", confirmMsg, function() {
		$.ajax({
			url:path+"/admin/users/message/"+messageId+"/delete",
			cache: false,
			type:"get",
			success:function(rs){
				if(rs.sucess){
					$.messager.alert("温馨提示",'删除成功!');
					refreshData();
				}else{
					$.messager.alert("温馨提示",'删除失败!');
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
	$("#qryNickName").val("");
	$("#dateRange").val("");
}

function refreshData() {
	$(grid_selector).jqGrid('setGridParam',{
		postData:{/*userName:null,realName:null,state:null*/},page : 1
	}).trigger("reloadGrid");
}