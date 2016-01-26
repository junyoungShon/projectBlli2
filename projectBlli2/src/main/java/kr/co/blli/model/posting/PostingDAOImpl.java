package kr.co.blli.model.posting;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliPostingVO;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostingDAOImpl implements PostingDAO{
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	@Override
	public int updatePosting(BlliPostingVO postingVO) {
		return sqlSessionTemplate.update("posting.updatePosting", postingVO);
	}
	@Override
	public void insertPosting(BlliPostingVO postingVO) {
		sqlSessionTemplate.insert("posting.insertPosting", postingVO);
	}
	@Override
	public List<BlliPostingVO> searchJsoupTest(String searchWord) {
		return sqlSessionTemplate.selectList("posting.searchJsoupTest", searchWord);
	}
	@Override
	public List<BlliPostingVO> postingListWithSmallProducts(String pageNo) {
		return sqlSessionTemplate.selectList("posting.postingListWithSmallProducts", pageNo);
	}
	@Override
	public List<String> searchProducts(String postingUrl) {
		return sqlSessionTemplate.selectList("posting.searchProducts", postingUrl);
	}
	@Override
	public void deleteProduct(String postingUrl) {
		sqlSessionTemplate.update("posting.deleteProduct", postingUrl);
	}
	@Override
	public void selectProduct(HashMap<String, String> map) {
		sqlSessionTemplate.update("posting.selectProduct", map);
	}
	@Override
	public int countOfPostingUrl(String postingUrl) {
		return sqlSessionTemplate.selectOne("posting.countOfPostingUrl", postingUrl);
	}
	@Override
	public String getPostingStatus(String postingUrl) {
		return sqlSessionTemplate.selectOne("posting.getPostingStatus",postingUrl);
	}
	@Override
	public List<String> getAllPostingStatus(String postingUrl) {
		return sqlSessionTemplate.selectList("posting.getAllPostingStatus",postingUrl);
	}
	@Override
	public int totalPostingWithProducts() {
		return sqlSessionTemplate.selectOne("posting.totalPostingWithProducts");
	}
	@Override
	public void deletePosting(String postingUrl) {
		sqlSessionTemplate.update("posting.deletePosting", postingUrl);
	}
}
