package kr.co.blli.model.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import kr.co.blli.model.vo.ListVO;

public interface AdminService {
	
	public void sendMail(String memberId, String mailForm) throws FileNotFoundException, URISyntaxException, UnsupportedEncodingException, MessagingException;
	
	abstract ListVO unconfirmedPosting(String pageNo) throws IOException;
	
	abstract ListVO postingListWithSmallProducts(String pageNo) throws IOException;
	
	public ListVO unconfirmedSmallProduct(String pageNo);
	
	abstract void selectProduct(List<Map<String, Object>> urlAndProduct);
	
	abstract void registerPosting(List<Map<String, Object>> urlAndProduct);
	
	public void registerSmallProduct(List<Map<String, Object>> smallProductInfo);
}
