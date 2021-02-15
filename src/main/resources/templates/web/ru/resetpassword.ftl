<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body class="login-body">
<#include 'top.html'>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="body regist-body">
	<div class="regist-path">Ваше нахождение：Главная страница - Найди пароль.</div>
	<div class="regist-form">
	  <div class="regist-form-center">
	    <div style="height:48px;"></div>
	    <fieldset class="layui-elem-field layui-field-title">
		  <legend><span>${typeName}</span>Восстановление пароля - заполните соответствующую информацию</legend>
		  <div class="layui-field-box">
		    Заполните точную информацию: наименование компании (на китайском языке) и электронную почту/мобильный телефон при регистрации. Система отправит новый пароль в вашу электронную почту. Войдите в систему ЭКСПО и изменить свой пароль для входа.
		  </div>
		</fieldset>
		<form class="layui-form  layui-form-pane">
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Наименование компании (на китайском языке)</label>
		    <div class="layui-input-block">
		      <input type="text" name="companyName" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Вариант восстановления пароля</label>
		    <div class="layui-input-inline">
		      <input type="radio" name="resetType" value="email" title="Электронная почта"  lay-filter="resetType" />
			  <input type="radio" name="resetType" value="phone" title="Мобильный телефон" checked lay-filter="resetType" >
		    </div>
		    	<div class="layui-form-mid layui-word-aux">Заполните адрес электронную почту при регистрации. Система отправит новый пароль в вашу электронную почту.</div>
		  </div>
		  <div class="layui-form-item" id="email" style="display:none">
		    <label class="layui-form-label"><span>*</span>Электронная почта</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" placeholder="Заполните Вашу электронную почту" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">Заполните адрес электронную почту при регистрации. Система отправит новый пароль в вашу электронную почту.</div>
		  </div>
		  <div class="layui-form-item" id="phone">
		    <label class="layui-form-label"><span>*</span> Номер мобильного телефона</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" placeholder="Пожалуйста, заполните номер мобильного телефона при регистрирации" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">Заполните номер мобильного телефона при регистрации. Система отправит новый пароль в ваш мобильник.</div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">Отправить</button>
		      <button type="reset" class="layui-btn layui-btn-primary">переустановить</button>
		    </div>
		  </div>
		</form>
		<div style="height:45px"></div>
	  </div>
	</div>
</div>
<script>
var phoneNoEmpty="Номер мобильного телефона нельзя быть пустым";
var emillNoEmpty="Электронная почта нельзя быть пустой";
var submitInformation='В статусе подачи информации';
var loginButtonTitle = "Войти";
var indexButtonTitle = "Домашняя страница";
</script>
<script src="/script/reset.js"></script>
<#include 'foot.html'>
</body>
</html>
