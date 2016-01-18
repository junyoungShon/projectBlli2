package kr.co.blli.model.admin;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import kr.co.blli.model.vo.BlliMailVO;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

@Service
public class AdminServiceImpl implements AdminService{
	@Resource
	private AdminDAO adminDAO;
	
	@Resource
	private JavaMailSender mailSender;
	@Resource
	private VelocityConfig velocityConfig;
	
	
	/**
	  * @Method Name : sendMail
	  * @Method 설명 : 스케줄러에 의해  주기적으로 실행되는 메일 발송 메소드. 실행 시기 : 회원가입시, ...? 
	  * @작성일 : 2016. 1. 18.
	  * @작성자 : yongho
	  * @param recipient
	  * @param subject
	  * @param text
	  * @param textParams
	  * @param formUrl
	  * @throws FileNotFoundException
	  * @throws URISyntaxException
	  */
	@Override
	public void sendMail(String memberId, String mailForm) throws FileNotFoundException, URISyntaxException {
		
		BlliMailVO mvo = adminDAO.findMailSubjectAndContentByMailForm(mailForm);
		
		String recipient = adminDAO.findMemberMailAddressById(memberId);
		String subject = mvo.getMailSubject();
		String content = mvo.getMailContent();
		String formUrl = mvo.getMailForm();
		
		Map<String, Object> textParams = new HashMap<String, Object>();
		
		textParams.put("content", content);
		textParams.put("contentHeight", 150);

		try {
			MimeMessage message = mailSender.createMimeMessage();
			
			String mailText = null;
			if(textParams != null) {
				mailText = VelocityEngineUtils.mergeTemplateIntoString(velocityConfig.getVelocityEngine(), formUrl, "utf-8", textParams);
			}
			
			message.setFrom(new InternetAddress("yongho.kim@blli.co.kr"));
			message.addRecipient(RecipientType.TO, new InternetAddress(recipient)); //import javax.mail.Message.RecipientType;
			message.setSubject(subject);
			message.setText(mailText, "utf-8", "html");
			
			mailSender.send(message);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
