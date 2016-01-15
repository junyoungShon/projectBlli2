package kr.co.blli.model.security;

import javax.annotation.Resource;

import kr.co.blli.model.member.MemberDAO;
import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class BlliUserDetailsService implements UserDetailsService{
	@Resource
	private MemberDAO memberDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		BlliMemberVO blliMemberVO = memberDAO.findMemberByIdForLogin(username);
		System.out.println("자동로그인 체크시 이곳으로 온다.+"+username);
		if(blliMemberVO==null)
			throw new UsernameNotFoundException(username);	
		BlliUserDetails blliUserDetails = 
				new BlliUserDetails(blliMemberVO.getMemberId(),blliMemberVO.getMemberPassword(),blliMemberVO.getAuthority());
		User user = new User(blliMemberVO.getMemberId(),blliMemberVO.getMemberPassword(), blliUserDetails.getAuthorities());
		return user;
	}
}
