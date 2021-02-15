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
								<td>氏名</td>
								<td width="40%">${info.name}</td>
								<td>性别</td>
								<td>
									${info.sexname}
      							</td>
							</tr>
							<tr>
								<td>国／地域</td>
								<td>
									${info.countryAndprovince}
								</td>
								<td>職務</td>
								<td>${info.jobtitle}</td>
							</tr>
							<tr>
								<td>会社名称</td>
								<td>${info.companyname}</td>
								<td>証明書の種類</td>
								<td>${info.cardtypename}</td>
							</tr>
							<tr>
								<td>身分証／パスポート／記者証</td>
								<td>${info.idcardpassportname}<td>証明書番号</td>
								<td>${info.cardnumber}</td>
							</tr>
							<tr>
								<td>郵便番号</td>
								<td>${info.postcode}</td>
								<td>住所</td>
								<td>${info.address}</td>
							</tr>
							<tr>
								<td>会社URL</td>
								<td colspan="3">${info.website}</td>
							</tr>
							<tr>
								<td>携帯電話番号</td>
								<td>${info.phone}</td>
								<td>Eメール</td>
								<td>${info.email}</td>
							</tr>
							<tr>
								<td>電話</td>
								<td>${info.tel}</td>
								<td>ファックス</td>
								<td>${info.fax}</td>
							</tr>
							
							<tr>
								<td>業務分野</td>
								<td>${info.industryname}</td>
								<td>参加役割</td>
								<td>${info.participantsname}</td>
							</tr>
							<tr>
								<td>参加目的</td>
								<td colspan="3" id="purpose">
								</td>
							</tr>
							<tr>
								<td>あなたが見学したい展示エリア</td>
								<td colspan="3">${info.zqname}</td>
							</tr>
							<tr>
								<td>あなたはどのように展示会を知りましたか</td>
								<td colspan="3" id="knowexhibition">
								</td>
							</tr>
							<tr>
								<td>業務の性質</td>
								<td colspan="3" id="businessnature">
								</td>
							</tr>
							
							<tr>
								<td>証明書類の写真</td>								
								<td colspan="3" class="prepic"><image src="${info.imagepath}" width="195" height="243" id="preimagepath" /></td>
							</tr>
							<tr>
								<td><#if info.idcardpassport==2 >新聞記者証<#else>身分証明書（パスポート）</#if></td>								
								<td colspan="3" class="prepic"><image src="${info.idphotopath}" width="195" height="132" id="preidphotopath" /></td>
							</tr>
							<#if info.cardtypename=="采购商证">
							<tr>
								<td>営業許可証</td>								
								<td colspan="3" class="prepic"><image src="${info.businesslicense}" width="195" height="258" id="prebusinesslicense" /></td>
							</tr>
							<tr>
								<td>購入意向</td>
								<td colspan="3">${info.businesslicense}</td>
							</tr>
							</#if>
							<tr class="papers-edit-bg">
								<td colspan="4" align="center">
									<button type="button" onclick="var index = parent.layer.getFrameIndex(window.name);parent.layer.close(index);" class="layui-btn layui-btn-primary close">もどる</button>									
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
