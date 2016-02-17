package kr.co.blli.model.posting;

import java.util.HashMap;
import java.util.List;

import kr.co.blli.model.vo.BlliMemberScrapeVO;
import kr.co.blli.model.vo.BlliPostingDisLikeVO;
import kr.co.blli.model.vo.BlliPostingLikeVO;
import kr.co.blli.model.vo.BlliPostingVO;

import org.springframework.stereotype.Repository;

@Repository
public interface PostingDAO {

	int getPostingDislikeCount = 0;

	int updatePosting(BlliPostingVO postingVO);

	void insertPosting(BlliPostingVO postingVO);

	List<BlliPostingVO> searchPosting(HashMap<String, String> map);

	List<String> searchProducts(String postingUrl);

	String getPostingStatus(String postingUrl);

	void updatePostingViewCountAndResidenceTime(BlliPostingVO blliPostingVO);

	int totalPageOfPosting(String searchWord);

	List<BlliPostingVO> searchPostingListInProductDetail(String searchWord);
	
	List<BlliPostingVO> selectAllPosting();

	void updatePostingScore(BlliPostingVO blliPostingVO);

	String getPostingStatus(BlliPostingVO blliPostingVO);

	int selectThisPostingScrape(BlliMemberScrapeVO blliMemberScrapVO);

	int selectThisPostingLike(BlliPostingLikeVO blliPostingLikeVO);

	int selectThisPostingDisLike(BlliPostingDisLikeVO blliPostingDisLikeVO);

	List<BlliPostingVO> selectPostingBySmallProductId(HashMap<String, String> paraMap);

	String selectTotalPostingtNum();

	BlliPostingVO getPostingInfo(BlliMemberScrapeVO blliMemberScrapeVO);

	int getPostingScrapeCount(BlliMemberScrapeVO scrapeVO);

	int getPostingLikeCount(BlliMemberScrapeVO scrapeVO);

	int getPostingDislikeCount(BlliMemberScrapeVO scrapeVO);

}
