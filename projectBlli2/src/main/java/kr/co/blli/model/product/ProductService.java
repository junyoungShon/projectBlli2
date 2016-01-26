package kr.co.blli.model.product;

import java.io.IOException;
import java.util.List;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberDibsVO;
import kr.co.blli.model.vo.BlliMemberScrapVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliNotRecommMidCategoryVO;
import kr.co.blli.model.vo.BlliPostingDisLikeVO;
import kr.co.blli.model.vo.BlliPostingLikeVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

public interface ProductService {

	public void insertBigCategory() throws IOException;

	public void insertMidCategory() throws IOException;

	public void insertSmallProduct() throws IOException;

	public List<BlliMidCategoryVO> selectRecommendingMidCategory(BlliBabyVO blliBabyVO);

	public void deleteRecommendMidCategory(BlliNotRecommMidCategoryVO blliNotRecommMidCategoryVO);

	public List<BlliSmallProductVO> selectSameAgeMomBestPickedSmallProductList(
			List<BlliMidCategoryVO> blliMidCategoryVOList, BlliBabyVO blliBabyVO);

	public List<BlliPostingVO> selectPostingBySmallProductList(List<BlliSmallProductVO> blliSmallProductVOList,String meberId);

	public int smallProductDib(BlliMemberDibsVO blliMemberDibsVO);

	public int postingScrap(BlliMemberScrapVO blliMemberScrapVO);

	public int postingLike(BlliPostingLikeVO blliPostingLikeVO);

	public int postingDisLike(BlliPostingDisLikeVO blliPostingDisLikeVO);

}
