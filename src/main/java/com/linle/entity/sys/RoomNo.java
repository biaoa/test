package com.linle.entity.sys;

import java.io.Serializable;

import com.linle.common.util.ExcelVOAttribute;

public class RoomNo implements Serializable{
	private static final long serialVersionUID = -7359787330722986402L;

	private Long id;
    
    @ExcelVOAttribute(column = "A", name = "")
    private String building;
    
    @ExcelVOAttribute(column = "B", name = "")
    private String unit;
    
    @ExcelVOAttribute(column = "C", name = "")
    private String room;

    private Long communityId;
    
    
    private String overall; //幢-单元-房号

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

	public String getOverall() {
		return overall;
	}

	public void setOverall(String overall) {
		this.overall = overall;
	}
}