package kr.co.blli.model.admin;

import java.util.HashMap;
import java.util.List;

import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

public interface AdminDAO {

	BlliMemberVO findMemberInfoById(String memberId);
	
	BlliMailVO findMailSubjectAndContentByMailForm(String mailForm);
	
	List<BlliPostingVO> unconfirmedPosting(String pageNo);
	
	int totalUnconfirmedPosting();
	
	List<BlliPostingVO> postingListWithSmallProducts(String pageNo);
	
	int totalPostingWithProducts();
	
	List<BlliSmallProductVO> unconfirmedSmallProduct(String pageNo);
	
	int totalUnconfirmedSmallProduct();
	
	void deletePosting(BlliPostingVO vo);
	
	void addProduct(HashMap<String, String> map);
	
	void selectProduct(HashMap<String, String> map);
	
	void deleteProduct(String postingUrl);
	
	void registerPosting(BlliPostingVO vo);
	
	void deleteSmallProduct(String smallProductId);

	void registerSmallProduct(BlliSmallProductVO vo);

	void registerAndUpdateSmallProduct(BlliSmallProductVO vo);

	void updateSmallProductName(BlliSmallProductVO vo);

	void updateMidCategoryWhenToUse(BlliSmallProductVO vo);

	String getMidCategory(String smallProductId);
	
}
 
