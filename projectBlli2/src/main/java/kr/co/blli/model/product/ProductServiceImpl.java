package kr.co.blli.model.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliBigCategoryVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliNotRecommMidCategoryVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

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
			String categoryId = e.attr("href");
			categoryId = categoryId.substring(categoryId.lastIndexOf("=")+1);
			String bigCategory = e.select("strong").text();
			BlliBigCategoryVO blliBigCategoryVO = new BlliBigCategoryVO(bigCategory, categoryId);
			int updateResult = productDAO.updateBigCategory(blliBigCategoryVO);
			if(updateResult == 0){
				productDAO.insertBigCategory(blliBigCategoryVO);
			}
		}
	}

	@Override
	public void insertMidCategory() throws IOException {
		int index = 1;
		ArrayList<BlliBigCategoryVO> bigCategory = (ArrayList<BlliBigCategoryVO>)productDAO.getBigCategory();
		for(int i=0;i<bigCategory.size();i++){
			Document doc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?cat_id="+bigCategory.get(i).getCategoryId()).get();
			Elements midCategoriesHtml = doc.select(".finder .finder_row .finder_list a");
			for(Element e : midCategoriesHtml){
				if(e.attr("title").contains("출산/육아>"+bigCategory.get(i).getBigCategory())){
					BlliMidCategoryVO blliMidCategoryVO = new BlliMidCategoryVO();
					String midCategory = e.attr("title");
					midCategory = midCategory.substring(midCategory.lastIndexOf(" ")+1);
					String categoryId = e.attr("_value");
					blliMidCategoryVO.setMidCategory(midCategory);
					blliMidCategoryVO.setCategoryId(categoryId);
					blliMidCategoryVO.setBigCategory(bigCategory.get(i).getBigCategory());
					String imgSrc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?cat_id="+categoryId).get().select("._model_list .img_area img").attr("data-original");
					if(imgSrc == null || imgSrc.equals("")){
						imgSrc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?cat_id="+categoryId).get().select("._product_list .img_area img").attr("data-original");
					}
					blliMidCategoryVO.setMidCategoryMainPhotoLink(imgSrc);
					System.out.println((index++) + " " + blliMidCategoryVO);
					int updateResult = productDAO.updateMidCategory(blliMidCategoryVO);
					if(updateResult == 0){
						productDAO.insertMidCategory(blliMidCategoryVO);
					}
				} //if
			} //for
		} //for
	}

	@Override
	public void insertSmallProduct() throws IOException {
		String key = "6694c8294c8d04cdfe78262583a13052"; //네이버 검색API 이용하기 위해 발급받은 key값
		int i = 1;
		int order = 1;
		Document doc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?pagingIndex=1&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&cat_id=50000686&frm=NVSHMDL").get();
		Elements e = doc.select(".goods_list ._model_list");
		for(Element el : e){
			String smallProduct = el.select(".info .tit").text();
			String smallProductMainPhotoLink = el.select(".img_area ._productLazyImg").attr("data-original");
			int totalPosting = Integer.parseInt(Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+smallProduct+"&display=1&start=1&target=blog&sort=sim").get().select("total").text());
			System.out.println("제품명 : "+smallProduct); // small_product
			System.out.println("이미지 : "+smallProductMainPhotoLink); // small_product_main_photo_link
			System.out.println("포스팅 개수 : "+totalPosting); // small_product_posting_count
			System.out.println("네이버 쇼핑 검색 순서 : "+(order++)); // naver_shopping_order
			
			
			System.out.println("*************** "+(i++)+" ***************");
		}
		
		/*ArrayList<BlliMidCategoryVO> midCategory = (ArrayList<BlliMidCategoryVO>)productDAO.getMidCategory();
		for(int i=0;i<midCategory.size();i++){
			BlliSmallProductVO blliSmallProductVO = new BlliSmallProductVO();
			Document doc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?pagingIndex=1&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&cat_id="+midCategory.get(i).getCategoryId()+"&frm=NVSHMDL").get();
			
		}*/
	}

	/**
	  * @Method Name : selectRecommendingMidCategory
	  * @Method 설명 : 회원의 추천받을 아이이름과 , 아이디를 이용해 추천대상인 중분류 제품을 선정한다.(회원이 추천을 기피했던 중제품 제외)
	  * @작성일 : 2016. 1. 20.
	  * @작성자 : junyoung
	  * @param blliBabyVO
	  * @return
	 */
	@Override
	public List<BlliMidCategoryVO> selectRecommendingMidCategory(BlliBabyVO blliBabyVO) {
		//회원의 추천받을 아이이름과 , 아이디를 이용해 추천대상인 중분류 제품을 선정한다.(회원이 추천을 기피했던 중제품 제외)
		List<BlliMidCategoryVO> blliMidCategoryVOList = productDAO.selectRecommendingMidCategory(blliBabyVO);
		//기피 중분류들을 추출한다.
		List<BlliNotRecommMidCategoryVO> notRecommMidCategoryList = productDAO.selectNotRecommMidCategoryList(blliBabyVO); 
		//이중 for문으로 추천받을 중분류 제품에서 기피한 중제품을 제거한다.
		if(blliMidCategoryVOList!=null&&notRecommMidCategoryList!=null)
		for(int i=0;i<notRecommMidCategoryList.size();i++){
			for(int j=0;j<blliMidCategoryVOList.size();j++){
				if(notRecommMidCategoryList.get(i).getCategoryId().equals(blliMidCategoryVOList.get(j).getCategoryId())){
					blliMidCategoryVOList.remove(j);
				}
			}
		}
		return blliMidCategoryVOList;
	}
	/**
	  * @Method Name : deleteRecommendMidCategory
	  * @Method 설명 : 회원이 중분류 카테고리를 추천받고 싶지않을 때 추천 받지 않을 중분류 제품을 삭제해준다.
	  * @작성일 : 2016. 1. 20.
	  * @작성자 : junyoung
	  * @param blliNotRecommMidCategoryVO
	 */
	@Override
	public void deleteRecommendMidCategory(BlliNotRecommMidCategoryVO blliNotRecommMidCategoryVO) {
		productDAO.deleteRecommendMidCategory(blliNotRecommMidCategoryVO);
	}

	@Override
	public List<BlliSmallProductVO> selectSameAgeMomBestPickedSmallProductList(
			List<BlliMidCategoryVO> blliMidCategoryVOList, BlliBabyVO blliBabyVO) {
		List<BlliSmallProductVO> blliSmallProductVOList = new ArrayList<BlliSmallProductVO>();
		int recommMidNumber = blliMidCategoryVOList.size();
		
		if(recommMidNumber>9){
			for(int i=0;i<blliMidCategoryVOList.size();i++){
				HashMap<String,String> paraMap = new HashMap<String, String>();
				paraMap.put("recommMid", blliMidCategoryVOList.get(i).getMidCategory());
				paraMap.put("babyMonthAge",Integer.toString(blliBabyVO.getBabyMonthAge()));
				//중제품 당 찜 상위 1개 만을 가져온다.
				blliSmallProductVOList.add(productDAO.selectSameAgeMomBestPickedSmallProduct(paraMap));
			}
		}else{
			for(int i=0;i<blliMidCategoryVOList.size();i++){
				HashMap<String,String> paraMap = new HashMap<String, String>();
				paraMap.put("recommMid", blliMidCategoryVOList.get(i).getMidCategory());
				paraMap.put("babyMonthAge",Integer.toString(blliBabyVO.getBabyMonthAge()));
				// 중제품 당 찜 상위 2개씩을 가져온다.
				List<BlliSmallProductVO> tempList = productDAO.selectSameAgeMomBestPickedSmallProductList(paraMap);
				for(int j=0;j<tempList.size();j++){
					blliSmallProductVOList.add(tempList.get(j));
				}
			}
		}
		
		return blliSmallProductVOList;
	}
}
