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
			<span>优质展品</span>
			<div class="area">
				您现在的位置： <a href="index.html">首页</a> > 展品
			</div>
		</div>
		<div class="zs-list-search1">
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
									<input type="text" id="productkeyword" value="${params.keywords}" class="text" placeholder="请输入展品名称">
									<button
									onclick=SearchExhibitor('',$("#country").val(),$("#province").val(),$("#industryid").val(),$("#exhibitionid").val(),1,16,'product','')
									 class="input">搜索</button>
								</div>
				     		</td>
				     	</tr>
				     	</table>
				    </div>
			  	</div>
		  	</span>				
		</div>
		<div class="yzzp-list" style="margin-bottom: 30px;">
			<ul>
				<#list ProductLists as prolist>
				<li>
					<div class="pic">
						<a href="productinfo-${prolist.productid}.html">
							<span><img src="${prolist.productpicture}" alt=""></span>
						</a>
					</div>
					<div class="tit">
						<a href="productinfo-${prolist.productid}.html">
							${prolist.productname}
						</a>
					</div>
				</li>
				</#list>
			</ul>
			<div class="pages">
				<#list paging as page>
					<a ${(page.yema == pageIndex)?string('class="active"','')} ${(page.yema == 0)?string('','href="products-'+params.keywords+"-"+params.country+'-'+params.province+'-'+(params.industry==0)?string("",params.industry)+'-'+params.exhibition+'-'+page.yema+'-'+params.limit+'-'+params.companyid+'.html"')}>${page.title}</a>
				</#list>
			</div>
		</div>
	</div>
	<div class="cl"></div>
</div>
<input type="hidden" id="txtcountry" value="${params.country}">
<input type="hidden" id="txtprovince" value="${params.province}">
<div class="cl"></div>
<#include 'foot.html'>
<script src="/online/cn/js/backtop.js"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/online/loadSelect.js"></script>


</body>
</html>
