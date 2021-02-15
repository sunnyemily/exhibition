<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>현제위치 / ${typeName} / 정부단체 정보 수정</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
		<div class="form-head">정부단체 정보 수정</div>
		<div class="form-content">
	<fieldset class="layui-elem-field noborder">
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="id" value="${agent.id}" />
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>성함</label>
		    <div class="layui-input-block">
		      <input type="text" name="name" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.name}" />
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label"><span>*</span>성별</label>
		    <div class="layui-input-block">
				<input type="radio" name="sex" value="0" title="남"  <#if agent.sex==0>checked</#if>  />
				<input type="radio" name="sex" value="1" title="여"  <#if agent.sex==1>checked</#if> />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>엄체명</label>
		    <div class="layui-input-block">
		      <input type="text" name="companyname" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.companyname}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>직무</label>
		    <div class="layui-input-block">
		      <input type="text" name="jobtitle" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.jobtitle}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>휴대전화</label>
		    <div class="layui-input-block">
		      <input type="text" name="phone" required  lay-verify="required|phone" placeholder="휴대전화 번호를 입력하세요." autocomplete="off" class="layui-input" value="${agent.phone}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>책임자휴대전화</label>
		    <div class="layui-input-inline">
		      <input type="text" name="areaphone" required  lay-verify="required|phone" placeholder="请输入展区负责人手机号" autocomplete="off" class="layui-input" value="${agent.areaphone}" />
		    </div>
		    <div class="layui-form-mid layui-word-aux">휴대전화번호는 비밀번호 찾기와 알림 메시지 수신 등에 사용합니다.</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>전화번호</label>
		    <div class="layui-input-inline">
		      <input type="text" name="tel" required  lay-verify="required" placeholder="휴대전화 번호를 입력하세요." autocomplete="off" class="layui-input" value="${agent.tel}" />
		    </div>
		    	<div class="layui-form-mid layui-word-aux">형식: 국가번-구번호-전화번호 예: 86-451-82340100 (횡선은 반드시 반각횡선이어야 함)</div>
		  </div>
		  <table>
			<tr>
				<td>사진</td>
						<input type="hidden" name="imagepath" lay-verify="required" lay-reqText="请上传营业执照" id = "imagepath"  value="${agent.imagepath}" />
				<td width="40%">
					<button type="button" class="layui-btn" id="btnimagepathCropper">
					  <i class="layui-icon">&#xe67c;</i>재단조수
					</button>
					<div class="layui-form-mid layui-word-aux"><br />대리인 증명사진을 업로드하세요.포맷: jpg 파일 크기<2M증명 화소 크기: 390*487(가로*높이).</div>
				</td>
				<td colspan="2" class="prepic"><image src="" width="390" height="487" id="preimagepath" /></td>
			</tr>
		</table>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">저장</button>
		      <button type="reset" class="layui-btn layui-btn-primary">재설정</button>
		    </div>
		  </div>
		</form>
    </fieldset>
    </div>
</div>
<style>
.form-content{font-size:12px;}
</style>
<script>
var submitInfo='등록 정보 제출중';
var usernameValidateTips = '아이디는 영문,숫자,한자의 혼합만 가능합니다.';
var usernameValidateTips2 = "아이디는 반드시 4-30자는 영문,숫자,한자의 혼합만 가능합니다.";
var passwordValidateTips = '비밀번호 반드시 6-12자 이어야 하며 빈칸이 생기면 않됩니다.';
var passwordValidateTips2 = "두번 입력한 비밀번호는 반드시 일치해야 합니다.";
var pictureToolTitle = '컷아웃 공구';
</script>
<script src="/script/agent.js"></script>
</body>
</html>
