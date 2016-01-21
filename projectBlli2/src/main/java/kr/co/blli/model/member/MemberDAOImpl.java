package kr.co.blli.model.member;

import java.util.List;

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
	/**
	  * @Method Name : insertMemberInfo
	  * @Method 설명 :회원가입 시 회원 정보를 회원 DB테이블에 삽입합니다.
	  * @작성일 : 2016. 1. 17.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	 */
	@Override
	public void insertMemberInfo(BlliMemberVO blliMemberVO) {
		sqlSessionTemplate.insert("member.insertMemberInfo", blliMemberVO);
	}
	/**
	  * @Method Name : updateMemberEmail
	  * @Method 설명 :회원의 이메일 주소를 업데이트 합니다.
	  * @작성일 : 2016. 1. 17.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	 */
	@Override
	public void updateMemberEmail(BlliMemberVO blliMemberVO) {
		sqlSessionTemplate.update("member.updateMemberEmail",blliMemberVO);
	}
	/**
	  * @Method Name : insertBabyInfo
	  * @Method 설명 : 회원이 아이의 정보를 입력한 경우 이를 DB에 삽입합니다.
	  * @작성일 : 2016. 1. 17.
	  * @작성자 : junyoung
	  * @param blliBabyVO
	 */
	@Override
	public void insertBabyInfo(BlliBabyVO blliBabyVO) {
		sqlSessionTemplate.insert("member.insertBabyInfo",blliBabyVO);
	}
	/**
	  * @Method Name : updateMemberAuthority
	  * @Method 설명 : 회원의 이메일을 활용해 권한을 수정한다.
	  * @작성일 : 2016. 1. 17.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	 */
	@Override
	public void updateMemberAuthority(BlliMemberVO blliMemberVO) {
		sqlSessionTemplate.update("updateMemberAuthority", blliMemberVO);
	}
	/**
	  * @Method Name : selectBlliMemberInfoByMemberId
	  * @Method 설명 : 회원의 아이디로 회원의 비밀번호를 제외한 모든 컬럼의 정보를 조회
	  * @작성일 : 2016. 1. 18.
	  * @작성자 : junyoung
	  * @param memberId
	  * @return
	 */
	@Override
	public BlliMemberVO selectBlliMemberInfoByMemberId(String memberId) {
		return sqlSessionTemplate.selectOne("member.selectBlliMemberInfoByMemberId", memberId);
	}
	/**
	  * @Method Name : selectBabyListByMemberId
	  * @Method 설명 : 회원의 이메일아이디를 키값으로 하여 회원의 아이리스트를 받아온다.
	  * @작성일 : 2016. 1. 18.
	  * @작성자 : junyoung
	  * @param memberId
	  * @return
	 */
	@Override
	public List<BlliBabyVO> selectBabyListByMemberId(String memberId) {
		return sqlSessionTemplate.selectList("member.selectBabyListByMemberId", memberId);
	}
	/**
	  * @Method Name : updateRecommendingBabyName
	  * @Method 설명 : 회원이 추천 받고 있는 아이를 변경해줍니다.
	  * @작성일 : 2016. 1. 20.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	 */
	@Override
	public void updateRecommendingBabyName(BlliMemberVO blliMemberVO) {
		sqlSessionTemplate.update("member.updateRecommendingBabyName", blliMemberVO);
	}
}
