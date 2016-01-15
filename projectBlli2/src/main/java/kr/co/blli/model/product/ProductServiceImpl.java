package kr.co.blli.model.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
	@Resource
	private ProductDAO productDAO;
}
