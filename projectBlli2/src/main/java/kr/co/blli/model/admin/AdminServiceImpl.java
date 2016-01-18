package kr.co.blli.model.admin;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	@Override
	public void sendMail(String recipient, String subject, String text, Map<String, Object> textParams, String formUrl)
			throws FileNotFoundException, URISyntaxException {

		try {
			MimeMessage message = mailSender.createMimeMessage();
			
			String mailText = null;
			if(textParams != null) {
				mailText = VelocityEngineUtils.mergeTemplateIntoString(velocityConfig.getVelocityEngine(), formUrl, "utf-8", textParams);
			}
			
			message.setFrom(new InternetAddress("yongho.kim@blli.co.kr"));
			message.addRecipient(RecipientType.TO, new InternetAddress(recipient)); //import 틀렸을 수 있음
			message.setSubject(subject);
			message.setText(mailText, "utf-8", "html");
			
			mailSender.send(message);
			
		} catch(Exception e) { 
			e.printStackTrace();
		}
		
	}
	
}
