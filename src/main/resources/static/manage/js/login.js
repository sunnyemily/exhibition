/**
 * 与服务器交互文件
 */
$(document).ready(function(){
	$(".loading").css("top",$(window).height() / 2 - 30);
	
	/**
	 * 登陆事件
	 */
	$("#login").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		var vCode = $("#vCode").val();
		if(username.length==0||password.length==0||vCode.length==0){
			//处理
			alert('用户名、密码和验证码不可为空');
		}
		else{
			displayLoading();
			$.post("/login", { username: username, password: password ,vCode:vCode},
			function(data){
				if(data.status==1){//登陆成功
					window.location.href="/manage/index.html"; 
				}
				else{
					alert(data.msg);
					refreshValidateCode();
				}
				hiddenLoading();
			});
			return false;
		}
	});
});
var util = layui.util;
$("#btnActivation").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		if(username.length==0||password.length==0){
			layer.alert('请正确输入用户名、密码', {icon: 5},function(index){
				layer.close(index);
			})
		}
		//2.验证手机号码是否已注册，并发送验证码
		var index = layer.load('正在发送验证码', {
            icon: 1,
            shade: [0.8, '#000'] //0.1透明度的白色背景
        });
		$.post("/sendConsolePhoneCode",{username: username, password: password},function(result) {
            layer.close(index);
            if (result.status == 1) {
            	countDown();
            }
            layer.msg(result.msg);
        });
		
});
function countDown(){
   	 var endTime = new Date().getTime();
  	 var startTime = endTime;
  	 endTime = endTime + 1000 * 60;
	 $("#btnActivation").attr("disabled","disabled");
	 $("#btnActivation").addClass("layui-btn-disabled");
	 $("#btnActivation").html("60秒后重新获取");
	 util.countdown(endTime, startTime, function(date, startTime, timer){
		 if(date[3]!=0){
		    var str = date[3] + '秒后重新获取';
		    $("#btnActivation").html(str);
		 }
		 if(date[3]==0&&$("#btnActivation").html()!="60秒后重新获取"){
			 $("#btnActivation").removeAttr("disabled");
			 $("#btnActivation").removeClass("layui-btn-disabled");
			 $("#btnActivation").html("获取验证码");

		 }
	  });
}
function displayLoading(){
	$(".layer").css("display","block");
	$(".loading").css("display","block");
}
function hiddenLoading(){
	$(".layer").css("display","none");
	$(".loading").css("display","none");
}
function refreshValidateCode(){
	$("#validateImage").attr("src","/getVerifyCode?"+Math.random());
}