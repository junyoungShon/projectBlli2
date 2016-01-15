package kr.co.blli.model.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
	@Resource
	private AdminDAO adminDAO;
}
