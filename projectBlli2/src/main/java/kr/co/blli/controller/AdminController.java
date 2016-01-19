package kr.co.blli.controller;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

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
		
		try {
			adminService.sendMail(memberId, mailForm);
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (URISyntaxException ue) {
			ue.printStackTrace(); 
		}
		
		return "admin/adminPage";
	}
}
