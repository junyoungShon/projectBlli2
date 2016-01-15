package kr.co.blli.model.member;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;
@Repository
public class MemberDAOImpl implements MemberDAO{
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	
	/**
	 * 
	  * @Method Name : findMemberById
	  * @Method 설명 : 아이디로 회원의 존재 유무를 판단합니다.
	  * @작성일 : 2016. 1. 13.
	  * @작성자 : junyoung
	  * @param memberId
	  * @return
	 */
	@Override
	public BlliMemberVO findMemberByIdForLogin(String memberId) {
		return sqlSessionTemplate.selectOne("member.findMemberByIdForLogin", memberId);
	}
	@Override
	public void insertMemberInfo(BlliMemberVO blliMemberVO) {
		System.out.println(blliMemberVO);
		sqlSessionTemplate.selectOne("member.insertMemberInfo", blliMemberVO);
	}
	@Override
	public void insertChildInfo(BlliBabyVO blliChildVO) {
		System.out.println(blliChildVO);
		sqlSessionTemplate.selectOne("member.insertChildInfo", blliChildVO);		
	}

}
