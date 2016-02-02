package kr.co.blli.model.scheduler;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

import javax.annotation.Resource;

import kr.co.blli.model.posting.PostingDAO;
import kr.co.blli.model.product.ProductDAO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Component
@Service
public class PostingScheduler {
	
	@Resource
	private ProductDAO productDAO;
	
	@Resource
	private PostingDAO postingDAO;
	
	/**
	 * 
	 * @Method Name : insertPosting
	 * @Method 설명 : 매일 오전 4시에 소제품 리스트 목록을 불러와 해당 소제품 관련 블로그 포스팅을 insert or update하는 메서드
	 * @작성일 : 2016. 1. 16.
	 * @작성자 : hyunseok
	 * @return
	 * @throws IOException
	 */
	@Scheduled(cron = "00 00 04 * * *")
	public void insertPosting() throws IOException {
		long start = System.currentTimeMillis(); // 시작시간 
		
		String key = "2a636a785d0e03f7048319f8adb3d912"; //네이버 검색API 이용하기 위해 발급받은 key값 세번째
		//소제품 리스트를 불러와 변수에 할당
		ArrayList<BlliSmallProductVO> smallProductList = (ArrayList<BlliSmallProductVO>)productDAO.getSmallProduct(); 
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
		boolean flag = true;
		int count = 0;
		
		label:
		while(flag){
			try{
				for(int i=count;i<smallProductList.size();i++){ //소제품들 한개씩 뽑아서 포스팅 검색
					count = i+1;
					double maxPosting = 50; //검색할 포스팅 최대 개수
					System.out.println("소제품명 : "+smallProductList.get(i).getSmallProduct());
					System.out.println("소제품 카운트 : "+(i+1));
					if(smallProductList.size() == (i+1)){
						countOfSmallProduct = (i+1);
					}
					
					int countOfPosting = 0; //수집한 포스팅 개수
					smallProduct = smallProductList.get(i).getSmallProduct().replaceAll("&", "%26");
					int totalPosting = 0;
					
					Document doc = Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+
									smallProduct+"&display=100&start=1&target=blog&sort=sim").timeout(0).get();
					if(doc.select("message").text().contains("Query limit exceeded")){
						key = "2a636a785d0e03f7048319f8adb3d912";
						continue;
					}
					//소제품 관련 총 포스팅 개수
					String totalPostingText = doc.select("total").text().trim();
					if(totalPostingText.equals("")){
						totalPostingText = "0";
					}
					totalPosting = Integer.parseInt(totalPostingText);
					if(Math.ceil(totalPosting*0.3) < maxPosting){ //총 포스팅 개수의 30%가 50개 미만일 경우 
																	 //포스팅 최대 개수를 총 포스팅의 30%로 설정
						maxPosting = Math.ceil(totalPosting*0.3);
					}
					Elements item = doc.select("item"); //포스팅 간략 정보
					for(Element e : item){
						if(countOfPosting >= maxPosting){
							break;
						}
						
						String bloggerLink = e.select("bloggerlink").text();
						//네이버 블로그 포스팅이 아닐 경우 
						//countOfPosting을 한개 늘려주고 while문 처음으로 이동(네이버 블로그가 아닐 경우 DB에 저장X)
						if(!bloggerLink.contains("http://blog.naver.com")){ 
							countOfPosting++;
							continue;
						}
						
						try{
							//openAPI를 통해 얻은 포스팅URL 연결
							doc = Jsoup.connect(e.select("link").html()).timeout(5000).get(); 
						}catch(SocketTimeoutException exception){
							exception.printStackTrace();
							continue;
						}
						//frameSourceURL 불러와 설정
						frameSourceUrl = "http://blog.naver.com" + doc.select("#mainFrame").attr("src"); 
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
						postingVO.setPostingRank(countOfPosting+1); //포스팅 검색시 배열 순서(검색 순위)를 vo에 저장
						
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
							postingVO.setPostingDate(doc.select(".se_publishDate").text().
									substring(0, doc.select(".se_publishDate").text().lastIndexOf(".")));
						}else{ //스마트에디터가 아닐 경우
							if(postingDate.attr("class").equals("date fil5 pcol2 _postAddDate")){
								//포스팅 작성일을 vo에 저장
								postingVO.setPostingDate(postingDate.text().substring(0, postingDate.text().lastIndexOf("."))); 
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
								//포스팅 작성자를 vo에 저장
								postingVO.setPostingAuthor(postingAuthor.substring(postingAuthor.lastIndexOf("|")+2)); 
							}
						}
						//본문에 등록된 이미지가 없을 시 포스팅 개수 한개 줄이고 while문 처음으로 이동
						//(이미지가 없을 시 네이버 기본 이미지가 들어가 있음)
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
							//영상 개수
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
					productDAO.updateSearchTime(smallProductList.get(i).getSmallProductId());
					long end = System.currentTimeMillis();  //종료시간
					//종료-시작=실행시간		
					if((end-start)/1000.0 > 3*60*60){ //3시간을 초과하면 실행 중지
						break label;
					}
				} //for
				System.out.println("*****************************************");
				System.out.println("총 소제품 개수 : "+countOfSmallProduct);
				System.out.println("총 포스팅 개수 : "+countOfAllPosting);
				flag = false;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}