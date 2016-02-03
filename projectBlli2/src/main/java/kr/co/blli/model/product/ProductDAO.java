package kr.co.blli.model.product;

import java.util.HashMap;
import java.util.List;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliBigCategoryVO;
import kr.co.blli.model.vo.BlliMemberDibsVO;
import kr.co.blli.model.vo.BlliMemberScrapeVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliNotRecommMidCategoryVO;
import kr.co.blli.model.vo.BlliPostingDisLikeVO;
import kr.co.blli.model.vo.BlliPostingLikeVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductBuyLinkVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

public interface ProductDAO {

	List<BlliSmallProductVO> getSmallProduct();

	void insertBigCategory(BlliBigCategoryVO blliBigCategoryVO);

	List<BlliBigCategoryVO> getBigCategory();

	void insertMidCategory(BlliMidCategoryVO blliMidCategoryVO);

	int updateBigCategory(BlliBigCategoryVO blliBigCategoryVO);

	int updateMidCategory(BlliMidCategoryVO blliMidCategoryVO);

	List<BlliMidCategoryVO> getMidCategory();

	List<BlliMidCategoryVO> selectRecommendingMidCategory(BlliBabyVO blliBabyVO);

	void deleteRecommendMidCategory(BlliNotRecommMidCategoryVO blliNotRecommMidCategoryVO);

	List<BlliNotRecommMidCategoryVO> selectNotRecommMidCategoryList(BlliBabyVO blliBabyVO);

	void selectSameAgeMomBestPickedSmallProductList(Integer put);

	List<BlliSmallProductVO> selectSameAgeMomBestPickedSmallProductList(HashMap<String, String> paraMap);

	BlliSmallProductVO selectSameAgeMomBestPickedSmallProduct(HashMap<String, String> paraMap);

	void insertSmallProduct(BlliSmallProductVO blliSmallProductVO);

	int updateSmallProduct(BlliSmallProductVO blliSmallProductVO);

	void insertSmallProductBuyLink(BlliSmallProductBuyLinkVO blliSmallProductBuyLinkVO);

	int updateSmallProductBuyLink(BlliSmallProductBuyLinkVO blliSmallProductBuyLinkVO);

	List<BlliPostingVO> selectPostingBySmallProductList(String midCategoryId);

	int deleteDipsInfo(BlliMemberDibsVO blliMemberDibsVO);

	int insertDipsInfo(BlliMemberDibsVO blliMemberDibsVO);

	void updatePlusSmallProductDibsCount(BlliMemberDibsVO blliMemberDibsVO);

	void updateMinusSmallProductDibsCount(BlliMemberDibsVO blliMemberDibsVO);

	int deletePostingScrapeInfo(BlliMemberScrapeVO blliMemberScrapVO);

	int insertPostingScrape(BlliMemberScrapeVO blliMemberScrapVO);

	void updatePlusPostingScrapeCount(BlliMemberScrapeVO blliMemberScrapVO);

	void updateMinusPostingScrapeCount(BlliMemberScrapeVO blliMemberScrapVO);

	int deletePostingLikeInfo(BlliPostingLikeVO blliPostingLikeVO);

	int insertPostingLikeInfo(BlliPostingLikeVO blliPostingLikeVO);

	void updatePlusPostingLikeCount(BlliPostingLikeVO blliPostingLikeVO);

	void updateMinusPostingLikeCount(BlliPostingLikeVO blliPostingLikeVO);

	int deletePostingDisLikeInfo(BlliPostingDisLikeVO blliPostingDisLikeVO);

	int insertPostingDisLikeInfo(BlliPostingDisLikeVO blliPostingDisLikeVO);

	void updatePlusPostingDisLikeCount(BlliPostingDisLikeVO blliPostingDisLikeVO);

	void updateMinusPostingDisLikeCount(BlliPostingDisLikeVO blliPostingDisLikeVO);

	int selectMemberDibsSmallProduct(BlliMemberDibsVO blliMemberDibsVO);

	int selectThisPostingScrape(BlliMemberScrapeVO blliMemberScrapVO);

	int selectThisPostingLike(BlliPostingLikeVO blliPostingLikeVO);

	int selectThisPostingDisLike(BlliPostingDisLikeVO blliPostingDisLikeVO);

	void updateSearchTime(String smallProductId);

	List<BlliSmallProductVO> searchMidCategory(HashMap<String, String> map);

	BlliSmallProductVO searchSmallProduct(String searchWord);

	List<BlliSmallProductBuyLinkVO> getSmallProductBuyLink(String smallProductId);

	List<BlliSmallProductVO> getOtherSmallProductList(HashMap<String, Object> map);

	List<BlliSmallProductVO> searchSmallProductList(HashMap<String, String> map);

	int totalOtherSmallProduct(String midCategory);

	int totalPageOfSmallProductOfMidCategory(String searchWord);

	int totalPageOfSmallProductRelatedSearchWord(String searchWord);

}
