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
								<td>성함</td>
								<td width="40%">${info.name}</td>
								<td>성별</td>
								<td>
									${info.sexname}
      							</td>
							</tr>
							<tr>
								<td>국가/지자체</td>
								<td>
									${info.countryAndprovince}
								</td>
								<td>직무</td>
								<td>${info.jobtitle}</td>
							</tr>
							<tr>
								<td>엄체명</td>
								<td>${info.companyname}</td>
								<td>출입증 유형</td>
								<td>${info.cardtypename}</td>
							</tr>
							<tr>
								<td>주민등록증/역권/기자증</td>
								<td>${info.idcardpassportname}<td>증명서 번호</td>
								<td>${info.cardnumber}</td>
							</tr>
							<tr>
								<td>우편번호</td>
								<td>${info.postcode}</td>
								<td>주소</td>
								<td>${info.address}</td>
							</tr>
							<tr>
								<td>회사 공식 사이트</td>
								<td colspan="3">${info.website}</td>
							</tr>
							<tr>
								<td>휴대전화</td>
								<td>${info.phone}</td>
								<td>우편번호</td>
								<td>${info.email}</td>
							</tr>
							<tr>
								<td>전화번호</td>
								<td>${info.tel}</td>
								<td>팩스</td>
								<td>${info.fax}</td>
							</tr>
							
							<tr>
								<td>*업무영역</td>
								<td>${info.industryname}</td>
								<td>참가자 신분</td>
								<td>${info.participantsname}</td>
							</tr>
							<tr>
								<td>참가 목적</td>
								<td colspan="3" id="purpose">
								</td>
							</tr>
							<tr>
								<td>참관하고 싶은 전시구</td>
								<td colspan="3">${info.zqname}</td>
							</tr>
							<tr>
								<td>당신은 어떻게 본 전시회를 알게 되셨나요?</td>
								<td colspan="3" id="knowexhibition">
								</td>
							</tr>
							<tr>
								<td>업무성격</td>
								<td colspan="3" id="businessnature">
								</td>
							</tr>
							
							<tr>
								<td>증명사진</td>								
								<td colspan="3" class="prepic"><image src="${info.imagepath}" width="195" height="243" id="preimagepath" /></td>
							</tr>
							<tr>
								<td><#if info.idcardpassport==2 >언론사 기자증<#else>주민등록증/여권</#if></td>								
								<td colspan="3" class="prepic"><image src="${info.idphotopath}" width="195" height="132" id="preidphotopath" /></td>
							</tr>
							<#if info.cardtypename=="바이어 증">
							<tr>
								<td>사업자등록증</td>								
								<td colspan="3" class="prepic"><image src="${info.businesslicense}" width="195" height="258" id="prebusinesslicense" /></td>
							</tr>
							<tr>
								<td>구매의향</td>
								<td colspan="3">${info.businesslicense}</td>
							</tr>
							</#if>
							<tr class="papers-edit-bg">
								<td colspan="4" align="center">
									<button type="button" onclick="var index = parent.layer.getFrameIndex(window.name);parent.layer.close(index);" class="layui-btn layui-btn-primary close">돌아가기</button>									
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
