package com.linle.entity.sys;

public class ShopMainType extends BaseDomain{

	private static final long serialVersionUID = -4721744761511414986L;
	
	private String typeName;
	
	private int sort;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}