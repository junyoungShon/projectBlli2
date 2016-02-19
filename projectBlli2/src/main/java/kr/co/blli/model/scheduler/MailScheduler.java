package kr.co.blli.model.scheduler;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import kr.co.blli.model.member.MemberDAO;
import kr.co.blli.model.member.MemberService;
import kr.co.blli.model.product.ProductService;
import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberVO;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
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
	private MemberService memberService;
	@Resource
	private ProductService productService;
	
	@Resource
	private JavaMailSender mailSender;
	@Resource
	private VelocityConfig velocityConfig;
	
	/**
	  * @Method Name : sendRecommendingMail
	  * @Method 설명 : 스케줄러에 의해  주기적으로 실행되는 제품추천메일 발송 메소드. 
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : yongho
	  * @param memberId
	  * @param mailForm
	 * @throws ParseException 
	  */
	@Scheduled(cron = "00 00 00 * * *") // 매일 00시 월령이 바뀐 자녀를 가진 회원에게 월령별 추천 제품 메일 발송
	//@Scheduled(cron = "00/10 * * * * *") //테스트용
	public void sendRecommendingMail()
			throws FileNotFoundException, URISyntaxException, UnsupportedEncodingException, MessagingException, ParseException {
		Random random = new Random();
		int r = random.nextInt(3000);
		try {
			System.setProperty("random", Integer.toString(r));
			Thread.sleep(r);
			if(Integer.parseInt(System.getProperty("random")) != r){
				long start = System.currentTimeMillis(); // 시작시간 
				Logger logger = Logger.getLogger(getClass());
				String methodName = new Throwable().getStackTrace()[0].getMethodName();
				logger.info("start : "+methodName);
				logger.info("요청자 : scheduler");
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
				String datetime = sdf.format(cal.getTime());
				logger.info("발생 일자 : "+datetime);
				
				//월령이 바뀐 아이를 가진 회원 목록을 불러온다.
				List<BlliMemberVO> memberList = memberService.getMemberHavingBabyAgeChangedList();
					
				if(memberList.size()==0) {
					System.out.println("월령이 바뀐 아기가 한명도 없습니다.");
				} else {
					
					//해당 회원의 아이 중 월령이 바뀐 아이의 정보를 회원VO가 가진 회원아기VOList 변수에 set 해준다.
					for(int i=0; i<memberList.size(); i++) {
						memberList.get(i).setBlliBabyVOList(memberService.getBabyAgeChangedListOfMember(memberList.get(i).getMemberId()));
						memberList.get(i).setBlliRecommendingMidCategoryVOList(productService.selectRecommendingMidCategory(memberService.getBabyAgeChangedListOfMember(memberList.get(i).getMemberId()).get(0)));
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
						textParams.put("recommendingProductList", memberList.get(i).getBlliRecommendingMidCategoryVOList());
						textParams.put("memberEmail", recipient);
						//System.out.println(memberList.get(i).getBlliRecommendingMidCategoryVOList());
						message.setRecipient(RecipientType.TO, new InternetAddress(recipient)); //import javax.mail.Message.RecipientType;
						mailText = VelocityEngineUtils.mergeTemplateIntoString(velocityConfig.getVelocityEngine(), contentFile, "utf-8", textParams);
						message.setText(mailText, "utf-8", "html");
						mailSender.send(message);
						System.out.println(memberList.get(i).getMemberName()+"님의 메일주소 "+recipient+"로 메일 발송");
					}
				}
				
				long end = System.currentTimeMillis();  //종료시간
				//종료-시작=실행시간		
				logger.info("실행 시간  : "+(int)Math.ceil((end-start)/1000.0)+"초");
				logger.info("end : "+methodName);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
