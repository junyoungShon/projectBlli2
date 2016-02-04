package kr.co.blli.model.scheduler;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import kr.co.blli.model.member.MemberDAO;
import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

@Component
public class MailScheduler {
	@Resource
	private MemberDAO memberDAO;
	
	@Resource
	private JavaMailSender mailSender;
	@Resource
	private VelocityConfig velocityConfig;
	
	/**
	  * @Method Name : sendRecommendingMail
	  * @Method 설명 : 스케줄러에 의해  주기적으로 실행되는 제품추천메일 발송 메소드. 실행 시기 : 매일 00시 월령이 바뀐 자녀를 가진 회원에게 월령병 추천 제품 메일 발송
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : yongho
	  * @param memberId
	  * @param mailForm
	  */
	@Scheduled(cron = "00 00 00 * * *")
	//@Scheduled(cron = "00/03 * * * * *") //테스트용
	public void sendRecommendingMail()
			throws FileNotFoundException, URISyntaxException, UnsupportedEncodingException, MessagingException {
		
		//월령이 바뀐 아이를 가진 회원 목록을 불러온다.
		List<BlliMemberVO> memberList = memberDAO.getMemberHavingBabyAgeChangedList();
		
		if(memberList.size()==0) {
			System.out.println("월령이 바뀐 아기가 한명도 없습니다.");
		}
		
		//해당 회원의 아이 중 월령이 바뀐 아이의 정보를 회원VO가 가진 회원아기VOList 변수에 set 해준다.
		for(int i=0; i<memberList.size(); i++) {
			memberList.get(i).setBlliBabyVOList(memberDAO.getBabyAgeChangedListOfMember(memberList.get(i).getMemberId()));
		}
			
		Map<String, Object> textParams = new HashMap<String, Object>();
		
		BlliMailVO mlvo = memberDAO.findMailSubjectAndContentByMailForm("recommendingMail");
		
		String subject = mlvo.getMailSubject();
		String contentFile = mlvo.getMailContentFile();
		
		MimeMessage message = mailSender.createMimeMessage();
		
		message.setFrom(new InternetAddress("admin@blli.co.kr","블리", "utf-8"));
		message.setSubject(subject);
		
		String mailText = null;
		for(int i=0; i<memberList.size(); i++) {
			String recipient = memberList.get(i).getMemberEmail();
			textParams.put("memberName", memberList.get(i).getMemberName());
			textParams.put("memberBabyName", memberList.get(i).getBlliBabyVOList().get(0).getBabyName());
			message.addRecipient(RecipientType.TO, new InternetAddress(recipient)); //import javax.mail.Message.RecipientType;
			mailText = VelocityEngineUtils.mergeTemplateIntoString(velocityConfig.getVelocityEngine(), contentFile, "utf-8", textParams);
			message.setText(mailText, "utf-8", "html");
			
			mailSender.send(message);
			System.out.println(memberList.get(i).getMemberName()+"님의 메일주소 "+recipient+"로 메일 발송");
		}
		
	}
	
}
