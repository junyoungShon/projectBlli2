package kr.co.blli.model.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
	@Resource
	private ProductDAO productDAO;
	
	@Override
	public void insertBigCategory() throws IOException {
		Document doc = Jsoup.connect("http://shopping.naver.com/category/category.nhn?cat_id=50000005").get();
		Elements bigCategories = doc.select(".category_cell h3 a");
		for(Element e : bigCategories){
			HashMap<String, String> map = new HashMap<String, String>();
			String href = e.attr("href");
			href = href.substring(href.lastIndexOf("=")+1);
			String bigCategory = e.select("strong").text();
			map.put("categoryId", href);
			map.put("bigCategory", bigCategory);
			productDAO.insertBigCategory(map);
		}
	}

	@Override
	public void insertMidCategory() throws IOException {
		ArrayList<String> bigCategoryId = (ArrayList<String>)productDAO.getBigCategoryId();
		for(int i=0;i<bigCategoryId.size();i++){
			Document doc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?cat_id="+bigCategoryId.get(i)).get();
			//System.out.println(doc);
			Elements midCategories = doc.select(".finder_list a");
			for(Element e : midCategories){
				HashMap<String, String> map = new HashMap<String, String>();
				String categoryId = e.attr("_value");
				String midCategory = e.text();
				System.out.println(categoryId+" "+midCategory);
				map.put("categoryId", categoryId);
				map.put("midCategory", midCategory);
			}
		}
	}
}
