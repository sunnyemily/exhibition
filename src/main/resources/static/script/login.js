if(window.top !== window.self){ window.top.location = window.location;}
if(window.location.pathname.lastIndexOf("login.html")<0){
	window.location.href="/"+language+"/"+type+"-login.html";
}
layui.use('form', function(){
  var form = layui.form;
  //监听提交
  form.on('submit(formDemo)', function(data){
	$.post("/mlogin", data.field,
	function(res){
		if(res.status==1){//登陆成功
		var url = "/"+language+"/"+type+"-";
			if(res.result.memberType==7){
				url = url + "online.html";
			}
			else{
				url = url + "member.html";

			}
			window.location.href=url; 
		}
		else if(res.status==7){
			layer.confirm(activationInfo, {icon: 3, title:countersign}, function(index){
			  activeAccount(res.result.memberId,res.result.memberSessionId);
			layer.close(index);
			});
		}
		else{
			alert(res.msg);
			refreshValidateCode();
		}
	});
    return false;
  });
function activeAccount(memberId,sessionId){
	//1.验证手机号码是否已注册，并发送验证码
	var index = layer.load(activateAccount, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
	$.post("/activateHistoryAccount",{memberId:memberId,sessionId:sessionId},function(result) {
        layer.close(index);
        if (result.status == 1) {
        	layer.confirm(login, {
                btn: [register, goPage] //按钮
            }, function() {
                location.href = "/"+language+"/"+type+"-login.html";
            }, function() {
                location.href = "/index.html";
            });
        }
		else{
		        layer.msg(result.msg);
		}
    });
}
  form.verify({
	  memberUsername: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
		      return user;
		    }
		    if(/(^\_)|(\__)|(\_+$)/.test(value)){
		      return user2;
		    }
		    if(/(^-?[0-9][0-9]*(.[0-9]+)?)$/ .test(value)){
		      return user3;
		    }
		  }
		  //我们既支持上述函数式的方式，也支持下述数组的形式
		  //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
		  ,password: [
		    /^[\S]{6,12}$/
		    ,password
		  ] 
		  ,memberActivationCode:function(value, item){
			    if(value.trim().length!=5){
				      return regainException
				    }
				  }
		});
});
function refreshValidateCode(){
  	$("#validateImage").attr("src","/getVerifyCode?"+Math.random());
  }
