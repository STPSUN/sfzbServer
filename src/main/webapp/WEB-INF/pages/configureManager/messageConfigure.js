var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var grid_balance_selector = "#grid-balance-table";
var pager_balance_selector = "#grid-balance-pager";
var actionType = "add";//新增add,修改update
var selectAbilityId = null;
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.98);
//	});
	$(grid_selector).jqGrid({
		url : path + "/admin/configure/message/actions/search",
		datatype : "json",
		mtype : 'get',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'configureId',
			index : 'configureId',
			label : '配置Id',
			hidden:true
		}, {
			name : 'alertStyle',
			index : 'alertStyle',
			label : '提醒类别',
			formatter:function(cellvalue, options, rowdata) {
				if("all" == cellvalue){
					return "全部";
				}else if("project_review" == cellvalue){
					return "项目审核";
				}else{
					return "";
				}
			}
		}, {
			name : 'mobile',
			index : 'mobile',
			label : '接收手机号'
		}, {
			name : 'realName',
			index : 'realName',
			label : '负责人'
		}, {
			name : 'createTime',
			index : 'createTime',
			label : '创建时间'
		}, {
			name : 'state',
			index : 'state',
			label : '状态',
			formatter:function(cellvalue, options, rowdata) {
				if("0" == cellvalue){
					return "停止";
				}else if("1" == cellvalue){
					return "启用";
				}else{
					return "";
				}
			}
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			align: 'center',
			title : false,
			formatter:function(cellvalue, options, rowdata) {
				var opt = "<button type='button' class='btn btn-link' onclick='del(\""+rowdata.configureId+"\")'>删除</button>";
				if(rowdata.state == "1"){
					opt += "<button type='button' class='btn btn-link' onclick='stop(\""+rowdata.configureId+"\")' >停止</button>";
				}else if(rowdata.state == "0"){
					opt += "<button type='button' class='btn btn-link' onclick='start(\""+rowdata.configureId+"\")' >开启</button>";
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
		var alertStyle = $("#qryAlertStyle").val();
		var mobile = $("#qryMobile").val();
		$(grid_selector).jqGrid('setGridParam', {
			postData : {
				alertStyle : alertStyle,
				mobile : mobile
			},
			page : 1
		}).trigger("reloadGrid");
	})
	
	// 添加配置
	$("#addMessageConfigure").click(function() {
		//重置表单
		$('#showForm')[0].reset();
		$("#addModalLabel").text("添加短信配置");
		$("#showModal").modal({
			keyboard : false,
			show : true,
			backdrop : "static"
		});
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
 * 重置查询条件
 */
function resetQry(){
	$("#qryAlertStyle").val("all");
	$("#qryMobile").val("");
}

function refreshData() {
	$(grid_selector).jqGrid('setGridParam',{
		postData:{/*userName:null,realName:null,state:null*/},page : 1
	}).trigger("reloadGrid");
}

/**
 * 添加短信配置
 * @param userId
 */
function changeSend() {
	var alertStyle = $("#alertStyle").val();
	var mobile = $("#mobile").val();
	if(mobile == null || mobile.trim() == "" ){
		$.messager.alert("温馨提示",'请填写接收手机号!');
		return;
	}
	var realName = $("#realName").val();
	if(realName == null || realName.trim() == ""){
		$.messager.alert("温馨提示",'请填写负责人!');
		return;
	}
	$.ajax({
		url:path+"/admin/configure/message/action/add",
		cache: false,
		type:"post",
		data:{
			"alertStyle":alertStyle,
			"mobile":mobile,
			"realName":realName
		},
		success:function(rs){
			if(rs.code == "SUCCESS"){
				refreshData();
				$('#showModal').modal('hide');
			}else{
				$.messager.alert("温馨提示",'短信配置添加失败!'+rs.message);
			}
		}
	});
}

//开启配置
function start(configureId){
	$.ajax({
		url:path+"/admin/configure/message/start/"+configureId,
		cache: false,
		type:"get",
		success:function(rs){
			if(rs.code == "SUCCESS"){
				refreshData();
			}else{
				$.messager.alert("温馨提示",'启用短信配置失败!'+rs.message);
			}
		}
	});
}

//停止配置
function stop(configureId){
	$.ajax({
		url:path+"/admin/configure/message/stop/"+configureId,
		cache: false,
		type:"get",
		success:function(rs){
			if(rs.code == "SUCCESS"){
				refreshData();
			}else{
				$.messager.alert("温馨提示",'停用短信配置失败!'+rs.message);
			}
		}
	});
}

//删除配置
function del(configureId){
	$.ajax({
		url:path+"/admin/configure/message/"+configureId,
		cache: false,
		type:"delete",
		success:function(rs){
			if(rs.code == "SUCCESS"){
				refreshData();
			}else{
				$.messager.alert("温馨提示",'短信配置删除失败!'+rs.message);
			}
		}
	});
}
