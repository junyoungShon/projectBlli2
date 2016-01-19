package kr.co.blli.model.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

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
	public void insertBigCategory(HashMap<String, String> bigCategory) {
		sqlSessionTemplate.insert("product.insertBigCategory", bigCategory);
	}

	@Override
	public List<String> getBigCategoryId() {
		return sqlSessionTemplate.selectList("product.getBigCategoryId");
	}
}
