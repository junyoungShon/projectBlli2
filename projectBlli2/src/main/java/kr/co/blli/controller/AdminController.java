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
	public String sendMail(String[] args) {
			
		String recipient = "sk1597530@gmail.com";
		String subject = "왔다!!!!!!!!!";
		String text = "velocity로 메일 보내기 테스트";
		String formUrl = "mailForm_findPassword.vm";
		
		Map<String, Object> textParams = new HashMap<String, Object>();
		
		textParams.put("content", text);
		textParams.put("contentHeight", 150);
		
		try {
			adminService.sendMail(recipient, subject, text, textParams, formUrl);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace(); 
		}
		
		return "admin/adminPage";
	}
}
