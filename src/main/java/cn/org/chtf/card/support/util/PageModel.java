package cn.org.chtf.card.support.util;

/**
 * 分页列表实体模型
 * @author wushixing
 *
 */
public class PageModel{
	
	private String country;
	private String province;
	private String industry;
	private String exhibition;
	private String session;
	private String companyid;
	private String phone;
	private String platenumber;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	private Integer memberid;
	
	public Integer getMemberid() {
		return memberid;
	}
	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	private Integer act;
	
	public Integer getAct() {
		return act;
	}
	public void setAct(Integer act) {
		this.act = act;
	}
	private Integer dicid;
	
	public Integer getDicid() {
		return dicid;
	}
	public void setDicid(Integer dicid) {
		this.dicid = dicid;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getExhibition() {
		return exhibition;
	}
	public void setExhibition(String exhibition) {
		this.exhibition = exhibition;
	}
	/**
	 * 当前页码
	 */
	private Integer page;
	/**
	 * limit 开始行数
	 */
	private Integer start;
	/**
	 * 每页显示数量
	 */
	private Integer limit;
	/**
	 * 搜索关键字
	 */
	private String keywords;
	
	/**
	 * 搜索企业名称
	 */
	private String companyname;
	
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	/**
	 * 排序字段
	 */
	private String field;
	/**
	 * 排序方式
	 */
	private String order;
	/**
	 * 栏目ID
	 */
	private Integer menuId;

	private Integer status;
	
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		if(page>1&&this.limit!=null) {
			this.start = (page-1)*this.limit;
		}
		else {
			this.start=0;
		}
		this.page = this.start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		if(this.page!=null && this.page>1) {
			this.start = (this.page-1)*this.limit;
		}
		else {
			this.start=0;
		}
		this.limit = limit;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		if(StringUtil.isEmpty(field)) {
			this.field=null;
		}
		else
		{
			this.field = StringUtil.humpToLine(field);
		}
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}

	public String getPlatenumber() {
		return platenumber;
	}

	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
