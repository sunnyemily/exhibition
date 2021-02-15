<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body class="login-body">
<#include 'top.html'>
<div class="body regist-body">
	<div class="article-left">
		<div class="left-head gradient"><i class="fa fa-th-list"></i>${menu.menuName}</div>
		<#list articles as info>
		<a  class="left-item" href="/${language}/article-${menu.menuId}-${info.articleId}.html" title="${info.articleTitle}"><span>${info.articleTitle}</span><i class="fa jt fa-chevron-right"></i></a>
		</#list>
	</div>
	<div class="article-right">
		<div class="regist-path gradient">あなたの場所：トップページ - ${menu.menuName}</div>
		<div class="right-container">
			<div class="right-content">
			${article.articleContent}
			</div>
		</div>
	</div>
	<div style="clear:both;"></div>
</div>
<script>
$(document).ready(function(){
	var leftHeight = $(".article-left").height();
	var rightHeight = $(".right-content").height();
	if(leftHeight>rightHeight+50){
		$(".right-content").height(leftHeight-50);
	}
	else{
		$(".article-left").height(rightHeight+50);
	}
}
);
</script>
<#include 'foot.html'>
</body>
</html>
