package kr.co.blli.model.scheduler;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;

import javax.annotation.Resource;

import kr.co.blli.model.product.ProductDAO;
import kr.co.blli.model.vo.BlliBigCategoryVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliSmallProductBuyLinkVO;
import kr.co.blli.model.vo.BlliSmallProductVO;
import kr.co.blli.utility.BlliFileDownLoader;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Component
@Service
public class CategoryAndProductScheduler {
	
	@Resource
	private ProductDAO productDAO;
	@Resource
	private BlliFileDownLoader blliFileDownLoader;
	
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
		Random random = new Random();
		int r = random.nextInt(3000);
		try {
			System.setProperty("random", Integer.toString(r));
			Thread.sleep(r);
			if(Integer.parseInt(System.getProperty("random")) == r){
				long start = System.currentTimeMillis(); // 시작시간 
				Logger logger = Logger.getLogger(getClass());
				String methodName = new Throwable().getStackTrace()[0].getMethodName();
				logger.info("start : "+methodName);
				logger.info("요청자 : scheduler");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
				Calendar cal = Calendar.getInstance();
				String datetime = sdf.format(cal.getTime());
				logger.info("발생 일자 : "+datetime);
				
				int bigCategoryCount = 0;
				int insertBigCategoryCount = 0;
				int updateBigCategoryCount = 0;
				int exceptionCount = 0;
				int allExceptionCount = 0;
				LinkedHashMap<String, String> detailException = new LinkedHashMap<String, String>();
				String bigCategoryId = "";
				boolean flag = true;
				
				while(flag){
					try{
						Document doc = Jsoup.connect("http://shopping.naver.com/category/category.nhn?cat_id=50000005").timeout(0).get();
						Elements bigCategories = doc.select(".category_cell h3 a");
						for(Element e : bigCategories){
							bigCategoryId = e.attr("href");
							bigCategoryId = bigCategoryId.substring(bigCategoryId.lastIndexOf("=")+1);
							String bigCategory = e.select("strong").text();
							BlliBigCategoryVO blliBigCategoryVO = new BlliBigCategoryVO(bigCategory, bigCategoryId);
							int updateResult = productDAO.updateBigCategory(blliBigCategoryVO);
							if(updateResult == 0){
								productDAO.insertBigCategory(blliBigCategoryVO);
								insertBigCategoryCount++;
							}else{
								updateBigCategoryCount++;
							}
							bigCategoryCount++;
						}
						flag = false;
					}catch(Exception e){
						exceptionCount++;
						if(!detailException.containsKey(bigCategoryId)){
							allExceptionCount++;
							detailException.put(bigCategoryId, e.getMessage());
						}
						if(exceptionCount > 5){
							flag = false;
						}
					}
				}
				logger.info("총 대분류 개수 : "+bigCategoryCount);
				logger.info("insert한 대분류 개수 : "+insertBigCategoryCount);
				logger.info("update한 대분류 개수 : "+updateBigCategoryCount);
				logger.info("Exception 발생 횟수 : "+allExceptionCount);
				Iterator<String> bigCategoryIdList = detailException.keySet().iterator();
				while(bigCategoryIdList.hasNext()){
					bigCategoryId = bigCategoryIdList.next();
					logger.info("Exception이 발생한 bigCategoryId : "+bigCategoryId);
					logger.info("Exception 내용 : "+detailException.get(bigCategoryId));
				}
				
				long end = System.currentTimeMillis();  //종료시간
				
				//종료-시작=실행시간		
				logger.info("실행 시간  : "+(int)Math.ceil((end-start)/1000.0)+"초");
				logger.info("end : "+methodName);
			} 
		}catch (InterruptedException e) {
			e.printStackTrace();
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
	@Scheduled(cron = "00 10 01 * * *")
	public void insertMidCategory() {
		Random random = new Random();
		int r = random.nextInt(3000);
		try {
			System.setProperty("random", Integer.toString(r));
			Thread.sleep(r);
			if(Integer.parseInt(System.getProperty("random")) == r){
				long start = System.currentTimeMillis(); // 시작시간 
				Logger logger = Logger.getLogger(getClass());
				String methodName = new Throwable().getStackTrace()[0].getMethodName();
				logger.info("start : "+methodName);
				logger.info("요청자 : scheduler");
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
				String datetime = sdf.format(cal.getTime());
				logger.info("발생 일자 : "+datetime);
				
				ArrayList<BlliBigCategoryVO> bigCategory = (ArrayList<BlliBigCategoryVO>)productDAO.getBigCategory();
				String midCategoryId = "";
				int count = 0;
				int midCategoryCount = 0;
				int insertMidCategoryCount = 0;
				int updateMidCategoryCount = 0;
				int exceptionCount = 0;
				int allExceptionCount = 0;
				boolean flag = true;
				LinkedHashMap<String, String> detailException = new LinkedHashMap<String, String>();
				
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
									midCategoryCount++;
									BlliMidCategoryVO blliMidCategoryVO = new BlliMidCategoryVO();
									String midCategory = e.attr("title");
									midCategory = midCategory.substring(midCategory.lastIndexOf(" ")+1);
									midCategoryId = e.attr("_value");
									blliMidCategoryVO.setMidCategory(midCategory);
									blliMidCategoryVO.setMidCategoryId(midCategoryId);
									blliMidCategoryVO.setBigCategory(bigCategory.get(i).getBigCategory());
									String imgSrc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?cat_id="+midCategoryId).
											          timeout(0).get().select("._model_list .img_area img").attr("data-original");
									if(imgSrc == null || imgSrc.equals("")){
										imgSrc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?cat_id="+midCategoryId).timeout(0).
												  get().select("._product_list .img_area img").attr("data-original");
									}
									
									doc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?pagingIndex=1"+
											"&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&cat_id="+
											midCategoryId+"&frm=NVSHMDL&oldModel=true").timeout(0).get();
									int smallProductCount = Integer.parseInt(doc.select("#_resultCount").text().replace(",", ""));
									blliMidCategoryVO.setSmallProductCount(smallProductCount);
									int updateResult = productDAO.updateMidCategory(blliMidCategoryVO);
									if(updateResult == 0){
										blliMidCategoryVO.setMidCategoryMainPhotoLink(
												blliFileDownLoader.imgFileDownLoader(imgSrc, midCategoryId, "midCategory"));
										productDAO.insertMidCategory(blliMidCategoryVO);
										insertMidCategoryCount++;
									}else{
										updateMidCategoryCount++;
									}
								} //if
							} //for
						} //for
						flag = false;
					}catch(Exception e){
						exceptionCount++;
						if(!detailException.containsKey(midCategoryId)){
							allExceptionCount++;
							detailException.put(midCategoryId, e.getMessage());
						}
					}
				}
				logger.info("총 대분류 개수 : "+bigCategory.size());
				logger.info("총 중분류 개수 : "+midCategoryCount);
				logger.info("insert한 중분류 개수 : "+insertMidCategoryCount);
				logger.info("update한 중분류 개수 : "+updateMidCategoryCount);
				logger.info("Exception 발생 횟수 : "+allExceptionCount);
				Iterator<String> midCategoryIdList = detailException.keySet().iterator();
				while(midCategoryIdList.hasNext()){
					midCategoryId = midCategoryIdList.next();
					logger.info("Exception이 발생한 midCategoryId : "+midCategoryId);
					logger.info("Exception 내용 : "+detailException.get(midCategoryId));
				}
				long end = System.currentTimeMillis();  //종료시간
				
				//종료-시작=실행시간		
				if((end-start/1000) > 60){
					int minute = (int)Math.floor(((end-start)/1000.0)/60);
					int second = (int)Math.ceil((end-start)/1000.0-minute*60);
					logger.info("실행 시간  : "+minute+"분 "+second+"초");
				}else{
					logger.info("실행 시간  : "+(end-start)/1000.0+"초");
				}
				logger.info("end : "+methodName);
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
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
	@Scheduled(cron = "00 00 02 * * *")
	public void insertSmallProduct() {
		Random random = new Random();
		int r = random.nextInt(3000);
		try {
			System.setProperty("random", Integer.toString(r));
			Thread.sleep(r);
			if(Integer.parseInt(System.getProperty("random")) == r){
				long start = System.currentTimeMillis(); // 시작시간 
				Logger logger = Logger.getLogger(getClass());
				String methodName = new Throwable().getStackTrace()[0].getMethodName();
				logger.info("start : "+methodName);
				logger.info("요청자 : scheduler");
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
				String datetime = sdf.format(cal.getTime());
				logger.info("발생 일자 : "+datetime);
				
				ArrayList<BlliMidCategoryVO> midCategory = (ArrayList<BlliMidCategoryVO>)productDAO.getMidCategory();
				String key = "6694c8294c8d04cdfe78262583a13052"; //네이버 검색API 이용하기 위해 발급받은 key값
				int allSmallProductCount = 0;
				int insertSmallProductCount = 0;
				int updateSmallProductCount = 0;
				int denySmallProductCount = 0;
				int notUpdateProductCount = 0;
				int allExceptionCount = 0;
				String smallProductId = "";
				LinkedHashMap<String, String> detailException = new LinkedHashMap<String, String>();
				int naverShoppingRank = 0;
				
				boolean flag = true;
				int count = 0;
				int exceptionCount = 0;
				
				while(flag){
					try{	
						for(int i=count;i<midCategory.size();i++){
							if(exceptionCount > 3){
								count = i+1;
								exceptionCount = 0;
							}else{
								count = i;
							}
							BlliSmallProductVO blliSmallProductVO = new BlliSmallProductVO();
							int page = 1;
							double lastPage = 0;
							
							/*System.out.println("중분류 카테고리 : "+midCategory.get(i).getMidCategory());
							System.out.println("중분류 카운트 : "+(i+1));*/
							
							do{
								Document doc = Jsoup.connect("http://shopping.naver.com/search/list.nhn?pagingIndex="+
										page+"&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&cat_id="+
										midCategory.get(i).getMidCategoryId()+"&frm=NVSHMDL&oldModel=true").timeout(0).get();
								int resultCount = Integer.parseInt(doc.select("#_resultCount").text().replace(",", ""));
								lastPage = Math.ceil(resultCount/40.0);
								double maxSmallProduct = 10;
								if(resultCount > 10 && resultCount < 100){
									maxSmallProduct = resultCount/10;
								}
								Elements e = doc.select(".goods_list ._model_list");
								for(Element el : e){
									//System.out.println("*************** 소제품 ***************");
									naverShoppingRank++;
									
									smallProductId = el.select(".img_area .report").attr("product_id");
									String smallProductStatus = productDAO.getSmallProductStatus(smallProductId);
									
									if(smallProductStatus == null){
										String smallProduct = el.select(".info .tit").text();
										smallProduct = smallProduct.replaceAll("&", "%26");
										try{
											doc = Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+smallProduct+
													"&display=1&start=1&target=blog&sort=sim").timeout(5000).get();
											if(doc.select("message").text().contains("Query limit exceeded")){
												key = "0a044dc7c63b8f3b9394e1a5e49db7ab";
												doc = Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+smallProduct+
														"&display=1&start=1&target=blog&sort=sim").timeout(5000).get();
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
											if(naverShoppingRank > maxSmallProduct){
												denySmallProductCount++;
												blliSmallProductVO.setSmallProductId(smallProductId);
												blliSmallProductVO.setMidCategoryId(midCategory.get(i).getMidCategoryId());
												blliSmallProductVO.setMidCategory(midCategory.get(i).getMidCategory());
												productDAO.insertDeadSmallProduct(blliSmallProductVO);
												continue;
											}
										}else if(smallProductPostingCount < 50 || smallProductPostingCount > 1000){
											denySmallProductCount++;
											blliSmallProductVO.setSmallProductId(smallProductId);
											blliSmallProductVO.setMidCategoryId(midCategory.get(i).getMidCategoryId());
											blliSmallProductVO.setMidCategory(midCategory.get(i).getMidCategory());
											productDAO.insertDeadSmallProduct(blliSmallProductVO);
											continue;
										}
										
										String smallProductMaker = el.select(".info_mall .mall_txt .mall_img").attr("title");
										if(smallProductMaker.equals("")){
											smallProductMaker = "-";
										}
										String productRegisterDay = el.select(".etc .date").text();
										productRegisterDay = productRegisterDay.substring(productRegisterDay.indexOf(" ")+1, 
												productRegisterDay.lastIndexOf("."))+".01";
										
										
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
										blliSmallProductVO.setSmallProductPostingCount(smallProductPostingCount);
										blliSmallProductVO.setNaverShoppingRank(naverShoppingRank);
										blliSmallProductVO.setSmallProductMaker(smallProductMaker);
										blliSmallProductVO.setProductRegisterDay(productRegisterDay);
										blliSmallProductVO.setSmallProductId(smallProductId);
										
										doc = Jsoup.connect("http://shopping.naver.com/detail/detail.nhn?nv_mid="+smallProductId+
												"&cat_id="+midCategory.get(i).getMidCategoryId()+"&frm=NVSHMDL&query=").timeout(0).get();
										String smallProductMainPhotoLink = doc.select("#summary_thumbnail_img").attr("src");
										blliSmallProductVO.setSmallProductMainPhotoLink(
											blliFileDownLoader.imgFileDownLoader(smallProductMainPhotoLink, smallProductId, "smallProduct"));
										
										productDAO.insertSmallProduct(blliSmallProductVO);
										
										Elements ele = doc.select("#price_compare tbody tr");
										//System.out.println("*************** 구매링크 ***************");
										for(Element elem : ele){
											BlliSmallProductBuyLinkVO blliSmallProductBuyLinkVO = new BlliSmallProductBuyLinkVO();
											String buyLink = elem.select(".buy_area a").attr("href");
											String buyLinkPrice = elem.select(".price a").text().replace(",", "");
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
											System.out.println("쇼핑몰 : "+seller);
											System.out.println("제품 ID : "+smallProductId);*/
											
											blliSmallProductBuyLinkVO.setSmallProductId(smallProductId);
											blliSmallProductBuyLinkVO.setBuyLink(buyLink);
											blliSmallProductBuyLinkVO.setBuyLinkPrice(buyLinkPrice);
											blliSmallProductBuyLinkVO.setBuyLinkDeliveryCost(buyLinkDeliveryCost);
											blliSmallProductBuyLinkVO.setBuyLinkOption(buyLinkOption);
											blliSmallProductBuyLinkVO.setSeller(seller);
											
											int isSmallProductSeller = productDAO.isSmallProductSeller(blliSmallProductBuyLinkVO);
											if(isSmallProductSeller == 0){
												productDAO.insertSmallProductBuyLink(blliSmallProductBuyLinkVO);
											}
										}
										insertSmallProductCount++;
										//System.out.println("*****************************************");
									}else if(smallProductStatus.equals("confirmed")){
										String smallProduct = el.select(".info .tit").text();
										smallProduct = smallProduct.replaceAll("&", "%26");
										try{
											doc = Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+smallProduct+
													"&display=1&start=1&target=blog&sort=sim").timeout(5000).get();
											if(doc.select("message").text().contains("Query limit exceeded")){
												key = "0a044dc7c63b8f3b9394e1a5e49db7ab";
												doc = Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+smallProduct+
														"&display=1&start=1&target=blog&sort=sim").timeout(5000).get();
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
										blliSmallProductVO.setSmallProductPostingCount(smallProductPostingCount);
										blliSmallProductVO.setNaverShoppingRank(naverShoppingRank);
										blliSmallProductVO.setSmallProductId(smallProductId);
										
										productDAO.updateSmallProduct(blliSmallProductVO);
										
										doc = Jsoup.connect("http://shopping.naver.com/detail/detail.nhn?nv_mid="+
										smallProductId+"&cat_id="+midCategory.get(i).getMidCategoryId()+"&frm=NVSHMDL&query=").timeout(0).get();
										Elements ele = doc.select("#price_compare tbody tr");
										//System.out.println("*************** 구매링크 ***************");
										for(Element elem : ele){
											BlliSmallProductBuyLinkVO blliSmallProductBuyLinkVO = new BlliSmallProductBuyLinkVO();
											String buyLink = elem.select(".buy_area a").attr("href");
											String buyLinkPrice = elem.select(".price a").text().replace(",", "");
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
											
											productDAO.updateSmallProductBuyLink(blliSmallProductBuyLinkVO);
										}
										updateSmallProductCount++;
									}else{
										notUpdateProductCount++;
									}
								}
								page++;
								allSmallProductCount += naverShoppingRank;
								naverShoppingRank = 0;
							}while(page <= lastPage);
						}
						flag = false;
					}catch(Exception e){
						exceptionCount++;
						if(!detailException.containsKey(smallProductId)){
							allExceptionCount++;
							detailException.put(smallProductId, e.getMessage());
						}
					}
				}
				logger.info("총 중분류 개수 : "+midCategory.size());
				logger.info("총 소제품 개수 : "+allSmallProductCount);
				logger.info("insert한 소제품 개수 : "+insertSmallProductCount);
				logger.info("update한 소제품 개수 : "+updateSmallProductCount);
				logger.info("insert한 조건에 맞지 않는 소제품 개수 : "+denySmallProductCount);
				logger.info("update하지 않은 소제품 개수 : "+notUpdateProductCount);
				logger.info("Exception 발생 횟수 : "+allExceptionCount);
				Iterator<String> smallProductIdList = detailException.keySet().iterator();
				while(smallProductIdList.hasNext()){
					smallProductId = smallProductIdList.next();
					logger.info("Exception이 발생한 smallProductId : "+smallProductId);
					logger.info("Exception 내용 : "+detailException.get(smallProductId));
				}
				long end = System.currentTimeMillis();  //종료시간
				
				//종료-시작=실행시간		
				if((end-start)/1000 > 60*60){
					int hour = (int)Math.floor((((end-start)/1000.0)/60.0)/60);
					int minute = (int)Math.floor(((end-start)/1000.0)/60-hour*60);
					int second = (int)Math.ceil((end-start)/1000.0-minute*60);
					logger.info("실행 시간  : "+hour+"시간 "+minute+"분 "+second+"초");
				}else if((end-start)/1000 > 60){
					int minute = (int)Math.floor(((end-start)/1000.0)/60.0);
					int second = (int)Math.ceil((end-start)/1000.0-minute*60);
					logger.info("실행 시간  : "+minute+"분 "+second+"초");
				}else{
					logger.info("실행 시간  : "+(int)Math.ceil((end-start)/1000.0)+"초");
				}
				logger.info("end : "+methodName);
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}