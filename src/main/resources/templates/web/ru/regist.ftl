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
	<div class="regist-path">Ваше нахождение:：<a href="/${language}/default.html">Главная страница</a> -Регистрация-Заполните информацию о компании</div>
	<div class="regist-form">
	  <div class="regist-form-center">
	    <div style="height:48px;"></div>
	    <fieldset class="layui-elem-field layui-field-title">
		  <legend><span>${typeName}</span>Регистрация-Заполните информацию о компании</legend>
		  <div class="layui-field-box">
		   Точно заполните нижеследующую информацию о компании. Информация должна быть реальной (достоверной) и законной. Опубликовавший несет полную ответственность за реальность (достоверность) информации. После рассмотрения информация не изменится. После регистрации система автоматически отправит логин и пароль на Ваш мобильный телефон. Электронная почта будет использоваться для восстановления пароля. Заполните свой реальный адрес электронной почты. Разделы с знаком * - это необходимо заполнить.
		  </div>
		</fieldset>
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="session" value="${exhibitionSessionId}" />
		<input type="hidden" name="memberId" value="0" />
		<input type="hidden" name="memberType" value="${typeId}" />
		<input type="hidden" name="isActive" id="isActive" value="0" />
		<input type="hidden" name="buslicensepath" value="" />
		<input type="hidden" name="companypicture" value="" />
		<input type="hidden" name="companyvideo" value="" />
		<input type="hidden" name="companypictures" value="" />
		<input type="hidden" name="relateddocumentspath" value="" />
		<input type="hidden" name="companylogo" value="" />
		<div class="layui-tab">
		  <ul class="layui-tab-title" style="display:none">
		    <li class="layui-this">Подтверждение названия компании</li>
		    <li>Сообщение подтверждения</li>
		    <li>Другая регистрационная информация</li>
		  </ul>
		  <div class="layui-tab-content">
		    <div class="layui-tab-item layui-show">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Наименование компании ( на китайском языке)</label>
			    <div class="layui-input-block">
			      <input type="text" name="chinesename" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <div class="layui-form-item" style="text-align:center;">
			      <button class="layui-btn" id="btnValidate">Проверка</button>
			  </div>
		  	</div>
		    <div class="layui-tab-item">
		  	<#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span></span>Ответственное лицо</label>
			    <div class="layui-input-block">
			      <input type="text" name="testPrincipal" placeholder="Контактное лицо" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			</#if>		    	
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Контактное лицо</label>
			    <div class="layui-input-block">
			      <input type="text" name="testContactperson" placeholder="Ф.И.О. контактного лица" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item" style="text-align:center;">
			      <button class="layui-btn" id="btnSafeValidate">Проверка</button>
			  </div>
		    </div>
		    <div class="layui-tab-item">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Логин</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberUsername" lay-verify="username" placeholder="Заполните регистрационный логин" autocomplete="off" class="layui-input">
			    </div>
				<div class="layui-form-mid layui-word-aux">Пароль состоит из букв a-z, цифр 0-9, а длина пароля составляет 4-30 сочетатий из буквы и цифры.</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Пароль</label>
			    <div class="layui-input-inline">
			      <input type="password" name="memberPassword" lay-verify="password" placeholder="Пароль" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">Пароль не короче 6 сочетатий из буквы и цифры</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Подтвердите пароль</label>
			    <div class="layui-input-inline">
			      <input type="password" name="ValidateMemberPassword" lay-verify="required|passwordEqual" placeholder="Двуктратный пароль должны быть одинаковыми." autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">Двуктратный пароль должны быть одинаковыми.</div>
			  </div>
			  <#if type=="exhibitor">
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label"><span>*</span>Вариант участия   </label>
			    <div class="layui-input-block">			      
			      <b><input type="radio" name="exhibitionType" value="2" title="Участие оффлайн" checked lay-filter="exhibitionType"></b>
			      <input type="radio" name="exhibitionType" value="1" title="Участвуйте только онлайн" lay-filter="exhibitionType">
			    </div>
			  </div>
			  <div class="layui-form-item" pane id="exhibitionnArea" style="display:none">
			    <label class="layui-form-label"><span>*</span>Выберите выставочный зал</label>
			    <div class="layui-input-block">	
					<select class="form-control input-outline" name='tradinggroupid' id="tradinggroupid">
						<option value="">Пожалуйста, выбирайте</option>
				      	<#list exhibitionArea as area>
						<option value="${area.id}" >${(area.russianame!="")?string(area.russianame,area.name)}</option>
						</#list>
					</select>
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Единый код общественной кредитоспособности</label>
			    <div class="layui-input-block">
			      <input type="text" name="organizationcode" lay-verify="required" placeholder="Китайский экспонент заполнит Единый код общественной кредитоспособности, а инностранный участник заполнит номер свидетельства о государственной регистрации" autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Наименование компании ( на китайском языке)</label>
			    <div class="layui-input-block">
			      <input type="text" name="validChinesename" lay-verify="required" placeholder="" disabled autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Наименование компании  ( на английском)</label>
			    <div class="layui-input-block">
			      <input type="text" name="engname" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">Наименование компании  ( на русском)</label>
			    <div class="layui-input-block">
			      <input type="text" name="russianname" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Адрес  ( на китайском)</label>
			    <div class="layui-input-block">
			      <input type="text" name="chineseaddress" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Адрес ( на английском)</label>
			    <div class="layui-input-block">
			      <input type="text" name="engaddress" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Почтовый индекс</label>
			    <div class="layui-input-block">
			      <input type="text" name="postcode" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Мобильный телефон</label>
			    <div class="layui-input-inline">
			      <input type="text" name="phone" lay-verify="required" placeholder="Заполните номер мобильного телефона" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">Номер мобильного телефона используется для восстановления пароля и получения SMS.</div>
		
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Телефон</label>
			    <div class="layui-input-inline">
			      <input type="text" name="tel" lay-verify="required" placeholder="Заполните контактный телефон" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">В формате: код страны-код города- номер телефона, например: 86-451-82340100 (горизонтальная линия "-" должна быть полушириной.).</div>
			  </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Факс</label>
			    <div class="layui-input-inline">
			      <input type="text" name="fax" lay-verify="required" placeholder="Заполните номер факса" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">В формате: код страны-код города- номер телефона, например: 86-451-82340100 (горизонтальная линия "-" должна быть полушириной.).</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">Адрес сайта</label>
			    <div class="layui-input-block">
			      <input type="text" name="website" placeholder="Заполните адрес сайта" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Электронная почта</label>
			    <div class="layui-input-inline">
			      <input type="text" name="email"  lay-verify="required|email" placeholder="Заполните Вашу электронную почту" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-input-inline">
			      <button id="btnActivation" type="button" class="layui-btn layui-btn-fluid">Получить код подтверждения</button>
			    </div>
			    	<div class="layui-form-mid layui-word-aux">Пожалуйста, заполните реальную электронную почту, которая будет использоваться для восстановления пароля и связи.</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Код подтверждения электронной почты</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberActivationCode" lay-verify="required" placeholder="Пожалуйста, заполните код подтверждения электронной почты" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Страна / Регион</label>
			    <div class="layui-input-inline">
			      <select name="country" lay-verify="required" lay-filter="country" placeholder="выбрать страну">
					  <option value="">Пожалуйста, выбирайте</option>
				  </select>   
			    </div>
			    <div class="layui-input-inline">
			      <select name="province" lay-verify="required" lay-filter="province" placeholder="выбрать регион">
					  <option value="">Пожалуйста, выбирайте</option>
				  </select>   
			    </div>
			    <div class="layui-input-inline">
			      <select name="city" lay-verify="required" placeholder="выбрать город">
					  <option value="">Пожалуйста, выбирайте</option>
				  </select>   
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Ответственное лицо</label>
			    <div class="layui-input-block">
			      <input type="text" name="principal" lay-verify="required" placeholder="Ф.И.О. ответственного лица" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Контактное лицо</label>
			    <div class="layui-input-block">
			      <input type="text" name="contactperson" lay-verify="required" placeholder="Ф.И.О. контактного лица" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <#if type=="exhibitor">
			   <div class="layui-form-item">
			    <label class="layui-form-label">Адрес виртуального выставочного павильона</label>
			    <div class="layui-input-block">
			      <input type="text" name="url" placeholder="请输入虚拟展厅地址" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label">Должность контактного лица</label>
			    <div class="layui-input-block">
			      <input type="text" name="jobtitle" placeholder="Заполните должность контактного лица" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Отрасль деятельности</label>
			    <div class="layui-input-block">
			      <select name="industryid" lay-verify="">
			    	<#list industries as industry>
			    	<option value="${industry.id}">${industry.russianName}</option>
			    	</#list>
				  </select>
			   </div>
			  </div>
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label"><span>*</span>Свойство компании</label>
			    <div class="layui-input-block">
			    	<#list companytypes as type>
			      <input type="radio" name="exhibitorsNatureId" value="${type.id}" title="${type.russianName}">
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
			    <label class="layui-form-label">Клиент, который желаете познакомиться</label>
			    <div class="layui-input-block">
			      <textarea name="hopecustomers" placeholder="Заполните клиент, с которым вы желаете познакомиться." class="layui-textarea"></textarea>
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
			  </#if>
			  <div class="layui-form-item">
			    <div class="layui-input-block form-input">
			      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">Отправить регистрацию</button>
			      <button type="reset" class="layui-btn layui-btn-primary">Переустановить</button>
			    </div>
			  </div>
		    </div>
		  </div>
		</div>
		</form>
		<div style="height:45px"></div>
	  </div>
	</div>
</div>
<script>
var selectTips = "Пожалуйста, выбирайте";
var onlineSelectTips = "Необходимо выбрать тип онлайн-выставочного зала";
var registSubmitLoadingTips = "Регистрационная информация в процессе подачи";
var registSuccessTips = "Регистрация успешно завершена! Хотите войти в систему?";
var loginButtonTitle = "Войти";
var indexButtonTitle = "Домашняя страница";
var activeConfirmTips = "Подтвердите, нужно ли активироват";
var userNameValidateTips = "Логин может быть только на английском, сочетанием цифр и китайских иероглифов. Проверьте, есть ли другие знаки!";
var userNameLengthTips = "Логин должен состоять из 4-30 символов.";
var passwordCompareTips = "'Двуктратный пароль не одинаковый'";
var companyNameValidateTips = 'Пожалуйста, заполните точное название компании';
var sendValidateTips = "Код подтверждения в отправлении";
var phoneValidateTips = "Пожалуйста, заполните точный 11-значный номер мобильного телефона";
var emailValidateTips = "Пожалуйста, заполните точный адрес электронной почты.";
var secondCountTips = "Через секунд повторно получить";
var getValidateCodeTitle = "Получить код подтверждения";
var registLoadingTitle = "В активации аккаунта.";
var activateSuccessTips = "Активация прошла успешно. Войдите в систему по новому паролю. Вы хотите войти в систему?";
var companyNoNullValidateTips = "Пожалуйста, заполните название компании";
var companyValidateTips = "Название компании в статусе подверждения";
var historyValidateTips = "Подтверждение предыдущей информации";
var contactorNoNullTips = "Ф.И.О. контактного лица";
var responserNoNullTips = "Ф.И.О. ответственного лица";
var comapnyHistoryValidateTips = "Информация компании а статусе подтверждения.";
var historySuccessTips = "Предыдущая информация была успешно извлечена, продолжайте дополнить другую информацию.";
var onlineOnlyTips = "Здравствуйте! Вы выбрали участие только в онлайн-выставке, и поэтому Вы не сможете подать заявку на оффлайн-стенд и бейдж. Если Вы хотите участвовать в оффлайн режиме, выберите «Участие оффлайн ».";
var passwordValidateTips = 'Пароль должен состоять из 6–12 цифр без пробелов.';
</script>
<script src="/script/common.js"></script>
<script src="/script/regist.js"></script>
<#include 'foot.html'>
</body>
</html>
