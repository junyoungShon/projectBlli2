package kr.co.blli.model.posting;

import java.util.HashMap;
import java.util.List;

import kr.co.blli.model.vo.BlliPostingVO;

import org.springframework.stereotype.Repository;

@Repository
public interface PostingDAO {

	int updatePosting(BlliPostingVO postingVO);

	void insertPosting(BlliPostingVO postingVO);

	List<BlliPostingVO> searchJsoupTest(HashMap<String, String> map);

	List<BlliPostingVO> postingListWithSmallProducts(String pageNo);

	List<String> searchProducts(String postingUrl);

	void deleteProduct(String postingUrl);

	int countOfPostingUrl(String postingUrl);

	String getPostingStatus(String postingUrl);

	List<String> getAllPostingStatus(String postingUrl);

	void selectProduct(HashMap<String, String> map);

	int totalPostingWithProducts();

	void deletePosting(String postingUrl);
	
	void updatePostingViewCountAndResidenceTime(BlliPostingVO blliPostingVO);

	List<BlliPostingVO> unconfirmedPosting(String pageNo);

	int totalUnconfirmedPosting();

	void registerPosting(HashMap<String, String> map);

	void addProduct(HashMap<String, String> map);

	int totalPageOfPosting(String searchWord);

}
