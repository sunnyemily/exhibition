package cn.org.chtf.card.manage.basic.pojo;

import java.util.Date;

public class Basic {
    private Integer menuId;

    private Date basicUpdatetime;

    private String basicOperator;

    private String basicContent;

    private String basicIntro;

    private String basicPicture;
    
    private Integer status;
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private String liveaddress;
    public String getLiveaddress() {
		return liveaddress;
	}

	public void setLiveaddress(String liveaddress) {
		this.liveaddress = liveaddress;
	}

	public String getVideortmp() {
		return videortmp;
	}

	public void setVideortmp(String videortmp) {
		this.videortmp = videortmp;
	}

	public String getVideoflv() {
		return videoflv;
	}

	public void setVideoflv(String videoflv) {
		this.videoflv = videoflv;
	}

	public String getVideohls() {
		return videohls;
	}

	public void setVideohls(String videohls) {
		this.videohls = videohls;
	}

	public String getVideoudp() {
		return videoudp;
	}

	public void setVideoudp(String videoudp) {
		this.videoudp = videoudp;
	}

	private String videortmp;
    private String videoflv;
    private String videohls;
    private String videoudp;
    
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

	/**
     * 是否新增标识
     */
    private boolean editType;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Date getBasicUpdatetime() {
        return basicUpdatetime;
    }

    public void setBasicUpdatetime(Date basicUpdatetime) {
        this.basicUpdatetime = basicUpdatetime;
    }

    public String getBasicOperator() {
        return basicOperator;
    }

    public void setBasicOperator(String basicOperator) {
        this.basicOperator = basicOperator == null ? null : basicOperator.trim();
    }

    public String getBasicContent() {
        return basicContent;
    }

    public void setBasicContent(String basicContent) {
        this.basicContent = basicContent == null ? null : basicContent.trim();
    }

	public boolean isEditType() {
		return editType;
	}

	public void setEditType(boolean editType) {
		this.editType = editType;
	}

	public String getBasicIntro() {
		return basicIntro;
	}

	public void setBasicIntro(String basicIntro) {
		this.basicIntro = basicIntro;
	}

	public String getBasicPicture() {
		return basicPicture;
	}

	public void setBasicPicture(String basicPicture) {
		this.basicPicture = basicPicture;
	}
}