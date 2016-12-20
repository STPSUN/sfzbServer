var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var actionType = "add";//新增add,修改update
var selectAdvId = null;
var usedAdvSort = {};
var oldAdvSort = null;
var advImg = null;
var createTime = null;
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.99 -5);
//	});
	//查询已经使用的排序
	getAdvSordUsed();
	
	//查询省份
	getPlayAreaSelectOption();
	
	$(grid_selector).jqGrid({
		url : path + "/admin/contentManager/adbanners/actions/search",
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
			label : '广告名称'
		},{
			name : 'advContent',
			index : 'advContent',
			label : '广告内容'
		}, {
			name : 'advImg',
			index : 'advImg',
			label : '广告图片',
			formatter:function(cellvalue, options, rowdata) {
				var imgPath ="http://27.151.123.121:11024/bfzb"+"/resource/img/"+cellvalue; //path
				var opt = "<img alt='广告图片' src='"+imgPath+"'" +
						" onclick='showImg(\""+imgPath+"\",\"广告图片\")' class='img-thumbnail' />";
    			return opt;
		    }
		}, {
			name : 'advClientType',
			index : 'advClientType',
			label : '广告类型',
			formatter:function(cellvalue, options, rowdata) {
				if("PC_CLIENT" == cellvalue){
					return "PC端";
				}else if("IOS_CLIENT" == cellvalue){
					return "IOS端";
				}else if("ANDROID_CLIENT" == cellvalue){
					return "安卓端";
				}
		    }
		}, {
			name : 'advLink',
			index : 'advLink',
			label : '广告链接'
		}, {
			name : 'advLocation',
			index : 'advLocation',
			label : '广告位置',
			formatter:function(cellvalue, options, rowdata) {
				if("0" == cellvalue){
					return "PC首页banner";
				}else if("1" == cellvalue){
					return "APP首页banner";
				}else if("2" == cellvalue){
					return "首页底部";
				}else if("3" == cellvalue){
					return "列表页底部";
				}else if("4" == cellvalue){
					return "详情页底部";
				}else if("5" == cellvalue){
					return "其他底部";
				}else{
					return "";
				}
		    }
		}, {
			name : 'createTime',
			index : 'createTime',
			label : '创建时间'
		}, {
			name : 'startTime',
			index : 'startTime',
			label : '开始时间'
		}, {
			name : 'endTime',
			index : 'endTime',
			label : '结束时间'
		}, {
			name : 'advSort',
			index : 'advSort',
			label : '广告排序'
		}, {
			name : 'playArea',
			index : 'playArea',
			label : '投放区域'
		}, {
			name : 'status',
			index : 'status',
			label : '广告状态',
			formatter:function(cellvalue, options, rowdata) {
				if("0" == cellvalue){
					return "启用";
				}else if("1" == cellvalue){
					return "停用";
				}
		    }
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			title : false,
			width :190,
			formatter:function(cellvalue, options, rowdata) {
				var opt = "<button type='button' class='btn btn-link' onclick='change(\""+rowdata.advId+"\")' >修改</button>" +
					"<button type='button' class='btn btn-link' onclick='del(\""+rowdata.advId+"\")'>删除</button>";
				if("0" == rowdata.status){
					opt += "<button type='button' class='btn btn-link' onclick='stop(\""+rowdata.advId+"\")'>停播</button>";
				}else if("1" == rowdata.status){
					opt += "<button type='button' class='btn btn-link' onclick='start(\""+rowdata.advId+"\")'>播放</button>";
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
		var advClientType = $("#qryAdvClientType").val();
		var advLocation = $("#qryAdvLocation").val();
		var title = $("#qryTitle").val();
		var status = $("#qryStatus").val();
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
				advClientType : advClientType,
				advLocation : advLocation,
				title : title,
				status : status,
				qryStartTime : qryStartTime,
				qryEndTime : qryEndTime
			},
			page : 1
		}).trigger("reloadGrid");
	})
	
	$("#addAdv").click(function() {
		//重置表单
		$('#showForm')[0].reset();
		actionType = "add";
		oldAdvSort = "";
		$("#addModalLabel").text("添加广告");
		$("#advLocation").removeAttr("disabled");
		$("#advClientType").removeAttr("disabled");
		
		var html = "";
		for(var i=1; i <= 10; i++){
			html += "<option value='"+i+"'>"+i+"</option>";
		}
		$("#advSort").html(html);
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

function refreshData() {
	var advClientType = $("#qryAdvClientType").val();
	var advLocation = $("#qryAdvLocation").val();
	var title = $("#qryTitle").val();
	var status = $("#qryStatus").val();
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
	$(grid_selector).jqGrid('setGridParam',{
		postData:{
			advClientType : advClientType,
			advLocation : advLocation,
			title : title,
			status : status,
			qryStartTime : qryStartTime,
			qryEndTime : qryEndTime
		},page : 1
	}).trigger("reloadGrid");
}
//修改
function change(advId) {
	$("#addModalLabel").text("修改广告");
	$("#advLocation").attr("disabled","disabled");
	$("#advClientType").attr("disabled","disabled");
	$("#advImgFile").val("");
	
	actionType = "update";
	selectAdvId = advId;
	
	var html = "";
	for(var i=1; i <= 10; i++){
		html += "<option value='"+i+"'>"+i+"</option>";
	}
	$("#advSort").html(html);
	
	$.ajax({
		url:path + "/admin/contentManager/adbanners/actions/search",
		cache: false,
		type:"post",
		data:{"advId":advId},
		success:function(rs){
			if(rs.records == 1){
				var data = rs.rows[0];
				$("#title").val(data.title);
				$("#advContent").val(data.advContent);
				$("#advImgFileText").val("有图片");
				advImg = data.advImg;
				$("#advClientType").val(data.advClientType);
				$("#advLocation").val(data.advLocation);
				$("#advLink").val(data.advLink);
				if(data.playArea == null || data.playArea == ""){
					$("#playArea").val("全国");
				}else{
					$("#playArea").val(data.playArea);
				}
				$("#advSort").val(data.advSort);
				oldAdvSort = data.advSort;
				var startTime = data.startTime.replace(" 00:00:00","").replace(new RegExp('-','gm'),'/');
				var endTime = data.endTime.replace(" 00:00:00","").replace(new RegExp('-','gm'),'/');
				$("#playTime").val(startTime+" - "+endTime);
				createTime = data.createTime;
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

function del(advId) {
	var confirmMsg="确认删除？";
	$.messager.confirm("温馨提示", confirmMsg, function() {
		$.ajax({
			url:path+"/admin/contentManager/adbanners/"+advId,
			cache: false,
			type:"delete",
			success:function(rs){
				if(rs.code == "SUCCESS"){
					refreshData();
					getAdvSordUsed();
				}else{
					$.messager.alert("温馨提示",'广告删除失败!'+rs.message);
				}
			}
		});
	})
}

function stop(advId) {
	var confirmMsg="确认停止播放？";
	$.messager.confirm("温馨提示", confirmMsg, function() {
		$.ajax({
			url:path+"/admin/contentManager/adbanners/stop/"+advId,
			cache: false,
			type:"get",
			success:function(rs){
				if(rs.code == "SUCCESS"){
					refreshData();
					getAdvSordUsed();
				}else{
					$.messager.alert("温馨提示",'停止播放失败!'+rs.message);
				}
			}
		});
	})
}

function start(advId) {
	var confirmMsg="确认开始播放？请注意修改播放时间。";
	$.messager.confirm("温馨提示", confirmMsg, function() {
		$.ajax({
			url:path+"/admin/contentManager/adbanners/start/"+advId,
			cache: false,
			type:"get",
			success:function(rs){
				if(rs.code == "SUCCESS"){
					refreshData();
					getAdvSordUsed();
				}else{
					$.messager.alert("温馨提示",'开始播放失败!'+rs.message);
				}
			}
		});
	})
}

function resetQry(){
	$("#qryAdvClientType").val('');
	$("#qryAdvLocation").val('');
	$("#qryTitle").val('');
	$("#qryStatus").val('');
	$("#dateRange").val('');
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
		url:path+"/admin/contentManager/adbanners/search/advSort",
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
		$.messager.alert("温馨提示",'请填写广告名称！');
		return;
	}
	var advContent = $("#advContent").val();
	if(advContent == null || advContent.trim() == ""){
		$.messager.alert("温馨提示",'请填写广告内容！');
		return;
	}
	var advImgFile = $("#advImgFile").val();
	if(actionType == "add"){
		if(advImgFile == null || advImgFile == ""){
			$.messager.alert("温馨提示",'请选择上传图片！');
			return;
		}
	}
	
	var advClientType = $("#advClientType").val();
	if(advClientType == null || advClientType == ""){
		$.messager.alert("温馨提示",'请选择广告类型！');
		return;
	}
	var advLocation = $("#advLocation").val();
	if(advLocation == null || advLocation == ""){
		$.messager.alert("温馨提示",'请选择广告位置！');
		return;
	}
	var advLink = $("#advLink").val();
	if(advLink == null || advLink.trim == ""){
		$.messager.alert("温馨提示",'请填写广告链接！');
		return;
	}
	var playTime = $("#playTime").val();
	if(playTime == null || playTime == ""){
		$.messager.alert("温馨提示",'请选择开始结束时间！');
		return;
	}
	var playArea = $("#playArea").val();
	if(playArea == null || playArea == ""){
		$.messager.alert("温馨提示",'请填写投放区域！');
		return;
	}
	var advSort = $("#advSort").val();
	
	var startTime="";
	var endTime="";
	if(playTime!=null && playTime!=""){
		var drArr = playTime.split(" - ");
		if(drArr.length>=2){
			startTime = drArr[0].replace(new RegExp('/','gm'),'-') + " 00:00:00";
			endTime = drArr[1].replace(new RegExp('/','gm'),'-') + " 00:00:00";
		}
	}
	
	for (i in usedAdvSort){
		if(advSort == usedAdvSort[i] && oldAdvSort != advSort){
			$.messager.alert("温馨提示",'序号'+advSort+"重复使用，请选择其他序号。");
			return;
		}
	}
	
	if(advImgFile == null || advImgFile == ""){
		if(actionType == "add"){
			$.ajax({
				url:path+"/admin/contentManager/adbanners/action/add",
				cache: false,
				type:"post",
				data:{
					"title":title,
					"advContent":advContent,
					"advImg":advImg,
					"advClientType":advClientType,
					"advLocation":advLocation,
					"advLink":advLink,
					"startTime":startTime,
					"endTime":endTime,
					"playArea":playArea,
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
				url:path+"/admin/contentManager/adbanners/action/modify",
				cache: false,
				type:"post",
				data:{
					"advId":selectAdvId,
					"title":title,
					"advContent":advContent,
					"advImg":advImg,
					"advClientType":advClientType,
					"advLocation":advLocation,
					"advLink":advLink,
					"startTime":startTime,
					"endTime":endTime,
					"playArea":playArea,
					"createTime":createTime,
					"advSort":advSort
				},
				success:function(rs){
					if(rs.code == "SUCCESS"){
						$('#showModal').modal('hide');
						getAdvSordUsed();
						refreshData();
					}else{
						$.messager.alert("温馨提示",'类别修改失败!'+rs.message);
					}
				}
			});
		}
	}else{
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
				console.log(json);
				var ret = json.data;
				if(ret.code == "SUCCESS"){
					var advImg = ret.data.big_image.attch_id;
					if(actionType == "add"){
					$.ajax({
						url:path+"/admin/contentManager/adbanners/action/add",
						cache: false,
						type:"post",
						data:{
							"title":title,
							"advContent":advContent,
							"advImg":advImg,
							"advClientType":advClientType,
							"advLocation":advLocation,
							"advLink":advLink,
							"startTime":startTime,
							"endTime":endTime,
							"playArea":playArea,
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
						url:path+"/admin/contentManager/adbanners/action/modify",
						cache: false,
						type:"post",
						data:{
							"advId":selectAdvId,
							"title":title,
							"advContent":advContent,
							"advImg":advImg,
							"advClientType":advClientType,
							"advLocation":advLocation,
							"advLink":advLink,
							"startTime":startTime,
							"endTime":endTime,
							"playArea":playArea,
							"advSort":advSort
						},
						success:function(rs){
							if(rs.code == "SUCCESS"){
								$('#showModal').modal('hide');
								getAdvSordUsed();
								refreshData();
							}else{
								$.messager.alert("温馨提示",'类别修改失败!'+rs.message);
							}
						}
					});
				}
				}else{
					$.messager.alert("温馨提示",ret.message);
				}
			},
			error: function (data, status, e){
				$.messager.alert("温馨提示","错误!");
			}
		});
	}
}

//回显选择的图片名
function showFileName(){
	$("#advImgFileText").val($("#advImgFile").val());
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
			$("#playArea").html(html);
		}
	});
}
