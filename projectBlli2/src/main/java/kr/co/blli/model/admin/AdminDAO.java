package kr.co.blli.model.admin;

import kr.co.blli.model.vo.BlliMailVO;

public interface AdminDAO {

	String findMemberMailAddressById(String memberId);
	BlliMailVO findMailSubjectAndContentByMailForm(String mailForm);
}
 