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
		Система регистрации ЭКСПО экологически чистых продуктов пров.Хэйлунцзян
	</div>
	<a href="http://www.hljlbh.org.cn/">Вернуться на официальный сайт</a>
	<div class="login-info" style="display:none">
		<#if memberPojo??>${memberPojo.memberCompany!}</#if>，Добро пожаловать в систему，今天是${(.now?string("yyyy年MM月dd日"))!} 星期<script>var weeks=[ "天","一", "二", "三", "四", "五", "六"];document.write(weeks[new Date().getDay()])</script>
	</div>
</div>
<div>
	<div class="member-left">
		<div class="left-head"><i class="fa fa-th-list"></i>Управление функциями</div>
		<div style="height:20px;"></div>
		<#if type=="exhibitor">	
		<div class="left-item" data-page="apply"><i class="fa fa-desktop"></i>Заявка на стенд<i class="fa jt fa-chevron-right"></i></div>	
		<div class="left-item" data-page="product"><i class="fa fa-desktop"></i>Управление продуктом<i class="fa jt fa-chevron-right"></i></div>
		</#if>
		<#if type=="delegation">
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1>
					<div class="left-item" data-page="personpapers-5"><i class="fa fa-credit-card-alt"></i>Управлять информацией заполненых экспонентов<i class="fa jt fa-chevron-right"></i></div>
					<div class="two-level">
						<div class="left-item" data-page="enterprise"><i class="fa fa-bars"></i>Добавить  экспонент-компания</div>
						<div class="left-item" data-page="personpapers-${certificate.id}"><i class="fa fa-bars"></i>Добавить информацию о бэйдже "Экспонент"</div>
					</div>
					<div class="left-item" data-page="product"><i class="fa fa-desktop"></i>Управление продуктом<i class="fa jt fa-chevron-right"></i></div>
				</#if>
			</#list>
		<#elseif type=="exhibitor">
		<div class="left-item" data-page="personpapers-5"><i class="fa fa-credit-card-alt"></i>Добавить  экспонент-компания<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1&&canInCertification>
				<div class="left-item" data-page="personpapers-${certificate.id}i"><i class="fa fa-bars"></i>Оформление бейджа "Экспонент" (в павильоне)</div>
				</#if>
			</#list>
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1&&canOutCertification>
				<div class="left-item" data-page="personpapers-${certificate.id}o"><i class="fa fa-bars"></i>Оформление бейджа "Экспонент" (вне помещениея)</div>
				</#if>
			</#list>
		</div>
		</#if>
		<div class="left-item" data-page="person"><i class="fa fa-vcard-o"></i>Персональный бейдж<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
		<#list certificateTypes as certificate> 
			<#if certificate.type==0 && certificate.isexhibitor==0>
			<div class="left-item" data-page="personpapers-${certificate.id}"><i class="fa fa-bars"></i>${certificate.chinesename}</div>
			</#if>
		</#list>
		</div>
		<#if type=="delegation" || type=="reporter" || type=="exhibitor" || type=="decorator">
		<div class="left-item" data-page="car"><i class="fa fa-car"></i>Пропуск на транспорт<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
		<#list certificateTypes as certificate> 
			<#if certificate.type==1>
			<div class="left-item" data-page="vehiclecard-${certificate.id}"><i class="fa fa-bars"></i>${certificate.chinesename}</div>
			</#if>
		</#list>
		</div>
		</#if>
		<div class="left-item" data-page="historycard"><i class="fa fa-history"></i>Извлечение информации из предыдущих ЭКСПО<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
			<#if type=="delegation">
			<div class="left-item" data-page="historyenterprise"><i class="fa fa-bars"></i>Извлечение информации о предприятии</div>
			</#if>
			<#if type=="delegation"|| type=="report"|| type=="exhibitor" || type=="decorator">
			<div class="left-item" data-page="historypersoncard"><i class="fa fa-bars"></i>Извлечение персональных бейджей из предыдущих ЭКСПО</div>
			</#if>
			<#if type=="delegation"|| type=="report"|| type=="exhibitor" || type=="decorator">
			<div class="left-item" data-page="historycarcard"><i class="fa fa-bars"></i>Извлечение Пропусков на транспорт</div>
			</#if>
		</div>
		<div class="left-item" data-page="report"><i class="fa fa-newspaper-o"></i>Таблица для получения  бейджей<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item" data-page="info"><i class="fa fa-pie-chart"></i>Сводная информация<i class="fa jt fa-chevron-right"></i></div>
		<#if type=="delegation" || type=="reporter">
		<div class="left-item" data-page="agent"><i class="fa fa-cog"></i>Измени информацию.<i class="fa jt fa-chevron-right"></i></div>
		<#elseif type=="exhibitor" || type=="online">
		<div class="left-item" data-page="company"><i class="fa fa-cog"></i>Исправить информацию о компании<i class="fa jt fa-chevron-right"></i></div>
		</#if>
		<div class="left-item" data-page="password"><i class="fa fa-lock"></i>Изменение пароля<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item"  data-page="exit"><i class="fa fa-power-off"></i>Вывод<i class="fa jt fa-chevron-right"></i></div>
	</div>
	<div class="member-right">
		<iframe src="/${language}/${type}-info.html">
			
		</iframe>
	</div>
</div>
<script>
var printing="Распечатать таблицу для получения  пропусков";
var noBooth='尚未申请展位，是否立即前往？';
var noLogo='尚未设置企业Logo，是否立即前往？';
var noSurfacePlot='尚未设置企业封面图，是否立即前往设置？';
var noProduct='尚未添加产品，是否立即前往添加？';
</script>
<script src="/script/member.js"></script>
</body>
</html>
