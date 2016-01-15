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
	@Override
	public String jsoupTest() throws IOException {
		String key = "6694c8294c8d04cdfe78262583a13052"; //네이버 검색API 이용하기 위해 발급받은 key값
		ArrayList<String> smallProductList = (ArrayList<String>)productDAO.getSmallProduct(); //검색어(소제품)
		String query = "";
		String postingURL = ""; //포스팅주소
		String frameSourceURL = ""; //프레임소스주소
		String postingTitle = ""; //포스팅 제목
		String postingContent = ""; //포스팅 본문
		int countOfMedia = 0; //포스팅에 등록된 이미지 갯수
		String linkOfMainImage = ""; //대표 사진 링크
		int maxPosting = 100;
		ArrayList<String> regex = new BlliPostingVO().regex;
		
		for(int i=0;i<smallProductList.size();i++){
			int countOfPosting = 1;
			int page = 1;
			query = smallProductList.get(i);
			int totalPosting = Integer.parseInt(Jsoup.connect("http://openapi.naver.com/search?key="+key+"&query="+query+"&display=1&start="+page+"&target=blog&sort=sim").get().select("total").text());
			
			if(totalPosting/5 < maxPosting){
				maxPosting = totalPosting/5;
			}
			
			while(countOfPosting <= maxPosting){
				//display : 검색 결과 갯수, start : 페이지 번호, target : 검색 대상, sort : 나열 기준(sim : 유사한 순대로 정렬)
				Document doc = Jsoup.connect("http://openapi.naver.com/search?key=" + key + "&query=" + query + "&display=1&start=" + page + "&target=blog&sort=sim").get();
				Elements postingURLByOpenAPI = doc.select("item link"); //openAPI를 통해 얻은 포스팅URL
				for(Element e : postingURLByOpenAPI){
					//이미지 갯수 초기화
					countOfMedia = 0;
					doc = Jsoup.connect(e.html()).get();
					frameSourceURL = "http://blog.naver.com" + doc.select("#mainFrame").attr("src");
					
					if(!frameSourceURL.equals("http://blog.naver.com")){ //네이버 블로그 포스팅일 경우
						doc = Jsoup.parse(new URL(frameSourceURL).openStream(),"ms949",frameSourceURL);
						page++;
						countOfPosting++;
					}else{ //네이버 블로그 포스팅이 아닐 경우
						page++;
						continue;
					}
					
					Elements metaInfo = doc.select("meta");
					BlliPostingVO postingVO = new BlliPostingVO();
					postingVO.setSmallProduct(query);
					for(Element el : metaInfo){
						String property = el.attr("property"); //meta정보
						if(property.contains("url")){
							postingURL = el.attr("content");
							postingVO.setPostingUrl(postingURL);
						}else if(property.contains("title")){
							postingTitle = el.attr("content");
							postingVO.setPostingTitle(postingTitle);
						}else if(property.contains("image")){
							linkOfMainImage = el.attr("content");
							postingVO.setPostingPhotoLink(linkOfMainImage);
						}
					}
					
					Elements imgInfo = doc.select("#postViewArea img");
					if(imgInfo.size() != 0){ //스마트에디터가 아닐 경우
						//이미지 갯수
						for(Element ele : imgInfo){
							String src = ele.attr("src");
							if(src.contains("postfiles") || src.contains("blogfiles")){
								countOfMedia++;
							}
						}
						//영상 갯수
						Elements playerInfo = doc.select("#postViewArea iframe");
						for(Element elem : playerInfo){
							if(elem.attr("name").equals("mplayer")){
								countOfMedia++;
							}
						}
					}else{ //스마트에디터일 경우
						//이미지 갯수
						imgInfo = doc.select("#printPost1 tbody a");
						for(Element ele : imgInfo){
							String imgType = ele.attr("data-linktype");
							if(imgType.equals("img")){
								countOfMedia++;
							}
						}
						//영상 갯수
						Elements playerInfo = doc.select("#printPost1 tbody div");
						for(Element elem : playerInfo){
							if(elem.attr("class").equals("se_mediaArea")){
								countOfMedia++;
							}
						}
					}
					postingVO.setPostingMediaCount(countOfMedia);
					
					if(doc.select("#postViewArea").size() != 0){ //스마트에디터가 아닐 경우
						postingContent = doc.select("#postViewArea").html();
						for(int j=0;j<regex.size();j++){
							postingContent = postingContent.replaceAll(regex.get(j), "");
						}
						postingContent = postingContent.replaceAll("( )+", " ").trim();
					}else{ //스마트에디터일 경우
						postingContent = "";
						Elements contentTag = doc.select("#printPost1 tbody p");
						for(Element eleme : contentTag){
							if(eleme.attr("class").equals("se_textarea")){
								postingContent += eleme.html();
							}
						}
						for(int j=0;j<regex.size();j++){
							postingContent = postingContent.replaceAll(regex.get(j), "");
						}
						postingContent = postingContent.replaceAll("( )+", " ").trim();
					}
					postingVO.setPostingContent(postingContent);
					if(postingContent.length() > 200){
						postingVO.setPostingSummary(postingContent.substring(0,180) + "...");
					}else{
						postingVO.setPostingSummary(postingContent);
					}
					int updateResult = postingDAO.updatePosting(postingVO);
					if(updateResult == 0){
						postingDAO.insertPosting(postingVO);
					}
				} //for
			} //while
		} //for
		return "등록 완료!";
	}
	
	@Override
	public ArrayList<BlliPostingVO> searchJsoupTest(String searchWord) {
		return (ArrayList<BlliPostingVO>)postingDAO.searchJsoupTest(searchWord);
	}
}
