package kr.co.blli.model.posting;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import kr.co.blli.model.vo.BlliMemberScrapeVO;
import kr.co.blli.model.vo.BlliPostingVO;

public interface PostingService {

	abstract ArrayList<BlliPostingVO> searchPosting(String pageNo, String searchWord);

	abstract void recordResidenceTime(BlliPostingVO blliPostingVO);

	abstract int totalPageOfPosting(String searchWord);

	abstract ArrayList<BlliPostingVO> searchPostingListInProductDetail(String searchWord);

	abstract ArrayList<BlliPostingVO> searchPostingListInProductDetail(String smallProductId, HttpServletRequest request, String pageNo);

	abstract String selectTotalPostingtNum();

	abstract BlliPostingVO getPostingInfo(BlliMemberScrapeVO blliMemberScrapeVO, String memberId);

}
