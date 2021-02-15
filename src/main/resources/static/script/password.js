var form;
layui.use('form', function() {
	form = layui.form;
	//监听提交
	form.on('submit(formDemo)', function(data) {
		var index = layer.load(submitInformation, {
			icon : 1,
			shade : [ 0.8, '#000' ]
		//0.1透明度的白色背景
		});
		$.post("/modifyPassword", data.field, function(result) {
			layer.close(index);
			if (result.status == 1) {
				layer.alert(loginAgain, function() {
					location.href = type+"-login.html";
				});

			} else {
				layer.msg(result.msg);
				refreshValidateCode();
			}
		});
		return false;
	});
	form.verify({
		password : [ /^[\S]{6,12}$/, passwordValidateTips],
		passwordEqual : function(value, item) {
			if (value != $("input[name=newPassword]").val()) {
				return passwordValidateTips2;
			}
		}
	});
});
function refreshValidateCode() {
	$("#validateImage").attr("src", "/getVerifyCode?" + Math.random());
}