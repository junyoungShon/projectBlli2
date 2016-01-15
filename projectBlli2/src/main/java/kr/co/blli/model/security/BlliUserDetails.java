package kr.co.blli.model.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class BlliUserDetails implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private String authority;
	
	
	
	public BlliUserDetails() {
		super();
	}
	public BlliUserDetails(String userName, String password,
			String authority) {
		super();
		this.userName = userName;
		this.password = password;
		this.authority = authority;
	}
	/**
	 * 인증받은 회원이 가진 권한은 리스트 형식으로 반환
	  * @Method Name : getAuthorities
	  * @Method 설명 :
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @return
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority ga = new SimpleGrantedAuthority(authority);
		authorities.add(ga);
		return authorities;
	}
	
	public String getAuthority() {
		return authority;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
