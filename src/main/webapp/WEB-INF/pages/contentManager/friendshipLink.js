var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var actionType = "add";//新增add,修改update
var selectAdvId = null;
var usedAdvSort = {};
var oldAdvSort = null;
var proviceHtml = null;
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.99 -5);
//	});
	//查询已经使用的排序
	getAdvSordUsed();
	
	//查询省份
	getPlayAreaSelectOption();
	
	$(grid_selector).jqGrid({
		url : path + "/admin/contentManager/friendshiplink/actions/search",
		datatype : "json",
		mtype : 'POST',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'advId',
			index : 'advId',
			label : 'UUID',
			hidden:true
		}, {
			name : 'createTime',
			index : 'createTime',
			label : '创建时间'
		}, {
			name : 'advLocation',
			index : 'advLocation',
			label : '位置分类',
			formatter:function(cellvalue, options, rowdata) {
				if("6" == cellvalue){
					return "首页友情链接";
				}else if("7" == cellvalue){
					return "列表页友情链接";
				}else if("8" == cellvalue){
					return "新闻页友情链接";
				}else if("9" == cellvalue){
					return "分站友情链接";
				}else{
					return "";
				}
		    }
		}, {
			name : 'advLocationDetail',
			index : 'advLocationDetail',
			label : '具体位置'
		},{
			name : 'title',
			index : 'title',
			label : '网站名称'
		}, {
			name : 'advLink',
			index : 'advLink',
			label : '详情链接'
		}, {
			name : 'advUser',
			index : 'advUser',
			label : '联系人'
		}, {
			name : 'advUserMobile',
			index : 'advUserMobile',
			label : '联系方式'
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			width :190,
			formatter:function(cellvalue, options, rowdata) {
				var opt = "<button type='button' class='btn btn-link' onclick='change(\""+rowdata.advId+"\")' >修改</button>" +
					"<button type='button' class='btn btn-link' onclick='del(\""+rowdata.advId+"\")'>删除</button>";
				
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
		var advLocation = $("#qryAdvLocation").val();
		var title = $("#qryTitle").val();
		$(grid_selector).jqGrid('setGridParam', {
			postData : {
				advLocation:advLocation,
				title:title
			},
			page : 1
		}).trigger("reloadGrid");
	})
	
	$("#addAdv").click(function() {
		//重置表单
		$('#showForm')[0].reset();
		actionType = "add";
		oldAdvSort = "";
		$("#addModalLabel").text("添加友情链接");
		$("#showModal").modal({
			keyboard : false,
			show : true,
			backdrop : "static"
		});
	})
	
	$("#advLocation").change(function() {
		var value = $(this).children('option:selected').val();
		$("#advLocationDetail").val('');
		var html = "";
		if("6" == value){
			html = "<option value='首页底部'>首页底部</option>";
			$("#advLocationDetail").html(html);
			return;
		}
		if("7" == value){
			html = "<option value='列表页底部'>列表页底部</option>";
			$("#advLocationDetail").html(html);
			return;
		}
		if("8" == value){
			html = "<option value='新闻页底部'>新闻页底部</option>";
			$("#advLocationDetail").html(html);
			return;
		}
		if("9" == value){
			html = proviceHtml;
			$("#advLocationDetail").html(html);
			return;
		}
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
	var advLocation = $("#qryAdvLocation").val();
	var title = $("#qryTitle").val();
	$(grid_selector).jqGrid('setGridParam',{
		postData:{
			advLocation:advLocation,
			title:title
		},page : 1
	}).trigger("reloadGrid");
}
//修改
function change(advId) {
	$('#showForm')[0].reset();
	$("#addModalLabel").text("修改友情链接");
	
	actionType = "update";
	selectAdvId = advId;
	
	var html = "";
	for(var i=1; i <= 10; i++){
		html += "<option value='"+i+"'>"+i+"</option>";
	}
	$("#advSort").html(html);
	
	$.ajax({
		url:path + "/admin/contentManager/friendshiplink/actions/search",
		cache: false,
		type:"post",
		data:{"advId":advId},
		success:function(rs){
			if(rs.records == 1){
				var data = rs.rows[0];
				$("#title").val(data.title);
				$("#advLocation").val(data.advLocation);
				$("#advLink").val(data.advLink);
				$("#advUser").val(data.advUser);
				$("#advUserMobile").val(data.advUserMobile);
				$("#advSort").val(data.advSort);
				oldAdvSort = data.advSort;
				var html = "";
				if("6" == data.advLocation){
					html = "<option value='首页底部'>首页底部</option>";
				}else if("7" == data.advLocation){
					html = "<option value='列表页底部'>列表页底部</option>";
				}else if("8" == data.advLocation){
					html = "<option value='新闻页底部'>新闻页底部</option>";
				}else if("9" == data.advLocation){
					html = proviceHtml;
				}
				$("#advLocationDetail").html(html);
				$("#advLocationDetail").val(data.advLocationDetail);
			}else{
				$.messager.alert("温馨提示",'查询广告记录失败!');
			}
		}
	});
	
	$("#showModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}
//删除
function del(advId) {
	var confirmMsg="确认删除？";
	$.messager.confirm("温馨提示", confirmMsg, function() {
		$.ajax({
			url:path+"/admin/contentManager/friendshiplink/"+advId,
			cache: false,
			type:"delete",
			success:function(rs){
				if(rs.code == "SUCCESS"){
					refreshData();
					getAdvSordUsed();
				}else{
					$.messager.alert("温馨提示",'友情链接删除失败!'+rs.message);
				}
			}
		});
	})
}

function resetQry(){
	$("#qryAdvLocation").val('');
	$("#qryTitle").val('');
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

function getAdvSordUsed(){
	$.ajax({
		url:path+"/admin/contentManager/friendshiplink/search/advSort",
		cache: false,
		type:"get",
		success:function(rs){
			if(rs.code == "SUCCESS"){
				var data = rs.data;
				usedAdvSort = {};
				for(var i=0; i < data.length ; i++){
					var advSort = data[i].advSort;
					usedAdvSort[i] = advSort;
				}
			}else{
				$.messager.alert("温馨提示",'查询剩余序列失败!'+rs.message);
			}
		}
	});
}

/**
 * 新增修改操作
 * @param userId
 */
function changeSend() {
	var title = $("#title").val();
	if(title == null || title.trim() == ""){
		$.messager.alert("温馨提示",'请填写网站名称！');
		return;
	}
	var advLocation = $("#advLocation").val();
	if(advLocation == null || advLocation == ""){
		$.messager.alert("温馨提示",'请选择位置分类！');
		return;
	}
	var advLocationDetail = $("#advLocationDetail").val();
	var advLink = $("#advLink").val();
	if(advLink == null || advLink.trim() == ""){
		$.messager.alert("温馨提示",'请填写详情链接！');
		return;
	}
	var advUser = $("#advUser").val();
	if(advUser == null || advUser.trim() == ""){
		$.messager.alert("温馨提示",'请填写联系人！');
		return;
	}
	var advUserMobile = $("#advUserMobile").val();
	if(advUserMobile == null || advUserMobile.trim() == ""){
		$.messager.alert("温馨提示",'请填写联系方式！');
		return;
	}
	var advSort = $("#advSort").val();
	if(advSort == null || advSort.trim() == ""){
		$.messager.alert("温馨提示",'请填写序号！');
		return;
	}else{
		for (i in usedAdvSort){
			if(advSort == usedAdvSort[i] && oldAdvSort != advSort){
				$.messager.alert("温馨提示",'序号'+advSort+"重复使用，请填写其他数字。");
				return;
			}
		}
	}
	
	if(actionType == "add"){
		$.ajax({
			url:path+"/admin/contentManager/friendshipLink/action/add",
			cache: false,
			type:"post",
			data:{
				"title":title,
				"advLocation":advLocation,
				"advLocationDetail":advLocationDetail,
				"advLink":advLink,
				"advUser":advUser,
				"advUserMobile":advUserMobile,
				"advSort":advSort
			},
			success:function(rs){
				if(rs.code == "SUCCESS"){
					$('#showModal').modal('hide');
					getAdvSordUsed();
					refreshData();
				}else{
					$.messager.alert("温馨提示",'广告新增失败!'+rs.message);
				}
			}
		});
	}else if(actionType == "update"){
		$.ajax({
		url:path+"/admin/contentManager/friendshipLink/action/modify",
		cache: false,
		type:"post",
		data:{
			"advId":selectAdvId,
			"title":title,
			"advLocation":advLocation,
			"advLocationDetail":advLocationDetail,
			"advLink":advLink,
			"advUser":advUser,
			"advUserMobile":advUserMobile,
			"advSort":advSort
		},
		success:function(rs){
			if(rs.code == "SUCCESS"){
				$('#showModal').modal('hide');
				getAdvSordUsed();
				refreshData();
			}else{
				$.messager.alert("温馨提示",'广告新增失败!'+rs.message);
			}
		}
	});
	}
}

function getPlayAreaSelectOption(){
	$.ajax({
		url:path+"/api/users/regions?parentRegionId=1",
		cache: false,
		type:"get",
		success:function(rs){
			var rsJson = eval("(" + rs + ")");
			var html = "<option value='全国'>全国</option>";
			if(rsJson.code == "SUCCESS"){
				var data = rsJson.data;
				var provinces = data.provinces;
				for(var i=0;i < provinces.length; i++){
					html += "<option value='"+provinces[i].region_name+"'>"+provinces[i].region_name+"</option>";
				}
			}else{
				$.messager.alert("温馨提示",'查询省份失败!'+rs.message);
			}
			proviceHtml = html;
		}
	});
}
