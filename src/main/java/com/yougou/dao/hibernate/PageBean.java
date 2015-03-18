package com.yougou.dao.hibernate;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;


@XmlRootElement
public class PageBean<T> {
	
	// 正序排列标识
	public static final String ASC = "asc";
	//倒序排列标识
	public static final String DESC = "desc";
	//当前页数
	protected int pageNow = 1;
	//每页多少条
	protected int pageSize = 1;
	//排序字段
	protected String orderBy = null;
	//排序方向
	protected String order = null;
	//是否查询记录总数
	protected boolean autoCount = true;
	
	//返回结果 //
	protected List<T> result = Collections.emptyList();
	//总记录数
	protected int totalCount = -1;
	
	protected int totalPages = -1;
	
	//扩展属性，设定当前显示的翻页列表中的最小编号
	private int rangeMin;
	
	//扩展属性，设定当前显示的翻页列表中的最大编号
	private int rangeMax;
	
	//第一页
	private int firstPage;
	
	//最后一页
	private int lastPage;
	
	//前一页
	@SuppressWarnings("unused")
	private int prePage;
	
	//下一页
	@SuppressWarnings("unused")
	private int nextPage;
	

	public int getFirstPage() {
		return firstPage;
	}


	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}


	public int getLastPage() {
		return lastPage;
	}


	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}


	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}


	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}


	public int getRangeMin() {
		return rangeMin;
	}


	public void setRangeMin(int rangeMin) {
		this.rangeMin = rangeMin;
	}


	public int getRangeMax() {
		return rangeMax;
	}


	public void setRangeMax(int rangeMax) {
		this.rangeMax = rangeMax;
	}


	// 构造函数 //
	public PageBean() {
	}

	
	public PageBean(final int pageSize) {
		setPageSize(pageSize);
	}

	public PageBean(final int pageSize, final boolean autoCount) {
		setPageSize(pageSize);
		setAutoCount(autoCount);
	}

	// 查询参数访问函数 //

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNow() {
		return pageNow;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNow(final int pageNow) {
		this.pageNow = pageNow;
		if (pageNow < 1) {
			this.pageNow = 1;
		}
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	/**
	 * 根据pageNow和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNow - 1) * pageSize) + 1;
	}

	/**
	 * 获得排序字段,无默认值.多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	/**
	 * 获得排序方向.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		//检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr))
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
		}
		this.order = StringUtils.lowerCase(order);
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	// 访问查询结果函数 //

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public int getTotalPages() {
		if (totalCount < 0){
			return -1;
		}
		int count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}
	
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNow + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()){
			return pageNow + 1;
		}else{
			return pageNow;	
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNow - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()){
			return pageNow - 1;
		}else{
			return pageNow;
		}
	}
	
	//设定记录条数的时候要做相关页数，以及当前页的设定
	public void setRecordCount(int totalCount) {
		// 设定记录总述
		this.totalCount = totalCount;
		//计算页数
		this.totalPages=this.totalCount/pageSize+(this.totalCount%pageSize==0?0:1);
		if(this.pageNow>this.totalPages){
			pageNow=totalPages>=1?totalPages:1;
		}
		//开始设定扩展属性,设定相关显示的范围
		if(this.getPageNow()<=10&&this.getTotalPages()<=10){
			this.rangeMin=1;
			this.rangeMax=this.getTotalPages();
			if(this.rangeMax==0){
				this.rangeMax=1;
			}
		}else if(this.getPageNow()<=10&&this.getTotalPages()>=10){
			this.rangeMin=1;
			this.rangeMax=10;
		}else if(this.getPageNow()>(this.getTotalPages()-10)){
			this.rangeMin=this.getTotalPages()-9;
			this.rangeMax=this.getTotalPages();
		}else{
			this.rangeMin=this.getPageNow()-4;
			this.rangeMax=this.getPageNow()+4;
		}
		
		//开始设置前后页、首尾页面
		this.firstPage= 1;
		this.lastPage=this.getTotalPages();
		this.nextPage=Math.min(getPageNow() + 1, this.getTotalPages());
		this.prePage=Math.max(getPageNow() - 1, 1);
	}
}
