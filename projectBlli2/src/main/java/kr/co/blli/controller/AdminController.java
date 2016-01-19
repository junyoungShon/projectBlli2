package kr.co.blli.controller;

import javax.annotation.Resource;

import kr.co.blli.model.admin.AdminService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
