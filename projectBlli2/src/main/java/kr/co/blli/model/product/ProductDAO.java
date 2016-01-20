package kr.co.blli.model.product;

import java.util.ArrayList;
import java.util.List;

import kr.co.blli.model.vo.BlliBigCategoryVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;

public interface ProductDAO {

	List<String> getSmallProduct();

	void insertBigCategory(BlliBigCategoryVO blliBigCategoryVO);

	List<BlliBigCategoryVO> getBigCategory();

	void insertMidCategory(BlliMidCategoryVO blliMidCategoryVO);

	int updateBigCategory(BlliBigCategoryVO blliBigCategoryVO);

	int updateMidCategory(BlliMidCategoryVO blliMidCategoryVO);

	List<BlliMidCategoryVO> getMidCategory();

}
