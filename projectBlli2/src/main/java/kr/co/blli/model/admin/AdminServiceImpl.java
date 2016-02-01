package kr.co.blli.model.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliPagingBean;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;
import kr.co.blli.model.vo.ListVO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

@Service
public class AdminServiceImpl implements AdminService{
	@Resource
	private AdminDAO adminDAO;
	
	@Resource
	private JavaMailSender mailSender;
	@Resource
	private VelocityConfig velocityConfig;
	
	
	/**
	  * @Method Name : sendMail
	  * @Method 설명 : 스케줄러에 의해  주기적으로 실행되는 메일 발송 메소드. 실행 시기 : 회원가입시, ...? 
	  * @작성일 : 2016. 1. 18.
	  * @작성자 : yongho
	  * @param recipient
	  * @param subject
	  * @param text
	  * @param textParams
	  * @param formUrl
	  * @throws FileNotFoundException
	  * @throws URISyntaxException
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	  */
	@Override
	public void sendMail(String memberId, String mailForm) throws FileNotFoundException, URISyntaxException, UnsupportedEncodingException, MessagingException {
		
		BlliMailVO mlvo = adminDAO.findMailSubjectAndContentByMailForm(mailForm);
		BlliMemberVO mbvo = adminDAO.findMemberInfoById(memberId);
		
		String recipient = mbvo.getMemberEmail();
		String subject = mlvo.getMailSubject();
		String contentFile = mlvo.getMailContentFile();
		
		Map<String, Object> textParams = new HashMap<String, Object>();
		
		if(mailForm.equals("findPassword")) {
			textParams.put("memberPassword", mbvo.getMemberPassword());
			textParams.put("memberName", mbvo.getMemberName());
		}
		
		MimeMessage message = mailSender.createMimeMessage();
		
		String mailText = null;
		if(textParams != null) {
			mailText = VelocityEngineUtils.mergeTemplateIntoString(velocityConfig.getVelocityEngine(), contentFile, "utf-8", textParams);
		}
		
		message.setFrom(new InternetAddress("admin@blli.co.kr","블리", "utf-8"));
		message.addRecipient(RecipientType.TO, new InternetAddress(recipient)); //import javax.mail.Message.RecipientType;
		message.setSubject(subject);
		message.setText(mailText, "utf-8", "html");
		
		mailSender.send(message);
		
	}
	/**
	 * 
	 * @Method Name : unconfirmedPosting
	 * @Method 설명 : 확정안된 포스팅의 리스트를 pagingBean과 함께 반환해주는 메서드 
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @return
	 * @throws IOException
	 */
	public ListVO unconfirmedPosting(String pageNo) throws IOException {
		if(pageNo == null || pageNo == ""){
			pageNo = "1";
		}
		ArrayList<BlliPostingVO> postingList = (ArrayList<BlliPostingVO>)adminDAO.unconfirmedPosting(pageNo);
		for(int i=0;i<postingList.size();i++){
			String url = postingList.get(i).getPostingUrl();
			String smallProduct = postingList.get(i).getSmallProduct();
			Document doc = Jsoup.connect("http://shopping.naver.com/search/all_search.nhn?query="+smallProduct+
					"&pagingIndex=1&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&frm=NVSHMDL").get();
			Elements imgTag = doc.select("img");
			HashMap<String, String> smallProductImage = new HashMap<String, String>();
			for(Element e : imgTag){
				if(e.attr("alt").equals(smallProduct)){
					smallProductImage.put(smallProduct, e.attr("data-original"));
					postingList.get(i).setSmallProductImage(smallProductImage);
					break;
				}
			}
			ArrayList<String> imgList = new ArrayList<String>(); 
			doc = Jsoup.connect(url).get();
			String frameSourceUrl = "http://blog.naver.com" + doc.select("#mainFrame").attr("src");
			doc = Jsoup.connect(frameSourceUrl).get();
			Elements imgTagList = doc.select("#postViewArea img");
			if(imgTagList.size() != 0){ //스마트에디터가 아닐 경우
				for(Element e : imgTagList){
					String imgSrc = e.attr("src");
					if(imgSrc.contains("postfiles") || imgSrc.contains("blogfiles")){
						imgList.add(imgSrc);
					}
				}
			}else{ //스마트에디터일 경우
				imgTagList = doc.select(".se_mediaImage");
				for(Element e : imgTagList){
					imgList.add(e.attr("src"));
				}
			}
			postingList.get(i).setImageList(imgList); //포스팅에 등록된 모든 이미지 vo에 저장
		}
		int total = adminDAO.totalUnconfirmedPosting();
		BlliPagingBean paging = new BlliPagingBean(total, Integer.parseInt(pageNo));
		ListVO lvo = new ListVO(postingList, paging);
		return lvo;
	}
	
	/**
	 * 
	 * @Method Name : postingListWithSmallProducts
	 * @Method 설명 : 두개 이상의 소제품과 관련된 포스팅의 리스트와 pagingBean을 반환해주는 메서드 
	 * @작성일 : 2016. 1. 19.
	 * @return
	 * @throws IOException
	 */
	@Override
	public ListVO postingListWithSmallProducts(String pageNo) throws IOException {
		if(pageNo == null || pageNo == ""){
			pageNo = "1";
		}
		// 두개 이상의 소제품을 가지고 있는 포스팅 할당
		ArrayList<BlliPostingVO> postingList = (ArrayList<BlliPostingVO>)adminDAO.postingListWithSmallProducts(pageNo); 
		String url = "";
		String imgSource = "";
		HashMap<String, String> smallProductImageList = new HashMap<String, String>();
		for(int i=0;i<postingList.size();i++){
			ArrayList<String> smallProductList = new ArrayList<String>();
			//이전 postingUrl과 현재 postingUrl이 같을 경우 해당 포스팅VO를 지우고 인덱스를 -1 해줌
			if(postingList.get(i).getPostingUrl().equals(url)){ 
				postingList.remove(i);
				i--;
				continue;
			}else{ //이전 postingUrl과 현재 postingUrl이 다를 경우 현재 postingUrl에 해당하는 소제품 목록과 대표 이미지 vo에 저장
				url = postingList.get(i).getPostingUrl();
				smallProductList.add(postingList.get(i).getSmallProduct());
				Document doc = Jsoup.connect("http://shopping.naver.com/search/all_search.nhn?query="+postingList.get(i).getSmallProduct()+
						"&pagingIndex=1&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&frm=NVSHMDL").get();
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
							doc = Jsoup.connect("http://shopping.naver.com/search/all_search.nhn?query="+postingList.get(j).getSmallProduct()+
									"&pagingIndex=1&pagingSize=40&productSet=model&viewType=list&sort=rel&searchBy=none&frm=NVSHMDL").get();
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
					if(imgSrc.contains("postfiles") || imgSrc.contains("blogfiles")){
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
		int total = adminDAO.totalPostingWithProducts();
		BlliPagingBean paging = new BlliPagingBean(total, Integer.parseInt(pageNo));
		ListVO lvo = new ListVO(postingList, paging);
		return lvo;
	}
	/**
	 * 
	 * @Method Name : unconfirmedSmallProduct
	 * @Method 설명 : 확정안된 소제품 리스트와 pagingBean을 반환해주는 메서드 
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @return
	 */
	@Override
	public ListVO unconfirmedSmallProduct(String pageNo) {
		if(pageNo == null || pageNo == ""){
			pageNo = "1";
		}
		ArrayList<BlliSmallProductVO> smallProductList = (ArrayList<BlliSmallProductVO>)adminDAO.unconfirmedSmallProduct(pageNo);
		int total = adminDAO.totalUnconfirmedSmallProduct();
		BlliPagingBean paging = new BlliPagingBean(total, Integer.parseInt(pageNo));
		paging.setNumberOfPostingPerPage(10);
		ListVO lvo = new ListVO();
		lvo.setSmallProductList(smallProductList);
		lvo.setPagingBean(paging);
		return lvo;
	}
	
	/**
	 * @Method Name : selectProduct
	 * @Method 설명 : 두개 이상의 소제품을 가지고 있는 포스팅을 한개 또는 두개 이상의 소제품으로 변경해주는 메서드
	 * @작성일 : 2016. 1. 19.
	 * @param urlAndProduct
	 */
	@Override
	public void selectProduct(List<Map<String, Object>> urlAndProduct) {
		String prePostingUrl = "";
		String preSmallProduct = "";
		for(int i=0;i<urlAndProduct.size();i++){
			String selectProduct = urlAndProduct.get(i).get("smallProduct").toString();
			if(selectProduct.equals("")){ //선택하지 않은 포스팅에 대해서는 기능 적용X
				continue;
			}
			String postingUrl = urlAndProduct.get(i).get("postingUrl").toString();
			HashMap<String, String> map = new HashMap<String, String>();
			if(selectProduct.equals("삭제")){
				adminDAO.deletePosting(postingUrl);
			}else{
				map.put("postingUrl", postingUrl);
				if(prePostingUrl.equals(postingUrl)){
					map.put("smallProduct", preSmallProduct+" / "+selectProduct);
					map.put("preSmallProduct", preSmallProduct);
					adminDAO.addProduct(map);
				}else{
					map.put("smallProduct", selectProduct);
					adminDAO.selectProduct(map);
					adminDAO.deleteProduct(postingUrl);
				}
			}
			prePostingUrl = postingUrl;
			preSmallProduct = selectProduct;
		}
	}
	/**
	 * 
	 * @Method Name : registerPosting
	 * @Method 설명 : 확정안된 포스팅을 확정해주는 메서드(사용자가 볼 수 있게 전환) 
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param urlAndProduct
	 */
	@Override
	public void registerPosting(List<Map<String, Object>> urlAndProduct) {
		for(int i=0;i<urlAndProduct.size();i++){
			String smallProduct = urlAndProduct.get(i).get("smallProduct").toString();
			if(smallProduct.equals("")){ //선택하지 않은 포스팅에 대해서는 기능 적용X
				continue;
			}
			String postingUrl = urlAndProduct.get(i).get("postingUrl").toString();
			HashMap<String, String> map = new HashMap<String, String>();
			if(smallProduct.equals("삭제")){
				adminDAO.deletePosting(postingUrl);
			}else{
				map.put("smallProduct", smallProduct);
				map.put("postingUrl", postingUrl);
				adminDAO.registerPosting(map);
			}
		}
	}
	/**
	 * 
	 * @Method Name : registerSmallProduct
	 * @Method 설명 : 확정안된 소제품을 확정해주는 메서드(포스팅 검색 대상으로 전환)
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param smallProductInfo
	 */
	@Override
	public void registerSmallProduct(List<Map<String, Object>> smallProductInfo) {
		for(int i=0;i<smallProductInfo.size();i++){
			String delete = smallProductInfo.get(i).get("delete").toString();
			BlliSmallProductVO vo = new BlliSmallProductVO();
			vo.setSmallProductId(smallProductInfo.get(i).get("smallProductId").toString());
			vo.setSmallProduct(smallProductInfo.get(i).get("smallProduct").toString());
			if(delete.equals("삭제")){
				adminDAO.deleteSmallProduct(vo.getSmallProductId());
			}else{
				String smallProductWhenToUseMin = smallProductInfo.get(i).get("smallProductWhenToUseMin").toString();
				String smallProductWhenToUseMax = smallProductInfo.get(i).get("smallProductWhenToUseMax").toString();
				if((smallProductWhenToUseMin == null || smallProductWhenToUseMin == "") && 
						(smallProductWhenToUseMax == null || smallProductWhenToUseMax == "")){
					adminDAO.updateSmallProductName(vo);
				}else{
					if(smallProductWhenToUseMin == null || smallProductWhenToUseMin == ""){
						smallProductWhenToUseMin = "0";
					}
					if(smallProductWhenToUseMax == null || smallProductWhenToUseMax == ""){
						smallProductWhenToUseMax = "100";
					}
					vo.setSmallProductWhenToUseMin(Integer.parseInt(smallProductWhenToUseMin));
					vo.setSmallProductWhenToUseMax(Integer.parseInt(smallProductWhenToUseMax));
					if(vo.getSmallProduct() == null || vo.getSmallProduct() == ""){
						adminDAO.registerSmallProduct(vo);
					}else{
						adminDAO.registerAndUpdateSmallProduct(vo);
					}
				}
			}
		}
	}
}
