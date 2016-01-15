package kr.co.blli.model.posting;

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
		return sqlSessionTemplate.insert("posting.updatePosting", postingVO);
	}
	@Override
	public void insertPosting(BlliPostingVO postingVO) {
		sqlSessionTemplate.insert("posting.insertPosting", postingVO);
	}
	@Override
	public List<BlliPostingVO> searchJsoupTest(String searchWord) {
		return sqlSessionTemplate.selectList("posting.searchJsoupTest", searchWord);
	}
}
