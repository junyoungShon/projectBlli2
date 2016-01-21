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
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductBuyLinkVO;
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
			Document doc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?cat_id="+bigCategory.get(i).getBigCategoryId()).get();
			Elements midCategoriesHtml = doc.select(".finder .finder_row .finder_list a");
			for(Element e : midCategoriesHtml){
				if(e.attr("title").contains("출산/육아>"+bigCategory.get(i).getBigCategory())){
					BlliMidCategoryVO blliMidCategoryVO = new BlliMidCategoryVO();
					String midCategory = e.attr("title");
					midCategory = midCategory.substring(midCategory.lastIndexOf(" ")+1);
					String categoryId = e.attr("_value");
					blliMidCategoryVO.setMidCategory(midCategory);
					blliMidCategoryVO.setMidCategoryId(categoryId);
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
		long start = System.currentTimeMillis(); // 시작시간 
		//System.out.println("시작시간 : "+start);
		
		//System.out.println(Jsoup.connect("http://cr2.shopping.naver.com/adcrNoti.nhn?x=vK2wx%2FkkeF3RxIGrb2K3Iv%2F%2F%2Fw%3D%3DshotYqpoRlc5RyW%2FEZ7xM1U5Ie4RBwlEhjxrTh3jqregck%2FteIRnv6xKR9eidiY9kMups5UjNXxG%2F%2FoH7KpPkRKAe51AOfyXHtwOr7JwaIPtNYkiqCN3qDml5ZEtXKBL%2F5H2y%2BjDxLRNT6MQKeYR28D0Sj43oDikpYRg3MFYOhk3HQu%2F2xkp2QF0w9tAYjXIGKU3pzTjydW0EJaZMR9IlCCVWiemc41DK%2Bso4QHLSz3VdqViG%2FK0ZiAcl5kz2oFpzW6BIbRUzvePtOBVZBnmnK6195C9CZhrP9GvjM2KyXr46otwoM8x204COnUi43W7PCquII3QhIWm1aXa3atmHsHW7wOxISDL%2BK949F2dg5N7h9YzdkmJNkP83FfQwfSye7DmSXS%2B%2F3e1SlPkkKiPAlNBqmnlldCizLsSCc6oTEfed7LgjMXoCAk%2B7srwm8%2BFCupC7HBW520J5IC5%2FUy7fns%2BJoywsz0E9M95tSZllCCWnkCkIkdEJkT49H%2BujIRxq5mA%2FChL8ZK7R0br2HJNcZ1w4zqDHnNYyXxSLl1dPbOK%2FWhzUcSEPMuX7ai17XECFpu917dyuhZv3Wh5P8ipSQOi3oAoHw4FK0B1ZJeGd6HffTYunXqmCpvPQvauuiaD%2B&nv_mid=9297500650&cat_id=50000217").get());
		//System.out.println(Jsoup.connect("http://cr2.shopping.naver.com/adcr.nhn?x=vK2wx%2FkkeF3RxIGrb2K3Iv%2F%2F%2Fw%3D%3DshotYqpoRlc5RyW%2FEZ7xM1U5Ie4RBwlEhjxrTh3jqregck%2FteIRnv6xKR9eidiY9kMups5UjNXxG%2F%2FoH7KpPkRKAe51AOfyXHtwOr7JwaIPtNYkiqCN3qDml5ZEtXKBL%2F5H2y%2BjDxLRNT6MQKeYR28D0Sj43oDikpYRg3MFYOhk3HQu%2F2xkp2QF0w9tAYjXIGKU3pzTjydW0EJaZMR9IlCCVWiemc41DK%2Bso4QHLSz3VdqViG%2FK0ZiAcl5kz2oFpzW6BIbRUzvePtOBVZBnmnK6195C9CZhrP9GvjM2KyXr46otwoM8x204COnUi43W7PCquII3QhIWm1aXa3atmHsHW7wOxISDL%2BK949F2dg5N7h9YzdkmJNkP83FfQwfSye7DmSXS%2B%2F3e1SlPkkKiPAlNBqmnlldCizLsSCc6oTEfed7LgjMXoCAk%2B7srwm8%2BFCupC7HBW520J5IC5%2FUy7fns%2BJoywsz0E9M95tSZllCCWnkCkIkdEJkT49H%2BujIRxq5mA%2FChL8ZK7R0br2HJNcZ1w4zqDHnNYyXxSLl1dPbOK%2FWhzUcSEPMuX7ai17XECFpu917dyuhZv3Wh5P8ipSQOi3oAoHw4FK0B1ZJeGd6HffTYunXqmCpvPQvauuiaD%2B").get());
		
		String key = "6694c8294c8d04cdfe78262583a13052"; //네이버 검색API 이용하기 위해 발급받은 key값
		ArrayList<BlliMidCategoryVO> midCategory = (ArrayList<BlliMidCategoryVO>)productDAO.getMidCategory();
		int countOfMidCategory = 0;
		int countOfAllSmallProduct = 0;
		
		for(int i=0;i<midCategory.size();i++){
			
			BlliSmallProductVO blliSmallProductVO = new BlliSmallProductVO();
			int naverShoppingOrder = 1;
			int page = 1;
			double lastPage;
			
			System.out.println("중분류 카테고리 : "+midCategory.get(i).getMidCategory());
			System.out.println("중분류 카운트 : "+(i+1));
			if(midCategory.size() == (i+1)){
				countOfMidCategory = (i+1);
			}
			int countOfSmallProduct = 0;
			do{
				Document doc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?pagingIndex="+page+"&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&cat_id="+midCategory.get(i).getMidCategoryId()+"&frm=NVSHMDL&oldModel=true").get();
				int resultCount = Integer.parseInt(doc.select("#_resultCount").text().replace(",", ""));
				lastPage = Math.ceil(resultCount/40.0);
				Elements e = doc.select(".goods_list ._model_list");
				for(Element el : e){
					countOfSmallProduct++;
					//System.out.println("*************** 소제품 ***************");
					
					String smallProduct = el.select(".info .tit").text();
					String smallProductMainPhotoLink = el.select(".img_area ._productLazyImg").attr("data-original");
					String smallProductMaker = el.select(".info_mall .mall_txt .mall_img").attr("title");
					if(smallProductMaker.equals("")){
						smallProductMaker = "-";
					}
					String productRegisterDay = el.select(".etc .date").text();
					productRegisterDay = productRegisterDay.substring(productRegisterDay.indexOf(" ")+1, productRegisterDay.lastIndexOf("."))+".01";
					String smallProductId = el.select(".img_area .report").attr("product_id");
					String totalPostingText = Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+smallProduct+"&display=1&start=1&target=blog&sort=sim").get().select("total").text();
					if(totalPostingText.equals("")){
						totalPostingText = "0";
					}
					int smallProductPostingCount = Integer.parseInt(totalPostingText);

					/*System.out.println("중분류 : "+midCategory.get(i).getMidCategory());
					System.out.println("제품명 : "+smallProduct);
					System.out.println("이미지 : "+smallProductMainPhotoLink);
					System.out.println("포스팅 개수 : "+smallProductPostingCount);
					System.out.println("네이버 쇼핑 검색 순서 : "+(naverShoppingOrder++));
					System.out.println("제조사 : "+smallProductMaker);
					System.out.println("제조일 : "+productRegisterDay);
					System.out.println("제품ID : "+smallProductId);*/
					
					blliSmallProductVO.setMidCategory(midCategory.get(i).getMidCategory());
					blliSmallProductVO.setMidCategoryId(midCategory.get(i).getMidCategoryId());
					blliSmallProductVO.setSmallProduct(smallProduct);
					blliSmallProductVO.setSmallProductMainPhotoLink(smallProductMainPhotoLink);
					blliSmallProductVO.setSmallProductPostingCount(smallProductPostingCount);
					blliSmallProductVO.setNaverShoppingOrder(naverShoppingOrder++);
					blliSmallProductVO.setSmallProductMaker(smallProductMaker);
					blliSmallProductVO.setProductRegisterDay(productRegisterDay);
					blliSmallProductVO.setSmallProductId(smallProductId);
					int updateResult = productDAO.updateSmallProduct(blliSmallProductVO);
					if(updateResult == 0){
						productDAO.insertSmallProduct(blliSmallProductVO);
					}
					
					Document document = Jsoup.connect("http://shopping.naver.com/detail/detail.nhn?nv_mid="+smallProductId+"&cat_id=8688776147&frm=NVSHMDL&query=").get();
					Elements ele = document.select("#price_compare tbody tr");
					//System.out.println("*************** 구매링크 ***************");
					for(Element elem : ele){
						BlliSmallProductBuyLinkVO blliSmallProductBuyLinkVO = new BlliSmallProductBuyLinkVO();
						String buyLink = elem.select(".buy_area a").attr("href");
						int buyLinkPrice = Integer.parseInt(elem.select(".price a").text().replace(",", ""));
						String buyLinkDeliveryCost = elem.select(".gift").first().text().trim();
						String buyLinkOption = elem.select(".gift").last().text().trim();
						String seller = elem.select(".mall a").text();
						if(seller.equals("")){
							seller = elem.select(".mall a img").attr("alt");
						}
						/*System.out.println("구매 링크 : "+buyLink);
						System.out.println("판매 가격 : "+buyLinkPrice);
						System.out.println("배송비 : "+buyLinkDeliveryCost);
						System.out.println("부가정보 : "+buyLinkOption);
						System.out.println("쇼핑몰 : "+seller);*/
						
						blliSmallProductBuyLinkVO.setSmallProductId(smallProductId);
						blliSmallProductBuyLinkVO.setBuyLink(buyLink);
						blliSmallProductBuyLinkVO.setBuyLinkPrice(buyLinkPrice);
						blliSmallProductBuyLinkVO.setBuyLinkDeliveryCost(buyLinkDeliveryCost);
						blliSmallProductBuyLinkVO.setBuyLinkOption(buyLinkOption);
						blliSmallProductBuyLinkVO.setSeller(seller);
						
						updateResult = productDAO.updateSmallProductBuyLink(blliSmallProductBuyLinkVO);
						if(updateResult == 0){
							productDAO.insertSmallProductBuyLink(blliSmallProductBuyLinkVO);
						}
					}
					//System.out.println("*****************************************");
				}
				page++;
			}while(page <= lastPage);
			System.out.println("소제품 카운트 : "+countOfSmallProduct);
			countOfAllSmallProduct += countOfSmallProduct;
			
		}
		System.out.println("*****************************************");
		System.out.println("총 중분류 개수 : "+countOfMidCategory);
		System.out.println("총 소제품 개수 : "+countOfAllSmallProduct);
		
		long end = System.currentTimeMillis();  //종료시간
		//System.out.println("종료시간 : "+end);
		
		//종료-시작=실행시간		
		System.out.println("실행시간  : "+(end-start)/1000.0+"초");
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
				if(notRecommMidCategoryList.get(i).getMidCategoryId().equals(blliMidCategoryVOList.get(j).getMidCategoryId())){
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
	/**
	  * @Method Name : selectSameAgeMomBestPickedSmallProductList
	  * @Method 설명 : 현재 추천 받고 있는 중분류를 기준으로 또래 엄마들이 가장많이 찜한 상품들을 보여준다.
	  * @작성일 : 2016. 1. 21.
	  * @작성자 : junyoung
	  * @param blliMidCategoryVOList
	  * @param blliBabyVO
	  * @return
	 */
	@Override
	public List<BlliSmallProductVO> selectSameAgeMomBestPickedSmallProductList(
			List<BlliMidCategoryVO> blliMidCategoryVOList, BlliBabyVO blliBabyVO) {
		List<BlliSmallProductVO> blliSmallProductVOList = new ArrayList<BlliSmallProductVO>();
		int recommMidNumber = blliMidCategoryVOList.size();
		System.out.println("여기오냐");
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
		System.out.println(blliSmallProductVOList);
		return blliSmallProductVOList;
	}
	/**
	  * @Method Name : selectPostingBySmallProductList
	  * @Method 설명 : <!극혐주의!> 포스팅 관련 이므로 여기있으면 안되지만 구조상 여기왔다 . 상의해보자
	  * @작성일 : 2016. 1. 21.
	  * @작성자 : junyoung
	  * @param blliMidCategoryVOList
	  * @return
	 */
	@Override
	public List<BlliPostingVO> selectPostingBySmallProductList(List<BlliSmallProductVO> blliSmallProductVOList) {
		List<BlliPostingVO> blliPostingVOList = new ArrayList<BlliPostingVO>();
		//점수순 노출 , 상태(confirmed) , 포스팅 대상 소제품 등을 기준으로 출력<!극혐주의!> 포스팅 관련 이므로 여기있으면 안되지만 구조상 여기왔다 . 상의해보자
		for(int i=0;i<blliSmallProductVOList.size();i++){
			List<BlliPostingVO> tempList = productDAO.selectPostingBySmallProductList(blliSmallProductVOList.get(i).getSmallProductId());
			System.out.println(blliSmallProductVOList.get(i).getMidCategoryId());
			if(tempList!=null){
				for(int j=0;j<tempList.size();j++){
					blliPostingVOList.add(tempList.get(j));
					System.out.println(tempList.get(j));
				}
			}
			
		}
		return blliPostingVOList;
	}
}
