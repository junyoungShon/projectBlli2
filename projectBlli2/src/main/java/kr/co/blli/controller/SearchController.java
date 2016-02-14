package kr.co.blli.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.blli.model.posting.PostingService;
import kr.co.blli.model.product.ProductService;
import kr.co.blli.model.scheduler.CategoryAndProductScheduler;
import kr.co.blli.model.scheduler.PostingMarker;
import kr.co.blli.model.scheduler.PostingScheduler;
import kr.co.blli.model.vo.BlliBuyLinkClickVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;
import kr.co.blli.model.vo.BlliWordCloudVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
	
	@Resource
	private PostingService postingService;
	
	@Resource
	private ProductService productService;
	
	//스케줄러 완성 전까지 임시 사용
	@Resource
	private CategoryAndProductScheduler categoryAndProductScheduler;
	//스케줄러 완성 전까지 임시 사용
	@Resource
	private PostingScheduler postingScheduler;
	//포스팅 채점기 완성전까지 임시사용
	@Resource
	private PostingMarker postingMarker;
	
	//스케줄러 완성 전까지 임시 사용
	@RequestMapping("insertPosting.do")
	public ModelAndView insertPosting() throws IOException{
		postingScheduler.insertPosting();
		return new ModelAndView("insertDataResult");
	}
	/**
	 * 
	 * @Method Name : searchSmallProduct
	 * @Method 설명 : 검색어에 해당하는 페이지를 출력해주는 메서드
	 * @작성일 : 2016. 2. 3.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @param searchWord
	 * @return
	 */
	@RequestMapping("searchSmallProduct.do")
	public ModelAndView searchSmallProduct(String pageNo, String searchWord,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		ArrayList<BlliSmallProductVO> smallProductList = productService.searchMidCategory(pageNo, searchWord);
		String viewName = "";
		if(smallProductList.isEmpty()){
			HashMap<String, Object> smallProductInfo = productService.searchSmallProduct(searchWord);
			if(smallProductInfo.get("smallProduct") != null){
				ArrayList<BlliPostingVO> postingList = 
						postingService.searchPostingListInProductDetail(((BlliSmallProductVO)smallProductInfo.get("smallProduct")).getSmallProductId(),request,"1");
				viewName = "smallProductDetailPage";
				mav.addObject("smallProductInfo", smallProductInfo);
				mav.addObject("blliPostingVOList", postingList);
				System.out.println("스몰프로덕트 :" +postingList);
			}else{
				smallProductList = productService.searchSmallProductList(pageNo, searchWord);
				viewName = "midCategoryDetailPage";
				mav.addObject("resultList", smallProductList);
				mav.addObject("totalPage", productService.totalPageOfSmallProductRelatedSearchWord(searchWord));
				mav.addObject("searchWord", searchWord);
			}
			//ArrayList<BlliPostingVO> postingList = postingService.searchPosting(pageNo, searchWord);
		}else{
			viewName = "midCategoryDetailPage";
			for(int i = 0;i<smallProductList.size();i++){
				List<BlliWordCloudVO> list = productService.selectWordCloudList(smallProductList.get(i).getSmallProductId());
				smallProductList.get(i).setBlliWordCloudVOList(list);
			}
			mav.addObject("resultList", smallProductList);
			mav.addObject("totalPage", productService.totalPageOfSmallProductOfMidCategory(searchWord));
			mav.addObject("searchWord", searchWord);
			
			//소제품 찜 여부 체크
			HttpSession session =  request.getSession();
			if(session!=null){
				BlliMemberVO blliMemberVO = (BlliMemberVO) session.getAttribute("blliMemberVO");
				for(int i=0;i<smallProductList.size();i++){
					BlliSmallProductVO blliSmallProductVO = productService.productDibChecker(blliMemberVO.getMemberId(),smallProductList.get(i));
					smallProductList.get(i).setIsDib(blliSmallProductVO.getIsDib());
				}
			}
			
			
		}
		
		mav.setViewName(viewName);
		//mav.addObject("searchWord", searchWord);
		//mav.addObject("totalPage", postingService.totalPageOfPosting(searchWord));
		return mav;
	}
	/**
	 * 
	 * @Method Name : getPostingList
	 * @Method 설명 : 검색어(소제품)에 해당하는 포스팅 리스트의 두번째 페이지 이상에서 해당 페이지를 반환해주는 메서드
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @param searchWord
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getPostingList.do")
	public ArrayList<BlliPostingVO> getPostingList(String pageNo, String searchWord){
		return postingService.searchPosting(pageNo, searchWord);
	}
	//스케줄러 완성 전까지 임시 사용
	@RequestMapping("insertBigCategory.do")
	public ModelAndView insertBigCategory() throws IOException{
		categoryAndProductScheduler.insertBigCategory();
		return new ModelAndView("insertDataResult");
	}
	//스케줄러 완성 전까지 임시 사용
	@RequestMapping("insertMidCategory.do")
	public ModelAndView insertMidCategory() {
		categoryAndProductScheduler.insertMidCategory();
		return new ModelAndView("insertDataResult");
	}
	//스케줄러 완성 전까지 임시 사용
	@RequestMapping("insertSmallProduct.do")
	public ModelAndView insertSmallProduct() {
		categoryAndProductScheduler.insertSmallProduct();
		return new ModelAndView("insertDataResult");
	}
	/**
	  * @Method Name : goPosting
	  * @Method 설명 : 포스팅으로 이동하기 위한 메서드!
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingVO
	  * @return
	 */
	@RequestMapping("goPosting.do")
	public ModelAndView goPosting(BlliPostingVO blliPostingVO){
		ModelAndView mav = new ModelAndView();
		mav.addObject(blliPostingVO);
		mav.setViewName("/blli/postingContents");
		return mav;
	}
	/**
	  * @Method Name : recordResidenceTime
	  * @Method 설명 : 체류시간을 기록하는 메서드
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingVO
	 */
	@RequestMapping("recordResidenceTime.do")
	@ResponseBody
	public String recordResidenceTime(BlliPostingVO blliPostingVO){
		System.out.println("오긴오냐");
		postingService.recordResidenceTime(blliPostingVO);
		return "success";
	}
	
	@RequestMapping("goMidCategoryDetailView.do")
	public ModelAndView goMidCategoryDetailView(BlliMidCategoryVO blliMidCategoryVO){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("midCategoryDetailPage");
		blliMidCategoryVO.getMidCategoryId();
		return mav;
	}
	/**
	 * 
	 * @Method Name : goSmallProductDetailView
	 * @Method 설명 : 소제품을 클릭할 때 소제품 상세 페이지를 출력해주는 메서드
	 * @작성일 : 2016. 2. 3.
	 * @작성자 : hyunseok
	 * @param smallProduct
	 * @return
	 */
	@RequestMapping("goSmallProductDetailView.do")
	public ModelAndView goSmallProductDetailView(String smallProduct,HttpServletRequest request){
		HttpSession session = request.getSession();
		BlliMemberVO blliMemberVO = null;
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> smallProductInfo = productService.searchSmallProduct(smallProduct);
		String smallProductId = ((BlliSmallProductVO)smallProductInfo.get("smallProduct")).getSmallProductId() ;
		ArrayList<BlliPostingVO> postingList = 
				postingService.searchPostingListInProductDetail(smallProductId,request,"1");
		List<BlliWordCloudVO> wordCloudList = productService.selectWordCloudList(smallProductId);
		if(session!=null){
			blliMemberVO = (BlliMemberVO) session.getAttribute("blliMemberVO");
			BlliSmallProductVO blliSmallProductVO = productService.productDibChecker(blliMemberVO.getMemberId(),(BlliSmallProductVO) smallProductInfo.get("smallProduct"));
			((BlliSmallProductVO) smallProductInfo.get("smallProduct")).setIsDib(blliSmallProductVO.getIsDib());
		}
		
		mav.addObject("smallProductInfo", smallProductInfo);
		mav.addObject("blliPostingVOList", postingList);
		mav.addObject("blliMemberVO",blliMemberVO);
		mav.addObject("wordCloudList",wordCloudList);
		mav.setViewName("smallProductDetailPage");
		return mav;
	}
	/**
	 * 
	 * @Method Name : getSmallProductList
	 * @Method 설명 : 중분류 상세 페이지 무한 스크롤을 위한 페이징 메서드
	 * @작성일 : 2016. 2. 3.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @param searchWord
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSmallProductList.do")
	public ArrayList<BlliSmallProductVO> getSmallProductList(String pageNo, String searchWord){
		ArrayList<BlliSmallProductVO> smallProductList = productService.searchMidCategory(pageNo, searchWord);
		if(smallProductList.isEmpty()){
			smallProductList = productService.searchSmallProductList(pageNo, searchWord);
		}
		for(int i=0;i<smallProductList.size();i++){
			smallProductList.get(i).setBlliWordCloudVOList(productService.selectWordCloudList(smallProductList.get(i).getSmallProductId()));
		}
		return smallProductList;
	}
	@RequestMapping("selectSmallProductRank.do")
	@ResponseBody
	public List<BlliSmallProductVO> selectSmallProductRank(String midCategoryId){
		System.out.println(productService.selectSmallProductRank(midCategoryId));
		return productService.selectSmallProductRank(midCategoryId);
	}
	@RequestMapping("selectPostingBySmallProduct.do")
	@ResponseBody
	public List<BlliPostingVO> selectPostingBySmallProduct(String smallProductIdList ,String pageNum,String memberId){
		//return productService.selectSmallProductRank(midCategoryId);
		System.out.println(smallProductIdList);
		System.out.println(pageNum);
		String list[] = smallProductIdList.split("/");
		List<BlliSmallProductVO> blliSmallProductVOList = new ArrayList<BlliSmallProductVO>();
		for(int i=0;i<list.length;i++){
			BlliSmallProductVO blliSmallProductVO = new BlliSmallProductVO(); 
			blliSmallProductVO.setSmallProductId(list[i]);
			blliSmallProductVOList.add(blliSmallProductVO);
		}
		System.out.println(productService.selectPostingBySmallProductList(blliSmallProductVOList, memberId, pageNum));
		return productService.selectPostingBySmallProductList(blliSmallProductVOList, memberId, pageNum);
	}
	
	@RequestMapping("selectPostingBySmallProductInSmallProductDetailView.do")
	@ResponseBody
	public List<BlliPostingVO> selectPostingBySmallProductInSmallProductDetailView(String smallProductId ,String pageNum,HttpServletRequest request){
		ArrayList<BlliPostingVO> postingList = 
				postingService.searchPostingListInProductDetail(smallProductId,request,pageNum);
		return postingList;
	}
	@RequestMapping("footerStatics.do")
	@ResponseBody
	public HashMap<String,String> footerStatics(){
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("productStatics", productService.selectTotalProductNum());
		map.put("postingStatics", postingService.selectTotalPostingtNum());
		return map;
	}
	
	@RequestMapping("goBuyMidPage.do")
	public ModelAndView goBuyMidPage(BlliBuyLinkClickVO blliBuyLinkClickVO,HttpServletRequest request){
		System.out.println(blliBuyLinkClickVO);
		ModelAndView mav = new ModelAndView();
		String targetURL = request.getParameter("buyLink");
		System.out.println(targetURL);
		mav.setViewName("buyMidPage");
		mav.addObject("blliBuyLinkClickVO", blliBuyLinkClickVO);
		mav.addObject("targetURL", targetURL);
		productService.buyLinkClick(blliBuyLinkClickVO);
		return mav;
	}
	
	//임시 메서드
	@RequestMapping("postingMarker.do")
	public void postingMarker() throws ParseException{
		postingMarker.postingMarkering();
	}
	//임시 메서드
	@RequestMapping("productMarker.do")
	public void productMarker() throws ParseException{
		postingMarker.productMarkering();
	}
	//임시 메서드
	@RequestMapping("smallProductRankingMaker.do")
	public void smallProductRankingMaker() throws ParseException{
		postingMarker.smallProductRankingMaker();
	}
}
