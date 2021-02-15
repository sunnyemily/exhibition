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
		${exhibitionInfo.exhibitionName}${typeName}报名管理系统
	</div>
	<a href="http://www.hljlbh.org.cn/">返回官网</a>
	<div class="login-info">
		<#if memberPojo??>${memberPojo.memberCompany!}</#if>，欢迎您登录本系统，今天是${(.now?string("yyyy年MM月dd日"))!} 星期<script>var weeks=[ "天","一", "二", "三", "四", "五", "六"];document.write(weeks[new Date().getDay()])</script>
	</div>
</div>
<div>
	<div class="member-left">
		<div class="left-head"><i class="fa fa-th-list"></i>功能管理</div>
		<div style="height:20px;"></div>
		<#if type=="exhibitor">	
		<div class="left-item" data-page="apply"><i class="fa fa-desktop"></i>申请展位<i class="fa jt fa-chevron-right"></i></div>	
		<div class="left-item" data-page="product"><i class="fa fa-desktop"></i>产品管理<i class="fa jt fa-chevron-right"></i></div>
		</#if>
		<#if type=="delegation">
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1>
					<div class="left-item" data-page="personpapers-5"><i class="fa fa-credit-card-alt"></i>参展商管理<i class="fa jt fa-chevron-right"></i></div>
					<div class="two-level">
						<div class="left-item" data-page="enterprise"><i class="fa fa-bars"></i>添加参展企业</div>
						<div class="left-item" data-page="personpapers-${certificate.id}"><i class="fa fa-bars"></i>添加参展证人员信息</div>
					</div>
					<div class="left-item" data-page="product"><i class="fa fa-desktop"></i>产品管理<i class="fa jt fa-chevron-right"></i></div>
				</#if>
			</#list>
		<#elseif type=="exhibitor">
		<div class="left-item" data-page="personpapers-5"><i class="fa fa-credit-card-alt"></i>参展证办理<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1&&canInCertification>
				<div class="left-item" data-page="personpapers-${certificate.id}i"><i class="fa fa-bars"></i>申办参展证（室内展场）</div>
				</#if>
			</#list>
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1&&canOutCertification>
				<div class="left-item" data-page="personpapers-${certificate.id}o"><i class="fa fa-bars"></i>申办参展证（室外展场）</div>
				</#if>
			</#list>
		</div>
		</#if>
		<div class="left-item" data-page="person"><i class="fa fa-vcard-o"></i>人员证件<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
		<#list certificateTypes as certificate> 
			<#if certificate.type==0 && certificate.isexhibitor==0>
			<div class="left-item" data-page="personpapers-${certificate.id}"><i class="fa fa-bars"></i>${certificate.chinesename}</div>
			</#if>
		</#list>
		</div>
		<#if type=="delegation" || type=="reporter" || type=="exhibitor" || type=="decorator">
		<div class="left-item" data-page="car"><i class="fa fa-car"></i>车辆证件<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
		<#list certificateTypes as certificate> 
			<#if certificate.type==1>
			<div class="left-item" data-page="vehiclecard-${certificate.id}"><i class="fa fa-bars"></i>${certificate.chinesename}</div>
			</#if>
		</#list>
		</div>
		</#if>
		<div class="left-item" data-page="historycard"><i class="fa fa-history"></i>历届信息提取<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
			<#if type=="delegation">
			<div class="left-item" data-page="historyenterprise"><i class="fa fa-bars"></i>企业信息提取</div>
			</#if>
			<#if type=="delegation"|| type=="report"|| type=="exhibitor" || type=="decorator">
			<div class="left-item" data-page="historypersoncard"><i class="fa fa-bars"></i>人员证件提取</div>
			</#if>
			<#if type=="delegation"|| type=="report"|| type=="exhibitor" || type=="decorator">
			<div class="left-item" data-page="historycarcard"><i class="fa fa-bars"></i>车辆证件提取</div>
			</#if>
		</div>
		<div class="left-item" data-page="report"><i class="fa fa-newspaper-o"></i>取证报表<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item" data-page="info"><i class="fa fa-pie-chart"></i>汇总信息<i class="fa jt fa-chevron-right"></i></div>
		<#if type=="delegation" || type=="reporter">
		<div class="left-item" data-page="agent"><i class="fa fa-cog"></i>修改信息<i class="fa jt fa-chevron-right"></i></div>
		<#elseif type=="exhibitor" || type=="online">
		<div class="left-item" data-page="company"><i class="fa fa-cog"></i>修改企业信息<i class="fa jt fa-chevron-right"></i></div>
		</#if>
		<div class="left-item" data-page="password"><i class="fa fa-lock"></i>修改密码<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item"  data-page="exit"><i class="fa fa-power-off"></i>退出<i class="fa jt fa-chevron-right"></i></div>
	</div>
	<div class="member-right">
		<iframe src="/${language}/${type}-info.html">
			
		</iframe>
	</div>
</div>
<script src="/script/member.js"></script>
</body>
</html>
