package com.linle.entity.sys;

import java.util.List;

import com.linle.communitySuggestionsComment.model.CommunitySuggestionsComment;

public class CommunitySuggestions extends BaseDomain{

  
	private static final long serialVersionUID = -1973809515289702867L;

	private Long userId;

    private Long communityId;

    private String advice;
    
    private Long folderId;
    
    private SysFolder folder;
    
    private Users user;

    private String userName;
    private String overall;
    private String mobilePhone;
    
    private int status;
    
    private Integer communityDelFalg; //物业删除状态 0 正常   1 删除
    
    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOverall() {
		return overall;
	}

	public void setOverall(String overall) {
		this.overall = overall;
	}

	private List<CommunitySuggestionsComment>  commentList;
    
    
    public List<CommunitySuggestionsComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommunitySuggestionsComment> commentList) {
		this.commentList = commentList;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public SysFolder getFolder() {
		return folder;
	}

	public void setFolder(SysFolder folder) {
		this.folder = folder;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getCommunityDelFalg() {
		return communityDelFalg;
	}

	public void setCommunityDelFalg(Integer communityDelFalg) {
		this.communityDelFalg = communityDelFalg;
	}

}