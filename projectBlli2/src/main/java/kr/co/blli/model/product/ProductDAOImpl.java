package kr.co.blli.model.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliBigCategoryVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAOImpl implements ProductDAO{
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<String> getSmallProduct() {
		return sqlSessionTemplate.selectList("product.getSmallProduct");
	}

	@Override
	public void insertBigCategory(BlliBigCategoryVO blliBigCategoryVO) {
		sqlSessionTemplate.insert("product.insertBigCategory", blliBigCategoryVO);
	}

	@Override
	public List<BlliBigCategoryVO> getBigCategory() {
		return sqlSessionTemplate.selectList("product.getBigCategory");
	}

	@Override
	public void insertMidCategory(BlliMidCategoryVO blliMidCategoryVO) {
		sqlSessionTemplate.insert("product.insertMidCategory", blliMidCategoryVO);
	}

	@Override
	public int updateBigCategory(BlliBigCategoryVO blliBigCategoryVO) {
		return sqlSessionTemplate.update("product.updateBigCategory", blliBigCategoryVO);
	}

	@Override
	public int updateMidCategory(BlliMidCategoryVO blliMidCategoryVO) {
		return sqlSessionTemplate.update("product.updateMidCategory", blliMidCategoryVO);
	}

	@Override
	public List<BlliMidCategoryVO> getMidCategory() {
		return sqlSessionTemplate.selectList("product.getMidCategory");
	}
}
