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
		${exhibitionInfo.exhibitionName}出展申込管理システム
	</div>
	<a href="http://www.hljlbh.org.cn/">公式サイトに戻る</a>
	<div class="login-info">
		<#if memberPojo??>${memberPojo.memberCompany!}</#if>，本システムにログインいただきありがとうございます。本日は${(.now?string("yyyy年MM月dd日"))!} 曜日<script>var weeks=[ "天","一", "二", "三", "四", "五", "六"];document.write(weeks[new Date().getDay()])</script>
	</div>
</div>
<div>
	<div class="member-left">
		<div class="left-head"><i class="fa fa-th-list"></i>機能管理</div>
		<div style="height:20px;"></div>
		<div class="left-item" data-page="product"><i class="fa fa-desktop"></i>製品管理<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item" data-page="company"><i class="fa fa-cog"></i>企業情報の修正<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item" data-page="password"><i class="fa fa-lock"></i>パスワード変更<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item"  data-page="exit"><i class="fa fa-power-off"></i>ログアウト<i class="fa jt fa-chevron-right"></i></div>
	</div>
	<div class="member-right">
		<iframe src="/${language}/${type}-company.html">
			
		</iframe>
	</div>
</div>
<script>
var printing="取证报表打印";
var noLogo='尚未设置企业Logo，是否立即前往？';
var noSurfacePlot='尚未设置企业封面图，是否立即前往设置？';
var noProduct='尚未添加产品，是否立即前往添加？';
</script>
<script src="/script/online.js"></script>
</body>
</html>
