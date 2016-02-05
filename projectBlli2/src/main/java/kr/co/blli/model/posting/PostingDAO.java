package kr.co.blli.model.posting;

import java.util.HashMap;
import java.util.List;

import kr.co.blli.model.vo.BlliPostingVO;

import org.springframework.stereotype.Repository;

@Repository
public interface PostingDAO {

	int updatePosting(BlliPostingVO postingVO);

	void insertPosting(BlliPostingVO postingVO);

	List<BlliPostingVO> searchPosting(HashMap<String, String> map);

	List<String> searchProducts(String postingUrl);

	int countOfPostingUrl(String postingUrl);

	String getPostingStatus(String postingUrl);

	List<String> getAllPostingStatus(String postingUrl);

	void updatePostingViewCountAndResidenceTime(BlliPostingVO blliPostingVO);

	int totalPageOfPosting(String searchWord);

	List<BlliPostingVO> searchPostingListInProductDetail(String searchWord);
	
	List<BlliPostingVO> selectAllPosting();

	void updatePostingScore(BlliPostingVO blliPostingVO);
}
