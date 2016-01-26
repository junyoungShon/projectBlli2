package kr.co.blli.model.vo;

import java.util.List;

public class ListVO {
	private List<BlliPostingVO> postingList;
	private PagingBeanOfPostingListWithProducts pagingBean;

	public ListVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListVO(List<BlliPostingVO> postingList, PagingBeanOfPostingListWithProducts pagingBean) {
		super();
		this.postingList = postingList;
		this.pagingBean = pagingBean;
	}

	public List<BlliPostingVO> getPostingList() {
		return postingList;
	}

	public void setPostingList(List<BlliPostingVO> postingList) {
		this.postingList = postingList;
	}

	public PagingBeanOfPostingListWithProducts getPagingBean() {
		return pagingBean;
	}

	public void setPagingBean(PagingBeanOfPostingListWithProducts pagingBean) {
		this.pagingBean = pagingBean;
	}

}