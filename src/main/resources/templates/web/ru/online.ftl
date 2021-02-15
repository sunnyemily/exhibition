<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<script>
var type="${type}";
var language="${language}";
var member = ${member};
var certificateTypes = ${certificateTypesPojo};
<#if type=="exhibitor">
var applyBooth = ${applyInfo.applyBooth?string("true","false")};
var hasLogo = ${applyInfo.hasLogo?string("true","false")};
var hasPicture = ${applyInfo.hasPicture?string("true","false")};
var productCount = ${applyInfo.productCount};
var boothCount = ${applyInfo.boothCount};
</#if>
</script>
<body class="login-body">
<div class="member-head">
	<div class="logo">
		<image src="${exhibitionInfo.siteSmartLogo}" />
	</div>
	<div class="site-name">
		${exhibitionInfo.exhibitionName}Система управления регистрацией экспонентов
	</div>
	<a href="http://www.hljlbh.org.cn/">Вернуться на официальный сайт</a>
	<div class="login-info">
		<#if memberPojo??>${memberPojo.memberCompany!}</#if>，欢迎您登录本系统，今天是${(.now?string("yyyy年MM月dd日"))!} 星期<script>var weeks=[ "天","一", "二", "三", "四", "五", "六"];document.write(weeks[new Date().getDay()])</script>
	</div>
</div>
<div>
	<div class="member-left">
		<div class="left-head"><i class="fa fa-th-list"></i>Управление функциями</div>
		<div style="height:20px;"></div>
		<div class="left-item" data-page="product"><i class="fa fa-desktop"></i>Управление продуктом<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item" data-page="company"><i class="fa fa-cog"></i>Исправить информацию о компании<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item" data-page="password"><i class="fa fa-lock"></i>Изменение пароля<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item"  data-page="exit"><i class="fa fa-power-off"></i>Вывод<i class="fa jt fa-chevron-right"></i></div>
	</div>
	<div class="member-right">
		<iframe src="/${language}/${type}-company.html">
			
		</iframe>
	</div>
</div>
<script>
var printing="Распечатать таблицу для получения  пропусков";
var noLogo='尚未设置企业Logo，是否立即前往？';
var noSurfacePlot='尚未设置企业封面图，是否立即前往设置？';
var noProduct='尚未添加产品，是否立即前往添加？';
</script>
<script src="/script/online.js"></script>
</body>
</html>
