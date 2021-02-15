package cn.org.chtf.card.manage.dictionaries.pojo;

public class Dictionaries {
    private Integer dicId;

    private String dicCode;

    private String dicName;

    private String dicParentcode;

    private Integer dicType;

    private String dicDescription;
    
    private Integer dicOrder;

    public Integer getDicId() {
        return dicId;
    }

    public void setDicId(Integer dicId) {
        this.dicId = dicId;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode == null ? null : dicCode.trim();
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
    }

    public String getDicParentcode() {
        return dicParentcode;
    }

    public void setDicParentcode(String dicParentcode) {
        this.dicParentcode = dicParentcode == null ? null : dicParentcode.trim();
    }

    public Integer getDicType() {
        return dicType;
    }

    public void setDicType(Integer dicType) {
        this.dicType = dicType;
    }

    public String getDicDescription() {
        return dicDescription;
    }

    public void setDicDescription(String dicDescription) {
        this.dicDescription = dicDescription == null ? null : dicDescription.trim();
    }

	public Integer getDicOrder() {
		return dicOrder;
	}

	public void setDicOrder(Integer dicOrder) {
		this.dicOrder = dicOrder;
	}
}