<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	
	<div>Текущее нахождение / изменить пароль</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content">
	<fieldset class="layui-elem-field noborder">
	  <form class="layui-form layui-form-pane" action="">
		<div class="form-head">Изменить пароль</div>
		<div class="form-content">
		  <div class="layui-form-item">
		    <label class="layui-form-label">Прежний паорль</label>
		    <div class="layui-input-block">
		      <input type="password" name="oldPassword" required lay-verify="required|password" placeholder="Пароль не должен быть короче 6 цифр." autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">Новый пароль</label>
		    <div class="layui-input-block">
		      <input type="password" name="newPassword" required lay-verify="required|password" placeholder="Пароль не должен быть короче 6 цифр." autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">Подтвердить пароль</label>
		    <div class="layui-input-block">
		      <input type="password" name="ValidateMemberPassword" required lay-verify="required|passwordEqual" placeholder="Двукратный пароль должен быть одинаковым." autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">Сохранить</button>
		    </div>
		  </div>
		</div>
      </form>
    </fieldset>
</div>
<script>
var submitInformation='Регистрационная информация в процессе подачи';
var loginAgain="Корректив прошел успешно, пожалуйста, войдите снова.";
var passwordValidateTips = 'Пароль должен состоять из 6–12 цифр без пробелов.';
var passwordValidateTips2 = "Двуктратный пароль не одинаковый";
</script>
<script src="/script/password.js"></script>
</body>
</html>
