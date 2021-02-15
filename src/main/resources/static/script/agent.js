var form;
var areas;
layui.use('form', function() {
    form = layui.form;
	form.render();
});

var clientWidth = document.documentElement.clientWidth;
var clientHeight = document.documentElement.clientHeight;
$(document).ready(function(){
	if($("#imagepath").val()!=""){
		$("#uploadPictrue").parent().css("display","none");
		$("#imagepath").parent().parent().css("display","inline-block");
		$("#preimagepath").attr("src",$("#imagepath").val());
	}
	//2.图片和文件查看功能
	$(".fa-eye").on("click",function(){
		if($(this).parent().prev().hasClass("file")){
			window.open($("#applyFile").val());
			return;
		}
		//debugger;
		var src = $(this).parent().prev().attr("src");　
		var i = new Image();
		i.src = src;
		var rw,rh;
		if (typeof i.naturalWidth == "undefined") {
		　　rw = i.width;
		　　rh = i.height;
		}
		else {
		　　rw = i.naturalWidth;
		　　rh = i.naturalHeight;
		console.info(rw + ":" + rh);
		}
		layer.open({type:2,content:src,area: ['800px','100%']});
	});
	}
);
//1.企业是否已注册
//2.后期需要验证用户名、手机号、邮箱是否已存在，存在需重新填写
//监听提交
form.on('submit(formDemo)', function(data) {
    var index = layer.load(submitInfo, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
    $.post("/api/agent/update", data.field, function(result) {
        layer.close(index);
        if (result.status == 1) {
            layer.msg(result.msg);
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
            return usernameValidateTips;
        }
        if (value.length < 4 || value.length > 30) {
            return usernameValidateTips2;
        }
    },
    password: [
        /^[\S]{6,12}$/, passwordValidateTips
    ],
    passwordEqual: function(value, item) {
        if (value != $("input[name=memberPassword]").val()) {
            return passwordValidateTips2;
        }
    }
});
$("#btnimagepathCropper").on("click",function(){
	var w = 390;
	var h = 487;
	var index = layer.open({
	  title:pictureToolTitle,
	  type: 2, 
	  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=imagepath&pre=prebuslicensepath','no'],
	  area:[clientWidth+"px",clientHeight+"px"]
	});
	layer.full(index);		
});