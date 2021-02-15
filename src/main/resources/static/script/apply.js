var form;
var language = window.parent.language;
var type = window.parent.type;
var memberId = window.parent.member.memberId;
var sessionId = window.parent.member.memberSessionId;

var clientWidth = document.documentElement.clientWidth;
var clientHeight = document.documentElement.clientHeight;
layui.use('form', function() {
    form = layui.form;
    form.render();
});
form.on('submit(formDemo)', function(data) {
	if($("[name^=showroom_type_id-]:checked").length==0){
		layer.msg(boothEmptyValidateTips, {icon: 2});
		return false;
	}
	var parameter = {};
	parameter.sessionId = sessionId;
	parameter.memberId = memberId;
	if((/^[1-9]\d*$/.test($("[name=applyId]").val()))){
		parameter.applyId = $("[name=applyId]").val();
	}
	parameter.applyProducts = $("[name=product]").val();
	parameter.applyLicense = $("#applyLicense").val();
	parameter.applyFile = $("#applyFile").val();
	var applyList = [];
	var innerResult = true;
	var priceAmount = 0;
	$("[name^=showroom_type_id-]:checked").each(function(){
		var booth = {};
		var txtName = $(this).attr("name");
		var txtDivision = txtName.split("-")[1];
		booth.tradinggroupId = txtDivision.split("_")[0];
		booth.showroomTypeId = txtDivision.split("_")[1];
		if($("[name=apply_build_type-"+txtDivision+"]:checked").val()) booth.applyBuildType = $("[name=apply_build_type-"+txtDivision+"]:checked").val();
		if($("[name=apply_remove_separator-"+txtDivision+"]").val()) booth.applyRemoveSeparator = $("[name=apply_remove_separator-"+txtDivision+"]").val();
		booth.applyDeviceLength = $("[name=apply_device_length-"+txtDivision+"]").val();
		booth.applyDeviceWidth = $("[name=apply_device_width-"+txtDivision+"]").val();
		booth.applyDeviceHigh = $("[name=apply_device_high-"+txtDivision+"]").val();
		booth.applyElectricityAmount = $("[name=apply_electricity_amount-"+txtDivision+"]").val();
		booth.applyVoltage = $("[name=apply_voltage-"+txtDivision+"]:checked").val();
		var applyCount = $("[name=apply_count-"+txtDivision+"]").val();
		if($("[name=apply_count-"+txtDivision+"]").length>0&&!applyCount)
		{
			layer.msg(boothCountValidateTips, {icon: 2});
			$("[name=apply_count-"+txtDivision+"]").focus();
			innerResult = false;
			return;
		}
		var applyArea = $("[name=apply_area-"+txtDivision+"]").val();

		if($("[name=apply_area-"+txtDivision+"]").length>0&&!applyArea)
		{
			layer.msg(boothAreaValidateTips, {icon: 2});
			$("[name=apply_area-"+txtDivision+"]").focus();
			innerResult = false;
			return;
		}
		if(applyCount){
			booth.applyCount = applyCount;
		}
		else{
			booth.applyArea = applyArea;
		}
		applyList.push(booth);
		//计算本展厅申请展位的价格
		var amountItem = 0;
		var count = 0;
		var price = 0;
		if(booth.applyBuildType==1)//stanted booth
		{
			count = booth.applyCount;
			price = $("[name=apply_count-"+txtDivision+"]").data("price1")
		}
		else{
			count = booth.applyArea;
			price = $("[name=apply_area-"+txtDivision+"]").data("price3")
		}
		amountItem = count * price;
		priceAmount = priceAmount + amountItem;
	});
	if(!innerResult){
		return false;
	}
	parameter.list = applyList;
	boothMoneyValidateTips = boothMoneyValidateTips.replace("{moeny}",priceAmount);
	if(confirm(boothMoneyValidateTips)){
	    var index = layer.load(applyTips, {
	        icon: 1,
	        shade: [0.8, '#000'] //0.1透明度的白色背景
	    });
	    url = "/"+language+"/"+type+"-apply-update";
	    
	    $.post(url,{parameter:JSON.stringify(parameter)}, function(result) {
	        layer.close(index);
	        if (result.status == 1) {
				layer.alert(result.msg, function(index){
					window.parent.location=window.parent.location;
				  layer.close(index);
				});
	        }
	        else {
	            layer.msg(result.msg);
	        }
	    });
    }

    return false;
});
form.verify({
});
form.on('radio', function(data){
	//data.elem
	var txtName = $(data.elem).attr("name");
	//不是展位搭建类型则直接退出
	if(txtName.indexOf("apply_build_type-")==-1){
		return;
	}
	var parentDom = $("[name="+txtName+"]").last().parent();
	var areaOrCountDom = parentDom.find("[type=text]").first();
	var areaOrCountDomName = areaOrCountDom.attr("name");
	areaOrCountDom.val("");
	if(data.value=="1"){
		areaOrCountDom.attr("name",areaOrCountDomName.replace("area","count"));
		parentDom.find(".apply_unit").html("个");
		parentDom.find(".apply_remove_separator").css("display","inline");
	}
	else{
		areaOrCountDom.attr("name",areaOrCountDomName.replace("count","area"));
		parentDom.find(".apply_unit").html(applyUnit);
		parentDom.find(".apply_remove_separator").css("display","none");
	}
});
form.on('checkbox', function(data){
	if(data.elem.checked){
		$(data.elem).parent().next().find("input").removeAttr("disabled");
		$(data.elem).parent().next().next().find("input").removeAttr("disabled");
	}
	else{
		$(data.elem).parent().next().find("input").attr("disabled","disabled");
		$(data.elem).parent().next().find("input[type=text]").val("");
		$(data.elem).parent().next().next().find("input").attr("disabled","disabled");
		$(data.elem).parent().next().next().find("input[type=text]").val("0.00");
	}
	form.render();
});
$(document).ready(function(){
	//1.验证输入的面积或展位数量时间
	$("input[type=text][name^=apply_area-]").on("blur",function(){
		verifyCountAndArea($(this));
	});
	$("input[type=text][name^=apply_count-]").on("blur",function(){
		verifyCountAndArea($(this));
	});
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
	//3.图片和文件删除功能
	$(".fa-trash").on("click",function(){
		//表单置空
		$(this).parent().prev().prev().val("");
		//图片置空
		$(this).parent().prev().attr("src","");　
		//隐藏预览结构
		$(this).parent().parent().parent().css("display","none");
		//显示上传按钮
		$(this).parent().parent().parent().prev().css("display","inline-block");
	});
	//4.获取申请信息
	getApplyInfo();
});
function getApplyInfo(){
	var index = layer.load(applyGetTips, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
	$.post("/"+language+"/"+type+"-apply-get",{memberId:memberId,sessionId:sessionId},function(result){
		layer.close(index);
		if(result.status==1&&result.result!=null){
			var apply = result.result;
			//1.基本信息设置
			$("[name=applyId]").val(apply.applyId);
			 $("[name=product]").val(apply.applyProducts);
			 $("#applyLicense").val(apply.applyLicense);
			$("#preapplyLicense").attr("src", $("#applyLicense").val());			
			$("#applyFile").val(apply.applyFile);
		
			 if(apply.applyFile!=""){
			$("#applyFileContainer").html("<a href='"+apply.applyFile+"' target='_blank'>"+previourTitle+"</a>");
			 }
			 //2.展位申请信息设置
			for(i=0;i<apply.list.length;i++){
				applyList = apply.list[i];
				var txtDivision = applyList.tradinggroupId+"_"+applyList.showroomTypeId;
				$("[name=showroom_type_id-"+txtDivision+"]").next().click();
				if(applyList.applyBuildType!=null){
					$("[name=apply_build_type-"+txtDivision+"][value="+applyList.applyBuildType+"]").next().click();
					if(applyList.applyArea!=null){
						$("[name=apply_area-"+txtDivision+"]").val(applyList.applyArea);
					}
					else{
						$("[name=apply_count-"+txtDivision+"]").val(applyList.applyCount);
					}
				}
				else{
					$("[name=apply_area-"+txtDivision+"]").val(applyList.applyArea);
				}
				$("[name=apply_device_length-"+txtDivision+"]").val(applyList.applyDeviceLength);
				$("[name=apply_device_width-"+txtDivision+"]").val(applyList.applyDeviceWidth);
				$("[name=apply_device_high-"+txtDivision+"]").val(applyList.applyDeviceHigh);
				$("[name=apply_electricity_amount-"+txtDivision+"]").val(applyList.applyElectricityAmount);
				$("[name=apply_voltage-"+txtDivision+"][value="+applyList.applyVoltage+"]").next().click();
			}
		}
		else{
			layer.msg(result.msg);
		}
	});
}
function verifyCountAndArea(dom){
	var txtName = dom.attr("name");
	var value = dom.val();
	if(txtName.indexOf("apply_area")>=0){
		//1.是否符合最低面积要求
		var lowArea = dom.data("limit");
		if(value < lowArea){
			areaErrorTips = areaErrorTips.replace("{lowArea}",lowArea)
			layer.msg(areaErrorTips, {icon: 2});
			dom.focus();
			return;
		}
		var perArea = dom.data("area");
		if(value % perArea != 0){
			areaErrorTips2 = areaErrorTips2.replace("{perArea}",perArea)
			layer.msg(areaErrorTips2, {icon: 2});
			dom.focus();
			return;
		}
	}
	else if(txtName.indexOf("apply_count")>=0){
		if(!(/^[1-9]\d*$/.test(value))){
			layer.msg(countErrorTip, {icon: 2});
			dom.focus();
			return;
		}
	}
}

//上传部分

layui.use('upload', function(){
	var upload = layui.upload;
//上传文件
var uploadFile = upload.render({
	accept:'file'
	,size:1024*15
	,exts:'rar|zip'
	,elem: '#btnApplyFile' //绑定元素
	,url: '/uploadFile' //上传接口接
	,before: function(obj){
		layer.load(); //上传loading
	}
	,done: function(res){
	  if(res.status==1){
		$("#applyFile").val(res.result);
		$("#applyFileContainer").html("<a href='"+res.result+"' target='_blank'>"+previourTitle+"</a>");
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

/**(cropper area) */
$(document).ready(function(){
	$("#btnApplyLicenseCropper").on("click",function(){
		var w = 800;
		var h = 1060;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=applyLicense&pre=preapplyLicense','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
});
