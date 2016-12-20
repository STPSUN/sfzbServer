var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var grid_balance_selector = "#grid-balance-table";
var pager_balance_selector = "#grid-balance-pager";
var actionType = "add";//新增add,修改update
var selectCategoryId = null;
jQuery(function($) {
//	$(window).resize(function() {
//		$("#grid-table").setGridWidth($(window).width() * 0.98);
//	});
	$(grid_selector).jqGrid({
		url : path + "/admin/configure/category/actions/search",
		datatype : "json",
		mtype : 'get',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'categoryId',
			index : 'categoryId',
			label : '类别Id',
			hidden:true
		}, {
			name : 'parentId',
			index : 'parentId',
			label : '服务类别id',
			hidden:true
		}, {
			name : 'parentName',
			index : 'parentName',
			label : '服务类别'
		}, {
			name : 'categoryName',
			index : 'categoryName',
			label : '类别名称'
		}, {
			name : 'abilityNames',
			index : 'abilityNames',
			label : '所需技能'
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			align: 'center',
			title : false,
			formatter:function(cellvalue, options, rowdata) { 
				var opt = "<button type='button' class='btn btn-link' onclick='update(\""+rowdata.categoryId+"\",\""+rowdata.parentName+"\",\""+rowdata.categoryName+"\")' >配置</button>" +
				"<button type='button' class='btn btn-link' onclick='del(\""+rowdata.categoryId+"\")'>删除</button>";
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
		var categoryName = $("#qryCategoryName").val();
		$(grid_selector).jqGrid('setGridParam', {
			postData : {
				categoryName : categoryName
			},
			page : 1
		}).trigger("reloadGrid");
	})
	
	// 新增技能
	$("#addCategory").click(function() {
		//重置表单
		$('#showForm')[0].reset();
		actionType = "add";
		$("#addModalLabel").text("新增类别");
		$("#parentName").val("IT服务");
		$.ajax({
			url:path+"/admin/configure/category/link/ability",
			cache: false,
			type:"get",
			success:function(rs){
				if(rs.code == "SUCCESS"){
					var data = rs.data;
					var html = "";
					for(var i=0; i < data.length; i++){
						var name = data[i].abilityName;
						if(name.length > 6){
							name = name.substr(0,5) + "...";
						}
						var isCheck = "";
						if(data[i].isLink == 1){
							isCheck = 'checked="checked"';
						}
						html += '<div class="checkbox col-sm-3">'
							+ '<label title="'+data[i].abilityName+'">'
							+ '<input type="checkbox" name="ability" id="'+data[i].abilityId+'" '+isCheck+' />'
							+ '<span style="display:block;margin-top:-3px;">'+name
							+ '</span></label></div>';
					}
					$("#abilityLists").html(html);
					$("#showModal").modal({
						keyboard : false,
						show : true,
						backdrop : "static"
					});
				}else{
					$.messager.alert("温馨提示",'查询关联技能失败!'+rs.message);
				}
			}
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
	$("#qryCategoryName").val("");
}

function refreshData() {
	$(grid_selector).jqGrid('setGridParam',{
		postData:{/*userName:null,realName:null,state:null*/},page : 1
	}).trigger("reloadGrid");
}

/**
 * 新增修改操作
 * @param userId
 */
function changeSend() {
	var categoryName = $("#categoryName").val();
	var abilityIds = {};
	var i = 0;
	$("[name='ability']").each(function(){
		if(this.checked == true){
			abilityIds[i] = $(this).attr("id");
			i++;
		}
	})
	
	if(categoryName == null || categoryName.trim() == ""){
		$.messager.alert("温馨提示",'请填写类别名称!');
		return;
	}

	if(i == 0){
		$.messager.alert("温馨提示",'请选择至少一个技能!');
		return;
	}

	if(actionType == "add"){
		$.ajax({
			url:path+"/admin/configure/category/action/add",
			cache: false,
			type:"post",
			data:{
				"categoryName":categoryName,
				"abilityIds":abilityIds
			},
			success:function(rs){
				if(rs.code == "SUCCESS"){
					$('#showModal').modal('hide');
					refreshData();
				}else{
					$.messager.alert("温馨提示",'类别新增失败!'+rs.message);
				}
			}
		});
	}else if(actionType == "update"){
		$.ajax({
			url:path+"/admin/configure/category/action/modify",
			cache: false,
			type:"post",
			data:{
				"categoryId":selectCategoryId,
				"categoryName":categoryName,
				"abilityIds":abilityIds
			},
			success:function(rs){
				if(rs.code == "SUCCESS"){
					$('#showModal').modal('hide');
					refreshData();
				}else{
					$.messager.alert("温馨提示",'类别修改失败!'+rs.message);
				}
			}
		});
	}
}

//修改技能
function update(categoryId,parentName,categoryName){
	$("#addModalLabel").text("修改类别");
	actionType = "update";
	selectCategoryId = categoryId;
	$("#parentName").val(parentName);
	$("#categoryName").val(categoryName);
	$.ajax({
		url:path+"/admin/configure/category/link/ability",
		cache: false,
		type:"get",
		data:{"categoryId":categoryId},
		success:function(rs){
			if(rs.code == "SUCCESS"){
				var data = rs.data;
				var html = "";
				for(var i=0; i < data.length; i++){
					var name = data[i].abilityName;
					if(name.length > 6){
						name = name.substr(0,5) + "...";
					}
					var isCheck = "";
					if(data[i].isLink == "1"){
						isCheck = 'checked="checked"';
					}
					html += '<div class="checkbox col-sm-3">'
						+ '<label title="'+data[i].abilityName+'">'
						+ '<input type="checkbox" name="ability" id="'+data[i].abilityId+'"'+isCheck+' />'
						+ '<span style="display:block;margin-top:-3px;">'+name
						+ '</span></label></div>';
				}
				$("#abilityLists").html(html);
				$("#showModal").modal({
					keyboard : false,
					show : true,
					backdrop : "static"
				});
			}else{
				$.messager.alert("温馨提示",'查询关联技能失败!'+rs.message);
			}
		}
	});
}

//删除类别
function del(categoryId){
	$.messager.confirm("温馨提示", "确认删除类别", function() {
		$.ajax({
			url:path+"/admin/configure/category/del/"+categoryId,
			cache: false,
			type:"delete",
			success:function(rs){
				if(rs.code == "SUCCESS"){
					refreshData();
				}else{
					$.messager.alert("温馨提示",'分类删除失败!'+rs.message);
				}
			}
		});
	});
}

