<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>Текущее нахождение / ${typeName} / Исправлять информацию компания</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
		<div class="form-head">Исправлять информацию компания</div>
		<div class="form-content">
	<fieldset class="layui-elem-field noborder">
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="id" />
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Логин</label>
		    <div class="layui-form-mid layui-word-aux"> &nbsp;<script>document.write(window.parent.member.memberUsername)</script>
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Единый код общественной кредитоспособности</label>
		    <div class="layui-input-block">
		      <input type="text" name="organizationcode" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Наименование компании (на китайском языке)</label>
		    <div class="layui-input-block">
		      <input type="text" name="chinesename" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" disabled />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Наименование компании (на английском языке)</label>
		    <div class="layui-input-block">
		      <input type="text" name="engname" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">Наименование компании (на русском языке)</label>
		    <div class="layui-input-block">
		      <input type="text" name="russianname" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Адрес компании (на китайском языке)</label>
		    <div class="layui-input-block">
		      <input type="text" name="chineseaddress" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Адрес компании (на английском языке)</label>
		    <div class="layui-input-block">
		      <input type="text" name="engaddress" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Индекс</label>
		    <div class="layui-input-block">
		      <input type="text" name="postcode" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Номер мобильного телефона</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" required  lay-verify="required" placeholder="Заполните номер мобильного телефона" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">Номер мобильного телефона используется для восстановления пароля и получения SMS.</div>
	
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Телефон</label>
		    <div class="layui-input-inline">
		      <input type="text" name="tel" required  lay-verify="required" placeholder="Заполните контактный телефон" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">В формате: код страны-код города- номер телефона, например: 86-451-82340100 (горизонтальная линия "-" должна быть полушириной.).</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Факс</label>
		    <div class="layui-input-inline">
		      <input type="text" name="fax" required  lay-verify="required" placeholder="Заполните номер факса" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">В формате: код страны-код города- номер телефона, например: 86-451-82340100 (горизонтальная линия "-" должна быть полушириной.).</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">Адрес сайта</label>
		    <div class="layui-input-block">
		      <input type="text" name="website" placeholder="Заполните адрес сайта" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Электронная почта</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" required  lay-verify="required|email" placeholder="Заполните Вашу электронную почту" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">Пожалуйста, заполните реальную электронную почту, которая будет использоваться для восстановления пароля и связи.</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Провинция</label>
		    <div class="layui-input-inline">
		      <select name="country" lay-verify="required" lay-filter="country">
				  <option value="">Пожалуйста, выбирайте</option>
			  </select>   
		    </div>
		    <div class="layui-input-inline">
		      <select name="province" lay-verify="required" lay-filter="province">
				  <option value="">Пожалуйста, выбирайте</option>
			  </select>   
		    </div>
		    <div class="layui-input-inline">
		      <select name="city" lay-verify="required">
				  <option value="">Пожалуйста, выбирайте</option>
			  </select>   
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Ответственное лицо</label>
		    <div class="layui-input-block">
		      <input type="text" name="principal" required  lay-verify="required" placeholder="Ф.И.О. ответственного лица" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Контактное лицо</label>
		    <div class="layui-input-block">
		      <input type="text" name="contactperson" required lay-verify="required" placeholder="Ф.И.О. контактного лица" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">Должность контактного лица</label>
		    <div class="layui-input-block">
		      <input type="text" name="jobtitle" placeholder="Заполните должность контактного лица" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		   <div class="layui-form-item">
		    <label class="layui-form-label">Адрес виртуального выставочного павильона</label>
		    <div class="layui-input-block">
		      <input type="text" name="url" placeholder="Пожалуйста, заполните адрес виртуального выставочного зала"  autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Род отрасли</label>
		    <div class="layui-input-block">
		      <select name="industryid" lay-verify="">
		    	<#list industries as industry>
		    	<option value="${industry.id}">${industry.chineseName}</option>
		    	</#list>
			  </select>
		   </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label"><span>*</span>Свойство экспонета</label>
		    <div class="layui-input-block">
		    	<#list companytypes as type>
		      <input type="radio" name="exhibitorsNatureId" value="${type.id}" title="${type.chineseName}">
		    	</#list>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>Сфера деятельности</label>
		    <div class="layui-input-block">
		      <textarea name="busscope" required  lay-verify="required" placeholder="Заполните  сферу деятельности компании" class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">С каким партнером вы хотите познакомиться?</label>
		    <div class="layui-input-block">
		      <textarea name="hopecustomers" placeholder="Пожалуйста, заполните клиент, с которым желаете познакомиться" class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>Краткое описание компании</label>
		    <div class="layui-input-block">
		      <textarea name="companyprofile" required  lay-verify="required" placeholder="Заполните краткое описание компании" class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>Намерение закупки</label>
		    <div class="layui-input-block">
		      <textarea name="purchaseintention" required  lay-verify="required" placeholder="Заполните намерение закупки" class="layui-textarea"></textarea>
		    </div>
		  </div>
				<table width="100%"  class="layui-table">
					<tr>
						<td>*logo компании</td>
						<input type="hidden" name="companylogo" id="companylogo" value=""  lay-verify="required" lay-reqText="Необходимо загрузить логотип компании"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanylogo" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>Загрузите картинки
							</button>
								<button type="button" class="layui-btn" id="btnCompanylogoCropper">
								  <i class="layui-icon">&#xe67c;</i>Ассистент по резке
								</button>
							<div class="layui-form-mid layui-word-aux"><br />Примечание: в формате jpg, размером меньше 100 КБ, в разрешении 200 (ш) на 200 (в) пикселей.</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="200" height="200" id="precompanylogo" /></td>
					</tr>
					<tr>
						<td>*Свидетельство о государственной регистрации</td>
						<input type="hidden" name="buslicensepath" id="buslicensepath" value=""  lay-verify="required" lay-reqText="Необходимо загрузить  Свидетельство о государственной регистрации"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="idpic" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>Загрузите картинки
							</button>
							<button type="button" class="layui-btn" id="btnBuslicensepathCropper">
							  <i class="layui-icon">&#xe67c;</i>Ассистент по резке
							</button>
							<div class="layui-form-mid layui-word-aux"><br />Примечание: в формате jpg, размером меньше 2 МБ, в разрешении 800 (ш) на 1060 (в) пикселей</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="390" height="516" id="prebuslicensepath" /></td>
					</tr>
					<tr>
						<td>*Обложка компании</td>
						<input type="hidden" name="companypicture" id="companypicture" value=""  lay-verify="required" lay-reqText="Необходимо загрузить обложку компании"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyPicture" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>Загрузите картинки
							</button>
							<button type="button" class="layui-btn" id="btnCompanypictureCropper">
							  <i class="layui-icon">&#xe67c;</i>Ассистент по резке
							</button>
							<div class="layui-form-mid layui-word-aux"><br />Примечание: в формате jpg, размером меньше 2 МБ, в разрешении 750 (ш) на 422 (в) пикселей.</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="390" height="219" id="precompanypicture" /></td>
					</tr>
					<tr>
						<td>Рекламный ролик</td>
						<input type="hidden" name="companyvideo" id="companyvideo" value=""  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyVideo">
							  <i class="layui-icon">&#xe67c;</i>Загрузить видео
							</button>
							<div class="layui-form-mid layui-word-aux"><br />Примечание: в формате MP4, размером меньше 20 МБ, в разрешении 750 (ш) на 422 (в) пикселей</div>
						</td>
						<td colspan="2" class="prepic" id="videoContainer"><image src="" width="390" height="219" id="prebuslicensepath" /></td>
					</tr>
					<tr>
						<td>*Карусель картинки</td>
						<input type="hidden" name="companypictures" id="companypictures" value=""  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyPictures" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>Загрузите картинки
							</button>
							<button type="button" class="layui-btn" id="btnCompanypicturesCropper">
							  <i class="layui-icon">&#xe67c;</i>Ассистент по резке
							</button>
							<div class="layui-form-mid layui-word-aux"><br />Примечание: в формате jpg, размером меньше 2 МБ, количеством меньше 3 листа, в разрешении 750 (ш) на 422 (в) пикселей.</div>
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
					      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">Сохранить</button>
					      <button type="reset" class="layui-btn layui-btn-primary">переустановить</button>
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
var slideshow1="Загрузите не менее 1 фотография！";
var slideshow3="Загрузите не более 3 фотографии！";
var usernameValidateTips = 'Логин может быть только на английском, сочетанием цифр и китайских иероглифов. Проверьте, есть ли другие знаки!';
var usernameValidateTips2 = "Логин должен состоять из 4-30 символов.";
var passwordValidateTips = 'Пароль должен состоять из 6–12 цифр без пробелов.';
var passwordValidateTips2 = "Двуктратный пароль не одинаковый";
var phoneNumber='Пожалуйста, заполните точный 11-значный номер мобильного телефона。';
var verificationCode ='В отправлении кода подтверждения';
var regain60="Повторно получить через 60 секунд";
var regain="Повторно получить через секунд";
var regainCode="Получить код подтверждения";
var uploadSuccessful='Загрузить успешно';
var qingXuanZe="Пожалуйста, выбирайте";
var tishi="Напоминать";
var interfaceException='Интерфейс в исключении';
var picture6=" максимум 6 фотографий。";
var previourTitle = "Предварительный просмотр";
var pictureToolTitle = 'Инструмент обрезки фотография';
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
