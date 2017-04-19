package com.linle.problemType.model;

import java.io.Serializable;
import java.util.List;

import com.linle.commonProblem.model.CommonProblem;

public class ProblemType implements Serializable {

	private static final long serialVersionUID = -4071809166279383675L;

	private Long id;

	private String typeName;

	private Integer status;

	private Integer sort;

	private Integer belongTo;

	private List<CommonProblem> commonProblemList;

	public Integer getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(Integer belongTo) {
		this.belongTo = belongTo;
	}

	public List<CommonProblem> getCommonProblemList() {
		return commonProblemList;
	}

	public void setCommonProblemList(List<CommonProblem> commonProblemList) {
		this.commonProblemList = commonProblemList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}