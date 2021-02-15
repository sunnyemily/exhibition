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
		${exhibitionInfo.exhibitionName}exhibitor registration management system
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
		<div class="left-item" data-page="product"><i class="fa fa-desktop"></i>production managment<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item" data-page="company"><i class="fa fa-cog"></i>company information modification<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item" data-page="password"><i class="fa fa-lock"></i>password modification<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item"  data-page="exit"><i class="fa fa-power-off"></i>log off<i class="fa jt fa-chevron-right"></i></div>
	</div>
	<div class="member-right">
		<iframe src="/${language}/${type}-company.html">
			
		</iframe>
	</div>
</div>
<script>
var printing="printing card collection form";
var noLogo='尚未设置企业Logo，是否立即前往？';
var noSurfacePlot='尚未设置企业封面图，是否立即前往设置？';
var noProduct='尚未添加产品，是否立即前往添加？';
</script>
<script src="/script/online.js"></script>
</body>
</html>
