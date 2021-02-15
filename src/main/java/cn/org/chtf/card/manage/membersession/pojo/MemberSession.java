package cn.org.chtf.card.manage.membersession.pojo;

public class MemberSession {
	/**
	 * 主键，用于取证箱号
	 */
	private Integer middleid;
    private Integer memberId;

    private Integer id;

    private Integer orgnizationId;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgnizationId() {
        return orgnizationId;
    }

    public void setOrgnizationId(Integer orgnizationId) {
        this.orgnizationId = orgnizationId;
    }

	/**
	 * @return the middleid
	 */
	public Integer getMiddleid() {
		return middleid;
	}

	/**
	 * @param middleid the middleid to set
	 */
	public void setMiddleid(Integer middleid) {
		this.middleid = middleid;
	}
}