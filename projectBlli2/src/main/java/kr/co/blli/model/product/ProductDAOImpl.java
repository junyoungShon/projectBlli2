package kr.co.blli.model.product;

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
}
