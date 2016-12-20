package com.idg.bfzb.server.common.model;

import java.io.Serializable;
import java.util.List;

public class PageInfo<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//一页显示的记录数
	private int pageSize;
	//记录总数
	private int totalRows;
	//总页数
	private int totalPages;
	//当前页码
	private int pageNum;
	//起始行数
	private int startIndex;
	//结束行数
	private int lastIndex;
	//结果集存放List
	private List<T> pageData;

	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getTotalRows() {
		return totalRows;
	}


	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}


	public int getTotalPages() {
		return totalPages;
	}


	public void setTotalPages() {
		if(this.totalRows % this.pageSize == 0){
			this.totalPages = this.totalRows / this.pageSize;
		}else{
			this.totalPages = (totalRows / this.pageSize) + 1;
		}
		
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getStartIndex() {
		return startIndex;
	}


	public void setStartIndex() {
		this.startIndex = this.pageNum * this.pageSize;
	}


	public int getLastIndex() {
		return lastIndex;
	}


	public void setLastIndex() {
		if( this.totalRows < this.pageSize){
			this.lastIndex = this.totalRows;
		}else if((this.totalRows % this.pageSize == 0) || (this.totalRows % this.pageSize != 0 && this.pageNum < this.totalPages)){
			this.lastIndex = (this.pageNum+1) * this.pageSize;
		}else if(this.totalRows % this.pageSize != 0 && this.pageNum == this.totalPages){//最后一页
			this.lastIndex = this.totalRows ;
		}
	}


	public List<T> getPageData() {
		return pageData;
	}


	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
}
