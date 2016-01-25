package kr.co.blli.controller;

import javax.annotation.Resource;

import kr.co.blli.model.admin.AdminService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	@Resource
	private AdminService adminService;
	
}
 