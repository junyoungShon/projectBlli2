package kr.co.blli.model.admin;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Map;

public interface AdminService {
	
	public void sendMail(String recipient, String subject, String text, Map<String, Object> textParams, String formUrl) 
			throws FileNotFoundException, URISyntaxException;
}
