package kr.co.blli.model.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ProductDAO {

	List<String> getSmallProduct();

	void insertBigCategory(HashMap<String, String> bigCategory);

	List<String> getBigCategoryId();

}
