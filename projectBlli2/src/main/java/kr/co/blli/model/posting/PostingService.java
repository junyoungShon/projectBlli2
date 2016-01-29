package kr.co.blli.model.posting;

import java.util.ArrayList;

import kr.co.blli.model.vo.BlliPostingVO;

public interface PostingService {

	abstract ArrayList<BlliPostingVO> searchSmallProduct(String pageNo, String searchWord);

	abstract void recordResidenceTime(BlliPostingVO blliPostingVO);

	abstract int totalPageOfPosting(String searchWord);

}
