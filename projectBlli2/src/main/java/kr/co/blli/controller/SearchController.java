package kr.co.blli.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.blli.model.posting.PostingService;
import kr.co.blli.model.product.ProductService;
import kr.co.blli.model.vo.BlliPostingVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
	
	@Resource
	private PostingService postingService;
	
	@Resource
	private ProductService productService;
	
	@RequestMapping("schedule_jsoupTest.do")
	public ModelAndView JsoupTest() throws IOException{
		String result = postingService.jsoupTest();
		return new ModelAndView("TestPosting", "result", result);
	}
	@RequestMapping("search_jsoupTest.do")
	public ModelAndView JsoupTest2(String searchWord){
		return new ModelAndView("searchResult","resultList",postingService.searchJsoupTest(searchWord));
	}
	@RequestMapping("postingListWithSmallProducts.do")
	public ModelAndView postingListWithSmallProducts() throws IOException{
		return new ModelAndView("postingListWithSmallProducts","resultList",postingService.postingListWithSmallProducts());
	}
	@ResponseBody
	@RequestMapping("selectProduct.do")
	public void selectProduct(@RequestBody List<Map<String, Object>> urlAndProduct){
		postingService.selectProduct(urlAndProduct);
	}
	@RequestMapping("insert_big_category.do")
	public ModelAndView insertBigCategory() throws IOException{
		productService.insertBigCategory();
		return new ModelAndView("insertDataResult");
	}
	@RequestMapping("insert_mid_category.do")
	public ModelAndView insertMidCategory() throws IOException{
		productService.insertMidCategory();
		return new ModelAndView("insertDataResult");
	}
	@RequestMapping("insert_small_product.do")
	public ModelAndView insertSmallProduct() throws IOException{
		productService.insertSmallProduct();
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
		System.out.println(blliPostingVO);
		postingService.recordResidenceTime(blliPostingVO);
	}
	
}
