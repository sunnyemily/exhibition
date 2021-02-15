var form;
var areas;
layui.use('form', function() {
    form = layui.form;
	//获取地区信息
	$.get("/getCountryArea", function(r){
		areas = r;
		loadCountry();
		form.on('select(country)', function(data){
			loadProvince();
		});
		form.on('select(province)', function(data){
			loadCity();
		});
	});
    form.render();
});

//1.企业是否已注册
//2.后期需要验证用户名、手机号、邮箱是否已存在，存在需重新填写
//监听提交
form.on('submit(formDemo)', function(data) {
	if(data.field.memberType==7&&data.field.tradinggroupid==""){
		alert(onlineSelectTips);
		return false;
	}
    var index = layer.load(registSubmitLoadingTips, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
    $.post("/router/cn/regist?token="+token, data.field, function(result) {
        layer.close(index);
        if (result.status == 1) {
            layer.confirm(registSuccessTips, {
                btn: [loginButtonTitle] //按钮
            });
        }
        else if(result.status==7){
        		layer.confirm(result.msg, {icon: 3, title:activeConfirmTips}, function(index){
        		  //do something
        		  activeAccount(result.result.id);
        		  layer.close(index);
        		});
        }
        else {
            layer.msg(result.msg);
            //refreshValidateCode();
        }
    });
    return false;
});
form.verify({
    username: function(value, item) { //value：表单的值、item：表单的DOM对象
        var reg =/^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;  
        if (!reg.test(value)) {
            return userNameValidateTips;
        }
        if (value.length < 4 || value.length > 30) {
            return userNameLengthTips;
        }
    },
    password: [
        /^[\S]{6,12}$/, passwordValidateTips
    ],
    passwordEqual: function(value, item) {
        if (value != $("input[name=memberPassword]").val()) {
            return passwordCompareTips;
        }
    }
});
var util = layui.util;
$("#btnActivation").click(function(){
		if($("input[name=chinesename]").val().length==0){
			layer.alert(companyNameValidateTips, {icon: 5},function(index){
				layer.close(index);
				$("input[name=chinesename]").focus();
			})
		}
		//2.验证手机号码是否已注册，并发送验证码
		var index = layer.load(sendValidateTips, {
            icon: 1,
            shade: [0.8, '#000'] //0.1透明度的白色背景
        });
if(language=="cn"){
	//1.验证手机号码的正确性
	var reg = /^[1][0-9]{10}$/;
	if(!reg.test($("input[name=phone]").val())){
		layer.alert(phoneValidateTips, {icon: 5},function(index){
			layer.close(index);
			$("input[name=phone]").focus();
		});
		return;
	}
	$.post("/router/cn/sendPhoneCode?token="+token,{phone:$("input[name=phone]").val(),companyName:$("input[name=chinesename]").val(),sessionId:$("input[name=session]").val()},function(result) {
        layer.close(index);
        if (result.status == 1) {
        	countDown();
        }
        layer.msg(result.msg);
    });
}
else{
	//1.验证邮箱地址的正确性
	var reg = /^[A-Za-z0-9]+([_\.][A-Za-z0-9]+)*@([A-Za-z0-9\-]+\.)+[A-Za-z]{2,6}$/;
	if(!reg.test($("input[name=email]").val())){
		layer.alert(emailValidateTips, {icon: 5},function(index){
			layer.close(index);
			$("input[name=email]").focus();
		});
		return;
	}
	$.post("/router/cn/sendEmailCode?token="+token,{email:$("input[name=email]").val(),companyName:$("input[name=chinesename]").val(),sessionId:$("input[name=session]").val()},function(result) {
        layer.close(index);
        if (result.status == 1) {
        	countDown();
        }
        layer.msg(result.msg);
    });
}
		//调用完发送短信接口后，执行以下倒计时命令
		
});
function countDown(){
   	 var endTime = new Date().getTime();
  	 var startTime = endTime;
  	 endTime = endTime + 1000 * 60;
	 $("#btnActivation").attr("disabled","disabled");
	 $("#btnActivation").addClass("layui-btn-disabled");
	 $("#btnActivation").html("60"+secondCountTips);
	 util.countdown(endTime, startTime, function(date, startTime, timer){
		 if(date[3]!=0){
		    var str = date[3] + secondCountTips;
		    $("#btnActivation").html(str);
		 }
		 if(date[3]==0&&$("#btnActivation").html()!="60"+secondCountTips){
			 $("#btnActivation").removeAttr("disabled");
			 $("#btnActivation").removeClass("layui-btn-disabled");
			 $("#btnActivation").html(getValidateCodeTitle);

		 }
	  });
}
//此方法作废
function activeAccount(companyId){
	//1.验证手机号码是否已注册，并发送验证码
	var index = layer.load(registLoadingTitle, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
	$.post("/activateHistoryAccount",{companyId:companyId,sessionId:$("input[name=session]").val()},function(result) {
        layer.close(index);
        if (result.status == 1) {
        	layer.confirm(activateSuccessTips, {
                btn: [loginButtonTitle, indexButtonTitle] //按钮
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
//验证企业名称
$("#btnValidate").click(function(){
	if($("input[name=chinesename]").val()==""){
		layer.msg(companyNoNullValidateTips);
		return;
	}
	var index = layer.load(companyValidateTips, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
	$.post("/api/common/company/validate",{companyName:$("input[name=chinesename]").val(),memberType:type},function(result) {
        layer.close(index);	
		//首先隐藏全部内容	
    	$(".layui-tab-content .layui-tab-item").each(function(){
			$(this).removeClass("layui-show");
		});
        if (result.status == 1) {//可正常注册
			$("input[name=validChinesename]").val($("input[name=chinesename]").val());
			$(".layui-tab-content .layui-tab-item").eq(2).addClass("layui-show");
        }
		else if(result.status == 7){//需要验证
			layer.confirm(result.msg, {icon: 3, title:historyValidateTips}, function(index){
			$(".layui-tab-content .layui-tab-item").eq(1).addClass("layui-show");
			  layer.close(index);
			});
		}
		else{//无法正常注册
		     layer.msg(result.msg);
			$(".layui-tab-content .layui-tab-item").eq(0).addClass("layui-show");
		}
	});
	return false;
});
//验证安全
$("#btnSafeValidate").click(function(){
	if($("input[name=testContactperson]").val()==""){
		layer.msg(contactorNoNullTips);
		return;
	}
	/** if((type=="exhibitor"||type=="purchaser")&&$("input[name=testPrincipal]").val()==""){
		layer.msg(responserNoNullTips);
		return;
	} */
	var index = layer.load(comapnyHistoryValidateTips, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
	$.post("/api/common/company/history/validate",{
			chinesename:$("input[name=chinesename]").val(),
			principal:$("input[name=testPrincipal]").val(),
			contactperson:$("input[name=testContactperson]").val()
		},function(result) {
        layer.close(index);	
		//首先隐藏全部内容	
    	$(".layui-tab-content .layui-tab-item").each(function(){
			$(this).removeClass("layui-show");
		});
        if (result.status == 1) {//可正常注册
			layer.alert(historySuccessTips);
			//将历届信息填入表单
			var obj = result.result;
			editValue("form",obj.company);
			if(obj.member){
				editValue("form",obj.member);
			}
			obj = obj.company;
			loadProvince();
			$("select[name=province]").val(obj["province"]);
			loadCity();
			$("select[name=city]").val(obj["city"]);
			form.render();
			
			$("input[name=validChinesename]").val($("input[name=chinesename]").val());
			//显示表单
			$(".layui-tab-content .layui-tab-item").eq(2).addClass("layui-show");
			$("#isActive").val("1");
        }
		else{//无法正常注册
		     layer.msg(result.msg);
			$(".layui-tab-content .layui-tab-item").eq(1).addClass("layui-show");
		}
	});	
	return false;	
})
form.on('radio(exhibitionType)', function(data){
	if(data.value==1){
		$("input[name=memberType]").val(7);
		layer.alert(onlineOnlyTips);
		$("#exhibitionnArea").css("display","block");
	}
	else{
		$("input[name=memberType]").val(2);
		$("#exhibitionnArea").css("display","none");
	}
});