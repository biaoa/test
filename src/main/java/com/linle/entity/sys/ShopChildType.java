package com.linle.entity.sys;

public class ShopChildType extends BaseDomain{

	private static final long serialVersionUID = -886319638275743140L;

//	private Long parentId;
	
	private ShopMainType parent;

    private String typeName;

//    public Long getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Long parentId) {
//        this.parentId = parentId;
//    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

	public ShopMainType getParent() {
		return parent;
	}

	public void setParent(ShopMainType parent) {
		this.parent = parent;
	}

}