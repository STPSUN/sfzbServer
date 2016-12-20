/**
 * 用一个全局的方法来处理，session超时要跳转的页面。 jquery 可以用$.ajaxSetup 方法，ext也有类似的方法
 */
var i = 0;
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	beforeSend : function(xhr) {
		// 可以设置自定义标头防止缓存
		// xhr.setRequestHeader("Cache-Control","no-cache");
		xhr.setRequestHeader("If-Modified-Since",
				"Sat, 01 Jan 2000 00:00:00 GMT");
	},
	complete : function(XMLHttpRequest, textStatus) {
		// var mainheight = $("#mainFrame").contents().find("body").height()+30;
		// $("#mainFrame").height(mainheight);
		if (!ajaxFlag) { // 表示还未被调用
			ajaxFlag = true;
			var sessionstatus = XMLHttpRequest
					.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
			// var data = XMLHttpRequest.responseText;
			// console.info(++i+"=="+sessionstatus);
			if (sessionstatus == "timeout") {
				alert("登陆超时,请重新登录！");
				window.top.location.href = ctp + "/tologin";

			}
			ajaxFlag = false;
		}
	}
});