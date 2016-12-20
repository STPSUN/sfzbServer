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
<title>
<sitemesh:write property='title' />
</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=path%>/pages/assets/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=path%>/pages/assets/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=path%>/pages/assets/css/jquery-ui-1.10.3.full.min.css" />
<link rel="stylesheet" href="<%=path%>/pages/assets/css/datepicker.css" />
<link rel="stylesheet" href="<%=path%>/pages/assets/css/googleFamily.css" />
<link rel="stylesheet" href="<%=path%>/pages/assets/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=path%>/pages/assets/css/chosen.css" />
<link rel="stylesheet" href="<%=path%>/pages/assets/css/custom.css" />
<link rel="stylesheet" href="<%=path%>/pages/assets/css/ace.min.css" />
<link rel="stylesheet" href="<%=path%>/pages/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="<%=path%>/pages/assets/css/ace-skins.min.css" />
<style type="text/css">
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
.noBorder{
	border-color:#fff;
	box-shadow:inset 1 0px 0px rgba(0,0,0,0.075);
}
#gview_grid-table {
	border-left-width: 1px;
	border-right-width: 1px;
	border-left-style: solid;
	border-right-style: solid;
	border-left-color: #e1e1e1;
	border-right-color: #e1e1e1;
	border-collapse: collapse;
	border-spacing: 0;
}
.widget-box{
	border-style: none;
}
.widget-body{
	border-style: none;
}
.carousel {
  height: 100%;
}
/* Declare heights because of positioning of img element */
.carousel .item {
  height: 100%;
  background-color: #fff;
}
.carousel-inner > .item > img {
  max-width:100%;
}
	
</style>

<!--[if lte IE 8]>
  <link rel="stylesheet" href="<%=path%>/pages/assets/css/ace-ie.min.css" />
<![endif]-->

<sitemesh:write property='head'/>
<!--[if !IE]> -->
	<script src="<%=path%>/pages/assets/js/jquery-2.0.3.min.js"></script>
	<!-- <![endif]-->
	<!--[if IE]>
	<script src="<%=path%>/pages/assets/js/jquery-1.10.2.min.js" ></script>		
	<![endif]-->

<script type="text/javascript">
	var ctp = "<%=path%>";
	var ajaxFlag=false;
	
</script>
</head>
<body style="background: #ffffff;overflow-y:hidden">
	<sitemesh:write property='body'/>
	<script src="<%=path%>/pages/common/ajaxSetup.js"></script> 
	<script src="<%=path%>/pages/assets/js/ace-extra.min.js"></script>
	<!--[if lt IE 9]>
	<script src="<%=path%>/pages/assets/js/html5shiv.js"></script>
	<script src="<%=path%>/pages/assets/js/respond.min.js"></script>
	<![endif]-->
	<script src="<%=path%>/pages/assets/js/bootstrap.min.js"></script>
	<script src="<%=path%>/pages/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=path%>/pages/assets/js/jquery.slimscroll.min.js"></script>
	<script src="<%=path%>/pages/assets/js/chosen.jquery.min.js"></script>
	<script src="<%=path%>/pages/assets/js/ace-elements.min.js"></script>
	<script src="<%=path%>/pages/assets/js/ace.min.js"></script>
	<script src="<%=path%>/pages/assets/js/md5.js"></script>
	<script src="<%=path%>/pages/assets/js/custom.js"></script>
	<script src="<%=path %>/pages/assets/js/jquery.messager.js"></script>
	
</body>
</html>