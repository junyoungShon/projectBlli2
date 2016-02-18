package kr.co.blli.model.member;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import kr.co.blli.model.security.BlliUserDetails;
import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberScrapeVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.utility.BlliFileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Resource
	private MemberDAO memberDAO;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Resource
	private BlliFileUtils blliFileUtils;

	/**
	  * @Method Name : joinMemberByEmail
	  * @Method 설명 : 이메일을 통해 가입하는 회원들의 권한 부여 및 패스워드 암호화 등을 진행
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	  * @param blliChildVO
	 */
	@Override
	public void joinMemberByEmail(BlliMemberVO blliMemberVO) {
		blliMemberVO.setMemberEmail(blliMemberVO.getMemberId());
		blliMemberVO.setAuthority("ROLE_RESTRICTED");
		blliMemberVO.setMemberPassword(passwordEncoder.encode(blliMemberVO.getMemberPassword()));
		memberDAO.insertMemberInfo(blliMemberVO);
		//회원 가입 직후 권한을 부여하여 로그인을 시킨 뒤 페이지 이동 시켜줌
		BlliUserDetails blliUserDetails = new BlliUserDetails
				(blliMemberVO.getMemberId(), blliMemberVO.getMemberPassword(), blliMemberVO.getAuthority());
		Authentication authentication = new UsernamePasswordAuthenticationToken(blliUserDetails, null,blliUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	  * @Method Name : joinMemberBySNS
	  * @Method 설명 :SNS를 통해 가입하는 회원들의 권한 부여 및 패스워드 암호화 등을 진행
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	 */
	@Override
	public void joinMemberBySNS(BlliMemberVO blliMemberVO) {
		blliMemberVO.setMemberPassword(passwordEncoder.encode("snsMember"));
		memberDAO.insertMemberInfo(blliMemberVO);
		//회원 가입 직후 권한을 부여하여 로그인을 시킨 뒤 페이지 이동 시켜줌
		BlliUserDetails blliUserDetails = new BlliUserDetails
				(blliMemberVO.getMemberId(), 
						blliMemberVO.getMemberPassword(), blliMemberVO.getAuthority());
		Authentication authentication = new UsernamePasswordAuthenticationToken
				(blliUserDetails, null,blliUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	};
	/**
	 * 
	  * @Method Name : findMemberById
	  * @Method 설명 : 멤버아이디를 기준으로 회원이 있는지 없는지 판단해주는 메서드
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	 */
	@Override
	public BlliMemberVO findMemberById(BlliMemberVO blliMemberVO) {
		return memberDAO.findMemberByIdForLogin(blliMemberVO.getMemberId());
	}
	/**
	  * @Method Name : insertBabyInfo
	  * @Method 설명 : 카카오톡 가입자의 아이정보와 이메일을 입력해주는 서비스 메서드
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param list
	  * @param memberEmail
	 * @throws Exception 
	 */
	@Override
	public void insertBabyInfo(BlliMemberVO blliMemberVO,HttpServletRequest request) throws Exception {
	    ArrayList<BlliBabyVO> list = new ArrayList<BlliBabyVO>();
		int targetAmount = Integer.parseInt(request.getParameter("targetAmount"));
		System.out.println(blliMemberVO.getBlliBabyVOList());
		SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDay = null;
		for(int i=0;i<targetAmount;i++){
			System.out.println(targetAmount);
			BlliBabyVO blliBabyVO = new BlliBabyVO();
			blliBabyVO.setMemberId(request.getParameter("memberId"));
			blliBabyVO.setBabyName(request.getParameter("BlliBabyVO["+i+"].babyName"));
			birthDay = formatter.parse(request.getParameter("BlliBabyVO["+i+"].babyBirthday"));
			blliBabyVO.setBabyBirthday(formatter.format(birthDay));
			blliBabyVO.setBabySex(request.getParameter("BlliBabyVO["+i+"].babySex"));
			if(i==0){
				blliBabyVO.setRecommending(1);
			}else{
				blliBabyVO.setRecommending(0);
			}
			System.out.println(blliBabyVO);
			list.add(blliBabyVO);
		}
		if(blliMemberVO.getMemberEmail()!=null){ 
			memberDAO.updateMemberEmail(blliMemberVO);
		}
		//파일을 업로드 하고, 저장한 파일명을 반환해준다.
		blliFileUtils.parseInsertFileInfo(request,list);
		//아이정보를 입력하면 유저의 권한이 ROLE_USER로 변경
		 blliMemberVO.setAuthority("ROLE_USER");
		 memberDAO.updateMemberAuthority(blliMemberVO);
		 //아이정보를 입력하며 첫째 아이로 아이정보를 수정해줌
		 for(int i=0;i<list.size();i++){
			 System.out.println(list.get(i));
			 memberDAO.insertBabyInfo(list.get(i));
		 }
		//아이정보 입력 후 권한을 갱신하여 로그인을 시킨 뒤 페이지 이동 시켜줌
		BlliUserDetails blliUserDetails = new BlliUserDetails
				(blliMemberVO.getMemberId(), blliMemberVO.getMemberPassword(), blliMemberVO.getAuthority());
		Authentication authentication = new UsernamePasswordAuthenticationToken(blliUserDetails, null,blliUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	
	/**
	 * 
	  * @Method Name : selectBlliMemberInfoByMemberId
	  * @Method 설명 : 사용자가 메인에 접근할 때 필요로하는 정보들을 담는 메서드
	  * @작성일 : 2016. 1. 18.
	  * @작성자 : junyoung
	  * @param memberId
	  * @return
	 */
	@Override
	public BlliMemberVO selectBlliMemberInfoByMemberId(String memberId) {
		System.out.println(memberId);
		//1. 비밀번호를 제외한 사용자의 모든 정보
		BlliMemberVO blliMemberVO= memberDAO.selectBlliMemberInfoByMemberId(memberId);
		//2. 사용자의 아이정보
		blliMemberVO.setBlliBabyVOList(memberDAO.selectBabyListByMemberId(memberId));
		return blliMemberVO;
	}
	/**
	  * @Method Name : selectBabyListByMemberId
	  * @Method 설명 : 회원의 아이디를 기준으로 아이의 리스트를 조회한다.
	  * @작성일 : 2016. 1. 20.
	  * @작성자 : junyoung
	  * @param memberId
	  * @return
	 * @throws ParseException 
	 */
	@Override
	public List<BlliBabyVO> selectBabyListByMemberId(String memberId) throws ParseException {
		List<BlliBabyVO> blliBabyVOList = memberDAO.selectBabyListByMemberId(memberId);
		//아이의 월령 및 태어난 날 수 세팅
		for(int i=0;i<blliBabyVOList.size();i++){
			System.out.println(blliBabyVOList.get(i).getBabyBirthday());
			int babyDayAge = babyDayAgeCounter(blliBabyVOList.get(i).getBabyBirthday());
			int babyMonthAge = babyMonthAgeCounter(blliBabyVOList.get(i).getBabyBirthday());
			blliBabyVOList.get(i).setBabyDayAge(babyDayAge);
			blliBabyVOList.get(i).setBabyMonthAge(babyMonthAge);
		}
		return blliBabyVOList;
	}
	public int babyDayAgeCounter(String babyBirthday) throws ParseException{
		SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDay = null;
		birthDay = formatter.parse(babyBirthday);
		Date nowDate = new Date();
		long diff = nowDate.getTime() - birthDay.getTime() ;
		long diffDays = diff/(24*60*60*1000);
		if(diffDays<0){
			diffDays = diffDays-1;
		}
		return (int) diffDays;
	}
	public int babyMonthAgeCounter(String babyBirthday) throws ParseException{
		int babyMonthAge = 0;
		System.out.println(babyBirthday);
		String babyBirth[] = babyBirthday.substring(0, 10).split("-");
		
		int babyYear = Integer.parseInt(babyBirth[0]);
		int babyMonth = Integer.parseInt(babyBirth[1]);
		int babyDate = Integer.parseInt(babyBirth[2]);
		Calendar today = Calendar.getInstance();    
		int nowYear = today.get(Calendar.YEAR);
		int nowMonth = (today.get(Calendar.MONTH))+1;
		int nowDate = today.get(Calendar.DATE);
		System.out.println(nowYear +" "+ nowMonth + " " + nowDate);
		//현재 - 생일
		int diffYears = nowYear - babyYear;
		int diffMonths = nowMonth - babyMonth;
		int diffDays = nowDate - babyDate;
		if(diffYears>0){
			babyMonthAge = diffYears * 12; 
		}else if(diffYears==0){
			babyMonthAge = 0;
		}else if(diffYears<0){
			//아직 태어나지 않았으므로 월령은 -1로 설정
			babyMonthAge = -1;
		}
		//월을 통한 계산 (아직 태어나지 않았다고 확인되었으므로 더이상 계산하지 않는다.)
		if(babyMonthAge!=-1){
			babyMonthAge = babyMonthAge+diffMonths;
			if(babyMonthAge<-1){
				babyMonthAge=-1;
			}
		}
		
		//일을 통한 계산 (아직 태어나지 않았다고 확인되었으므로 더이상 계산하지 않는다.)
		if(babyMonthAge!=-1){
			if(diffDays<0){
				babyMonthAge = babyMonthAge - 1;
			}
			if(babyMonthAge<-1){
				babyMonthAge=-1;
			}
		}
		System.out.println(babyMonthAge);
		return babyMonthAge;
	}
	/**
	  * @Method Name : changeRecommendingBaby
	  * @Method 설명 : 추천 대상아이를 바꿔줍니다.
	  * @작성일 : 2016. 1. 20.
	  * @작성자 : junyoung
	  * @param blliBabyVO
	 */
	@Override
	public void changeRecommendingBaby(BlliBabyVO blliBabyVO) {
		//현재 추천 대상인 아이를 찾아내 추천 제외 시킴니다 0으로 변화
		List<BlliBabyVO> blliBabyVOList = memberDAO.selectBabyListByMemberId(blliBabyVO.getMemberId());
		for(int i=0;i<blliBabyVOList.size();i++){
			if(blliBabyVOList.get(i).getRecommending()==1){
				BlliBabyVO beforeblliBabyVO = blliBabyVOList.get(i);
				beforeblliBabyVO.setRecommending(0);
				memberDAO.changeRecommendingBaby(beforeblliBabyVO);
			}
		}
		//새로운 추천 대상을 등록합니다.
		blliBabyVO.setRecommending(1);
		memberDAO.changeRecommendingBaby(blliBabyVO);
	}
	
	@Override
	public void updateMemberInfoByEmail(BlliMemberVO blliMemberVO) {
		//비번 암호화
		blliMemberVO.setMemberPassword(passwordEncoder.encode(blliMemberVO.getMemberPassword()));
		memberDAO.updateMemberInfoByEmail(blliMemberVO);
	}

	@Override
	public void deleteBabyInfo(BlliMemberVO blliMemberVO) {
		memberDAO.deleteBabyInfo(blliMemberVO);
	}

	
	//용호 작성 영역
	@Override
	public List<BlliMemberVO> getMemberHavingBabyAgeChangedList() {
		return memberDAO.getMemberHavingBabyAgeChangedList();
	}

	@Override
	public List<BlliBabyVO> getBabyAgeChangedListOfMember(String memberId) throws ParseException {
		List<BlliBabyVO> blliBabyVOList = memberDAO.getBabyAgeChangedListOfMember(memberId);
		//아이의 월령 및 태어난 날 수 세팅
		for(int i=0;i<blliBabyVOList.size();i++){
			System.out.println(blliBabyVOList.get(i).getBabyBirthday());
			int babyDayAge = babyDayAgeCounter(blliBabyVOList.get(i).getBabyBirthday());
			int babyMonthAge = babyMonthAgeCounter(blliBabyVOList.get(i).getBabyBirthday());
			blliBabyVOList.get(i).setBabyDayAge(babyDayAge);
			blliBabyVOList.get(i).setBabyMonthAge(babyMonthAge);
		}
		return blliBabyVOList;
	}

	
	@Resource
	private JavaMailSender mailSender;
	@Resource
	private VelocityConfig velocityConfig;
	
	@Override
	public void sendLinkToGetTemporaryPassword(String memberEmail) throws UnsupportedEncodingException, MessagingException {

		BlliMailVO mlvo = memberDAO.findMailSubjectAndContentByMailForm("receiveLinkToGetTemporaryPasswordMail");
		String memberName = memberDAO.findMemberNameByEmail(memberEmail);
		
		String subject = mlvo.getMailSubject();
		String contentFile = mlvo.getMailContentFile();
		String recipient = memberEmail;

		Map<String, Object> textParams = new HashMap<String, Object>();
		
		textParams.put("memberName", memberName);
		textParams.put("linkToGetTemporaryPassword", "http://bllidev.dev/projectBlli2/getTemporaryPassword.do?memberEmail="+memberEmail);
		
		String mailText = VelocityEngineUtils.mergeTemplateIntoString(velocityConfig.getVelocityEngine(), contentFile, "utf-8", textParams);
		
		MimeMessage message = mailSender.createMimeMessage();

		message.setFrom(new InternetAddress("admin@blli.co.kr","블리", "utf-8"));
		message.setSubject(subject);
		message.addRecipient(RecipientType.TO, new InternetAddress(recipient)); //import javax.mail.Message.RecipientType;
		message.setText(mailText, "utf-8", "html");
		
		mailSender.send(message);
		System.out.println(memberName+"님의 메일주소 "+recipient+"로 임시비밀번호 받는 링크 발송");
	}    
	
	@Override
	public String updateMemberPasswordToTemporaryPassword(String memberEmail) {
		BlliMemberVO blliMemberVO = new BlliMemberVO();
		String temporaryPassword = createTemporaryPassword();
		blliMemberVO.setMemberEmail(memberEmail);
		blliMemberVO.setMemberPassword(passwordEncoder.encode(temporaryPassword));
		memberDAO.updateMemberPasswordToTemporaryPassword(blliMemberVO);
		return temporaryPassword;
	}
	
	@Override
	public void sendTemporaryPasswordMail(String memberEmail, String TemporaryPassword) throws UnsupportedEncodingException, MessagingException {
		
		BlliMailVO mlvo = memberDAO.findMailSubjectAndContentByMailForm("getTemporaryPassword");
		String memberName = memberDAO.findMemberNameByEmail(memberEmail);
		
		String subject = mlvo.getMailSubject();
		String contentFile = mlvo.getMailContentFile();
		String recipient = memberEmail;

		Map<String, Object> textParams = new HashMap<String, Object>();
		
		textParams.put("memberName", memberName);
		textParams.put("TemporaryPassword",TemporaryPassword);
		
		String mailText = VelocityEngineUtils.mergeTemplateIntoString(velocityConfig.getVelocityEngine(), contentFile, "utf-8", textParams);
		
		MimeMessage message = mailSender.createMimeMessage();

		message.setFrom(new InternetAddress("admin@blli.co.kr","블리", "utf-8"));
		message.setSubject(subject);
		message.addRecipient(RecipientType.TO, new InternetAddress(recipient)); //import javax.mail.Message.RecipientType;
		message.setText(mailText, "utf-8", "html");
		
		mailSender.send(message);
		System.out.println(memberName+"님의 메일주소 "+recipient+"로 임시비밀번호 발송");
	}
	
	private String createTemporaryPassword() {
		int index = 0;
		char[] charSet = new char[] {
				'0','1','2','3','4','5','6','7','8','9',
				'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
		};
		
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<10;i++) {
			index = (int) (charSet.length * Math.random());
			sb.append(charSet[index]);
		}
		return sb.toString();
	}

	@Override
	public int denySendEmail(String memberEmail) {
		return memberDAO.denySendEmail(memberEmail);
	}

	@Override
	public ArrayList<BlliMemberScrapeVO> getScrapeInfoByMemberId(BlliMemberVO memberVO) {
		return (ArrayList<BlliMemberScrapeVO>)memberDAO.getScrapeInfoByMemberId(memberVO);
	}

	
}
