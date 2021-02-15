package cn.org.chtf.card.manage.member.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;

public class RegistParam extends EbsCompanyinfo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4556977569194022006L;

	private Integer memberId;

    private String memberUsername;

    private String memberPassword;

    private String memberCompany;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date memberRegistDate;

    private Integer memberStatus;

    private String memberActivationCode;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date memberActivationDate;

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
}