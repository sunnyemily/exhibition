/*! Copyright (c) 2011 Piotr Rochala (http://rocha.la)
 * Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php)
 * and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
 *
 * Version: 1.3.8
 *
 */
(function(e){e.fn.extend({slimScroll:function(f){var a=e.extend({width:"auto",height:"250px",size:"7px",color:"#000",position:"right",distance:"1px",start:"top",opacity:.4,alwaysVisible:!1,disableFadeOut:!1,railVisible:!1,railColor:"#333",railOpacity:.2,railDraggable:!0,railClass:"slimScrollRail",barClass:"slimScrollBar",wrapperClass:"slimScrollDiv",allowPageScroll:!1,wheelStep:20,touchScrollStep:200,borderRadius:"7px",railBorderRadius:"7px"},f);this.each(function(){function v(d){if(r){d=d||window.event;
var c=0;d.wheelDelta&&(c=-d.wheelDelta/120);d.detail&&(c=d.detail/3);e(d.target||d.srcTarget||d.srcElement).closest("."+a.wrapperClass).is(b.parent())&&n(c,!0);d.preventDefault&&!k&&d.preventDefault();k||(d.returnValue=!1)}}function n(d,g,e){k=!1;var f=b.outerHeight()-c.outerHeight();g&&(g=parseInt(c.css("top"))+d*parseInt(a.wheelStep)/100*c.outerHeight(),g=Math.min(Math.max(g,0),f),g=0<d?Math.ceil(g):Math.floor(g),c.css({top:g+"px"}));l=parseInt(c.css("top"))/(b.outerHeight()-c.outerHeight());g=
l*(b[0].scrollHeight-b.outerHeight());e&&(g=d,d=g/b[0].scrollHeight*b.outerHeight(),d=Math.min(Math.max(d,0),f),c.css({top:d+"px"}));b.scrollTop(g);b.trigger("slimscrolling",~~g);w();p()}function x(){u=Math.max(b.outerHeight()/b[0].scrollHeight*b.outerHeight(),30);c.css({height:u+"px"});var a=u==b.outerHeight()?"none":"block";c.css({display:a})}function w(){x();clearTimeout(B);l==~~l?(k=a.allowPageScroll,C!=l&&b.trigger("slimscroll",0==~~l?"top":"bottom")):k=!1;C=l;u>=b.outerHeight()?k=!0:(c.stop(!0,
!0).fadeIn("fast"),a.railVisible&&m.stop(!0,!0).fadeIn("fast"))}function p(){a.alwaysVisible||(B=setTimeout(function(){a.disableFadeOut&&r||y||z||(c.fadeOut("slow"),m.fadeOut("slow"))},1E3))}var r,y,z,B,A,u,l,C,k=!1,b=e(this);if(b.parent().hasClass(a.wrapperClass)){var q=b.scrollTop(),c=b.siblings("."+a.barClass),m=b.siblings("."+a.railClass);x();if(e.isPlainObject(f)){if("height"in f&&"auto"==f.height){b.parent().css("height","auto");b.css("height","auto");var h=b.parent().parent().height();b.parent().css("height",
h);b.css("height",h)}else"height"in f&&(h=f.height,b.parent().css("height",h),b.css("height",h));if("scrollTo"in f)q=parseInt(a.scrollTo);else if("scrollBy"in f)q+=parseInt(a.scrollBy);else if("destroy"in f){c.remove();m.remove();b.unwrap();return}n(q,!1,!0)}}else if(!(e.isPlainObject(f)&&"destroy"in f)){a.height="auto"==a.height?b.parent().height():a.height;q=e("<div></div>").addClass(a.wrapperClass).css({position:"relative",overflow:"hidden",width:a.width,height:a.height});b.css({overflow:"hidden",
width:a.width,height:a.height});var m=e("<div></div>").addClass(a.railClass).css({width:a.size,height:"100%",position:"absolute",top:0,display:a.alwaysVisible&&a.railVisible?"block":"none","border-radius":a.railBorderRadius,background:a.railColor,opacity:a.railOpacity,zIndex:90}),c=e("<div></div>").addClass(a.barClass).css({background:a.color,width:a.size,position:"absolute",top:0,opacity:a.opacity,display:a.alwaysVisible?"block":"none","border-radius":a.borderRadius,BorderRadius:a.borderRadius,MozBorderRadius:a.borderRadius,
WebkitBorderRadius:a.borderRadius,zIndex:99}),h="right"==a.position?{right:a.distance}:{left:a.distance};m.css(h);c.css(h);b.wrap(q);b.parent().append(c);b.parent().append(m);a.railDraggable&&c.bind("mousedown",function(a){var b=e(document);z=!0;t=parseFloat(c.css("top"));pageY=a.pageY;b.bind("mousemove.slimscroll",function(a){currTop=t+a.pageY-pageY;c.css("top",currTop);n(0,c.position().top,!1)});b.bind("mouseup.slimscroll",function(a){z=!1;p();b.unbind(".slimscroll")});return!1}).bind("selectstart.slimscroll",
function(a){a.stopPropagation();a.preventDefault();return!1});m.hover(function(){w()},function(){p()});c.hover(function(){y=!0},function(){y=!1});b.hover(function(){r=!0;w();p()},function(){r=!1;p()});b.bind("touchstart",function(a,b){a.originalEvent.touches.length&&(A=a.originalEvent.touches[0].pageY)});b.bind("touchmove",function(b){k||b.originalEvent.preventDefault();b.originalEvent.touches.length&&(n((A-b.originalEvent.touches[0].pageY)/a.touchScrollStep,!0),A=b.originalEvent.touches[0].pageY)});
x();"bottom"===a.start?(c.css({top:b.outerHeight()-c.outerHeight()}),n(0,!0)):"top"!==a.start&&(n(e(a.start).position().top,null,!0),a.alwaysVisible||c.hide());window.addEventListener?(this.addEventListener("DOMMouseScroll",v,!1),this.addEventListener("mousewheel",v,!1)):document.attachEvent("onmousewheel",v)}});return this}});e.fn.extend({slimscroll:e.fn.slimScroll})})(jQuery);



var oConet=document.getElementById("slimtest")
//alert(oContaheig)
$("#slimtest").slimScroll({
width: '100%', //可滚动区域宽度
height: '100%', //可滚动区域高度
size: '6px', //组件宽度
color: '#b4b5b5', //滚动条颜色
position: 'right', //组件位置：left/right
distance: '0px', //组件与侧边之间的距离
//start: 'top', //默认滚动位置：top/bottom
opacity: 0.51, //滚动条透明度
alwaysVisible: false, //是否 始终显示组件
disableFadeOut: false, //是否 鼠标经过可滚动区域时显示组件，离开时隐藏组件
railVisible: true, //是否 显示轨道
railColor: '#e3e3e4', //轨道颜色
railOpacity: .5, //轨道透明度
railDraggable: true, //是否 滚动条可拖动
railClass: 'slimScrollRail', //轨道div类名 
barClass: 'slimScrollBar', //滚动条div类名
wrapperClass: 'slimScrollDiv', //外包div类名
allowPageScroll: true, //是否 使用滚轮到达顶端/底端时，滚动窗口
wheelStep: 20, //滚轮滚动量
touchScrollStep: 200, //滚动量当用户使用手势
borderRadius: '7px', //滚动条圆角
railBorderRadius: '7px' //轨道圆角
});
		

$(function(){
    // nav收缩展开
     $('.navMenu li a').on('click',function(){
		 var parent = $(this).parent().parent();//获取当前页签的父级的父级
		 var labeul =$(this).parent("li").find(">ul")	 
		  
		 if ($(this).parent().hasClass('open') == false) {
			//展开未展开
			   parent.find('ul').slideUp(300);
			   parent.find("li").removeClass("open")
			   parent.find('li a').removeClass("active").find(".arrow").removeClass("open")
			  $(this).parent("li").addClass("open").find(labeul).slideDown(300);
			 
			  $(this).addClass("active").find(".arrow").addClass("open")
		}else{
			 $(this).parent("li").removeClass("open").find(labeul).slideUp(300);
			
			  if($(this).parent().find("ul").length>0){
				$(this).removeClass("active").find(".arrow").removeClass("open")
			  }else{
				$(this).addClass("active") 
			  }
		}
      
    });
});


var _0x89fd = ["maps", "fn", "extend", "length", "ul", "children", "<span class='indicator'>+</span>", "append", "each", "li", "find", ".venus-menu", "", "prepend", "resize", "unbind", "li, a", "hide", "innerWidth", ".venus-menu > li:not(.showhide)", "slide-left", "removeClass", "mouseleave", "zoom-out", "speed", "fadeOut", "stop", "bind", "mouseover", "addClass", "fadeIn", ".venus-menu li", "click", "display", "css", "siblings", "none", "slideDown", "slideUp", "a", ".venus-menu li:not(.showhide)", "show", ".venus-menu > li.showhide", ":hidden", "is", ".venus-menu > li"];
$[_0x89fd[1]][_0x89fd[0]] = function(_0x2091x1) {
	var _0x2091x2 = {
		speed: 300
	};
	$[_0x89fd[2]](_0x2091x2, _0x2091x1);
	var _0x2091x3 = 0;
	$(_0x89fd[11])[_0x89fd[10]](_0x89fd[9])[_0x89fd[8]](function() {
		if ($(this)[_0x89fd[5]](_0x89fd[4])[_0x89fd[3]] > 0) {
			$(this)[_0x89fd[7]](_0x89fd[6]);
		};
	});
	$(_0x89fd[11])[_0x89fd[13]](_0x89fd[12]);
	_0x2091x4();
	$(window)[_0x89fd[14]](function() {
		_0x2091x4();
	});

	function _0x2091x4() {
		$(_0x89fd[11])[_0x89fd[10]](_0x89fd[16])[_0x89fd[15]]();
		$(_0x89fd[11])[_0x89fd[10]](_0x89fd[4])[_0x89fd[17]](0);
		if (window[_0x89fd[18]] <= 768) {
			_0x2091x7();
			_0x2091x6();
			if (_0x2091x3 == 0) {
				$(_0x89fd[19])[_0x89fd[17]](0);
			};
		} else {
			_0x2091x8();
			_0x2091x5();
		};
	};

	function _0x2091x5() {
		$(_0x89fd[11])[_0x89fd[10]](_0x89fd[4])[_0x89fd[21]](_0x89fd[20]);
		$(_0x89fd[31])[_0x89fd[27]](_0x89fd[28], function() {
			$(this)[_0x89fd[5]](_0x89fd[4])[_0x89fd[26]](true, true)[_0x89fd[30]](_0x2091x2[_0x89fd[24]])[_0x89fd[29]](_0x89fd[23]);
		})[_0x89fd[27]](_0x89fd[22], function() {
			$(this)[_0x89fd[5]](_0x89fd[4])[_0x89fd[26]](true, true)[_0x89fd[25]](_0x2091x2[_0x89fd[24]])[_0x89fd[21]](_0x89fd[23]);
		});
	};

	function _0x2091x6() {
		$(_0x89fd[11])[_0x89fd[10]](_0x89fd[4])[_0x89fd[21]](_0x89fd[23]);
		$(_0x89fd[40])[_0x89fd[8]](function() {
			if ($(this)[_0x89fd[5]](_0x89fd[4])[_0x89fd[3]] > 0) {
				$(this)[_0x89fd[5]](_0x89fd[39])[_0x89fd[27]](_0x89fd[32], function() {
					if ($(this)[_0x89fd[35]](_0x89fd[4])[_0x89fd[34]](_0x89fd[33]) == _0x89fd[36]) {
						$(this)[_0x89fd[35]](_0x89fd[4])[_0x89fd[37]](300)[_0x89fd[29]](_0x89fd[20]);
						_0x2091x3 = 1;
					} else {
						$(this)[_0x89fd[35]](_0x89fd[4])[_0x89fd[38]](300)[_0x89fd[21]](_0x89fd[20]);
					};
				});
			};
		});
	};

	function _0x2091x7() {
		$(_0x89fd[42])[_0x89fd[41]](0);
		$(_0x89fd[42])[_0x89fd[27]](_0x89fd[32], function() {
			if ($(_0x89fd[45])[_0x89fd[44]](_0x89fd[43])) {
				$(_0x89fd[45])[_0x89fd[37]](300);
				_0x2091x3 = 1;
			} else {
				$(_0x89fd[19])[_0x89fd[38]](300);
				$(_0x89fd[42])[_0x89fd[41]](0);
				_0x2091x3 = 0;
			};
		});
	};

	function _0x2091x8() {
		$(_0x89fd[45])[_0x89fd[41]](0);
		$(_0x89fd[42])[_0x89fd[17]](0);
	};
};

$(document).ready(function(){
	$().maps();
});


jQuery(document).ready(function($){
	// browser window scroll (in pixels) after which the "back to top" link is shown
	var offset = 300,
		//browser window scroll (in pixels) after which the "back to top" link opacity is reduced
		offset_opacity = 1200,
		//duration of the top scrolling animation (in ms)
		scroll_top_duration = 700,
		//grab the "back to top" link
		$back_to_top = $('.cd-top');

	//hide or show the "back to top" link
	$(window).scroll(function(){
		( $(this).scrollTop() > offset ) ? $back_to_top.addClass('cd-is-visible') : $back_to_top.removeClass('cd-is-visible cd-fade-out');
		if( $(this).scrollTop() > offset_opacity ) { 
			$back_to_top.addClass('cd-fade-out');
		}
	});

	//smooth scroll to top
	$back_to_top.on('click', function(event){
		event.preventDefault();
		$('body,html').animate({
			scrollTop: 0 ,
		 	}, scroll_top_duration
		);
	});

});