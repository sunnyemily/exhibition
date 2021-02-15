
var memberId = window.parent.member.memberId;
var sessionId = window.parent.member.memberSessionId;
$(document).ready(function(){
	//一、根据窗口大小动态调整高度
	resize();
	//二、为左侧菜单添加事件
	$(".left-item").click(function(){
		//1.先判断是否有下级菜单，如果有，则进行开关操作
		if($(this).next().hasClass("two-level")){
			$(this).next().slideToggle();
			$(this).find(".jt").toggleClass("fa-chevron-down");
			return;
		}
		//2.如果没有
		var page = $(this).data("page");
		var url = "/"+language+"/"+type+"-"+page+".html";
		//2.1报表采用弹出形式
		if(page=="report"){
			var index = layer.open({
				title:printing,
				  type: 2,
				  shade: false,
				  area: '50%,100%',
				  maxmin: true,
				  content: url,
				  zIndex: layer.zIndex, //重点1
				  success: function(layero){
				  layer.setTop(layero); //重点2
				  layer.full(index);
				  return;
				  }
				});  
			return;
		}
		if(page=="exit"){
			logout();
			return;
		}
		//2.2其他则进行页面跳转
		$("iframe").attr("src",url);
	});
	//三、首个菜单默认打开
	openFirstMenu();
	validateApplyInfo();
});
window.onresize = resize;
function resize(){
	$(".member-left,.member-right").css("height",($(window).height()-71)+"px");
	//$(".member-right").css("height",($(window).height()-71)+"px");
}
function openFirstMenu(){
	if($(".left-item").first().next().hasClass("two-level"))
	{
		$(".left-item").first().click();
	}
}
function logout(){
	var index = layer.load(2, {time: 10*1000}); //又换了种风格，并且设定最长等待10秒 
	var url = "/mlogout";
	$.post(url,
	function(data){
		if(data.status==5||data.status==1){
			window.location.href=window.location.href;
		}
		else{
			alert(data.msg, {icon: 6});
		}
		layer.close(index);
	});
}
/**
 * 验证申请信息，如果有没有
 */
function validateApplyInfo(){
		//尚未申请信息
		if(!hasLogo){		
			layer.confirm(noLogo, function(index){
			  layer.close(index);
			  $("iframe").attr("src","/"+language+"/"+type+"-company.html");
			});
			return;
		}
		if(!hasPicture){	
			layer.confirm(noSurfacePlot, function(index){
			  layer.close(index);
			  $("iframe").attr("src","/"+language+"/"+type+"-company.html");
			});
			return;
		}
		if(productCount==0){
			layer.confirm(noProduct, function(index){
			  layer.close(index);
			  $("iframe").attr("src","/"+language+"/"+type+"-product.html");
			});
			return;
		}
}