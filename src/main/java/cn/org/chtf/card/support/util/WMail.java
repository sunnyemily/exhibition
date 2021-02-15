package cn.org.chtf.card.support.util;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import cn.org.chtf.card.manage.basicsetting.pojo.BasicSetting;
import cn.org.chtf.card.manage.basicsetting.service.BasicSettingService;

public class WMail extends JavaMailSenderImpl {
	
	public WMail(BasicSettingService basicService){
		super();
		ResultModel result = basicService.getBasicSetting(1);
		BasicSetting basic = (BasicSetting)result.getResult();
		this.setHost(basic.getBsSmtp());
		this.setPort(587);
		this.setUsername(basic.getBsMailEmail());
		this.setPassword(basic.getBsMailPassword());
	}
	/**
	 * 给单人发送简单邮件。
	 * @param to
	 * @param title
	 * @param content
	 * @throws MailException
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	public void send(String to,String title,String content) throws MailException, MessagingException, UnsupportedEncodingException {
	    MimeMessage message = createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
		helper.setFrom(new InternetAddress(this.getUsername(), "系统邮件", "UTF-8"));
		helper.setTo(to);
		helper.setText(content,true);
		helper.setSubject(title);
		send(message);
	}
	
}
