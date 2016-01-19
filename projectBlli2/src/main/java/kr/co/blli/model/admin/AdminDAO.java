package kr.co.blli.model.admin;

import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberVO;

public interface AdminDAO {

	BlliMemberVO findMemberInfoById(String memberId);
	BlliMailVO findMailSubjectAndContentByMailForm(String mailForm);
}
 