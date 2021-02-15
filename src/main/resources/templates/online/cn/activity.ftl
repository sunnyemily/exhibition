<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>绿博会线上展厅</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link href="/online/cn/css/nav.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/font-awesome.4.6.0.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/swiper.min.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/style-activity.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/media.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="/online/cn/css/jquery.mCustomScrollbar.css">


<script src="/online/cn/js/jquery.min.js" type="text/javascript"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/online/cn/js/VerticalMenuJs.js" type="text/javascript"></script>
<script type="text/javascript" src="/online/cn/js/scroll.js" ></script>
<script src="/online/cn/js/jquery.easydropdown.js"></script>
<script src="/online/cn/js/Sweefty.js" type="text/javascript"></script>
<script src="/online/cn/js/moaModal.minified.js" type="text/javascript"></script>
<script type="text/javascript" src="/online/common.js" ></script>
<script type="text/javascript">
$(function(){
	//一次纵向滚动一个
	$('#marquee').kxbdSuperMarquee({
		distance:57,
		time:1,
		isAuto:false,
		btnGo:{up:'#goU',down:'#goD'},
		direction:'up'
	});	
});
</script>

<!--视频播放器-->
<link rel="stylesheet" href="/online/cn/video/css/ckin.css">
</head>

<body>
<!--移动端菜单-->


<div class="box">
	<div class="home-bg">
		<div class="home-zb-left">
			<h3 class="h-title">展会日程</h3>
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
			<h3 class="h-title" id="h3biaoti">推荐活动</h3>
			<div class="zb-play-box">
				<div id="shipin" style="width:100%; height:auto;"></div>
				<img id="tupian" height="413px" style="display:none" src="">
			</div>
			<div class="zb-play-title">				
				<p id="hdintro"></p>
			</div>
		</div>
	</div>
</div>


<input type="hidden" value="0" id="mid">

<!--视频播放器-->
<script src="https://imgcache.qq.com/open/qcloud/video/vcplayer/TcPlayer-2.3.2.js" charset="utf-8"></script>

<!--滚动条js-->
<script src="/online/cn/js/jquery.mCustomScrollbar.min.js"></script>	
<script>
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
		LoadQualityProducts(0,0,8); 
		
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
	  
	  $tabMenu.on('click', function() {
	    
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
<style>
.home-zb-left {
    width: 558px;
}
.home-zb-left .zb-bg {
    background: url(images/bg4.png) no-repeat;
    background-size: 100% 100%;
}
.box{
width:1380px;
}
</style>
</body>
</html>
	<script>
	var player =  new TcPlayer('shipin', {
	"m3u8": "${kms.liveaddress}",
	"flv": "${kms.liveaddress}", 
	//"autoplay" : true,
	"poster" : "${kms.basicPicture}",
	"width" :  '100%',
	"height" : '100%'
	});
	</script>