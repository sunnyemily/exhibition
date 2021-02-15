var form;
var areas;
var resetType = "phone";
layui.use('form', function() {
    form = layui.form;
    form.render();
});
form.on('radio(resetType)', function(data){
	if(data.value=="phone"){
		$("#phone").css("display","block");
		$("#email").css("display","none");
	}
	else{
		$("#phone").css("display","none");
		$("#email").css("display","block");
	}
	resetType = data.value;
	console.info(resetType);
});  
form.on('submit(formDemo)', function(data) {
	if(resetType == "phone"&& $("input[name=phone]").val()==""){
		layer.msg(phoneNoEmpty, {icon: 2});
		return false;
	}
	if(resetType == "email"&&$("input[name=email]").val()==""){
		layer.msg(emillNoEmpty, {icon: 2});
		return false;
	}
    var index = layer.load(submitInformation, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
   $.post("/api/common/forgot", data.field, function(result) {
        layer.close(index);
        if (result.status == 1) {
            layer.confirm(result.msg, {
                btn: [loginButtonTitle, indexButtonTitle] //按钮
            }, function() {
                location.href = "/"+language+"/"+type+"-login.html";
            }, function() {
                location.href = "/index.html";
            });
        }
        else {
            layer.msg(result.msg);
        }
    });
    return false;
});