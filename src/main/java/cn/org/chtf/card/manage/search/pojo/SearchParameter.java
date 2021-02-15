package cn.org.chtf.card.manage.search.pojo;

import cn.org.chtf.card.support.util.PageModel;

public class SearchParameter extends PageModel {
    private Integer id;

    private Integer menuid;

    private Integer menutype;
    
    private Integer topmenuid;
    
    private String language;

    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    public Integer getMenutype() {
        return menutype;
    }

    public void setMenutype(Integer menutype) {
        this.menutype = menutype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

	public Integer getTopmenuid() {
		return topmenuid;
	}

	public void setTopmenuid(Integer topmenuid) {
		this.topmenuid = topmenuid;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}