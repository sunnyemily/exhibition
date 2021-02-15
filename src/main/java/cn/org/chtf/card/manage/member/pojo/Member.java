package cn.org.chtf.card.manage.member.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Member implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4902755489656688477L;
	/**
	 * 交易团类型码
	 */
	public static final int MEMBER_TYPE_TRADE_CODE = 0;
	/**
	 * 记者类型码
	 */
	public static final int MEMBER_TYPE_REPORT_CODE = 1;
	/**
	 * 参展商类型码
	 */
	public static final int MEMBER_TYPE_EXHIBITOR_CODE = 2;
	/**
	 * 布撤展上类型码
	 */
	public static final int MEMBER_TYPE_DECORATOR_CODE = 3;
	/**
	 * 外宾类型码
	 */
	public static final int MEMBER_TYPE_FOREIGN_CODE = 4;
	/**
	 * 采购商类型码
	 */
	public static final int MEMBER_TYPE_PURCHASER_CODE = 5;
	/**
	 * 观众类型码
	 */
	public static final int MEMBER_TYPE_AUDIENCE_CODE = 6;
	/**
	 * 线上参展商类型码
	 */
	public static final int MEMBER_TYPE_EXHIBITOR_ONLINE_CODE = 7;
	/**
	 * 企业安全错误次数
	 */
	public static final String MEMBER_VALIDATE_ERROR_KEY = "VALIDATEERRORCOUNT";
	
    private Integer memberId;

    private String memberUsername;

    private String memberPassword;

    private String memberCompany;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date memberRegistDate;

    private Integer memberStatus;
    
    private Integer memberType;

    private String memberActivationCode;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date memberActivationDate;
    private Integer memberSessionId;
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername == null ? null : memberUsername.trim();
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword == null ? null : memberPassword.trim();
    }

    public String getMemberCompany() {
        return memberCompany;
    }

    public void setMemberCompany(String memberCompany) {
        this.memberCompany = memberCompany == null ? null : memberCompany.trim();
    }

    public Date getMemberRegistDate() {
        return memberRegistDate;
    }

    public void setMemberRegistDate(Date memberRegistDate) {
        this.memberRegistDate = memberRegistDate;
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getMemberActivationCode() {
        return memberActivationCode;
    }

    public void setMemberActivationCode(String memberActivationCode) {
        this.memberActivationCode = memberActivationCode == null ? null : memberActivationCode.trim();
    }

    public Date getMemberActivationDate() {
        return memberActivationDate;
    }

    public void setMemberActivationDate(Date memberActivationDate) {
        this.memberActivationDate = memberActivationDate;
    }

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getMemberSessionId() {
		return memberSessionId;
	}

	public void setMemberSessionId(Integer memberSessionId) {
		this.memberSessionId = memberSessionId;
	}
}