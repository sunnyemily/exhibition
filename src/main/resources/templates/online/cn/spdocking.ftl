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
<div class="header contact-header">
	<div class="box">
		<div class="logo">
			<a href="" class="alogo"><img src="images/logo2.png" alt=""></a>
			
			<div class="header-right">			
				<div class="header-search">
					<#include 'search.html'>
				</div>
			</div>
		</div>
		<div class="header-text">
			<#include 'time.html'>
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
		<div class="gcdj-left">
			<div class="gcdj-zxxp">
				<h3 class="title">
					<span>在线询盘</span>
				</h3>
				<div class="titbot">
					<!--p class="t">发起询价</p-->
					你可以在此输入您想询问的内容<br>
我们将把您的询价内容推送至符合条件的参展商<br>
以确保您第一时间获取参展商提供的报价
				</div>
				<style>
					.dropdown{
						width: 100%;
						border-radius: 5px;
						border:1px solid #DADADA;
					}
					.dropdown .selected, .dropdown li{
						height: 36px;
						line-height: 36px;
						padding: 0 15px;
						font-size: 14px;
					}
					.dropdown .carat{
						right: 15px;
					}
				</style>
				<div class="zxxp-form">
					<div class="form-left">
						<div class="selectbox1">
							<select tabindex="1" id="productmenuid" class="dropdown">
								<option value="">产品类别</option>
							</select>
						</div>				
						<input type="text" class="text3" style="margin-top:0px" id="quantity" placeholder="数量,例如：1000吨" >		
						<div class="selectbox2">
						
						<input type="hidden" id="quantityunit" value="0">						
						</div>
						<input type="text" id="title" class="text2" placeholder="输入标题">
						<input type="text" id="tel" class="text3" placeholder="请输入电话号码">
					</div>
					<div class="form-r">
						<textarea name="content" id="content" cols="30" rows="10" class="text4" placeholder="请输入您需要了解的内容"></textarea>
						<p>
							<button onclick="tj_zxxp()" class="input">提交询价</button>
						</p>
					</div>
				</div>
			</div>
			<div class="gcdj-left-gcdj">
				<div class="title">
					<span id="3">供采对接</span>
					<div class="rsearch">
						<i class="ico"></i>
						<input type="text" id="xpkeyword" class="text" placeholder="搜索">
						<button onclick="SearchOnlineInquiry(${params.menuid})" class="input">搜索</button>
					</div>
				</div>
				<div class="gcdj-menu">
				</div>
				
				<#if gclistsCount==0>
				<div style="width: 100%;float: left;padding: 40px 0;text-align: center;background: #fff;"><img src="/online/cn/images/nodata.png" alt=""><p style="color: #A9A9A9;font-size: 18px;padding-top: 15px;">还没有内容哦~</p></div>
				<#else>
				<div class="gcdj-list">
					<ul>	
					<#list gclists as gclist>				
						<li>
							<div class="topbg"></div>
							<div class="libg">
								<div class="time">${gclist.addtime}前发布</div>
								<h3 class="tit">
									<a href="javascript:void(0)" title="${gclist.title}">${gclist.shorttitle}</a>
								</h3>
								<div class="number">${gclist.quantity}</div>
								<div class="text">
									${gclist.content}
								</div>
								<div class="bot">
									<span class="fl">${gclist.menuname}</span>
									<button onclick=layer.alert('联系电话：${gclist.tel}') class="input">获取联系方式</button>
								</div>
							</div>
						</li>
					</#list>
					</ul>
					<div class="pages">
						<#list paging as page>
							<a ${(page.yema == pageIndex)?string('class="active"','')} ${(page.yema == 0)?string('','href="spdocking-'+params.menuid+"-"+params.keywords+'-'+page.yema+'.html"')}>${page.title}</a>
						</#list>
					</div>
				</div>
				</#if>
			</div>
		</div>
		<div class="gcdj-right">
			<ul>
				<#list ProductLists as prolist>
				<li>
					<div class="pic">
						<a href="productinfo-${prolist.productid}.html">
							<img src="${prolist.productpicture}" alt="" class="zoom-img">
						</a>
					</div>
					<h3>
						<a href="">
							${prolist.productname}
						</a>
					</h3>
					<button style="display:none" class="input">免费咨询</button>
				</li>
				</#list>	
			</ul>
			
		</div>
	</div>
	<div class="cl"></div>
</div>
<input type="hidden" value="0" id="mid">
<div class="cl"></div>
<#include 'foot.html'>
<script src="/online/cn/js/backtop.js"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/online/loadSelect.js"></script>
<script>
	//产品单位
	loadZiDianByParentID_Select($("#quantityunit").val(), 105, "quantityunit");
	//产品类别
	loadZiDianByParentID_Select($("#productmenuid").val(), 90, "productmenuid");
	//loadZiDianByParentID_Menu();
	loadZiDianByParentID_Menu1(${params.menuid});
	//LoadOnlineInquiry(0);	
</script>

</body>
</html>
