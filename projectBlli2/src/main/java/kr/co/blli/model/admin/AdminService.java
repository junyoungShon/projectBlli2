package kr.co.blli.model.admin;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Map;

public interface AdminService {
	
	public void sendMail(String memberId, String mailForm) throws FileNotFoundException, URISyntaxException;
}
