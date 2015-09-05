package com.craftsmanasia.request;

import javax.xml.bind.annotation.XmlElement;

public class PagingRequest {
	
	private int pageNumber;
	private int pageSize;
	
	@XmlElement
	private String orderBy;
	
	@XmlElement
	private String order;
	
	public int getPageNumber() {
        return pageNumber;
    }
    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isAsc() {
		if ("desc".equalsIgnoreCase(order)) {
			return false;
		} else {
			return true;
		}
    }
	
	public boolean validatePagingRequest() {
    	if (pageNumber <= 0 || pageSize <= 0 || pageSize > 1000) {
    		return false;
    	}
    	return true;
    }
}
