package kr.co.blli.model.vo;


public class ListVO {
	private Object list;
	private BlliPagingBean pagingBean;

	public ListVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListVO(Object list, BlliPagingBean pagingBean) {
		super();
		this.list = list;
		this.pagingBean = pagingBean;
	}

	public Object getList() {
		return list;
	}

	public void setList(Object list) {
		this.list = list;
	}

	public BlliPagingBean getPagingBean() {
		return pagingBean;
	}

	public void setPagingBean(BlliPagingBean pagingBean) {
		this.pagingBean = pagingBean;
	}

}