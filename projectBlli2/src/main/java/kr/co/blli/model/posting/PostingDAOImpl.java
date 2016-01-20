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
	public List<BlliPostingVO> postingListWithSmallProducts() {
		return sqlSessionTemplate.selectList("posting.postingListWithSmallProducts");
	}
	@Override
	public List<String> searchProducts(String postingUrl) {
		return sqlSessionTemplate.selectList("posting.searchProducts", postingUrl);
	}
	@Override
	public void deleteProduct(HashMap<String, String> map) {
		sqlSessionTemplate.delete("posting.deleteProduct", map);
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
}
