package com.wanda.kyc.service;

import com.wanda.kyc.message.MailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class AsyncService {

	@Autowired
	private SendMailService sendMailService;

	@Async
	public void sendMail(MailInfo mailInfo) {
		sendMailService.sendMail(mailInfo);
	}

}
