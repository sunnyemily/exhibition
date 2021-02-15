package cn.org.chtf.card.manage.function.pojo;

public class Function implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9022468541718536621L;

	private Integer functionId;

    private String functionName;

    private Integer functionParentid;

    private String functionUrl;

    private Integer functionOrder;

    private String language;

    private String functionIco;
    private boolean functionIsInterface;
    private Integer functionMenuId;

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public Integer getFunctionParentid() {
        return functionParentid;
    }

    public void setFunctionParentid(Integer functionParentid) {
        this.functionParentid = functionParentid;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl == null ? null : functionUrl.trim();
    }

    public Integer getFunctionOrder() {
        return functionOrder;
    }

    public void setFunctionOrder(Integer functionOrder) {
        this.functionOrder = functionOrder;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

	public String getFunctionIco() {
		return functionIco;
	}

	public void setFunctionIco(String functionIco) {
        this.functionIco = functionIco == null ? null : functionIco.trim();
	}

	public boolean isFunctionIsInterface() {
		return functionIsInterface;
	}

	public void setFunctionIsInterface(boolean functionIsInterface) {
		this.functionIsInterface = functionIsInterface;
	}

	public Integer getFunctionMenuId() {
		return functionMenuId;
	}

	public void setFunctionMenuId(Integer functionMenuId) {
		this.functionMenuId = functionMenuId;
	}
}