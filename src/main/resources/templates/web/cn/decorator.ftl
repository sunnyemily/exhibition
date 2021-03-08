<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
    <div>当前位置 / ${typeName} / 修改企业资质</div>
</div>
<div class="bg10"></div>
<script>
    var type = "${type}";
    var language = "${language}";
</script>
<div class="apply-content" id="editform">
    <div class="form-head">修改企业资质</div>
    <div class="form-content">
        <fieldset class="layui-elem-field noborder">
            <form class="layui-form  layui-form-pane">
                <input type="hidden" name="id"/>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>审核状态</label>
                    <div class="layui-form-mid layui-word-aux" id="auditInfo"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>用户名</label>
                    <div class="layui-form-mid layui-word-aux"> &nbsp;<script>document.write(window.parent.member.memberUsername)</script>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>社会统一信用代码</label>
                    <div class="layui-input-block">
                        <input type="text" name="organizationcode" required lay-verify="required" placeholder="" autocomplete="off" class="layui-input" disabled/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>单位名称（中文）</label>
                    <div class="layui-input-block">
                        <input type="text" name="chinesename" required lay-verify="required" placeholder="" autocomplete="off" class="layui-input" disabled/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>单位地址（中文）</label>
                    <div class="layui-input-block">
                        <input type="text" name="chineseaddress" required lay-verify="required" placeholder="" autocomplete="off" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>手机</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" required lay-verify="required|phone" placeholder="请输入您的手机号码" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid layui-word-aux">手机号码用作密码找回，发送提示短信。</div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>电话</label>
                    <div class="layui-input-inline">
                        <input type="text" name="tel" required lay-verify="required" placeholder="请输入您的联系电话" autocomplete="off" class="layui-input" disabled>
                    </div>
                    <div class="layui-form-mid layui-word-aux">格式为：国家号-区号-电话号，如：86-451-82340100（横线需确保必须为半角横线）</div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>电子信箱</label>
                    <div class="layui-input-inline">
                        <input type="text" name="email" required lay-verify="required|email" placeholder="请输入您的电子信箱" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid layui-word-aux">请填写真实邮箱地址，邮箱地址将做为密码恢复、信息联络等用途。</div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>联系人</label>
                    <div class="layui-input-block">
                        <input type="text" name="contactperson" required lay-verify="required" placeholder="请输入联系人姓名" autocomplete="off" class="layui-input">
                    </div>
                </div>
				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label"><span>*</span>企业简介</label>
					<div class="layui-input-block">
						<textarea name="companyprofile" required lay-verify="required" placeholder="请输入企业简介" class="layui-textarea"></textarea>
					</div>
				</div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>法人</label>
                    <div class="layui-input-block">
                        <input type="text" name="legalpersonname" required lay-verify="required" placeholder="请输入法人姓名" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span>*</span>法人身份证号</label>
                    <div class="layui-input-block">
                        <input type="text" name="legalpersoncardnumber" required lay-verify="required" placeholder="请输入法人身份证号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <table width="100%" class="layui-table">
					<tr>
						<td>*法人身份证扫描件</td>
						<input type="hidden" name="legalpersoncardpath" id="legalpersoncardpath" value="" lay-verify="required" lay-reqText="必须法人身份证扫描件"/>
						<td width="40%">
							<button type="button" class="layui-btn" id="btnLegalPersonCardPath" style="display:none">
								<i class="layui-icon">&#xe67c;</i>上传图片
							</button>
							<button type="button" class="layui-btn" id="btnLegalPersonCardPathCropper">
								<i class="layui-icon">&#xe67c;</i>裁剪助手
							</button>
							<div class="layui-form-mid layui-word-aux"><br/>格式为JPG，上传文件小于100K，尺寸441*358（宽*高）像素。</div>
						</td>
						<td colspan="2" class="prepic">
							<image src="" width="441" height="358" id="prelegalpersoncardpath"/>
						</td>
					</tr>
                    <tr>
                        <td>*营业执照</td>
                        <input type="hidden" name="buslicensepath" id="buslicensepath" value=""  lay-verify="required" lay-reqText="必须上传营业执照"  />
                        <td width="40%">
                            <button type="button" class="layui-btn" id="btnBusLicensePath" style="display:none">
                                <i class="layui-icon">&#xe67c;</i>上传图片
                            </button>
                            <button type="button" class="layui-btn" id="btnBuslicensepathCropper">
                                <i class="layui-icon">&#xe67c;</i>裁剪助手
                            </button>
                            <div class="layui-form-mid layui-word-aux"><br />格式为JPG，上传文件小于2M，尺寸516*390（宽*高）像素。</div>
                        </td>
                        <td colspan="2" class="prepic"><image src="" width="516" height="390" id="prebuslicensepath" /></td>
                    </tr>
                    <tr>
                        <td>*特装搭建施工准入资质单位申请表</td>
                        <input type="hidden" name="admissionapplicationform" id="admissionapplicationform" value="" lay-verify="required" lay-reqText="必须上传特装搭建施工准入资质单位申请表" />
                        <td width="40%">
                            <button type="button" class="layui-btn" id="btnAdmissionApplicationForm">
                                <i class="layui-icon">&#xe67c;</i>上传文件
                            </button>
                            <div class="layui-form-mid layui-word-aux"><br />此处可上传特装搭建施工准入资质单位申请表,上传文件为DOC和DOCX类型并且大小要在5MB以内。</div>
                        </td>
                        <td colspan="2" class="prepic" id="admissionApplicationFormContainer"></td>
                    </tr>
                    <tr>
                        <td>*制作工场车间的产权证明或租赁合同</td>
                        <input type="hidden" name="workshopcertificate" id="workshopcertificate" value="" lay-verify="required" lay-reqText="必须上传制作工场车间的产权证明或租赁合同" />
                        <td width="40%">
                            <button type="button" class="layui-btn" id="btnWorkshopCertificate">
                                <i class="layui-icon">&#xe67c;</i>上传文件
                            </button>
                            <div class="layui-form-mid layui-word-aux"><br />此处可上传制作工场车间的产权证明或租赁合同,上传文件为DOC和DOCX类型并且大小要在5MB以内。</div>
                        </td>
                        <td colspan="2" class="prepic" id="workshopCertificateContainer"></td>
                    </tr>
                    <tr>
                        <td>*技术力量证明材料</td>
                        <input type="hidden" name="technicalproofdocuments" id="technicalproofdocuments" value="" lay-verify="required" lay-reqText="必须上传技术力量证明材料" />
                        <td width="40%">
                            <button type="button" class="layui-btn" id="btnTechnicalProofDocuments">
                                <i class="layui-icon">&#xe67c;</i>上传文件
                            </button>
                            <div class="layui-form-mid layui-word-aux"><br />此处可上传设计师、工程师的专业资格证书复印件以及其他与展览工程有关工种人员上岗证复印件,上传文件为RAR和ZIP类型并且大小要在15MB以内。</div>
                        </td>
                        <td colspan="2" class="prepic" id="technicalProofDocumentsContainer"></td>
                    </tr>
                    <tr>
                        <td>设计施工能力和资质的其他证明资料</td>
                        <input type="hidden" name="othersupportingdocuments" id="othersupportingdocuments" value="" />
                        <td width="40%">
                            <button type="button" class="layui-btn" id="btnOtherSupportingDocuments">
                                <i class="layui-icon">&#xe67c;</i>上传文件
                            </button>
                            <div class="layui-form-mid layui-word-aux"><br />此处可上传设计施工能力和资质的其他证明资料,上传文件为RAR和ZIP类型并且大小要在15MB以内。</div>
                        </td>
                        <td colspan="2" class="prepic" id="otherSupportingDocumentsContainer"></td>
                    </tr>
                    <tr>
                        <td>往届成稿案例</td>
                        <input type="hidden" name="previouscase" id="previouscase" value="" />
                        <td width="40%">
                            <button type="button" class="layui-btn" id="btnPreviousCase">
                                <i class="layui-icon">&#xe67c;</i>上传文件
                            </button>
                            <div class="layui-form-mid layui-word-aux"><br />此处可上传往届成稿案例,上传文件为RAR和ZIP类型并且大小要在15MB以内。</div>
                        </td>
                        <td colspan="2" class="prepic" id="previousCaseContainer"></td>
                    </tr>
                </table>
                <#if (applyInfo.boothCount > 0) >
                    <input type="hidden" id="edit" value="1"/>
                <#else>
                    <div class="layui-form-item">
                        <div class="layui-input-block form-input">
                            <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">保存</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </#if>
            </form>
        </fieldset>
    </div>
</div>
<style>
    .form-content {
        font-size: 12px;
    }
</style>
<script>
    var slideshow1 = "至少上传1一张轮播图！";
    var slideshow3 = "最多上传3张轮播图！";
    var usernameValidateTips = '用户名只允许为英文，数字和汉字的混合,请检查是否有其他符号！';
    var usernameValidateTips2 = "用户名必须为4-30个字符";
    var passwordValidateTips = '密码必须6到12位，且不能出现空格';
    var passwordValidateTips2 = "两次输入密码不一致";
    var phoneNumber = '请正确填写11位手机号码。';
    var verificationCode = '正在发送验证码';
    var regain60 = "60秒后重新获取";
    var regain = "秒后重新获取";
    var regainCode = "获取验证码";
    var uploadSuccessful = '上传成功';
    var qingXuanZe = "请选择";
    var tishi = "提示";
    var interfaceException = '接口异常';
    var picture6 = "最多只能传6张图片。";
    var previourTitle = "预览";
    var pictureToolTitle = '图片裁剪工具';
    var legalPersonCardNumberTitle = '请输入正确的法人身份证号';
</script>
<script src="/script/validate.js?v=3.3.6"></script>
<script src="/script/decorator.js"></script>
<div id="mutiPictureTemplate" style="display:none">
    <div class="item">
        <img src=""/>
        <div class="fc-upload-cover">
            <i class="ivu-icon fa fa-eye"></i>
            <i class="ivu-icon fa fa-trash"></i>
        </div>
    </div>
</div>
</body>
</html>
