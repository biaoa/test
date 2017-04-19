package com.linle.vote.model;

import java.util.Date;
import java.util.List;

import com.linle.entity.sys.BaseDomain;
import com.linle.entity.sys.Users;
import com.linle.voteOptions.model.VoteOptions;

public class Vote extends BaseDomain{
    private Long themeId;

    private String content;

    private String imgUrl;

    private Integer pattern;

    private Integer patternCount;

    private long voteCount;//投票总数
    
    private long voteUserCount;//参与投票成员总人数
    
    private Long userId;

    private Integer votePrivacy;

    private Integer votePrvlg;

    private Integer status;

    private Long communityId;
    
	private float remindTime;

    private String startDate;

    private String endDate;

    private int isDel;
    
    private int pushFlg;
    
    private String optionsContents;
    
    private Date createDate;
    
    private String createDateStr;
    

	private Users user;
	
	private List<VoteOptions>  voteOptionsList;
	
	private VoteOptions voteOptions;
	
	private long userVoteCount;//用户投票选项总数  判断是否投票过没
	
	private long voteUserAddressCount;//判断当前用户该活动是否有相同房号地址投过票
	
    public long getVoteUserAddressCount() {
		return voteUserAddressCount;
	}

	public void setVoteUserAddressCount(long voteUserAddressCount) {
		this.voteUserAddressCount = voteUserAddressCount;
	}

	public long getUserVoteCount() {
		return userVoteCount;
	}

	public int getPushFlg() {
		return pushFlg;
	}

	public void setPushFlg(int pushFlg) {
		this.pushFlg = pushFlg;
	}

	public long getVoteUserCount() {
		return voteUserCount;
	}

	public void setVoteUserCount(long voteUserCount) {
		this.voteUserCount = voteUserCount;
	}

	public void setUserVoteCount(long userVoteCount) {
		this.userVoteCount = userVoteCount;
	}

	public VoteOptions getVoteOptions() {
		return voteOptions;
	}

	public void setVoteOptions(VoteOptions voteOptions) {
		this.voteOptions = voteOptions;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public List<VoteOptions> getVoteOptionsList() {
		return voteOptionsList;
	}

	public void setVoteOptionsList(List<VoteOptions> voteOptionsList) {
		this.voteOptionsList = voteOptionsList;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
    public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getOptionsContents() {
		return optionsContents;
	}

	public void setOptionsContents(String optionsContents) {
		this.optionsContents = optionsContents;
	}

	public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public String getContent() {
        return content;
    }
    public Long getCommunityId() {
 		return communityId;
 	}

 	public void setCommunityId(Long communityId) {
 		this.communityId = communityId;
 	}

    public void setContent(String content) {
        this.content = content;
    }


    public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getPattern() {
        return pattern;
    }

    public void setPattern(Integer pattern) {
        this.pattern = pattern;
    }

    public Integer getPatternCount() {
        return patternCount;
    }

    public void setPatternCount(Integer patternCount) {
        this.patternCount = patternCount;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getVotePrivacy() {
        return votePrivacy;
    }

    public void setVotePrivacy(Integer votePrivacy) {
        this.votePrivacy = votePrivacy;
    }

    public Integer getVotePrvlg() {
        return votePrvlg;
    }

    public void setVotePrvlg(Integer votePrvlg) {
        this.votePrvlg = votePrvlg;
    }


    public float getRemindTime() {
        return remindTime;
    }

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setRemindTime(float remindTime) {
        this.remindTime = remindTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}