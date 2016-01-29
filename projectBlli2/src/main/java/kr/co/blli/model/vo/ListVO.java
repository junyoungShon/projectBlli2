package kr.co.blli.model.vo;

import java.util.List;

public class ListVO {
	private List<BlliPostingVO> postingList;
	private List<BlliSmallProductVO> smallProductList;
	private BlliPagingBean pagingBean;

	public ListVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListVO(List<BlliPostingVO> postingList, BlliPagingBean pagingBean) {
		super();
		this.postingList = postingList;
		this.pagingBean = pagingBean;
	}
	
	public List<BlliSmallProductVO> getSmallProductList() {
		return smallProductList;
	}

	public void setSmallProductList(List<BlliSmallProductVO> smallProductList) {
		this.smallProductList = smallProductList;
	}

	public List<BlliPostingVO> getPostingList() {
		return postingList;
	}

	public void setPostingList(List<BlliPostingVO> postingList) {
		this.postingList = postingList;
	}

	public BlliPagingBean getPagingBean() {
		return pagingBean;
	}

	public void setPagingBean(BlliPagingBean pagingBean) {
		this.pagingBean = pagingBean;
	}

}