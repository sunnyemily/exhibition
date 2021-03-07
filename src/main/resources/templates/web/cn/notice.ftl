<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="/plugins/bootstrap-table/bootstrap-table.min.css">
	<link rel="stylesheet" href="/plugins/layui/css/layui.css"  media="all">
	<link href="/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

	<script src="/plugins/layui/layui.all.js" charset="utf-8"></script>
	<script src="/script/jquery.min.js"></script>
</head>
<body class="login-body">
<div class="regist-body">
	<div class="notice-left">
		<div class="left-head notice-gradient"><i class="fa fa-th-list"></i>公告</div>
		<#if notices?? && (notices?size > 0) >
		<#list notices as info>
		<a  class="left-item" href="/${language}/notice-${info.id}.html" title="${info.title}"><i class="fa jt fa-chevron-left"></i><span>${info.title}</span></a>
		</#list>
		</#if>
	</div>
	<div class="notice-right">
		<div class="notice-right-container">
			<#if notice?? >
			<div class="right-title" >
				${notice.title}
			</div>
			<div class="right-time">
				${notice.updateTimeStr}
			</div>
			<div class="right-content">
				${notice.content}
			</div>
			</#if>
		</div>
	</div>
	<div style="clear:both;"></div>
</div>
<script>
$(document).ready(function(){
	var leftHeight = $(".notice-left").height();
	var rightHeight = $(".right-content").height();
	if(leftHeight>rightHeight+50){
		$(".right-content").height(leftHeight-50);
	}
	else{
		$(".notice-left").height(rightHeight+50);
	}
}
);
</script>
</body>
</html>
