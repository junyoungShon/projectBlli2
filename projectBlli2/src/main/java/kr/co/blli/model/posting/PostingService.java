package kr.co.blli.model.posting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.ListVO;

public interface PostingService {
	abstract String jsoupTest() throws IOException;

	abstract ArrayList<BlliPostingVO> searchJsoupTest(String searchWord);

	abstract ListVO postingListWithSmallProducts(String pageNo) throws IOException;

	abstract void selectProduct(List<Map<String, Object>> urlAndProduct);

	abstract void recordResidenceTime(BlliPostingVO blliPostingVO);

}
