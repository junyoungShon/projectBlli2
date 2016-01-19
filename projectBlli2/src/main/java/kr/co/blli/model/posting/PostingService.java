package kr.co.blli.model.posting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.blli.model.vo.BlliPostingVO;

public interface PostingService {
	abstract String jsoupTest() throws IOException;

	abstract ArrayList<BlliPostingVO> searchJsoupTest(String searchWord);

	abstract ArrayList<BlliPostingVO> postingListWithSmallProducts() throws IOException;

	abstract void selectProduct(List<Map<String, Object>> urlAndProduct);

}
