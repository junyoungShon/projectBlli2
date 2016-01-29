package kr.co.blli.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.blli.model.admin.AdminService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
	@Resource
	private AdminService adminService;
	
	@RequestMapping("sendMail.do")
	public String sendMail(String memberId, String mailForm) {
		
		String viewName = "admin/sendMail_success";
		try {
			adminService.sendMail(memberId, mailForm);
		} catch (Exception e) {
			e.printStackTrace();
			viewName = "admin/sendMail_fail";
		}
		
		return viewName;
	}
	/**
	 * 
	 * @Method Name : unconfirmedPosting
	 * @Method 설명 : 확정안된 포스팅의 리스트를 반환해주는 메서드 
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("unconfirmedPosting.do")
	public ModelAndView unconfirmedPosting(String pageNo) throws IOException{
		return new ModelAndView("admin/unconfirmedPosting","resultList",adminService.unconfirmedPosting(pageNo));
	}	
	/**
	 * 
	 * @Method Name : postingListWithSmallProducts
	 * @Method 설명 : 두개 이상의 소제품과 관련된 포스팅의 리스트를 반환해주는 메서드
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("postingListWithSmallProducts.do")
	public ModelAndView postingListWithSmallProducts(String pageNo) throws IOException{
		return new ModelAndView("admin/postingListWithSmallProducts","resultList",adminService.postingListWithSmallProducts(pageNo));
	}
	/**
	 * 
	 * @Method Name : unconfirmedSmallProduct
	 * @Method 설명 : 확정안된 소제품의 리스트를 반환해주는 메서드 
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("unconfirmedSmallProduct.do")
	public ModelAndView unconfirmedSmallProduct(String pageNo){
		return new ModelAndView("admin/unconfirmedSmallProduct","resultList",adminService.unconfirmedSmallProduct(pageNo));
	}	
	/**
	 * 
	 * @Method Name : selectProduct
	 * @Method 설명 : 두개 이상의 소제품과 관련된 포스팅의 소제품을 확정하는 메서드
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param urlAndProduct
	 */
	@ResponseBody
	@RequestMapping("selectProduct.do")
	public void selectProduct(@RequestBody List<Map<String, Object>> urlAndProduct){
		adminService.selectProduct(urlAndProduct);
	}
	/**
	 * 
	 * @Method Name : registerPosting
	 * @Method 설명 : 확정안된 포스팅을 확정하는 메서드 
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param urlAndProduct
	 */
	@ResponseBody
	@RequestMapping("registerPosting.do")
	public void registerPosting(@RequestBody List<Map<String, Object>> urlAndProduct){
		adminService.registerPosting(urlAndProduct);
	}
	/**
	 * 
	 * @Method Name : registerSmallProduct
	 * @Method 설명 : 확정안된 소제품을 확정하는 메서드 
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param smallProductInfo
	 */
	@ResponseBody
	@RequestMapping("registerSmallProduct.do")
	public void registerSmallProduct(@RequestBody List<Map<String, Object>> smallProductInfo){
		adminService.registerSmallProduct(smallProductInfo);
	}
}
