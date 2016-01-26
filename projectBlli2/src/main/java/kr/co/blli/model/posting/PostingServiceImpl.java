package kr.co.blli.model.posting;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.blli.model.product.ProductDAO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;
import kr.co.blli.model.vo.ListVO;
import kr.co.blli.model.vo.PagingBeanOfPostingListWithProducts;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class PostingServiceImpl implements PostingService {
	
	@Resource
	private PostingDAO postingDAO;
	
	@Resource
	private ProductDAO productDAO;
	
	/**
	 * 
	 * @Method Name : jsoupTest
	 * @Method 설명 : 스케줄러에의해 실행되어 소제품 리스트 목록을 불러와 해당 소제품 관련 블로그 포스팅을 insert or update하는 메서드
	 * @작성일 : 2016. 1. 16.
	 * @작성자 : hyunseok
	 * @return
	 * @throws IOException
	 */
	@Override
	public String jsoupTest() throws IOException {
		/*Document doc = Jsoup.connect("http://openapi.naver.com/search?key=0a044dc7c63b8f3b9394e1a5e49db7ab&query=색팽이&display=1&start=74&target=blog&sort=sim").get();
		System.out.println(doc);
		System.out.println("**************************************");
		try{
			doc = Jsoup.connect(doc.select("item link").html()).get(); //openAPI를 통해 얻은 포스팅URL 연결
		}catch(SocketTimeoutException e){
			e.printStackTrace();
		}
		System.out.println(doc);
		System.out.println("**************************************");
		String frameSourceUrl = "http://blog.naver.com" + doc.select("#mainFrame").attr("src"); //frameSourceURL 불러와 설정
		doc = Jsoup.parse(new URL(frameSourceUrl).openStream(),"ms949",frameSourceUrl);
		System.out.println(doc);
		if(doc.select("#post-area script").html().contains("삭제되었거나 존재하지 않는 게시물입니다")){
			System.out.println("삭제되었다 짜샤");
		}
		Elements postingDate = doc.select(".post-top tbody tr .date");
		if(postingDate.size() == 0){ //스마트에디터일 경우
			System.out.println(doc.select(".se_publishDate").text().substring(0, doc.select(".se_publishDate").text().lastIndexOf(".")));
		}else{ //스마트에디터가 아닐 경우
			if(postingDate.attr("class").equals("date fil5 pcol2 _postAddDate")){
				System.out.println(postingDate.text().substring(0, postingDate.text().lastIndexOf("."))); //포스팅 작성일을 vo에 저장
			}
		}*/
		
		
		long start = System.currentTimeMillis(); // 시작시간 
		//System.out.println("시작시간 : "+start);
		
		//String key = "6694c8294c8d04cdfe78262583a13052"; //네이버 검색API 이용하기 위해 발급받은 key값 첫번째
		String key = "2a636a785d0e03f7048319f8adb3d912"; //네이버 검색API 이용하기 위해 발급받은 key값 두번째
		//String key = "0a044dc7c63b8f3b9394e1a5e49db7ab"; //네이버 검색API 이용하기 위해 발급받은 key값 세번째
		
		ArrayList<BlliSmallProductVO> smallProductList = (ArrayList<BlliSmallProductVO>)productDAO.getSmallProduct(); //소제품 리스트를 불러와 변수에 할당
		String postingUrl = ""; //포스팅 주소
		String smallProduct = ""; //검색할 소제품
		String frameSourceUrl = ""; //프레임 소스 주소
		String postingTitle = ""; //포스팅 제목
		String postingContent = ""; //포스팅 본문
		int postingMediaCount = 0; //포스팅에 등록된 이미지 개수
		String postingPhotoLink = ""; //대표 사진 링크
		String postingAuthor = ""; //포스팅 작성자 닉네임
		String postingReplyCount = ""; //댓글 수
		ArrayList<String> regex = new BlliPostingVO().regex; //html에서 제거되야 할 태그와 특수문자들 리스트
		int countOfSmallProduct = 0;
		int countOfAllPosting = 0;
		
		try{
		for(int i=0;i<smallProductList.size();i++){ //소제품들 한개씩 뽑아서 포스팅 검색
			double maxPosting = 50; //검색할 포스팅 최대 개수
			System.out.println("소제품명 : "+smallProductList.get(i).getSmallProduct());
			System.out.println("소제품 카운트 : "+(i+1));
			if(smallProductList.size() == (i+1)){
				countOfSmallProduct = (i+1);
			}
			
			int countOfPosting = 0; //수집한 포스팅 개수
			smallProduct = smallProductList.get(i).getSmallProduct().replaceAll("&", "%26");
			int totalPosting = 0;
			
			Document doc = Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+smallProduct+"&display=100&start=1&target=blog&sort=sim").get();
			
			//소제품 관련 총 포스팅 개수
			String totalPostingText = doc.select("total").text().trim();
			if(totalPostingText.equals("")){
				totalPostingText = "0";
			}
			totalPosting = Integer.parseInt(totalPostingText);
			if(Math.ceil(totalPosting*0.3) < maxPosting){ //총 포스팅 개수의 30%가 50개 미만일 경우 포스팅 최대 개수를 총 포스팅의 30%로 설정
				maxPosting = Math.ceil(totalPosting*0.3);
			}
			Elements item = doc.select("item"); //포스팅 간략 정보
			for(Element e : item){
				if(countOfPosting >= maxPosting){
					break;
				}
				
				String bloggerLink = e.select("bloggerlink").text();
				if(!bloggerLink.contains("http://blog.naver.com")){ //네이버 블로그 포스팅이 아닐 경우 -> countOfPosting을 한개 늘려주고 while문 처음으로 이동(네이버 블로그가 아닐 경우 DB에 저장X)
					countOfPosting++;
					continue;
				}
				
				try{
					doc = Jsoup.connect(e.select("link").html()).get(); //openAPI를 통해 얻은 포스팅URL 연결
				}catch(SocketTimeoutException exception){
					continue;
				}
				frameSourceUrl = "http://blog.naver.com" + doc.select("#mainFrame").attr("src"); //frameSourceURL 불러와 설정
				doc = Jsoup.parse(new URL(frameSourceUrl).openStream(),"ms949",frameSourceUrl);
				
				if(doc.select("#post-area script").html().contains("삭제되었거나 존재하지 않는 게시물입니다")){
					countOfPosting++;
					continue;
				}
				if(doc.select("#post-area script").html().contains("비공개 포스트 입니다")){
					countOfPosting++;
					continue;
				}
				
				BlliPostingVO postingVO = new BlliPostingVO();
				postingMediaCount = 0; //이미지 갯수 초기화
				postingVO.setSmallProductId(smallProductList.get(i).getSmallProductId()); //소제품ID를 vo에 저장
				postingVO.setSmallProduct(smallProduct.replaceAll("%26", "&")); //소제품을 vo에 저장
				postingVO.setPostingOrder(countOfPosting+1); //포스팅 검색시 배열 순서(검색 순위)를 vo에 저장
				
				Elements reply = doc.select(".postre .pcol2");
				for(Element el : reply){
					if(el.attr("class").contains("pcol2 _cmtList _param")){
						postingReplyCount = el.text().substring(el.text().lastIndexOf(" ")+1);
						if(postingReplyCount.equals("쓰기")){
							postingReplyCount = "0";
						}
						postingVO.setPostingReplyCount(Integer.parseInt(postingReplyCount)); //댓글 수를 vo에 저장
					}
				}
				
				Elements postingDate = doc.select(".post-top tbody tr .date");
				if(postingDate.size() == 0){ //스마트에디터일 경우
					postingVO.setPostingDate(doc.select(".se_publishDate").text().substring(0, doc.select(".se_publishDate").text().lastIndexOf(".")));
				}else{ //스마트에디터가 아닐 경우
					if(postingDate.attr("class").equals("date fil5 pcol2 _postAddDate")){
						postingVO.setPostingDate(postingDate.text().substring(0, postingDate.text().lastIndexOf("."))); //포스팅 작성일을 vo에 저장
					}
				}
				
				Elements metaInfo = doc.select("meta");
				for(Element el : metaInfo){
					String property = el.attr("property"); //meta정보
					if(property.contains("url")){
						postingUrl = el.attr("content");
						postingVO.setPostingUrl(postingUrl); //postingURL을 vo에 저장
					}else if(property.contains("title")){
						postingTitle = el.attr("content");
						postingVO.setPostingTitle(postingTitle); //postingTitle을 vo에 저장
					}else if(property.contains("image")){
						postingPhotoLink = el.attr("content");
						postingVO.setPostingPhotoLink(postingPhotoLink); //postingPhotoLink를 vo에 저장
					}else if(property.contains("author")){
						postingAuthor = el.attr("content");
						postingVO.setPostingAuthor(postingAuthor.substring(postingAuthor.lastIndexOf("|")+2)); //포스팅 작성자를 vo에 저장
					}
				}
				//본문에 등록된 이미지가 없을 시 포스팅 개수 한개 줄이고 while문 처음으로 이동(이미지가 없을 시 네이버 기본 이미지가 들어가 있음)
				if(postingPhotoLink.equals("http://blogimgs.naver.net/nblog/mylog/post/og_default_image2.png")){
					countOfPosting++;
					continue;
				}
				
				Elements imgInfo = doc.select("#postViewArea img");
				ArrayList<String> mediaList = new ArrayList<String>();
				if(imgInfo.size() != 0){ //스마트에디터가 아닐 경우
					//이미지 개수
					for(Element el : imgInfo){
						String src = el.attr("src");
						if(src.contains("postfiles") || src.contains("blogfiles")){
							for(int j=0;j<mediaList.size();j++){
								if(!mediaList.contains(src)){
									mediaList.add(src);
									postingMediaCount++;
								}
							}
							if(mediaList.size() == 0){
								mediaList.add(src);
								postingMediaCount++;
							}
						}
					}
					//영상 갯수
					Elements playerInfo = doc.select("#postViewArea iframe");
					for(Element el : playerInfo){
						String mediaName = el.attr("name");
						if(mediaName.equals("mplayer")){
							postingMediaCount++;
						}
					}
				}else{ //스마트에디터일 경우
					//이미지 개수
					imgInfo = doc.select("#printPost1 tbody a");
					for(Element el : imgInfo){
						String imgType = el.attr("data-linktype");
						if(imgType.equals("img")){
							String src = el.select("img").attr("src");
							for(int j=0;j<mediaList.size();j++){
								if(!mediaList.contains(src)){
									mediaList.add(src);
									postingMediaCount++;
								}
							}
							if(mediaList.size() == 0){
								mediaList.add(src);
								postingMediaCount++;
							}
						}
					}
					//영상 개수
					Elements playerInfo = doc.select("#printPost1 tbody div");
					for(Element el : playerInfo){
						if(el.attr("class").equals("se_mediaArea")){
							postingMediaCount++;
						}
					}
				}
				if(postingMediaCount == 0){ //이미지와 영상의 개수가 0개일때 제거
					countOfPosting++;
					continue;
				}
				postingVO.setPostingMediaCount(postingMediaCount); //이미지와 영상의 개수를 vo에 저장
				
				if(doc.select("#postViewArea").size() != 0){ //스마트에디터가 아닐 경우
					postingContent = doc.select("#postViewArea").html();
					for(int j=0;j<regex.size();j++){ //태그 및 특수문자 제거
						postingContent = postingContent.replaceAll(regex.get(j), "");
					}
					postingContent = postingContent.replaceAll("( )+", " ").trim(); //공란을 한칸으로 변경 및 앞 뒤 공란 제거
				}else{ //스마트에디터일 경우
					postingContent = ""; //본문 초기화
					Elements contentTag = doc.select("#printPost1 tbody p");
					for(Element el : contentTag){
						if(el.attr("class").equals("se_textarea")){ //본문 내용 누적
							postingContent += el.html();
						}
					}
					for(int j=0;j<regex.size();j++){ //태그 및 특수문자 제거
						postingContent = postingContent.replaceAll(regex.get(j), "");
					}
					postingContent = postingContent.replaceAll("( )+", " ").trim(); //공란을 한칸으로 변경 및 앞 뒤 공란 제거
				}
				
				if(postingContent.length() == 0){ //본문 내용이 없으면 제거
					countOfPosting++;
					continue;
				}
				
				postingVO.setPostingContent(postingContent); //본문을 vo에 저장
				
				if(postingContent.length() > 200){ //본문 길이 검사 후 본문 요약 vo에 저장
					postingVO.setPostingSummary(postingContent.substring(0,180) + "...");
				}else{
					postingVO.setPostingSummary(postingContent);
				}
				
				System.out.println("검색 순위 : "+(countOfPosting+1)+"   포스팅 제목 : "+postingVO.getPostingTitle());
				
				int countOfPostingUrl = postingDAO.countOfPostingUrl(postingVO.getPostingUrl());
				int updateResult;
				//url개수에 따른 포스팅 상태와 update, insert 기능
				if(countOfPostingUrl == 0){
					postingDAO.insertPosting(postingVO);
				}else if(countOfPostingUrl == 1){
					String postingStatus = postingDAO.getPostingStatus(postingVO.getPostingUrl());
					if(postingStatus.equals("unconfirmed")){
						updateResult = postingDAO.updatePosting(postingVO);						
						if(updateResult == 0){
							postingDAO.insertPosting(postingVO);
						}
					}else if(postingStatus.equals("confirmed")){
						postingDAO.updatePosting(postingVO);
					}else{
						updateResult = postingDAO.updatePosting(postingVO);
						if(updateResult == 0){
							postingDAO.insertPosting(postingVO);
						}
					}
				}else{
					ArrayList<String> postingStatus = (ArrayList<String>)postingDAO.getAllPostingStatus(postingVO.getPostingUrl());
					int confirmed = 0;
					int dead = 0;
					for(int j=0;j<postingStatus.size();j++){
						if(postingStatus.get(j).equals("confirmed")){
							postingDAO.updatePosting(postingVO);
							confirmed++;
						}else if(postingStatus.get(j).equals("confirmed")){
							updateResult = postingDAO.updatePosting(postingVO);
							if(updateResult == 0){
								postingDAO.insertPosting(postingVO);
							}
						}else{
							dead++;
						}
					}
					if(confirmed == 0){
						if(dead == 0){
							updateResult = postingDAO.updatePosting(postingVO);
							if(updateResult == 0){
								postingDAO.insertPosting(postingVO);
							}
						}
					}
				}
				countOfPosting++;
			}
			System.out.println("포스팅 카운트 : "+countOfPosting);
			countOfAllPosting += countOfPosting;
		} //for
		System.out.println("*****************************************");
		System.out.println("총 소제품 개수 : "+countOfSmallProduct);
		System.out.println("총 포스팅 개수 : "+countOfAllPosting);
		
		long end = System.currentTimeMillis();  //종료시간
		//System.out.println("종료시간 : "+end);
		
		//종료-시작=실행시간		
		System.out.println("실행시간  : "+(end-start)/1000.0+"초");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(postingTitle);
		}
		return "등록 완료!";
	}
	
	/**
	 * 
	 * @Method Name : searchJsoupTest
	 * @Method 설명 : 검색 테스트
	 * @작성일 : 2016. 1. 16.
	 * @작성자 : hyunseok
	 * @param searchWord
	 * @return
	 */
	@Override
	public ArrayList<BlliPostingVO> searchJsoupTest(String searchWord) {
		return (ArrayList<BlliPostingVO>)postingDAO.searchJsoupTest(searchWord);
	}
	
	/**
	 * 
	 * @Method Name : postingListWithSmallProducts
	 * @Method 설명 : 포스팅에 포함되는 소제품이 두개 이상일 경우에 대해 출력해주는 메서드 
	 * @작성일 : 2016. 1. 19.
	 * @return
	 * @throws IOException
	 */
	@Override
	public ListVO postingListWithSmallProducts(String pageNo) throws IOException {
		if(pageNo == null || pageNo == ""){
			pageNo = "1";
		}
		ArrayList<BlliPostingVO> postingList = (ArrayList<BlliPostingVO>)postingDAO.postingListWithSmallProducts(pageNo); // 두개 이상의 소제품을 가지고 있는 포스팅 할당
		String url = "";
		String imgSource = "";
		HashMap<String, String> smallProductImageList = new HashMap<String, String>();
		for(int i=0;i<postingList.size();i++){
			ArrayList<String> smallProductList = new ArrayList<String>();
			if(postingList.get(i).getPostingUrl().equals(url)){ //이전 postingUrl과 현재 postingUrl이 같을 경우 해당 포스팅VO를 지우고 인덱스를 -1 해줌
				postingList.remove(i);
				i--;
				continue;
			}else{ //이전 postingUrl과 현재 postingUrl이 다를 경우 현재 postingUrl에 해당하는 소제품 목록과 대표 이미지 vo에 저장
				url = postingList.get(i).getPostingUrl();
				smallProductList.add(postingList.get(i).getSmallProduct());
				Document doc = Jsoup.connect("http://shopping.naver.com/search/all_search.nhn?query="+postingList.get(i).getSmallProduct()+"&pagingIndex=1&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&frm=NVSHMDL").get();
				Elements imgTag = doc.select("img");
				for(Element e : imgTag){
					if(e.attr("alt").equals(postingList.get(i).getSmallProduct())){
						imgSource = e.attr("data-original");
						smallProductImageList.put(postingList.get(i).getSmallProduct(), imgSource);
						break;
					}
				}
				for(int j=i+1;j<postingList.size();j++){
					if(url.equals(postingList.get(j).getPostingUrl())){
						smallProductList.add(postingList.get(j).getSmallProduct());
						if(!smallProductImageList.containsKey(postingList.get(j).getSmallProduct())){
							doc = Jsoup.connect("http://shopping.naver.com/search/all_search.nhn?query="+postingList.get(j).getSmallProduct()+"&pagingIndex=1&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&frm=NVSHMDL").get();
							imgTag = doc.select("img");
							for(Element e : imgTag){
								if(e.attr("alt").equals(postingList.get(j).getSmallProduct())){
									imgSource = e.attr("data-original");
									smallProductImageList.put(postingList.get(j).getSmallProduct(), imgSource);
									break;
								}
							}
						}
					}else{
						break;
					}
				}
				postingList.get(i).setSmallProductImage(smallProductImageList);
				postingList.get(i).setSmallProductList(smallProductList);
			}
			ArrayList<String> imgList = new ArrayList<String>(); 
			Document doc = Jsoup.connect(url).get();
			String frameSourceUrl = "http://blog.naver.com" + doc.select("#mainFrame").attr("src");
			doc = Jsoup.connect(frameSourceUrl).get();
			Elements imgTag = doc.select("#postViewArea img");
			if(imgTag.size() != 0){ //스마트에디터가 아닐 경우
				for(Element e : imgTag){
					String imgSrc = e.attr("src");
					if(imgSrc.contains("postfiles")){
						imgList.add(imgSrc);
					}
				}
			}else{ //스마트에디터일 경우
				imgTag = doc.select(".se_mediaImage");
				for(Element e : imgTag){
					imgList.add(e.attr("src"));
				}
			}
			postingList.get(i).setImageList(imgList); //포스팅에 등록된 모든 이미지 vo에 저장
		}
		int total = postingDAO.totalPostingWithProducts();
		PagingBeanOfPostingListWithProducts paging = new PagingBeanOfPostingListWithProducts(total, Integer.parseInt(pageNo));
		ListVO lvo = new ListVO(postingList, paging);
		return lvo;
	}

	/**
	 * 
	 * @Method Name : selectProduct
	 * @Method 설명 : 두개 이상의 소제품을 가지고 있는 포스팅을 한개의 소제품으로 변경해주는 메서드
	 * @작성일 : 2016. 1. 19.
	 * @param urlAndProduct
	 */
	@Override
	public void selectProduct(List<Map<String, Object>> urlAndProduct) {
		for(int i=0;i<urlAndProduct.size();i++){
			String selectProduct = urlAndProduct.get(i).get("smallProduct").toString();
			if(selectProduct.equals("")){ //선택하지 않은 포스팅에 대해서는 기능 적용X
				continue;
			}
			String postingUrl = urlAndProduct.get(i).get("postingUrl").toString();
			HashMap<String, String> map = new HashMap<String, String>();
			if(selectProduct.equals("삭제")){
				postingDAO.deletePosting(postingUrl);
			}else{
				map.put("smallProduct", selectProduct);
				map.put("postingUrl", postingUrl);
				postingDAO.selectProduct(map);
				postingDAO.deleteProduct(postingUrl);
			}
		}
	}
}
