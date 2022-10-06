package com.wanda.kyc.service;

import com.wanda.kyc.config.Platform;
import com.wanda.kyc.message.MailInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.wanda.kyc.constant.PlatformConst.PLATFORM_NAME;


@Slf4j
@Service
public class SendMailService {

	@Value("${spring.mail.username}")
	private String platformMail;
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private freemarker.template.Configuration freemarkerConfig;

	@Autowired
	private Platform platform;
	@Autowired
	private LocaleMessageSourceService mService;
	
	/**
	 * 發送信件
	 * @param mailInfo
	 */
	public void sendMail(MailInfo mailInfo) {
		try {
			// 放入平台基本資料
			mailInfo.getTemplateParamMap().put(PLATFORM_NAME, platform.getPlatformName(mailInfo.getLang()));
			
			// 整理要發送的資料
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
			mimeMessageHelper.setTo(mailInfo.getToEmail());
			String title = platform.getPlatformName(mailInfo.getLang());
			mimeMessageHelper.setSubject(title);
			mimeMessageHelper.setText(getTemplateString(mailInfo), true);
			
			// 寄出
			mailSender.send(mimeMessage);
			
			log.info("[發送Email]信箱[{}]發送成功", mailInfo.getToEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取得寄信內文
	 * 
	 * @param mailInfo
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	private String getTemplateString(MailInfo mailInfo) throws IOException, TemplateException {
		String lang = mailInfo.getLang().split("-")[1].toLowerCase();
		String templateName = String.format(mailInfo.getMailTemplateEnum().getTemplateName(), lang);
		Template template = freemarkerConfig.getTemplate(templateName);
		return FreeMarkerTemplateUtils.processTemplateIntoString(template, mailInfo.getTemplateParamMap());
	}

}
