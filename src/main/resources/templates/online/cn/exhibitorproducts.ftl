<!doctype html>
<html>
<#include 'head.html'>

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
<div class="show-bodybg">
	<div class="box">
		<div class="show-zp-top">
			<div class="show-zp-logo">
				<span>
					<#if companyinfo.companylogo=="">
						<img width="109px" src="/online/cn/images/nologo.jpg" alt="">
					<#else>
						<img width="109px" src="${companyinfo.companylogo}" alt="">
					</#if>
				</span>
			</div>
			<div class="show-zp-con">
				<h1>
					${companyinfo.chinesename}
					<div class="h1link">
						${(companyinfo.url=="")?string('','<a href="${companyinfo.url}"><span class="sp1"><i>虚拟展厅</i></span></a>')}
						${(zhibo.zhuangtai==0)?string('','<a target="_blank" href="${zhibo.faddress}"><span class="sp2"><i>直播</i><img src="images/ico12.png" alt=""></span></a>')}
					</div>
				</h1>
				<p>
					展区    ${companyinfo.tradinggroupname}<br>
					<#if companyinfo.comanyTypeId!=7>
					展位号    ${companyinfo.zhanweihao} <br> 
					</#if>                           
				</p>
			</div>
			<div class="show-zp-topr">
				<div>
					参展次数
					<p><span>${companyinfo.attendcount}</span>届</p>
				</div>
			</div>
		</div>
		<div class="show-zp-menu">
			<ul>
				<li><a href="exhibitorpage-${companyinfo.id}.html"><img src="images/ico13.png" alt="">企业介绍</a></li>
				<li class="active"><a href=""><img src="images/ico14.png" alt="">展品</a></li>
			</ul>
			<div class="zp-menur-search">
				<i class="ico"></i>
				<input type="text" id="productkeyword" value="${params.keywords}" class="text" placeholder="搜索本企业展品">
				<button
				onclick="SearchProduct(${companyinfo.id},${params.menuid})"
				 class="input">搜索</button>
			</div>
		</div>
		<div class="navMenubox wap wap-nav">
			<div>    					
				<ul class="navMenu">
					<li>
						<a href="javascript:;" class="afinve"><span class="">展品分类</span><span class="arrow"></span></a>
						<ul class="sub-menu">
							<#list productmenus as productmenu>
							<li>
								<a href="exhibitorproducts--${companyinfo.id}-${productmenu.menuid}-1-9.html">${productmenu.menuname}</a>
							</li>
							</#list>							
						</ul>					
					</li>				
				</ul>
			</div>
		</div>               
		<div class="zszp-left pc">
			<h3 class="tit">展品分类</h3>
			<ul>    
				<#list productmenus as productmenu>
					<li>
						<a href="exhibitorproducts--${companyinfo.id}-${productmenu.menuid}-1-9.html">${productmenu.menuname}</a>
					</li>
				</#list>	
			</ul>
		</div>
		<#if ProductListsCount==0>
		<div style="width:76%;float: left;margin-top:29px;padding: 40px 0;text-align: center;background: #fff;"><img src="/online/cn/images/nodata.png" alt=""><p style="color: #A9A9A9;font-size: 18px;padding-top: 15px;">还没有内容哦~</p></div>
		<#else>
		<div class="zszp-right">
			<ul>
			<#list ProductLists as prolist>
				<li>
					<div class="pic">
						<a href="productinfo-${prolist.productid}.html" class="mask">
							<span><img src="${prolist.productpicture}" alt=""></span>
						</a>
					</div>
					<h3>
						<a href="productinfo-${prolist.productid}.html">
                        ${prolist.productname}
                        </a>
					</h3>
				</li>
			</#list>				
			</ul>
			<div class="pages">
				<#list paging as page>
					<a ${(page.yema == pageIndex)?string('class="active"','')} ${(page.yema == 0)?string('','href="exhibitorproducts-'+params.keywords+'-'+params.companyid+'-'+(params.menuid==0)?string("",params.menuid)+'-'+page.yema+'-'+params.limit+'.html"')}>${page.title}</a>
				</#list>
			</div>
		</div>
		</#if>
	</div>
	<div class="cl"></div>
</div>

<div class="cl"></div>
<#include 'foot.html'>
<script src="/online/cn/js/backtop.js"></script>

</body>
</html>
