package kr.co.blli.model.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.member.MemberDAO;
import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BlliAuthenticationProvider implements AuthenticationProvider{
	@Resource
	private MemberDAO memberDAO;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	/**
	 * 
	  * @Method Name : authenticate
	  * @Method 설명 : spring seurity가 전달해 주는 입력폼의 값과 DB의 회원 정보 값을 서로 비교하여 자격을 발급해준다.
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @param authentication
	  * @return
	  * @throws AuthenticationException
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
		BlliMemberVO blliMemberVO = memberDAO.findMemberByIdForLogin(authToken.getName());
		if(blliMemberVO==null)
			throw new UsernameNotFoundException(authToken.getName());	
		
		BlliUserDetails blliUserDetails = 
				new BlliUserDetails(blliMemberVO.getMemberId(),blliMemberVO.getMemberPassword(),blliMemberVO.getAuthority());
		
		
		if(!matchPassword(blliUserDetails.getPassword(),authToken.getCredentials())){
			throw new BadCredentialsException("회원의 아이디 또는 비밀번호가 옳지 않습니다.");
		}
		
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) blliUserDetails.getAuthorities();
        UsernamePasswordAuthenticationToken result = 
        		new UsernamePasswordAuthenticationToken(blliUserDetails.getUsername(),blliUserDetails.getPassword(),authorities);
		return result;
	}

	/**
	 * 
	  * @Method Name : matchPassword
	  * @Method 설명 : 암호화된 db의 비밀번호와 회원이 입력한 암호화되지 않은 비밀번호를 비교하여 참 거짓을 반환해준다.
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @param password
	  * @param credentials
	  * @return
	 */
	private boolean matchPassword(String password, Object credentials) {
		return passwordEncoder.matches((CharSequence) credentials, password);
	}


}
