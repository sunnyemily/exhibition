package cn.org.chtf.card.manage.accessrecords.pojo;

public class AccessRecords {
    private String id;

    private String ticketnum;

    private Integer type;

    private Long datetime;
    private String door;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTicketnum() {
        return ticketnum;
    }

    public void setTicketnum(String ticketnum) {
        this.ticketnum = ticketnum == null ? null : ticketnum.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

	/**
	 * @return the door
	 */
	public String getDoor() {
		return door;
	}

	/**
	 * @param door the door to set
	 */
	public void setDoor(String door) {
		this.door = door;
	}
}