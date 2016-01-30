package kr.co.blli.model.posting;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import kr.co.blli.model.product.ProductDAO;
import kr.co.blli.model.vo.BlliPostingVO;

import org.springframework.stereotype.Service;

@Service
public class PostingServiceImpl implements PostingService {
	
	@Resource
	private PostingDAO postingDAO;
	
	
	/**
	 * 
	 * @Method Name : searchSmallProduct
	 * @Method 설명 : 검색어(소제품)에 해당하는 포스팅을 찾아주는 메서드
	 * @작성일 : 2016. 1. 16.
	 * @작성자 : hyunseok
	 * @param searchWord
	 * @return
	 */
	@Override
	public ArrayList<BlliPostingVO> searchSmallProduct(String pageNo, String searchWord) {
		HashMap<String, String> map = new HashMap<String, String>();
		if(pageNo == null || pageNo == ""){
			pageNo = "1";
		}
		map.put("pageNo", pageNo);
		map.put("searchWord", searchWord);
		ArrayList<BlliPostingVO> list = (ArrayList<BlliPostingVO>)postingDAO.searchSmallProduct(map);
		return list;
	}
	
	/**
	  * @Method Name : recordResidenceTime
	  * @Method 설명 : 포스팅내에 얼마나 머물렀는지를 초단위로 기록하며, 포스팅의 조회수를 올려준다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingVO
	 */
	@Override
	public void recordResidenceTime(BlliPostingVO blliPostingVO) {
		postingDAO.updatePostingViewCountAndResidenceTime(blliPostingVO);
	}

	/**
	 * 
	 * @Method Name : totalPageOfPosting
	 * @Method 설명 : 검색 결과에 해당하는 포스팅의 총 페이지 수를 반환해주는 메서드
	 * @작성일 : 2016. 1. 27.
	 * @작성자 : hyunseok
	 * @param searchWord
	 * @return
	 */
	@Override
	public int totalPageOfPosting(String searchWord) {
		return postingDAO.totalPageOfPosting(searchWord);
	}
}
