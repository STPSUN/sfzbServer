var loading;
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.99);
//	});
	
	var showNav=parent.document.getElementById("showNav");
	$(showNav).html("网站新闻管理");
	$(grid_selector).jqGrid({
		url : path + "/admin/webNewsManager/action/list",
//		postData:{"userAuthType":"normal"},
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'advId',
			index : 'advId',
			label : 'UUID',
			hidden:true
		},{
			name : 'title',
			index : 'title',
			label : '新闻名称'
		},{
			name : 'advImg',
			index : 'advImg',
			label : '新闻图片',
			align :'center',
			formatter:function(cellvalue, options, rowdata){
				var val = cellvalue;
				if(val != null){
					val = "<img src='"+val+"' width='50px' height='50px'" 
						+ " onclick='showImg(\""+val+"\",\"广告图片\")' class='img-thumbnail' />";
				}
				return val;
			}
		}, {
			name : 'advLink',
			index : 'advLink',
			label : '新闻链接',
			hidden: true
		}, {
			name : 'createTime',
			index : 'createTime',
			label : '添加时间'
		}, {
			name : 'advKeyword',
			index : 'advKeyword',
			label : '关键字'
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			width : 100,
			formatter:function(cellvalue, options, rowdata) {
				var opt = "";
				opt +="<button type='button' class='btn btn-link' onclick='editWebNews(\"update\",\""+options.rowId+"\")'>编辑</button>";
				opt +="<button type='button' class='btn btn-link' onclick='deleteWebNews(\""+rowdata.advId+"\")'>删除</button>";
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
	//新增网站新闻事件
	$('#addWebNewsBtn').click(function(){
		addWebNews();
	});
	$('#openUeditorBtn').click(function(){
		
	});
	//富文本编辑框取消按钮
	$('#cancelUeditorBtn').click(function(){
		closeUeditor();
	});
});
function showImg(imgPath){
	$("#imgModalBody").html("<img alt='' src='"+imgPath+"' class='img-responsive' />");
	$("#imgModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
//查询操作
function query(){
	var webNewsNameSea = $("#webNewsNameSea").val();
	var webNewsKeyWordSea = $("#webNewsKeyWordSea").val();
	$(grid_selector).jqGrid('setGridParam', {
		postData : {
			webNewsNameSea : webNewsNameSea,
			webNewsKeyWordSea : webNewsKeyWordSea
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
//新增网站热点新闻
function addWebNews(){
	editWebNews('add');
}
//网站新闻编辑 type = add/update
function editWebNews(type,rowId) {
	
	$("#modifyModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
	
	if(type == "add"){
		$("#addModalLabel").html("新增网站新闻");
		//清除界面上数据
		$("#advId").val("");
		$("#title").val("");
		$("#advLink").val("");
//		$("#advContent").val("");
		$("#advKeyword").val("");
		$("#advImgFileText").val("");
		
		editor.setContent("");
		
	}else if(type == "update" && rowId){
		
		var row = $(grid_selector).getRowData(rowId);
		
		$("#addModalLabel").html("编辑网站新闻");
		
		$("#advId").val(row.advId);
		$("#title").val(row.title);
		
		$("#advImgFileText").val(row.advImg);
		
		$("#advLink").val(row.advLink);
		$("#advKeyword").val(row.advKeyword);
	}

}

//保存网站新闻 action:update/add
function saveWebNews(){
	//1是新增操作 2是更新操作
	var action = 1;
	
	var advId = $("#advId").val();
	var title = $("#title").val();
	var advImgFile = $("#advImgFile").val();
	var advLink = $("#advLink").val();
	var advContent = editor.getContent();
	var advKeyword = $("#advKeyword").val();
	if(advId == null || advId == ""){
		action = 1;
	}else {
		action = 2;
	}
	
	if(title == null || title == ''){
		$.messager.alert("温馨提示",'请输入新闻名称！');
		
		return ;
	}else if(action == 1 && (advImgFile == null || advImgFile == '')){
		$.messager.alert("温馨提示",'请选择新闻图片！');
		
		return ;
	}
//	else if(advLink == null || advLink == ''){
//		$.messager.alert("温馨提示",'请输入新闻链接！');
//		
//		return ;
//	}
//	else if(advContent == null || advContent == ''){
//		$.messager.alert("温馨提示",'请输入新闻内容！');
//		
//		return ;
//	}
	
	if(advImgFile != null && advImgFile != ''){
		
		//上传新闻图片
		$.ajaxFileUpload({ 
			url: path + "/admin/webNewsManager/action/images/upload",
			type:"POST",
			cache: false,
			secureuri:true,
			dataType:"json",
			fileElementId:"advImgFile",//文件选择框的id属性  
			data:{},
			success: function(result){
				var json = eval("("+result+")");
				
				var ret = json.data;
				if(ret.code == "SUCCESS"){
					
					var attchId = ret.data.big_image.attch_id;
					saveWebNewsAction(action,advId,title,advLink,advContent,advKeyword,attchId);
				}else{
					
					$.messager.alert("温馨提示",ret.message);
				}
			},
			error: function (data, status, e){
				$.messager.alert("温馨提示","错误!");
			},
			complete:function(){
				layer.close(loading);
				$("#isBatchImport").attr("disabled", false);
			}
		});
	}
	//不需要上传图片
	else{
		saveWebNewsAction(action,advId,title,advLink,advContent,advKeyword,'');
	}
	
}
//保存热点新闻操作
function saveWebNewsAction(action,advId,title,advLink,advContent,advKeyword,attchId){
	$.ajax({
		url:path+"/admin/webNewsManager/action/saveWebNews",
		cache: false,
		type:"post",
		data:{
			"action" : action,
			"advId":advId,
			"title":title,
			"advLink":advLink,
			"advContent":advContent,
			"advKeyword" : advKeyword,
			"attchId":attchId
		},
		success:function(result){
			
			if(result.code == '0'){
				//操作成功
				$.messager.alert("温馨提示",result.message);
				//清除富文本内容
				editor.setContent('');
				$("#modifyModal").modal('hide');
				query();
			}else{
				$.messager.alert("温馨提示",'获取信息错误!');
			}
		}
	});
}
//删除新闻操作
function deleteWebNews(advId){
	$.messager.confirm("温馨提示", "确认是否删除选中网站新闻记录？", function() {
	
		$.ajax({
			url:path+"/admin/webNewsManager/action/deleteWebNewsByAdvId",
			cache: false,
			type:"post",
			data:{
				"advId":advId
			},
			success:function(result){
				
				if(result.code == '0'){
					//操作成功
					$.messager.alert("温馨提示",'网站新闻删除成功！');
					query();
				}else{
					$.messager.alert("温馨提示",'获取信息错误!');
				}
			}
		});
	});
}
//回显选择的图片名
function showFileName(){
	$("#advImgFileText").val($("#advImgFile").val());
}
/**
 * 重置查询条件
 */
function resetAppQry(){
	$("#webNewsNameSea").val('');
	$("#webNewsContentSea").val('');
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
