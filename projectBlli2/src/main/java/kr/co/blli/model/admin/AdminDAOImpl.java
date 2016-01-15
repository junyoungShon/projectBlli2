package kr.co.blli.model.admin;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAOImpl implements AdminDAO{
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
}
