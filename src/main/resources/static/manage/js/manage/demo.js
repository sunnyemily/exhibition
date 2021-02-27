

//获取证件类型，赋值指定select
function loadCardTypeForRadioForChange(form,inputid,isexhibitor,type,val){
	var params = {};
	params.type=type;
	params.isexhibitor=isexhibitor;
	
	$.ajax({
		url : "/common/GetCardType",
		data : JSON.stringify(params),
		type : "post",async:false, 
		contentType : "application/json",
		success : function(result) {
			var strHtml = '<input type="radio" class="cardtype" name="cardtype" value="" checked>全部';
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					if(result.data[j].name == '布撤展车证')
						strHtml += '&nbsp;&nbsp;<input class="cardtype" type="radio" name="cardtype" value="'+result.data[j].id+'">'+result.data[j].name;
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
/*
 * 代办员下拉框
 */
function GetAgentCertificateReviewForChange(form,inputid,type,printstatus,status,verificationstatus,cardnature,isplastic,cardtype){
	var params = {};
	params.type=type;
	params.printstatus=printstatus;
	params.status=status;
	params.verificationstatus=verificationstatus;
	params.cardnature=cardnature;
	params.isplastic=isplastic;
	params.cardtype=cardtype;
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

function GetAgentCertificateReviewForQuery(form,inputid,type,printstatus,
		status,verificationstatus,cardnature,isplastic,isforensics,ZJLX){
	var params = {};
	params.type=type;
	params.printstatus=printstatus;
	params.status=status;
	params.verificationstatus=verificationstatus;
	params.cardnature=cardnature;
	params.isplastic=isplastic;
	params.isforensics=isforensics;
	params.ZJLX=ZJLX;
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

function GetPendingDocumentsForChange(form,inputid,type,cardtypename,companytype,printstatus,status,verificationstatus,cardnature,isplastic){
	var params = {};
	params.cardtypename=cardtypename;
	params.type=type;
	params.printstatus=printstatus;
	params.status=status;
	params.verificationstatus=verificationstatus;
	params.cardnature=cardnature;
	params.isplastic=isplastic;
	
	$.ajax({
		url : "/common/GetPendingDocuments",
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

function GetPendingDocumentsForQuery(form,inputid,type,cardtypename,companytype,
		printstatus,status,verificationstatus,cardnature,isplastic,isforensics,ZJLX){
	var params = {};
	params.cardtypename=cardtypename;
	params.type=type;
	params.printstatus=printstatus;
	params.status=status;
	params.verificationstatus=verificationstatus;
	params.cardnature=cardnature;
	params.isplastic=isplastic;
	params.isforensics=isforensics;
	params.ZJLX=ZJLX;
	$.ajax({
		url : "/common/GetPendingDocuments",
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