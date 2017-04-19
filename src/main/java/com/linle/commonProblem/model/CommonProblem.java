package com.linle.commonProblem.model;

public class CommonProblem {
    private Long id;

    private String title;

    private Long typeId;

    private Integer sort;

    private String content;

    private String typeName;
    private int belongTo;
    
    public int getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(int belongTo) {
		this.belongTo = belongTo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}