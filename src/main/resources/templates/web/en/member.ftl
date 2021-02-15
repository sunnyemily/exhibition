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
	<div class="site-name">Exhibitors Registration Management System of Heilongjiang Green Food Industry Expo.
	</div>
	<a href="http://www.hljlbh.org.cn/">return to official website</a>
	<div class="login-info">
		<#if memberPojo??>${memberPojo.memberCompany!}</#if>，Welcome to login the system. Today is <script>var weeks=[ "Sun.","Mon.", "Tues.", "Wed.", "Thur.", "Fri.", "Sat."];document.write(weeks[new Date().getDay()])</script>, ${(.now?string("MM, dd, yyyy"))!} 
	</div>
</div>
<div>
	<div class="member-left">
		<div class="left-head"><i class="fa fa-th-list"></i>function management</div>
		<div style="height:20px;"></div>
		<#if type=="exhibitor">	
		<div class="left-item" data-page="apply"><i class="fa fa-desktop"></i><span>booth application</span><i class="fa jt fa-chevron-right"></i></div>	
		<div class="left-item" data-page="product"><i class="fa fa-desktop"></i><span>production managment</span><i class="fa jt fa-chevron-right"></i></div>
		</#if>
		<#if type=="delegation">
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1>
					<div class="left-item" data-page="personpapers-5"><i class="fa fa-credit-card-alt"></i><span>exhibiting companies management</span><i class="fa jt fa-chevron-right"></i></div>
					<div class="two-level">
						<div class="left-item" data-page="enterprise"><i class="fa fa-bars"></i><span>add exhibiting companies</span></div>
						<div class="left-item" data-page="personpapers-${certificate.id}"><i class="fa fa-bars"></i><span>add personnel information for exhibitors card</span></div>
					</div>
					<div class="left-item" data-page="product"><i class="fa fa-desktop"></i><span>production managment</span><i class="fa jt fa-chevron-right"></i></div>
				</#if>
			</#list>
		<#elseif type=="exhibitor">
		<div class="left-item" data-page="personpapers-5"><i class="fa fa-credit-card-alt"></i><span>exhibitors card application</span><i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1&&canInCertification>
				<div class="left-item" data-page="personpapers-${certificate.id}i"><i class="fa fa-bars"></i><span>exhibitors card application(indoor exhibition area)</span></div>
				</#if>
			</#list>
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1&&canOutCertification>
				<div class="left-item" data-page="personpapers-${certificate.id}o"><i class="fa fa-bars"></i><span>exhibitors card application(outdoor exhibition area)</span></div>
				</#if>
			</#list>
		</div>
		</#if>
		<div class="left-item" data-page="person"><i class="fa fa-vcard-o"></i><span>personnel card</span><i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
		<#list certificateTypes as certificate> 
			<#if certificate.type==0 && certificate.isexhibitor==0>
			<div class="left-item" data-page="personpapers-${certificate.id}"><i class="fa fa-bars"></i><span>${certificate.engname}</span></div>
			</#if>
		</#list>
		</div>
		<#if type=="delegation" || type=="reporter" || type=="exhibitor" || type=="decorator">
		<div class="left-item" data-page="car"><i class="fa fa-car"></i><span>vehicle card</span><i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
		<#list certificateTypes as certificate> 
			<#if certificate.type==1>
			<div class="left-item" data-page="vehiclecard-${certificate.id}"><i class="fa fa-bars"></i><span>${certificate.engname}</div>
			</#if>
		</#list>
		</div>
		</#if>
		<div class="left-item" data-page="historycard"><i class="fa fa-history"></i><span>previous information retrieval</span><i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
			<#if type=="delegation">
			<div class="left-item" data-page="historyenterprise"><i class="fa fa-bars"></i><span>company information retrieval</span></div>
			</#if>
			<#if type=="delegation"|| type=="report"|| type=="exhibitor" || type=="decorator">
			<div class="left-item" data-page="historypersoncard"><i class="fa fa-bars"></i><span>personnel card retrieval</span></div>
			</#if>
			<#if type=="delegation"|| type=="report"|| type=="exhibitor" || type=="decorator">
			<div class="left-item" data-page="historycarcard"><i class="fa fa-bars"></i><span>vehicle card retrieval</span></div>
			</#if>
		</div>
		<div class="left-item" data-page="report"><i class="fa fa-newspaper-o"></i><span>card collection form</span><i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item" data-page="info"><i class="fa fa-pie-chart"></i><span>summary information</span><i class="fa jt fa-chevron-right"></i></div>
		<#if type=="delegation" || type=="reporter">
		<div class="left-item" data-page="agent"><i class="fa fa-cog"></i><span>information modification</span><i class="fa jt fa-chevron-right"></i></div>
		<#elseif type=="exhibitor" || type=="online">
		<div class="left-item" data-page="company"><i class="fa fa-cog"></i><span>company information modification</span><i class="fa jt fa-chevron-right"></i></div>
		</#if>
		<div class="left-item" data-page="password"><i class="fa fa-lock"></i><span>password modification</span><i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item"  data-page="exit"><i class="fa fa-power-off"></i><span>log off</span><i class="fa jt fa-chevron-right"></i></div>
	</div>
	<div class="member-right">
		<iframe src="/${language}/${type}-info.html">
			
		</iframe>
	</div>
</div>
<script>
var printing="printing card collection form";
var noBooth='尚未申请展位，是否立即前往？';
var noLogo='尚未设置企业Logo，是否立即前往？';
var noSurfacePlot='尚未设置企业封面图，是否立即前往设置？';
var noProduct='尚未添加产品，是否立即前往添加？';
</script>
<script src="/script/member.js"></script>
</body>
</html>
