<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body class="login-body">
  <div class="body">
	<div class="index-top">
		<div class="common-return-home">
			<a href="http://www.hljlbh.org.cn/">返回官网</a>
		</div>
		<div class="common-logo">
			<img src="${exhibitionInfo.siteSmartLogo}">
		</div>
		<div class="common-sitename">
			<img src="${exhibitionInfo.siteHeadLogo}">
		</div>
		<div style="clear:both;height:10px"></div>
	</div>
	<div class="banner">
	  <div class="banner-left" style="display:none"><img src="/images/banner-left.png" /></div>
	  <div class="banner-time" style="display:none">
	  	<div class="time-left">距${exhibitionInfo.sessionName}<br />${exhibitionInfo.exhibitionName}</div>
	  	<div class="time-right"><div>${days}</div><br />&nbsp;天</div>
	  </div>
	  <div class="banner-right">
	    <img src="/images/banner-right.png" />
	  </div>
	</div>
	<div style="height:35px;clear:both;"></div>
	<div class="index-left">
		<div class="index-note">
			<div class="index-note-head">通知公告</div>
			<div class="index-note-c"><#list notices as notice><a href="/${language}/article-${notice.menuId}-${notice.articleId}.html">${notice.articleTitle}</a></#list></div>
			<div class="index-note-line"></div>
			<div class="index-cz-head">参展指南</div>
			<div class="index-cz-c"><#list helps as help><a href="/${language}/article-${help.menuId}-${help.articleId}.html">${help.articleTitle}</a></#list></div>
			<div style="height:10px;clear:both;"></div>
			<div class="index-cz-head">常见问题</div>
			<div class="index-question-c"><#list FAQs as faq><a href="/${language}/article-${faq.menuId}-${faq.articleId}.html">${faq.articleTitle}</a></#list></div>
		</div>
	</div>
	<div class="index-right">
		<div class="l1"><a href="/${language}/exhibitor-login.html">参展商登录</a></div>
		<div class="l2"><a href="/${language}/delegation-login.html">省直厅局/交易团登录</a></div>
		<div class="l3"><a href="/${language}/decorator-login.html">搭建商登录</a></div>
		<div class="l4"><a href="/${language}/foreign-login.html">外宾网上登录</a></div>
		<div class="l5"><a href="/${language}/reporter-login.html">记者网上登录</a></div>
		<div class="l6"><a href="/${language}/purchaser-login.html">采购商登录</a></div>
	</div>
  </div>
  <div class="index-footer">网站主办单位：黑龙江省国际博览发展促进中心</div>
</body>
</html>
