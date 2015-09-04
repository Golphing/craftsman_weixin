package com.craftsmanasia.model.filter;

public class SearchFilter {
    // 用于控制最多的返回记录数，避免造成数据性能问题
    private static final int MAX_RECORD = 1000;
    
    private Integer limit;
    
    private boolean paged;
    private PagingData pagingData;
    
    private boolean ordered;

	public boolean isPaged() {
        return paged;
    }
    
    public void setPaged(boolean paged) {
        this.paged = paged;
    }
    
    public PagingData getPagingData() {
        return pagingData;
    }
    
    public void setPagingData(PagingData pagingData) {
        this.pagingData = pagingData;
    }
    
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public int getLimit() {
        if (limit  == null) {
            return MAX_RECORD;
        }
        return limit;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

}
