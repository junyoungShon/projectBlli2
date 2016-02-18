package kr.co.blli.model.admin;

import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;
import kr.co.blli.model.vo.BlliWordCloudVO;

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
	public void selectProduct(BlliPostingVO vo) {
		sqlSessionTemplate.update("admin.selectProduct", vo);
		sqlSessionTemplate.update("admin.deleteProduct", vo);
	}
	@Override
	public void deleteProduct(String postingUrl) {
		sqlSessionTemplate.update("admin.deleteProduct", postingUrl);
	}
	@Override
	public int registerPosting(BlliPostingVO vo) {
		return sqlSessionTemplate.update("admin.registerPosting", vo);
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
	@Override
	public void updatePostingCount(BlliPostingVO vo) {
		sqlSessionTemplate.update("admin.updatePostingCount", vo);
	}
	@Override
	public List<BlliPostingVO> makingWordCloud(String smallProductId) {
		return sqlSessionTemplate.selectList("admin.makingWordCloud", smallProductId);
	}
	@Override
	public String selectPostingContentByPostingUrl(String postingUrl) {
		return sqlSessionTemplate.selectOne("admin.selectPostingContentByPostingUrl", postingUrl);
	}
	@Override
	public int updateWordCloud(BlliWordCloudVO blliWordCloudVO) {
		return sqlSessionTemplate.update("admin.updateWordCloud", blliWordCloudVO);
	}
	@Override
	public void insertWordCloud(BlliWordCloudVO blliWordCloudVO) {
		sqlSessionTemplate.insert("admin.insertWordCloud", blliWordCloudVO);
	}
	@Override
	public void snsShareCountUp(String smallProductId) {
		System.out.println("타겟 아이디"+smallProductId);
		sqlSessionTemplate.update("admin.snsShareCountUp", smallProductId);
	}
	@Override
	public List<BlliMidCategoryVO> selectAllMidCategory() {
		return sqlSessionTemplate.selectList("admin.selectAllMidCategory");
	}
	@Override
	public List<BlliSmallProductVO> selectAllSmallProduct() {
		return sqlSessionTemplate.selectList("admin.selectAllSmallProduct");
	}
	@Override
	public List<BlliPostingVO> checkPosting() {
		return sqlSessionTemplate.selectList("admin.checkPosting");
	}
	@Override
	public void notAdvertisingPosting(BlliPostingVO postingVO) {
		sqlSessionTemplate.update("admin.notAdvertisingPosting", postingVO);
	}
	@Override
	public List<BlliMemberVO> checkMember() {
		return sqlSessionTemplate.selectList("admin.checkMember");
	}
	@Override
	public void updateSmallProductStatus(String smallProductId) {
		sqlSessionTemplate.update("admin.updateSmallProductStatus", smallProductId);
	}
}
