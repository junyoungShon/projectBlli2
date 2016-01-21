package kr.co.blli.model.product;

import java.io.IOException;
import java.util.List;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliNotRecommMidCategoryVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

public interface ProductService {

	public void insertBigCategory() throws IOException;

	public void insertMidCategory() throws IOException;

	public void insertSmallProduct() throws IOException;

	public List<BlliMidCategoryVO> selectRecommendingMidCategory(BlliBabyVO blliBabyVO);

	public void deleteRecommendMidCategory(BlliNotRecommMidCategoryVO blliNotRecommMidCategoryVO);

	public List<BlliSmallProductVO> selectSameAgeMomBestPickedSmallProductList(
			List<BlliMidCategoryVO> blliMidCategoryVOList, BlliBabyVO blliBabyVO);

}
