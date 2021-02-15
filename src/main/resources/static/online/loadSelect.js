var form;
var layer;
var areas;
layui.use(['form','layer'], function () {
	form = layui.form;
	layer = layui.layer;
});
$(document).ready(function () {	
	//获取地区信息
	$.get("/getCountryArea", function (r) {
		areas = r;		
		//loadCountry($("#txtcountry").val());
		//loadProvince($("#txtprovince").val());	
		loadCountry();
		form.on('select(country)', function (data) {			
			loadProvince();	
		});
		$("select[name=country]").val($("#txtcountry").val());	
		loadProvince();	
		$("select[name=province]").val($("#txtprovince").val());
		form.render();
	});
});

function loadCountry() {	
	var country = $("select[name=country]");
	country.html("<option value=''>国别</option>");
	for (let index in areas) {
		var html="";
		if (areas[index].parentId == "0"){
			html="<option value='" + areas[index].id + "'";
			
			html+=">" + areas[index].name + "</option>";
			country.append(html);
		}
	}	
	form.render('select');
}
function loadProvince() {
	var province = $("select[name=province]");
	province.html("<option value=''>地区</option>");	
	for (let index in areas) {
		var html="";
		if (areas[index].parentId == $("select[name=country]").val()){
			html="<option value='" + areas[index].id + "' ";
			
			html+=">" + areas[index].name + "</option>";
			province.append(html);
		}
	}
	form.render('select');
}

//根据指定parentid获取字典信息，赋值指定select
function loadZiDianByParentID_Select(val,parentid,inputid){
	var params = {};
	params.useable = 1;
	params.dicParentid = parentid;
	$.ajax({
		url : "/common/GetParticipants",
		data : JSON.stringify(params),
		type : "post",
		async:false, 
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml = "<option ";
					if(val==result.data[j].dicid){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].dicid + "'>"
							+ result.data[j].dicCnName + "</option>";
					$("#"+inputid).append(strHtml);	
				}
				//alert(strHtml);
							
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

function CheckPhone(str){
	var reg = /^[1][0-9]{10}$/;
	return reg.test(str);	
}

function CheckTel(str){
	var reg = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
	return reg.test(str);	
}

//在线询盘提交
function tj_zxxp(){
	var productmenuid = $("#productmenuid").val();
	//var name
	var title = $("#title").val();
	var quantity = $("#quantity").val();
	var quantityunit = $("#quantityunit").val();
	var tel = $("#tel").val();
	var content = $("#content").val();	
	
	if(productmenuid==''){
		layer.msg('请选择产品类别',{icon:5});
		return;
	}
	if(quantity==''){
		layer.msg('请输入数量',{icon:5});
		return;
	}
	if(quantityunit==''){
		layer.msg('请选择产品单位',{icon:5});
		return;
	}
	if(title==''){
		layer.msg('请输入标题',{icon:5});
		return;
	}
	if(tel==''){
		layer.msg('请输入联系方式',{icon:5});
		return;
	}
	
	if(!CheckPhone(tel) && !CheckTel(tel)){
		layer.msg('请输入正确的联系方式',{icon:5});
		return;
	}
	
	if(content==''){
		layer.msg('请输入内容描述',{icon:5});
		return;
	}
	var params = {};
	params.productmenuid = productmenuid;
	//params.name=  ;
	params.title = title;
	params.quantity = quantity;
	params.quantityunit = quantityunit;
	params.tel = tel;
	params.content = content;
	$.ajax({
		url : "/online/cn/addSpdocking",
		data : JSON.stringify(params),
		type : "post",
		//async:false, 
		contentType : "application/json",
		success : function(res) {			
			if(res.code==1){
				layer.msg("提交成功，请等待管理人员审核",{icon:6}, 
				function() {
					window.location.reload();
				});
			}
			else{
				layer.msg(res.msg,{icon:5});
			}
		}
	});
}

function tj_yxdd(){
	//var name
	var name = $("#name").val();	
	var tel = $("#tel").val();
	var content = $("#content").val();
	if(name==''){
		layer.msg('请输入姓名',{icon:5});
		return;
	}
	if(tel==''){
		layer.msg('请输入联系方式',{icon:5});
		return;
	}
	if(!CheckPhone(tel) && !CheckTel(tel)){
		layer.msg('请输入正确的联系方式',{icon:5});
		return;
	}
	if(content==''){
		layer.msg('请输入内容描述',{icon:5});
		return;
	}
	var params = {};
	params.companyid = $("#txtcompanyid").val();
	params.productid = $("#txtproductid").val();
	params.name=name;	
	params.tel = tel;
	params.content = content;
	params.act = 0;
	$.ajax({
		url : "/online/cn/addIntentionOrder",
		data : JSON.stringify(params),
		type : "post",
		//async:false, 
		contentType : "application/json",
		success : function(res) {			
			if(res.code==1){
				layer.msg("提交成功",{icon:6}, 
				function() {
					window.location.reload();
				});
			}
			else{
				layer.msg(res.msg,{icon:5});
			}
		}
	});
}


function tj_yyqt(){
	//var name
	var name = $("#name2").val();	
	var tel = $("#tel2").val();
	var content = $("#content2").val();
	if(name==''){
		layer.msg('请输入姓名',{icon:5});
		return;
	}
	if(tel==''){
		layer.msg('请输入联系方式',{icon:5});
		return;
	}
	if(!CheckPhone(tel) && !CheckTel(tel)){
		layer.msg('请输入正确的联系方式',{icon:5});
		return;
	}
	if(content==''){
		layer.msg('请输入内容描述',{icon:5});
		return;
	}
	var params = {};
	params.companyid = $("#txtcompanyid").val();
	params.productid = $("#txtproductid").val();
	params.name=name;	
	params.tel = tel;
	params.content = content;
	params.act = 1;	
	console.log(params);
	$.ajax({
		url : "/online/cn/addIntentionOrder",
		data : JSON.stringify(params),
		type : "post",
		//async:false, 
		contentType : "application/json",
		success : function(res) {			
			if(res.code==1){
				layer.msg("提交成功",{icon:6}, 
				function() {
					window.location.reload();
				});
			}
			else{
				layer.msg(res.msg,{icon:5});
			}
		}
	});
}


function loadZiDianByParentID_Menu(){
	var params = {};
	params.useable = 1;
	params.dicParentid = 90;
	$.ajax({
		url : "/common/GetParticipants",
		data : JSON.stringify(params),
		type : "post",
		async:false, 
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<a href='javascript:void(0)' rel='0' onclick='LoadOnlineInquiry(0)' class='clamenu'>全部</a>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {	
					strHtml += "<a href='javascript:void(0)' class='clamenu' rel='"+result.data[j].dicid+"' onclick='LoadOnlineInquiry("+result.data[j].dicid+")'>"+result.data[j].dicCnName+"</a>";								
				}
				$(".gcdj-menu").append(strHtml);	
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

function loadZiDianByParentID_Menu1(menuid){
	var params = {};
	params.useable = 1;
	params.dicParentid = 90;
	$.ajax({
		url : "/common/GetParticipants",
		data : JSON.stringify(params),
		type : "post",
		async:false, 
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<a href='javascript:void(0)' rel='0' onclick='SearchOnlineInquiry(0)' class='clamenu ";
			if(menuid==0){
				strHtml+="active";
			}
			strHtml+="'>全部</a>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {	
					strHtml += "<a href='javascript:void(0)' class='clamenu ";
					if(result.data[j].dicid==menuid){
						strHtml+="active";
					}
					strHtml+="' rel='"+result.data[j].dicid+"' onclick='SearchOnlineInquiry("+result.data[j].dicid+")'>"+result.data[j].dicCnName+"</a>";								
				}
				$(".gcdj-menu").append(strHtml);	
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

function SearchOnlineInquiry(menuid){
	var keyword = $("#xpkeyword").val();
	window.location.href="spdocking-"+menuid+"-"+keyword+"-1.html";
}


//加载企业列表产品
function loadCompanyProductInfo(){
	var ids = $("#ids").val();
	if(ids!=''){
		var strArgs = ids.split(',');
		for(var j=0;j<strArgs.length;j++){
			var companyid=strArgs[j];
			$.ajax({
				url : "/online/cn/GetQualityProducts/3/0/"+companyid,
				data : {},
				type : "post",
				 async: false,
				contentType : "application/json",
				success : function(res) {
					//console.log(res);
					if(res.code==1){
						var strHtml="";
						for(var j=0;j<res.data.length;j++){
							strHtml+="<dd>";
							strHtml+="	<div class=''pic'>";
							strHtml+="		<a href='productinfo-"+res.data[j].productid+".html'>";
							strHtml+="			<img style='width:158px' src='"+res.data[j].productpicture+"' alt='' class='zoom-img'>";
							strHtml+="		</a>";
							strHtml+="	</div>";
							strHtml+="	<p>";
							strHtml+="		<a href='productinfo-"+res.data[j].productid+".html'>"+res.data[j].productname+"</a>";
							strHtml+="	</p>";
							strHtml+="</dd>";
						}
						//strHtml+="</ul>";
						$("#cp"+companyid).html(strHtml);
					}
					else{
						layer.msg(res.msg);
					}
				}
			});
		}
	}
}

