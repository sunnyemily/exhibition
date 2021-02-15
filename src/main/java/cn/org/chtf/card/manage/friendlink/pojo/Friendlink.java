package cn.org.chtf.card.manage.friendlink.pojo;

import java.util.Date;

public class Friendlink {
    private Integer friendlinkId;

    private Integer menuId;
    
    private String companyName;

    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	private String starttime;
	private String endtime;

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	private String friendlinkName;

    private String friendlinkAddress;

    private String friendlinkOperator;

    private Date friendlinkUpdatetime;
    
    private Integer friendlinkOrder;
    
    private String friendlinkPicture;

    private String friendlinkPicture2;
    
    private String targets;
    
    private Integer status;
    
    private Integer zhuangtai;
    
    private String videourl;
    
    public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}

	public Integer getZhuangtai() {
		return zhuangtai;
	}

	public void setZhuangtai(Integer zhuangtai) {
		this.zhuangtai = zhuangtai;
	}

	private String firendcontent;

    public String getFirendcontent() {
		return firendcontent;
	}

	public void setFirendcontent(String firendcontent) {
		this.firendcontent = firendcontent;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFriendlinkId() {
        return friendlinkId;
    }

    public void setFriendlinkId(Integer friendlinkId) {
        this.friendlinkId = friendlinkId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getFriendlinkName() {
        return friendlinkName;
    }

    public void setFriendlinkName(String friendlinkName) {
        this.friendlinkName = friendlinkName == null ? null : friendlinkName.trim();
    }

    public String getFriendlinkAddress() {
        return friendlinkAddress;
    }

    public void setFriendlinkAddress(String friendlinkAddress) {
        this.friendlinkAddress = friendlinkAddress == null ? null : friendlinkAddress.trim();
    }

    public String getFriendlinkOperator() {
        return friendlinkOperator;
    }

    public void setFriendlinkOperator(String friendlinkOperator) {
        this.friendlinkOperator = friendlinkOperator == null ? null : friendlinkOperator.trim();
    }

    public Date getFriendlinkUpdatetime() {
        return friendlinkUpdatetime;
    }

    public void setFriendlinkUpdatetime(Date friendlinkUpdatetime) {
        this.friendlinkUpdatetime = friendlinkUpdatetime;
    }

	public Integer getFriendlinkOrder() {
		return friendlinkOrder;
	}

	public void setFriendlinkOrder(Integer friendlinkOrder) {
		this.friendlinkOrder = friendlinkOrder;
	}

	public String getFriendlinkPicture() {
		return friendlinkPicture;
	}

	public void setFriendlinkPicture(String friendlinkPicture) {
		this.friendlinkPicture = friendlinkPicture;
	}

	public String getFriendlinkPicture2() {
		return friendlinkPicture2;
	}

	public void setFriendlinkPicture2(String friendlinkPicture2) {
		this.friendlinkPicture2 = friendlinkPicture2;
	}
}