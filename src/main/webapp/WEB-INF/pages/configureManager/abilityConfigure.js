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
		url : path + "/admin/configure/ability/actions/search",
		datatype : "json",
		mtype : 'get',
		height : window.screen.availHeight - 340 - $("#selectRow").height(),   
		colModel : [ {
			name : 'abilityId',
			index : 'abilityId',
			label : '技能Id',
			hidden:true
		}, {
			name : 'abilityName',
			index : 'abilityName',
			label : '技能名称'
		}, {
			name : 'description',
			index : 'description',
			label : '技能说明'
		}, {
			name : 'createTime',
			index : 'createTime',
			label : '创建时间'
		},{
			name : 'opt',
			index : 'opt',
			label : '操作',
			align: 'center',
			title : false,
			formatter:function(cellvalue, options, rowdata) {
				var opt = "<button type='button' class='btn btn-link' onclick='update(\""+rowdata.abilityId+"\",\""+rowdata.abilityName+"\",\""+rowdata.description+"\")' >修改</button>" +
				"<button type='button' class='btn btn-link' onclick='del(\""+rowdata.abilityId+"\")'>删除</button>";
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
		var abilityName = $("#qryAbilityName").val();
		$(grid_selector).jqGrid('setGridParam', {
			postData : {
				abilityName : abilityName
			},
			page : 1
		}).trigger("reloadGrid");
	})
	
	// 新增技能
	$("#addAbility").click(function() {
		//重置表单
		$('#showForm')[0].reset();
		actionType = "add";
		$("#addModalLabel").text("新增技能");
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
	$("#qryAbilityName").val("");
}

function refreshData() {
	$(grid_selector).jqGrid('setGridParam',{
		postData:{/*userName:null,realName:null,state:null*/},page : 1
	}).trigger("reloadGrid");
}

/**
 * 详情页面
 * @param userId
 */
function changeSend() {
	var abilityName = $("#abilityName").val();
	if(abilityName == null || abilityName.trim() == ""){
		$.messager.alert("温馨提示",'请填写技能名称!');
		return;
	}
	var description = $("#description").val();
	if(description == null || description.trim() == ""){
		$.messager.alert("温馨提示",'请填写技能说明!');
		return;
	}
	if(actionType == "add"){
		$.ajax({
			url:path+"/admin/configure/ability/actions/add",
			cache: false,
			type:"post",
			data:{
				"abilityName":abilityName,
				"description":description
			},
			success:function(rs){
				if(rs.code == "SUCCESS"){
					$('#showModal').modal('hide');
					refreshData();
				}else{
					$.messager.alert("温馨提示",'技能新增失败!'+rs.message);
				}
			}
		});
	}else if(actionType == "update"){
		$.ajax({
			url:path+"/admin/configure/ability/actions/modify",
			cache: false,
			type:"post",
			data:{
				"abilityId":selectAbilityId,
				"abilityName":abilityName,
				"description":description
			},
			success:function(rs){
				if(rs.code == "SUCCESS"){
					$('#showModal').modal('hide');
					refreshData();
				}else{
					$.messager.alert("温馨提示",'技能修改失败!'+rs.message);
				}
			}
		});
	}
}

//修改技能
function update(abilityId,abilityName,description){
	$("#addModalLabel").text("修改技能");
	actionType = "update";
	selectAbilityId = abilityId
	$("#abilityName").val(abilityName);
	$("#description").val(description);
	$("#showModal").modal({
		keyboard : false,
		show : true,
		backdrop : "static"
	});
}

//删除技能
function del(abilityId){
	$.messager.confirm("温馨提示", "确认删除技能", function() {
		$.ajax({
			url:path+"/admin/configure/ability/"+abilityId,
			cache: false,
			type:"delete",
			success:function(rs){
				if(rs.code == "SUCCESS"){
					refreshData();
				}else{
					$.messager.alert("温馨提示",'技能删除失败!'+rs.message);
				}
			}
		});
	});
}
