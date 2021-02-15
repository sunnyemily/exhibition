<!doctype html>
<html>
<#include 'head.html'>
<link rel="stylesheet" href="/plugins/layui/css/layui-online.css">
<body>

<!--移动端菜单-->
<div class="wap-header wap">
	<div class="mobile">
		<div class="mobile-inner">
			<div class="mobile-inner-header">
	    		<a href="#" class="wap-logo"><img src="images/logo.png"></a>
		    	<div class="mobile-inner-header-icon mobile-inner-header-icon-out"><span></span><span></span><span></span></div>
		    	<div class="wap-header-menu">
					<a href="javascript:;" class="memico btn-mem">
					</a>
				</div>
		    </div>
		    <div class="mobile-inner-nav">
		    	<div class="navMenubox">
  					<div id="slimtest">
    					
     						<#include 'navmobile.html'>					
    					
  					</div>
				</div>				
		    </div>
		</div>
	</div>
	<script>
	$(document).ready(function(){
		  $(".mobile-inner-header-icon").click(function(){
		  	$(this).toggleClass("mobile-inner-header-icon-click mobile-inner-header-icon-out");
		  	$(".mobile-inner-nav").slideToggle(250);
		  });
		  $(".mobile-inner-nav a").each(function( index ) {
		  	$( this ).css({'animation-delay': (index/10)+'s'});
		  });
		});
	</script>
</div>
<!--移动端菜单结束-->
<div class="header">
	<div class="box">
		<div class="logo">
			<a href=""><img src="images/logo.png" alt=""></a>
		</div>
		<div class="header-right">
			<div class="toptext">
				<#include 'time.html'>
			</div>
			<div class="header-search">
				<#include 'search.html'>
			</div>
		</div>
	</div>
</div>
<div class="menubox">
	<div class="box">
		<#include 'nav.html'>
	</div>
</div>

<div class="">
	<div class="box">
		<div class="zs-list-title">
			<span>优质展商</span>
			<div class="area">
				您现在的位置： <a href="index.html">首页</a> > 展商
			</div>
		</div>
		<div class="zs-list-search1" style="border:0px solid blue">
			
			
			<span class="layui-form">
				<div class="layui-form-item">
				    <div class="layui-input-block" style="border:0px solid red">
				     <table  style="border:0px solid red;width:100%">
				     	<tr>
				     		<td style="width:15%;padding:0px 3px">
				     			<select  id="industryid">
									<option value="">行业</option>
									<#list industries as industry>${params.industry}
										<option ${(industry.id==params.industry)?string('selected','')} value="${industry.id}">${industry.chineseName}</option>
									</#list>				
								</select>
				     		</td>
				     		<td style="width:15%;padding:0px 3px">
				     			<select  id="exhibitionid">
									<option value="">展区</option>
									<#list exhibitionLists as exhibitionList>
										<option ${(exhibitionList.exhibitionid==params.exhibition)?string('selected','')} value="${exhibitionList.exhibitionid}">${exhibitionList.exhibitionname}</option>
									</#list>				
								</select>
				     		</td>
				     		<td style="width:15%;padding:0px 3px">
				     			<select name="country" lay-filter="country" id="country">						
					  			</select>
				     		</td>
				     		<td style="width:15%;padding:0px 3px">
				     			<select name="province" id="province">
								</select>
				     		</td>
				     		<td align="right">
				     			<div class="zp-menur-search zs-r-search">
									<i class="ico"></i>
									<input type="text" id="exhibitorkeyword" value="${params.keywords}" class="text" placeholder="请输入展商名称">
									<button
									onclick=SearchExhibitor('',$("#country").val(),$("#province").val(),$("#industryid").val(),$("#exhibitionid").val(),1,6,'exhibitior','')
									 class="input">搜索</button>
								</div>
				     		</td>
				     	</tr>
				     	</table>
				    </div>
			  	</div>
		  	</span>	
			
		</div>
		<div class="sub-zs-list">
			<ul>
			<#list ExhibitionList as elist> 
				<li>
					<div class="zs-logo">
						<a href="exhibitorpage-${elist.companyid}.html">
						<#if elist.companylogo=="">
							<img width="76" height="76" src="/online/cn/images/nologo.jpg" alt="">
						<#else>
							<img width="76" height="76" src="${elist.companylogo}" alt="">
						</#if>
						</a>
					</div>
					<div class="text">
						<h3 class="tit">
							<a href="exhibitorpage-${elist.companyid}.html">${elist.chinesename}</a> ${(elist.url=="")?string('','<span><a target="_blank" href="${elist.url}">虚拟展厅</a></span>')}
						</h3>
						<div class="con">
							${elist.shortname}
						</div>
						<div class="conbot">
							展区：<span>${elist.exhibitionname}</span><br>
							<#if elist.companytypeid!=7>
							展位号：<span>${elist.zhanweihao}</span>
							</#if>
						</div>
					</div>
					<div class="rpics">
						<dl id="cp${elist.companyid}">
							
						</dl>
					</div>
				</li>
			</#list>				
			</ul>
			<div class="pages">
				<#list paging as page>
					<a ${(page.yema == pageIndex)?string('class="active"','')} ${(page.yema == 0)?string('','href="exhibitorlist-'+params.keywords+"-"+params.country+'-'+params.province+'-'+(params.industry==0)?string('',params.industry)+'-'+params.exhibition+'-'+page.yema+'-'+params.limit+'-'+params.companyid+'.html"')}>${page.title}</a>
				</#list>
			</div>
		</div>
		<div class="sub-zs-bottit">
			<span>推荐企业</span>
			<a href="javascript:void(0)" onclick="LoadQualityExhibitors(0,12)" class="reg">换一批</a>
		</div>
		<div class="yzzs-list" style="margin-bottom: 30px;">
			<ul>
				<li><a id="yzzsa0" href=""><img id="yzzsimg0" src="" alt=""></a></li>
				<li><a id="yzzsa1" href=""><img id="yzzsimg1" src="" alt=""></a></li>
				<li><a id="yzzsa2" href=""><img id="yzzsimg2" src="" alt=""></a></li>
				<li><a id="yzzsa3" href=""><img id="yzzsimg3" src="" alt=""></a></li>
				<li><a id="yzzsa4" href=""><img id="yzzsimg4" src="" alt=""></a></li>
				<li><a id="yzzsa5" href=""><img id="yzzsimg5" src="" alt=""></a></li>
				<li><a id="yzzsa6" href=""><img id="yzzsimg6" src="" alt=""></a></li>
				<li><a id="yzzsa7" href=""><img id="yzzsimg7" src="" alt=""></a></li>
				<li><a id="yzzsa8" href=""><img id="yzzsimg8" src="" alt=""></a></li>
				<li><a id="yzzsa9" href=""><img id="yzzsimg9" src="" alt=""></a></li>
				<li><a id="yzzsa10" href=""><img id="yzzsimg10" src="" alt=""></a></li>
				<li><a id="yzzsa11" href=""><img id="yzzsimg11" src="" alt=""></a></li>	
			</ul>
		</div>
	</div>
	<div class="cl"></div>
</div>
<input type="text" value="${ids}" id="ids">
<div class="cl"></div>
<input type="hidden" id="txtcountry" value="${params.country}">
<input type="hidden" id="txtprovince" value="${params.province}">
<#include 'foot.html'>
<script src="/online/cn/js/backtop.js"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/online/loadSelect.js"></script>
<script>
	//加载推荐企业
	LoadQualityExhibitors(0,12);
	loadCompanyProductInfo();
</script>

</body>
</html>
