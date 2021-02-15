var form;
var areas;
var isCanEdit = $("#edit").val();

var clientWidth = document.documentElement.clientWidth;
var clientHeight = document.documentElement.clientHeight;
layui.use('form', function() {
    form = layui.form;
	//获取地区信息
	layer.load(1);
	$.get("/getCountryArea", function(r){
		areas = r;
		loadCountry();
		form.on('select(country)', function(data){
			loadProvince();
		});
		form.on('select(province)', function(data){
			loadCity();
		});
		loadCompany();
	});
});
function loadCompany(){
	$.get("/api/company/get", function(result){
		var obj = result.result.company;
		layer.closeAll();
		$("#editform input[type=hidden]").each(function () {
			var name = $(this).attr("name");
			$(this).val(obj[name]);
		});
		$("#editform input[type=text]").each(function () {
			var name = $(this).attr("name");
			$(this).val(obj[name]);
		});
		$("#editform textarea").each(function () {
			var name = $(this).attr("name");
			$(this).val(obj[name]);
		});
		$("#editform select").each(function () {
			var name = $(this).attr("name");
			$(this).val(obj[name]);
		});
		$("#editform input[type=checkbox]").each(function () {
			var name = $(this).attr("name").replace("chk", "");
			if (!obj[name]) {
				return true;
			}
			var arr = obj[name].split(',');
			for (i = 0; i < arr.length; i++) {
				if (arr[i] == $(this).val())
					$(this).prop("checked", true);
			}
		});
		$("#editform input[type=radio]").each(function () {
			var name = $(this).attr("name").replace("chk", "");
			if (!obj[name]) {
				return true;
			}
			if (obj[name] == $(this).val())
				$(this).prop("checked", true);
		});
		$("select[name=country]").val(obj["country"]);
		loadProvince();
		$("select[name=province]").val(obj["province"]);
		loadCity();
		$("select[name=city]").val(obj["city"]);
    	form.render();	

		$("#precompanylogo").attr("src", $("#companylogo").val());
		$("#prebuslicensepath").attr("src", $("#buslicensepath").val());
		$("#precompanypicture").attr("src", $("#companypicture").val());
		if($("#companyvideo").val()!=""){
			$("#videoContainer").html("<a href='"+$("#companyvideo").val()+"' target='_blank'>"+previourTitle+"</a>");
		}
		else{
			$("#videoContainer").html("");
		}
		
		$(".mutipic").html("");
		var companyPictures = eval(obj["companypictures"]);
		if(companyPictures)
		for(var i = 0;i<companyPictures.length;i++){
			$("#mutiPictureTemplate img").attr("src",companyPictures[i]);
			var templateHtml = $("#mutiPictureTemplate").html();
			var item = $(templateHtml);
			$(".mutipic").append(item);
		}
		//预览和删除添加事件
		addLisenter();
		if(isCanEdit == 1){
			$("input").attr("disabled","disabled");
			$("select").attr("disabled","disabled");
			$("button").attr("disabled","disabled");
    		form.render();	
		}
	});
}
//1.企业是否已注册
//2.后期需要验证用户名、手机号、邮箱是否已存在，存在需重新填写
//监听提交
form.on('submit(formDemo)', function(data) {
	var images = $(".mutipic img");
	var arrImages =new Array();
	for(let i = 0;i<images.length;i++){
		let url = images[i].src;
		url = "/upload" + url.split("upload")[1];
		arrImages.push(url);
	}
	if(arrImages.length==0){
		alert(slideshow1);
		return false;
	}
	if(arrImages.length>3){
		alert(slideshow3);
		return false;
	}
	var index = layer.load(2);
	data.field.companypictures = JSON.stringify(arrImages);
    $.post("/api/company/update", data.field, function(result) {
        layer.close(index);
        if (result.status == 1) {
			layer.alert(result.msg, function(index){
				window.parent.location=window.parent.location;
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
var util = layui.util;
$("#btnActivation").click(function(){
		//1.验证手机号码的正确性
		var reg = /^[1][0-9]{10}$/;
		if(!reg.test($("input[name=phone]").val())){
			layer.alert(phoneNumber, {icon: 5},function(index){
				layer.close(index);
				$("input[name=phone]").focus();
			});
			
			return;
		}
		//2.验证手机号码是否已注册，并发送验证码
		var index = layer.load(verificationCode, {
            icon: 1,
            shade: [0.8, '#000'] //0.1透明度的白色背景
        });
		$.post("/sendPhoneCode",{phone:$("input[name=phone]").val(),sessionId:$("input[name=session]").val()},function(result) {
            layer.close(index);
            if (result.status == 1) {
            		countDown();
            }
            layer.msg(result.msg);
        });
		//调用完发送短信接口后，执行以下倒计时命令
		
});
function countDown(){
   	 var endTime = new Date().getTime();
  	 var startTime = endTime;
  	 endTime = endTime + 1000 * 60;
	 $("#btnActivation").attr("disabled","disabled");
	 $("#btnActivation").addClass("layui-btn-disabled");
	 $("#btnActivation").html(regain60);
	 util.countdown(endTime, startTime, function(date, startTime, timer){
		 if(date[3]!=0){
		    var str = date[3] + regain;
		    $("#btnActivation").html(str);
		 }
		 if(date[3]==0&&$("#btnActivation").html()!=regain60){
			 $("#btnActivation").removeAttr("disabled");
			 $("#btnActivation").removeClass("layui-btn-disabled");
			 $("#btnActivation").html(regainCode);

		 }
	  });
}
function loadCountry(){
	var country = $("select[name=country]");
	country.html("<option value=''>"+qingXuanZe+"</option>");
	for(let index in areas){
		if(areas[index].parentId=="0")
			country.append("<option value='"+areas[index].id+"'>"+areas[index].name+"</option>");
	}
	form.render("select");
}
function loadProvince(){
	var province = $("select[name=province]");
	province.html("<option value=''>"+qingXuanZe+"</option>");
	for(let index in areas){
		if(areas[index].parentId==$("select[name=country]").val())
			province.append("<option value='"+areas[index].id+"'>"+areas[index].name+"</option>");
	}
	form.render("select");
}
function loadCity(){
	var city = $("select[name=city]");
	city.html("<option value=''>"+qingXuanZe+"</option>");
	for(let index in areas){
		if(areas[index].parentId==$("select[name=province]").val())
			city.append("<option value='"+areas[index].id+"'>"+areas[index].name+"</option>");
	}
	if($("select[name=city] option").length==1){
		city.append("<option value='"+$("select[name=province]").val()+"'>"+$("select[name=province]").find("option:selected").text()+"</option>");
	}
	form.render("select");
}

layui.use('upload', function () {
	var upload = layui.upload;
	var uploadCompanyLogo = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#btnCompanylogo' //绑定元素
		, url: '/updatePictureLimit' //上传接
		, data: { width: 200, height: 200, size: 100 }
		, done: function (res) {
			if (res.status == 1) {
				$("#companylogo").val(res.result);
				$("#precompanylogo").attr("src", res.result);
				layer.msg(uploadSuccessful, { icon: 6 });
			}
			else if (res.status == 4) {
				window.location.href = "/error/permission.html";
			}
			else if (res.status == 5) {
				layer.confirm(res.msg, { icon: 3, title: tishi }, function (index) {
					window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
					layer.close(index);
				});
			}
			else {
				layer.msg(res.msg, { shift: 6 });
			}
			layer.closeAll('loading');
		}
		, error: function () {
			//请求异常回调
			layer.msg(interfaceException, { shift: 6 });
			layer.closeAll('loading');
		}
	});
	var uploadIdPictrue = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#idpic' //绑定元素
		, url: '/updatePictureLimit' //上传接
		, data: { width: 800, height: 1060, size: 2048 }
		, done: function (res) {
			if (res.status == 1) {
				$("#buslicensepath").val(res.result);
				$("#prebuslicensepath").attr("src", res.result);
				layer.msg(uploadSuccessful, { icon: 6 });
			}
			else if (res.status == 4) {
				window.location.href = "/error/permission.html";
			}
			else if (res.status == 5) {
				layer.confirm(res.msg, { icon: 3, title: tishi }, function (index) {
					window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
					layer.close(index);
				});
			}
			else {
				layer.msg(res.msg, { shift: 6 });
			}
			layer.closeAll('loading');
		}
		, error: function () {
			//请求异常回调
			layer.msg(interfaceException, { shift: 6 });
			layer.closeAll('loading');
		}
	});
	var uploadCompanyPictrue = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#btnCompanyPicture' //绑定元素
		, url: '/updatePictureLimit' //上传接
		, data: { width: 750, height: 422, size: 2048 }
		, done: function (res) {
			if (res.status == 1) {
				$("#companypicture").val(res.result);
				$("#precompanypicture").attr("src", res.result);
				layer.msg(uploadSuccessful, { icon: 6 });
			}
			else if (res.status == 4) {
				window.location.href = "/error/permission.html";
			}
			else if (res.status == 5) {
				layer.confirm(res.msg, { icon: 3, title:tishi}, function (index) {
					window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
					layer.close(index);
				});
			}
			else {
				layer.msg(res.msg, { shift: 6 });
			}
			layer.closeAll('loading');
		}
		, error: function () {
			//请求异常回调
			layer.msg(interfaceException, { shift: 6 });
			layer.closeAll('loading');
		}
	});
	var uploadCompanyPictrues = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#btnCompanyPictures' //绑定元素
		, url: '/updatePictureLimit' //上传接
		, data: { width: 750, height: 422, size: 2048 }
		, before: function(obj){
			if($(".mutipic .item").length>=6){
		    	layer.msg(picture6);
				throw picture6;
			}
		  }
		, done: function (res) {
			if (res.status == 1) {
				$("#mutiPictureTemplate img").attr("src",res.result);
				var templateHtml = $("#mutiPictureTemplate").html();
				var item = $(templateHtml);
				$(".mutipic").append(item);
				layer.msg(uploadSuccessful, { icon: 6 });
				addLisenter();
			}
			else if (res.status == 4) {
				window.location.href = "/error/permission.html";
			}
			else if (res.status == 5) {
				layer.confirm(res.msg, { icon: 3, title: tishi }, function (index) {
					window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
					layer.close(index);
				});
			}
			else {
				layer.msg(res.msg, { shift: 6 });
			}
			layer.closeAll('loading');
		}
		, error: function () {
			//请求异常回调
			layer.msg(interfaceException, { shift: 6 });
			layer.closeAll('loading');
		}
	});
	
	//上传文件
	var uploadVideoFile = upload.render({
		accept:'file'
		,size:1024*20
		,exts:'mp4'
		,elem: '#btnCompanyVideo' //绑定元素
		,url: '/uploadFile' //上传接口接
		,before: function(obj){
			layer.load(); //上传loading
		}
		,done: function(res){
		  if(res.status==1){
			$("#uploadFile").parent().css("display","none");
			$("#applyFile").parent().parent().css("display","inline-block");
			$("#companyvideo").val(res.result);
			$("#videoContainer").html("<a href='"+res.result+"' target='_blank'>"+previourTitle+"</a>");
			$("#applyFile").next().attr("src",res.result);
			 layer.msg(uploadSuccessful, {icon: 6});
		  }
			else if(res.status==4){
				window.location.href="/error/permission.html";
			}
			else if(res.status==5){
				layer.confirm(res.msg, {icon: 3, title:tishi}, function(index){
					  window.location.href="/"+window.parent.language+"/"+window.parent.type+"-login.html";
					  layer.close(index);
					});
			}
		  else{
			 layer.msg(res.msg, {shift: 6});
		  }
		  layer.closeAll('loading');
		}
		,error: function(){
		  	layer.msg(interfaceException, {shift: 6});
			layer.closeAll('loading');
		}
	  });
});
function addLisenter(){	
	//1.图片和文件查看功能
	$(".fa-eye").one("click",function(){
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
		}
		layer.open({type:2,content:src,area: ['750px','100%']});
	});
	//2.图片和文件删除功能
	$(".fa-trash").one("click",function(){
		//显示上传按钮
		$(this).parent().parent().remove();
	});
}
/**(cropper area) */
$(document).ready(function(){
	$("#btnCompanylogoCropper").on("click",function(){
		var w = 200;
		var h = 200;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=companylogo&pre=precompanylogo','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
	$("#btnBuslicensepathCropper").on("click",function(){
		var w = 800;
		var h = 1060;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=buslicensepath&pre=prebuslicensepath','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
	$("#btnCompanypictureCropper").on("click",function(){
		var w = 750;
		var h = 422;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=companypicture&pre=precompanypicture','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
	$("#btnCompanypicturesCropper").on("click",function(){
		var w = 750;
		var h = 422;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=3&i=mutipic&pre=precompanypictures','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
});