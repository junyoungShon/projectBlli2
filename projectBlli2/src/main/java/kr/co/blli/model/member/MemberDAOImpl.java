package kr.co.blli.model.member;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberVO;
@Repository
public class MemberDAOImpl implements MemberDAO{
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	
	
	@Override
	public BlliMemberVO findMemberByIdForLogin(String memberId) {
		return sqlSessionTemplate.selectOne("member.findMemberByIdForLogin", memberId);
	}
	
	@Override
	public void insertMemberInfo(BlliMemberVO blliMemberVO) {
		sqlSessionTemplate.insert("member.insertMemberInfo", blliMemberVO);
	}

	@Override
	public void updateMemberEmail(BlliMemberVO blliMemberVO) {
		sqlSessionTemplate.update("member.updateMemberEmail",blliMemberVO);
	}
	
	@Override
	public void insertBabyInfo(BlliBabyVO blliBabyVO) {
		System.out.println(blliBabyVO);
		sqlSessionTemplate.insert("member.insertBabyInfo",blliBabyVO);
	}
	
	@Override
	public void updateMemberAuthority(BlliMemberVO blliMemberVO) {
		sqlSessionTemplate.update("updateMemberAuthority", blliMemberVO);
	}
	
	@Override
	public BlliMemberVO selectBlliMemberInfoByMemberId(String memberId) {
		return sqlSessionTemplate.selectOne("member.selectBlliMemberInfoByMemberId", memberId);
	}

	@Override
	public List<BlliBabyVO> selectBabyListByMemberId(String memberId) {
		return sqlSessionTemplate.selectList("member.selectBabyListByMemberId", memberId);
	}

	@Override
	public void changeRecommendingBaby(BlliBabyVO blliBabyVO) {
		sqlSessionTemplate.update("member.changeRecommendingBaby", blliBabyVO);
	}
	@Override
	public void updateMemberInfoByEmail(BlliMemberVO blliMemberVO) {
		System.out.println("dao: "+blliMemberVO);
		sqlSessionTemplate.update("member.updateMemberInfoByEmail", blliMemberVO);
	}
	
	//용호 메소드 영역
	@Override
	public BlliMemberVO findMemberInfoById(String memberId) {
		return sqlSessionTemplate.selectOne("member.findMemberInfoById", memberId);
	}
	@Override
	public BlliMailVO findMailSubjectAndContentByMailForm(String mailForm) {
		return sqlSessionTemplate.selectOne("member.findMailSubjectAndContentByMailForm", mailForm);
	}
	@Override
	public List<BlliMemberVO> getMemberHavingBabyAgeChangedList() {
		return sqlSessionTemplate.selectList("member.getMemberHavingBabyAgeChangedList");	
	}
	@Override
	public List<BlliBabyVO> getBabyAgeChangedListOfMember(String memberId) {
		return sqlSessionTemplate.selectList("member.getBabyAgeChangedListOfMember", memberId);	
	}

	@Override
	public void deleteBabyInfo(BlliMemberVO blliMemberVO) {
		sqlSessionTemplate.delete("member.deleteBabyInfo", blliMemberVO);
	}



}
