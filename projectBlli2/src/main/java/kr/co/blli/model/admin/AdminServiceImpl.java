package kr.co.blli.model.admin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import kr.co.blli.model.vo.BlliDetailException;
import kr.co.blli.model.vo.BlliLogVO;
import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliPagingBean;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;
import kr.co.blli.model.vo.BlliWordCloudVO;
import kr.co.blli.model.vo.ListVO;
import kr.co.blli.utility.BlliFileDownLoader;
import kr.co.blli.utility.BlliWordCounter;

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
	@Resource
	private BlliFileDownLoader blliFileDownLoader;
	@Resource
	private BlliWordCounter blliWordCounter;
	
	
	
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
		lvo.setList(smallProductList);
		lvo.setPagingBean(paging);
		return lvo;
	}
	
	/**
	 * @Method Name : selectProduct
	 * @Method 설명 : 두개 이상의 소제품을 가지고 있는 포스팅을 한개의 소제품으로 변경해주는 메서드
	 * @작성일 : 2016. 1. 19.
	 * @param urlAndProduct
	 */
	@Override
	public void selectProduct(List<Map<String, Object>> urlAndImage) {
		for(int i=0;i<urlAndImage.size();i++){
			String delete = urlAndImage.get(i).get("del").toString();
			String postingUrl = urlAndImage.get(i).get("postingUrl").toString();
			String postingPhotoLink = urlAndImage.get(i).get("postingPhotoLink").toString();
			String smallProduct = urlAndImage.get(i).get("smallProduct").toString();
			BlliPostingVO vo = new BlliPostingVO();
			vo.setPostingUrl(postingUrl);
			vo.setPostingPhotoLink(postingPhotoLink);
			vo.setSmallProduct(smallProduct);
			if(delete.equals("YES")){
				adminDAO.deletePosting(vo);
			}else{
				adminDAO.selectProduct(vo);
			}
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
	public void registerPosting(List<Map<String, Object>> urlAndImage) {
		ArrayList<BlliPostingVO> blliPostingVOList = new ArrayList<BlliPostingVO>();
		for(int i=0;i<urlAndImage.size();i++){
			String delete = urlAndImage.get(i).get("del").toString();
			String postingUrl = urlAndImage.get(i).get("postingUrl").toString();
			String postingPhotoLink = urlAndImage.get(i).get("postingPhotoLink").toString();
			String smallProductId = urlAndImage.get(i).get("smallProductId").toString();
			BlliPostingVO vo = new BlliPostingVO();
			//이미지 파일 다운로드
			vo.setPostingUrl(postingUrl);
			vo.setSmallProductId(smallProductId);
			if(delete.equals("YES")){
				adminDAO.deletePosting(vo);
			}else{
				vo.setPostingPhotoLink(blliFileDownLoader.imgFileDownLoader(
						postingPhotoLink,UUID.randomUUID().toString().replace("-", ""), "postingImage"));
				blliPostingVOList.add(vo);
				int updateResult = adminDAO.registerPosting(vo);
				if(updateResult != 0){
					adminDAO.updatePostingCount(vo);
				}
			}
		}
		insertAndUpdateWordCloud(blliPostingVOList);
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
			vo.setMidCategory(adminDAO.getMidCategory(smallProductInfo.get(i).get("smallProductId").toString()));
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
					adminDAO.updateMidCategoryWhenToUse(vo);
					if(vo.getSmallProduct() == null || vo.getSmallProduct() == ""){
						adminDAO.registerSmallProduct(vo);
					}else{
						adminDAO.registerAndUpdateSmallProduct(vo);
					}
				}
			}
		}
	}
	@Override
	public void makingWordCloud(BlliPostingVO blliPostingVO) {
		String smallProductId = blliPostingVO.getSmallProductId();
		List<BlliPostingVO> list = adminDAO.makingWordCloud(smallProductId);
		StringBuffer sb = new StringBuffer();
		System.out.println("총 조사 포스팅 수 : "+list.size());
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i).getPostingContent());
		}
		HashMap<String, Integer> wordCounting = blliWordCounter.wordCounting(sb);
		Iterator<String> it = wordCounting.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			int value = wordCounting.get(key);
			BlliWordCloudVO blliWordCloudVO = new BlliWordCloudVO();
			blliWordCloudVO.setSmallProductId(smallProductId);
			blliWordCloudVO.setWord(key);
			blliWordCloudVO.setWordCount(value);
			if(adminDAO.updateWordCloud(blliWordCloudVO)!=0){
				adminDAO.insertWordCloud(blliWordCloudVO);
			}
		}
	}
	@Override
	public void insertAndUpdateWordCloud(ArrayList<BlliPostingVO> blliPostingVOList) {
		if(blliPostingVOList.size()>0){
			String smallProductId = blliPostingVOList.get(0).getSmallProductId();
			StringBuffer sb = new StringBuffer();
			System.out.println("총 조사 포스팅 수 : "+blliPostingVOList.size());
			for (int i = 0; i < blliPostingVOList.size(); i++) {
				blliPostingVOList.get(i).setPostingContent(adminDAO.selectPostingContentByPostingUrl(blliPostingVOList.get(i).getPostingUrl()));
				sb.append(blliPostingVOList.get(i).getPostingContent());
			}
			HashMap<String, Integer> wordCounting = blliWordCounter.wordCounting(sb);
			Iterator<String> it = wordCounting.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				int value = wordCounting.get(key);  
				BlliWordCloudVO blliWordCloudVO = new BlliWordCloudVO();
				blliWordCloudVO.setSmallProductId(smallProductId);
				blliWordCloudVO.setWord(key);
				blliWordCloudVO.setWordCount(value);
				System.out.println();
				if(adminDAO.updateWordCloud(blliWordCloudVO)==0){
					System.out.println("여기와야해");
					adminDAO.insertWordCloud(blliWordCloudVO);
				}
			}
		}
	}

	@Override
	public ArrayList<BlliLogVO> checkLog() {
		ArrayList<BlliLogVO> list = new ArrayList<BlliLogVO>();
		BlliLogVO vo = null;
		ArrayList<BlliDetailException> detailException = new ArrayList<BlliDetailException>();
		BlliDetailException exceptionVO = null;
		int number = 1;
		try {
			BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\용호\\git\\projectBlli2\\projectBlli2\\src\\main\\webapp\\logFile\\blliLog.log"));
			String message;
			String exceptionContent = "";
			while ((message = in.readLine()) != null) {
				if(message.startsWith("start")){
					vo = new BlliLogVO();
					vo.setNumber(number++);
					vo.setMethodName(message.substring(message.lastIndexOf(":")+2));
				}else if(message.startsWith("발생 일자")){
					vo.setStartTime(message.substring(message.indexOf(":")+2));
				}else if(message.startsWith("실행 시간")){
					if(!exceptionContent.equals("")){
						exceptionVO.setExceptionContent(exceptionContent);
						detailException.add(exceptionVO);
					}
					vo.setRunTime(message.substring(message.lastIndexOf(":")+2));
				}else if(message.startsWith("요청자")){
					vo.setExecutor(message.substring(message.lastIndexOf(":")+2));
				}else if(message.startsWith("총 대분류 개수")){
					if(vo.getMethodName().equals("insertBigCategory")){
						vo.setCategoryCount(message.substring(message.lastIndexOf(":")+2));
					}else{
						vo.setHighRankCategoryCount(message.substring(message.lastIndexOf(":")+2));
					}
				}else if(message.startsWith("총 중분류 개수")){
					if(vo.getMethodName().equals("insertMidCategory")){
						vo.setCategoryCount(message.substring(message.lastIndexOf(":")+2));
					}else{
						vo.setHighRankCategoryCount(message.substring(message.lastIndexOf(":")+2));
					}
				}else if(message.startsWith("총 소제품 개수")){
					if(vo.getMethodName().equals("insertPosting")){
						vo.setHighRankCategoryCount(message.substring(message.lastIndexOf(":")+2));
					}else{
						vo.setCategoryCount(message.substring(message.lastIndexOf(":")+2));
					}
				}else if(message.startsWith("총 포스팅 개수")){
					vo.setCategoryCount(message.substring(message.lastIndexOf(":")+2));
				}else if(message.startsWith("insert")){
					if(message.startsWith("insert한 조건에 맞지 않는 소제품 개수")){
						vo.setDenySmallProductCount(message.substring(message.lastIndexOf(":")+2));
					}else if(message.startsWith("insert하지 않은 조건에 맞지 않는 포스팅 개수")){
						vo.setDenyPostingCount(message.substring(message.lastIndexOf(":")+2));
					}else{
						vo.setInsertCategoryCount(message.substring(message.lastIndexOf(":")+2));
					}
				}else if(message.startsWith("update")){
					if(message.startsWith("update하지 않은 소제품 개수")){
						vo.setNotUpdateProductCount(message.substring(message.lastIndexOf(":")+2));
					}else if(message.startsWith("update하지 않은 포스팅 개수")){
						vo.setNotUpdatePostingCount(message.substring(message.lastIndexOf(":")+2));
					}else{
						vo.setUpdateCategoryCount(message.substring(message.lastIndexOf(":")+2));
					}
				}else if(message.startsWith("시간지연")){
					vo.setDelayConnectionCount(message.substring(message.lastIndexOf(":")+2));
				}else if(message.startsWith("Exception 발생 횟수")){
					vo.setExceptionCount(message.substring(message.lastIndexOf(":")+2));
				}else if(message.startsWith("Exception이 발생한")){
					if(!exceptionContent.equals("")){
						exceptionVO.setExceptionContent(exceptionContent);
						detailException.add(exceptionVO);
					}
					exceptionVO = new BlliDetailException();
					exceptionVO.setCategoryId(message.substring(message.lastIndexOf(":")+2));
				}else if(message.startsWith("Exception 내용")){
					if(message.length() != 15){
						exceptionVO.setExceptionContent(message.substring(message.indexOf(":")+1));
						detailException.add(exceptionVO);
					}else{
						exceptionContent = "";
					}
				}else if(message.startsWith("###")){
					exceptionContent += message.replaceAll("\"", "'")+"<br>";
				}else if(message.startsWith("end")){
					vo.setDetailException(detailException);
					list.add(vo);
					detailException = new ArrayList<BlliDetailException>();
					if(number > 20){
						break;
					}
				}
			}
			in.close();
		} catch (IOException e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
			System.exit(1);
		}
		return list;
	}
	@Override
	public ArrayList<BlliPostingVO> checkPosting() {
		return (ArrayList<BlliPostingVO>)adminDAO.checkPosting();
	}
	@Override
	public void deletePosting(BlliPostingVO postingVO) {
		adminDAO.deletePosting(postingVO);
	}
	@Override
	public void notAdvertisingPosting(BlliPostingVO postingVO) {
		adminDAO.notAdvertisingPosting(postingVO);
	}
	@Override
	public ArrayList<BlliMemberVO> checkMember() {
		return (ArrayList<BlliMemberVO>)adminDAO.checkMember();
	}
}
