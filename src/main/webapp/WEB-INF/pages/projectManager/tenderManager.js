var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.99);
//	});
	var showNav=parent.document.getElementById("showNav");
	$(showNav).html("投标列表");
	$(grid_selector).jqGrid({
		url : path + "/admin/tenderManager/action/list",
//		postData:{"userAuthType":"normal"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'recordId',
			index : 'recordId',
			label : 'recordId',
			hidden:true
		},{
			name : 'projectId',
			index : 'projectId',
			label : 'recordId',
			hidden:true
		} ,{
			name : 'projectName',
			index : 'projectName',
			label : '项目名称'
		},{
			name : 'createTime',
			index : 'createTime',
			label : '投标时间'
		}, {
			name : 'tenderUserName',
			index : 'tenderUserName',
			label : '投标人'
		}, {
			name : 'offer',
			index : 'offer',
			label : '报价'
		}, {
			name : 'description',
			index : 'description',
			label : '报价说明'
		}, {
			name : 'isMargin',
			index : 'isMargin',
			label : '是否保证售后',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue==0 || cellvalue == 'null' || cellvalue == null){ //是否质保（0：不质保 1：质保）
					return "不质保";
				}else if(cellvalue==1){
					return "质保";
				}else{
					return cellvalue;
				}
			}
		}, {
			name : 'isCompleteFinish',
			index : 'isCompleteFinish',
			label : '是否保证完成'
		}, {
			name : 'state',
			index : 'state',
			label : '是否中标',
			formatter:function(cellvalue, options, rowdata) {
				if(cellvalue=="0"){ //投标状态0 发起投标；1被拒绝；2被采纳
					return "发起投标";
				}else if(cellvalue=="1"){
					return "被拒绝";
				}else if(cellvalue=="2"){
					return "被采纳";
				}else{
					return cellvalue;
				}
			}
		}, {
			name : 'tenderTime',
			index : 'tenderTime',
			label : '中标时间'
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			hidden:true,
			formatter:function(cellvalue, options, rowdata) {
				var opt = '';
				
				opt += "<button type='button' class='btn btn-link' onclick='showAttach(\""+rowdata.recordId+"\")'>查看附件</button>";
				
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
		beforeRefresh : query()
	});

	// 查询
	$("#queryBtn").click(function() {
		query();
	});
});
//查询
function query(){
	
	var stateSea = $("#stateSea").val();
	var projectNameSea = $("#projectNameSea").val();
	var dateRange = $("#dateRange").val();
	var qryBeginTime="";
	var qryEndTime="";
	if(dateRange!=null && dateRange!=""){
		var drArr = dateRange.split(" - ");
		if(drArr.length>=2){
			qryBeginTime = drArr[0];
			qryEndTime = drArr[1];
		}
	}
	$(grid_selector).jqGrid('setGridParam', {
		postData : {
			stateSea : stateSea,
			projectNameSea : projectNameSea,
			qryBeginTime : qryBeginTime,
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
			});
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
function showAttach(recordId){
	$.messager.alert("温馨提示",'查看附件正在处理！');
}
/**
 * 重置查询条件
 */
function resetAppQry(){
	$("#stateSea").val('');
	$("#projectNameSea").val('');
	$("#dateRange").val('');
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
