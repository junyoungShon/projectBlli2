package kr.co.blli.model.scheduler;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.annotation.Resource;

import kr.co.blli.model.product.ProductDAO;
import kr.co.blli.model.vo.BlliBigCategoryVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliSmallProductBuyLinkVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

//@Component
@Service
public class CategoryAndProductScheduler {
	
	@Resource
	private ProductDAO productDAO;
	
	/**
	 * 
	 * @Method Name : insertBigCategory
	 * @Method 설명 : 매일 오전 1시에 네이버 쇼핑에 있는 출산/육아 카테고리에 있는 대분류 리스트를 받아와 
	 * 					  DB에 insert or update해주는 메서드 
	 * @작성일 : 2016. 1. 20.
	 * @작성자 : hyunseok
	 * @throws IOException
	 */
	//@Scheduled(cron = "00 00 01 * * *")
	public void insertBigCategory() throws IOException {
		Logger logger = Logger.getLogger(Main.class.getName());
		Document doc = Jsoup.connect("http://shopping.naver.com/category/category.nhn?cat_id=50000005").timeout(0).get();
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
			System.out.println(bigCategory);
			logger.warn("대분류명 : "+bigCategory);
		}
	}
	
	/**
	 * 
	 * @Method Name : insertMidCategory
	 * @Method 설명 : 매일 오전 1시 10분에 네이버 쇼핑에 있는 출산/육아 카테고리에 있는 중분류 리스트를 받아와 
	 * 					  DB에 insert or update해주는 메서드 
	 * @작성일 : 2016. 1. 20.
	 * @작성자 : hyunseok
	 * @throws IOException
	 */
	//@Scheduled(cron = "00 10 01 * * *")
	public void insertMidCategory() {
		int index = 1;
		int count = 0;
		boolean flag = true;
		int exceptionCount = 0;
		ArrayList<BlliBigCategoryVO> bigCategory = (ArrayList<BlliBigCategoryVO>)productDAO.getBigCategory();
		
		while(flag){
			try{
				for(int i=count;i<bigCategory.size();i++){
					if(exceptionCount > 5){ //Exception이 발생하면 다섯번까지는 재실행해보고 여전히 Exception이
											  //발생하게 되면 다음 대분류에 대해 실행하게 된다
						count = i+1;
						exceptionCount = 0;
					}else{
						count = i;
					}
					Document doc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?cat_id="+bigCategory.
									  get(i).getBigCategoryId()).timeout(0).get();
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
							String imgSrc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?cat_id="+categoryId).
									          timeout(0).get().select("._model_list .img_area img").attr("data-original");
							if(imgSrc == null || imgSrc.equals("")){
								imgSrc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?cat_id="+categoryId).timeout(0).
										  get().select("._product_list .img_area img").attr("data-original");
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
				flag = false;
			}catch(Exception e){
				exceptionCount++;
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @Method Name : insertSmallProduct
	 * @Method 설명 : 매일 오전 2시에 네이버 쇼핑에 있는 출산/육아 카테고리에 있는 소제품 리스트를 받아와
	 * 					 DB에 insert or update해주는 메서드 
	 * @작성일 : 2016. 1. 21.
	 * @작성자 : hyunseok
	 * @throws IOException
	 */
	//@Scheduled(cron = "00 00 02 * * *")
	public void insertSmallProduct() {
		long start = System.currentTimeMillis(); // 시작시간 
		
		int countOfMidCategory = 0;
		int countOfAllSmallProduct = 0;
		ArrayList<BlliMidCategoryVO> midCategory = (ArrayList<BlliMidCategoryVO>)productDAO.getMidCategory();
		String key = "6694c8294c8d04cdfe78262583a13052"; //네이버 검색API 이용하기 위해 발급받은 key값
		int count = 0;
		int exceptionCount = 0;
		boolean flag = true;
		
		while(flag){
			try{	
				for(int i=count;i<midCategory.size();i++){
					if(exceptionCount > 5){
						count = i+1;
						exceptionCount = 0;
					}else{
						count = i;
					}
					BlliSmallProductVO blliSmallProductVO = new BlliSmallProductVO();
					int naverShoppingRank = 0;
					int page = 1;
					double lastPage = 0;
					
					System.out.println("중분류 카테고리 : "+midCategory.get(i).getMidCategory());
					System.out.println("중분류 카운트 : "+(i+1));
					if(midCategory.size() == (i+1)){
						countOfMidCategory = (i+1);
					}
					int countOfSmallProduct = 0;
					do{
						Document doc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?pagingIndex="+
								page+"&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&cat_id="+
								midCategory.get(i).getMidCategoryId()+"&frm=NVSHMDL&oldModel=true").timeout(0).get();
						int resultCount = Integer.parseInt(doc.select("#_resultCount").text().replace(",", ""));
						lastPage = Math.ceil(resultCount/40.0);
						double maxSmallProduct = 10.0;
						if(resultCount > 10){
							if(Math.ceil(resultCount/10.0) < 10){
								maxSmallProduct = Math.ceil(resultCount/10.0);
							}
						}
						Elements e = doc.select(".goods_list ._model_list");
						for(Element el : e){
							countOfSmallProduct++;
							naverShoppingRank++;
							//System.out.println("*************** 소제품 ***************");
							
							String smallProduct = el.select(".info .tit").text();
							smallProduct = smallProduct.replaceAll("&", "%26");
							try{
								doc = Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+smallProduct+
										"&display=1&start=1&target=blog&sort=sim").timeout(5000).get();
								if(doc.select("message").text().contains("Query limit exceeded")){
									key = "0a044dc7c63b8f3b9394e1a5e49db7ab";
									continue;
								}
							}catch(SocketTimeoutException se){
								se.printStackTrace();
								continue;
							}
							String totalPostingText = doc.select("total").text().trim();
							if(totalPostingText.equals("")){
								totalPostingText = "0";
							}
							int smallProductPostingCount = Integer.parseInt(totalPostingText);
							
							if(smallProductPostingCount >= 20 && smallProductPostingCount <= 50 && resultCount > 10){
								if(countOfSmallProduct > maxSmallProduct){
									countOfSmallProduct--;
									continue;
								}
							}else if(smallProductPostingCount < 50 || smallProductPostingCount > 1000){
								countOfSmallProduct--;
								continue;
							}
							
							String smallProductMainPhotoLink = el.select(".img_area ._productLazyImg").attr("data-original");
							String smallProductMaker = el.select(".info_mall .mall_txt .mall_img").attr("title");
							if(smallProductMaker.equals("")){
								smallProductMaker = "-";
							}
							String productRegisterDay = el.select(".etc .date").text();
							productRegisterDay = productRegisterDay.substring(productRegisterDay.indexOf(" ")+1, productRegisterDay.lastIndexOf("."))+".01";
							String smallProductId = el.select(".img_area .report").attr("product_id");
							
							
							/*System.out.println("중분류 : "+midCategory.get(i).getMidCategory());
							System.out.println("제품명 : "+smallProduct);
							System.out.println("이미지 : "+smallProductMainPhotoLink);
							System.out.println("포스팅 개수 : "+smallProductPostingCount);
							System.out.println("네이버 쇼핑 검색 순서 : "+(naverShoppingRank));
							System.out.println("제조사 : "+smallProductMaker);
							System.out.println("제조일 : "+productRegisterDay);
							System.out.println("제품ID : "+smallProductId);*/
							
							blliSmallProductVO.setMidCategory(midCategory.get(i).getMidCategory());
							blliSmallProductVO.setMidCategoryId(midCategory.get(i).getMidCategoryId());
							blliSmallProductVO.setSmallProduct(smallProduct.replaceAll("%26", "&"));
							blliSmallProductVO.setSmallProductMainPhotoLink(smallProductMainPhotoLink);
							blliSmallProductVO.setSmallProductPostingCount(smallProductPostingCount);
							blliSmallProductVO.setNaverShoppingRank(naverShoppingRank);
							blliSmallProductVO.setSmallProductMaker(smallProductMaker);
							blliSmallProductVO.setProductRegisterDay(productRegisterDay);
							blliSmallProductVO.setSmallProductId(smallProductId);
							int updateResult = productDAO.updateSmallProduct(blliSmallProductVO);
							if(updateResult == 0){
								productDAO.insertSmallProduct(blliSmallProductVO);
							}
							
							doc = Jsoup.connect("http://shopping.naver.com/detail/detail.nhn?nv_mid="+smallProductId+"&cat_id="+midCategory.get(i).getMidCategoryId()+"&frm=NVSHMDL&query=").timeout(0).get();
							Elements ele = doc.select("#price_compare tbody tr");
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
				flag = false;
			}catch(Exception e){
				exceptionCount++;
				e.printStackTrace();
			}
		}
		System.out.println("*****************************************");
		System.out.println("총 중분류 개수 : "+countOfMidCategory);
		System.out.println("총 소제품 개수 : "+countOfAllSmallProduct);
		
		long end = System.currentTimeMillis();  //종료시간
		
		//종료-시작=실행시간		
		System.out.println("실행시간  : "+(end-start)/1000.0+"초");
	}
	
}