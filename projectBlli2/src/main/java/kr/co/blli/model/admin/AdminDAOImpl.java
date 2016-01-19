package kr.co.blli.model.admin;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliMailVO;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAOImpl implements AdminDAO{
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public String findMemberMailAddressById(String memberId) {
		return sqlSessionTemplate.selectOne("admin.findMemberMailAddressById", memberId);
	}
	@Override
	public BlliMailVO findMailSubjectAndContentByMailForm(String mailForm) {
		return sqlSessionTemplate.selectOne("admin.findMailSubjectAndContentByMailForm", mailForm);
	}

}
