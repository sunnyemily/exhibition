//根据权限，判断按钮隐藏与否
function getUserPermissions(btnList){
	var userPermissions;
	$.ajax({
	  url: "/common/getUserPermissions",
	  async: false,
	  type:'get',
	  success: function(r){
		  userPermissions = r.data;
		  btnList.forEach(function (btnItem) {
			  r.data.forEach(function (permissionItem) {
				  if (btnItem.url == permissionItem){
					  $('.'+btnItem.className).removeClass("layui-hide");
				  }
			  });
		  })
	  }
	});
	return userPermissions;
}

function loadUserPermissions(){
	var val="";
	$.ajax({
	  url: "/common/getUserPermissions",
	  async: false,
	  type:'get',
	  success: function(r){
		  val = r.data;
	  }
	});
	return val;
}
//打印模版证件类型
function loadAllCardTypesForPrint(form,inputid,val){
	var params = {};
	params.useable = 1;
	params.type=val;
	$.ajax({
		url : "/common/GetCardTypesForPrint",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = ""; 
			var strHtmlx = "";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {					
					strHtml+="<input class='chk' type='checkbox' value='"+result.data[j].id+"' lay-skin='primary' title='"+result.data[j].chinesename+"'>";					
				}				
				//alert(strHtml);
				$("#"+inputid).html(strHtml);				
				form.render('checkbox');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//打印
function doPrint(){
	bdhtml=window.document.body.innerHTML;   
    sprnstr="<!--startprint-->";   
    eprnstr="<!--endprint-->";   
    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);   
    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));   
    window.document.body.innerHTML=prnhtml;  
    window.print();
    window.document.body.innerHTML=bdhtml;
}

function GetAgentCertificateReview(form,inputid,type){
	var params = {};
	params.type=type;
	$.ajax({
		url : "/common/GetAgentCertificateReview",
		data : JSON.stringify(params),
		type : "post",
		//async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option value=''>==代办员==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					/*if(val==result.data[j].id){
						strHtml+=" selected ";
					}*/
					strHtml += " value='" + result.data[j].memberid + "'>"
							+ result.data[j].membername + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

function GetDocumentsToBePrinted(form,inputid,type,cardtypename){
	var params = {};
	params.cardtypename=cardtypename;
	params.type=type;
	$.ajax({
		url : "/common/GetDocumentsToBePrinted",
		data : JSON.stringify(params),
		type : "post",
		//async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option value=''>"+cardtypename+"</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					/*if(val==result.data[j].id){
						strHtml+=" selected ";
					}*/
					strHtml += " value='" + result.data[j].companyname + "'>"
							+ result.data[j].companynamecount + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}


function GetPendingDocuments(form,inputid,type,cardtypename,companytype){
	var params = {};
	params.cardtypename=cardtypename;
	params.type=type;
	$.ajax({
		url : "/common/GetPendingDocuments",
		data : JSON.stringify(params),
		type : "post",
		//async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";//"<option value=''>全部</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					/*if(val==result.data[j].id){
						strHtml+=" selected ";
					}*/
					strHtml += " value='" + result.data[j].companyname + "'>"
							+ result.data[j].companynamecount + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).append(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}
//获取交易团列表
function loadLingSanTradingGroup(form,inputid,val,leixing){
	var params = {};
	params.useable = 1;
	params.type=leixing;
	$.ajax({
		url : "/common/GetTradingGroup",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option value=''>==请选择==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}
//获取交易团列表
function loadTradingGroupByType(form,val,inputid,leixing){
	var params = {};
	params.useable = 1;
	params.type=leixing;
	var tradingGroupList;
	$.ajax({
		url : "/common/GetTradingGroup",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option "+((val=='')?" selected ":"")+" value=''>全部</option>";
			if (result.code === 1) {
				tradingGroupList=result.data;
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option "+((val==result.data[j].id)?" selected ":"")+" value='"+result.data[j].id+"'>"+result.data[j].name+"</option>";
				}
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
	return tradingGroupList;
}
//加载交易团列表，不关联用户权限
function loadTradingGroupByMap(form,val,inputid,leixing){
	var params = {};
	params.type=leixing;
	var tradingGroupList;
	$.ajax({
		url : "/manage/Exhibitors/ebsTradinggroup/selectByType",
		data : params,
		type : "get",
		async: false,
		success : function(result) {
			var strHtml = "<option "+((val=='')?" selected ":"")+" value=''></option>";
			if (result.code === 200) {
				tradingGroupList=result.data;
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option "+((val==result.data[j].id)?" selected ":"")+" value='"+result.data[j].id+"'>"+result.data[j].name+"</option>";
				}
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
	return tradingGroupList;
}
//加载交易团列表，不关联用户权限，初始值为全部
function loadTradingGroupNoDefaultValue(form,inputid,leixing){
	var params = {};
	params.type=leixing;
	var tradingGroupList;
	$.ajax({
		url : "/manage/Exhibitors/ebsTradinggroup/selectByType",
		data : params,
		type : "get",
		async: false,
		success : function(result) {
			var strHtml = "<option selected value=''>全部</option>";
			if (result.code === 200) {
				tradingGroupList=result.data;
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option value='"+result.data[j].id+"'>"+result.data[j].name+"</option>";
				}
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
	return tradingGroupList;
}
//获取交易团列表
function loadZhengJianZhiZuoTradingGroup(form,inputid,val,leixing,isexhibitor,caozuotype,printstatus,isplastic){
	var params = {};
	params.useable = 1;
	params.type=leixing;
	params.isexhibitor=isexhibitor;
	params.caozuotype=caozuotype;
	params.isplastic = isplastic;
	params.printstatus = printstatus;
	$.ajax({
		url : "/common/GetZhengJianZhiZuoTradingGroup",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option value=''>==请选择==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).html(strHtml);
				//form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}
function loadVolunteer(form,val,inputid){
	var params = {};
	params.useable = 1;
	$.ajax({
		url : "/common/GetZhiYuanZhe",
		data : JSON.stringify(params),
		type : "post",async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option value=''>请选择志愿者</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).append(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//志愿者考勤类型
function loadZhiYuanZheKaoQinLeixing(form,val,inputid){
	var strHtml="";
	strHtml+="<option value='0' ";
	if(val==0){strHtml+=" selected ";}
	strHtml+=" >正常</option>";
	strHtml+="<option value='1' ";
	if(val==1){strHtml+=" selected ";}
	strHtml+="  >迟到</option>";
	strHtml+="<option value='2' ";
	if(val==2){strHtml+=" selected ";}
	strHtml+="  >早退</option>";
	strHtml+="<option value='3' ";
	if(val==3){strHtml+=" selected ";}
	strHtml+="  >请假</option>";
	strHtml+="<option value='4' ";
	if(val==4){strHtml+=" selected ";}
	strHtml+="  >旷工</option>";
	
	$("#"+inputid).html(strHtml);
	form.render('select');
}

//字典目录select
function loadZhiYuanZhe(form,val,parentid,inputid,type){
	var params = {};
	params.useable = 1;
	params.dicParentid = parentid;
	$.ajax({
		url : "/manage/system/systemDictionaries/findByMap",
		data : JSON.stringify(params),
		type : "post",async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			if(type==0){
				strHtml="<option value=''>选择部门</option>";
			} else if(type==1){
				strHtml="<option value=''>选择语种</option>";
			}
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].dicid){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].dicid + "'>"
							+ result.data[j].dicCnName + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).append(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//调查表统计
function loadDaoChaBiaoTongJi(form,inputid){
	var params = {};
	//params.companyid=companyid;
	$.ajax({
		url : "/common/GetQuestionInfo",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			if(result.code==1){
				var strHtml="<table id='tabDCBTJ' class='layui-table'>";
				for(var j=0;j<result.data.length;j++){
					$("#txtsurveyid").val(result.data[j].parentid);
					var questionid = result.data[j].id;
					var cntitle = result.data[j].cntitle;
					var code = result.data[j].code;
					var showtype = result.data[j].showtype;
					if(showtype==0 || showtype==1){
						strHtml+="<tr><td align='left' style='font-size:16px'>"+(j+1)+"."+cntitle+"</td></tr>";
						strHtml+=loadAnswerTongJi(questionid,code,showtype);	
					}
				}
				strHtml+="</table>";		
				$("#"+inputid).html(strHtml);
				form.render('checkbox');
				form.render('radio');
			}
		}
	});
}

function loadAnswerTongJi(questionid,code,showtype){
	var strHtml="";
	if(showtype==2 || showtype==3){
		return strHtml;
	}
	var params = {};
	//params.companyid=companyid;
	params.questionid=questionid;
	$.ajax({
		url : "/common/GetAnswerTongJiInfo",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			strHtml="<tr><td><table ";
			if(showtype==2){
				strHtml+=" style='width:100%' ";
			}
			strHtml+="><tr>";
			for(var j=0;j<result.data.length;j++){
				//var cnanswername = result.data[j].cnanswername;
				//var res = result.data[j].res;
				//var id = result.data[j].id;
				//var remark = result.data[j].remark				
				strHtml+="<td>"+result.data[j].answerpercent+"</td>";							
			}
			strHtml+="</tr></table></td></tr>";			
		}
	});
	return strHtml;
}




//调查表
function loadDaoChaBiao(form,companyid,inputid){
	var params = {};
	params.companyid=companyid;
	$.ajax({
		url : "/common/GetQuestionInfo",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			if(result.code==1){
				var strHtml="<table class='layui-table'>";
				for(var j=0;j<result.data.length;j++){
					$("#txtsurveyid").val(result.data[j].parentid);
					var questionid = result.data[j].id;
					var cntitle = result.data[j].cntitle;
					var code = result.data[j].code;
					var showtype = result.data[j].showtype;
					strHtml+="<tr><td align='left' style='font-size:16px'>"+(j+1)+"."+cntitle+"</td></tr>";
					strHtml+=loadAnswer(questionid,code,showtype,companyid);						
				}
				strHtml+="</table>";		
				$("#"+inputid).html(strHtml);
				form.render('checkbox');
				form.render('radio');
			}
		}
	});
}

function loadAnswer(questionid,code,showtype,companyid){
	var strHtml="";
	var params = {};
	params.companyid=companyid;
	params.questionid=questionid;
	$.ajax({
		url : "/common/GetAnswerInfo",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			strHtml="<tr><td><table ";
			if(showtype==2){
				strHtml+=" style='width:100%' ";
			}
			strHtml+="><tr>";
			for(var j=0;j<result.data.length;j++){
				var cnanswername = result.data[j].cnanswername;
				var res = result.data[j].res;
				var id = result.data[j].id;
				var remark = result.data[j].remark
				if(showtype==0){
					strHtml+="<td><input type='checkbox'" ;
					if(res==1){
						strHtml+=" checked ";
					}
					strHtml+=" class='chk' lay-skin='primary' name='like["+code+"]' title='"+cnanswername+"' value='"+questionid+"|"+id+"'></td>";
				}else if(showtype==1){
					strHtml+="<td><input type='radio' ";
					if(res==1){
						strHtml+=" checked ";
					}
					strHtml+=" class='chk' lay-skin='primary' name='"+code+"' title='"+cnanswername+"' value='"+questionid+"|"+id+"'></td>";
				} else if(showtype==2){
					strHtml+="<td><textarea id='"+code+"' name='"+code+"' class='layui-textarea'>";
					if(remark!=''){
						strHtml+=remark;
					}
					strHtml+="</textarea></td>"
					if($("#txtkongjian").val()==''){
						$("#txtkongjian").val($("#txtkongjian").val()+questionid+"@"+id+"@"+code);
					}
					else{
						$("#txtkongjian").val($("#txtkongjian").val()+"|"+questionid+"@"+id+"@"+code);
					}
					
				} else if(showtype==3){
					strHtml+="<td><input id='"+code+"' name='"+code+"' class='layui-input' ";
					if(remark!=''){
						strHtml+= " value='"+remark+"' ";
					}
					strHtml+=" ></td>"
						if($("#txtkongjian").val()==''){
							$("#txtkongjian").val($("#txtkongjian").val()+questionid+"@"+id+"@"+code);
						}
						else{
							$("#txtkongjian").val($("#txtkongjian").val()+"|"+questionid+"@"+id+"@"+code);
						}
				}			
			}
			strHtml+="</tr></table></td></tr>";			
		}
	});
	return strHtml;
}

//获取代办员信息
function loadAgent(form,inputid){	
	var params = {};
	params.parentid='0';
	$.ajax({
		url : "/common/GetAgent",
		data : JSON.stringify(params),
		type : "post",async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			strHtml+="<option value=''>==全部负责人==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";					
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).append(strHtml);				
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//获取国家信息，赋值指定select
function loadCountryInfo(form,countryinputid,countryid){	
	var params = {};
	params.parentid='0';
	$.ajax({
		url : "/common/GetCountry",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			strHtml+="<option value=''>==请选择==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(countryid==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				$("#"+countryinputid).append(strHtml);				
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//获取历史届次
function loadHistorySession(form,inputid,ty){
	var params = {};
	params.useable = 1;
	$.ajax({
		url : "/common/getHistorySession",
		data : JSON.stringify(params),
		type : "post",
		async:false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			if (result.code === 1) {
				if(ty!='')strHtml+="<option value=''>全部</option>";
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].sessionid + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//获取历届与当届交易团
function loadPreviousTradingGroup(form,val,inputid,oldsession){
	var params = {};
	params.useable = 1;
	params.oldsession=oldsession;
	$.ajax({
		url : "/common/GetPreviousTradingGroup",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option value='0'>==请选择==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//获取交易团列表
function loadTradingGroupForGreen(form,val,inputid){
	var params = {};
	params.useable = 1;
	$.ajax({
		url : "/common/GetTradingGroupForGreen",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option value=''>==请选择==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}
//获取所有证件类型for select
function loadAllCardTypes(form,val,inputid){
	var params = {};
	params.useable = 1;
	$.ajax({
		url : "/common/GetDaiBanYuanZhengJianLeiXing",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option value=''>全部</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}



//获取代办员证件类型
function loadDaiBanYuanZhengJian(form,inputid,val,session){
	var params = {};
	params.oldsession=session;
	$.ajax({
		url : "/common/GetDaiBanYuanZhengJianLeiXing",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<table border=1 width='96%'><tr>";
			if (result.code === 1) {
				var Args = val=='' ? '0|0' : val;
				console.log(Args);
				for (var j = 0; j < result.data.length; j++) {
					var n=j%2;
					if(j>0 && n==0){
						strHtml+="</tr><tr>";
					}
					var xuanzhong="";
					var shuliang="";
					var types=Args.split(',');
					for(var k=0;k<types.length;k++){
						var Array=types[k].split('|');
						if(Array[0]==result.data[j].id){
							xuanzhong = " checked ";
							if(result.data[j].isexhibitor==1){
								$("#trjyt").show();
								$("#zhuangtai").val("1");
							}								
						}
						if(Array[0]==result.data[j].id && Array[1]!=0){
							shuliang = " value='"+Array[1]+"' ";
						}
					}
						strHtml+="<td height='40px'><input type='checkbox' "+xuanzhong;
						
						if(result.data[j].isexhibitor==1){//参展证
							strHtml+=" id='canzhanzheng' lay-filter='canzhanzheng' ";
						}
						
						strHtml+=" lay-skin='primary' name='like[write]' class='chk' title='"+result.data[j].name+"' rel='"+j+"' value='"+result.data[j].id+"'></td><td style='padding-left:5px'>";
						if(result.data[j].isexhibitor!=1){	
							strHtml+="<table><tr><td>数量：</td><td><input onkeyup=\"value=value.replace(/[^(\-)?\(0-9)+((0-9)))]/g,'')\" class='layui-input' ";
							strHtml+=shuliang;		
							strHtml+=" style='width:100px' autocomplete='off' type='text' id='txt"+j+"'></td></tr></table>";
						}
						else{
							strHtml+="<table style='display:none'><tr><td>数量：</td><td><input class='layui-input' style='width:100px' type='text' id='txt"+j+"'></td></tr></table>";
						}
						strHtml+="</td>";
										
				}
				strHtml+="</tr></table>";
				//alert(strHtml);				
				$("#"+inputid).html(strHtml);
				form.render('checkbox');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//获取企业列表
function loadCompany(form,val,inputid){
	var params = {};
	params.useable = 1;
	$.ajax({
		url : "/common/GetCompanyInfo",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option value=''>==请选择==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].chinesename + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//字典目录select
function loadZiDian(form,val,parentid){
	var params = {};
	params.useable = 1;
	params.dicParentid = parentid;
	$.ajax({
		url : "/manage/system/systemDictionaries/findByMap",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].dicid){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].dicid + "'>"
							+ result.data[j].dicCnName + "</option>";
				}
				//alert(strHtml);
				$("#dicParentid").append(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//证件类型select
function loadZhengJianLeiXing(form,val,type){
	var params = {};
	params.useable = 1;
	params.type=type;
	$.ajax({
		url : "/manage/MakeEvidence/cmCertificateType/getlist",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			if (result.code === 200) {
				strHtml+="<option value=''>==选择证件类型==</option>";
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].chinesename + "</option>";
				}
				//alert(strHtml);
				$("#cardtype").html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//展会select
function loadexhibition(form,val){
	var params = {};
	params.useable = 1;
	$.ajax({
		url : "/manage/system/sysSession/getlist",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			if (result.code === 200) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].exhibitionname + "</option>";
				}
				//alert(strHtml);
				$("#exhibitionid").append(strHtml);
				//form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//展厅类型select
function loadselect(form,val) {
	var params = {};
	params.status = 1;
	$.ajax({
		url : "/manage/Exhibitors/ebsShowroomtype/getlist",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "<option value>请选择展厅类型</option>";
			if (result.code === 200) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				$("#type").html(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//根据指定parentid获取字典信息，赋值指定select
function loadZiDianByParentID_Select(form,val,parentid,inputid){
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
					strHtml += "<option ";
					if(val==result.data[j].dicid){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].dicid + "'>"
							+ result.data[j].dicCnName + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).append(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}
//获取行业信息，赋值指定select
function loadIndustry(form,val,inputid){
	var params = {};
	$.ajax({
		url : "/common/GetIndustry",
		data : JSON.stringify(params),
		type : "post",
		async:false, 
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).append(strHtml);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//根据指定parentid获取字典信息，赋值checkbox
function loadZiDianByParentID_Checkbox(form,val,parentid,inputid){
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
					strHtml+="<input type='checkbox' ";
					if(val!=""){
						var strArgs = val.split(",");
						for(var k=0;k<strArgs.length;k++){
							if(result.data[j].dicid == strArgs[k]){
								strHtml+=" checked ";
							}
						}
					}
					strHtml+= " value='"+result.data[j].dicid+"' title='"+result.data[j].dicCnName+"' name='chk"+inputid+"' >";
				}
				//alert(strHtml);
				$("#"+inputid).append(strHtml);
				form.render('checkbox');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//获取国家信息，赋值指定select
function loadCountry(form,countryid,areaid,cityid,countryinputid,areainputid,cityinputid){
	var params = {};
	params.parentid='';
	$.ajax({
		url : "/common/GetCountry",
		data : JSON.stringify(params),
		type : "post",
		async:false, 
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			strHtml+="<option value=''>==请选择==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(countryid==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				$("#"+countryinputid).append(strHtml);
				loadProvince(form,countryid,areaid,cityid,areainputid,cityinputid);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

function loadProvince(form,countryid,areaid,cityid,areainputid,cityinputid){
	var params = {};
	params.parentid = countryid == 0 ? 900000 : countryid;	
	$.ajax({
		url : "/common/GetCountry",
		data : JSON.stringify(params),
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			strHtml+="<option value=''>==请选择==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(areaid==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				
				$("#"+areainputid).html(strHtml);
				loadCity(form,areaid,cityid,cityinputid);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

function loadProvinceChange(form,countryid,areaid,areainputid){
	console.log(countryid);
	var params = {};
	params.parentid = countryid == 0 ? 900000 : countryid;	
	$.ajax({
		url : "/common/GetCountry",
		data : JSON.stringify(params),
		type : "post",
		async:false, 
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			strHtml+="<option value=''>==请选择==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(areaid==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				
				$("#"+areainputid).html(strHtml);
				//loadCity(form,areaid,cityid,cityinputid);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}


function loadCity(form,areaid,cityid,cityinputid){	
	var params = {};
	params.parentid = areaid;	
	$.ajax({
		url : "/common/GetCountry",
		data : JSON.stringify(params),
		type : "post",
		async:false, 
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			strHtml+="<option value='0'>==请选择==</option>";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(cityid==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				
				$("#"+cityinputid).html(strHtml);
				//loadCity(form,areaid,cityid,cityinputid);
				form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}



//获取证件类型，赋值指定select
function loadCardTypeForRadio(form,inputid,isexhibitor,type,val){
	var params = {};
	params.type=type;
	params.isexhibitor=isexhibitor;
	$.ajax({
		url : "/common/GetCardType",
		data : JSON.stringify(params),
		type : "post",async:false, 
		contentType : "application/json",
		success : function(result) {
			var strHtml = '<input type="radio" name="cardtype" value="" checked>全部';
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += '&nbsp;&nbsp;<input type="radio" name="cardtype" value="'+result.data[j].id+'">'+result.data[j].name;					
				}
				//alert(strHtml);
				$("#"+inputid).append(strHtml);
				//form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}

//获取证件类型，赋值指定select
function loadCardType(form,inputid,isexhibitor,type,val){
	var params = {};
	params.type=type;
	params.isexhibitor=isexhibitor;
	$.ajax({
		url : "/common/GetCardType",
		data : JSON.stringify(params),
		type : "post",async:false, 
		contentType : "application/json",
		success : function(result) {
			var strHtml = "";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<option ";
					if(val==result.data[j].id){
						strHtml+=" selected ";
					}
					strHtml += " value='" + result.data[j].id + "'>"
							+ result.data[j].name + "</option>";
				}
				//alert(strHtml);
				$("#"+inputid).append(strHtml);
				//form.render('select');
			} else {
				layer.alert(result.msg);
			}
		}
	});
}


function XuanZeZhanHui(zhanhuiID){
	alert(zhanhuiID);
}
