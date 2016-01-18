package kr.co.blli.model.posting;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.annotation.Resource;

import kr.co.blli.model.product.ProductDAO;
import kr.co.blli.model.vo.BlliPostingVO;

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
		String key = "6694c8294c8d04cdfe78262583a13052"; //네이버 검색API 이용하기 위해 발급받은 key값
		ArrayList<String> smallProductList = (ArrayList<String>)productDAO.getSmallProduct(); //소제품 리스트를 불러와 변수에 할당
		String postingURL = ""; //포스팅 주소
		String smallProduct = ""; //검색할 소제품
		String frameSourceURL = ""; //프레임 소스 주소
		String postingTitle = ""; //포스팅 제목
		String postingContent = ""; //포스팅 본문
		int postingMediaCount = 0; //포스팅에 등록된 이미지 개수
		String postingPhotoLink = ""; //대표 사진 링크
		String postingAuthor = ""; //포스팅 작성자 닉네임
		int maxPosting = 100; //검색할 포스팅 최대 개수
		ArrayList<String> regex = new BlliPostingVO().regex; //html에서 제거되야 할 태그와 특수문자들 리스트
		
		for(int i=0;i<smallProductList.size();i++){ //소제품들 한개씩 뽑아서 포스팅 검색
			int countOfPosting = 1; //수집한 포스팅 개수
			int page = 1; //검색 페이지
			smallProduct = smallProductList.get(i);
			//소제품 관련 총 포스팅 개수
			int totalPosting = Integer.parseInt(Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+smallProduct+"&display=1&start="+page+"&target=blog&sort=sim").get().select("total").text());
			
			if(totalPosting/5 < maxPosting){ //총 포스팅 개수의 20%가 100개 미만일 경우 포스팅 최대 개수를 총 포스팅의 20%로 설정
				maxPosting = totalPosting/5;
			}
			
			while(countOfPosting <= maxPosting){ //수집한 포스팅 개수가 포스팅 최대 개수와 같아질 때까지 포스팅 수집 
				//display : 검색 결과 갯수, start : 페이지 번호, target : 검색 대상(블로그), sort : 나열 기준(sim : 유사한 순대로 정렬)
				Document doc = Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+smallProduct+"&display=1&start="+page+"&target=blog&sort=sim").get();
				doc = Jsoup.connect(doc.select("item link").html()).get(); //openAPI를 통해 얻은 포스팅URL 연결
				frameSourceURL = "http://blog.naver.com" + doc.select("#mainFrame").attr("src"); //frameSourceURL 불러와 설정
				
				if(!frameSourceURL.equals("http://blog.naver.com")){ //네이버 블로그 포스팅일 경우 -> page와 포스팅 개수를 한개씩 늘려줌
					doc = Jsoup.parse(new URL(frameSourceURL).openStream(),"ms949",frameSourceURL);
					page++;
					countOfPosting++;
				}else{ //네이버 블로그 포스팅이 아닐 경우 -> page만 한개 늘려주고 while문 처음으로 이동(네이버 블로그가 아닐 경우 DB에 저장X) 
					page++;
					continue;
				}
				
				BlliPostingVO postingVO = new BlliPostingVO();
				postingMediaCount = 0; //이미지 갯수 초기화
				postingVO.setSmallProduct(smallProduct); //소제품을 vo에 저장
				
				Elements postingDate = doc.select(".date");
				if(postingDate.size() == 0){ //스마트에디터일 경우
					postingVO.setPostingDate(doc.select(".se_publishDate").text().substring(0, doc.select(".se_publishDate").text().lastIndexOf(".")));
				}else{ //스마트에디터가 아닐 경우
					for(Element e : postingDate){
						if(e.attr("class").equals("date fil5 pcol2 _postAddDate")){
							postingVO.setPostingDate(e.text().substring(0, e.text().lastIndexOf("."))); //포스팅 작성일을 vo에 저장
						}
					}
				}
				
				Elements metaInfo = doc.select("meta");
				for(Element e : metaInfo){
					String property = e.attr("property"); //meta정보
					if(property.contains("url")){
						postingURL = e.attr("content");
						postingVO.setPostingUrl(postingURL); //postingURL을 vo에 저장
					}else if(property.contains("title")){
						postingTitle = e.attr("content");
						postingVO.setPostingTitle(postingTitle); //postingTitle을 vo에 저장
					}else if(property.contains("image")){
						postingPhotoLink = e.attr("content");
						postingVO.setPostingPhotoLink(postingPhotoLink); //postingPhotoLink를 vo에 저장
					}else if(property.contains("author")){
						postingAuthor = e.attr("content");
						postingVO.setPostingAuthor(postingAuthor.substring(postingAuthor.lastIndexOf("|")+2)); //포스팅 작성자를 vo에 저장
					}
				}
				//본문에 등록된 이미지가 없을 시 포스팅 개수 한개 줄이고 while문 처음으로 이동(이미지가 없을 시 네이버 기본 이미지가 들어가 있음)
				if(postingPhotoLink.equals("http://blogimgs.naver.net/nblog/mylog/post/og_default_image2.png")){
					countOfPosting--;
					continue;
				}
				
				Elements imgInfo = doc.select("#postViewArea img");
				if(imgInfo.size() != 0){ //스마트에디터가 아닐 경우
					//이미지 개수
					for(Element e : imgInfo){
						String src = e.attr("src");
						if(src.contains("postfiles") || src.contains("blogfiles")){
							postingMediaCount++;
						}
					}
					//영상 갯수
					Elements playerInfo = doc.select("#postViewArea iframe");
					for(Element e : playerInfo){
						if(e.attr("name").equals("mplayer")){
							postingMediaCount++;
						}
					}
				}else{ //스마트에디터일 경우
					//이미지 개수
					imgInfo = doc.select("#printPost1 tbody a");
					for(Element e : imgInfo){
						String imgType = e.attr("data-linktype");
						if(imgType.equals("img")){
							postingMediaCount++;
						}
					}
					//영상 개수
					Elements playerInfo = doc.select("#printPost1 tbody div");
					for(Element e : playerInfo){
						if(e.attr("class").equals("se_mediaArea")){
							postingMediaCount++;
						}
					}
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
					for(Element e : contentTag){
						if(e.attr("class").equals("se_textarea")){ //본문 내용 누적
							postingContent += e.html();
						}
					}
					for(int j=0;j<regex.size();j++){ //태그 및 특수문자 제거
						postingContent = postingContent.replaceAll(regex.get(j), "");
					}
					postingContent = postingContent.replaceAll("( )+", " ").trim(); //공란을 한칸으로 변경 및 앞 뒤 공란 제거
				}
				postingVO.setPostingContent(postingContent); //본문을 vo에 저장
				
				if(postingContent.length() > 200){ //본문 길이 검사 후 본문 요약 vo에 저장
					postingVO.setPostingSummary(postingContent.substring(0,180) + "...");
				}else{
					postingVO.setPostingSummary(postingContent);
				}
				//포스팅 update
				int updateResult = postingDAO.updatePosting(postingVO);
				//포스팅 insert
				if(updateResult == 0){
					postingDAO.insertPosting(postingVO);
				}
			} //while
		} //for
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
}
