package kr.co.blli.model.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliBigCategoryVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliNotRecommMidCategoryVO;
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

}
