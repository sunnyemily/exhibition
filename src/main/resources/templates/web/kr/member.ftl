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
		흑룡강녹색식품산업박람회 부스신청 관리 시스템
	</div>
	<a href="http://www.hljlbh.org.cn/">웹 사이트로 돌아가기</a>
	<div class="login-info" style="display:none">
		<#if memberPojo??>${memberPojo.memberCompany!}</#if>，본 시스템에 로그인한 것을 환영합니다. 오늘은 ${(.now?string("yyyy년MM월dd일"))!} 요일입니다<script>var weeks=[ "天","一", "二", "三", "四", "五", "六"];document.write(weeks[new Date().getDay()])</script>
	</div>
</div>
<div>
	<div class="member-left">
		<div class="left-head"><i class="fa fa-th-list"></i>기능관리</div>
		<div style="height:20px;"></div>
		<#if type=="exhibitor">	
		<div class="left-item" data-page="apply"><i class="fa fa-desktop"></i>부스신청<i class="fa jt fa-chevron-right"></i></div>	
		<div class="left-item" data-page="product"><i class="fa fa-desktop"></i>제품관리<i class="fa jt fa-chevron-right"></i></div>
		</#if>
		<#if type=="delegation">
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1>
					<div class="left-item" data-page="personpapers-5"><i class="fa fa-credit-card-alt"></i>전시판매업관리<i class="fa jt fa-chevron-right"></i></div>
					<div class="two-level">
						<div class="left-item" data-page="enterprise"><i class="fa fa-bars"></i>참가업체 추가</div>
						<div class="left-item" data-page="personpapers-${certificate.id}"><i class="fa fa-bars"></i>출입증 추가</div>
					</div>
					<div class="left-item" data-page="product"><i class="fa fa-desktop"></i>제품관리<i class="fa jt fa-chevron-right"></i></div>
				</#if>
			</#list>
		<#elseif type=="exhibitor">
		<div class="left-item" data-page="personpapers-5"><i class="fa fa-credit-card-alt"></i>출입증 관리<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1&&canInCertification>
				<div class="left-item" data-page="personpapers-${certificate.id}i"><i class="fa fa-bars"></i>출입증 신청(실내 전시구)</div>
				</#if>
			</#list>
			<#list certificateTypes as certificate> 
				<#if certificate.type==0 && certificate.isexhibitor==1&&canOutCertification>
				<div class="left-item" data-page="personpapers-${certificate.id}o"><i class="fa fa-bars"></i>출입증 신청(실외 전시구)</div>
				</#if>
			</#list>
		</div>
		</#if>
		<div class="left-item" data-page="person"><i class="fa fa-vcard-o"></i>인원 출입증<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
		<#list certificateTypes as certificate> 
			<#if certificate.type==0 && certificate.isexhibitor==0>
			<div class="left-item" data-page="personpapers-${certificate.id}"><i class="fa fa-bars"></i>${certificate.chinesename}</div>
			</#if>
		</#list>
		</div>
		<#if type=="delegation" || type=="reporter" || type=="exhibitor" || type=="decorator">
		<div class="left-item" data-page="car"><i class="fa fa-car"></i>차량 출입증<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
		<#list certificateTypes as certificate> 
			<#if certificate.type==1>
			<div class="left-item" data-page="vehiclecard-${certificate.id}"><i class="fa fa-bars"></i>${certificate.chinesename}</div>
			</#if>
		</#list>
		</div>
		</#if>
		<div class="left-item" data-page="historycard"><i class="fa fa-history"></i>역대 정보 추출<i class="fa jt fa-chevron-right"></i></div>
		<div class="two-level">
			<#if type=="delegation">
			<div class="left-item" data-page="historyenterprise"><i class="fa fa-bars"></i>업체정보 추출</div>
			</#if>
			<#if type=="delegation"|| type=="report"|| type=="exhibitor" || type=="decorator">
			<div class="left-item" data-page="historypersoncard"><i class="fa fa-bars"></i>인원 출입증 추출</div>
			</#if>
			<#if type=="delegation"|| type=="report"|| type=="exhibitor" || type=="decorator">
			<div class="left-item" data-page="historycarcard"><i class="fa fa-bars"></i>차량 출입증 추출</div>
			</#if>
		</div>
		<div class="left-item" data-page="report"><i class="fa fa-newspaper-o"></i>출입증 수령 증명서<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item" data-page="info"><i class="fa fa-pie-chart"></i>정보 총괄정리<i class="fa jt fa-chevron-right"></i></div>
		<#if type=="delegation" || type=="reporter">
		<div class="left-item" data-page="agent"><i class="fa fa-cog"></i>정보 수정<i class="fa jt fa-chevron-right"></i></div>
		<#elseif type=="exhibitor" || type=="online">
		<div class="left-item" data-page="company"><i class="fa fa-cog"></i>업체정보 수정<i class="fa jt fa-chevron-right"></i></div>
		</#if>
		<div class="left-item" data-page="password"><i class="fa fa-lock"></i>비밀번호 수정<i class="fa jt fa-chevron-right"></i></div>
		<div class="left-item"  data-page="exit"><i class="fa fa-power-off"></i>나가기(Exit)<i class="fa jt fa-chevron-right"></i></div>
	</div>
	<div class="member-right">
		<iframe src="/${language}/${type}-info.html">
			
		</iframe>
	</div>
</div>
<script>
var printing="출입증 수령 증명서 프린터";
var noBooth='尚未申请展位，是否立即前往？';
var noLogo='尚未设置企业Logo，是否立即前往？';
var noSurfacePlot='尚未设置企业封面图，是否立即前往设置？';
var noProduct='尚未添加产品，是否立即前往添加？';
</script>
<script src="/script/member.js"></script>
</body>
</html>
