package com.linle.voteOptions.model;

import java.util.Date;

import com.linle.entity.sys.Users;
import com.linle.voteUser.model.VoteUser;

public class VoteOptions {
    private Long optionsId;

    private String content;

    private Long themeId;
    
    private VoteUser voteUser;
    
    private long optionsCount;
    
    private String createDateStr;
    private Date createDate;
    private Users user;
    private String voteUsers;//投票人集合：该选项所有投票人
    private int isVoteOptions;//是否投票过该选项
    
    public int getIsVoteOptions() {
		return isVoteOptions;
	}

	public void setIsVoteOptions(int isVoteOptions) {
		this.isVoteOptions = isVoteOptions;
	}

	private double percent;
    
	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getVoteUsers() {
		return voteUsers;
	}

	public void setVoteUsers(String voteUsers) {
		this.voteUsers = voteUsers;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public VoteUser getVoteUser() {
		return voteUser;
	}

	public long getOptionsCount() {
		return optionsCount;
	}

	public void setOptionsCount(long optionsCount) {
		this.optionsCount = optionsCount;
	}

	public void setVoteUser(VoteUser voteUser) {
		this.voteUser = voteUser;
	}

	public Long getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(Long optionsId) {
        this.optionsId = optionsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }
}