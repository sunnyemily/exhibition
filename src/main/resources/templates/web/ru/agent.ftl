<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>Текущее нахождение  / ${typeName} / Исправить информацию деловой группы</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
		<div class="form-head">Исправить информацию деловой группы</div>
		<div class="form-content">
	<fieldset class="layui-elem-field noborder">
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="id" value="${agent.id}" />
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Ф.И.О</label>
		    <div class="layui-input-block">
		      <input type="text" name="name" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.name}" />
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label"><span>*</span>Пол</label>
		    <div class="layui-input-block">
				<input type="radio" name="sex" value="0" title="М."  <#if agent.sex==0>checked</#if>  />
				<input type="radio" name="sex" value="1" title="Ж."  <#if agent.sex==1>checked</#if> />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Наименование компании</label>
		    <div class="layui-input-block">
		      <input type="text" name="companyname" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.companyname}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Должность</label>
		    <div class="layui-input-block">
		      <input type="text" name="jobtitle" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.jobtitle}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Номер мобильного телефона</label>
		    <div class="layui-input-block">
		      <input type="text" name="phone" required  lay-verify="required|phone" placeholder="Заполните номер мобильного телефона" autocomplete="off" class="layui-input" value="${agent.phone}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Ответственное лицо Номер мобильного телефона</label>
		    <div class="layui-input-inline">
		      <input type="text" name="areaphone" required  lay-verify="required|phone" placeholder="Заполните номер мобильного телефона ответственного лица выставочного сектора" autocomplete="off" class="layui-input" value="${agent.areaphone}" />
		    </div>
		    <div class="layui-form-mid layui-word-aux">Номер мобильного телефона используется в отправке и получении  SMS для восстановлением пароля</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Телефон</label>
		    <div class="layui-input-inline">
		      <input type="text" name="tel" required  lay-verify="required" placeholder="Заполните Ваш номер мобильного телефона" autocomplete="off" class="layui-input" value="${agent.tel}" />
		    </div>
		    	<div class="layui-form-mid layui-word-aux">В формате: код страны-код города-номер телефна. Например, 86-451-82340100 (  горизонтальная линия "-" должна быть полушириной.</div>
		  </div>
		  <table>
			<tr>
				<td>Фото для бейджа</td>
						<input type="hidden" name="imagepath" lay-verify="required" lay-reqText="Загрузите Свидетельство о государственной регистрации" id = "imagepath"  value="${agent.imagepath}" />
				<td width="40%">
					<button type="button" class="layui-btn" id="btnimagepathCropper">
					  <i class="layui-icon">&#xe67c;</i>Ассистент по резке
					</button>
					<div class="layui-form-mid layui-word-aux"><br />Отправляйте фото  агента: в формате jpg, размером меньше 2 МБ, в разрешении 390 (ш) на 487 (в) пикселей</div>
				</td>
				<td colspan="2" class="prepic"><image src="" width="390" height="487" id="preimagepath" /></td>
			</tr>
		</table>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">Сохранить</button>
		      <button type="reset" class="layui-btn layui-btn-primary">переустановить</button>
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
var submitInfo='Регистрационная информация в процессе подачи';
var usernameValidateTips = 'Логин может быть только на английском, сочетанием цифр и китайских иероглифов. Проверьте, есть ли другие знаки!';
var usernameValidateTips2 = "Логин должен состоять из 4-30 символов.";
var passwordValidateTips = 'Пароль должен состоять из 6–12 цифр без пробелов.';
var passwordValidateTips2 = "Двуктратный пароль не одинаковый";
var pictureToolTitle = 'Инструмент обрезки фотография';
</script>
<script src="/script/agent.js"></script>
</body>
</html>
