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
    					<ul class="navMenu">
    						<li>
    							<div class="header-search">
									<#include 'search.html'>
								</div>
    						</li>	
     						<#include 'navmobile.html'>						
    					</ul>
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
		<div class="swiper-container dmjzq-banner">
			<div class="swiper-wrapper">
		 		<div class="swiper-slide">
		 			<img src="images/13.jpg">
		 		</div>		      	
		 		<div class="swiper-slide">
		 			<img src="images/13.jpg">
		 		</div>	
		 		<div class="swiper-slide">
		 			<img src="images/13.jpg">
		 		</div>	
		    </div>
		    <!-- Add Pagination -->
		    <div class="swiper-pagination"></div>    
		</div>
		<div class="dmjzq-title">
			<span>优质展商</span>
			<a href="javascript:;" onclick="LoadQualityExhibitors(${exhibitionid},12)" class="ref">换一批</a>
		</div>
		<div class="yzzs-list">
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
		<div class="dmjzq-title">
			<span>优质展品</span>
			<a href="/online/cn/products-----${exhibitionid}-1-16-.html" class="more">查看更多</a>
		</div>
		<div class="yzzp-list">
			<ul>
				<li><div class="pic"><a class="yzzpa0" href=""><span><img class="yzzpimg0" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa20" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa1" href=""><span><img class="yzzpimg1" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa21" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa2" href=""><span><img class="yzzpimg2" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa22" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa3" href=""><span><img class="yzzpimg3" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa23" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa4" href=""><span><img class="yzzpimg4" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa24" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa5" href=""><span><img class="yzzpimg5" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa25" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa6" href=""><span><img class="yzzpimg6" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa26" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa7" href=""><span><img class="yzzpimg7" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa27" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa8" href=""><span><img class="yzzpimg8" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa28" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa9" href=""><span><img class="yzzpimg9" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa29" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa10" href=""><span><img class="yzzpimg10" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa210" href=""></a></div></li>
				<li><div class="pic"><a class="yzzpa11" href=""><span><img class="yzzpimg11" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa211" href=""></a></div></li>
						
			</ul>
		</div>
	</div>
	<div class="cl"></div>
</div>
<div class="dmjzq-formbg">
	<div class="box">
		<div class="form-left">
			<h3>在线询盘</h3>
			<p>
			你可以在此输入您想询问的内容<br>
我们将把您的询价内容推送至符合条件的参展商<br>
以确保您第一时间获取参展商提供的报价
                            
			</p>
		</div>
		<div class="form-list">
			<h3>发起询价</h3>
			<ul>
				<li>
					<select id="productmenuid" name="productmenuid">
						<option value="">产品类别</option>
					</select>
				</li>				
				<li>
					<input type="text" id="title" class="text2" style="width:305px" placeholder="输入标题">
				</li>
				<li>
					<input type="text" id="tel" class="text2" style="width:305px" placeholder="输入电话">
				</li>
				<li>
					<textarea name="" id="content" cols="30" rows="5" class="text" style="height:102px" placeholder="请输入您需要了解的内容..."></textarea>
				</li>
				<li>
					<input type="text" id="quantity" class="text2" placeholder="数量" onkeyup="value=value.replace(/[^(\-)?\(0-9)+((0-9)))]/g,'')">
					<select name="quantityunit" id="quantityunit" class="select2">
						<option value="">单位</option>
					</select>
				</li>
				<li>
					<button type="button" onclick="tj_zxxp()" class="input">提交询价</button>
				</li>
			</ul>
		</div>
	</div>
	<div class="cl"></div>
</div>
<div class="cl"></div>
<#include 'foot.html'>
<script src="/online/cn/js/backtop.js"></script>
<script src="/online/cn/js/swiper.min.js"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/online/loadSelect.js"></script>
<script>
	//加载优质展商
	LoadQualityExhibitors(${exhibitionid},12);
	//加载优质展品
	LoadQualityProducts(${exhibitionid},0,12); 
	
	//产品单位
	loadZiDianByParentID_Select($("#quantityunit").val(), 105, "quantityunit");
	//产品类别
	loadZiDianByParentID_Select($("#productmenuid").val(), 90, "productmenuid");
	
    var swiper = new Swiper('.dmjzq-banner', {
      autoHeight: true, //enable auto height
      spaceBetween: 1,
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
      
    });
</script> 

</body>
</html>
