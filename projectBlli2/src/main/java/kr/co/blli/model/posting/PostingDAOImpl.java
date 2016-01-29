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
	public List<BlliPostingVO> searchSmallProduct(HashMap<String, String> map) {
		return sqlSessionTemplate.selectList("posting.searchSmallProduct", map);
	}
	@Override
	public List<String> searchProducts(String postingUrl) {
		return sqlSessionTemplate.selectList("posting.searchProducts", postingUrl);
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
	/**
	  * @Method Name : updatePostingViewCountAndResidenceTime
	  * @Method 설명 : 체류시간과 포스팅 조회수를 업데이트 해줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingVO
	 */
	@Override
	public void updatePostingViewCountAndResidenceTime(BlliPostingVO blliPostingVO) {
		sqlSessionTemplate.update("posting.updatePostingViewCountAndResidenceTime",blliPostingVO);
	}
	@Override
	public int totalPageOfPosting(String searchWord) {
		return sqlSessionTemplate.selectOne("posting.totalPageOfPosting", searchWord);
	}
}
