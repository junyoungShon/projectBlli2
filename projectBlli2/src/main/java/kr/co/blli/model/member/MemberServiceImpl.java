package kr.co.blli.model.member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.security.BlliUserDetails;
import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Resource
	private MemberDAO memberDAO ;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

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
				(blliMemberVO.getMemberId(), blliMemberVO.getMemberPassword(), blliMemberVO.getAuthority());
		Authentication authentication = new UsernamePasswordAuthenticationToken(blliUserDetails, null,blliUserDetails.getAuthorities());
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
	  * @Method Name : insertBabyInfoForKakaoUser
	  * @Method 설명 : 카카오톡 가입자의 아이정보와 이메일을 입력해주는 서비스 메서드
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param list
	  * @param memberEmail
	 */
	@Override
	public void insertBabyInfo(ArrayList<BlliBabyVO> list,BlliMemberVO blliMemberVO) {
		if(blliMemberVO.getMemberEmail()!=null){ 
			memberDAO.updateMemberEmail(blliMemberVO);
		}
		//아이정보를 입력하면 유저의 권한이 ROLE_USER로 변경
		 blliMemberVO.setAuthority("ROLE_USER");
		 memberDAO.updateMemberAuthority(blliMemberVO);
		 //아이정보를 입력하며 첫째 아이로 아이정보를 수정해줌
		 for(int i=0;i<list.size();i++){
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
		}else if(diffYears==0){
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
}
