<!DOCTYPE html>
<#include 'head.html'>
<body>

<div class="bg10"></div>

<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">

	    <div class="modal inmodal" id="edit-model" style="display:">
		<div class="modal-content animated bounceInUp">
			<div class="ibox-content">
				<fieldset class="layui-elem-field layui-field-title" style="border-top:0px">
					<form class="layui-form layui-form-pane" action="" id="editform">
					    <input type="hidden" name="id" value="0"  />
					    <input type="hidden" name="session" value="${exhibitionSessionId}"  />
					    <input type="hidden" name="cardnature" value="0"  />
					    <input type="hidden" name="isback" value="1"  />
						<table width="100%"  class="layui-table form-content">						
							
							<tr>
								<td>name</td>
								<td width="40%">${info.name}</td>
								<td>gender</td>
								<td>
									${info.sexname}
      							</td>
							</tr>
							<tr>
								<td>country/region</td>
								<td>
									${info.countryAndprovince}
								</td>
								<td>position</td>
								<td>${info.jobtitle}</td>
							</tr>
							<tr>
								<td>company name</td>
								<td>${info.companyname}</td>
								<td>type of card</td>
								<td>${info.cardtypename}</td>
							</tr>
							<tr>
								<td>ID/passport/journalist card</td>
								<td>${info.idcardpassportname}<td>ID/passport/journalist card number</td>
								<td>${info.cardnumber}</td>
							</tr>
							<tr>
								<td>Zipcode</td>
								<td>${info.postcode}</td>
								<td>address</td>
								<td>${info.address}</td>
							</tr>
							<tr>
								<td>company website address</td>
								<td colspan="3">${info.website}</td>
							</tr>
							<tr>
								<td>moblie phone </td>
								<td>${info.phone}</td>
								<td>E-mail</td>
								<td>${info.email}</td>
							</tr>
							<tr>
								<td>Tel.</td>
								<td>${info.tel}</td>
								<td>fax</td>
								<td>${info.fax}</td>
							</tr>
							
							<tr>
								<td>business scope</td>
								<td>${info.industryname}</td>
								<td>exhibitor or visitor </td>
								<td>${info.participantsname}</td>
							</tr>
							<tr>
								<td>purpose of attending the exhibition</td>
								<td colspan="3" id="purpose">
								</td>
							</tr>
							<tr>
								<td>the exhibition area you want to visit</td>
								<td colspan="3">${info.zqname}</td>
							</tr>
							<tr>
								<td>how do you know the exhibition</td>
								<td colspan="3" id="knowexhibition">
								</td>
							</tr>
							<tr>
								<td>business nature</td>
								<td colspan="3" id="businessnature">
								</td>
							</tr>
							
							<tr>
								<td>card photo</td>								
								<td colspan="3" class="prepic"><image src="${info.imagepath}" width="195" height="243" id="preimagepath" /></td>
							</tr>
							<tr>
								<td><#if info.idcardpassport==2 >journalist card<#else>ID card（passport）</#if></td>								
								<td colspan="3" class="prepic"><image src="${info.idphotopath}" width="195" height="132" id="preidphotopath" /></td>
							</tr>
							<#if info.cardtypename=="purchaser card">
							<tr>
								<td>business license</td>								
								<td colspan="3" class="prepic"><image src="${info.businesslicense}" width="195" height="258" id="prebusinesslicense" /></td>
							</tr>
							<tr>
								<td>purchasing intention</td>
								<td colspan="3">${info.businesslicense}</td>
							</tr>
							</#if>
							<tr class="papers-edit-bg">
								<td colspan="4" align="center">
									<button type="button" onclick="var index = parent.layer.getFrameIndex(window.name);parent.layer.close(index);" class="layui-btn layui-btn-primary close">return</button>									
								</td>
							</tr>
							
						</table>
					</form>
				</fieldset>
			</div>
		</div>
	</div>
  </div>
</div>
<script>
var $,form;
layui.use(['form', 'table'], function() {	
	$ = layui.jquery;
	form = layui.form;
});
function loadZiDianByParentID_Checkbox(form, val, parentid, inputid) {
	var params = {};
	params.useable = 1;
	params.dicParentid = parentid;
	$.ajax({
		url: "/common/GetParticipants",
		data: JSON.stringify(params),
		type: "post",
		contentType: "application/json",
		success: function (result) {
			var strHtml = "";
			if (result.code === 1) {
				for (var j = 0; j < result.data.length; j++) {
					strHtml += "<input lay-skin='primary' type='checkbox' ";
					if (val != "") {
						var strArgs = val.split(",");
						for (var k = 0; k < strArgs.length; k++) {
							if (result.data[j].dicid == strArgs[k]) {
								strHtml += " checked ";
							}
						}
					}
					strHtml += " value='" + result.data[j].dicid + "' title='" + result.data[j].dicCnName + "' name='" + inputid + "' disabled>";
				}
				//alert(strHtml);
				$("#" + inputid).append(strHtml);
				form.render("checkbox");

			} else {
				layer.alert(result.msg);
			}
		}
	});
}

$(document).ready(function(){
	loadZiDianByParentID_Checkbox(form, "${info.purpose}", 4, "purpose");
	loadZiDianByParentID_Checkbox(form, "${info.knowexhibition}", 6, "knowexhibition");
	loadZiDianByParentID_Checkbox(form, "${info.businessnature}", 8, "businessnature");
});
</script>

</body>
</html>
