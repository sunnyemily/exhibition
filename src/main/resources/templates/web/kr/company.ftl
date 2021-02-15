<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>현제 위치 / ${typeName} / 업체정보 수정</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
		<div class="form-head">업체정보 수정</div>
		<div class="form-content">
	<fieldset class="layui-elem-field noborder">
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="id" />
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>아이디</label>
		    <div class="layui-form-mid layui-word-aux"> &nbsp;<script>document.write(window.parent.member.memberUsername)</script>
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>사업자등록증 번호</label>
		    <div class="layui-input-block">
		      <input type="text" name="organizationcode" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>업체명(중국어)</label>
		    <div class="layui-input-block">
		      <input type="text" name="chinesename" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" disabled />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>업체명(영어)</label>
		    <div class="layui-input-block">
		      <input type="text" name="engname" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">업체명(러시아어)</label>
		    <div class="layui-input-block">
		      <input type="text" name="russianname" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>업체주소(중국어)</label>
		    <div class="layui-input-block">
		      <input type="text" name="chineseaddress" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>업체주소(영어)</label>
		    <div class="layui-input-block">
		      <input type="text" name="engaddress" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>우편번호</label>
		    <div class="layui-input-block">
		      <input type="text" name="postcode" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>휴대전화</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" required  lay-verify="required" placeholder="휴대전화 번호를 입력하세요." autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">휴대전화 번호는 비밀번호 찾기와 알림 메시지 수신 등에 사용합니다.</div>
	
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>전화번호</label>
		    <div class="layui-input-inline">
		      <input type="text" name="tel" required  lay-verify="required" placeholder="휴대전화 번호를 입력하세요." autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">형식: 국가번-구번호-전화번호 예: 86-451-82340100 (횡선은 반드시 반각횡선이어야 함)</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>팩스</label>
		    <div class="layui-input-inline">
		      <input type="text" name="fax" required  lay-verify="required" placeholder="팩스번호 입력" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">형식: 국가번-구번호-전화번호 예: 86-451-82340100 (횡선은 반드시 반각횡선이어야 함)</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">웹 사이트</label>
		    <div class="layui-input-block">
		      <input type="text" name="website" placeholder="웹 사이트를 입력하세요." autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>이메일</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" required  lay-verify="required|email" placeholder="이메일 주소를 입력하세요." autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">정확한 이메일 주소를 기입해 주세요. 이메일 주소는 비밀번호 찾기, 정보 연락 등에  사용합니다.</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>지자체 소속</label>
		    <div class="layui-input-inline">
		      <select name="country" lay-verify="required" lay-filter="country">
				  <option value="">선택하세요</option>
			  </select>   
		    </div>
		    <div class="layui-input-inline">
		      <select name="province" lay-verify="required" lay-filter="province">
				  <option value="">선택하세요</option>
			  </select>   
		    </div>
		    <div class="layui-input-inline">
		      <select name="city" lay-verify="required">
				  <option value="">선택하세요</option>
			  </select>   
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>책임자</label>
		    <div class="layui-input-block">
		      <input type="text" name="principal" required  lay-verify="required" placeholder="책임자 성함을 입력하세요." autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>담당자</label>
		    <div class="layui-input-block">
		      <input type="text" name="contactperson" required lay-verify="required" placeholder="담당자 성함을 입력하세요." autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">담당자 직무</label>
		    <div class="layui-input-block">
		      <input type="text" name="jobtitle" placeholder="담당자 직무를 입력하세요." autocomplete="off" class="layui-input">
		    </div>
		  </div>
		   <div class="layui-form-item">
		    <label class="layui-form-label">사이버전시장 사이트</label>
		    <div class="layui-input-block">
		      <input type="text" name="url" placeholder="사이버전시장 링크를 기입하세요."  autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>업종분류</label>
		    <div class="layui-input-block">
		      <select name="industryid" lay-verify="">
		    	<#list industries as industry>
		    	<option value="${industry.id}">${industry.chineseName}</option>
		    	</#list>
			  </select>
		   </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label"><span>*</span>참가업체 성질</label>
		    <div class="layui-input-block">
		    	<#list companytypes as type>
		      <input type="radio" name="exhibitorsNatureId" value="${type.id}" title="${type.chineseName}">
		    	</#list>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>경영범위</label>
		    <div class="layui-input-block">
		      <textarea name="busscope" required  lay-verify="required" placeholder="업체 경영범위를 입력하세요." class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">바이어 매칭 요망</label>
		    <div class="layui-input-block">
		      <textarea name="hopecustomers" placeholder="결실을 원하는 바이어 업종범위를 기입하세요." class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>업체소계</label>
		    <div class="layui-input-block">
		      <textarea name="companyprofile" required  lay-verify="required" placeholder="업체소계를 상세히 기입하세요." class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>구매의향</label>
		    <div class="layui-input-block">
		      <textarea name="purchaseintention" required  lay-verify="required" placeholder="구매의향을 입력하세요." class="layui-textarea"></textarea>
		    </div>
		  </div>
				<table width="100%"  class="layui-table">
					<tr>
						<td>* 회사LOGO</td>
						<input type="hidden" name="companylogo" id="companylogo" value=""  lay-verify="required" lay-reqText="회사 LOGO를 반드시 업로드 하셔야 합니다."  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanylogo" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>이미지 업로드
							</button>
								<button type="button" class="layui-btn" id="btnCompanylogoCropper">
								  <i class="layui-icon">&#xe67c;</i>재단조수
								</button>
							<div class="layui-form-mid layui-word-aux"><br />주의:포맷:jpg 파일크기< 100K LOGO 화소크기:200*200(가로*높이).</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="200" height="200" id="precompanylogo" /></td>
					</tr>
					<tr>
						<td>*사업자등록증</td>
						<input type="hidden" name="buslicensepath" id="buslicensepath" value=""  lay-verify="required" lay-reqText="사업자등록증을 반드시 업로드 하셔야 합니다."  />
						<td width="40%">
							<button type="button" class="layui-btn" id="idpic" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>이미지 업로드
							</button>
							<button type="button" class="layui-btn" id="btnBuslicensepathCropper">
							  <i class="layui-icon">&#xe67c;</i>재단조수
							</button>
							<div class="layui-form-mid layui-word-aux"><br />주의:포맷:jpg 파일크기<2M 화소크기:800*1060(가로*높이).</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="390" height="516" id="prebuslicensepath" /></td>
					</tr>
					<tr>
						<td>*회사표지</td>
						<input type="hidden" name="companypicture" id="companypicture" value=""  lay-verify="required" lay-reqText="회사 표지를 반드시 업로드 하셔야 합니다."  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyPicture" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>이미지 업로드
							</button>
							<button type="button" class="layui-btn" id="btnCompanypictureCropper">
							  <i class="layui-icon">&#xe67c;</i>재단조수
							</button>
							<div class="layui-form-mid layui-word-aux"><br />주의:포맷:jpg 파일크기< 2M 화소크기:750*422(가로*높이).</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="390" height="219" id="precompanypicture" /></td>
					</tr>
					<tr>
						<td>홍보 동영상</td>
						<input type="hidden" name="companyvideo" id="companyvideo" value=""  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyVideo">
							  <i class="layui-icon">&#xe67c;</i>동영상 업로드 하세요.
							</button>
							<div class="layui-form-mid layui-word-aux"><br />주의:포맷:MP4 파일크기< 20M 파일 사이즈:750*422(가로*높이).</div>
						</td>
						<td colspan="2" class="prepic" id="videoContainer"><image src="" width="390" height="219" id="prebuslicensepath" /></td>
					</tr>
					<tr>
						<td>*순환홍보 이미지</td>
						<input type="hidden" name="companypictures" id="companypictures" value=""  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyPictures" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>이미지 업로드
							</button>
							<button type="button" class="layui-btn" id="btnCompanypicturesCropper">
							  <i class="layui-icon">&#xe67c;</i>재단조수
							</button>
							<div class="layui-form-mid layui-word-aux"><br />주의:포맷:jpg 파일크기< 2M 화소크기:750*422(가로*높이).업로드 파일은 3장이내 이어야 함.</div>
						</td>
						<td colspan="2">
						<div class="mutipic">
						</div>
						</td>
					</tr>
				</table>
				<#if (applyInfo.boothCount > 0) >
						<input type="hidden" id="edit" value="1" />
				<#else>
					  <div class="layui-form-item">
					    <div class="layui-input-block form-input">
					      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">저장</button>
					      <button type="reset" class="layui-btn layui-btn-primary">재설정</button>
					    </div>
					  </div>
		  		</#if>
		</form>
    </fieldset>
    </div>
</div>
<style>
.form-content{font-size:12px;}
</style>
<script>
var slideshow1="순환 홍보 이미지는 최소 1장 업로드 하셔야 합니다！";
var slideshow3="순환 홍보 이미지는 3장이내로 업로드 하셔야 합니다！";
var usernameValidateTips = '아이디는 영문,숫자,한자의 혼합만 가능합니다！';
var usernameValidateTips2 = "아이디는 반드시 4-30자는 영문,숫자,한자의 혼합만 가능합니다.";
var passwordValidateTips = '비밀번호 반드시 6-12자 이어야 하며 빈칸이 생기면 않됩니다.';
var passwordValidateTips2 = "두번 입력한 비밀번호는 반드시 일치해야 합니다.";
var phoneNumber='11자리 휴대전화 번호를 정확히 입력하세요.';
var verificationCode ='인증번호 보내는 중입니다';
var regain60="60초후 다시 취득합니다.";
var regain="초후 다시 취득합니다.";
var regainCode="인증번호 취득";
var uploadSuccessful='업로드 완료 되었습니다.';
var qingXuanZe="선택하세요.";
var tishi="제시";
var interfaceException='인터페이스 이상이 생겼습니다.';
var picture6="최대 6 장의 그림 만 전달 할 수 있 습 니 다。";
var previourTitle = "미리보기";
var pictureToolTitle = '컷아웃 공구';
</script>
<script src="/script/company.js"></script>
<div id="mutiPictureTemplate" style="display:none">
	<div class="item">
		<img src="" />
		<div class="fc-upload-cover">
			<i class="ivu-icon fa fa-eye"></i>
			<i class="ivu-icon fa fa-trash"></i>
		</div>
	</div>
</div>
</body>
</html>
