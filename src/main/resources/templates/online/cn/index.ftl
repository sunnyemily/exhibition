<!doctype html>
<html>
<#include 'head.html'>
<script src="/online/cn/js/jquery.SuperSlide.2.1.1.js" type="text/javascript"></script>
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
									<div class="sear-select">
										<select id="topselect1">
											<option value="0">找产品</option>
											<option value="1">找企业</option>
										</select>
										<input type="text" id="topkeyword1" placeholder="输入关键词" class="text">
										<button type="button" onclick="TopSearch1()"  class="input">搜索</button>
									</div>
								</div>
    						</li>	
     										
    					</ul><#include 'navmobile.html'>		
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
<div class="header pc">
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
<div class="menubox pc">
	<div class="box">
		<#include 'nav.html'>		
	</div>
</div>
<!--banner 开始-->
<div class="box">
	<div class="picBtnTop">
	<div class="hd">
		<ul>
			<#list toppictureLists as toppictureList>
				<li><a title="${toppictureList.title}" href="${toppictureList.link}" target="_blank">◇ ${toppictureList.shorttitle}</a></li>
			</#list>			
		</ul>
	</div>
	<div class="bd">
		<ul>
			<#list bannerLists as bannerList>		
			<li>				
				<a href="${bannerList.link}" target="_blank">
					<img src="${bannerList.picture}">
					<p>${bannerList.title}</p>
				</a>				
			</li>
			</#list>	
		</ul>
	</div>
</div>
<script type="text/javascript">
	jQuery(".picBtnTop").slide({ mainCell:".bd ul",effect:"left",autoPlay:true,triggerTime:0 });
</script>
</div>
<!--banner 结束-->
<div class="box">
	<div class="home-toplink">
		<ul class="link1">
		<#list zxhdLists as zxhdList>    
			<li style="color:white"><a href="${zxhdList.link}" target="_blank" title="${zxhdList.title}">${zxhdList.shorttitle}</a></li>
		</#list>			
		</ul>
		<ul class="link2">
		<#list wszgLists as wszgList>    
			<li><a href="${wszgList.link}" target="_blank" title="${wszgList.title}">${wszgList.shorttitle}</a></li>
		</#list>
		</ul>
		<ul class="link3">
		<#list gcdjLists as gcdjList>    
			<li><a href="${gcdjList.link}" target="_blank" title="${gcdjList.title}">${gcdjList.shorttitle}</a></li>
		</#list>
		</ul>
	</div>
</div>

<div class="box">
	<div class="home-bg">
		<h3 class="h-title h-title2" id="1"><span>展会日程</span></h3>
		<div class="home-zb-left">
			
			<div class="zb-bg">
				<div class="week-list">
					<ul>
						<li class="li1">
							十<br>月
						</li>
						<#list topzxs as topzx>
						<li>
							<a id="rq${topzx.date}" class="biaozhux" href="javascript:void(0)" onclick="LoadOnlineActivity('${topzx.date}','index')">
								${topzx.week}
							    <p>${topzx.shortdate}</p>
							</a>
						</li>
						</#list>
					</ul>
				</div>
				<div class="zb-left-list" id="content-1">
					<ul id="ulzxhd">
						
					</ul>
				</div>
			</div>
		</div>
		<div class="home-zb-right">
			
			<div class="zb-play-box">
				<!--<video id="shipin" poster="" src="images/ckin.mp4" data-ckin="default" data-overlay="1" data-title=""></video>-->
				<div id="shipin" style="width:100%; height:auto;"></div>
				<img id="tupian" height="413px" style="display:none" src="">
			</div>
			<h3 class="title2" id="h3biaoti">推荐活动</h3>
			<div class="zb-play-title">				
				<p id="hdintro"></p>
			</div>
		</div>
	</div>
</div>

<div class="box">
	<h3 class="h-title" id="2">
		在线巡馆
	</h3>
	<div class="home-zxxg">
		<ul>
		<#list onlinepavilions as onlinepavilion>
			<li>
				<div class="pic"><a target="_blank" href="${(onlinepavilion.link=="")?string('pavilioninfo-'+onlinepavilion.id+'.html',onlinepavilion.link)}" class="mask"><img src="${onlinepavilion.picture}" alt="" class="zoom-img"></a></div>
				<p>
					<a target="_blank" href="${(onlinepavilion.link=="")?string('pavilioninfo-'+onlinepavilion.id+'.html',onlinepavilion.link)}">${onlinepavilion.title}</a>
				</p>
			</li>
		</#list>			
		</ul>
	</div>
</div>

<div class="box">
	<h3 class="h-title" id="3">360全景展示</h3>
</div>
<div class="cl"></div>
<div class="h-360">
	<div class="box">
		<iframe src="${url360}" width="100%" height="450" frameborder="0"></iframe>
	</div>
</div>

<div class="home-xszt">
	<div class="box">
		<h3 class="h-title" id="4">线上展厅</h3>
		<div class="swiper-container home-banner2 xszt-wap wap"><!--移动端滚动图-->
			<div class="swiper-wrapper">
		 		<div class="swiper-slide">
		 			<a href="">
		 				<img src="images/3.jpg" alt="">
		 				<p>
		 					大米节展厅
		 				</p>
		 			</a>
		 		</div>		      	
		 		<div class="swiper-slide">
		 			<a href="">
		 				<img src="images/3.jpg" alt="">
		 				<p>
		 					绿色、有机食品展厅
		 				</p>
		 			</a>
		 		</div>
		 		<div class="swiper-slide">
		 			<a href="">
		 				<img src="images/3.jpg" alt="">
		 				<p>
		 					国内特色食品展厅
		 				</p>
		 			</a>
		 		</div>
		    </div>
		      
		</div>
		<div class="tab-wrapper pc">
			<div class="xszt-left">
				<ul class="tab-content">
					<#list pictureListMs as pictureList>    
						<li><a href="${pictureList.link}" target="_blank"><img src="${pictureList.picture}" alt=""></a></li>
					</#list>					
				</ul>
			</div>
			<div class="xszt-right">
				<h3 class="title">线上展厅</h3>
				<div id="marquee">
					<ul class="tab-menu">   
						<#list pictureListMs as pictureList>    
						<li title="${pictureList.title}">
							<a href="${pictureList.link}" target="_blank">
								${pictureList.shorttitle}
							</a>
						</li>
						</#list>
						
					</ul>
				</div>
				
			</div>
		</div>
	</div>
	<div class="cl"></div>
</div>

<div class="box">
	<div class="yzzs-title">
		<span class="t" id="5">优质展商</span>
		<a href="javascript:;" onclick="LoadQualityExhibitors(0,12)" class="ref">换一批</a>
		<a href="exhibitorlist------1-6-.html" class="more">查看更多 &gt;</a>
		<div class="rsearch">
			<i class="ico"></i>
			<input type="text" id="exhibitorkeyword" class="text" placeholder="搜索相关展商">
			<button onclick="SearchExhibitor('','','','','',1,6,'exhibitior')" class="input">搜索</button>
		</div>
	</div>
	<div class="yzzs-list">
		<ul>
			<li id="liyzzsa0"><a id="yzzsa0" href=""><img id="yzzsimg0" src="" alt=""></a></li>
			<li id="liyzzsa1"><a id="yzzsa1" href=""><img id="yzzsimg1" src="" alt=""></a></li>
			<li id="liyzzsa2"><a id="yzzsa2" href=""><img id="yzzsimg2" src="" alt=""></a></li>
			<li id="liyzzsa3"><a id="yzzsa3" href=""><img id="yzzsimg3" src="" alt=""></a></li>
			<li id="liyzzsa4"><a id="yzzsa4" href=""><img id="yzzsimg4" src="" alt=""></a></li>
			<li id="liyzzsa5"><a id="yzzsa5" href=""><img id="yzzsimg5" src="" alt=""></a></li>
			<li id="liyzzsa6"><a id="yzzsa6" href=""><img id="yzzsimg6" src="" alt=""></a></li>
			<li id="liyzzsa7"><a id="yzzsa7" href=""><img id="yzzsimg7" src="" alt=""></a></li>
			<li id="liyzzsa8"><a id="yzzsa8" href=""><img id="yzzsimg8" src="" alt=""></a></li>
			<li id="liyzzsa9"><a id="yzzsa9" href=""><img id="yzzsimg9" src="" alt=""></a></li>
			<li id="liyzzsa10"><a id="yzzsa10" href=""><img id="yzzsimg10" src="" alt=""></a></li>
			<li id="liyzzsa11"><a id="yzzsa11" href=""><img id="yzzsimg11" src="" alt=""></a></li>			
		</ul>
	</div>
	
	<div class="yzzs-title">
		<span class="t" id="6">优质展品</span>
		<a href="javascript:;" onclick="LoadQualityProducts(0,0,8)" class="ref">换一批</a>
		<a href="products------1-16-.html" class="more">查看更多 &gt;</a>
		<div class="rsearch">
			<i class="ico"></i>
			<input type="text" class="text" id="productkeyword" placeholder="搜索相关展品">
			<button onclick="SearchExhibitor('','','','','',1,16,'product')" class="input">搜索</button>
		</div>
	</div>
	<div class="yzzp-list">	
		<ul>
			<li id="liyzzpa0"><div class="pic"><a class="yzzpa0" href=""><span><img class="yzzpimg0" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa20" href=""></a></div></li>
			<li id="liyzzpa1"><div class="pic"><a class="yzzpa1" href=""><span><img class="yzzpimg1" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa21" href=""></a></div></li>
			<li id="liyzzpa2"><div class="pic"><a class="yzzpa2" href=""><span><img class="yzzpimg2" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa22" href=""></a></div></li>
			<li id="liyzzpa3"><div class="pic"><a class="yzzpa3" href=""><span><img class="yzzpimg3" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa23" href=""></a></div></li>
			<li id="liyzzpa4"><div class="pic"><a class="yzzpa4" href=""><span><img class="yzzpimg4" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa24" href=""></a></div></li>
			<li id="liyzzpa5"><div class="pic"><a class="yzzpa5" href=""><span><img class="yzzpimg5" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa25" href=""></a></div></li>
			<li id="liyzzpa6"><div class="pic"><a class="yzzpa6" href=""><span><img class="yzzpimg6" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa26" href=""></a></div></li>
			<li id="liyzzpa7"><div class="pic"><a class="yzzpa7" href=""><span><img class="yzzpimg7" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa27" href=""></a></div></li>
		</ul>
	</div>
	
	<div class="home-gcdj">
		<h3 class="h-title" id="7">
			<span>供采对接</span>
		</h3>
		<div class="gcdj-menu">			
		</div>
		<div id="nodatainfo" style="display:none;width: 100%;float: left;padding: 40px 0;text-align: center;background: #fff;"><img src="images/nodata.png" alt=""><p style="color: #A9A9A9;font-size: 18px;padding-top: 15px;">还没有内容哦~</p></div>
		<div class="gcdj-list">
			<ul id="ulgcdj">
				
			</ul>
		</div>
	</div>
</div>
<input type="hidden" value="0" id="mid">
<div class="cl"></div>
<#include 'foot.html'>
<!--视频播放器-->
<script src="https://imgcache.qq.com/open/qcloud/video/vcplayer/TcPlayer-2.3.2.js" charset="utf-8"></script>

<!--滚动条js-->
<script src="/online/cn/js/jquery.mCustomScrollbar.min.js"></script>	
<script>	
	if(location.href.indexOf("https://online.hljlbh.org.cn/")==-1&&location.href.indexOf("https://")>-1){
		location.href = location.href.replace("https://","http://");
	}
	(function($){
		$(window).load(function(){			
			$("#content-1").mCustomScrollbar({
				theme:"minimal"
			});			
		});
	})(jQuery);
</script>
<!--滚动条js end-->
<script src="/online/cn/js/backtop.js"></script>
<script src="/online/cn/js/swiper.min.js"></script>
<script>
    var swiper = new Swiper('.home-banner', {
      autoHeight: true, //enable auto height
      spaceBetween: 1,
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
      
    });
    var swiper = new Swiper('.home-banner2', {
      autoHeight: true, //enable auto height
      spaceBetween: 1,
      observer:true, 
	  observeParents:true 
      
    });
</script> 
<script>
	$(document).ready(function() {  
		//加载优质展商
		LoadQualityExhibitors(0,12);
		//加载优质展品
		LoadQualityProducts(0,0,10); 
		
	  var $wrapper = $('.tab-wrapper'),
	      $allTabs = $wrapper.find('.tab-content > li'),
	      $tabMenu = $wrapper.find('.tab-menu li'),
	      $line = $('<div class="line"></div>').appendTo($tabMenu);
	  
	  $allTabs.not(':first-of-type').hide();  
	  $tabMenu.filter(':first-of-type').find(':first').width('100%')
	  
	  $tabMenu.each(function(i) {
	    $(this).attr('data-tab', 'tab'+i);
	  });
	  
	  $allTabs.each(function(i) {
	    $(this).attr('data-tab', 'tab'+i);
	  });
	  
	  $tabMenu.on('mouseover', function() {
	    
	    var dataTab = $(this).data('tab'),
	        $getWrapper = $(this).closest($wrapper);
	    
	    $getWrapper.find($tabMenu).removeClass('active');
	    $(this).addClass('active');
	    
	    $getWrapper.find('.line').width(0);
	    $(this).find($line).animate({'width':'100%'}, 'fast');
	    $getWrapper.find($allTabs).hide();
	    $getWrapper.find($allTabs).filter('[data-tab='+dataTab+']').show();
	  });

	});
</script> 

<script src="/online/loadSelect.js"></script>
<script>
	LoadOnlineActivity('${firstDate}');
	loadZiDianByParentID_Menu();
	LoadOnlineInquiry(0);
	LoadKaiMuShi('${kms.basicPicture}','${kms.basicIntro}','${kms.basicContent}','${kms.basicOperator}',${kms.status},'${kms.liveaddress}');
</script>
</body>
</html>
	<script>
	$("#shipin").html("");
	var player =  new TcPlayer('shipin', {
	"m3u8": "${kms.basicIntro}",
	//"flv": "http://play.hljlbh.org.cn/live/test.flv", 
	//"autoplay" : true,
	"poster" : "${kms.basicPicture}",
	"width" :  '100%',
	"height" : '100%'
	});
	</script>
