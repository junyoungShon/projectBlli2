package kr.co.blli.model.posting;

import java.util.HashMap;
import java.util.List;

import kr.co.blli.model.vo.BlliPostingVO;

import org.springframework.stereotype.Repository;

@Repository
public interface PostingDAO {

	int updatePosting(BlliPostingVO postingVO);

	void insertPosting(BlliPostingVO postingVO);

	List<BlliPostingVO> searchJsoupTest(String searchWord);

	List<BlliPostingVO> postingListWithSmallProducts(String pageNo);

	List<String> searchProducts(String postingUrl);

	void deleteProduct(String postingUrl);

	int countOfPostingUrl(String postingUrl);

	String getPostingStatus(String postingUrl);

	List<String> getAllPostingStatus(String postingUrl);

	void selectProduct(HashMap<String, String> map);

	int totalPostingWithProducts();

	void deletePosting(String postingUrl);

}
