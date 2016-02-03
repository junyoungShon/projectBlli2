package kr.co.blli.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.posting.PostingService;
import kr.co.blli.model.product.ProductService;
import kr.co.blli.model.scheduler.CategoryAndProductScheduler;
import kr.co.blli.model.scheduler.PostingScheduler;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

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
	public ModelAndView searchSmallProduct(String pageNo, String searchWord){
		ModelAndView mav = new ModelAndView();
		ArrayList<BlliSmallProductVO> smallProductList = productService.searchMidCategory(pageNo, searchWord);
		String viewName = "";
		if(smallProductList.isEmpty()){
			HashMap<String, Object> smallProductInfo = productService.searchSmallProduct(searchWord);
			if(smallProductInfo.get("smallProduct") != null){
				ArrayList<BlliPostingVO> postingList = postingService.searchPostingListInProductDetail(searchWord);
				viewName = "smallProductDetailPage";
				mav.addObject("smallProductInfo", smallProductInfo);
				mav.addObject("postingList", postingList);
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
			mav.addObject("resultList", smallProductList);
			mav.addObject("totalPage", productService.totalPageOfSmallProductOfMidCategory(searchWord));
			mav.addObject("searchWord", searchWord);
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
	public void recordResidenceTime(BlliPostingVO blliPostingVO){
		postingService.recordResidenceTime(blliPostingVO);
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
	public ModelAndView goSmallProductDetailView(String smallProduct){
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> smallProductInfo = productService.searchSmallProduct(smallProduct);
		ArrayList<BlliPostingVO> postingList = postingService.searchPostingListInProductDetail(smallProduct);
		mav.addObject("smallProductInfo", smallProductInfo);
		mav.addObject("postingList", postingList);
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
}
