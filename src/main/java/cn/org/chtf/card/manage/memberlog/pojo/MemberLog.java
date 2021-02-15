package cn.org.chtf.card.manage.memberlog.pojo;

import java.util.Date;

public class MemberLog {
    private Integer mlogId;

    private Integer memberId;

    private String mlogIp;

    private Date mlogDatetime;

    private String mlogDescription;

    public Integer getMlogId() {
        return mlogId;
    }

    public void setMlogId(Integer mlogId) {
        this.mlogId = mlogId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMlogIp() {
        return mlogIp;
    }

    public void setMlogIp(String mlogIp) {
        this.mlogIp = mlogIp == null ? null : mlogIp.trim();
    }

    public Date getMlogDatetime() {
        return mlogDatetime;
    }

    public void setMlogDatetime(Date mlogDatetime) {
        this.mlogDatetime = mlogDatetime;
    }

    public String getMlogDescription() {
        return mlogDescription;
    }

    public void setMlogDescription(String mlogDescription) {
        this.mlogDescription = mlogDescription == null ? null : mlogDescription.trim();
    }
}