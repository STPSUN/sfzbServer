<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String basePath = request.getContextPath();
if(basePath.endsWith("/")){
	basePath = basePath.substring(0, basePath.length()-1);
}
String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+basePath;
%>
<!DOCTYPE HTML>
<html>
<head>
<title>网站新闻管理</title>
<link rel="stylesheet" type="text/css" media="all" href="<%=path%>/pages/assets/css/daterangepicker-bs3.css"/>
 <link type="text/css" href="<%=path%>/pages/assets/ueditor/themes/default/css/ueditor.css" rel="stylesheet"/>
<style>
.btn{
	padding-top: 0;padding-bottom: 0;
}
.norecords { 
	border-width: 2px !important; 
	display:none; 
	font-weight: bold; 
	left: 45%; 
	margin: 5px; 
	padding: 6px; 
	position: absolute; 
	text-align: center; 
	top: 45%; 
	width: auto; 
	z-index: 102; 
}
/* 文件选择样式  */
.file {
   position: relative;
   display: inline-block;
   background: #D0EEFF;
   border: 1px solid #99D3F5;
   border-radius: 4px;
   padding: 4px 12px;
   overflow: hidden;
   color: #1E88C7;
   text-decoration: none;
   text-indent: 0;
   line-height: 20px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}
</style>
</head>
<body style="background: #ffffff;">
	<div class="row">
		<div class="col-xs-12">
			<div class="widget-box">
				<div class="widget-body">
					<div class="widget-body-inner" style="display: block;">
						<div class="widget-main">
							<div>
								<div class="row">
									<div class="col-xs-12" style="height:60px">
										<div class="panel panel-default">
											<div class="panel-heading">
												<form class="form-horizontal" role="form" onsubmit="return false;">
													<div class="form-group" style="margin-bottom: 0;">
														<label class="col-sm-1 control-label" style="width: 8%;">新闻名称:</label>
														<input  class="form-control"  style="width: 200px; float: left;margin-left:12px" placeholder="新闻名称搜索" type="text" id="webNewsNameSea">
															
														<label class="col-sm-1 control-label" style="width: 8%;">关键字:</label>
														<input class="form-control" style="width: 200px; float: left;margin-left:12px" placeholder="新闻内容搜索" type="text" id="webNewsKeyWordSea">
														
														<div class="col-sm-1" style="float:right;margin-right: 3%;">
															<button class="btn btn-info myBtn" onclick="resetAppQry()" id="reset"><i class="fa fa-circle-o-notch">&nbsp;</i>重置</button>
														</div>
														<div class="col-sm-1" style="float:right;">
															<button id="queryBtn" class="btn btn-info btn-label-left myBtn">
																<i class="fa fa-search">&nbsp;</i> 查询
															</button>
														</div>
														
													</div>
												</form>
											</div>
										</div>
									</div>
									<div class="col-xs-12">
										<div style="margin-bottom: 10px;">
											<button  class="btn btn-success" id="addWebNewsBtn">
												<i class="fa  fa-plus"></i> 新增
											</button>
											<!-- <button  class="btn btn-danger myBtnClick" id="delete">
												<i class="fa fa-trash-o fa-lg"></i> 删除
											</button>
											<button  class="btn btn-primary myBtnClick" id="active">
												<i class="fa fa-check"></i> 启用
											</button>
											<button  class="btn btn-warning myBtnClick" id="locked">
												<i class="fa fa-ban"></i> 禁用
											</button> -->
										</div>
										<table id="grid-table"></table>
										<div id="grid-pager"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>	
			</div>
		</div>	
	</div>
	<!-- 修改网站新闻 -->
	<div id="modifyModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				 <div class="modal-header">
			        <!-- <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
			        <h4 class="modal-title" id="addModalLabel">新增网站新闻</h4>
			      </div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="addForm" onsubmit="return false;">
						<input id="advId" name="title" type="hidden" class="form-control" placeholder="请输入新闻ID">
						<div class="form-group" >
							<label class="col-sm-2 control-label">新闻名称</label>
							<div class="col-sm-8">
								<input id="title" name="title" type="text" class="form-control" placeholder="请输入新闻名称">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">新闻图片</label>
							<div class="col-sm-8">
							
								<input id="advImgFileText" name="advImgFileText" type="text" readonly="readonly" class="form-control" style="width:250px">
								<a href="javascript:;" class="file" style="margin-left:255px;height:33px; position:absolute; bottom:0;cursor:pointer">选择文件
									<input id="advImgFile" name="advImgFile" type="file" accept="image/*" onchange="showFileName()">
								</a>
							</div>
						</div>
						<div class="form-group" style="display:none;">
							<label class="col-sm-2 control-label">新闻链接</label>
							<div class="col-sm-8" >
								<input id="advLink" name="advLink" type="text" class="form-control" placeholder="请输入新闻链接">
							</div>
						</div>
						
						<div class="form-group" >
							<label class="col-sm-2 control-label">关键字</label>
							<div class="col-sm-8" >
								<input id="advKeyword" name="advKeyword" type="text" class="form-control" placeholder="请输入新闻关键字">
							</div>
						</div>
						<div class="form-group" >
							<label class="col-sm-2 control-label">新闻内容</label>
							<div class="col-sm-8" >
								<button  class="btn btn-success" onclick="openUeditor();">
									<i class="fa  fa-plus"></i> 编辑
								</button>
								<!-- <textarea id="advContent" name="advContent" rows="4" class="form-control limited" maxlength="1000" placeholder="请输入新闻内容"></textarea> -->
							</div>
						</div>
						
					</form>
				</div>
				<div class="modal-footer">
					<button data-bb-handler="confirm" type="button" id="asaveSaveBtn" class="btn btn-success" onclick="saveWebNews()">
						<span><i class="icon-ok"></i></span> &nbsp;保&nbsp;&nbsp;存
					</button>
					<button data-bb-handler="cancel" type="button" id="" modal="" class="cancel btn btn-danger cancel" onclick="closeWebNewsModal()">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 图片展示模态  -->
	<div id="imgModal" class="bootbox modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width:50%">
			<div class="modal-content">
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="imgModalLabel">新闻图片</h4>
			    </div>
				<div class="modal-body" id="imgModalBody">
				</div>
			</div>
		</div>
	</div>
	<!-- =================Ueditor富文本=========================================== -->
	<div id="ueditorModelDialog" class="bootbox modal fade" role="dialog" style="width:100%;height:100%;">
		<div class="modal-content" style="width:100%;">
			<div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="ueditorModelLabel">编辑新闻内容</h4>
		     </div>
			<div class="modal-body" >
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<script id="editor" type="text/plain" style="width:100%;height:100%;"></script>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="cancleProjectPlan" class="btn btn-success" onclick="confirmUeditor();">确认</button>
			</div>
		</div>
	</div>
<script src="<%=path%>/pages/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="<%=path%>/pages/assets/js/ajaxfileupload.js"></script>
<script src="<%=path%>/pages/assets/js/jqGrid/i18n/grid.locale-en.js" type="text/javascript" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/moment.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/daterangepicker.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/date-time/date.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/assets/js/layer/layer.min.js" charset="UTF-8"></script>
<script src="<%=path%>/pages/contentManager/webNewsManager.js"></script>
	<!-- 引入ueditor文件 -->
<script type="text/javascript" charset="utf-8" src="<%=path%>/pages/assets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/pages/assets/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/pages/assets/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	var path='<%=path%>';
	//ueditor
	var editor = UE.getEditor('editor', {
    	toolbars: [
	           [
					'source', '|', 'undo', 'redo', '|',  
					'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',  
					'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',  
					'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',  
					'directionalityltr', 'directionalityrtl', 'indent', '|',  
					'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',  
					'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|', 'simpleupload',
					'insertimage', 'emotion', 'scrawl'/* , 'insertvideo', 'music', 'attachment' */, 'insertframe','insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',  
					'horizontal', 'date', 'time', 'spechars', 'wordimage', '|',  
					'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',  
					/* 'print', */ 'preview', 'searchreplace'/* , 'help', 'drafts' */,'fullscreen'
	           ]
	       ],
       zIndex : 99999,
       initialFrameHeight:600
    });
	//editor.render('myEditor');
	$(function(){
		$(".cancel").click(function(){
			$("#"+$(this).attr("modal")).modal("hide");
		});
		
		/*JQuery 限制文本框只能输入数字和小数点*/  
        $(".numDecText").keyup(function(){    
                $(this).val($(this).val().replace(/[^0-9.]/g,''));
            }).bind("paste",function(){  //CTR+V事件处理
                $(this).val($(this).val().replace(/[^0-9.]/g,''));
            }).css("ime-mode", "disabled"); //CSS设置输入法不可用
	});
	//打开富文本编辑窗口
	function openUeditor(){
		//广告ID存在即为编辑，不存在则新增
		var advId = $("#advId").val();
		var advContent = editor.getContent();
		
		//新增操作，无需从数据库加载数据
		if((advId == null || advId == "")){
			
			showUeditorModel(advContent);
		}
		//更新，但是富文本有内容情况
		else if(advContent != null && advContent != ""){
			
			showUeditorModel(advContent);
		}else {
				
			$.ajax({
				url:path+"/admin/webNewsManager/action/getWebNewsContentByAdvId",
				cache: false,
				type:"get",
				data:{
					"advId":advId
				},
				success:function(result){
					
					if(result.code == '0'){
						showUeditorModel(result.data);
					}else{
						$.messager.alert("温馨提示",'获取信息错误!');
					}
				}
			});
		}
	}
	//显示富文本编辑对话框  新闻的更新方式是编辑还是新增type=add/update
	function showUeditorModel(advContent){
		//操作成功
		$("#ueditorModelDialog").modal({
			keyboard : false,
			show : true,
			backdrop : "static"
		});
		
		editor.setContent(advContent);
	}
	//清空富文本内容
	function resetUeditorContent(){
		editor.setContent('');
	}
	//确认富文本输入内容
	function confirmUeditor(){
		$("#ueditorModelDialog").modal("hide");
	}
	//关闭新闻编辑窗口
	function closeWebNewsModal(){
		$("#modifyModal").modal("hide");
		editor.setContent('');
	}
</script>

</body>
</html>