package kr.co.blli.model.posting;

import java.io.IOException;
import java.util.ArrayList;

import kr.co.blli.model.vo.BlliPostingVO;

public interface PostingService {
	abstract String jsoupTest() throws IOException;

	abstract ArrayList<BlliPostingVO> searchJsoupTest(String searchWord);
}
