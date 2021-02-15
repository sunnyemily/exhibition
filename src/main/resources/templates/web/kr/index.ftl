<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body class="login-body">
  <div class="body">
	<div class="index-top">
		<div class="common-return-home">
			<a href="http://www.hljlbh.org.cn/">웹 사이트로 돌아가기</a>
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
	  <div class="banner-left"><img src="/images/banner-left.png" /></div>
	  <div class="banner-time">
	  	<div class="time-left">까지${exhibitionInfo.sessionName}<br />${exhibitionInfo.exhibitionName}</div>
	  	<div class="time-right"><div>${days}</div><br />&nbsp;일</div>
	  </div>
	  <div class="banner-right">
	    <img src="/images/banner-right.png" />
	  </div>
	</div>
	<div style="height:35px;clear:both;"></div>
	<div class="index-left">
		<div class="index-note">
			<div class="index-note-head">통지</div>
			<div class="index-note-c"><#list notices as notice><a href="/${language}/article-${notice.menuId}-${notice.articleId}.html">${notice.articleTitle}</a></#list></div>
			<div class="index-note-line"></div>
			<div class="index-cz-head">전시회 참가 안내</div>
			<div class="index-cz-c"><#list helps as help><a href="/${language}/article-${help.menuId}-${help.articleId}.html">${help.articleTitle}</a></#list></div>
			<div style="height:10px;clear:both;"></div>
			<div class="index-cz-head">빈번한 질문</div>
			<div class="index-question-c"><#list FAQs as faq><a href="/${language}/article-${faq.menuId}-${faq.articleId}.html">${faq.articleTitle}</a></#list></div>
		</div>
	</div>
	<div class="index-right">
		<div class="l1"><a href="/${language}/exhibitor-login.html">개별업체 로그인 </a></div>
		<div class="l2"><a href="/${language}/delegation-login.html">정부단체 로그인</a></div>
		<div class="l3"><a href="/${language}/decorator-login.html">시공사 로그인</a></div>
		<div class="l4"><a href="/${language}/foreign-login.html">내빈 로그인</a></div>
		<div class="l5"><a href="/${language}/reporter-login.html">기자 로그인</a></div>
		<div class="l6"><a href="/${language}/purchaser-login.html">바이어 로그인</a></div>
	</div>
  </div>
  <div class="index-footer">웹 사이트 주관기관:흑룍강성국제박랍발전촉진센터</div>
</body>
</html>
