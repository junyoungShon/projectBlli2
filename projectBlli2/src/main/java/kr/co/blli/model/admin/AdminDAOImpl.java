package kr.co.blli.model.admin;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAOImpl implements AdminDAO{
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public BlliMemberVO findMemberInfoById(String memberId) {
		return sqlSessionTemplate.selectOne("admin.findMemberInfoById", memberId);
	}
	@Override
	public BlliMailVO findMailSubjectAndContentByMailForm(String mailForm) {
		return sqlSessionTemplate.selectOne("admin.findMailSubjectAndContentByMailForm", mailForm);
	}
	@Override
	public List<BlliPostingVO> unconfirmedPosting(String pageNo) {
		return sqlSessionTemplate.selectList("admin.unconfirmedPosting", pageNo);
	}
	@Override
	public int totalUnconfirmedPosting() {
		return sqlSessionTemplate.selectOne("admin.totalUnconfirmedPosting");
	}
	@Override
	public List<BlliPostingVO> postingListWithSmallProducts(String pageNo) {
		return sqlSessionTemplate.selectList("admin.postingListWithSmallProducts", pageNo);
	}
	@Override
	public int totalPostingWithProducts() {
		return sqlSessionTemplate.selectOne("admin.totalPostingWithProducts");
	}
	@Override
	public List<BlliSmallProductVO> unconfirmedSmallProduct(String pageNo) {
		return sqlSessionTemplate.selectList("admin.unconfirmedSmallProduct", pageNo);
	}
	@Override
	public int totalUnconfirmedSmallProduct() {
		return sqlSessionTemplate.selectOne("admin.totalUnconfirmedSmallProduct");
	}
	@Override
	public void deletePosting(BlliPostingVO vo) {
		sqlSessionTemplate.update("admin.deletePosting", vo);
	}
	@Override
	public void addProduct(HashMap<String, String> map) {
		sqlSessionTemplate.update("admin.addProduct", map);
	}
	@Override
	public void selectProduct(HashMap<String, String> map) {
		sqlSessionTemplate.update("admin.selectProduct", map);
	}
	@Override
	public void deleteProduct(String postingUrl) {
		sqlSessionTemplate.update("admin.deleteProduct", postingUrl);
	}
	@Override
	public void registerPosting(BlliPostingVO vo) {
		sqlSessionTemplate.update("admin.registerPosting", vo);
	}
	@Override
	public void deleteSmallProduct(String smallProductId) {
		sqlSessionTemplate.update("admin.deleteSmallProduct", smallProductId);
	}
	@Override
	public void registerSmallProduct(BlliSmallProductVO vo) {
		sqlSessionTemplate.update("admin.registerSmallProduct", vo);
	}
	@Override
	public void registerAndUpdateSmallProduct(BlliSmallProductVO vo) {
		sqlSessionTemplate.update("admin.registerAndUpdateSmallProduct", vo);
	}
	@Override
	public void updateSmallProductName(BlliSmallProductVO vo) {
		sqlSessionTemplate.update("admin.updateSmallProductName", vo);
	}
	@Override
	public void updateMidCategoryWhenToUse(BlliSmallProductVO vo) {
		sqlSessionTemplate.update("admin.updateMidCategoryWhenToUseMin", vo);
		sqlSessionTemplate.update("admin.updateMidCategoryWhenToUseMax", vo);
	}
	@Override
	public String getMidCategory(String smallProductId) {
		return sqlSessionTemplate.selectOne("admin.getMidCategory", smallProductId);
	}
}
